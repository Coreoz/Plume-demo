CREATE TABLE  `demo_city` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `id_file_image` bigint(20) NOT NULL,
  `active` tinyint(1) NOT NULL,
  `last_modified` datetime NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `uniq_demo_city_name` (`name`),
  CONSTRAINT `demo_city_image` FOREIGN KEY (`id_file_image`) REFERENCES `plm_file` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO PLM_ROLE_PERMISSION VALUES(1, 'CITIES_ACCESS');
INSERT INTO PLM_ROLE_PERMISSION VALUES(1, 'CITIES_ALTER');
INSERT INTO PLM_ROLE_PERMISSION VALUES(1, 'CITIES_GALLERY');
