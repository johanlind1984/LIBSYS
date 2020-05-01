package com.newtongroup.library.Utils;

import com.newtongroup.library.Entity.User;

import java.security.Principal;

public class HeaderUtils {
    public static String getHeaderString(User user) {
        if(user.getAuthority().getAuthorityName().equals("ROLE_ADMIN")) {
            return "admin/adminheader.html";
        } else if(user.getAuthority().getAuthorityName().equals("ROLE_LIBRARIAN")) {
            return "librarian/librarianheader.html";
        } else if(user.getAuthority().getAuthorityName().equals("ROLE_VISITOR")) {
            return "visitor/visitorheader.html";
        }
        return null;
    }
}
