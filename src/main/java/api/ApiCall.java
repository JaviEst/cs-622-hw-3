package api;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiCall {
  private static final String API_URL = "https://api.jikan.moe/v4/anime";

  /**
   * Fetch data from a rest api.
   */
  public static String fetchData() {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = null;

    try {
      request = HttpRequest.newBuilder()
                  .uri(new URI(API_URL))
                  .GET()
                  .build();
    } catch (URISyntaxException e) {
      System.out.printf("Error with URL (%s): %s", API_URL, e.getMessage());
      return "";
    }

    HttpResponse<String> response = null;
    try {
      response = client.send(request, HttpResponse.BodyHandlers.ofString());
      if (response.statusCode() > 299) {
        System.out.println("HTTP error: " + response.statusCode());
        return "";
      } else {
        System.out.println("Successfully fecthed the api data.");
        return response.body();
      }
    } catch (InterruptedException | IOException e) {
      System.out.println("The server connection was interrupted due to" + e.getMessage());
      return "";
    }
  }
}