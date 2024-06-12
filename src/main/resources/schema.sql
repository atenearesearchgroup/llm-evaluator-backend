CREATE SCHEMA
    IF NOT EXISTS hermesanalyzer;
USE hermesanalyzer;
CREATE TABLE IF NOT EXISTS intent_model
(
    model_name   VARCHAR(255) NOT NULL PRIMARY KEY,
    display_name VARCHAR(255) NULL
);
CREATE TABLE IF NOT EXISTS intent_instance
(
    id                   BIGINT       NOT NULL auto_increment PRIMARY KEY,
    platform             VARCHAR(255) NULL,
    display_name         VARCHAR(255) NULL,
    intent_model_id      VARCHAR(255) NULL,
    max_chats            INT          NOT NULL,
    max_errors           INT          NOT NULL,
    max_repeating_prompt INT          NOT NULL,
    CONSTRAINT intent_model_instance
        FOREIGN KEY (intent_model_id)
            REFERENCES intent_model (model_name)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS chat
(
    id           BIGINT NOT NULL auto_increment PRIMARY KEY,
    draft_number INT    NOT NULL,
    instance_id  BIGINT NULL,
    finalized    BIT    NOT NULL,
    actual_node  TEXT   NULL,
    CONSTRAINT instance_chat
        FOREIGN KEY (instance_id)
            REFERENCES intent_instance (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS model_settings
(
    id                BIGINT            NOT NULL PRIMARY KEY,
    frequency_penalty FLOAT DEFAULT - 1 NULL,
    max_tokens        INT   DEFAULT - 1 NULL,
    model_name        VARCHAR(255)      NULL,
    model_owner       VARCHAR(255)      NULL,
    presence_penalty  FLOAT DEFAULT -1  NULL,
    system_prompt     VARCHAR(255)      NULL,
    temperature       FLOAT DEFAULT -1  NULL,
    topp              FLOAT DEFAULT -1  NULL,
    version           VARCHAR(255)      NULL,
    CONSTRAINT instance_model_settings FOREIGN KEY (id)
        REFERENCES intent_instance (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS prompt_iteration
(
    id        BIGINT auto_increment PRIMARY KEY,
    iteration INT          NOT NULL,
    type      VARCHAR(255) NULL,
    chat_id   BIGINT       NULL,
    CONSTRAINT chat_iteration
        FOREIGN KEY (chat_id)
            REFERENCES chat (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);
CREATE TABLE IF NOT EXISTS message
(
    message_type        VARCHAR(31)           NOT NULL,
    id                  BIGINT auto_increment PRIMARY KEY,
    content TEXT    NULL,
    timestamp           timestamp(6)          NULL,
    score   DECIMAL NULL,
    is_manual           boolean DEFAULT FALSE NULL,
    prompt_iteration_id BIGINT                NULL,
    CONSTRAINT iteration_message
        FOREIGN KEY (prompt_iteration_id)
            REFERENCES prompt_iteration (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);