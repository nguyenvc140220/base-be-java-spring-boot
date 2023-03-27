package com.metechvn.dynamic.repositories;

import com.metechvn.dynamic.entities.DynamicEntity;
import com.metechvn.dynamic.entities.DynamicEntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface DynamicEntityRepository extends JpaRepository<DynamicEntity, UUID> {
    @Query("select det from DynamicEntity det " +
            " left join fetch det.entityType et " +
            " left join fetch det.properties dp " +
            " left join fetch dp.entityPropertyValue dpv " +
            " where det.id = ?1")
    DynamicEntity findIncludeRelationsById(UUID id);

}
