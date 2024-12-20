-- 设置外键检查关闭（SQLite默认开启）
PRAGMA foreign_keys = OFF;

-- ----------------------------
-- Table structure for t_blogs
-- ----------------------------
CREATE TABLE IF NOT EXISTS `t_blogs`
(
    `id`           INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `title`        TEXT                              NOT NULL,
    `content`      TEXT                              NOT NULL,
    `summary`      TEXT,
    `cover_image`  TEXT                              NOT NULL,
    `tags`         INTEGER                           NOT NULL,
    `status`       INTEGER                           NOT NULL,
    `isTop`        INTEGER                           NOT NULL,
    `is_delete`    INTEGER                           NOT NULL,
    `create_time`  INTEGER                           NOT NULL,
    `update_time`  INTEGER,
    `publish_time` INTEGER
);

-- ----------------------------
-- Table structure for t_tags
-- ----------------------------
CREATE TABLE IF NOT EXISTS `t_tags`
(
    `tag_id`   INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `tag_name` TEXT                              NOT NULL
);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
CREATE TABLE IF NOT EXISTS `t_user`
(
    `user_id`         TEXT PRIMARY KEY NOT NULL,
    `nick_name`       TEXT             NOT NULL,
    `email`           TEXT             NOT NULL,
    `password`        TEXT             NOT NULL,
    `role`            INTEGER          NOT NULL,
    `last_login_time` INTEGER
);

INSERT INTO `t_user` (`user_id`, `nick_name`, `email`, `password`, `role`)
SELECT '15683771693056',
       'admin',
       'admin@example.com',
       '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92',
       1
WHERE NOT EXISTS(SELECT 1 FROM `t_user`);

-- ----------------------------
-- Table structure for t_profile
-- ----------------------------
CREATE TABLE IF NOT EXISTS `t_profile`
(
    `id`        INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `name`      TEXT                              NOT NULL,
    `avatar`    TEXT                              NOT NULL,
    `web_title` TEXT                              NOT NULL,
    `bio`       TEXT                              NOT NULL,
    `area`      TEXT                              NOT NULL,
    `github`    TEXT                              NOT NULL,
    `telegram`  TEXT                              NOT NULL,
    `email`     TEXT                              NOT NULL,
    `markdown`  TEXT                              NOT NULL
);

INSERT INTO `t_profile` (`id`, `name`, `avatar`, `web_title`, `bio`, `area`, `github`, `telegram`, `email`, `markdown`)
SELECT 1,
       'Default Name',
       'https://example.com/default-avatar.png',
       'My Personal Blog',
       'Welcome to my blog!',
       'China',
       'default',
       'https://t.me/default',
       'default@example.com',
       '# Welcome to my blog\nThis is default content.'
WHERE NOT EXISTS(SELECT 1 FROM `t_profile`);

-- ----------------------------
-- Table structure for t_link
-- ----------------------------

CREATE TABLE IF NOT EXISTS `t_link`
(
    `id`     INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `title`  TEXT                              NOT NULL,
    `url`    TEXT                              NOT NULL,
    `avatar` TEXT                              NOT NULL
);

INSERT INTO `t_link` (`id`, `title`, `url`, `avatar`)
SELECT 1,
       '样例友链',
       'https://example.com',
       'https://example.com/avatar.png'
WHERE NOT EXISTS(SELECT 1 FROM `t_link`);

-- ----------------------------
-- Table structure for t_tools
-- ----------------------------

CREATE TABLE IF NOT EXISTS `t_tools`
(
    `id`          INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `title`       TEXT                              NOT NULL,
    `description` TEXT                              NOT NULL,
    `url`         TEXT                              NOT NULL,
    `icon`        TEXT                              NOT NULL
);

INSERT INTO `t_tools` (`id`, `title`, `description`, `url`, `icon`)
SELECT 1,
       '样例工具',
       '这是一个样例工具',
       'https://example.com',
       'https://example.com/icon.png'
WHERE NOT EXISTS(SELECT 1 FROM `t_tools`);

-- ----------------------------
-- Table structure for t_system_setting
-- ----------------------------

CREATE TABLE IF NOT EXISTS `t_system_setting`
(
    `id`               INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `secret_key`       TEXT,
    `site_secret_key`  TEXT,
    `site_url`         TEXT                              NOT NULL,
    `enable_turnstile` INTEGER                           NOT NULL,
    `icp`              TEXT                              NOT NULL
);

INSERT INTO `t_system_setting` (`id`, `site_url`, `enable_turnstile`, `icp`)
SELECT 1,
       'http://localhost:8080/api/',
       0,
       '备案中'
WHERE NOT EXISTS(SELECT 1 FROM `t_system_setting`);

-- 恢复外键检查
PRAGMA foreign_keys = ON;
