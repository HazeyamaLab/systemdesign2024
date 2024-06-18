package modelUtil;

public class Failure extends Exception {
  public Failure(String message, Throwable cause) {
    super(message, cause);
  }

  public Failure(String message) {
    super(message);
  }
}
