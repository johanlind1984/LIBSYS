package com.newtongroup.library.Repository;

import com.newtongroup.library.Entity.Book;
import com.newtongroup.library.Entity.RemovedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemovedBookRepository extends JpaRepository<RemovedBook, Long> {

}
