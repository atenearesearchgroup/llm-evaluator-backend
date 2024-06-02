create schema if not exists mydatabase;

create table if not exists mydatabase.intent_model
(
    model_name
    varchar
(
    255
) not null
    primary key,
    display_name varchar
(
    255
) null
    );

create table if not exists mydatabase.intent_instance
(
    id
    bigint
    not
    null
    auto_increment
    primary
    key,
    platform
    varchar
(
    255
) null,
    display_name varchar
(
    255
) null,
    intent_model_id varchar
(
    255
) null,
    max_drafts int not null,
    maxk int not null,
    max_repeating_prompt int not null,
    constraint FK6ojoga3xfyits88igt9y1atd7
    foreign key
(
    intent_model_id
) references mydatabase.intent_model
(
    model_name
)
    on delete CASCADE
    on update CASCADE
    );

create table if not exists mydatabase.draft
(
    id
    bigint
    not
    null
    auto_increment
    primary
    key,
    draft_number
    int
    not
    null,
    instance_id
    bigint
    null,
    finalized
    bit
    not
    null,
    actual_node
    text
    null,
    constraint
    FK8xrqdx3to04ayagov8u18qdpl
    foreign
    key
(
    instance_id
) references mydatabase.intent_instance
(
    id
)
    on delete CASCADE
    on update CASCADE
    );

create table if not exists mydatabase.model_settings
(
    id
    bigint
    not
    null
    primary
    key,
    frequency_penalty
    float
    default
    -
    1
    null,
    max_tokens
    int
    default
    -
    1
    null,
    model_name
    varchar
(
    255
) null,
    model_owner varchar
(
    255
) null,
    presence_penalty float default -1 null,
    system_prompt varchar
(
    255
) null,
    temperature float default -1 null,
    topp float default -1 null,
    version varchar
(
    255
) null,
    constraint FK4n4ijkx1q8gncr1qj1fpol9r1
    foreign key
(
    id
) references mydatabase.intent_instance
(
    id
)
    on delete CASCADE
    on update CASCADE
    );

create table if not exists mydatabase.prompt_iteration
(
    id
    bigint
    auto_increment
    primary
    key,
    iteration
    int
    not
    null,
    type
    varchar
(
    255
) null,
    draft_id bigint null,
    constraint FKopttgjigkrdykjqqylv15ldg4
    foreign key
(
    draft_id
) references mydatabase.draft
(
    id
)
    on delete CASCADE
    on update CASCADE
    );

create table if not exists mydatabase.message
(
    message_type
    varchar
(
    31
) not null,
    id bigint auto_increment
    primary key,
    content varchar
(
    255
) null,
    timestamp timestamp(
    6
) null,
    score int null,
    is_manual boolean default false null,
    prompt_iteration_id bigint null,
    constraint FKkr6fop6r5pdynj78o7aypiuai
    foreign key
(
    prompt_iteration_id
) references mydatabase.prompt_iteration
(
    id
)
    on delete CASCADE
    on update CASCADE
    );

