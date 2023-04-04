package com.metechvn.contacts.repositories;

import com.metechvn.contacts.entities.Segmentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface SegmentationRepository extends JpaRepository<Segmentation, UUID> {

    List<Segmentation> findByName(String name);

    @Query("from Segmentation s where ?1 is null or lower(s.name) like ?1")
    Page<Segmentation> getSegmentations(String keyword, Pageable pageable);

}
