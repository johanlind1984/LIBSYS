package com.newtongroup.library.Repository;

import com.newtongroup.library.Entity.Unlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnlockRepository extends JpaRepository <Unlock, Long> {
}
