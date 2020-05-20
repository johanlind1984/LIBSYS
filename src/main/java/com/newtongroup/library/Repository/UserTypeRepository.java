package com.newtongroup.library.Repository;

import com.newtongroup.library.Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserTypeRepository<T extends Person> extends JpaRepository<T, Long> {

}
