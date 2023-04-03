package com.metechvn.resource.repositories;

import com.metechvn.resource.entities.ImportFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ImportFileRepository extends JpaRepository<ImportFile, UUID> {

    @Query("select f from ImportFile f join fetch f.importStatus where f.id = ?1")
    ImportFile findIncludeStatusById(UUID id);
}
