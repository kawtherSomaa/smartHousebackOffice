package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BathRoom;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BathRoom entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BathRoomRepository extends JpaRepository<BathRoom, Long>, JpaSpecificationExecutor<BathRoom> {
}
