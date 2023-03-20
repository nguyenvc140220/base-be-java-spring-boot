package com.metechvn.dynamic.repositories;

import com.metechvn.dynamic.entities.DynamicEntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface DynamicEntityTypeRepository extends JpaRepository<DynamicEntityType, UUID> {

    @Query("select det from DynamicEntityType det " +
            " left join fetch det.properties detp " +
            " left join fetch detp.property dp " +
            " where det.id = ?1")
    DynamicEntityType findIncludeRelationsById(UUID id);

    @Query("select det from DynamicEntityType det " +
            " left join fetch det.properties detp " +
            " left join fetch detp.property dp " +
            " where det.code = ?1")
    DynamicEntityType findIncludeRelationsByCode(String code);
}
