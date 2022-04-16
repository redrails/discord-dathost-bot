package com.ihtasham.model;

public enum Emoji {
  CHECK_EMOJI("\u2705");

  private final String unicode;

  Emoji(final String unicode) {
    this.unicode = unicode;
  }

  public String getUnicode() {
    return this.unicode;
  }
}
