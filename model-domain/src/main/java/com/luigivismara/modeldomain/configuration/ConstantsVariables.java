package com.luigivismara.modeldomain.configuration;

public class ConstantsVariables {

    //https://developers.thoughtspot.com/docs/v1v2-comparison
    public static final String API_V1 = "/api/v1";
    public static final String API_V2 = "/api/v2";

    public static final String APPLICATION_NAME = "real-time-notification-service";
    public static final String APPLICATION_VERSION = "1.0";

    public static final String SERVER_CONNECTION_TIMEOUT = "5000";

    public static final String PATTERN_SECURE_PASSWORD = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    public static final String PATTERN_SECURE_PASSWORD_MESSAGE = "The password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character (@, $, !, %, *, ?, &).";
    public static final String INVALID_STRING_NOT_BLANK = " is required and cannot be blank.";
    public static final String INVALID_EMAIL = "Please provide a valid email address.";
}
