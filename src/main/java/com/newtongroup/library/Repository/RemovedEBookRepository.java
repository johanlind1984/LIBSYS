package com.newtongroup.library.Repository;

import com.newtongroup.library.Entity.RemovedBook;
import com.newtongroup.library.Entity.RemovedEBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RemovedEBookRepository  extends JpaRepository<RemovedEBook, Long> {
}
