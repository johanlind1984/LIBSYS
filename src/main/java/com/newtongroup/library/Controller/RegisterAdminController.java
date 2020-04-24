package com.newtongroup.library.Controller;

import com.newtongroup.library.Entity.Admin;
import com.newtongroup.library.Repository.AdminRepository;
import com.newtongroup.library.Repository.UserAuthorityRepository;
import com.newtongroup.library.Repository.UserRepository;
import com.newtongroup.library.Wrapper.UserPerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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

    private String adminheader = "admin/adminheader.html";

    @RequestMapping("/")
    public String registerAdmin(Model theModel) {
        theModel.addAttribute("header", adminheader);
        theModel.addAttribute("userPerson", new UserPerson());
        return "register-admin/register-admin";
    }

    @RequestMapping("/save-admin")
    public String saveAdminToDatabase(@ModelAttribute("userPerson") UserPerson userPerson, Model theModel) {
        theModel.addAttribute("header", adminheader);
        Admin admin = adminRepository.findById(userPerson.getAdmin().getEmail()).orElse(null);
        if(admin == null) {
            setAdminValues(userPerson);
            saveToDb(userPerson);
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

    private void saveToDb(UserPerson userPerson) {
        adminRepository.saveAndFlush((userPerson.getAdmin()));
        userRepository.saveAndFlush(userPerson.getUser());
    }
}