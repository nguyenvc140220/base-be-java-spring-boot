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
