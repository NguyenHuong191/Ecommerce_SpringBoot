package com.huong.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.huong.model.User;
import com.huong.repository.UserReposity;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
	private UserReposity userReposity;
	
    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userReposity.findByEmail(username);
        
        if(user == null) {
        	throw new UsernameNotFoundException("User not found!");
        }
	
		return new CustomUser(user);
	}

}
