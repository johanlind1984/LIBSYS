package com.newtongroup.library.Repository;

import com.newtongroup.library.Entity.RemovedSeminary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemovedSeminaryRepository extends JpaRepository<RemovedSeminary, String> {
}
