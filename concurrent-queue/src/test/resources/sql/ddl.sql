SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `seckill_activity_product`;
CREATE TABLE `seckill_activity_product` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `activity_id` bigint(20) unsigned NOT NULL COMMENT '抢购活动id',
  `merchant_id` bigint(20) unsigned NOT NULL COMMENT '商家id',
  `product_id` bigint(20) unsigned NOT NULL COMMENT '商品id',
  `product_picture` varchar(120) NOT NULL COMMENT '商品图片',
  `product_name` varchar(100) NOT NULL COMMENT '商品名称',
  `original_price` decimal(10,2) unsigned NOT NULL COMMENT '商品原价',
  `product_model_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '商品型号总数量',
  `left_count` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '剩余数量',
  `turnover` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '成交额',
  `reasons` varchar(200) NOT NULL DEFAULT '' COMMENT '反馈原因',
  `auditor_account` varchar(50) DEFAULT NULL COMMENT '审核人员',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `attention_count` int(10) unsigned NOT NULL COMMENT '关注人数',
  `status` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '状态(1-未审核|2-已审核|3-未通过|)',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 ROW_FORMAT = Dynamic;