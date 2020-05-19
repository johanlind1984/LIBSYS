package com.newtongroup.library.Repository;

import com.newtongroup.library.Entity.Admin;
import com.newtongroup.library.Entity.Person;
import com.newtongroup.library.Entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitorRepository extends UserTypeRepository<Visitor> {
    Visitor findByEmail(String email);
    List<Visitor> findByIsActive(boolean isActive);
    String deleteByEmail(String email);
}
