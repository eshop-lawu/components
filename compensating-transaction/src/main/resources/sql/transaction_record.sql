CREATE TABLE IF NOT EXISTS `transaction_record`
(
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `relate_id` bigint(20) UNSIGNED NOT NULL COMMENT '关联ID',
  `type` tinyint(3) UNSIGNED NOT NULL COMMENT '事务类型',
  `is_processed` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '已处理，0否，1是',
  `times` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '执行次数',
  `gmt_modified` datetime(0) NOT NULL COMMENT '修改时间',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_relate_id_type`(`relate_id`, `type`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 ROW_FORMAT = Dynamic;