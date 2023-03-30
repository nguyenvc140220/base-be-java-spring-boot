package com.metechvn.resource.entities;

import com.metechvn.common.persistent.UUIDFullAuditedEntityImpl;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
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

    @Builder
    public ImportStatus(
            UUID id,
            int totalRows,
            int successRows,
            int errorRows,
            String errorFile) {
        this.setId(id);
        this.totalRows = totalRows;
        this.successRows = successRows;
        this.errorRows = errorRows;
        this.errorFile = errorFile;
    }

    public ImportStatus incSuccess(int num) {
        if (num > 0) {
            this.successRows = this.successRows == 0 ? num : this.successRows + num;
        }

        return this;
    }

    public ImportStatus incError(int num) {
        if (num > 0) {
            this.errorRows = this.errorRows == 0 ? num : this.errorRows + num;
        }

        return this;
    }
}
