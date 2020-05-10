package com.newtongroup.library.Repository;

import com.newtongroup.library.Entity.RemovedBook;
import com.newtongroup.library.Entity.RemovedUserLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemovedUserLoanRepository extends JpaRepository<RemovedUserLoan, Long> {

}
