CREATE TABLE "dynamic_entity"
(
    "id"               uuid NOT NULL,
    "is_deleted"       bool,
    "creation_by"      uuid,
    "creation_time"    int8,
    "deleted_by"       uuid,
    "deleted_time"     int8,
    "last_update_by"   uuid,
    "last_update_time" int8,
    "entity_type_id"   uuid
)
;

CREATE TABLE "dynamic_entity_property"
(
    "id"                      uuid NOT NULL,
    "is_deleted"              bool,
    "creation_by"             uuid,
    "creation_time"           int8,
    "deleted_by"              uuid,
    "deleted_time"            int8,
    "last_update_by"          uuid,
    "last_update_time"        int8,
    "entity_type_id"          uuid,
    "entity_type_property_id" uuid
)
;

CREATE TABLE "dynamic_entity_property_value"
(
    "id"                 uuid NOT NULL,
    "is_deleted"         bool,
    "creation_by"        uuid,
    "creation_time"      int8,
    "deleted_by"         uuid,
    "deleted_time"       int8,
    "last_update_by"     uuid,
    "last_update_time"   int8,
    "value"              varchar(255),
    "entity_property_id" uuid
)
;

CREATE TABLE "dynamic_entity_type"
(
    "id"               uuid NOT NULL,
    "is_deleted"       bool,
    "creation_by"      uuid,
    "creation_time"    int8,
    "deleted_by"       uuid,
    "deleted_time"     int8,
    "last_update_by"   uuid,
    "last_update_time" int8,
    "code"             varchar(255),
    "description"      varchar(510),
    "display_name"     varchar(255)
)
;

CREATE TABLE "dynamic_property"
(
    "id"               uuid NOT NULL,
    "is_deleted"       bool,
    "creation_by"      uuid,
    "creation_time"    int8,
    "deleted_by"       uuid,
    "deleted_time"     int8,
    "last_update_by"   uuid,
    "last_update_time" int8,
    "code"             varchar(255),
    "description"      varchar(255),
    "display_name"     varchar(255),
    "editable"         bool,
    "input_type"       varchar(255),
    "removable"        bool,
    "validators"       varchar(2040)
)
;

CREATE TABLE "join_dynamic_entity_type_property"
(
    "id"               uuid NOT NULL,
    "is_deleted"       bool,
    "creation_by"      uuid,
    "creation_time"    int8,
    "deleted_by"       uuid,
    "deleted_time"     int8,
    "last_update_by"   uuid,
    "last_update_time" int8,
    "code"             varchar(255),
    "create_role"      varchar(255),
    "delete_role"      varchar(255),
    "update_role"      varchar(255),
    "view_role"        varchar(255),
    "entity_type_id"   uuid,
    "property_id"      uuid
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
    ADD CONSTRAINT "fk_entity_type_join_entity" FOREIGN KEY ("entity_type_id") REFERENCES "dynamic_entity_type" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE "dynamic_entity_property"
    ADD CONSTRAINT "fk_entity_join_entity_property" FOREIGN KEY ("entity_type_id") REFERENCES "dynamic_entity" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE "dynamic_entity_property"
    ADD CONSTRAINT "fk_entity_type_property_join_property" FOREIGN KEY ("entity_type_property_id") REFERENCES "join_dynamic_entity_type_property" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE "dynamic_entity_property_value"
    ADD CONSTRAINT "fk_entity_property_join_property_value" FOREIGN KEY ("entity_property_id") REFERENCES "dynamic_entity_property" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE "join_dynamic_entity_type_property"
    ADD CONSTRAINT "fk_entity_type_join_entity_type_property" FOREIGN KEY ("entity_type_id") REFERENCES "dynamic_entity_type" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE "join_dynamic_entity_type_property"
    ADD CONSTRAINT "fk_property_join_entity_type_property" FOREIGN KEY ("property_id") REFERENCES "dynamic_property" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;
