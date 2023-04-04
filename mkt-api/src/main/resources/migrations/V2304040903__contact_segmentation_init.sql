create table join_segmentation_filter
(
    segmentation_id        uuid not null,
    segmentation_filter_id uuid not null
);

create table segmentation
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
    name                   varchar(510),
    primary key (id)
);

create table segmentation_filter
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
    filters                varchar(4080),
    name                   varchar(255),
    primary key (id)
);

alter table if exists join_segmentation_filter
    add constraint fk_segmentation_filter_to_segmentation foreign key (segmentation_filter_id) references segmentation_filter;
alter table if exists join_segmentation_filter
    add constraint fk_segmentation_to_segmentation_filter foreign key (segmentation_id) references segmentation;

INSERT INTO "dynamic_property" ("id", "is_deleted", "creation_by", "creation_time", "deleted_by", "deleted_time", "last_modification_by", "last_modification_time", "tenant", "code", "data_type", "description", "display_name", "editable", "input_type", "removable", "validators", "hint_text", "tooltip", "default_value") VALUES ('6af19636-b0d7-4008-a572-0c83b09fae9d', NULL, NULL, 1679649283001, NULL, NULL, NULL, NULL, NULL, 'source', 'TEXT', NULL, 'Nguồn', 'f', 'TEXT_BOX', 'f', NULL, NULL, NULL, NULL);
INSERT INTO "join_dynamic_entity_type_property" ("id", "is_deleted", "creation_by", "creation_time", "deleted_by", "deleted_time", "last_modification_by", "last_modification_time", "tenant", "code", "create_role", "delete_role", "update_role", "view_role", "entity_type_id", "property_id") VALUES ('5f5b134d-b740-48ba-9ec3-5b3fb11470e0', NULL, NULL, 1679649283538, NULL, NULL, NULL, NULL, NULL, 'source', NULL, NULL, NULL, NULL, 'd4c7efd9-d181-4791-aba5-1d64afebe9f0', '6af19636-b0d7-4008-a572-0c83b09fae9d');
