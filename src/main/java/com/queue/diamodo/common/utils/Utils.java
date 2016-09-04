package com.queue.diamodo.common.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.codec.Base64;

import sun.misc.BASE64Decoder;

import com.google.gson.Gson;
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


  public static boolean isNotEmpty(String str) {
    return !isEmpty(str);
  }

  public static boolean isValidEmailAddress(String email) {
    return email.matches(EMAIL_ADDRESS_PATTERN);
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
    return fullFileName.replace(SPACE, UNDERSCORE) + ".png";
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
      byte[] imageByte;
      BASE64Decoder decoder = new BASE64Decoder();
      imageByte = decoder.decodeBuffer(imageString);
      return imageByte;
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new InvalidFileFormatException();
    }
  }

  public static boolean areMatched(String userToken, String registeredUserToken) {
    return userToken.equals(registeredUserToken);
  }

  public static String prepareArgsString(Object[] args) {
    StringBuilder stringBuilder = new StringBuilder();
    if (args != null) {

      for (Object obj : args) {
        stringBuilder.append(obj.toString());
        stringBuilder.append(" ,");


      }
    }
    int indexOfLastComma = stringBuilder.indexOf(",");
    if (indexOfLastComma != -1) {
      return stringBuilder.substring(0, indexOfLastComma);
    }
    return stringBuilder.toString();
  }



  public static Date getTimeInUTC() {
    TimeZone timeZone = TimeZone.getTimeZone("UTC");
    Calendar calendar = Calendar.getInstance(timeZone);
    SimpleDateFormat simpleDateFormat =
        new SimpleDateFormat("EE MMM dd HH:mm:ss zzz yyyy", Locale.US);
    simpleDateFormat.setTimeZone(timeZone);
    return calendar.getTime();
  }


  public static <T> T JsonToObject(Object obj, Class<T> cls) {
    if (obj != null && cls != null) {
      Gson gson = new Gson();
      String objectAsJson = gson.toJson(obj);
      return gson.fromJson(objectAsJson, cls);
    }
    return null;
  }


  public static void saveImageToServer(String base64MessageContent, String imageName) {

    try (InputStream is = new ByteArrayInputStream(Base64.decode(base64MessageContent.getBytes()));
        OutputStream os = new FileOutputStream(imageName)

    ) {


      BufferedImage bufferedImage = ImageIO.read(is);

      ImageIO.write(bufferedImage, "png", os);

    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }


  }

}
