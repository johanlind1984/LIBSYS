package com.newtongroup.library.Repository;

import com.newtongroup.library.Entity.RemovedEBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemovedEBookRepository  extends JpaRepository<RemovedEBook, Long> {
}
