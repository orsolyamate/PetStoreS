package com.example.sandbox.util.body.pet;

import static com.example.sandbox.util.Tools.generateRandomNumber;

import com.example.sandbox.util.swagger.definitions.Item;
import com.example.sandbox.util.swagger.definitions.PetBody;
import com.example.sandbox.util.swagger.definitions.PetStatus;
import io.restassured.response.Response;

public class PetBodyHelper {

  public static PostCreatePet createPetBody(
      String name, String category, String photoUrl, String tag, String status) {
    return PostCreatePet.builder()
        .petBody(
            PetBody.builder()
                .id(generateRandomNumber())
                .category(Item.builder().id(1).name(category).build())
                .name(name)
                .photoUrl(photoUrl)
                .tag(Item.builder().id(2).name(tag).build())
                .status(status)
                .build())
        .build();
  }

  public static PostCreatePet updateStatusPetBody(Response response, PetStatus status) {
    return PostCreatePet.builder()
        .petBody(
            PetBody.builder()
                .id(response.jsonPath().get("id"))
                .category(
                    Item.builder()
                        .id(response.jsonPath().get("category.id"))
                        .name(response.jsonPath().get("category.name"))
                        .build())
                .name(response.jsonPath().get("name"))
                .photoUrl(response.jsonPath().get("photoUrls[0]"))
                .tag(
                    Item.builder()
                        .id(response.jsonPath().get("tags[0].id"))
                        .name(response.jsonPath().get("tags[0].name"))
                        .build())
                .status(status.getStatus())
                .build())
        .build();
  }
}
