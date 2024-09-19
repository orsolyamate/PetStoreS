package com.example.sandbox;

import static com.example.sandbox.util.Assertions.assertReturnCode;
import static com.example.sandbox.util.Assertions.assertReturnTime;
import static io.restassured.RestAssured.given;

import io.restassured.response.Response;
import java.util.Map;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.report.ReportingFilter;

@SpringBootTest
public class Common extends Endpoints {

  private static final int MAX_RESPONSE_TIME = 500;
  public static ThreadLocal<Boolean> checkResponseCodeFlag = new ThreadLocal<>();
  private static ThreadLocal<Response> threadLocalResponse = new ThreadLocal<>();
  protected ReportingFilter filter;

  @BeforeMethod(alwaysRun = true)
  public void baseBeforeMethod(ITestContext context) {
    filter = new ReportingFilter(context);
    checkResponseCodeFlag.set(true);
  }

  @AfterMethod(alwaysRun = true)
  public void checkResponse() {

    if (checkResponseCodeFlag.get() != null && checkResponseCodeFlag.get()) {
      assertReturnCode(threadLocalResponse.get(), 200);
    }
    assertReturnTime(threadLocalResponse.get(), maxResponseTime);
  }

  // ----------------------------------GET----------------------------------
  public Response getUrl(String endpoint) {

    Response response =
        given()
            .relaxedHTTPSValidation()
            .and()
            .filter(filter)
            .when()
            .get(baseUrl + endpoint)
            .then()
            .extract()
            .response();
    response.getStatusCode();
    threadLocalResponse.set(response);
    return response;
  }

  public Response getUrl(String endpoint, Map<String, String> queryParam) {

    Response response =
        given()
            .relaxedHTTPSValidation()
            .headers("correlationId", "testCorrelid")
            .cookie("session_id", "abc123")
            .param("param", "testParam")
            .formParam("asd", "testFormParams")
            .queryParams(queryParam)
            .and()
            .filter(filter)
            .when()
            .get(baseUrl + endpoint)
            .then()
            .extract()
            .response();
    threadLocalResponse.set(response);
    return response;
  }

  public Response getUrl(
      String endpoint, Map<String, String> headers, Map<String, String> queryParam) {
    Response response =
        given()
            .relaxedHTTPSValidation()
            .params(queryParam)
            .headers(headers)
            .and()
            .filter(filter)
            .when()
            .get(baseUrl + endpoint)
            .then()
            .extract()
            .response();
    threadLocalResponse.set(response);
    return response;
  }

  // ----------------------------------POST----------------------------------
  public Response postUrl(String endpoint, String body) {
    Response response =
        given()
            .relaxedHTTPSValidation()
            .contentType("application/json; charset=UTF-8")
            .body(body)
            .and()
            .filter(filter)
            .when()
            .post(baseUrl + endpoint)
            .then()
            .extract()
            .response();
    threadLocalResponse.set(response);
    return response;
  }

  // ----------------------------------PUT----------------------------------
  public Response putUrl(String endpoint, String body) {
    Response response =
        given()
            .relaxedHTTPSValidation()
            .contentType("application/json; charset=UTF-8")
            .body(body)
            .and()
            .filter(filter)
            .when()
            .put(baseUrl + endpoint)
            .then()
            .extract()
            .response();
    threadLocalResponse.set(response);
    return response;
  }

  // ----------------------------------DELETE----------------------------------
  public Response deleteUrl(String endpoint) {
    Response response =
        given()
            .relaxedHTTPSValidation()
            .contentType("application/json; charset=UTF-8")
            .filter(filter)
            .when()
            .delete(baseUrl + endpoint)
            .then()
            .extract()
            .response();
    threadLocalResponse.set(response);
    return response;
  }
}
