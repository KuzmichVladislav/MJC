INSERT INTO tag (id, name)
VALUES (1, 'name1');
INSERT INTO tag (id, name)
VALUES (2, 'name2');
INSERT INTO tag (id, name)
VALUES (3, 'name3');
INSERT INTO tag (id, name)
VALUES (4, 'name4');
INSERT INTO tag (id, name)
VALUES (5, 'name5');
INSERT INTO tag (id, name)
VALUES (6, 'name6');
INSERT INTO tag (id, name)
VALUES (7, 'name7');
INSERT INTO tag (id, name)
VALUES (8, 'name8');
INSERT INTO tag (id, name)
VALUES (9, 'name9');
INSERT INTO tag (id, name)
VALUES (10, 'name10');
INSERT INTO tag (id, name)
VALUES (11, 'name11');
INSERT INTO tag (id, name)
VALUES (12, 'name12');
INSERT INTO tag (id, name)
VALUES (13, 'name13');
INSERT INTO tag (id, name)
VALUES (14, 'name14');
INSERT INTO tag (id, name)
VALUES (15, 'name15');
INSERT INTO tag (id, name)
VALUES (16, 'name16');
INSERT INTO tag (id, name)
VALUES (17, 'name17');
INSERT INTO tag (id, name)
VALUES (18, 'name18');
INSERT INTO tag (id, name)
VALUES (19, 'name19');
INSERT INTO tag (id, name)
VALUES (20, 'name20');

INSERT INTO gift_certificate (id, name, description, price, duration, createDate, lastUpdateDate)
VALUES (1, 'name1', 'description1', 1.00, 1, '2021-12-01 21:24:44', '2021-12-01 21:24:44');
INSERT INTO gift_certificate (id, name, description, price, duration, createDate, lastUpdateDate)
VALUES (2, 'name2', 'description2', 2.00, 2, '2021-12-06 16:12:16', '2021-12-06 16:12:16');
INSERT INTO gift_certificate (id, name, description, price, duration, createDate, lastUpdateDate)
VALUES (3, 'name3', 'description3', 3.00, 3, '2021-12-06 16:16:02', '2021-12-06 16:16:02');
INSERT INTO gift_certificate (id, name, description, price, duration, createDate, lastUpdateDate)
VALUES (4, 'name4', 'description4', 4.00, 4, '2021-12-02 11:06:42', '2021-12-02 11:06:42');
INSERT INTO gift_certificate (id, name, description, price, duration, createDate, lastUpdateDate)
VALUES (5, 'name5', 'description5', 5.00, 5, '2021-12-02 11:07:01', '2021-12-02 11:07:01');
INSERT INTO gift_certificate (id, name, description, price, duration, createDate, lastUpdateDate)
VALUES (6, 'name6', 'description6', 6.00, 6, '2021-12-02 14:46:44', '2021-12-02 14:46:44');
INSERT INTO gift_certificate (id, name, description, price, duration, createDate, lastUpdateDate)
VALUES (7, 'name7', 'description7', 7.00, 7, '2021-12-06 15:38:23', '2021-12-06 15:38:23');
INSERT INTO gift_certificate (id, name, description, price, duration, createDate, lastUpdateDate)
VALUES (8, 'name8', 'description8', 8.00, 8, '2021-12-06 15:38:23', '2021-12-06 15:38:23');
INSERT INTO gift_certificate (id, name, description, price, duration, createDate, lastUpdateDate)
VALUES (9, 'name9', 'description9', 9.00, 9, '2021-12-02 15:05:29', '2021-12-02 15:05:29');
INSERT INTO gift_certificate (id, name, description, price, duration, createDate, lastUpdateDate)
VALUES (10, 'Name10', 'description10', 10.00, 10, '2021-12-02 15:17:42', '2021-12-08 16:36:38');
INSERT INTO gift_certificate (id, name, description, price, duration, createDate, lastUpdateDate)
VALUES (11, 'name11', 'description11', 11.00, 11, '2021-12-03 10:55:18', '2021-12-03 10:55:18');
INSERT INTO gift_certificate (id, name, description, price, duration, createDate, lastUpdateDate)
VALUES (12, 'name12', 'description12', 12.00, 12, '2021-12-03 11:09:22', '2021-12-03 11:09:22');
INSERT INTO gift_certificate (id, name, description, price, duration, createDate, lastUpdateDate)
VALUES (13, 'name13', 'description13', 13.00, 13, '2021-12-03 11:19:35', '2021-12-03 11:19:35');
INSERT INTO gift_certificate (id, name, description, price, duration, createDate, lastUpdateDate)
VALUES (14, 'name14', 'description14', 14.00, 14, '2021-12-03 11:33:38', '2021-12-03 11:33:38');
INSERT INTO gift_certificate (id, name, description, price, duration, createDate, lastUpdateDate)
VALUES (15, 'name15', 'description15', 15.00, 15, '2021-12-03 11:45:00', '2021-12-03 11:45:00');
INSERT INTO gift_certificate (id, name, description, price, duration, createDate, lastUpdateDate)
VALUES (16, 'name16', 'description16', 16.00, 16, '2021-12-03 11:46:07', '2021-12-03 11:46:07');
INSERT INTO gift_certificate (id, name, description, price, duration, createDate, lastUpdateDate)
VALUES (17, 'name17', 'description17', 17.00, 17, '2021-12-06 12:59:02', '2021-12-06 12:59:02');
INSERT INTO gift_certificate (id, name, description, price, duration, createDate, lastUpdateDate)
VALUES (18, 'name18', 'description18', 18.00, 18, '2021-12-06 13:00:35', '2021-12-06 13:00:35');
INSERT INTO gift_certificate (id, name, description, price, duration, createDate, lastUpdateDate)
VALUES (19, 'name19', 'description19', 19.00, 19, '2021-12-06 13:03:35', '2021-12-06 13:03:35');
INSERT INTO gift_certificate (id, name, description, price, duration, createDate, lastUpdateDate)
VALUES (20, 'name20', 'description20', 20.00, 20, '2021-12-06 15:38:23', '2021-12-06 15:38:23');

INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (1, 1);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (18, 1);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (19, 1);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (20, 1);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (1, 2);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (2, 2);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (19, 2);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (20, 2);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (1, 3);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (2, 3);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (3, 3);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (20, 3);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (1, 4);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (2, 4);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (3, 4);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (4, 4);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (2, 5);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (3, 5);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (4, 5);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (5, 5);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (3, 6);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (4, 6);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (5, 6);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (6, 6);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (4, 7);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (5, 7);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (6, 7);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (7, 7);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (5, 8);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (6, 8);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (7, 8);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (8, 8);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (6, 9);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (7, 9);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (8, 9);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (9, 9);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (7, 10);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (8, 10);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (9, 10);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (10, 10);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (8, 11);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (9, 11);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (10, 11);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (11, 11);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (9, 12);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (10, 12);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (11, 12);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (12, 12);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (10, 13);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (11, 13);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (12, 13);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (13, 13);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (11, 14);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (12, 14);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (13, 14);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (14, 14);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (12, 15);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (13, 15);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (14, 15);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (15, 15);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (13, 16);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (14, 16);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (15, 16);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (16, 16);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (14, 17);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (15, 17);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (16, 17);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (17, 17);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (15, 18);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (16, 18);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (17, 18);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (18, 18);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (16, 19);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (17, 19);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (18, 19);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (19, 19);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (17, 20);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (18, 20);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (19, 20);
INSERT INTO gift_certificate_tag_include (giftCertificate, tag)
VALUES (20, 20);