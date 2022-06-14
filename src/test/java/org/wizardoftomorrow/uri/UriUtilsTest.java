package org.wizardoftomorrow.uri;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class UriUtilsTest {

  @Test
  void shouldProperlyCompareUrls() {

    assertFalse(UriUtils.urisMatch("//test.com?A=a&B=b", "//test.com?a=a&b=b"));
    assertFalse(UriUtils.urisMatch("https://WWW.TEST.COM?A=1&b=2", "https://www.test.com?b=2&a=1"));
    assertFalse(UriUtils.urisMatch("/test?a=2&A=A", "/test?a=A&a=2"));
    assertFalse(UriUtils.urisMatch("https://WWW.TEST.COM?A=a&b=2", "https://www.test.com?b=2&A=1"));
    assertFalse(UriUtils.urisMatch("/test", null));
    assertFalse(UriUtils.urisMatch(null, "/test"));
    assertFalse(UriUtils.urisMatch("//test.com:22?A=a&B=b", "//test.com:443?A=a&B=b"));
    assertFalse(UriUtils.urisMatch("https://WWW.TEST.COM:8443", "https://www.test.com"));
    assertTrue(UriUtils.urisMatch(null, null));
    assertTrue(UriUtils.urisMatch("http://WWW.TEST.COM:2121", "https://www.test.com:2121"));
    assertTrue(UriUtils.urisMatch("HTTPS://WWW.TEST.COM", "https://www.test.com"));
    assertTrue(UriUtils.urisMatch("https://WWW.TEST.COM:443", "https://www.test.com"));
    assertTrue(UriUtils.urisMatch("http://WWW.TEST.COM?A=a&b=2", "https://www.test.com?b=2&A=a"));
    assertTrue(UriUtils.urisMatch("//test.com:443?A=a&B=b", "//test.com:443?A=a&B=b"));
    assertTrue(UriUtils.urisMatch("/test?a=A&a=2", "/test?a=A&a=2"));
    assertTrue(UriUtils.urisMatch("https://www.test.com", "https://www.test.com"));
    assertTrue(
        UriUtils.urisMatch("https://www.test.com?a=1&b=2&c=522%2fMe",
            "https://www.test.com?c=522%2fMe&b=2&a=1"));
    assertTrue(UriUtils.urisMatch("https://WWW.TEST.COM?a=1&b=2", "https://www.test.com?b=2&a=1"));
    assertTrue(UriUtils.urisMatch("https://WWW.TEST.COM?a=1&b=2", "https://www.test.com?b=2&a=1"));
    assertTrue(UriUtils.urisMatch("https://www.hp.com/sg-en/shop/", "www.hp.com/sg-en/shop/"));
    assertTrue(UriUtils.urisMatch("https://www.hp.com", "www.hp.com"));
    assertTrue(
        UriUtils.urisMatch("https://www.hp.com/sg-en/shop/", "http://www.hp.com/sg-en/shop/"));
    assertTrue(UriUtils.urisMatch("www.hp.com", "http://www.hp.com"));

  }

}
