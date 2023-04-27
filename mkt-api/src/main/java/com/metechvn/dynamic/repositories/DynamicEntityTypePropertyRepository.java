package com.metechvn.dynamic.repositories;

import com.metechvn.dynamic.entities.DynamicEntityTypeProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface DynamicEntityTypePropertyRepository extends JpaRepository<DynamicEntityTypeProperty, UUID> {

    @Query(value = "from DynamicEntityTypeProperty tp join fetch tp.property p " +
            " where tp.entityType.id = :typeId and (:keyword is null or lower(p.displayName) like %:keyword% or lower(p.code) like %:keyword%)" +
            " order by p.lastModificationTime desc nulls last",
            countQuery = "select count(tp) from DynamicEntityTypeProperty tp " +
                    " where tp.entityType.id = :typeId " +
                    "   and (:keyword is null or (tp.property is not null and (lower(tp.property.displayName) like %:keyword% or lower(tp.property.code) like %:keyword%)))")
    Page<DynamicEntityTypeProperty> findBy(@Param("typeId") UUID entityTypeId, @Param("keyword") String keyword, Pageable page);

}
