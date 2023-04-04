package com.metechvn.contacts.repositories;

import com.metechvn.contacts.entities.Segmentation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SegmentationRepository extends JpaRepository<Segmentation, UUID> {

    List<Segmentation> findByName(String name);

}
