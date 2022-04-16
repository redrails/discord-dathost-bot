package com.ihtasham.model;

public enum GameServerAction {
  START("/start"),
  STOP("/stop"),
  STATUS("");

  private final String uri;

  GameServerAction(final String uri) {
    this.uri = uri;
  }

  public String getUri() {
    return this.uri;
  }
}
