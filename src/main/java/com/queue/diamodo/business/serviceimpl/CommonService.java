package com.queue.diamodo.business.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.queue.diamodo.business.exception.DiamodoCheckedException;
import com.queue.diamodo.common.internationalization.DiamodoResourceBundleUtils;
import com.queue.diamodo.common.utils.Utils;
import com.queue.diamodo.dataaccess.dao.DiamodoClientDAO;

@Service
public class CommonService {

  @Autowired
  private DiamodoClientDAO diamodoClientDAO;

  protected void validatePassword(String password) throws DiamodoCheckedException {

    if (Utils.isEmpty(password)) {
      throw new DiamodoCheckedException(DiamodoResourceBundleUtils.PASSWORD_IS_REQUIRED_CODE,
          DiamodoResourceBundleUtils.PASSWORD_IS_REQUIRED_KEY);
    }

    if (!Utils.isValidPassword(password)) {
      throw new DiamodoCheckedException(DiamodoResourceBundleUtils.INVALID_PASSWORD_CODE,
          DiamodoResourceBundleUtils.INVALID_PASSWORD_KEY);
    }

  }

  protected void validateLastName(String lastName) throws DiamodoCheckedException {

    if (Utils.isEmpty(lastName)) {
      throw new DiamodoCheckedException(DiamodoResourceBundleUtils.LAST_NAME_IS_REQUIRED_CODE,
          DiamodoResourceBundleUtils.LAST_NAME_IS_REQUIRED_KEY);
    }

    if (!Utils.isValidName(lastName)) {
      throw new DiamodoCheckedException(DiamodoResourceBundleUtils.INVALID_LAST_NAME_CODE,
          DiamodoResourceBundleUtils.INVALID_LAST_NAME_KEY);
    }

  }

  protected void validateFirstName(String firstName) throws DiamodoCheckedException {
    if (Utils.isEmpty(firstName)) {
      throw new DiamodoCheckedException(DiamodoResourceBundleUtils.FIRST_NAME_IS_REQUIRED_CODE,
          DiamodoResourceBundleUtils.FIRST_NAME_IS_REQUIRED_KEY);
    }

    if (!Utils.isValidName(firstName)) {
      throw new DiamodoCheckedException(DiamodoResourceBundleUtils.INVALID_FIRST_NAME_CODE,
          DiamodoResourceBundleUtils.INVALID_FIRST_NAME_KEY);
    }

  }

  protected void validateUserName(String userName) throws DiamodoCheckedException {
    if (Utils.isEmpty(userName)) {

      throw new DiamodoCheckedException(DiamodoResourceBundleUtils.USER_NAME_IS_REQUIRED_CODE,
          DiamodoResourceBundleUtils.USER_NAME_IS_REQUIRED_KEY);
    }

    if (!Utils.isValidUserName(userName)) {
      throw new DiamodoCheckedException(DiamodoResourceBundleUtils.INVALID_USER_NAME_CODE,
          DiamodoResourceBundleUtils.INVALID_USER_NAME_KEY);
    }



  }

  protected void validateEmail(String email) throws DiamodoCheckedException {
    if (Utils.isEmpty(email)) {

      throw new DiamodoCheckedException(DiamodoResourceBundleUtils.EMAIL_IS_REQUIRED_CODE,
          DiamodoResourceBundleUtils.EMAIL_IS_REQUIRED_KEY);

    }



    if (!Utils.isValidEmailAddress(email)) {
      throw new DiamodoCheckedException(DiamodoResourceBundleUtils.INVALID_EMAIL_ADDRESS_CODE,
          DiamodoResourceBundleUtils.INVALID_EMAIL_ADDRESS_KEY);
    }

  }


  protected void throwDiamodException(int errorCode, String errorMessage)
      throws DiamodoCheckedException {
    throw new DiamodoCheckedException(errorCode, errorMessage);

  }


  protected void validateFriend(String friendId) throws DiamodoCheckedException {
    if (Utils.isEmpty(friendId) || !diamodoClientDAO.exists(friendId)) {
      throwDiamodException(DiamodoResourceBundleUtils.FRIEND_ACCOUNT_IS_NO_LONGER_EXIST_CODE,
          DiamodoResourceBundleUtils.FRIEND_ACCOUNT_IS_NO_LONGER_EXIST_KEY);
    }
  }

  protected void validateSender(String clientId) throws DiamodoCheckedException {

    if (Utils.isEmpty(clientId) || !diamodoClientDAO.exists(clientId)) {
      throwDiamodException(DiamodoResourceBundleUtils.SENDER_ACCOUNT_IS_NO_LONGER_EXIST_CODE,
          DiamodoResourceBundleUtils.SENDER_ACCOUNT_IS_NO_LONGER_EXIST_KEY);
    }

  }
  
  protected void validateClientExistance(String clientId) throws DiamodoCheckedException {
    boolean isUserExist = diamodoClientDAO.exists(clientId);
    if (!isUserExist) {
      throwDiamodException(DiamodoResourceBundleUtils.USER_IS_NOT_EXIST_CODE,
          DiamodoResourceBundleUtils.USER_IS_NOT_EXIST_KEY);
    }

  }
}
