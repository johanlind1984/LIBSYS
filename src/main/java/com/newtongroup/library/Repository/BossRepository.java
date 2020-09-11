package com.newtongroup.library.Repository;

import com.newtongroup.library.Entity.Boss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BossRepository extends JpaRepository<Boss, String> {

}
