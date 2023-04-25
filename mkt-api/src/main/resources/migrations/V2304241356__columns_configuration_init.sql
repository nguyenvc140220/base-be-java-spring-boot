create table columns_configuration
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
    columns                TEXT,
    tableName              varchar(255)
);

INSERT INTO "dynamic_property" ("id", "is_deleted", "creation_by", "creation_time", "deleted_by", "deleted_time",
                                "last_modification_by", "last_modification_time", "tenant", "code", "data_type",
                                "description", "display_name", "editable", "input_type", "removable", "validators",
                                "hint_text", "tooltip", "default_value")
VALUES ('2bf03eb9-de9a-448a-a414-99169b7b265e', NULL, NULL, 1682326983876, NULL, NULL, NULL, NULL, NULL, 'creationSource',
        'TEXT', NULL, 'Nguồn tạo', 'f', 'TEXT_BOX', 'f', NULL, NULL, NULL, NULL);

INSERT INTO "join_dynamic_entity_type_property" ("id", "is_deleted", "creation_by", "creation_time", "deleted_by",
                                                 "deleted_time", "last_modification_by", "last_modification_time",
                                                 "tenant", "code", "create_role", "delete_role", "update_role",
                                                 "view_role", "entity_type_id", "property_id")
VALUES ('5f5b134d-1805-1805-9ec3-5b3f051870e0', NULL, NULL, 1682327008726, NULL, NULL, NULL, NULL, NULL, 'creationSource', NULL,
        NULL, NULL, NULL, 'd4c7efd9-d181-4791-aba5-1d64afebe9f0', '2bf03eb9-de9a-448a-a414-99169b7b265e');
