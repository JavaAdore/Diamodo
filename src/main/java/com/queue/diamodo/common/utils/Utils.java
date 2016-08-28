package com.queue.diamodo.common.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;

import sun.misc.BASE64Decoder;

import com.queue.diamodo.common.exception.InvalidFileFormatException;

public class Utils {

  private static ModelMapper modelMapper = new ModelMapper();

  private static final String EMAIL_ADDRESS_PATTERN =
      "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

  private static final String NAME_PATTERN = "[a-zA-Z\u0600-\\u06ff']+";

  private static final String USER_NAME_PATTERN = "[a-zA-Z'0-9]+";


  private static final int MIN_ALLOWED_PASSWORD_LENGTH = 6;

  private static final int MAX_ALLOWED_PASSWORD_LENGTH = 25;

  private static final CharSequence SPACE = " ";

  private static final CharSequence UNDERSCORE = "_";

  public static <T> T mapObjectToAnother(Object source, Class<T> clz) {
    return modelMapper.map(source, clz);
  }

  public static boolean isEmpty(String str) {
    return str == null || str.trim().length() == 0;
  }

  public static boolean isValidEmailAddress(String email) {
    return email.matches(EMAIL_ADDRESS_PATTERN);
  }



  public static void main(String[] args) {

    boolean result = "محمود ".matches(USER_NAME_PATTERN);

    System.out.println(result);
  }

  public static boolean isValidUserName(String userName) {

    return userName.matches(USER_NAME_PATTERN);
  }

  public static boolean isValidName(String firstName) {
    return firstName.matches(NAME_PATTERN);
  }

  public static boolean isValidPassword(String password) {
    return password.length() >= MIN_ALLOWED_PASSWORD_LENGTH
        && password.length() <= MAX_ALLOWED_PASSWORD_LENGTH;
  }

  public static boolean isNotEmpty(Object obj) {
    return !isEmpty(obj);
  }

  public static boolean isEmpty(Object obj) {
    return obj == null;
  }

  public static String generateRandomToken() {
    return UUID.randomUUID().toString();
  }

  public static boolean isEmail(String authenticationAttribute) {
    return authenticationAttribute.contains("@");
  }

  public static String fixFileName(String fullFileName) {
    return fullFileName.replace(SPACE, UNDERSCORE);
  }

  public static boolean isEmpty(List list) {
    return list != null && list.isEmpty();
  }

  public static boolean isNotEmpty(List list) {
    return list != null && !list.isEmpty();
  }

  public static <T> T getFirstResult(List<T> list) {
    if (isNotEmpty(list)) {
      return list.get(0);
    }
    return null;
  }


  public static byte[] decodeToImage(String imageString) throws InvalidFileFormatException {
    try {
      BufferedImage image = null;
      byte[] imageByte;
      BASE64Decoder decoder = new BASE64Decoder();

      imageByte = decoder.decodeBuffer(imageString);

      ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);

      return imageByte;
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new InvalidFileFormatException();
    }
  }

}
