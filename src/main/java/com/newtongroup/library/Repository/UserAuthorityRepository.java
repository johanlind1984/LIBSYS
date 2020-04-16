package com.newtongroup.library.Repository;

import com.newtongroup.library.Entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthorityRepository extends JpaRepository<Authority, Long> {

}
