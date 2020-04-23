package com.newtongroup.library.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.newtongroup.library.Entity.EBook;

@Repository
public interface EBookRepository extends JpaRepository<EBook, Long> {

}
