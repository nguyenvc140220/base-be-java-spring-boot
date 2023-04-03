create table import_file
(
    id                     uuid not null,
    is_deleted             boolean,
    creation_by            uuid,
    creation_time          bigint,
    deleted_by             uuid,
    deleted_time           bigint,
    last_modification_by   uuid,
    last_modification_time bigint,
    tenant                 varchar(50),
    file_name              varchar(255),
    file_path              varchar(255),
    header_mapping         varchar(2040),
    total_records          bigint,
    import_status_id       uuid,
    primary key (id)
);

create table import_status
(
    id                     uuid not null,
    is_deleted             boolean,
    creation_by            uuid,
    creation_time          bigint,
    deleted_by             uuid,
    deleted_time           bigint,
    last_modification_by   uuid,
    last_modification_time bigint,
    tenant                 varchar(50),
    error_file             varchar(510),
    error_rows             int default 0,
    success_row            int default 0,
    total_rows             int default 0,
    primary key (id)
);


alter table import_file
    add constraint fk_import_file_status foreign key (import_status_id) references import_status;