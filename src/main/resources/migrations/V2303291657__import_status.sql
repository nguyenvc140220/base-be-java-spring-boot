create table import_status
(
    id                     uuid not null,
    is_deleted             boolean default false,
    creation_by            uuid,
    creation_time          bigint,
    deleted_by             uuid,
    deleted_time           bigint,
    last_modification_by   uuid,
    last_modification_time bigint,
    tenant                 varchar(50),
    job_id                 varchar(255),
    error_file             varchar(510),
    error_rows             int     default 0,
    success_row            int     default 0,
    total_rows             int     default 0,
    primary key (id)
);