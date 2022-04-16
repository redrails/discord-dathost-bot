package com.ihtasham.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.Builder;

@Builder
public class ApiResponse {

  String response;
  final ObjectMapper objectMapper = new ObjectMapper();

  public String getResponseSummary() throws IOException {
    return String.format("Response: %s", objectMapper.writeValueAsString(response));
  }

  public String getResponseRaw() throws IOException {
    return response;
  }
}
