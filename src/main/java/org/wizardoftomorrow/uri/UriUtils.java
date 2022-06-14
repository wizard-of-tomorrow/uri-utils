package org.wizardoftomorrow.uri;

/**
 * Utility class for manipulating
 * with {@link java.net.URI}
 *
 * @author Marko Vučković
 */
public final class UriUtils {

  /**
   * Private constructor to prevent instantiation
   * of this utility class.
   */
  private UriUtils() {
    super();
  }

  /**
   * Compares two uri strings based on provided comparison settings.
   *
   * @param firstUri Fist URI string
   * @param secondUri Second URI string
   * @param options Options that define what parts of URI should be taken
   *                in consideration when comparing two URIs.
   * @return {@code true} if two uris match, {@code false} otherwise
   */
  public static boolean urisMatch(String firstUri, String secondUri, UriCompareOptions options) {
    return new UriComparator(options).urlsMatch(firstUri, secondUri);
  }

  /**
   * Compares two uri strings based default comparison settings.
   *
   * @param firstUri Fist URI string
   * @param secondUri Second URI string
   * @return {@code true} if two uris match, {@code false} otherwise
   */
  public static boolean urisMatch(String firstUri, String secondUri) {
    return urisMatch(firstUri, secondUri, UriCompareOptions.DEFAULT);
  }

}
