package com.metechvn.dynamic.repositories;

import com.metechvn.dynamic.entities.DynamicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DynamicEntityRepository extends JpaRepository<DynamicEntity, UUID> {
}
