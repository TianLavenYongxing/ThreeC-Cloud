CREATE TABLE `sys_perm` (
                            `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                            `perm` varchar(100) CHARACTER SET utf8mb4 NOT NULL COMMENT '权限',
                            `perm_name` varchar(200) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '权限名称',
                            `type` enum('MENU','BUTTON','API') CHARACTER SET utf8mb4 NOT NULL COMMENT '权限类型',
                            `url` varchar(255) NOT NULL COMMENT 'URL',
                            `http_method` enum('GET','POST','PUT','DELETE','PATCH','OPTIONS','HEAD','ALL','NONE') NOT NULL COMMENT 'HTTP方法名',
                            `del_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '删除标志（0删除，1未删除）',
                            `create_by` int(11) NOT NULL COMMENT '创建人',
                            `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_by` int(11) DEFAULT NULL COMMENT '更新人',
                            `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                            `del_by` int(11) DEFAULT NULL COMMENT '删除人',
                            `del_time` datetime DEFAULT NULL COMMENT '删除时间',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `uk_permission_name` (`perm`),
                            KEY `del_by` (`del_by`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='权限表';

CREATE TABLE `sys_role` (
                            `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                            `role` varchar(64) NOT NULL COMMENT '角色',
                            `role_name` varchar(64) NOT NULL COMMENT '角色名',
                            `del_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '删除标志（0删除，1未删除）',
                            `create_by` int(11) NOT NULL COMMENT '创建人',
                            `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_by` int(11) DEFAULT NULL COMMENT '更新人',
                            `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                            `del_by` int(11) DEFAULT NULL COMMENT '删除人',
                            `del_time` datetime DEFAULT NULL COMMENT '删除时间',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `rolename` (`role_name`) USING HASH COMMENT '角色名索引',
                            KEY `update_by` (`update_by`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='系统角色表';

CREATE TABLE `sys_role_perm` (
                                 `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
                                 `perm_id` int(11) DEFAULT NULL COMMENT '权限ID',
                                 KEY `role_id` (`role_id`),
                                 KEY `perm_id` (`perm_id`),
                                 CONSTRAINT `perm_id` FOREIGN KEY (`perm_id`) REFERENCES `sys_perm` (`id`),
                                 CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sys_user` (
                            `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                            `username` varchar(64) NOT NULL COMMENT '用户名',
                            `nick_name` varchar(64) DEFAULT NULL COMMENT '昵称',
                            `password` varchar(64) NOT NULL COMMENT '密码',
                            `email` varchar(64) NOT NULL COMMENT '邮箱',
                            `phone_number` varchar(32) NOT NULL COMMENT '手机号',
                            `sex` tinyint(1) NOT NULL COMMENT '0男,1女,2跨性别,3未知',
                            `avatar` varchar(128) DEFAULT NULL COMMENT '头像',
                            `enabled` bit(1) NOT NULL DEFAULT b'1' COMMENT '启用（0未启用，1启用）',
                            `account_non_expired` bit(1) NOT NULL DEFAULT b'1' COMMENT '帐户未过期（0过期，1未过期）',
                            `credentials_non_expired` bit(1) NOT NULL DEFAULT b'1' COMMENT '凭证未过期(0过期，1未过期)',
                            `account_non_locked` bit(1) NOT NULL DEFAULT b'1' COMMENT '帐户未锁定(0锁定，1未锁定)',
                            `del_flag` bit(1) NOT NULL DEFAULT b'1' COMMENT '删除标志（0删除，1未删除）',
                            `create_by` int(11) NOT NULL COMMENT '创建人',
                            `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_by` int(11) DEFAULT NULL COMMENT '更新人',
                            `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `del_by` int(11) DEFAULT NULL COMMENT '删除人',
                            `del_time` datetime DEFAULT NULL COMMENT '删除时间',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `username` (`username`) USING HASH COMMENT '用户名索引'
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='系统用户表';

CREATE TABLE `sys_user_role` (
                                 `user_id` int(32) DEFAULT NULL COMMENT '用户ID',
                                 `role_id` int(32) NOT NULL COMMENT '角色ID',
                                 KEY `角色ID` (`role_id`),
                                 KEY `用户ID` (`user_id`),
                                 CONSTRAINT `用户ID` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色用户关联表';