package com.metechvn.contacts.repositories;

import com.metechvn.contacts.entities.Segmentation;
import com.metechvn.contacts.entities.SegmentationFilter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface SegmentationFilterRepository extends JpaRepository<SegmentationFilter, UUID> {


}
