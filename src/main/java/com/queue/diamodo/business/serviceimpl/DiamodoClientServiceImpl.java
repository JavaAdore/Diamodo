package com.queue.diamodo.business.serviceimpl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.*;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;

import com.queue.diamodo.business.exception.DiamodoCheckedException;
import com.queue.diamodo.business.service.DiamodoClientService;
import com.queue.diamodo.common.config.DiamodoConfigurations;
import com.queue.diamodo.common.document.DiamodoClient;
import com.queue.diamodo.common.document.ProfileImage;
import com.queue.diamodo.common.document.ClientDevice;
import com.queue.diamodo.common.internationalization.DiamodoResourceBundleUtils;
import com.queue.diamodo.common.utils.Utils;
import com.queue.diamodo.dataaccess.dao.DiamodoClientDAO;
import com.queue.diamodo.dataaccess.dto.ClientImageHolder;
import com.queue.diamodo.dataaccess.dto.ClientInfo;
import com.queue.diamodo.dataaccess.dto.FriendRepresentationDTO;
import com.queue.diamodo.dataaccess.dto.LoginDTO;
import com.queue.diamodo.dataaccess.dto.PagingDTO;
import com.queue.diamodo.dataaccess.dto.SignUpDTO;
import com.queue.diamodo.dataaccess.dto.UpdateProfileDTO;
import com.queue.diamodo.dataaccess.dto.UserInfoDTO;
import com.queue.diamodo.dataaccess.dto.UserWithDeviceInfo;

@Service
public class DiamodoClientServiceImpl extends CommonService implements DiamodoClientService {

	Logger logger = LogManager.getLogger(DiamodoClientServiceImpl.class);

	@Autowired
	private DiamodoClientDAO diamodoClientDAO;

	@Autowired
	private DiamodoConfigurations diamodoConfigurations;

	@Override
	public UserInfoDTO signUp(SignUpDTO signUpDTO) throws DiamodoCheckedException {

		validateSignUpDTO(signUpDTO);

		validateUserDublication(signUpDTO.getUserName(), signUpDTO.getEmail());

		signUpDTO.assignRandomToken();

		DiamodoClient diamodoClient = Utils.mapObjectToAnother(signUpDTO, DiamodoClient.class);

		diamodoClient = diamodoClientDAO.save(diamodoClient);

		UserInfoDTO userInfoDTO = Utils.mapObjectToAnother(diamodoClient, UserInfoDTO.class);

		return userInfoDTO;
	}

	private void validateUserDublication(String userName, String email) throws DiamodoCheckedException {

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
	public UserInfoDTO login(LoginDTO loginDTO) throws DiamodoCheckedException {

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

		diamodoClient.assignRandomToken();

		updateClientUserToken(diamodoClient.getId(), diamodoClient.getUserToken());

		UserInfoDTO userInfoDTO = Utils.mapObjectToAnother(diamodoClient, UserInfoDTO.class);
		return userInfoDTO;
	}

	private void updateClientUserToken(String id, String userToken) {

		diamodoClientDAO.updateUserToken(id, userToken);

	}

	private DiamodoClient validateLoginByUserName(String authenticationAttribute) throws DiamodoCheckedException {
		validateUserName(authenticationAttribute);

		DiamodoClient diamodoClient = diamodoClientDAO.getDiamodoClientByUserName(authenticationAttribute);
		if (Utils.isEmpty(diamodoClient)) {
			throwDiamodException(DiamodoResourceBundleUtils.USER_NAME_DOESNT_EXIST_CODE,
					DiamodoResourceBundleUtils.USER_NAME_DOESNT_EXIST_KEY);
		}
		return diamodoClient;
	}

	private DiamodoClient validateLoginByEmail(String authenticationAttribute) throws DiamodoCheckedException {

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
			throw new DiamodoCheckedException(DiamodoResourceBundleUtils.USERNAME_OR_EMAIL_IS_REQUIRED_CODE,
					DiamodoResourceBundleUtils.USERNAME_OR_EMAIL_IS_REQUIRED_KEY);
		}

		if (Utils.isEmpty(loginDTO.getPassword())) {
			throw new DiamodoCheckedException(DiamodoResourceBundleUtils.PASSWORD_IS_REQUIRED_CODE,
					DiamodoResourceBundleUtils.PASSWORD_IS_REQUIRED_KEY);
		}

	}

	@Override
	public void updateClientProfileImagePath(String clientId, String fullFileName) throws DiamodoCheckedException {

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

		updateClientProfileImagePath(clientId, fullFileName);

		return fullFileName;

	}

	private String saveImageToFile(String clientId, String base64Image) throws DiamodoCheckedException {
		String fullFileName = clientId + "_" + new Random().nextLong();
		;
		fullFileName = Utils.fixFileName(fullFileName);

		try (InputStream is = new ByteArrayInputStream(Base64.decode(base64Image.getBytes()));
				OutputStream os = new FileOutputStream(
						diamodoConfigurations.DEFAULT_UPLOAD_PROFILE_PICTURE_FOLDER_LOCATION + File.separator
								+ fullFileName)

		) {

			// OutputStream fos =
			// new FileOutputStream()
			// byte[] image = Base64.decode(base64Image.getBytes());
			// fos.write(image);

			BufferedImage bufferedImage = ImageIO.read(is);

			ImageIO.write(bufferedImage, "png", os);

		} catch (Exception ex) {
			ex.printStackTrace();
			throwDiamodException(DiamodoResourceBundleUtils.INVALID_FILE_FORMAT_CODE,
					DiamodoResourceBundleUtils.INVALID_FILE_FORMAT_KEY);

		}
		return fullFileName;
	}

	private void validateClientImageHolder(ClientImageHolder clientImageHolder) throws DiamodoCheckedException {

		if (Utils.isEmpty(clientImageHolder) || Utils.isEmpty(clientImageHolder.getBase64Image())) {
			throwDiamodException(DiamodoResourceBundleUtils.PROFILE_IMAGE_IS_REQUIRED_CODE,
					DiamodoResourceBundleUtils.PROFILE_IMAGE_IS_REQUIRED_KEY);
		}
		if (!Base64.isBase64(clientImageHolder.getBase64Image().getBytes())) {
			throwDiamodException(DiamodoResourceBundleUtils.INVALID_FILE_FORMAT_CODE,
					DiamodoResourceBundleUtils.INVALID_FILE_FORMAT_KEY);
		}
	}

	@Override
	public ClientInfo updateProfilePicture(String clientId, UpdateProfileDTO updateProfile)
			throws DiamodoCheckedException {

		validateUpdateProfileDTO(clientId, updateProfile);

		validateClientExistance(clientId);

		Map<String, Object> fieldsToUpdate = new HashMap<String, Object>();
		if (Utils.isNotEmpty(updateProfile.getFirstName())) {
			validateFirstName(updateProfile.getFirstName());
			fieldsToUpdate.put("firstName", updateProfile.getFirstName());
		}

		if (Utils.isNotEmpty(updateProfile.getLastName())) {
			validateLastName(updateProfile.getLastName());
			fieldsToUpdate.put("lastName", updateProfile.getLastName());
		}

		if (Utils.isNotEmpty(updateProfile.getPassword())) {
			validatePassword(updateProfile.getPassword());
			fieldsToUpdate.put("password", updateProfile.getPassword());
		}

		if (Utils.isNotEmpty(updateProfile.getImageHolder()) && updateProfile.getImageHolder().hasContent()) {
			String fullImagePath = saveImageToFile(clientId, updateProfile.getImageHolder().getBase64Image());

			updateClientProfileImagePath(clientId, fullImagePath);

		}

		diamodoClientDAO.updateClientInfo(clientId, fieldsToUpdate);

		return diamodoClientDAO.getClientInfo(clientId);
	}

	private void validateUpdateProfileDTO(String clientId, UpdateProfileDTO updateProfile)
			throws DiamodoCheckedException {

		if (Utils.isEmpty(clientId)) {
			throwDiamodException(DiamodoResourceBundleUtils.CLIENT_ID_IS_REQUIRED_CODE,
					DiamodoResourceBundleUtils.CLIENT_ID_IS_REQUIRED_KEY);
		}
		if (Utils.isEmpty(updateProfile)) {
			throwDiamodException(DiamodoResourceBundleUtils.UPDATE_PROFILE_DTO_SHOULD_NOT_BE_EMPTY_CODE,
					DiamodoResourceBundleUtils.UPDATE_PROFILE_DTO_SHOULD_NOT_BE_EMPTY_KEY);
		}
		if (Utils.isEmpty(updateProfile.getId())) {
			throwDiamodException(DiamodoResourceBundleUtils.UPDATE_PROFILE_MISSING_CLIENT_ID_CODE,
					DiamodoResourceBundleUtils.UPDATE_PROFILE_MISSING_CLIENT_ID_KEY);
		}

		if (!clientId.equals(updateProfile.getId())) {
			throwDiamodException(DiamodoResourceBundleUtils.YOU_ARE_TRYING_TO_UPDATE_MEMBER_PROFILE_CODE,
					DiamodoResourceBundleUtils.YOU_ARE_TRYING_TO_UPDATE_MEMBER_PROFILE_KEY);

		}

	}

	@Override
	public void updateDeviceToken(String clientId, String deviceType, String deviceToken)
			throws DiamodoCheckedException {

		validateClientExistance(clientId);
		validateDeviceType(deviceType);
		valdiateDeviceToken(deviceToken);
		updateClientDevice(clientId, deviceType, deviceToken);

	}

	private void updateClientDevice(String clientId, String deviceType, String deviceToken) {

		ClientDevice clientDevice = new ClientDevice();
		clientDevice.setDeviceToken(deviceToken);
		clientDevice.setDeviceType(deviceType);
		diamodoClientDAO.updateClientDevice(clientId, clientDevice);

	}

	private void valdiateDeviceToken(String deviceToken) throws DiamodoCheckedException {

		if (Utils.isEmpty(deviceToken)) {
			throwDiamodException(DiamodoResourceBundleUtils.DEVICE_TOKEN_IS_REQUIRED_CODE,
					DiamodoResourceBundleUtils.DEVICE_TOKEN_IS_REQUIRED_KEY);
		}
	}

	private void validateDeviceType(String deviceType) throws DiamodoCheckedException {

		if (!ClientDevice.isAcceptedDevice(deviceType)) {
			throwDiamodException(DiamodoResourceBundleUtils.NOT_ACCEPTED_DEVICE_TYPE_CODE,
					DiamodoResourceBundleUtils.NOT_ACCEPTED_DEVICE_TYPE_KEY);
		}

	}

	@Override
	public boolean isValidClientToken(String clientId, String userToken) {

		DiamodoClient diamodoClient = diamodoClientDAO.getUserByIdAndUserToken(clientId, userToken);

		boolean authenticationResult = diamodoClient != null;

		return authenticationResult;

	}

	@Override
	public UserWithDeviceInfo getClientWithDeviceInfo(String recieverId) {

		UserWithDeviceInfo userWithDeviceInfo = diamodoClientDAO.getClientWithDeviceInfo(recieverId);

		return userWithDeviceInfo;

	}

	@Override
	public ClientInfo getClientById(String clientId) {
		ClientInfo clientInfo = diamodoClientDAO.getClientInfo(clientId);
		return clientInfo;

	}

}
