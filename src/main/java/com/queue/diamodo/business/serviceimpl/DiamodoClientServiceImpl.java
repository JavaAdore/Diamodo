package com.queue.diamodo.business.serviceimpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;

import com.queue.diamodo.business.exception.DiamodoCheckedException;
import com.queue.diamodo.business.service.DiamodoClientService;
import com.queue.diamodo.common.config.DiamodoConfigurations;
import com.queue.diamodo.common.document.DiamodoClient;
import com.queue.diamodo.common.document.ProfileImage;
import com.queue.diamodo.common.internationalization.DiamodoResourceBundleUtils;
import com.queue.diamodo.common.utils.Utils;
import com.queue.diamodo.dataaccess.dao.DiamodoClientDAO;
import com.queue.diamodo.dataaccess.dto.ClientImageHolder;
import com.queue.diamodo.dataaccess.dto.FriendRepresentationDTO;
import com.queue.diamodo.dataaccess.dto.LoginDTO;
import com.queue.diamodo.dataaccess.dto.PagingDTO;
import com.queue.diamodo.dataaccess.dto.SignUpDTO;

@Service
public class DiamodoClientServiceImpl extends CommonService implements DiamodoClientService {


  @Autowired
  private DiamodoClientDAO diamodoClientDAO;
  
  

  @Autowired
  private DiamodoConfigurations diamodoConfigurations;

  @Override
  public DiamodoClient signUp(SignUpDTO signUpDTO) throws DiamodoCheckedException {

    validateSignUpDTO(signUpDTO);

    validateUserDublication(signUpDTO.getUserName(), signUpDTO.getEmail());

    DiamodoClient diamodoClient = Utils.mapObjectToAnother(signUpDTO, DiamodoClient.class);

    diamodoClient = diamodoClientDAO.save(diamodoClient);

    return diamodoClient;
  }


  private void validateUserDublication(String userName, String email)
      throws DiamodoCheckedException {

    DiamodoClient diamodoClient = diamodoClientDAO.getBasicUserInformations(userName, email);
    if (Utils.isNotEmpty(diamodoClient)) {
      if (diamodoClient.getUserName().equalsIgnoreCase(userName.trim())) {
        throw new DiamodoCheckedException(DiamodoResourceBundleUtils.USER_NAME_ALREADY_EXIST_CODE,
            DiamodoResourceBundleUtils.USER_NAME_ALREADY_EXIST_KEY);
      }


      if (diamodoClient.getEmail().equalsIgnoreCase(email.trim())) {
        throw new DiamodoCheckedException(DiamodoResourceBundleUtils.EMAIL_ALREADY_EXIST_CODE,
            DiamodoResourceBundleUtils.EMAIL_ALREADY_EXIST_KEY);
      }

    }

  }

  private void validateSignUpDTO(SignUpDTO signUpDTO) throws DiamodoCheckedException {


    validateEmail(signUpDTO.getEmail());

    validateUserName(signUpDTO.getUserName());

    validateFirstName(signUpDTO.getFirstName());

    validateLastName(signUpDTO.getLastName());

    validatePassword(signUpDTO.getPassword());



  }



  @Override
  public DiamodoClient login(LoginDTO loginDTO) throws DiamodoCheckedException {

    validateLoginDTO(loginDTO);

    String authenticationAttribute = loginDTO.getLoginAttribute();

    DiamodoClient diamodoClient = null;

    if (Utils.isEmail(authenticationAttribute)) {

      diamodoClient = validateLoginByEmail(authenticationAttribute);

    } else {

      diamodoClient = validateLoginByUserName(authenticationAttribute);

    }

    matchPasswords(loginDTO.getPassword(), diamodoClient.getPassword());

    validateAccountStatus(diamodoClient);

    return diamodoClient;
  }

  private DiamodoClient validateLoginByUserName(String authenticationAttribute)
      throws DiamodoCheckedException {
    validateUserName(authenticationAttribute);

    DiamodoClient diamodoClient =
        diamodoClientDAO.getDiamodoClientByUserName(authenticationAttribute);
    if (Utils.isEmpty(diamodoClient)) {
      throwDiamodException(DiamodoResourceBundleUtils.USER_NAME_DOESNT_EXIST_CODE,
          DiamodoResourceBundleUtils.USER_NAME_DOESNT_EXIST_KEY);
    }
    return diamodoClient;
  }

  private DiamodoClient validateLoginByEmail(String authenticationAttribute)
      throws DiamodoCheckedException {

    validateEmail(authenticationAttribute);

    DiamodoClient diamodoClient = diamodoClientDAO.getDiamodoClientByEmail(authenticationAttribute);
    if (Utils.isEmpty(diamodoClient)) {
      throwDiamodException(DiamodoResourceBundleUtils.EMAIL_DOESNT_EXIST_CODE,
          DiamodoResourceBundleUtils.EMAIL_DOESNT_EXIST_KEY);
    }
    return diamodoClient;

  }

  private void validateAccountStatus(DiamodoClient diamodoClient) throws DiamodoCheckedException {
    // TODO check if account is suspended or locked or deleted or whatever

  }

  private void matchPasswords(String password, String password2) throws DiamodoCheckedException {
    if (!password.equals(password2)) {
      throwDiamodException(DiamodoResourceBundleUtils.BAD_CREDENTIALS_CODE,
          DiamodoResourceBundleUtils.BAD_CREDENTIALS_KEY);
    }

  }



  private void validateLoginDTO(LoginDTO loginDTO) throws DiamodoCheckedException {
    if (Utils.isEmpty(loginDTO.getLoginAttribute())) {
      throw new DiamodoCheckedException(
          DiamodoResourceBundleUtils.USERNAME_OR_EMAIL_IS_REQUIRED_CODE,
          DiamodoResourceBundleUtils.USERNAME_OR_EMAIL_IS_REQUIRED_KEY);
    }

    if (Utils.isEmpty(loginDTO.getPassword())) {
      throw new DiamodoCheckedException(DiamodoResourceBundleUtils.PASSWORD_IS_REQUIRED_CODE,
          DiamodoResourceBundleUtils.PASSWORD_IS_REQUIRED_KEY);
    }



  }


  @Override
  public void updateClientProfileImagePath(String clientId, String fullFileName)
      throws DiamodoCheckedException {

    validateClientExistance(clientId);

    ProfileImage currentProfileImage = getClientProfileImage(clientId);

    diamodoClientDAO.addProfileImageToUserHistory(clientId, currentProfileImage);

    diamodoClientDAO.setCurrentProfileImage(clientId, new ProfileImage(fullFileName));



  }


  private ProfileImage getClientProfileImage(String clientId) {

    return diamodoClientDAO.getClientProfileImage(clientId);
  }


 


  @Override
  public String updateClientProfileImagePath(String clientId, ClientImageHolder clientImageHolder)
      throws DiamodoCheckedException {

    validateClientImageHolder(clientImageHolder);

    validateClientExistance(clientId);

    String fullFileName = saveImageToFile(clientId, clientImageHolder.getBase64Image());

    ProfileImage currentProfileImage = getClientProfileImage(clientId);

    diamodoClientDAO.addProfileImageToUserHistory(clientId, currentProfileImage);

    diamodoClientDAO.setCurrentProfileImage(clientId, new ProfileImage(fullFileName));

    return fullFileName;



  }

  private String saveImageToFile(String clientId, String base64Image)
      throws DiamodoCheckedException {
    String fullFileName = clientId + "_" + new Random().nextLong();;
    fullFileName = Utils.fixFileName(fullFileName);

    try (OutputStream fos =
        new FileOutputStream(diamodoConfigurations.DEFAULT_UPLOAD_PROFILE_PICTURE_FOLDER_LOCATION
            + File.separator + fullFileName)) {


      byte[] image = Base64.decode(base64Image.getBytes());
      fos.write(image);
      fos.flush();

    } catch (Exception ex) {
      ex.printStackTrace();
      throwDiamodException(DiamodoResourceBundleUtils.INVALID_FILE_FORMAT_CODE,
          DiamodoResourceBundleUtils.INVALID_FILE_FORMAT_KEY);

    }
    return fullFileName;
  }


  private void validateClientImageHolder(ClientImageHolder clientImageHolder)
      throws DiamodoCheckedException {

    if (Utils.isEmpty(clientImageHolder) || Utils.isEmpty(clientImageHolder.getBase64Image())) {
      throwDiamodException(DiamodoResourceBundleUtils.PROFILE_IMAGE_IS_REQUIRED_CODE,
          DiamodoResourceBundleUtils.PROFILE_IMAGE_IS_REQUIRED_KEY);
    }
    if (!Base64.isBase64(clientImageHolder.getBase64Image().getBytes())) {
      throwDiamodException(DiamodoResourceBundleUtils.INVALID_FILE_FORMAT_CODE,
          DiamodoResourceBundleUtils.INVALID_FILE_FORMAT_KEY);
    }
  }




}
