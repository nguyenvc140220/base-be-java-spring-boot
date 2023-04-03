CREATE TABLE "dynamic_entity"
(
    "id"                     uuid NOT NULL,
    "is_deleted"             bool,
    "creation_by"            uuid,
    "creation_time"          int8,
    "deleted_by"             uuid,
    "deleted_time"           int8,
    "last_modification_by"   uuid,
    "last_modification_time" int8,
    "tenant"                 varchar(50) COLLATE "pg_catalog"."default",
    "entity_type_id"         uuid
)
;

CREATE TABLE "dynamic_entity_property"
(
    "id"                       uuid NOT NULL,
    "is_deleted"               bool,
    "creation_by"              uuid,
    "creation_time"            int8,
    "deleted_by"               uuid,
    "deleted_time"             int8,
    "last_modification_by"     uuid,
    "last_modification_time"   int8,
    "tenant"                   varchar(50) COLLATE "pg_catalog"."default",
    "code"                     varchar(255) COLLATE "pg_catalog"."default",
    "entity_id"                uuid,
    "entity_type_property_id"  uuid,
    "entity_property_value_id" uuid
)
;

CREATE TABLE "dynamic_entity_property_value"
(
    "id"                     uuid NOT NULL,
    "is_deleted"             bool,
    "creation_by"            uuid,
    "creation_time"          int8,
    "deleted_by"             uuid,
    "deleted_time"           int8,
    "last_modification_by"   uuid,
    "last_modification_time" int8,
    "tenant"                 varchar(50) COLLATE "pg_catalog"."default",
    "value"                  varchar(255) COLLATE "pg_catalog"."default"
)
;

CREATE TABLE "dynamic_entity_type"
(
    "id"                     uuid NOT NULL,
    "is_deleted"             bool,
    "creation_by"            uuid,
    "creation_time"          int8,
    "deleted_by"             uuid,
    "deleted_time"           int8,
    "last_modification_by"   uuid,
    "last_modification_time" int8,
    "tenant"                 varchar(50) COLLATE "pg_catalog"."default",
    "code"                   varchar(255) COLLATE "pg_catalog"."default",
    "description"            varchar(510) COLLATE "pg_catalog"."default",
    "display_name"           varchar(255) COLLATE "pg_catalog"."default"
)
;

CREATE TABLE "dynamic_property"
(
    "id"                     uuid NOT NULL,
    "is_deleted"             bool,
    "creation_by"            uuid,
    "creation_time"          int8,
    "deleted_by"             uuid,
    "deleted_time"           int8,
    "last_modification_by"   uuid,
    "last_modification_time" int8,
    "tenant"                 varchar(50) COLLATE "pg_catalog"."default",
    "code"                   varchar(255) COLLATE "pg_catalog"."default",
    "data_type"              varchar(255) COLLATE "pg_catalog"."default",
    "description"            varchar(255) COLLATE "pg_catalog"."default",
    "display_name"           varchar(255) COLLATE "pg_catalog"."default",
    "editable"               bool,
    "input_type"             varchar(255) COLLATE "pg_catalog"."default",
    "removable"              bool,
    "validators"             varchar(2040) COLLATE "pg_catalog"."default"
)
;

CREATE TABLE "join_dynamic_entity_type_property"
(
    "id"                     uuid NOT NULL,
    "is_deleted"             bool,
    "creation_by"            uuid,
    "creation_time"          int8,
    "deleted_by"             uuid,
    "deleted_time"           int8,
    "last_modification_by"   uuid,
    "last_modification_time" int8,
    "tenant"                 varchar(50) COLLATE "pg_catalog"."default",
    "code"                   varchar(255) COLLATE "pg_catalog"."default",
    "create_role"            varchar(255) COLLATE "pg_catalog"."default",
    "delete_role"            varchar(255) COLLATE "pg_catalog"."default",
    "update_role"            varchar(255) COLLATE "pg_catalog"."default",
    "view_role"              varchar(255) COLLATE "pg_catalog"."default",
    "entity_type_id"         uuid,
    "property_id"            uuid
)
;

ALTER TABLE "dynamic_entity"
    ADD CONSTRAINT "dynamic_entity_pkey" PRIMARY KEY ("id");
ALTER TABLE "dynamic_entity_property"
    ADD CONSTRAINT "dynamic_entity_property_pkey" PRIMARY KEY ("id");
ALTER TABLE "dynamic_entity_property_value"
    ADD CONSTRAINT "dynamic_entity_property_value_pkey" PRIMARY KEY ("id");
ALTER TABLE "dynamic_entity_type"
    ADD CONSTRAINT "dynamic_entity_type_pkey" PRIMARY KEY ("id");
ALTER TABLE "dynamic_property"
    ADD CONSTRAINT "dynamic_property_pkey" PRIMARY KEY ("id");
ALTER TABLE "join_dynamic_entity_type_property"
    ADD CONSTRAINT "join_dynamic_entity_type_property_pkey" PRIMARY KEY ("id");
ALTER TABLE "dynamic_entity"
    ADD CONSTRAINT "fk8iwsibpk9eu2fea33a2b4acd3" FOREIGN KEY ("entity_type_id") REFERENCES "dynamic_entity_type" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;


ALTER TABLE "dynamic_entity_property"
    ADD CONSTRAINT "fk1bms23qe4jgkleak5c9s6018a" FOREIGN KEY ("entity_id") REFERENCES "dynamic_entity" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "dynamic_entity_property"
    ADD CONSTRAINT "fkae8f5wrmhrpax7rameeg3rucs" FOREIGN KEY ("entity_type_property_id") REFERENCES "join_dynamic_entity_type_property" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "dynamic_entity_property"
    ADD CONSTRAINT "fkektykoq78oqd05op56gsucxgi" FOREIGN KEY ("entity_property_value_id") REFERENCES "dynamic_entity_property_value" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE "join_dynamic_entity_type_property"
    ADD CONSTRAINT "fkof8yhnb00y26tndl9cfg9bi08" FOREIGN KEY ("entity_type_id") REFERENCES "dynamic_entity_type" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "join_dynamic_entity_type_property"
    ADD CONSTRAINT "fkpib006e13woh6c8i7xol2ejuq" FOREIGN KEY ("property_id") REFERENCES "dynamic_property" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
