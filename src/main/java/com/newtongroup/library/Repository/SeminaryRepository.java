package com.newtongroup.library.Repository;

import com.newtongroup.library.Entity.Seminary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeminaryRepository extends JpaRepository<Seminary, String> {
}
