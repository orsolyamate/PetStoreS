package com.example.sandbox.getPet;

import static com.example.sandbox.util.constans.Tags.SMOKE;

import com.example.sandbox.Common;
import com.example.sandbox.util.swagger.definitions.PetStatus;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.report.TestListener;

@Listeners(TestListener.class)
public class PetDetailTest extends Common {

  private static void checkStatus(Response response) {
    String status = response.jsonPath().get("status");
    if (status != null) {
      try {
        PetStatus.valueOf(status.toUpperCase());
      } catch (IllegalArgumentException e) {
        Assert.fail("The 'status' field value is not valid: " + status);
      }
    }
  }

  private static void checkPhotoUrl(Response response) {
    Assert.assertNotNull(response.jsonPath().get("photoUrls"), "The 'photoUrls' field is null");
    Object photoUrls = response.jsonPath().get("photoUrls");
    Assert.assertTrue(photoUrls instanceof ArrayList<?>, "The 'photoUrls' field is not a List");
  }

  private static void checkName(Response response) {
    Assert.assertNotNull(response.jsonPath().get("name"), "The 'name' field is null");
    Assert.assertTrue(
        response.jsonPath().get("name") instanceof String, "The 'name' field is not a String");
  }

  @Test(
      testName = "Pet detail Test",
      enabled = true,
      groups = {SMOKE},
      description = "description")
  public void detailTest() {
    String id = getPetIdWithAvailableStatus();
    Response response = getUrl(petById.replace("{petId}", id));
    checkName(response);
    checkPhotoUrl(response);
    checkStatus(response);
  }

  private String getPetIdWithAvailableStatus() {
    Map<String, String> queryParams = new TreeMap<>();
    queryParams.put("status", PetStatus.AVAILABLE.getStatus());

    Response response = getUrl(findByStatus, queryParams);
    return response.jsonPath().get("find{it.status.equals('available')}.id").toString();
  }
}
