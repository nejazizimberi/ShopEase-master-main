package com.ShopEase.ShopEase.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a User is not found in the system.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)  // Automatically returns a 404 Not Found status
public class UserNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private Long userId;  // User ID can be null if not provided

  /**
   * Constructor for creating the exception with a custom message only.
   *
   * @param message The error message to be thrown.
   */
  public UserNotFoundException(String message) {
    this(message, null);
  }

  /**
   * Constructor for creating the exception with a custom message and userId.
   *
   * @param message The error message to be thrown.
   * @param userId  The ID of the missing user.
   */
  public UserNotFoundException(String message, Long userId) {
    super(message);
    this.userId = userId;
  }

  /**
   * Retrieves the user ID for the missing user (if available).
   *
   * @return The user ID or null if it is not provided.
   */
  public Long getUserId() {
    return userId;
  }

  /**
   * Custom message that includes the userId if it is available.
   *
   * @return A detailed error message, optionally including the user ID.
   */
  @Override
  public String getMessage() {
    if (userId != null) {
      return super.getMessage() + " (User ID: " + userId + ")";
    } else {
      return super.getMessage();
    }
  }

  /**
   * Provides a string representation of the exception.
   *
   * @return The exception details as a string, including the message and userId if available.
   */
  @Override
  public String toString() {
    return String.format("UserNotFoundException{message='%s', userId=%s}",
            getMessage(), (userId != null ? userId : "N/A"));
  }
}
