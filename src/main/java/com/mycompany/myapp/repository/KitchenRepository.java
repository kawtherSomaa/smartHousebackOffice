package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Kitchen;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Kitchen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KitchenRepository extends JpaRepository<Kitchen, Long>, JpaSpecificationExecutor<Kitchen> {
}
