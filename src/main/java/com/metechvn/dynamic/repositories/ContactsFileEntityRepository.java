package com.metechvn.dynamic.repositories;

import com.metechvn.contacts.entities.ContactsFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContactsFileEntityRepository  extends JpaRepository<ContactsFileEntity, UUID> {
}

