package com.huong.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.huong.model.User;
import com.huong.service.UserService;

@Controller
@RequestMapping("/register")
public class RegisterController {
	@Autowired
    private UserService userService;
	
	@PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) throws InterruptedException, IOException {
		
		if(!userService.existsEmail(user.getEmail())) {
    	   boolean isSaved = userService.saveUser(user);
		
    	   if(isSaved) {
    		    redirectAttributes.addFlashAttribute("succMsg","Register success!");
    	   }else {
    		    redirectAttributes.addFlashAttribute("errorMsg","Register failed!");    		
    	    }
		}else {
		    redirectAttributes.addFlashAttribute("errorMsg","Register failed because email existed!");    		
		}
		return "redirect:/register";
    }
	
	
	@PostMapping("/save-admin")
    public String saveAdmin(@ModelAttribute User user, RedirectAttributes redirectAttributes) throws InterruptedException, IOException {
		String email = user.getEmail();
		
		if(!userService.existsEmail(email)) {
		
    	   boolean isSaved = userService.saveAdmin(user);
		
    	   if(isSaved) {
    		    redirectAttributes.addFlashAttribute("succMsg","Add success!");
    	   }else {
    		    redirectAttributes.addFlashAttribute("errorMsg","Add failed!");    		
    	}
		}else {
		    redirectAttributes.addFlashAttribute("errorMsg","Add failed because email existed!");    		
		}
		return "redirect:/admin/add-admin";
    }
}
