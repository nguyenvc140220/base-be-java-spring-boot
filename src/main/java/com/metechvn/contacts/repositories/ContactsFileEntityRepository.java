package com.metechvn.contacts.repositories;

import com.metechvn.contacts.entities.ContactsFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

public interface ContactsFileEntityRepository  extends JpaRepository<ContactsFileEntity, UUID> {
    @Transactional
    @Modifying
    @Query("update ContactsFileEntity c set c.headerMapping = ?1 where c.id = ?2")
    void  updateById(String headerMapping,UUID id);
}

