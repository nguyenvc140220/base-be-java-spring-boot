CREATE TABLE "contacts_file"
(
    "id"               uuid NOT NULL,
    "is_deleted"       bool,
    "creation_by"      uuid,
    "creation_time"    int8,
    "deleted_by"       uuid,
    "deleted_time"     int8,
    "last_modification_by"   uuid,
    "last_modification_time" int8,
    "entity_type_id"   uuid,
    "file_name"        varchar(255),
    "import_status"    varchar(50),
    "total_records"    int8,
    "total_records_success"  int8,
    "header_mapping"   varchar(2000),
    "file_path"        varchar(255)
)
;


ALTER TABLE "contacts_file"
    ADD CONSTRAINT "contacts_file_pkey" PRIMARY KEY ("id");
