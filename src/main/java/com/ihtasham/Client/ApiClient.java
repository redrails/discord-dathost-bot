package com.ihtasham.Client;

import com.ihtasham.model.ApiResponse;
import java.io.IOException;

public interface ApiClient {

  ApiResponse start(final String serverId) throws IOException;

  ApiResponse stop(final String serverId) throws IOException;

  ApiResponse status(final String serverId) throws IOException;
}
