package com.example.sandbox.businessProcesses;

import static com.example.sandbox.util.body.pet.JsonBody.createJsonBody;
import static com.example.sandbox.util.body.pet.PetBodyHelper.createPetBody;
import static com.example.sandbox.util.body.pet.PetBodyHelper.updateStatusPetBody;
import static com.example.sandbox.util.constans.Tags.SMOKE;
import static com.example.sandbox.util.constans.TestData.CHIMERAIMAGE;

import com.example.sandbox.Common;
import com.example.sandbox.util.body.pet.PostCreatePet;
import com.example.sandbox.util.swagger.definitions.PetStatus;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.report.TestListener;

@Listeners(TestListener.class)
public class PetLifeCycle extends Common {

  @Test(
      testName = "Lifecycle Test",
      enabled = true,
      groups = {SMOKE},
      description = "description")
  public void testPetLifecycle() {
    Response response = createAndVerifyPet();
    updatePetStatusAndVerify(response, PetStatus.PENDING);
    deleteAndVerifyPet(response.jsonPath().get("id").toString());
  }

  private Response createAndVerifyPet() {
    PostCreatePet body =
        createPetBody("Chimpi", "Chimera", CHIMERAIMAGE, "cutie", PetStatus.AVAILABLE.getStatus());
    Response response = postUrl(newPet, createJsonBody(body));
    String id = response.jsonPath().get("id").toString();
    Response response2 = getUrl(petById.replace("{petId}", id));
    Assert.assertEquals(response2.jsonPath().get("name"), "Chimpi");
    return response2;
  }

  private void updatePetStatusAndVerify(Response petResponse, PetStatus status) {
    Response response = putUrl(updatePet, createJsonBody(updateStatusPetBody(petResponse, status)));
    Assert.assertEquals(response.jsonPath().get("status"), status.getStatus());
    Assert.assertEquals(response.jsonPath().get("name"), "Chimpi");
  }

  private void deleteAndVerifyPet(String petId) {
    deleteUrl(deletePet.replace("{petId}", petId));
    verifyPetIsDeleted(petId);
  }

  private void verifyPetIsDeleted(String petId) {
    checkResponseCodeFlag.set(false);
    Response response = getUrl(petById.replace("{petId}", petId));
    Assert.assertEquals(response.getStatusCode(), 404);
  }
}
