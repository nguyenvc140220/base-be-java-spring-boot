package com.metechvn.dynamic.repositories;

import com.metechvn.dynamic.entities.ImportStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImportStatusRepository extends JpaRepository<ImportStatus, UUID> {

    ImportStatus findByJobId(String jobId);

}
