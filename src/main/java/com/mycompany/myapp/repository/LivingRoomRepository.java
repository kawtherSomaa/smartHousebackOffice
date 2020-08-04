package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.LivingRoom;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LivingRoom entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LivingRoomRepository extends JpaRepository<LivingRoom, Long>, JpaSpecificationExecutor<LivingRoom> {
}
