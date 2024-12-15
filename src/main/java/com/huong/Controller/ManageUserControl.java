package com.huong.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.huong.service.UserService;

@Controller
@RequestMapping("/admin/users")
public class ManageUserControl {
	@Autowired
	private UserService userService;
	
	@GetMapping("/updateUserStt")
	public String updateUserAccountStatus(@RequestParam Boolean stt, @RequestParam Integer uid,@RequestParam Integer type, RedirectAttributes redirectAttributes) {
		Boolean isUpdate = userService.updateAccountStatus(uid,stt);
		
		if(isUpdate) {
			redirectAttributes.addFlashAttribute("succMsg", "Update status for account successfully!");
		}else {
			redirectAttributes.addFlashAttribute("errorMsg", "Update status for account failed!");			
		}
		return "redirect:/admin/users?type="+type;
	}

	

}
