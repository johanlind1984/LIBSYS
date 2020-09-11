package com.newtongroup.library.Repository;

import com.newtongroup.library.Entity.Visitor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitorRepository extends UserTypeRepository<Visitor> {
    Visitor findByEmail(String email);
    List<Visitor> findByIsActive(boolean isActive);
    String deleteByEmail(String email);
}
