package com.metechvn.dynamic.repositories;

import com.metechvn.dynamic.entities.DynamicProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface DynamicPropertyRepository extends JpaRepository<DynamicProperty, UUID> {
    DynamicProperty findByCode(String code);

    @Query("from DynamicProperty p where ?1 is null or lower(p.code) like ?1 or lower(p.displayName) like ?1")
    Page<DynamicProperty> findByKeyword(String keyword, Pageable page);

    @Query(value = "SELECT count(dp)"
            + " FROM DynamicEntityProperty dp"
            + " WHERE dp.entityProperty.property.id = ?1")
    Integer countValueById(UUID propertyId);

}
