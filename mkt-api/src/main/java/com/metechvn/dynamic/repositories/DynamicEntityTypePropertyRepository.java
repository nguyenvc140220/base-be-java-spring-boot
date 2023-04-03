package com.metechvn.dynamic.repositories;

import com.metechvn.dynamic.entities.DynamicEntityType;
import com.metechvn.dynamic.entities.DynamicEntityTypeProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface DynamicEntityTypePropertyRepository extends JpaRepository<DynamicEntityTypeProperty, UUID> {

    @Query(value = "from DynamicEntityTypeProperty tp join fetch tp.property p " +
            " where tp.entityType = ?1 and (?2 is null or p.displayName like ?2 or p.code like ?2)",
            countQuery = "select count(tp) from DynamicEntityTypeProperty tp " +
                    " where tp.entityType = ?1 " +
                    "   and (?2 is null or tp.property.displayName like ?2 or tp.property.code like ?2)")
    Page<DynamicEntityTypeProperty> findBy(DynamicEntityType type, String keyword, Pageable page);

}
