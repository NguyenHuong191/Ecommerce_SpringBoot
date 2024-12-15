package com.huong.Controller;

import java.security.Principal;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.huong.model.User;
import com.huong.service.UserService;

@Controller
@RequestMapping("/profile")
public class ProfileUserController {
	
	   @Autowired
	   private UserService userService;
	   
	   @Autowired
	   private PasswordEncoder passwordEncoder;

	   //----------update profile
		@PostMapping("/update-user-profile")
		public String updateUserProfile(@ModelAttribute User updateUser, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
			CompletableFuture<User> user = userService.updateUserProfile(updateUser, file);
			
			try {
		        User updatedUser = user.get();  // Đợi kết quả trả về từ @Async
		        if(updatedUser != null) {
		            redirectAttributes.addFlashAttribute("succMsg", "Profile updated!");
		        } else {
		            redirectAttributes.addFlashAttribute("errorMsg", "Profile update failed!");
		        }
		    } catch (Exception e) {
		        redirectAttributes.addFlashAttribute("errorMsg", "Profile update failed due to an error.");
		        e.printStackTrace();
		    }
			return "redirect:/user/profile";
		}
		
		@PostMapping("/update-admin-profile")
		public String updateAdminProfile(@ModelAttribute User updateAdmin, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
			CompletableFuture<User> admin = userService.updateUserProfile(updateAdmin, file);
			
			try {
		        User updatedUser = admin.get();  // Đợi kết quả trả về từ @Async
		        if(updatedUser != null) {
		            redirectAttributes.addFlashAttribute("succMsg", "Profile updated!");
		        } else {
		            redirectAttributes.addFlashAttribute("errorMsg", "Profile update failed!");
		        }
		    } catch (Exception e) {
		        redirectAttributes.addFlashAttribute("errorMsg", "Profile update failed due to an error.");
		        e.printStackTrace();
		    }
			return "redirect:/admin/profile";
		}
		
		//----------change password
		@PostMapping("/change-password")
		public String changePassword(Principal p ,@RequestParam String currentPass, @RequestParam String newPass ,RedirectAttributes redirectAttributes) {
			
			String email = p.getName();
			User user = userService.getUserByEmail(email);
					
		    boolean isMatched = passwordEncoder.matches(currentPass, user.getPassword());	
		    
		    if(isMatched) {
		    	String encodeNewPass = passwordEncoder.encode(newPass);
		    	user.setPassword(encodeNewPass);
		    	User savedUser = userService.updateUser(user);
		    	
		    	if(!ObjectUtils.isEmpty(savedUser)) {
			    	redirectAttributes.addFlashAttribute("succMs","Update password successfully!");		    		
		    	}else {
			    	redirectAttributes.addFlashAttribute("errorMs","Update passord failed!");
		    	}
		    }else {
		    	redirectAttributes.addFlashAttribute("errorMs","Current passord incorrect!");
		    }
			return "redirect:/user/profile";
		}

		@PostMapping("/change-admin-password")
		public String changeAdminPassword(Principal p ,@RequestParam String currentPass, @RequestParam String newPass,RedirectAttributes redirectAttributes) {
			
			String email = p.getName();
			User user = userService.getUserByEmail(email);
					
		    boolean isMatched = passwordEncoder.matches(currentPass, user.getPassword());	
		    
		    if(isMatched) {
		    	String encodeNewPass = passwordEncoder.encode(newPass);
		    	user.setPassword(encodeNewPass);
		    	User savedUser = userService.updateUser(user);
		    	
		    	if(!ObjectUtils.isEmpty(savedUser)) {
			    	redirectAttributes.addFlashAttribute("succMs","Update password successfully!");		    		
		    	}else {
			    	redirectAttributes.addFlashAttribute("errorMs","Update passord failed!");
		    	}
		    }else {
		    	redirectAttributes.addFlashAttribute("errorMs","Current passord incorrect!");
		    }
			return "redirect:/admin/profile";
		}

}
