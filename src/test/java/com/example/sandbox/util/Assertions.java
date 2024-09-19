package com.example.sandbox.util;

import io.restassured.response.Response;
import org.junit.Assert;

public class Assertions {
  public static void assertReturnCode(Response response, Integer code) {
    Assert.assertEquals((Integer) response.getStatusCode(), code);
  }

  public static void assertReturnTime(Response response, Integer maxTime) {
    assert response.getTime() < maxTime
        : "The response time is not under " + maxTime + " ms: " + response.getTime() + " ms";
  }
}
