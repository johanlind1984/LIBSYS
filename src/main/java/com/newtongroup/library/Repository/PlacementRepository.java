package com.newtongroup.library.Repository;

import com.newtongroup.library.Entity.Placement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlacementRepository extends JpaRepository <Placement, Long> {
}
