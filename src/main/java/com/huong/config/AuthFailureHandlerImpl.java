package com.huong.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.huong.model.User;
import com.huong.repository.UserReposity;
import com.huong.service.UserService;
import com.huong.util.AppConstant;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler{

	@Autowired
	private UserReposity userReposity;
	
	@Autowired
	private UserService userService;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		String email = request.getParameter("username");
		
		User user = userReposity.findByEmail(email);
		
		
		if(user != null) {
		//neu tai khoan dang duoc kich hoat
		if(user.getIsEnable()) {
			
			//neu tai khoan chua bi khoa
		   	if(user.getAccountNonLocked()) {
		   		//neu so lan dang nhap that bai nho hon 5, tang so lan that bat
		   		if(user.getFailedAttempt()< AppConstant.ATTEMPT_TIME) {
		   			userService.increaseFailedAttempt(user);
		   		}else {
		   			//neu vuot qua  thi khoa tkhoan
		   			userService.accountLock(user);
		   			exception = new LockedException("Your account is locked! Failed attempt 5");
		   		}
		   	}else {
		   		//ktra co the mo tai khoan hay khong dua tren tgian cho
		   		if(userService.unlockAccount(user)) {
		   			exception = new LockedException("Your account was locked but has been unlocked. Please login again!");	
		   		}else {
					exception = new LockedException("Your account is locked! Please try again after 3s!");
		   		}
		   	}
		}else {
			exception = new LockedException("Your account is inactive!");
		}
		}else {
			exception = new LockedException("Email && password is invalid!");			
		}
		super.setDefaultFailureUrl("/signin?error");
		super.onAuthenticationFailure(request, response, exception);
	}

	

}
