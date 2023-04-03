alter table "dynamic_property"
    add column hint_text     varchar(255),
    add column tooltip       varchar(255),
    add column default_value varchar(4080);

alter table "dynamic_entity_property_value"
    alter column "value" type varchar(4080);