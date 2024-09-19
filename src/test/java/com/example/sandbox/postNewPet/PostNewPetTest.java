package com.example.sandbox.postNewPet;

import static com.example.sandbox.util.body.pet.JsonBody.createJsonBody;
import static com.example.sandbox.util.body.pet.PetBodyHelper.createPetBody;
import static com.example.sandbox.util.constans.Tags.SMOKE;
import static com.example.sandbox.util.constans.TestData.HYDRAIMAGE;
import static com.example.sandbox.util.constans.TestData.PEGAZUSIMAGE;

import com.example.sandbox.Common;
import com.example.sandbox.util.body.pet.PostCreatePet;
import com.example.sandbox.util.swagger.definitions.PetStatus;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.report.TestListener;

@Listeners(TestListener.class)
public class PostNewPetTest extends Common {

  @Test(
      enabled = true,
      groups = {SMOKE},
      description = "description")
  public void test1() {
    PostCreatePet body =
        createPetBody("Princess", "Hydra", HYDRAIMAGE, "cute", PetStatus.AVAILABLE.getStatus());
    postUrl(newPet, createJsonBody(body));
  }

  @Test(
      enabled = true,
      groups = {SMOKE},
      description = "description",
      testName = "Negativ teszt - Missing mandatory fields (name and photoUrl)")
  public void negativTest() {

    PostCreatePet body =
        createPetBody(null, "Pegazus", null, "fast", PetStatus.AVAILABLE.getStatus());
    Response response = postUrl(newPet, createJsonBody(body));
    checkResponseCodeFlag.set(false);
    Assert.assertNotEquals(
        response.getStatusCode(), 200, "No mandatory field(s) missing error message.");
  }

  @Test(
      testName = "Pozitiv teszt - new pet with pending status",
      enabled = true,
      groups = {SMOKE},
      description = "description")
  public void pozitivTest() {

    PostCreatePet body =
        createPetBody("Peggie", "Pegazus", PEGAZUSIMAGE, "fast", PetStatus.PENDING.getStatus());

    Response response = postUrl(newPet, createJsonBody(body));
    String id = response.jsonPath().get("id").toString();
    Response response2 = getUrl(petById.replace("{petId}", id));
    Assert.assertEquals(
        response2.jsonPath().get("status"), PetStatus.PENDING.getStatus(), "Invalid status");
  }
}
