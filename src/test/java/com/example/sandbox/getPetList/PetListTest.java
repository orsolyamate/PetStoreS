package com.example.sandbox.getPetList;

import static com.example.sandbox.util.constans.Tags.SMOKE;

import com.example.sandbox.Common;
import java.util.Map;
import java.util.TreeMap;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.report.TestListener;

@Listeners(TestListener.class)
public class PetListTest extends Common {

  @Test(
      enabled = true,
      groups = {SMOKE},
      description = "description")
  public void Test1() {
    Map<String, String> queryParams = new TreeMap<>();
    queryParams.put("status", "available");
    queryParams.put("asd", "asd");
    queryParams.put("maki", "kakadu");

    getUrl(findByStatus, queryParams);
  }

  @Test(
      enabled = true,
      groups = {SMOKE},
      description = "description")
  public void test2() {
    Map<String, String> queryParams = new TreeMap<>();
    queryParams.put("status", "available");
    Map<String, String> headers = new TreeMap<>();
    headers.put("Mandatoyheader", "BFG");

    getUrl(findByStatus, headers, queryParams);
  }
}
