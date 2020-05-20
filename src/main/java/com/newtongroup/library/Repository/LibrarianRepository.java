package com.newtongroup.library.Repository;

import com.newtongroup.library.Entity.Librarian;
import com.newtongroup.library.Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibrarianRepository extends UserTypeRepository<Librarian> {
    Librarian findByEmail(String email);
    String deleteByEmail(String email);
}
