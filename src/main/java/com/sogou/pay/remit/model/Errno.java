package com.sogou.pay.remit.model;

public class Errno {
  public static final int OK                  = 0;
  public static final int BAD_REQUEST         = 400;
  public static final int UNAUTHORIZED        = 401;
  public static final int FORBIDDEN           = 403;
  public static final int NOT_FOUND           = 404;
  public static final int NOT_ACCEPT          = 406;
  public static final int INTERNAL_ERROR      = 500;
  public static final int NOT_IMPLEMENT       = 501;
  public static final int SERVICE_UNAVAILABLE = 503;

  public static String getMessage(int code) {
    switch (code) {
    case OK: return "OK";
    case BAD_REQUEST: return "bad request";
    case UNAUTHORIZED: return "unauthorized";
    case NOT_FOUND: return "entity not found";
    case NOT_ACCEPT: return "not acceptable";
    case INTERNAL_ERROR: return "internal server error";
    case SERVICE_UNAVAILABLE: return "service unavailable";
    default: return "";
    }
  }

  public static class BadRequestException extends RuntimeException {
  }

  public static class InternalErrorException extends RuntimeException {
    public InternalErrorException(Throwable throwable) {
      super(throwable);
    }
  }
}
