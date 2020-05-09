package com.newtongroup.library.Utils;

import com.newtongroup.library.Entity.User;
import com.newtongroup.library.Repository.UserRepository;

import java.security.Principal;

public class HeaderUtils {
	
	public static String getHeaderString(UserRepository userRepo, Principal p) {
		if (null != p) {
			return getHeaderString(userRepo.findByUsername(p.getName()));
		} else {
			return "anonymous-user/bootstrapheader.html";
		}
	}
	
    public static String getHeaderString(User user) {
        if(user.getAuthority().getAuthorityName().equals("ROLE_ADMIN")) {
            return "admin/bootstrapheader.html";
        } else if(user.getAuthority().getAuthorityName().equals("ROLE_LIBRARIAN")) {
            return "librarian/bootstrapheader.html";
        } else if(user.getAuthority().getAuthorityName().equals("ROLE_VISITOR")) {
            return "visitor/bootstrapheader.html";
        }

        return null;
    }
}
