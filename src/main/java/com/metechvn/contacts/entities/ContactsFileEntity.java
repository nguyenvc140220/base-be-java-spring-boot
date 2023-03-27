package com.metechvn.contacts.entities;

import com.metechvn.common.persistent.UUIDFullAuditedEntityImpl;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "contacts_file", schema = "company")
public class ContactsFileEntity extends UUIDFullAuditedEntityImpl {
    @Column(
            name = "file_name"
    )
    private String fileName;
    @Column(
            name = "import_status"
    )
    private String importStatus;
    @Column(
            name = "total_records"
    )
    private Long totalRecords;
    @Column(
            name = "total_records_success"
    )
    private Long totalRecordsSuccess;
    @Column(
            name = "header_mapping"
    )
    private String headerMapping;
    @Column(
            name = "file_path"
    )
    private String filePath;
    @Column(
            name = "tenant"
    )
    private String tenant;


    public static ContactsFileEntity.ContactsFileEntityBuilder builder() {
        return new ContactsFileEntity.ContactsFileEntityBuilder();
    }

    public String getFileName() {
        return this.fileName;
    }
    public String getImportStatus() {
        return this.importStatus;
    }
    public Long getTotalRecords() {
        return this.totalRecords;
    }
    public Long getTotalRecordsSuccess() {
        return this.totalRecordsSuccess;
    }
    public String getHeaderMapping() {
        return this.headerMapping;
    }
    public String getFilePath() {
        return this.filePath;
    }
    public String getTenant() {
        return this.tenant;
    }

    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    public void setImportStatus(final String importStatus) {
        this.importStatus = importStatus;
    }

    public void setTotalRecords(final Long totalRecords) {
        this.totalRecords = totalRecords;
    }
    public void setTotalRecordsSuccess(final Long totalRecordsSuccess) {
        this.totalRecordsSuccess = totalRecordsSuccess;
    }

    public void setFilePath(final String filePath) {
        this.filePath = filePath;
    }

    public void setHeaderMapping(final String headerMapping) {
        this.headerMapping = headerMapping;
    }
    public void setTenant(final String tenant) {
        this.tenant = tenant;
    }



    public ContactsFileEntity() {
    }

    public ContactsFileEntity(final String fileName, final String importStatus, final Long totalRecords, final Long totalRecordsSuccess, final String headerMapping,final String filePath,final String tenant) {
        this.fileName = fileName;
        this.importStatus = importStatus;
        this.totalRecords = totalRecords;
        this.totalRecordsSuccess = totalRecordsSuccess;
        this.headerMapping = headerMapping;
        this.filePath = filePath;
        this.tenant = tenant;
    }

    public static class ContactsFileEntityBuilder {
        private String fileName;
        private String importStatus;
        private Long totalRecords;
        private Long totalRecordsSuccess;
        private String headerMapping;
        private String filePath;
        private String tenant;

        ContactsFileEntityBuilder() {
        }

        public ContactsFileEntity.ContactsFileEntityBuilder fileName(final String fileName) {
            this.fileName = fileName;
            return this;
        }

        public ContactsFileEntity.ContactsFileEntityBuilder importStatus(final String importStatus) {
            this.importStatus = importStatus;
            return this;
        }

        public ContactsFileEntity.ContactsFileEntityBuilder totalRecords(final Long totalRecords) {
            this.totalRecords = totalRecords;
            return this;
        }

        public ContactsFileEntity.ContactsFileEntityBuilder totalRecordsSuccess(final Long totalRecordsSuccess) {
            this.totalRecordsSuccess = totalRecordsSuccess;
            return this;
        }

        public ContactsFileEntity.ContactsFileEntityBuilder headerMapping(final String headerMapping) {
            this.headerMapping = headerMapping;
            return this;
        }

        public ContactsFileEntity.ContactsFileEntityBuilder filePath(final String filePath) {
            this.filePath = filePath;
            return this;
        }

        public ContactsFileEntity.ContactsFileEntityBuilder tenant(final String tenant) {
            this.tenant = tenant;
            return this;
        }

        public ContactsFileEntity build() {
            return new ContactsFileEntity(this.fileName, this.importStatus, this.totalRecords, this.totalRecordsSuccess, this.headerMapping, this.filePath, this.tenant);
        }

    }

}
