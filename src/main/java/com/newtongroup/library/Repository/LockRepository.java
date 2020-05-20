package com.newtongroup.library.Repository;

import com.newtongroup.library.Entity.Lock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LockRepository extends JpaRepository <Lock, Long> {
}
