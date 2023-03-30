package com.metechvn.resource.entities;

import com.metechvn.common.persistent.UUIDFullAuditedEntityImpl;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "import_status")
public class ImportStatus extends UUIDFullAuditedEntityImpl {

    @Column(name = "total_rows", columnDefinition = "int default 0")
    private int totalRows;

    @Column(name = "success_row", columnDefinition = "int default 0")
    private int successRows;

    @Column(name = "error_rows", columnDefinition = "int default 0")
    private int errorRows;

    @Column(name = "error_file", length = 510)
    private String errorFile;

    @Column(name = "job_id")
    private String jobId;
}
