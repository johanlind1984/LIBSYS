package com.newtongroup.library.Repository;

import com.newtongroup.library.Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository  extends UserTypeRepository<Person> {

}
