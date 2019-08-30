DROP TABLE IF EXISTS ACT_PROCESS_TYPE;
CREATE TABLE `ACT_PROCESS_TYPE` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code_id` varchar(50) NOT NULL COMMENT '类别id',
  `name_` varchar(50) NOT NULL COMMENT '类型名称',
  `pid` int(11) NOT NULL DEFAULT '0' COMMENT '分类的上级id',
  `state_` bigint(4) NOT NULL DEFAULT '0' COMMENT '有效性',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_` (`name_`),
  KEY `code_id` (`code_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='流程分类';

INSERT INTO `flowable-test1`.`act_id_user` (`ID_`, `FIRST_`, `LAST_`, `DISPLAY_NAME_`, `EMAIL_`, `PWD_`)  VALUES ('admin', 'admin', 'admin', 'admin', 'admin@admin.com', 'test');

