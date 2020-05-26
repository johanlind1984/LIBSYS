package com.newtongroup.library.Controller;

import com.newtongroup.library.Entity.Admin;
import com.newtongroup.library.Repository.AdminRepository;
import com.newtongroup.library.Repository.UserAuthorityRepository;
import com.newtongroup.library.Repository.UserRepository;
import com.newtongroup.library.Utils.HeaderUtils;
import com.newtongroup.library.Wrapper.UserPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/register-admin")
public class RegisterAdminController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthorityRepository userAuthorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/")
    public String registerAdmin(Model theModel, Principal principal) {
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        theModel.addAttribute("userPerson", new UserPerson());
        return "register-admin/register-admin";
    }

    @RequestMapping("/save-admin")
    public String saveAdminToDatabase(@ModelAttribute("userPerson") UserPerson userPerson, Model theModel, Principal principal) {
        theModel.addAttribute("header", HeaderUtils.getHeaderString(userRepository.findByUsername(principal.getName())));
        userPerson.setPersonAsAdmin();

        Admin admin = adminRepository.findByEmail(userPerson.getAdmin().getEmail());
        if(admin == null) {
            setAdminValues(userPerson);
            saveUserPersonAsAdminToDatabase(userPerson);
            return "register-admin/admin-registration-confirmation";
        } else {
            return "error/eposten-redan-registrerad";
        }
    }

    private void setAdminValues(UserPerson userPerson) {
        userPerson.getUser().setAuthority(userAuthorityRepository.findById((long) 1).orElse(null));
        userPerson.getUser().setEnabled(true);
        userPerson.getUser().setUsername(userPerson.getAdmin().getEmail());
        userPerson.getUser().setPassword(passwordEncoder.encode(userPerson.getUser().getPassword()));
    }

    private void saveUserPersonAsAdminToDatabase(UserPerson userPerson) {
        adminRepository.saveAndFlush((userPerson.getAdmin()));
        userRepository.saveAndFlush(userPerson.getUser());
    }
}
