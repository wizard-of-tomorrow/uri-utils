package org.wizardoftomorrow.uri;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

record UriComparator(UriCompareOptions options) {

  public boolean urlsMatch(String firstUrl, String secondUrl) {
    try {
      if (Objects.equals(firstUrl, secondUrl)) {
        return true;
      }

      var firstUri = new URI(firstUrl).normalize();
      var secondUri = new URI(secondUrl).normalize();

      return
          !schemesMissMatch(firstUri, secondUri) &&
          !hostMissMatch(firstUri, secondUri) &&
          !pathMissMatch(firstUri, secondUri) &&
          !portsMissMatch(firstUri, secondUri) &&
          !queryParamsMissMatch(firstUri, secondUri);

    } catch (Exception exception) {
      return false;
    }
  }

  private boolean pathMissMatch(URI firstUri, URI secondUri) {
    if (!options.pathRequired()) {
      return false;
    }

    var firstPath = getPath(firstUri);
    var secondPath = getPath(secondUri);

    return stringsAreNotEqual(firstPath, secondPath, options.pathCaseSensitive());
  }

  private boolean hostMissMatch(URI firstUri, URI secondUri) {
    if (!options.hostRequired()) {
      return false;
    }

    var firstHost = getHost(firstUri);
    var secondHost = getHost(secondUri);

    return stringsAreNotEqual(firstHost, secondHost, options.hostCaseSensitive());
  }

  private boolean queryParamsMissMatch(URI firstUri, URI secondUri) {
    if (!options.queryStringRequired()) {
      return false;
    }

    var firstParams = getQueryStringParams(firstUri);
    var secondParams = getQueryStringParams(secondUri);

    return !mapsAreEqual(firstParams, secondParams);
  }

  private boolean mapsAreEqual(Map<String, String> firstMap, Map<String, String> secondMap) {
    for (var entry : firstMap.entrySet()) {
      var key = entry.getKey();
      var firstMapValue = entry.getValue();
      var secondMapValue = secondMap.get(key);

      if (stringsAreNotEqual(
          firstMapValue,
          secondMapValue,
          options.queryStringValuesCaseSensitive())) {
        return false;
      }
    }

    for (var entry : secondMap.entrySet()) {
      var key = entry.getKey();
      var firstMapValue = entry.getValue();
      var secondMapValue = secondMap.get(key);

      if (stringsAreNotEqual(
          secondMapValue,
          firstMapValue,
          options.queryStringValuesCaseSensitive())) {
        return false;
      }
    }

    return true;
  }

  private boolean schemesMissMatch(URI firstUri, URI secondUri) {
    if (!options.schemaRequired()) {
      return false;
    }

    var httpScheme = UriConstants.HTTP_SCHEME.value;
    var firstScheme = ofNullable(firstUri.getScheme()).orElse(httpScheme);
    var secondScheme = ofNullable(secondUri.getScheme()).orElse(httpScheme);

    return stringsAreNotEqual(firstScheme, secondScheme, options.schemaCaseSensitive());
  }

  private boolean portsMissMatch(URI firstUri, URI secondUri) {
    var firstUriPort = firstUri.getPort();
    var secondUriPort = secondUri.getPort();

    if (firstUriPort == secondUriPort) {
      return false;
    }

    var httpScheme = UriConstants.HTTP_SCHEME.value;
    var notFound = Integer.parseInt(UriConstants.NOT_FOUND.value);
    var httpPort = Integer.parseInt(UriConstants.HTTP_PORT.value);
    var httpsPort = Integer.parseInt(UriConstants.HTTPS_PORT.value);

    if (firstUriPort == notFound) {
      firstUriPort = ofNullable(firstUri.getScheme())
          .orElse(httpScheme)
          .equalsIgnoreCase(httpScheme) ? httpPort : httpsPort;
    }

    if (secondUriPort == notFound) {
      secondUriPort = ofNullable(secondUri.getScheme())
          .orElse(httpScheme)
          .equalsIgnoreCase(httpScheme) ? httpPort : httpsPort;
    }

    return firstUriPort != secondUriPort;
  }

  private boolean stringsAreNotEqual(String firstString,
                                     String secondString,
                                     boolean caseSensitive) {
    if (caseSensitive) {
      return !StringUtils.equals(firstString, secondString);
    }
    return !StringUtils.equalsIgnoreCase(firstString, secondString);
  }

  private Map<String, String> getListAsMap(List<NameValuePair> list) {
    Map<String, String> result = options.queryStringKeysCaseSensitive()
        ? new HashMap<>()
        : new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    for (var param : list) {
      if (options.queryStringKeysCaseSensitive() && result.containsKey(param.getName())) {
        continue;
      }
      result.put(param.getName(), param.getValue());
    }

    return result;
  }

  private Map<String, String> getQueryStringParams(URI uri) {
    return getListAsMap(URLEncodedUtils.parse(uri, StandardCharsets.UTF_8));
  }

  private String getHost(URI url) {
    if (nonNull(url.getScheme())) {
      return url.getHost();
    }

    var path = url.getPath();
    var index = path.indexOf(UriConstants.PATH_DELIMITER.value);
    var notFound = Integer.parseInt(UriConstants.NOT_FOUND.value);

    if (index == notFound) {
      return path;
    }

    return path.substring(0, index);
  }

  private String getPath(URI url) {
    if (nonNull(url.getScheme())) {
      return url.getPath();
    }

    var path = url.getPath();
    var index = path.indexOf(UriConstants.PATH_DELIMITER.value);
    var notFound = Integer.parseInt(UriConstants.NOT_FOUND.value);

    if (index == notFound) {
      return StringUtils.EMPTY;
    }

    return path.substring(index);
  }

  private enum UriConstants {

    PATH_DELIMITER("/"),
    HTTP_SCHEME("http"),
    HTTP_PORT("80"),
    HTTPS_PORT("443"),
    NOT_FOUND("-1"),

    ;

    private final String value;

    UriConstants(String value) {
      this.value = value;
    }

  }

}
