SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `transaction_record`;
CREATE TABLE `transaction_record`(
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `relate_id` bigint(20) unsigned NOT NULL,
  `type` tinyint(3) unsigned NOT NULL,
  `is_processed` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `times` bigint(20) unsigned NOT NULL DEFAULT '0',
  `gmt_modified` datetime NOT NULL,
  `gmt_create` datetime NOT NULL,
  PRIMARY KEY (`id`)
);