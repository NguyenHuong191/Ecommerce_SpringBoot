package com.huong.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.huong.model.User;
import com.huong.repository.UserReposity;
import com.huong.service.UserService;
import com.huong.util.AppConstant;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
	private UserReposity userReposity;
	
    @Value("${profileImg.dir}")
    private String profileUpload;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    //----them moi user
	@Override
	public boolean saveUser(User user) {
		
		user.setRole("ROLE_USER");
		user.setIsEnable(true);
		user.setAccountNonLocked(true);
		user.setFailedAttempt(0);
		
		if(user.getImg() == null) {
			user.setImg("default.jpg");
		}
		
		String encodePassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodePassword);
		
		User saveUser = userReposity.save(user);
		
		if(!ObjectUtils.isEmpty(saveUser)) {
			return true;
		}
		
		return false;
	}

	//---them moi admin------
	@Override
	public boolean saveAdmin(User user) {
		
		user.setRole("ROLE_ADMIN");
		user.setIsEnable(true);
		user.setAccountNonLocked(true);
		user.setFailedAttempt(0);
		
		if(user.getImg() == null) {
			user.setImg("default.jpg");
		}
		
		String encodePassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodePassword);
		
		User saveUser = userReposity.save(user);
		
		if(!ObjectUtils.isEmpty(saveUser)) {
			return true;
		}
		
		return false;
	}

	//--------
	@Override
	public Boolean existsEmail(String email) {
		return userReposity.existsByEmail(email);
	}
	
	
	@Override
	public User getUserByEmail(String email) {
		return userReposity.findByEmail(email);
	}

	//----lay dsach user theo role
	@Override
	public List<User> getUsers(String role) {
		return userReposity.findByRole(role);
	}

	//----update account status
	@Override
	public Boolean updateAccountStatus(Integer id, Boolean status) {
		Optional<User> findByUser =  userReposity.findById(id);
		
		if(findByUser.isPresent()) {
			User user =  findByUser.get();
			user.setIsEnable(status);
			userReposity.save(user);
			return true;
		}
		return false;
	}

	//--------tang so lan that bai khi dang nhap
	@Override
	public void increaseFailedAttempt(User user) {
		int attempt = user.getFailedAttempt()+1;
		user.setFailedAttempt(attempt);
		userReposity.save(user);
	}

	// ---------khoa tai khoan va ghi lai thoi gian khoa
	@Override
	public void accountLock(User user) {
		user.setAccountNonLocked(false);
		user.setLockTime(new Date());
		userReposity.save(user);
	}

	//-----kiem tra neu thoi gian khoa da qua tgian qdinh thi mo khoa
	@Override
	public boolean unlockAccount(User user) {
		
		long lockTime = user.getLockTime().getTime();
		long unlockTime = lockTime+AppConstant.UNLOCK_DURATION_TIME;
		long currentTime = System.currentTimeMillis();
		
		if(unlockTime < currentTime) {
			user.setAccountNonLocked(true);
			user.setFailedAttempt(0);
			user.setLockTime(null);
			userReposity.save(user);
			
			return true;
		}
		
		return false;
	}

	@Override
	public void resetAttempt(int idUser) {
		
	}

	//--update user voi img
	@Async
	public CompletableFuture<User> updateUserProfile(User user,MultipartFile img) {
		User dbUser = userReposity.findById(user.getId()).get();
		
	    if(!img.isEmpty()) {
	    	String originalFilename = img.getOriginalFilename();
	        String fileExtension = Objects.requireNonNull(originalFilename).substring(originalFilename.lastIndexOf('.') + 1);
	        
	        if (!List.of("jpg", "jpeg", "png").contains(fileExtension.toLowerCase())) {
	            throw new RuntimeException("Invalid file type. Only JPG, JPEG, PNG are allowed.");
	        }

	        dbUser.setImg(originalFilename);
	        
	        try {
	            Path path = Paths.get(profileUpload + originalFilename);
	            img.transferTo(path);  
	        } catch (IOException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Error while saving the file");
	        }
	    }
		
		if(!ObjectUtils.isEmpty(dbUser)) {
			dbUser.setName(user.getName());
			dbUser.setPhone(user.getPhone());
			dbUser.setAddress(user.getAddress());
			dbUser.setCity(user.getCity());
			dbUser.setState(user.getState());
			dbUser.setPincode(user.getPincode());
			
			dbUser = userReposity.save(dbUser);
		}
		return CompletableFuture.completedFuture(dbUser);
	}

	//---update user voi password
	@Override
	public User updateUser(User user) {
		return userReposity.save(user);
	}

}
