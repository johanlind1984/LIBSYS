package com.newtongroup.library.Repository;

import com.newtongroup.library.Entity.LibraryCard;
import com.newtongroup.library.Entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryCardRepository extends JpaRepository <LibraryCard, Long> {
}
