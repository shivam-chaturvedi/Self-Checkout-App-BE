-- MySQL dump 10.13  Distrib 9.0.1, for Win64 (x86_64)
--
-- Host: localhost    Database: retaildb
-- ------------------------------------------------------
-- Server version	9.0.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `app_user`
--

DROP TABLE IF EXISTS `app_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `app_user` (
  `email` varchar(255) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `is_oauth_login_user` bit(1) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_user`
--

LOCK TABLES `app_user` WRITE;
/*!40000 ALTER TABLE `app_user` DISABLE KEYS */;
INSERT INTO `app_user` VALUES ('chaturvedishivam598@gmail.com','2025-03-09 00:00:00.000000','2025-03-09 00:00:00.000000',_binary '\0','$2a$10$spUIAb1PC7A.Q6yoJP..NuJnYg7VUXWZ4MuXCjE3qNLOJxLU1u5GO','ADMIN'),('shantanu@minipro','2025-03-09 12:06:24.782638','2025-03-09 12:06:24.782638',_binary '\0','$2a$10$TApQe.JgLK7D2st/He4rJ.3ewqrVv8bmui3oz3pEhwHEoc09/sd.i','ADMIN'),('shantanuyadav117@gmail.com','2025-03-09 00:00:00.000000','2025-03-09 12:08:29.250106',_binary '\0','$2a$10$BhOtqU6AUu8qkBbgXAD45eE9te10Vdlx8/DmQMUMfsNPQOWQEdQ4a','ADMIN'),('sonam.2426mca710@kiet.edu','2025-03-09 12:12:57.285919','2025-03-09 12:14:11.480819',_binary '\0','$2a$10$CAlyHcKUEJA1GdtKLSv81e157RHjKHta78vsstF4Nu0eQtZ1cgS.S','ADMIN'),('vineetprajapati1947@gmail.com','2025-03-09 12:13:16.378583','2025-03-10 04:44:48.627872',_binary '\0','$2a$10$8sM8JDOUT1aXy0IEUd76MOelrfZv5inwPVm2EWVOYpGIrNOO5u/1y','ADMIN');
/*!40000 ALTER TABLE `app_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_item`
--

DROP TABLE IF EXISTS `cart_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `amount` double NOT NULL,
  `product_id` bigint NOT NULL,
  `quantity` bigint NOT NULL,
  `user_cart_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpgax0otoefle4omeyc0adan32` (`user_cart_id`),
  CONSTRAINT `FKpgax0otoefle4omeyc0adan32` FOREIGN KEY (`user_cart_id`) REFERENCES `user_cart` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_item`
--

LOCK TABLES `cart_item` WRITE;
/*!40000 ALTER TABLE `cart_item` DISABLE KEYS */;
INSERT INTO `cart_item` VALUES (1,'2025-03-10 04:47:46.825800','2025-04-05 17:43:50.915526',40,2,8,1),(5,'2025-03-10 06:50:23.305830','2025-04-05 17:42:55.094528',900,6,10,2),(9,'2025-03-16 05:30:54.157930','2025-03-16 05:31:16.946268',30,1,2,4),(10,'2025-03-16 12:00:54.173589','2025-04-05 17:43:50.921524',25,2,5,4),(11,'2025-03-16 12:01:07.252214','2025-03-16 12:01:10.580217',270,6,3,4),(12,'2025-03-16 12:01:14.376659','2025-03-16 12:01:14.376659',120,3,1,4),(13,'2025-03-16 12:01:17.733014','2025-04-05 17:43:23.054852',32,4,4,4),(14,'2025-03-16 12:01:33.361579','2025-03-16 12:01:33.926052',8908,11,2,4),(15,'2025-03-23 13:19:05.681206','2025-04-05 17:43:23.062852',584,4,73,2),(16,'2025-03-23 13:30:11.020751','2025-04-05 17:43:43.227838',155,10,31,2),(17,'2025-03-23 14:17:44.726420','2025-04-05 17:43:50.929558',380,2,76,2),(18,'2025-03-23 22:27:38.071090','2025-03-23 22:27:44.668899',360,3,3,2),(19,'2025-03-24 21:28:26.604286','2025-04-05 16:26:51.463221',9450000,5,189,1),(20,'2025-04-04 21:51:04.030736','2025-04-04 23:11:41.762731',320688,11,72,1),(21,'2025-04-04 21:57:10.387386','2025-04-05 17:43:23.067850',360,4,45,1),(22,'2025-04-04 21:57:29.282695','2025-04-05 17:43:43.239840',190,10,38,1),(23,'2025-04-05 16:24:44.240574','2025-04-05 16:24:45.463052',240,3,2,1);
/*!40000 ALTER TABLE `cart_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `category` varchar(500) NOT NULL,
  `is_available` bit(1) NOT NULL,
  `name` varchar(500) NOT NULL,
  `price` double NOT NULL,
  `quantity` bigint NOT NULL,
  `rfid_tag` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKjmivyxk9rmgysrmsqw15lqr5b` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'2025-03-09 12:13:40.033899','2025-03-16 12:03:23.169009','Food',_binary '','Samosa',15,1000000,'45 56 7D GF'),(2,'2025-03-10 04:31:40.574210','2025-04-05 17:43:50.934521','Electronics',_binary '','HP Laptop',5,1000000,'12345678'),(3,'2025-03-10 04:35:23.021656','2025-03-16 12:03:23.227234','ELECTRONICS',_binary '','LED ',120,1000000,'87654321'),(4,'2025-03-10 04:52:49.552082','2025-04-05 17:43:23.076853','Electronics',_binary '','iPhone 14 Pro',8,1000000,'212344455'),(5,'2025-03-10 04:53:27.475251','2025-03-10 04:53:27.475251','Clothing',_binary '','Levi’s 501 Jeans',50000,1000000,'d3b2e412'),(6,'2025-03-10 04:54:30.527002','2025-03-16 12:03:23.209347','FOOD',_binary '','Chinese',90,1000000,'34 34 FF FA'),(7,'2025-03-10 04:54:32.241197','2025-03-10 04:54:32.241197','Electronic Gadget',_binary '','Oppo Reno',65000,1000000,'54237612'),(8,'2025-03-10 04:56:29.317393','2025-03-10 04:56:29.317393','Furniture',_binary '','Wooden Dining Table',79999,1000000,'112222222'),(9,'2025-03-10 04:59:39.562947','2025-03-10 04:59:39.562947','Headphones',_binary '','Sony WH-1000XM5',20000,1000000,'777'),(10,'2025-03-10 05:04:25.576099','2025-04-05 17:43:43.244826','Electronics',_binary '','Adapter',5,1000000,'12365454'),(11,'2025-03-10 06:49:44.993727','2025-03-16 12:03:23.264245','eelctronics',_binary '','laptop',4454,1000000,'131ff515');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `security_images`
--

DROP TABLE IF EXISTS `security_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `security_images` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image_data` longblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `security_images`
--

LOCK TABLES `security_images` WRITE;
/*!40000 ALTER TABLE `security_images` DISABLE KEYS */;
INSERT INTO `security_images` VALUES (1,'2025-03-24 20:33:15.148190','2025-03-24 20:33:15.148190','hello',_binary '�PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0�\0\0\0�\0\0\0`\�V\0\0\0sRGB\0�\�\�\0\0\0gAMA\0\0���a\0\0\0	pHYs\0\0%\0\0%IR$\�\0\0$�IDATx^\��eU�b/ `��`#!\ZQ��Tl\�hk\�((`\�\r\r*��D1b�\Z�`\rE�{�����X`�f�ﻜ�g\�=��\�{\�p߼�Ya޿��\�S\�=g\�s�Q\�mX7�j�U��+�S\���\�L��[*��_�\�_���rKfQ\�\�\�^�\n~\�\��se\�\�P\�\�n�6t��\�\�\n~\��\�3S�2\n~\����\�,���HSC\n>r��Cij�\�+�5\"M\ra|ŲF��!���Xֈ45���\�\�~\���\��?�a��\�_\�\�$et\�N�s\�9���\�O�\�&eB�i_\\v\�ea��.��\�؈.� ��[����&\�\r\�W\\\�D݈HSC��sr7��\r\�\�\�&M\�v�\�moc̓�\�;yn{\�\�6�v�\�u�ۤL\�5\�/|\�a�w��ݍ\�F<\��M\��\�_\�h\"\�P�\�\'?i�nD��!���3�\�g\��N;\�4z\�3�ٛ7�\�M\��\��\�?�q\�\�O~ŗ=\�i�ܽ\�\�+\�\�~�\�=\�V[m5QnDb\�\�O�EeG�эn4\�\��׿��ҿ��\�m��\�\�\�o|c*�k�r�\�\�\�+�\����\�|��k2�+^\�\���g=+���\�kJ��\�6\�L\\���\�]\�b�\n�sҳ\0���M\�\�,�\��\�?\�x�F�~\��C\�\�\rB,��\�97�U���.���\����o�\\����2��\�|\�\�\���bT�OA|�,\\V�\�\�\r-\�\�/n<c�\�\�\�\�j\�P\��\�-oyK\�y\�ސ\�9I<�)��\�XG��\�G\��\�o~s\�\�c�=R\�_�\�W�|\�\�җ�4�\��\�\�\�8\�(>\�ٽj\�W�*�\�w�?��N��^�\�OO�ʏx\�\�6��\\\�\\\�#���\�\��v�<Nn�\�\��0ٿ�\�o6\�\�\���\"P�|\�x\�\�W�\�\\~�\�a�ȱ\n���\��ʜ]�򗿼\�Rl�\�߈\�(��o\��\�\��r\��\����\�\��׽�\�\�\��d�\�?�\�D\���\��S��}\�k�uTo\�\�zCds�\�E/\Z��\�g?;\�]\�z\�M�\�<\�\�&��\�\�\�w\�t\�\�z׻6\�\'�|\�\�M\�\�\�w���X\��ַ\Z���x>@��U\�\r#\\��\�c^�lb�\� {^��\��&5F\�\r\�5킒\��� K\�\n�a�\�C\�4X9��\�\�\��/y\�K\�\�\��.��<\�)c\n�\�\�\�7�\�Moj����ew�!���<$L��_劷�\�mB_����{�}\�I�\���%\Z\��5\�-��\�\�?��)��i�{L�\�T�7�p}>�c{|^͂wK�\��\�%>�d\�\�\��ArN\�,*��\r\�S\�0B�쬂\�e%x�\�9\�׬Y\�x��V��ո>\�|�\�_�b]z\�M�I�\�\�-�\�\���\�~�\�)\�^\��\�s\�1ǌm{\�w�}\�K_J�?�\�O\'_\�\�O~rJ\�T\�I\'��\��-�\\\�LaP\'�i��x\�\�\�|�\�\�y\Z�:����\�?\�U.�>h|D\�t�\�sv�Oφu�\� �\�\�\�\�Y&w!6�\��>\r\�s�\�A|GT�W�\��Y���/�n��$\�G<\�\��_�\�;\�\�+_�\�\�\�\�.ɟW\�%�\\��x\�\�!\�oV�o�\�\�w��\�ˤ\�8\��qYT]t\�\�u\�y\�s���3\�H\�\�v\�-\���E#���\�\'�SO=5�q\�\��\��8�K\��\�\�J�=NJ\�C\�b�\n�eI\�� :\�\\\��Tv\�\'�\�=\�쌄B�M�@\�g�n67^p�\�	\�?\�R��k*\� b�͏V\����\�o\�%�Y\� }�b\�<qn\�<\�<l!��>\��\�\��=\�\�d\�\�(t<y��S�2!L�m�\�\�\�ϓ^��\�8��\�U\�3�\�\���\�}/\�yc\�\�<O{姑\rJ��\�<�ӟ�������p!�\�\\�\�]\�(uK�	>B$x��AJtA����P�\�F��!��\�P�\n�##\\u\�U\���\��7\�\�\�\�ޕ,X·(\�ӟ�\�qz)>C\�r�_s\�5)\�\��\�k4^F�X>�\�<��\�\�\�.&f)_D�\�\�$1�s뭷N�\\�\��\��\�gPY\������\�\��$\�<~_\�\�HSC����%KF\�~Ԩ,\�\�\�\���\��\�\�\�H\�%\\�Jqh�>#�����\n>C|;�\�0\�\��\�q�2,�x\�\�G�\�]�\�\�>��:	^㤗\��|v|\�CUH>Tg6lؐҜ�Ї�\�}\�s�t3\�q�{${5�\�dʂ\�\�\�\��\��\�\�h\����,\�\�{\���.H/�4�\�\�\�\�9O;\�Tn��nV�2\�\�\�\�v�d{�\�ߞ\��4����p\�\�R?��\�%>7A>\�1\�hu�G(\������Ǆ�F�\�\�\�\�\�\�\�۞�\�\�����\�\�\�\�_B��!\\�\��Us!\�\�;�q\�\�Oa�|f᷿�\�q����<\rM\�X�~�\�\�k�M���9\�\�Kv\�\�N#:�R���y|�9}\�J׾1�5��_�bi4z\��ޗ\�N\��)�QW��|zV�\�~�\�7ޣ\�\�8�?�Yh._\�J�X��s`\���\�9G�F\�ij�U\�\�#��П�mp�;�\�\�\�u�6�wF\��\�D�nI\���Hn\�*��$x\�i����_W�\�y�\"\�\�.*uT��=h|��ԧ&\�>d\Zq�\n\�\��\�~\��M�n�ۧ� �\�(�ւ�c�K�+\Z*�ϸ�\��|z�\�\�F\�g\�q\�7.�\�@v\�\�\�\�\��\�GK:\�L8\���P<�V\�\��_m<\'\�\�\�\'�	���p\�V\�/lkԔ A�\�\�w햄��4�\�tD�\�FZK#�]\�69\r�\�\�>�V�$�\�\r\�_�\��\�\�sD�\Z�\�ꨐW77�\n�B�Z\�}\�^3䧇C6U� \�e�\�/~\�\�h<G\\\��,H��I���z�!�+�|\�)\0s\�$=6��\���M\�З\�áʧwIi\��\�\�\�A\���\�oq�[�c\\\�\\sŊ��j�SG\�\�S����on\"\���\Z��\�y�E>9KO`�k1+\�X}\�÷Qp�{?|�*�\�\�\�\�sD�\Zާh\�\�\�\�\��\�\�\r�����}l�<�\�_\�*\�\��๩:>zS\�0\Z�\���o|\����H\�^\�r0\�|N�%\nĿ\�-o�b�}\�\�Mʤ\�KC�l\"E:\�z\�\�x\�;�|\�\���\�\�;Ｆ��`:�|)K�W�\��y�M\�\�<Q�	���!�\�\�\�u�\�\�F��\�\�)\�A.x�7Z�~�\�a�m\Z�Q쬂�\�*�IT�\�\�\���\'<a,$X�\�肧�@\Z�H\�\�>\�/�\��p�\"�_�\�WL<>\�Q�<>8% @�cD_�\�g�1�m\�@\�Q�\�\r�fG\�\�z.x_\0r\�QG%�C��O�lT��Oy�*)�\��nJ\�F�\�KYʿ\�;�|��\�/45��?���<����\�x\��ɻ\�o뇏��?jTz�\�\�ק.t��\�\Z�\�\�\rij�ৰ\n��B|\�.x^��+s35�)\�\n�.�\�Y[��Lq\��9Y\0�X\��\��\�G>2\�y�ʮm4�Ƈ̽�Ё\�^E\��*_�YB\��$\�>\��d\����^,;M��E+1y8(�\�ÕG�̈́~�m�}�w\�1ٕ�A2�s,�s\rr�j�;��\�<t.7]����*\�<b�/{����=���\�(.\�*踡\���Ϋ���\�\�>�/\�K\�\'Oiߛ.\�GA�Ŀ\�{#b|ߡ*�%t�zQx\�\n��\�<\\T��\�\�\�E���*�\�@4\�\�tg�*M�^�\�\�W\�~�\�*��U\�U\�9\"M\r\�BO7����LZB��\�\�\\��\�\�\��<Tc��n<�GdQ�|}_\Z\"{N� :>�Ի\�\�ä3\�+\�]\�Q�\�P�\�FɏR\�Q~�-�\�L`4ٯQN�\�*�\���\�|��~UtK�\�ҔO\�\�\��H�0m`�\�\�a�OC_���S�h\�\�\�4�+�\�|\�$\�\�V�/�\n�\n~nl[\�Qǅ\�}\�sӅ�Ժ\�\�G<�=ݱ}�\�_O��>9\�Y<7Ky\�1��E\��\�;\�\�ɏj\�4L���4yLK�l\�3\�\�s\�=S�<F�\�~\��\�_\�U1\��`V��L\�!��\�\�n\�.xF�\�˿\�˱\�45��On�7j�����%�\��\�y�\�銶�>\�\��}V<\�����`V�G�:%\\\�\�Ձ+�\�ꨂ���Ϫ��\�1�!;p\�!4 ��G�\Z�s�\�į$x\�*C��\�s]\�\�\�(OtӨ\�(�\�^\�\�K���\�?\'_6Z\�U���\��\�?\r\����|e\�cbE��򗿜|\�\\I1d;\�S\Z\�I�k�%_\'��9*ǖ#\�\�.[�s�?�j��@��\�\\\�.x\'\rb�7Z]\�Ē�2\�R|\�M\���c�\'<m�{�X6���ޯ�,�45�U\�K���d�\0F\���|�!^�\��<\�\�#�z3��s#T�W=J�����\�g9�\�\�s\����\�o}kSB�U\�9�M�d/1*Od$�K��\�\�A�A.x�HJ�\�\���b�fO��&G��!\\�\�KO\0.�\�m�\�\�.O��\�7�]\�N\�71\�FoT\�Qy\��\�]\�]\���N�U\�_?\�*�2\"M\r\�\�\�\�D	\�\�c�do<\�\\\�5\�Ⴇ\n���\�y\�{R�\�\�~��.��\'B�|�\�,m\�݆.\�e\�es\�1\�9�v\�<x\�\�\',��\��\�M�\�\�\�v�\�g*�\�;�\�S���+ߪ|�m�\�hu\�+0�\�Ӻ\�I\�8�\��Q�\��\�K\�F����x�\�y\�\�\�)!\�\�.D\���\�\��m�\�\�\�TEJԾ1<�e���J�\�pc\�Ð?\��\�O?�Ҝ������\�Q�\�e��>u���e\�\�t�\�\�0<���{Dގ\�\�\�?\�d�\�\�n�\�\�J\�\�\�\��\�.�\���\�,7\"\�\�.D\�%̳\��Be�~-P\ZA#�\"O�����\r8�(�+Km\�mul�mr\Z��?���VG|\�*��q!�\�\0D4\\\�|I\�}H$x>v��J�\��w�X\�\�\�=���\�\�\�#\�5�\�\�1F����!c\�\�.x\Z\�:\Z\�y��^xai\�\�m�h���\�:\�0�\�4���C9G��X��\Z��\\\�\�qp\�	��\���ǟ�\\\�m\�Qɹ\�p�;�Q\�<���Ԩ�8�Q��z�A���ij�\�3V�W�b� L`\�\n\�\�.�\�a\�%ul����`\�\�\�B\�\�\"ߗƷ\�+	�� \��\��\����F$x\�\�Iόʻ袋\Z\�M�����4.x/\�\�]\��\�\�tD�?\�\���\�\��|*˯�ij.�Y�\0\�|x�\�\Z�\�\�\n%�����^\�\�Wo�7*g\�w�;���_6i�\�/}��9WE?|I\�|͎>z�nݺ��$x�(ş�.x�\�Io�nL��!�a���9ٙ׮\�F\�r,:.�\��ӱM\�~��잦\�\�	\\pA\�\�Oװ��b�]��\��\��~.x\�\�\�_>�LL\��\�o�\��\��\"D�\Z\�e%��nI|�\�\�\��[2\Zi\�\�\�\�>Ӄ#���\�/!\�\�V�\�U𓨂\�P<_�c`2l\�\�\�w\�}�/��\�J\�>\�Y\��lŋ\ZQ}\�\�\�Xu\�\�m�]\�{\�\�qq�y�\���%�K/�4�����\n�\\&�ɇ`	k֬I~LYP,�W�\�Z��\��F�\�ww`��\�\�|\�;\�|[�HSC�l�Խ\�3g<\rSŘ&�Y�}(���\�\�\�h<~F\��\�\�ɱ\�45�U\��\�cT�wd�>�\�3�KB_\�	�\�\r\"\�Bz���\�!��>b� \���\����\r�t\\.xD\�ǑS���\�qc;\���͹\�^{�\�b��(�Y�:vXP\�  {<\��Ƿ�P�\�\���a�\�/�Ce)�ӿ�(D�\Z\�\�\�Q�=J\�\'p\���\�N|�7\�vorM���\��Ⱥ�$x��J�.��AJ�45��\�-��\�y>�.�\����|\�\�c?21\�L���\�`\�c\�\�wP;���\�HNbD\�\�\�+n�\�v)_!�\��\�c*�s\�Eߗ&\�7F���4ߗ��\\O\�\��{���2x`J\��\�O\�\��ڮ�45��\�\�n\����\�X\�7Hh$��\�\��[䟓s\�.m�\�\�i�`�\Zyz��7�\�g��vK6��oG�0.D\�o	\�.�\�m\�\�\�;.�\"\�kS��44\�\�\�\�:\�7-{�?_\�\'{i\�h\�bb(�&o�\���\�\�\�\'S\Z�\��\�h�\�w\�Md�@q}_\Z\'�h�3e\0\�jR>�}�\���t&\�)��\r{\�\�\�{SB��!\\�\�\�	\Z~�\�>�+\�o�\�\�iݒ��\\\�F��\�GZ\�q�{�R\�\�\�\'\�4D\�s���m\�3�4pM\�\�F�wKF=I�HSCX�\�*�IV�`�\�!7.\'=�*�ll\�\' l��\0^�\�e\�`\�\�\�\�\�\��\�3�^y�\�\rgy�\�\�W�\'>\�ɇ\�\�E7�\�\'>1�}�\�O6f^\�WU(<S\�{\�\�&_�\�\��\�\�&>�\�O\�\��>0\�\�\\\�.>9/{W\�<�\�*\�\�ij.x]��j��\�\�\�j���\'�ǧ\�\��4Z)Cv\�\�\�\�5*�\�\�~\�i�V�\�\�{�r(�\�\�\�W�45�U\�SP?�\�30\�\�E��\�k:az��/�x\�\�\�dc1�\�q\�\�.�L\�H𼞕�\r�n�\��\�E�C|\�\�\�,ȝ\�t��߫\0�ǧWJ1X���\��Qe�/\�\�M�ϵ��\���\�3����\�\���_�V~�dJ�Eq�ja\�i�;\���\�\�c#\"M\r\�B\�\�\�8۞�>\�\�S1GԨt�;��_ٽQ	J�\�.S$�R���\�.䚖\�\���\n��\�͇ \�\�V�/�\n�\n~n��+�z&dWZ]zY�s\�:ؐH�v\�m�q���\�W�<NzPT\�i���\�\�\\s\�8 \��;\�b��Sy:蠔�\��8Y�0->�`\�5���\�ۇ앩c\�^\'�fV�����ؘJvfq\�\�\�\�k\"NB\�\�\�~?\�L\�\�\�\�7B��!\\��V�\�O�F+7P�_\rA�N�-)���������\��\�\�1��7�\�o�Y\�ij�\��X\�\�*��lC\�qF\�V}\�#�^�\�\\��JY.x\��\���\���K�GZ�>?(|�\��F\�K�45�<\�X\ZR�\�\�\�t�3\�]1�\�\�	\'��%E$x\��A\�zCA6vU�>\��<6\�!xK)�\�׿�u\�J�Ұ\�\�\�	/\�UHV_ɗ	_�/\�V[m�\�\�ͪtF�e�?���\�v��45�|����.x.6�Ҵ�4��D\r͊H\���\�\�6\�\�T/o\Z�zi�Υ\�*M[/\rey^1z�G�\Z\�*�)����\�\��76�w�E\�}i�Xy\��ͩϧ0�J6\�\�e\�h~7*�׼\�5\��g�}vc�\�\�Ʒ�\��@\�߇~xʣh�d+ٝ��PUP\Z\���0\�\�ڙ��\�4��y\�c���g�\Z1�\�O7�\��>��\�45�|	}��\��\0��\�Ջ⍦\����W*�Q�Չrx;�k$\�\r2��k��=�\�٧Q!z�wy�T�Oa|�,\\�\���Ņ�~�1\\�R5!���c\�˩}i\���<W\��^4�o\�\�>\��\��DL��x��L�SY\�l�9�H\��糟�lc�n��\�%zZ\�\�\�\��t,T�\�\���s\��^v\�e�e�\�d-�b1ٍrم\"\�\�ޟU!����������F+\"!=j4�\��Ӵ\'�ϥ)��\�F�\�\�8#�\��*/3�^ן�v�<oN]bF\�\�*�%T�\�U\�\"\�Yن\�\�?\\���L9<�\��\�i\�\�w��(\"\�\�\�2\�!H\�\�\�(��\��X\�9ꨣRܜ^v���\�\"_��\�\�\�!_�\�肧�I�\�\�ۤ�F��~�&\�9s\�<]q.\�x\�;RL���L�\�5\���{�l\�]v\�%\�[�wm��X�wz�\�\�\�\'pWiT\�t�S\�\�\�gi�:�\n^:�\0qG\�7x�V�W�\�\�eU��:�\�fDAT�\�e/{Y��\��\0o����TV�It�q\�\�\Z\�\0Je�Ui\�8\�*�\'\�	\�E�1�_\��?�\�\�_���\��(�\�es�	�\�ʿ\�4\�h\�\'p\� ߈m�\�.o��FeD\�\��t�~�\�y�\n�>\�\�-\\\�ml|\�R��\n�?�2\�*��\�l��U�\�\'sK�%��\�%?�3\�o~\�0�\�\�\�gS&��Vb�\�JE媗�i���$N\�Χ\�\�Jdw���8����!-?t�s���x\�g�\�\0\r,�\\\�~)S�sD�\Z\�e%��\\�Y�F+u\�6�Ԃ�\�Z��\�l<\�Aof~\nިD��\�%\�\�O.�8�~\��.p��\�\�HSC���\��qj��Ґ6�XQ���r\�8?�\�ec�.xhȟ�*|ɏ8\�\�[Dy|\��\�#�cQ�S��g֤l^mc�56f,\�eBf(ʗ�b�l\�<�a\�I߹\�z\�M\�\�xAl��\���\�{\�|g!}�:�\��\"M\r\�Bω�Ŏ��\�4�\�\�s\�X\�	L٠�+�\r\�(uKN{��D\�\�o�\�g=~\"M\ra��\�\'Y?�\�پ%%Fۮ9hL\�y\�:\�tQY�I�={\�\�\��ް�p\�%��;\�s\�\�5\��ޔ\���*�XZ�\�\�����\�W�Q�\�vn�\�6��B\���8/�W	]�T9H�\Z�t\�N\��N���\�\�#y\�\�Vݾ4\�\rN]\�6�\� tm�_ir�\�\�#��>o\�\�\�n#�\�D��\���w\����:\rV]�us�\n�:V���l\�\�]\�&ȶnO\�݆]\�suJul�$��nI�\�\�6\�4\�\�\�\�9\rQ\�/\���\��[�HSCX�\�*�2�\�{2�_\Z^j�\���ǹ \�7\�f8\�6\�>2\�W�U�|ǀ.�g\�\��|]\�Ħ|��\'� �\�z\�q:�\����\�\�\�d�#@`3U\�\�c\�Y�\�b<s\�\�\�\�\�\��c\�\�T�\�3�]�.P��>�\�\��G\�GZI���\�#\��\�K��6�\�\�\nPvp�-\�v�\�Ku�6�G��\�P_F�$\"M\r\�<�H+\��\�\n�\�\�(�WO,-\�8U�\�\'o��C\�X\�F�����GN�� �<�\�:�裓�O-8餓���\�\���\�o\'3V�\�;,\�j��\'\"\�\"_\�_\�\�W:c:ߣ~\�	�m��_���/�\�-\� v�\�\�\no�\��?��hrZζ\'�@�`��M\�}��\�\�\rR��\n�\n~(W�\��\��\�,�n=8ˇ���]�\�ސg\ZK��)ߝv\�)�:�!*�i`���-�tPЇ��\�v\��\�\�\�6ټ�\�N\�V\��\n��G\�#�6A\�Дo����O|��\�\�\��9���\\#A\�q^���������\�\�W��\Z�\n��G�E\�\�\n}_\Z\Z�\�\�;\�\�٦\�\�OlRF\�y\�\�\�\r(�O\�\�\�\�D\�\�c!&�\�<��\�kμ,\�G�\�sjf> ��|e�l,^��\�\"�#h\�U\�\"M\r\�<7X�\�\�`�z�Y��x̜�\�P�x\Z}\�l�\�l�벹ij�\�3T�O�\n�\'#�Ϟ+\�kU\��k�\�`�/\�^��\\�.U\Z�\�?\�*ͬ\�u�x\��K�g)����^�an���ӫ4.x\�������\�\�Y�\�P횆U]�\�H\����\�\Z����\�\�	q�nI\'\�o�ׅ�Fk\��]Ս\�>X\�\�\�\�߅K\�����\�\�\\�\�a~_\"t\�\�Y\��t}��f��	(���:��e�Fb\n\�\��\�\�;�\�ڗ�s\�\�\�\�9Ki�\�v\�]���ѷ\��&x�*\�?F\���*5-�S\�T/<\�#��D�\�Ū�P�\�\��\�\�\Z\�m\�\�,\�\r҅�7�~�1Y�A\\=:\�\�ƹ�~\��j��\�肏PjG�O\��\�E��\���+�xjA	U\�ݸ�3둓����v\�\�d׀�����/�~���#���um\���T?���\�\�#Ď5k\�L�!�\��=�3�\�_>�	\�a\�|9����p!���\�HSCX_��ij�\�+�5\"M\ra(��\�׆γ��b\"M\ra(�u\�ׅΕ�[:C�_�\�\�й�rKg(�\rK�EΕ�[:C���7\\f��ܒY�������[2��\�)_�\�8U\�`݆\�cS�r\�*�\�jզr\�U\�\0\�\�\'}\�J`\'��N_����\��7U��[*{^`p�Y�!\�s\�Me\�\�\�̂��\�Q_��P_��0\Z�-�Cb\�\0\0\0\0IEND�B`�'),(2,'2025-03-24 20:33:17.543388','2025-03-24 20:33:17.543388','hello',_binary '�PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0�\0\0\0�\0\0\0`\�V\0\0\0sRGB\0�\�\�\0\0\0gAMA\0\0���a\0\0\0	pHYs\0\0%\0\0%IR$\�\0\0$�IDATx^\��eU�b/ `��`#!\ZQ��Tl\�hk\�((`\�\r\r*��D1b�\Z�`\rE�{�����X`�f�ﻜ�g\�=��\�{\�p߼�Ya޿��\�S\�=g\�s�Q\�mX7�j�U��+�S\���\�L��[*��_�\�_���rKfQ\�\�\�^�\n~\�\��se\�\�P\�\�n�6t��\�\�\n~\��\�3S�2\n~\����\�,���HSC\n>r��Cij�\�+�5\"M\ra|ŲF��!���Xֈ45���\�\�~\���\��?�a��\�_\�\�$et\�N�s\�9���\�O�\�&eB�i_\\v\�ea��.��\�؈.� ��[����&\�\r\�W\\\�D݈HSC��sr7��\r\�\�\�&M\�v�\�moc̓�\�;yn{\�\�6�v�\�u�ۤL\�5\�/|\�a�w��ݍ\�F<\��M\��\�_\�h\"\�P�\�\'?i�nD��!���3�\�g\��N;\�4z\�3�ٛ7�\�M\��\��\�?�q\�\�O~ŗ=\�i�ܽ\�\�+\�\�~�\�=\�V[m5QnDb\�\�O�EeG�эn4\�\��׿��ҿ��\�m��\�\�\�o|c*�k�r�\�\�\�+�\����\�|��k2�+^\�\���g=+���\�kJ��\�6\�L\\���\�]\�b�\n�sҳ\0���M\�\�,�\��\�?\�x�F�~\��C\�\�\rB,��\�97�U���.���\����o�\\����2��\�|\�\�\���bT�OA|�,\\V�\�\�\r-\�\�/n<c�\�\�\�\�j\�P\��\�-oyK\�y\�ސ\�9I<�)��\�XG��\�G\��\�o~s\�\�c�=R\�_�\�W�|\�\�җ�4�\��\�\�\�8\�(>\�ٽj\�W�*�\�w�?��N��^�\�OO�ʏx\�\�6��\\\�\\\�#���\�\��v�<Nn�\�\��0ٿ�\�o6\�\�\���\"P�|\�x\�\�W�\�\\~�\�a�ȱ\n���\��ʜ]�򗿼\�Rl�\�߈\�(��o\��\�\��r\��\����\�\��׽�\�\�\��d�\�?�\�D\���\��S��}\�k�uTo\�\�zCds�\�E/\Z��\�g?;\�]\�z\�M�\�<\�\�&��\�\�\�w\�t\�\�z׻6\�\'�|\�\�M\�\�\�w���X\��ַ\Z���x>@��U\�\r#\\��\�c^�lb�\� {^��\��&5F\�\r\�5킒\��� K\�\n�a�\�C\�4X9��\�\�\��/y\�K\�\�\��.��<\�)c\n�\�\�\�7�\�Moj����ew�!���<$L��_劷�\�mB_����{�}\�I�\���%\Z\��5\�-��\�\�?��)��i�{L�\�T�7�p}>�c{|^͂wK�\��\�%>�d\�\�\��ArN\�,*��\r\�S\�0B�쬂\�e%x�\�9\�׬Y\�x��V��ո>\�|�\�_�b]z\�M�I�\�\�-�\�\���\�~�\�)\�^\��\�s\�1ǌm{\�w�}\�K_J�?�\�O\'_\�\�O~rJ\�T\�I\'��\��-�\\\�LaP\'�i��x\�\�\�|�\�\�y\Z�:����\�?\�U.�>h|D\�t�\�sv�Oφu�\� �\�\�\�\�Y&w!6�\��>\r\�s�\�A|GT�W�\��Y���/�n��$\�G<\�\��_�\�;\�\�+_�\�\�\�\�.ɟW\�%�\\��x\�\�!\�oV�o�\�\�w��\�ˤ\�8\��qYT]t\�\�u\�y\�s���3\�H\�\�v\�-\���E#���\�\'�SO=5�q\�\��\��8�K\��\�\�J�=NJ\�C\�b�\n�eI\�� :\�\\\��Tv\�\'�\�=\�쌄B�M�@\�g�n67^p�\�	\�?\�R��k*\� b�͏V\����\�o\�%�Y\� }�b\�<qn\�<\�<l!��>\��\�\��=\�\�d\�\�(t<y��S�2!L�m�\�\�\�ϓ^��\�8��\�U\�3�\�\���\�}/\�yc\�\�<O{姑\rJ��\�<�ӟ�������p!�\�\\�\�]\�(uK�	>B$x��AJtA����P�\�F��!��\�P�\n�##\\u\�U\���\��7\�\�\�\�ޕ,X·(\�ӟ�\�qz)>C\�r�_s\�5)\�\��\�k4^F�X>�\�<��\�\�\�.&f)_D�\�\�$1�s뭷N�\\�\��\��\�gPY\������\�\��$\�<~_\�\�HSC����%KF\�~Ԩ,\�\�\�\���\��\�\�\�H\�%\\�Jqh�>#�����\n>C|;�\�0\�\��\�q�2,�x\�\�G�\�]�\�\�>��:	^㤗\��|v|\�CUH>Tg6lؐҜ�Ї�\�}\�s�t3\�q�{${5�\�dʂ\�\�\�\��\��\�\�h\����,\�\�{\���.H/�4�\�\�\�\�9O;\�Tn��nV�2\�\�\�\�v�d{�\�ߞ\��4����p\�\�R?��\�%>7A>\�1\�hu�G(\������Ǆ�F�\�\�\�\�\�\�\�۞�\�\�����\�\�\�\�_B��!\\�\��Us!\�\�;�q\�\�Oa�|f᷿�\�q����<\rM\�X�~�\�\�k�M���9\�\�Kv\�\�N#:�R���y|�9}\�J׾1�5��_�bi4z\��ޗ\�N\��)�QW��|zV�\�~�\�7ޣ\�\�8�?�Yh._\�J�X��s`\���\�9G�F\�ij�U\�\�#��П�mp�;�\�\�\�u�6�wF\��\�D�nI\���Hn\�*��$x\�i����_W�\�y�\"\�\�.*uT��=h|��ԧ&\�>d\Zq�\n\�\��\�~\��M�n�ۧ� �\�(�ւ�c�K�+\Z*�ϸ�\��|z�\�\�F\�g\�q\�7.�\�@v\�\�\�\�\��\�GK:\�L8\���P<�V\�\��_m<\'\�\�\�\'�	���p\�V\�/lkԔ A�\�\�w햄��4�\�tD�\�FZK#�]\�69\r�\�\�>�V�$�\�\r\�_�\��\�\�sD�\Z�\�ꨐW77�\n�B�Z\�}\�^3䧇C6U� \�e�\�/~\�\�h<G\\\��,H��I���z�!�+�|\�)\0s\�$=6��\���M\�З\�áʧwIi\��\�\�\�A\���\�oq�[�c\\\�\\sŊ��j�SG\�\�S����on\"\���\Z��\�y�E>9KO`�k1+\�X}\�÷Qp�{?|�*�\�\�\�\�sD�\Zާh\�\�\�\�\��\�\�\r�����}l�<�\�_\�*\�\��๩:>zS\�0\Z�\���o|\����H\�^\�r0\�|N�%\nĿ\�-o�b�}\�\�Mʤ\�KC�l\"E:\�z\�\�x\�;�|\�\���\�\�;Ｆ��`:�|)K�W�\��y�M\�\�<Q�	���!�\�\�\�u�\�\�F��\�\�)\�A.x�7Z�~�\�a�m\Z�Q쬂�\�*�IT�\�\�\���\'<a,$X�\�肧�@\Z�H\�\�>\�/�\��p�\"�_�\�WL<>\�Q�<>8% @�cD_�\�g�1�m\�@\�Q�\�\r�fG\�\�z.x_\0r\�QG%�C��O�lT��Oy�*)�\��nJ\�F�\�KYʿ\�;�|��\�/45��?���<����\�x\��ɻ\�o뇏��?jTz�\�\�ק.t��\�\Z�\�\�\rij�ৰ\n��B|\�.x^��+s35�)\�\n�.�\�Y[��Lq\��9Y\0�X\��\��\�G>2\�y�ʮm4�Ƈ̽�Ё\�^E\��*_�YB\��$\�>\��d\����^,;M��E+1y8(�\�ÕG�̈́~�m�}�w\�1ٕ�A2�s,�s\rr�j�;��\�<t.7]����*\�<b�/{����=���\�(.\�*踡\���Ϋ���\�\�>�/\�K\�\'Oiߛ.\�GA�Ŀ\�{#b|ߡ*�%t�zQx\�\n��\�<\\T��\�\�\�E���*�\�@4\�\�tg�*M�^�\�\�W\�~�\�*��U\�U\�9\"M\r\�BO7����LZB��\�\�\\��\�\�\��<Tc��n<�GdQ�|}_\Z\"{N� :>�Ի\�\�ä3\�+\�]\�Q�\�P�\�FɏR\�Q~�-�\�L`4ٯQN�\�*�\���\�|��~UtK�\�ҔO\�\�\��H�0m`�\�\�a�OC_���S�h\�\�\�4�+�\�|\�$\�\�V�/�\n�\n~nl[\�Qǅ\�}\�sӅ�Ժ\�\�G<�=ݱ}�\�_O��>9\�Y<7Ky\�1��E\��\�;\�\�ɏj\�4L���4yLK�l\�3\�\�s\�=S�<F�\�~\��\�_\�U1\��`V��L\�!��\�\�n\�.xF�\�˿\�˱\�45��On�7j�����%�\��\�y�\�銶�>\�\��}V<\�����`V�G�:%\\\�\�Ձ+�\�ꨂ���Ϫ��\�1�!;p\�!4 ��G�\Z�s�\�į$x\�*C��\�s]\�\�\�(OtӨ\�(�\�^\�\�K���\�?\'_6Z\�U���\��\�?\r\����|e\�cbE��򗿜|\�\\I1d;\�S\Z\�I�k�%_\'��9*ǖ#\�\�.[�s�?�j��@��\�\\\�.x\'\rb�7Z]\�Ē�2\�R|\�M\���c�\'<m�{�X6���ޯ�,�45�U\�K���d�\0F\���|�!^�\��<\�\�#�z3��s#T�W=J�����\�g9�\�\�s\����\�o}kSB�U\�9�M�d/1*Od$�K��\�\�A�A.x�HJ�\�\���b�fO��&G��!\\�\�KO\0.�\�m�\�\�.O��\�7�]\�N\�71\�FoT\�Qy\��\�]\�]\���N�U\�_?\�*�2\"M\r\�\�\�\�D	\�\�c�do<\�\\\�5\�Ⴇ\n���\�y\�{R�\�\�~��.��\'B�|�\�,m\�݆.\�e\�es\�1\�9�v\�<x\�\�\',��\��\�M�\�\�\�v�\�g*�\�;�\�S���+ߪ|�m�\�hu\�+0�\�Ӻ\�I\�8�\��Q�\��\�K\�F����x�\�y\�\�\�)!\�\�.D\���\�\��m�\�\�\�TEJԾ1<�e���J�\�pc\�Ð?\��\�O?�Ҝ������\�Q�\�e��>u���e\�\�t�\�\�0<���{Dގ\�\�\�?\�d�\�\�n�\�\�J\�\�\�\��\�.�\���\�,7\"\�\�.D\�%̳\��Be�~-P\ZA#�\"O�����\r8�(�+Km\�mul�mr\Z��?���VG|\�*��q!�\�\0D4\\\�|I\�}H$x>v��J�\��w�X\�\�\�=���\�\�\�#\�5�\�\�1F����!c\�\�.x\Z\�:\Z\�y��^xai\�\�m�h���\�:\�0�\�4���C9G��X��\Z��\\\�\�qp\�	��\���ǟ�\\\�m\�Qɹ\�p�;�Q\�<���Ԩ�8�Q��z�A���ij�\�3V�W�b� L`\�\n\�\�.�\�a\�%ul����`\�\�\�B\�\�\"ߗƷ\�+	�� \��\��\����F$x\�\�Iόʻ袋\Z\�M�����4.x/\�\�]\��\�\�tD�?\�\���\�\��|*˯�ij.�Y�\0\�|x�\�\Z�\�\�\n%�����^\�\�Wo�7*g\�w�;���_6i�\�/}��9WE?|I\�|͎>z�nݺ��$x�(ş�.x�\�Io�nL��!�a���9ٙ׮\�F\�r,:.�\��ӱM\�~��잦\�\�	\\pA\�\�Oװ��b�]��\��\��~.x\�\�\�_>�LL\��\�o�\��\��\"D�\Z\�e%��nI|�\�\�\��[2\Zi\�\�\�\�>Ӄ#���\�/!\�\�V�\�U𓨂\�P<_�c`2l\�\�\�w\�}�/��\�J\�>\�Y\��lŋ\ZQ}\�\�\�Xu\�\�m�]\�{\�\�qq�y�\���%�K/�4�����\n�\\&�ɇ`	k֬I~LYP,�W�\�Z��\��F�\�ww`��\�\�|\�;\�|[�HSC�l�Խ\�3g<\rSŘ&�Y�}(���\�\�\�h<~F\��\�\�ɱ\�45�U\��\�cT�wd�>�\�3�KB_\�	�\�\r\"\�Bz���\�!��>b� \���\����\r�t\\.xD\�ǑS���\�qc;\���͹\�^{�\�b��(�Y�:vXP\�  {<\��Ƿ�P�\�\���a�\�/�Ce)�ӿ�(D�\Z\�\�\�Q�=J\�\'p\���\�N|�7\�vorM���\��Ⱥ�$x��J�.��AJ�45��\�-��\�y>�.�\����|\�\�c?21\�L���\�`\�c\�\�wP;���\�HNbD\�\�\�+n�\�v)_!�\��\�c*�s\�Eߗ&\�7F���4ߗ��\\O\�\��{���2x`J\��\�O\�\��ڮ�45��\�\�n\����\�X\�7Hh$��\�\��[䟓s\�.m�\�\�i�`�\Zyz��7�\�g��vK6��oG�0.D\�o	\�.�\�m\�\�\�;.�\"\�kS��44\�\�\�\�:\�7-{�?_\�\'{i\�h\�bb(�&o�\���\�\�\�\'S\Z�\��\�h�\�w\�Md�@q}_\Z\'�h�3e\0\�jR>�}�\���t&\�)��\r{\�\�\�{SB��!\\�\�\�	\Z~�\�>�+\�o�\�\�iݒ��\\\�F��\�GZ\�q�{�R\�\�\�\'\�4D\�s���m\�3�4pM\�\�F�wKF=I�HSCX�\�*�IV�`�\�!7.\'=�*�ll\�\' l��\0^�\�e\�`\�\�\�\�\�\��\�3�^y�\�\rgy�\�\�W�\'>\�ɇ\�\�E7�\�\'>1�}�\�O6f^\�WU(<S\�{\�\�&_�\�\��\�\�&>�\�O\�\��>0\�\�\\\�.>9/{W\�<�\�*\�\�ij.x]��j��\�\�\�j���\'�ǧ\�\��4Z)Cv\�\�\�\�5*�\�\�~\�i�V�\�\�{�r(�\�\�\�W�45�U\�SP?�\�30\�\�E��\�k:az��/�x\�\�\�dc1�\�q\�\�.�L\�H𼞕�\r�n�\��\�E�C|\�\�\�,ȝ\�t��߫\0�ǧWJ1X���\��Qe�/\�\�M�ϵ��\���\�3����\�\���_�V~�dJ�Eq�ja\�i�;\���\�\�c#\"M\r\�B\�\�\�8۞�>\�\�S1GԨt�;��_ٽQ	J�\�.S$�R���\�.䚖\�\���\n��\�͇ \�\�V�/�\n�\n~n��+�z&dWZ]zY�s\�:ؐH�v\�m�q���\�W�<NzPT\�i���\�\�\\s\�8 \��;\�b��Sy:蠔�\��8Y�0->�`\�5���\�ۇ앩c\�^\'�fV�����ؘJvfq\�\�\�\�k\"NB\�\�\�~?\�L\�\�\�\�7B��!\\��V�\�O�F+7P�_\rA�N�-)���������\��\�\�1��7�\�o�Y\�ij�\��X\�\�*��lC\�qF\�V}\�#�^�\�\\��JY.x\��\���\���K�GZ�>?(|�\��F\�K�45�<\�X\ZR�\�\�\�t�3\�]1�\�\�	\'��%E$x\��A\�zCA6vU�>\��<6\�!xK)�\�׿�u\�J�Ұ\�\�\�	/\�UHV_ɗ	_�/\�V[m�\�\�ͪtF�e�?���\�v��45�|����.x.6�Ҵ�4��D\r͊H\���\�\�6\�\�T/o\Z�zi�Υ\�*M[/\rey^1z�G�\Z\�*�)����\�\��76�w�E\�}i�Xy\��ͩϧ0�J6\�\�e\�h~7*�׼\�5\��g�}vc�\�\�Ʒ�\��@\�߇~xʣh�d+ٝ��PUP\Z\���0\�\�ڙ��\�4��y\�c���g�\Z1�\�O7�\��>��\�45�|	}��\��\0��\�Ջ⍦\����W*�Q�Չrx;�k$\�\r2��k��=�\�٧Q!z�wy�T�Oa|�,\\�\���Ņ�~�1\\�R5!���c\�˩}i\���<W\��^4�o\�\�>\��\��DL��x��L�SY\�l�9�H\��糟�lc�n��\�%zZ\�\�\�\��t,T�\�\���s\��^v\�e�e�\�d-�b1ٍrم\"\�\�ޟU!����������F+\"!=j4�\��Ӵ\'�ϥ)��\�F�\�\�8#�\��*/3�^ן�v�<oN]bF\�\�*�%T�\�U\�\"\�Yن\�\�?\\���L9<�\��\�i\�\�w��(\"\�\�\�2\�!H\�\�\�(��\��X\�9ꨣRܜ^v���\�\"_��\�\�\�!_�\�肧�I�\�\�ۤ�F��~�&\�9s\�<]q.\�x\�;RL���L�\�5\���{�l\�]v\�%\�[�wm��X�wz�\�\�\�\'pWiT\�t�S\�\�\�gi�:�\n^:�\0qG\�7x�V�W�\�\�eU��:�\�fDAT�\�e/{Y��\��\0o����TV�It�q\�\�\Z\�\0Je�Ui\�8\�*�\'\�	\�E�1�_\��?�\�\�_���\��(�\�es�	�\�ʿ\�4\�h\�\'p\� ߈m�\�.o��FeD\�\��t�~�\�y�\n�>\�\�-\\\�ml|\�R��\n�?�2\�*��\�l��U�\�\'sK�%��\�%?�3\�o~\�0�\�\�\�gS&��Vb�\�JE媗�i���$N\�Χ\�\�Jdw���8����!-?t�s���x\�g�\�\0\r,�\\\�~)S�sD�\Z\�e%��\\�Y�F+u\�6�Ԃ�\�Z��\�l<\�Aof~\nިD��\�%\�\�O.�8�~\��.p��\�\�HSC���\��qj��Ґ6�XQ���r\�8?�\�ec�.xhȟ�*|ɏ8\�\�[Dy|\��\�#�cQ�S��g֤l^mc�56f,\�eBf(ʗ�b�l\�<�a\�I߹\�z\�M\�\�xAl��\���\�{\�|g!}�:�\��\"M\r\�Bω�Ŏ��\�4�\�\�s\�X\�	L٠�+�\r\�(uKN{��D\�\�o�\�g=~\"M\ra��\�\'Y?�\�پ%%Fۮ9hL\�y\�:\�tQY�I�={\�\�\��ް�p\�%��;\�s\�\�5\��ޔ\���*�XZ�\�\�����\�W�Q�\�vn�\�6��B\���8/�W	]�T9H�\Z�t\�N\��N���\�\�#y\�\�Vݾ4\�\rN]\�6�\� tm�_ir�\�\�#��>o\�\�\�n#�\�D��\���w\����:\rV]�us�\n�:V���l\�\�]\�&ȶnO\�݆]\�suJul�$��nI�\�\�6\�4\�\�\�\�9\rQ\�/\���\��[�HSCX�\�*�2�\�{2�_\Z^j�\���ǹ \�7\�f8\�6\�>2\�W�U�|ǀ.�g\�\��|]\�Ħ|��\'� �\�z\�q:�\����\�\�\�d�#@`3U\�\�c\�Y�\�b<s\�\�\�\�\�\��c\�\�T�\�3�]�.P��>�\�\��G\�GZI���\�#\��\�K��6�\�\�\nPvp�-\�v�\�Ku�6�G��\�P_F�$\"M\r\�<�H+\��\�\n�\�\�(�WO,-\�8U�\�\'o��C\�X\�F�����GN�� �<�\�:�裓�O-8餓���\�\���\�o\'3V�\�;,\�j��\'\"\�\"_\�_\�\�W:c:ߣ~\�	�m��_���/�\�-\� v�\�\�\no�\��?��hrZζ\'�@�`��M\�}��\�\�\rR��\n�\n~(W�\��\��\�,�n=8ˇ���]�\�ސg\ZK��)ߝv\�)�:�!*�i`���-�tPЇ��\�v\��\�\�\�6ټ�\�N\�V\��\n��G\�#�6A\�Дo����O|��\�\�\��9���\\#A\�q^���������\�\�W��\Z�\n��G�E\�\�\n}_\Z\Z�\�\�;\�\�٦\�\�OlRF\�y\�\�\�\r(�O\�\�\�\�D\�\�c!&�\�<��\�kμ,\�G�\�sjf> ��|e�l,^��\�\"�#h\�U\�\"M\r\�<7X�\�\�`�z�Y��x̜�\�P�x\Z}\�l�\�l�벹ij�\�3T�O�\n�\'#�Ϟ+\�kU\��k�\�`�/\�^��\\�.U\Z�\�?\�*ͬ\�u�x\��K�g)����^�an���ӫ4.x\�������\�\�Y�\�P횆U]�\�H\����\�\Z����\�\�	q�nI\'\�o�ׅ�Fk\��]Ս\�>X\�\�\�\�߅K\�����\�\�\\�\�a~_\"t\�\�Y\��t}��f��	(���:��e�Fb\n\�\��\�\�;�\�ڗ�s\�\�\�\�9Ki�\�v\�]���ѷ\��&x�*\�?F\���*5-�S\�T/<\�#��D�\�Ū�P�\�\��\�\�\Z\�m\�\�,\�\r҅�7�~�1Y�A\\=:\�\�ƹ�~\��j��\�肏PjG�O\��\�E��\���+�xjA	U\�ݸ�3둓����v\�\�d׀�����/�~���#���um\���T?���\�\�#Ď5k\�L�!�\��=�3�\�_>�	\�a\�|9����p!���\�HSCX_��ij�\�+�5\"M\ra(��\�׆γ��b\"M\ra(�u\�ׅΕ�[:C�_�\�\�й�rKg(�\rK�EΕ�[:C���7\\f��ܒY�������[2��\�)_�\�8U\�`݆\�cS�r\�*�\�jզr\�U\�\0\�\�\'}\�J`\'��N_����\��7U��[*{^`p�Y�!\�s\�Me\�\�\�̂��\�Q_��P_��0\Z�-�Cb\�\0\0\0\0IEND�B`�');
/*!40000 ALTER TABLE `security_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store_cart`
--

DROP TABLE IF EXISTS `store_cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `store_cart` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `is_active` bit(1) NOT NULL,
  `current_user_email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKkfv5yco13u9to4qe86anbdga4` (`current_user_email`),
  CONSTRAINT `FKatwlhrjpy77ogc5w874eedr60` FOREIGN KEY (`current_user_email`) REFERENCES `app_user` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store_cart`
--

LOCK TABLES `store_cart` WRITE;
/*!40000 ALTER TABLE `store_cart` DISABLE KEYS */;
INSERT INTO `store_cart` VALUES (1,'2025-03-09 12:13:46.769561','2025-03-16 05:23:58.855572',_binary '','shantanuyadav117@gmail.com'),(5,'2025-03-16 05:24:06.385664','2025-03-16 12:03:23.287618',_binary '',NULL);
/*!40000 ALTER TABLE `store_cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_cart`
--

DROP TABLE IF EXISTS `user_cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_cart` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `is_active` bit(1) NOT NULL,
  `store_cart_id` bigint NOT NULL,
  `user_email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1ldck9qk0bw74bd0uatg53xy1` (`store_cart_id`),
  KEY `FKly86uap1b119dpyxkbabdp6jd` (`user_email`),
  CONSTRAINT `FK1ldck9qk0bw74bd0uatg53xy1` FOREIGN KEY (`store_cart_id`) REFERENCES `store_cart` (`id`),
  CONSTRAINT `FKly86uap1b119dpyxkbabdp6jd` FOREIGN KEY (`user_email`) REFERENCES `app_user` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_cart`
--

LOCK TABLES `user_cart` WRITE;
/*!40000 ALTER TABLE `user_cart` DISABLE KEYS */;
INSERT INTO `user_cart` VALUES (1,'2025-03-10 04:46:51.380366','2025-03-10 05:51:40.202120',_binary '',1,'chaturvedishivam598@gmail.com'),(2,'2025-03-10 05:51:40.285549','2025-03-16 05:23:58.809813',_binary '',1,'shantanuyadav117@gmail.com'),(3,'2025-03-16 05:30:29.721009','2025-03-16 05:30:30.228779',_binary '',5,'shantanuyadav117@gmail.com'),(4,'2025-03-16 05:30:30.263670','2025-03-16 12:03:23.275781',_binary '',5,'shantanuyadav117@gmail.com');
/*!40000 ALTER TABLE `user_cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_transaction`
--

DROP TABLE IF EXISTS `user_transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_transaction` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `amount` double NOT NULL,
  `currency` varchar(255) DEFAULT NULL,
  `receipt` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `user_email` varchar(255) NOT NULL,
  `user_cart_id` bigint DEFAULT NULL,
  `refund_status` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKa4m1xklfg3n69gywiitvbl7pg` (`user_cart_id`),
  KEY `FKakc6gbq2dw8k5g6wllys17yls` (`user_email`),
  CONSTRAINT `FKakc6gbq2dw8k5g6wllys17yls` FOREIGN KEY (`user_email`) REFERENCES `app_user` (`email`),
  CONSTRAINT `FKjbfb5pski83hirvy06i0q4rj3` FOREIGN KEY (`user_cart_id`) REFERENCES `user_cart` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_transaction`
--

LOCK TABLES `user_transaction` WRITE;
/*!40000 ALTER TABLE `user_transaction` DISABLE KEYS */;
INSERT INTO `user_transaction` VALUES (1,'2025-03-10 06:26:00.611044','2025-04-05 17:43:56.446243',2379,'INR','REC-8638633c-ed2d-44d9-ad11-978882564a9e','Pending','shantanuyadav117@gmail.com',2,''),(2,'2025-03-16 05:31:10.493879','2025-04-05 18:09:23.001558',9385,'INR','REC-d7a3a551-421e-4ab0-b584-2ece383e31b5','Completed','shantanuyadav117@gmail.com',4,'Refund Initiated!');
/*!40000 ALTER TABLE `user_transaction` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-06 12:11:16
