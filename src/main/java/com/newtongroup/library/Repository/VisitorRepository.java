package com.newtongroup.library.Repository;

import com.newtongroup.library.Entity.Admin;
import com.newtongroup.library.Entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, String> {

}
