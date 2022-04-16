package com.ihtasham.Client;

import com.ihtasham.model.ApiResponse;
import com.ihtasham.model.Constants;
import com.ihtasham.model.GameServerAction;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.codec.binary.Base64;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpHeaders;

public class DatHostApiClient implements ApiClient {

  @Override
  public ApiResponse start(String serverId) throws IOException {
    HttpPost request = new HttpPost(generateUri(serverId, GameServerAction.START));
    request.setHeader(HttpHeaders.AUTHORIZATION, getAuthenticationHeader());

    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = httpClient.execute(request)) {
      final String responseString =
          new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
      return ApiResponse.builder().response(responseString).build();
    }
  }

  @Override
  public ApiResponse stop(String serverId) throws IOException {
    HttpPost request = new HttpPost(generateUri(serverId, GameServerAction.STOP));
    request.setHeader(HttpHeaders.AUTHORIZATION, getAuthenticationHeader());

    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = httpClient.execute(request)) {
      final String responseString =
          new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
      return ApiResponse.builder().response(responseString).build();
    }
  }

  @Override
  public ApiResponse status(String serverId) throws IOException {
    HttpGet request = new HttpGet(generateUri(serverId, GameServerAction.STATUS));
    request.setHeader(HttpHeaders.AUTHORIZATION, getAuthenticationHeader());

    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = httpClient.execute(request)) {
      final String responseString =
          new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
      return ApiResponse.builder().response(responseString).build();
    }
  }

  private String generateUri(final String serverId, final GameServerAction gameServerAction) {
    return String.format(
        "%s/game-servers/%s%s", Constants.API_HOST, serverId, gameServerAction.getUri());
  }

  private String getAuthenticationHeader() {
    String auth = System.getenv(Constants.USERNAME) + ":" + System.getenv(Constants.PASSWORD);
    byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
    return "Basic " + new String(encodedAuth);
  }
}
