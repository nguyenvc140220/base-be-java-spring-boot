package com.metechvn.dynamic.repositories;

import com.metechvn.dynamic.entities.DynamicProperty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DynamicPropertyRepository extends JpaRepository<DynamicProperty, UUID> {
    DynamicProperty findByCode(String code);
}
