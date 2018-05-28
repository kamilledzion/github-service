package pl.allegro.model;

import static java.util.Objects.hash;

import java.util.Objects;

public final class ErrorResponse {

  private final int status;
  private final String message;

  public ErrorResponse(int status, String message) {
    this.status = status;
    this.message = message;
  }

  public int getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorResponse that = (ErrorResponse) o;
    return status == that.status &&
        Objects.equals(message, that.message);
  }

  @Override
  public int hashCode() {

    return hash(status, message);
  }
}