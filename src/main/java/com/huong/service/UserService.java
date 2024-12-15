package com.huong.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.multipart.MultipartFile;

import com.huong.model.User;

public interface UserService {
	boolean saveUser(User user);

	User getUserByEmail(String email);
	
	List<User> getUsers(String role);

	Boolean updateAccountStatus(Integer id, Boolean status);

	void increaseFailedAttempt(User user);
	
	void accountLock(User user);
	
	boolean unlockAccount(User user);
	
	void resetAttempt(int idUser);
	
	User updateUser(User user);
		
	CompletableFuture<User> updateUserProfile(User user, MultipartFile img);

	boolean saveAdmin(User user);

    Boolean existsEmail(String email);
}
