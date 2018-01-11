CREATE TABLE IF NOT EXISTS `follow_transaction_record`
(
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `transation_id` bigint(20) UNSIGNED NOT NULL COMMENT '事务记录id',
  `topic` varchar(30) NOT NULL COMMENT 'MQ消息的topic',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uk_transation_id_topic`(`transation_id`, `topic`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 ROW_FORMAT = Dynamic;