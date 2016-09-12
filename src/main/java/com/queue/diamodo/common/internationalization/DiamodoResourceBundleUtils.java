package com.queue.diamodo.common.internationalization;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;


public class DiamodoResourceBundleUtils {

  private static Logger logger = Logger.getLogger(DiamodoResourceBundleUtils.class);


  public static final String FRIEND_REQUEST_SENT_SUCCESSFULLY_MESSAGE =
      "FRIEND_REQUEST_SENT_SUCCESSFULLY_MESSAGE";

  public static final String NO_RESULT_FOUND_MESSAGE = "NO_RESULT_FOUND_MESSAGE";

  public static final String ACCEPT_FRIEND_REQUEST_MESSAGE = "ACCEPT_FRIEND_REQUEST_MESSAGE";

  public static final String REJECT_FRIEND_REQUEST_SUCCESS_MESSAGE =
      "REJECT_FRIEND_REQUEST_SUCCESS_MESSAGE";

  public static final String DEFAULT_SUCCESS_MESSAGE = "DEFAULT_SUCCESS_MESSAGE";

  public static final int BACK_END_ERROR_CODE = -99999;
  public static final String BACK_END_ERROR_KEY = "BACK_END_ERROR_KEY";


  public final static int EMAIL_IS_REQUIRED_CODE = -1;
  public final static String EMAIL_IS_REQUIRED_KEY = "EMAIL_IS_REQUIRED_KEY";

  public final static int USER_NAME_IS_REQUIRED_CODE = -2;
  public final static String USER_NAME_IS_REQUIRED_KEY = "USER_NAME_IS_REQUIRED_KEY";

  public final static int FIRST_NAME_IS_REQUIRED_CODE = -3;
  public final static String FIRST_NAME_IS_REQUIRED_KEY = "FIRST_NAME_IS_REQUIRED_KEY";

  public final static int LAST_NAME_IS_REQUIRED_CODE = -4;
  public final static String LAST_NAME_IS_REQUIRED_KEY = "LAST_NAME_IS_REQUIRED_KEY";

  public final static int PASSWORD_IS_REQUIRED_CODE = -5;
  public final static String PASSWORD_IS_REQUIRED_KEY = "PASSWORD_IS_REQUIRED_KEY";

  public final static int USER_NAME_ALREADY_EXIST_CODE = -6;
  public final static String USER_NAME_ALREADY_EXIST_KEY = "USER_NAME_ALREADY_EXIST_KEY";

  public final static int EMAIL_ALREADY_EXIST_CODE = -7;
  public final static String EMAIL_ALREADY_EXIST_KEY = "EMAIL_ALREADY_EXIST_KEY";

  public final static int INVALID_EMAIL_ADDRESS_CODE = -8;
  public final static String INVALID_EMAIL_ADDRESS_KEY = "INVALID_EMAIL_ADDRESS_KEY";

  public final static int INVALID_USER_NAME_CODE = -9;
  public final static String INVALID_USER_NAME_KEY = "INVALID_USER_NAME_KEY";

  public final static int INVALID_FIRST_NAME_CODE = -10;
  public final static String INVALID_FIRST_NAME_KEY = "INVALID_FIRST_NAME_KEY";

  public static final int INVALID_LAST_NAME_CODE = -11;
  public static final String INVALID_LAST_NAME_KEY = "INVALID_LAST_NAME_KEY";

  public static final int INVALID_PASSWORD_CODE = -12;
  public static final String INVALID_PASSWORD_KEY = "INVALID_PASSWORD_KEY";

  public static final int USERNAME_OR_EMAIL_IS_REQUIRED_CODE = -13;
  public static final String USERNAME_OR_EMAIL_IS_REQUIRED_KEY =
      "USERNAME_OR_EMAIL_IS_REQUIRED_KEY";

  public static final int EMAIL_DOESNT_EXIST_CODE = -14;
  public static final String EMAIL_DOESNT_EXIST_KEY = "EMAIL_DOESNT_EXIST_KEY";

  public static final int BAD_CREDENTIALS_CODE = -15;
  public static final String BAD_CREDENTIALS_KEY = "BAD_CREDENTIALS_KEY";

  public static final int USER_NAME_DOESNT_EXIST_CODE = -16;
  public static final String USER_NAME_DOESNT_EXIST_KEY = "USER_NAME_DOESNT_EXIST_KEY";

  public static final int PROFILE_IMAGE_IS_REQUIRED_CODE = -17;
  public static final String PROFILE_IMAGE_IS_REQUIRED_KEY = "PROFILE_IMAGE_IS_REQUIRED_KEY";

  public static final int USER_IS_NOT_EXIST_CODE = -18;
  public static final String USER_IS_NOT_EXIST_KEY = "USER_IS_NOT_EXIST_KEY";

  public static final int FRIEND_ACCOUNT_IS_NO_LONGER_EXIST_CODE = -19;
  public static final String FRIEND_ACCOUNT_IS_NO_LONGER_EXIST_KEY =
      "FRIEND_ACCOUNT_IS_NO_LONGER_EXIST_KEY";

  public static final int SENDER_ACCOUNT_IS_NO_LONGER_EXIST_CODE = -20;
  public static final String SENDER_ACCOUNT_IS_NO_LONGER_EXIST_KEY =
      "SENDER_ACCOUNT_IS_NO_LONGER_EXIST_KEY";

  public static final int FRIEND_SHIP_REQUEST_ALREADY_SENT_CODE = -21;
  public static final String FRIEND_SHIP_REQUEST_ALREADY_SENT_KEY =
      "FRIEND_SHIP_REQUEST_ALREADY_SENT_KEY";

  public static final int FRIEND_HAS_SENT_YOU_FRIENDSHIP_REQUEST_YOU_CAN_ACCEPT_IT_CODE = -22;
  public static final String FRIEND_HAS_SENT_YOU_FRIENDSHIP_REQUEST_YOU_CAN_ACCEPT_IT_KEY =
      "FRIEND_HAS_SENT_YOU_FRIENDSHIP_REQUEST_YOU_CAN_ACCEPT_IT_KEY";

  public static final int SORRY_YOU_CANNOT_SENT_FRIEND_SHIP_REQUEST_TO_THIS_ACCOUNT_CODE = -23;
  public static final String SORRY_YOU_CANNOT_SENT_FRIEND_SHIP_REQUEST_TO_THIS_ACCOUNT_KEY =
      "  private static final String SORRY_YOU_CANNOT_SENT_FRIEND_SHIP_REQUEST_TO_THIS_ACCOUNT_KEY";

  public static final int YOU_CANNOT_SENT_REQUEST_TO_YOUR_SELF_CODE = -24;
  public static final String YOU_CANNOT_SENT_REQUEST_TO_YOUR_SELF_KEY =
      "YOU_CANNOT_SENT_REQUEST_TO_YOUR_SELF_KEY";

  public static final int FRIEND_SHIP_ID_IS_REQUIRED_CODE = -25;
  public static final String FRIEND_SHIP_ID_IS_REQUIRED_KEY = "FRIEND_SHIP_ID_IS_REQUIRED_KEY";


  public static final int SORRY_YOU_CANNOT_ACCEPT_FRIEND_SHIP_REQUEST_TO_THIS_ACCOUNT_CODE = -26;
  public static final String SORRY_YOU_CANNOT_ACCEPT_FRIEND_SHIP_REQUEST_TO_THIS_ACCOUNT_KEY =
      "SORRY_YOU_CANNOT_ACCEPT_FRIEND_SHIP_REQUEST_TO_THIS_ACCOUNT_KEY";

  public static final int FRIEND_SHIP_REQUEST_IS_NO_LONGER_EXIST_CODE = -27;
  public static final String FRIEND_SHIP_REQUEST_IS_NO_LONGER_EXIST_KEY =
      "FRIEND_SHIP_IS_NO_LONGER_EXIST_KEY";


  public static final int YOU_ARE_ALREADY_FRIENDS_CODE = -28;
  public static final String YOU_ARE_ALREADY_FRIENDS_KEY = "YOU_ARE_ALREADY_FRIENDS_KEY";

  public static final int INVALID_FILE_FORMAT_CODE = -29;
  public static final String INVALID_FILE_FORMAT_KEY = "INVALID_FILE_FORMAT_KEY";


  public static final int INVALID_ACTION_CODE = -30;
  public static final String INVALID_ACTION_KEY = "INVALID_ACTION_KEY";

  public static final int CLIENT_ID_IS_REQUIRED_CODE = -31;
  public static final String CLIENT_ID_IS_REQUIRED_KEY = "CLIENT_ID_IS_REQUIRED_KEY";

  public static final int UPDATE_PROFILE_DTO_SHOULD_NOT_BE_EMPTY_CODE = -32;
  public static final String UPDATE_PROFILE_DTO_SHOULD_NOT_BE_EMPTY_KEY =
      "UPDATE_PROFILE_DTO_SHOULD_NOT_BE_EMPTY_KEY";

  public static final int UPDATE_PROFILE_MISSING_CLIENT_ID_CODE = -33;
  public static final String UPDATE_PROFILE_MISSING_CLIENT_ID_KEY =
      "UPDATE_PROFILE_MISSING_CLIENT_ID_KEY";

  public static final int YOU_ARE_TRYING_TO_UPDATE_MEMBER_PROFILE_CODE = -34;
  public static final String YOU_ARE_TRYING_TO_UPDATE_MEMBER_PROFILE_KEY =
      "YOU_ARE_TRYING_TO_UPDATE_MEMBER_PROFILE_KEY";

  public static final int NOT_ACCEPTED_DEVICE_TYPE_CODE = -35;
  public static final String NOT_ACCEPTED_DEVICE_TYPE_KEY = "NOT_ACCEPTED_DEVICE_TYPE_KEY";

  public static final int DEVICE_TOKEN_IS_REQUIRED_CODE = -36;
  public static final String DEVICE_TOKEN_IS_REQUIRED_KEY = "DEVICE_TOKEN_IS_REQUIRED_KEY";


  public static String getValue(String key, Locale locale) {
    ResourceBundle resourceBundle =
        ResourceBundle.getBundle(
            "com.queue.diamodo.common.internationalization.diamodoResouceBundle", locale);

    logger.info(String.format("resource bundle of locale %s %s found ", locale,
        (locale == null) ? " not " : ""));
    if (resourceBundle == null) {
      resourceBundle =
          ResourceBundle
              .getBundle("com.queue.diamodo.common.internationalization.diamodoResouceBundle");
    }
    try {

      String value = resourceBundle.getString(key);
      return value;

    } catch (MissingResourceException ex) {
      logger.info("not able to find value for key " + key + " within locale " + locale);
      return key;

    }
  }



}
