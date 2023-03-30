package com.metechvn.resource.entities;

import com.metechvn.common.persistent.UUIDFullAuditedEntityImpl;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "import_file")
public class ImportFile extends UUIDFullAuditedEntityImpl {
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "total_records")
    private Long totalRecords;
    @Column(name = "header_mapping", length = 2040)
    private String headerMapping;
    @Column(name = "file_path")
    private String filePath;
    @Column(name = "tenant")
    private String tenant;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "import_status_id")
    private ImportStatus importStatus;

    public ImportFile() {
        super();

        this.importStatus = ImportStatus.builder().id(this.getId()).build();
    }

    @Builder
    public ImportFile(
            final String fileName,
            final ImportStatus importStatus,
            final Long totalRecords,
            final String headerMapping,
            final String filePath,
            final String tenant) {
        this();

        this.fileName = fileName;
        this.totalRecords = totalRecords;
        this.headerMapping = headerMapping;
        this.filePath = filePath;
        this.tenant = tenant;

        if (importStatus != null) this.importStatus = importStatus;

        if (totalRecords != null) this.importStatus.setTotalRows(totalRecords.intValue());
    }

}
