package com.newtongroup.library.Repository;

import com.newtongroup.library.Entity.RemovedEBook;
import com.newtongroup.library.Entity.RemovedSeminary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RemovedSeminaryRepository extends JpaRepository<RemovedSeminary, String> {
}
