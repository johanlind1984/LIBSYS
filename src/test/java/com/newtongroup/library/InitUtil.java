package com.newtongroup.library;

import com.newtongroup.library.Entity.Authority;
import com.newtongroup.library.Entity.User;
import com.newtongroup.library.Repository.AdminRepository;
import com.newtongroup.library.Repository.UserAuthorityRepository;
import com.newtongroup.library.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class InitUtil {

    public static void setupAuthorities(UserAuthorityRepository userAuthorityRepository) {
        Authority adminAuth = new Authority();
        adminAuth.setAuthorityName("ROLE_ADMIN");
        Authority librarianAuth = new Authority();
        librarianAuth.setAuthorityName("ROLE_LIBRARIAN");
        Authority visitorAuth = new Authority();
        visitorAuth.setAuthorityName("ROLE_VISITOR");
        userAuthorityRepository.save(adminAuth);
        userAuthorityRepository.save(librarianAuth);
        userAuthorityRepository.save(visitorAuth);
    }

    public static User setupAndReturnUser(Authority authority, String email) {
        User user = new User();
        user.setUsername(email);
        user.setPassword("test");
        user.setAuthority(authority);
        user.setEnabled(true);
        return user;
    }
}
