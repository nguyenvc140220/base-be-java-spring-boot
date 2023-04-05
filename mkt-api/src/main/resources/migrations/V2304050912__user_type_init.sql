INSERT INTO "dynamic_property" ("id", "is_deleted", "creation_by", "creation_time", "deleted_by", "deleted_time", "last_modification_by", "last_modification_time", "tenant", "code", "data_type", "description", "display_name", "editable", "input_type", "removable", "validators") VALUES ('cb5321ca-26f5-4292-91ba-2c29e8f59738', NULL, NULL, 1680681838123, NULL, NULL, NULL, NULL, NULL, 'lastActive', 'DATETIME', NULL, 'Ngày hoạt động cuối', 't', 'AUTO_GEN', 'f', NULL);
INSERT INTO "dynamic_property" ("id", "is_deleted", "creation_by", "creation_time", "deleted_by", "deleted_time", "last_modification_by", "last_modification_time", "tenant", "code", "data_type", "description", "display_name", "editable", "input_type", "removable", "validators") VALUES ('d2475f63-0b23-478c-8c98-1995ef522231', NULL, NULL, 1680681838123, NULL, NULL, NULL, NULL, NULL, 'userFullName', 'TEXT', NULL, 'Họ và tên', 'f', 'TEXT_BOX', 'f', '[{"type":"required","validatorValue":"1"},{"type":"string_pattern","validatorValue":"^[^0-9@#$%\^&!?:;+=_\-,\*\(\)\{\}\[\]'']+$"},{"type":"string_length_min","validatorValue":"4"},{"type":"string_length_max","validatorValue":"50"}]');
INSERT INTO "dynamic_property" ("id", "is_deleted", "creation_by", "creation_time", "deleted_by", "deleted_time", "last_modification_by", "last_modification_time", "tenant", "code", "data_type", "description", "display_name", "editable", "input_type", "removable", "validators") VALUES ('883495b3-e73e-4dfd-bfa9-1727ceba60cf', NULL, NULL, 1680681838123, NULL, NULL, NULL, NULL, NULL, 'status', 'BOOLEAN', NULL, 'Trạng thái', 't', 'SINGLE_SELECT', 'f', NULL);
INSERT INTO "dynamic_property" ("id", "is_deleted", "creation_by", "creation_time", "deleted_by", "deleted_time", "last_modification_by", "last_modification_time", "tenant", "code", "data_type", "description", "display_name", "editable", "input_type", "removable", "validators") VALUES ('f25b7b15-eaf4-472c-bce8-135108729b43', NULL, NULL, 1680681838123, NULL, NULL, NULL, NULL, NULL, 'roles', 'TEXT', NULL, 'Quyền', 't', 'SINGLE_SELECT', 'f', '[{"type":"required","validatorValue":"1"}]');
INSERT INTO "dynamic_property" ("id", "is_deleted", "creation_by", "creation_time", "deleted_by", "deleted_time", "last_modification_by", "last_modification_time", "tenant", "code", "data_type", "description", "display_name", "editable", "input_type", "removable", "validators") VALUES ('879547a7-3739-4c30-86e4-bde83511b62a', NULL, NULL, 1680681838123, NULL, NULL, NULL, NULL, NULL, 'username', 'TEXT', NULL, 'Tên đăng nhập', 'f', 'TEXT_BOX', 'f', '[{"type":"required","validatorValue":"1"},{"type":"string_length_max","validatorValue":"32"},{"type":"string_pattern","validatorValue":"^[a-zA-Z0-9_]+$"}]');

INSERT INTO "dynamic_entity_type" ("id", "is_deleted", "creation_by", "creation_time", "deleted_by", "deleted_time", "last_modification_by", "last_modification_time", "tenant", "code", "description", "display_name") VALUES ('f8e87080-0fb5-4c0a-8467-7e7fe8575a30', NULL, NULL, 1680681838123, NULL, NULL, NULL, NULL, NULL, 'USER', NULL, 'Người dùng');

INSERT INTO "join_dynamic_entity_type_property" ("id", "is_deleted", "creation_by", "creation_time", "deleted_by", "deleted_time", "last_modification_by", "last_modification_time", "tenant", "code", "create_role", "delete_role", "update_role", "view_role", "entity_type_id", "property_id") VALUES ('efca0433-2e4d-44f1-a83f-8b1e285469ac', NULL, NULL, 1680681838123, NULL, NULL, NULL, NULL, NULL, 'phoneNumber', NULL, NULL, NULL, NULL, 'f8e87080-0fb5-4c0a-8467-7e7fe8575a30', '6af19636-b0d7-4008-a572-0c83b09fae9c');
INSERT INTO "join_dynamic_entity_type_property" ("id", "is_deleted", "creation_by", "creation_time", "deleted_by", "deleted_time", "last_modification_by", "last_modification_time", "tenant", "code", "create_role", "delete_role", "update_role", "view_role", "entity_type_id", "property_id") VALUES ('e7029d66-96c5-4ec1-a3ed-c2de5c5f0026', NULL, NULL, 1680681838123, NULL, NULL, NULL, NULL, NULL, 'email', NULL, NULL, NULL, NULL, 'f8e87080-0fb5-4c0a-8467-7e7fe8575a30', 'db536197-890f-4353-966a-ad1080fd51be');
INSERT INTO "join_dynamic_entity_type_property" ("id", "is_deleted", "creation_by", "creation_time", "deleted_by", "deleted_time", "last_modification_by", "last_modification_time", "tenant", "code", "create_role", "delete_role", "update_role", "view_role", "entity_type_id", "property_id") VALUES ('445d540b-5621-46cb-9f28-35ad308d67a4', NULL, NULL, 1680681838123, NULL, NULL, NULL, NULL, NULL, 'lastActive', NULL, NULL, NULL, NULL, 'f8e87080-0fb5-4c0a-8467-7e7fe8575a30', '3c370e06-0f2e-4b70-8553-643aaf45f54c');
INSERT INTO "join_dynamic_entity_type_property" ("id", "is_deleted", "creation_by", "creation_time", "deleted_by", "deleted_time", "last_modification_by", "last_modification_time", "tenant", "code", "create_role", "delete_role", "update_role", "view_role", "entity_type_id", "property_id") VALUES ('60dabe63-5f5b-4c74-992b-adea7820581b', NULL, NULL, 1680681838123, NULL, NULL, NULL, NULL, NULL, 'userFullName', NULL, NULL, NULL, NULL, 'f8e87080-0fb5-4c0a-8467-7e7fe8575a30', 'd2475f63-0b23-478c-8c98-1995ef522231');
INSERT INTO "join_dynamic_entity_type_property" ("id", "is_deleted", "creation_by", "creation_time", "deleted_by", "deleted_time", "last_modification_by", "last_modification_time", "tenant", "code", "create_role", "delete_role", "update_role", "view_role", "entity_type_id", "property_id") VALUES ('1f3c9fe5-4c23-4666-ae1f-93001e9248e5', NULL, NULL, 1680681838123, NULL, NULL, NULL, NULL, NULL, 'status', NULL, NULL, NULL, NULL, 'f8e87080-0fb5-4c0a-8467-7e7fe8575a30', '883495b3-e73e-4dfd-bfa9-1727ceba60cf');
INSERT INTO "join_dynamic_entity_type_property" ("id", "is_deleted", "creation_by", "creation_time", "deleted_by", "deleted_time", "last_modification_by", "last_modification_time", "tenant", "code", "create_role", "delete_role", "update_role", "view_role", "entity_type_id", "property_id") VALUES ('fdc0523d-1181-4a08-b1dc-ef0f5321f461', NULL, NULL, 1680681838123, NULL, NULL, NULL, NULL, NULL, 'roles', NULL, NULL, NULL, NULL, 'f8e87080-0fb5-4c0a-8467-7e7fe8575a30', 'f25b7b15-eaf4-472c-bce8-135108729b43');
INSERT INTO "join_dynamic_entity_type_property" ("id", "is_deleted", "creation_by", "creation_time", "deleted_by", "deleted_time", "last_modification_by", "last_modification_time", "tenant", "code", "create_role", "delete_role", "update_role", "view_role", "entity_type_id", "property_id") VALUES ('6bebc122-1d37-484a-9c8d-cf34777a3ddf', NULL, NULL, 1680681838123, NULL, NULL, NULL, NULL, NULL, 'username', NULL, NULL, NULL, NULL, 'f8e87080-0fb5-4c0a-8467-7e7fe8575a30', '879547a7-3739-4c30-86e4-bde83511b62a');