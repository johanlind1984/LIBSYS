package com.newtongroup.library.Repository;

import com.newtongroup.library.Entity.Admin;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends UserTypeRepository<Admin> {
    Admin findByEmail(String email);
    String deleteByEmail(String email);
}
