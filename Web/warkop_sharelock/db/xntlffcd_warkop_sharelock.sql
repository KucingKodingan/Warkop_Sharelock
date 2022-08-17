-- phpMyAdmin SQL Dump
-- version 4.9.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Aug 17, 2022 at 12:49 PM
-- Server version: 10.3.35-MariaDB-cll-lve
-- PHP Version: 7.4.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `xntlffcd_warkop_sharelock`
--

-- --------------------------------------------------------

--
-- Table structure for table `jenis_menu`
--

CREATE TABLE `jenis_menu` (
  `id_jenis_menu` int(11) NOT NULL,
  `kategori` varchar(20) NOT NULL,
  `jenis_menu` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `jenis_menu`
--

INSERT INTO `jenis_menu` (`id_jenis_menu`, `kategori`, `jenis_menu`) VALUES
(1, 'Minuman Ice', 'Coffee'),
(2, 'Minuman Ice', 'Non Coffee'),
(3, 'Minuman Ice', 'Fresh Series'),
(4, 'Minuman Ice', 'Tea'),
(5, 'Minuman Ice', 'Regal Series'),
(6, 'Minuman Ice', 'Moctail'),
(7, 'Minuman Ice', 'Banana Series'),
(8, 'Minuman Ice', 'Noddle Series'),
(9, 'Minuman Hot', 'Coffee'),
(10, 'Minuman Hot', 'Non Coffee'),
(11, 'Minuman Hot', 'Tea'),
(12, 'Minuman Hot', 'Regal Series'),
(13, 'Minuman Hot', 'Banana Series'),
(14, 'Minuman Hot', 'Noddle Series'),
(15, 'Makanan', 'Makanan Ringan'),
(16, 'Makanan', 'Rich Series');

-- --------------------------------------------------------

--
-- Table structure for table `menu`
--

CREATE TABLE `menu` (
  `id_menu` int(11) NOT NULL,
  `nama_menu` varchar(60) NOT NULL,
  `kategori` varchar(20) NOT NULL,
  `jenis_menu` varchar(20) NOT NULL,
  `harga` varchar(12) NOT NULL,
  `gambar` varchar(50) NOT NULL,
  `kapasitas` varchar(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `menu`
--

INSERT INTO `menu` (`id_menu`, `nama_menu`, `kategori`, `jenis_menu`, `harga`, `gambar`, `kapasitas`) VALUES
(2, 'Green Tea Hot', 'Minuman Hot', 'Non Coffe', '23000', 'green tea hot.png', 'ada'),
(3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', 'thai tea ice.png', 'ada'),
(4, 'Thai Tea Hot', 'Minuman Hot', 'Coffee', '12000', 'thai tea hot.png', 'habis'),
(5, 'Cappucino Hot', 'Minuman Hot', 'Coffee', '12345', 'cappucino ice.png', 'ada'),
(6, 'Ice game baru', 'Makanan', 'Makanan Ringan', '12000', 'Ice game baru.jpg', 'ada'),
(7, 'Kentang Goreng', 'Makanan', 'Makanan Ringan', '6000', 'kentang goreng.png', 'ada'),
(8, 'Nasi Goreng', 'Makanan', 'Makanan Berat', '15000', 'nasi goreng.png', 'ada'),
(9, 'Pisang Goreng', 'Makanan', 'Makanan Ringan', '7000', 'pisang goreng.png', 'ada'),
(10, 'Game moccacino buatan mu', 'Minuman Ice', 'Non Coffee', '20000', 'Game moccacino buatan mu.jpg', 'ada'),
(39, 'es kopi susu pisang', 'Minuman Ice', 'Coffee', '18000', 'es kopi susu pisang.jpg', 'ada'),
(40, 'green tea', 'Minuman Ice', 'Non Coffee', '15000', 'green tea.', 'ada'),
(42, 'Burger', 'Makanan', 'Makanan Berat', '60000', 'Burger.', 'ada'),
(43, 'Capucinno', 'Minuman Hot', 'Coffee', '20000', 'Capucinno.', 'ada');

-- --------------------------------------------------------

--
-- Table structure for table `pesanan`
--

CREATE TABLE `pesanan` (
  `no` int(11) NOT NULL,
  `id_pesanan` varchar(11) NOT NULL,
  `email` varchar(50) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `id_menu` int(11) NOT NULL,
  `menu` varchar(60) NOT NULL,
  `kategori` varchar(20) NOT NULL,
  `jenis_menu` varchar(20) NOT NULL,
  `harga` varchar(7) NOT NULL,
  `jumlah` varchar(2) NOT NULL,
  `total` varchar(11) NOT NULL,
  `kasir` varchar(30) NOT NULL,
  `keterangan` varchar(12) NOT NULL,
  `tgl_pesanan` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pesanan`
--

INSERT INTO `pesanan` (`no`, `id_pesanan`, `email`, `nama`, `id_menu`, `menu`, `kategori`, `jenis_menu`, `harga`, `jumlah`, `total`, `kasir`, `keterangan`, `tgl_pesanan`) VALUES
(2, '12', 'munir@gmail.com', 'Munir', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffee', '12000', '3', '36000', '', 'sudah bayar', '2022-08-10 20:04:49'),
(3, '12', 'munir@gmail.com', 'Munir', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffee', '12000', '4', '48000', '', 'sudah bayar', '2022-08-10 20:12:41'),
(6, '13', 'munir@gmail.com', 'Munir', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffee', '12000', '2', '24000', '', 'belum bayar', '2022-08-10 20:13:28'),
(8, '14', 'munir@gmail.com', 'Munir', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffee', '12000', '3', '36000', '', 'sudah bayar', '2022-08-10 20:15:46'),
(10, '15', 'munir@gmail.com', 'Munir', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffee', '12000', '4', '48000', '', 'sudah bayar', '2022-08-10 20:17:00'),
(11, '16', 'kasir@gmail.com', 'kasir', 1, 'Green Tea Ice', 'Minuman Ice', 'Non Coffee', '12000', '4', '48000', '', 'sudah bayar', '2022-08-10 22:22:21'),
(12, '16', 'kasir@gmail.com', 'kasir', 2, 'Green Tea Hot', 'Minuman Hot', 'Non Coffee', '10000', '3', '30000', '', 'sudah bayar', '2022-08-10 22:22:25'),
(14, '23', 'munir@gmail.com', 'Munir', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffee', '12000', '2', '24000', '', 'sudah bayar', '2022-08-10 22:28:07'),
(18, '23', 'munir@gmail.com', 'Munir', 1, 'Green Tea Ice', 'Minuman Ice', 'Non Coffee', '12000', '5', '60000', '', 'sudah bayar', '2022-08-10 22:44:25'),
(20, '17', 'nur@gmail.com', 'Nur', 1, 'Green Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '4', '48000', '', 'sudah bayar', '2022-08-11 21:14:47'),
(21, '17', 'nur@gmail.com', 'Nur', 2, 'Green Tea Hot', 'Minuman Hot', 'Non Coffe', '10000', '5', '50000', '', 'sudah bayar', '2022-08-11 21:14:49'),
(22, '17', 'nur@gmail.com', 'Nur', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '0', '0', '', 'sudah bayar', '2022-08-11 21:14:51'),
(23, '17', 'nur@gmail.com', 'Nur', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '2', '24000', '', 'sudah bayar', '2022-08-11 21:14:55'),
(24, '18', 'nur@gmail.com', 'Nur', 1, 'Green Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '5', '60000', '', 'sudah bayar', '2022-08-11 21:15:10'),
(25, '18', 'nur@gmail.com', 'Nur', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '4', '48000', '', 'sudah bayar', '2022-08-11 21:15:12'),
(26, '18', 'nur@gmail.com', 'Nur', 5, 'Cappucino Hot', 'Minuman Hot', 'Coffee', '12345', '7', '86415', '', 'sudah bayar', '2022-08-11 21:15:16'),
(27, '19', 'nur@gmail.com', 'Nur', 1, 'Green Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '4', '48000', 'kasir', 'sudah bayar', '2022-08-11 21:15:26'),
(28, '19', 'nur@gmail.com', 'Nur', 34, 'Game moccacino buatan mu', 'Minuman Ice', 'Non Coffee', '20000', '2', '40000', 'kasir', 'sudah bayar', '2022-08-11 21:15:30'),
(29, '19', 'nur@gmail.com', 'Nur', 8, 'Nasi Goreng', 'Makanan', 'Makanan Berat', '15000', '4', '60000', 'kasir', 'sudah bayar', '2022-08-11 21:15:34'),
(30, '19', 'nur@gmail.com', 'Nur', 2, 'Green Tea Hot', 'Minuman Hot', 'Non Coffe', '10000', '2', '20000', 'kasir', 'sudah bayar', '2022-08-11 21:15:37'),
(31, '20', 'nur@gmail.com', 'Nur', 2, 'Green Tea Hot', 'Minuman Hot', 'Non Coffe', '10000', '3', '30000', '', 'belum bayar', '2022-08-11 21:15:49'),
(32, '20', 'nur@gmail.com', 'Nur', 36, 'makanan basah', 'Makanan', 'Makanan Berat', '125000', '11', '1375000', '', 'belum bayar', '2022-08-11 21:15:54'),
(33, '20', 'nur@gmail.com', 'Nur', 35, 'Makanan kering', 'Makanan', 'Makanan Ringan', '15000', '2', '30000', '', 'belum bayar', '2022-08-11 21:15:58'),
(34, '21', 'nur@gmail.com', 'Nur', 1, 'Green Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '3', '36000', '', 'sudah bayar', '2022-08-13 00:13:56'),
(35, '21', 'nur@gmail.com', 'Nur', 5, 'Cappucino Hot', 'Minuman Hot', 'Coffee', '12345', '4', '49380', '', 'sudah bayar', '2022-08-13 00:16:25'),
(36, '22', 'nur@gmail.com', 'Nur', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '2', '24000', '', 'belum bayar', '2022-08-13 00:18:01'),
(37, '24', 'kasir@gmail.com', 'Nama saya', 1, 'Green Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '5', '60000', '', 'sudah bayar', '2022-08-13 00:29:49'),
(38, '24', 'kasir@gmail.com', 'Nama saya', 2, 'Green Tea Hot', 'Minuman Hot', 'Non Coffe', '10000', '3', '30000', '', 'sudah bayar', '2022-08-13 00:29:54'),
(39, '27', 'mariomad2296@gmail.com', 'Rio', 1, 'Green Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '1', '12000', '', 'sudah bayar', '2022-08-13 07:51:28'),
(40, '27', 'mariomad2296@gmail.com', 'Rio', 1, 'Green Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '1', '12000', '', 'sudah bayar', '2022-08-13 07:51:28'),
(41, '27', 'mariomad2296@gmail.com', 'Rio', 0, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '1', '12000', '', 'sudah bayar', '2022-08-13 07:52:01'),
(42, '30', 'muhammadjayadi536@gmail.com', 'jay', 1, 'Green Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '3', '36000', '', 'sudah bayar', '2022-08-13 12:30:31'),
(43, '30', 'muhammadjayadi536@gmail.com', 'jay', 11, 'Makanan kering', 'Makanan', 'Makanan Ringan', '15000', '4', '60000', '', 'sudah bayar', '2022-08-13 12:35:57'),
(44, '25', '123', '123', 1, 'Green Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '1', '12000', '', 'belum bayar', '2022-08-13 14:54:18'),
(45, '26', '123', '123', 11, 'Makanan kering', 'Makanan', 'Makanan Ringan', '15000', '2', '30000', '', 'belum bayar', '2022-08-13 14:54:57'),
(46, '28', 'mariomad2296@gmail.com', 'Rio', 1, 'Green Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '2', '24000', '', 'sudah bayar', '2022-08-13 23:28:40'),
(47, '28', 'mariomad2296@gmail.com', 'Rio', 5, 'Cappucino Hot', 'Minuman Hot', 'Coffee', '12345', '2', '24690', '', 'sudah bayar', '2022-08-13 23:28:46'),
(48, '28', 'mariomad2296@gmail.com', 'Rio', 7, 'Kentang Goreng', 'Makanan', 'Makanan Ringan', '6000', '2', '12000', '', 'sudah bayar', '2022-08-13 23:28:49'),
(49, '28', 'mariomad2296@gmail.com', 'Rio', 7, 'Kentang Goreng', 'Makanan', 'Makanan Ringan', '6000', '2', '12000', '', 'sudah bayar', '2022-08-13 23:28:49'),
(50, '29', 'mariomad2296@gmail.com', 'Rio', 1, 'Green Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '1', '12000', '', 'sudah bayar', '2022-08-13 23:32:38'),
(51, '29', 'mariomad2296@gmail.com', 'Rio', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '1', '12000', '', 'sudah bayar', '2022-08-13 23:32:40'),
(52, '29', 'mariomad2296@gmail.com', 'Rio', 2, 'Green Tea Hot', 'Minuman Hot', 'Non Coffe', '10000', '1', '10000', '', 'sudah bayar', '2022-08-13 23:32:43'),
(53, '31', 'mariomad2296@gmail.com', 'Rio', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '1', '12000', '', 'belum bayar', '2022-08-14 00:57:49'),
(54, '32', 'mario@mario.com', 'Mario', 5, 'Cappucino Hot', 'Minuman Hot', 'Coffee', '12345', '1', '12345', '', 'sudah bayar', '2022-08-14 01:43:22'),
(55, '32', 'mario@mario.com', 'Mario', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '1', '12000', '', 'sudah bayar', '2022-08-14 01:43:37'),
(57, '33', 'munir@gmail.com', 'Misbahul Munir', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '3', '36000', '', 'sudah bayar', '2022-08-14 22:25:57'),
(58, '33', 'munir@gmail.com', 'Misbahul Munir', 5, 'Cappucino Hot', 'Minuman Hot', 'Coffee', '12345', '4', '49380', '', 'sudah bayar', '2022-08-14 22:26:02'),
(59, '33', 'munir@gmail.com', 'Misbahul Munir', 8, 'Nasi Goreng', 'Makanan', 'Makanan Berat', '15000', '0', '0', '', 'sudah bayar', '2022-08-14 22:26:06'),
(60, '33', 'munir@gmail.com', 'Misbahul Munir', 39, 'es kopi susu pisang', 'Minuman Ice', 'Coffee', '18000', '3', '54000', '', 'sudah bayar', '2022-08-14 22:26:12'),
(61, '33', 'munir@gmail.com', 'Misbahul Munir', 8, 'Nasi Goreng', 'Makanan', 'Makanan Berat', '15000', '3', '45000', '', 'sudah bayar', '2022-08-14 22:26:29'),
(62, '34', 'munir@gmail.com', 'Misbahul Munir', 8, 'Nasi Goreng', 'Makanan', 'Makanan Berat', '15000', '4', '60000', '', 'sudah bayar', '2022-08-14 22:27:03'),
(63, '34', 'munir@gmail.com', 'Misbahul Munir', 9, 'Pisang Goreng', 'Makanan', 'Makanan Ringan', '7000', '4', '28000', '', 'sudah bayar', '2022-08-14 22:27:08'),
(64, '34', 'munir@gmail.com', 'Misbahul Munir', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '3', '36000', '', 'sudah bayar', '2022-08-14 22:27:16'),
(65, '34', 'munir@gmail.com', 'Misbahul Munir', 5, 'Cappucino Hot', 'Minuman Hot', 'Coffee', '12345', '4', '49380', '', 'sudah bayar', '2022-08-14 22:27:25'),
(66, '35', 'kasir@gmail.com', 'Nama', 2, 'Green Tea Hot', 'Minuman Hot', 'Non Coffe', '10000', '5', '50000', '', 'sudah bayar', '2022-08-14 23:07:42'),
(67, '35', 'kasir@gmail.com', 'Nama', 5, 'Cappucino Hot', 'Minuman Hot', 'Coffee', '12345', '6', '74070', '', 'sudah bayar', '2022-08-14 23:07:45'),
(68, '35', 'kasir@gmail.com', 'Nama', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '7', '84000', '', 'sudah bayar', '2022-08-14 23:07:49'),
(69, '35', 'kasir@gmail.com', 'Nama', 7, 'Kentang Goreng', 'Makanan', 'Makanan Ringan', '6000', '6', '36000', '', 'sudah bayar', '2022-08-14 23:07:52'),
(70, '36', 'kasir@gmail.com', 'Nama', 2, 'Green Tea Hot', 'Minuman Hot', 'Non Coffe', '10000', '4', '40000', '', 'sudah bayar', '2022-08-14 23:11:24'),
(71, '36', 'kasir@gmail.com', 'Nama', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '5', '60000', '', 'sudah bayar', '2022-08-14 23:11:28'),
(72, '36', 'kasir@gmail.com', 'Nama', 5, 'Cappucino Hot', 'Minuman Hot', 'Coffee', '12345', '7', '86415', '', 'sudah bayar', '2022-08-14 23:11:34'),
(73, '36', 'kasir@gmail.com', 'Nama', 8, 'Nasi Goreng', 'Makanan', 'Makanan Berat', '15000', '3', '45000', '', 'sudah bayar', '2022-08-14 23:11:40'),
(74, '37', 'kasir@gmail.com', 'Nama', 2, 'Green Tea Hot', 'Minuman Hot', 'Non Coffe', '10000', '4', '40000', '', 'belum bayar', '2022-08-14 23:13:51'),
(75, '37', 'kasir@gmail.com', 'Nama', 5, 'Cappucino Hot', 'Minuman Hot', 'Coffee', '12345', '5', '61725', '', 'belum bayar', '2022-08-14 23:13:55'),
(76, '38', 'kasir@gmail.com', 'nama', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '4', '48000', 'kasir', 'sudah bayar', '2022-08-14 23:16:03'),
(77, '38', 'kasir@gmail.com', 'nama', 5, 'Cappucino Hot', 'Minuman Hot', 'Coffee', '12345', '4', '49380', 'kasir', 'sudah bayar', '2022-08-14 23:16:06'),
(78, '38', 'kasir@gmail.com', 'nama', 7, 'Kentang Goreng', 'Makanan', 'Makanan Ringan', '6000', '4', '24000', 'kasir', 'sudah bayar', '2022-08-14 23:16:09'),
(79, '39', 'kasir@gmail.com', 'Nama', 2, 'Green Tea Hot', 'Minuman Hot', 'Non Coffe', '10000', '4', '40000', '', 'belum bayar', '0000-00-00 00:00:00'),
(80, '39', 'kasir@gmail.com', 'Nama', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '2', '24000', '', 'belum bayar', '0000-00-00 00:00:00'),
(81, '39', 'kasir@gmail.com', 'Nama', 5, 'Cappucino Hot', 'Minuman Hot', 'Coffee', '12345', '2', '24690', '', 'belum bayar', '0000-00-00 00:00:00'),
(82, '39', 'kasir@gmail.com', 'Nama', 7, 'Kentang Goreng', 'Makanan', 'Makanan Ringan', '6000', '2', '12000', '', 'belum bayar', '0000-00-00 00:00:00'),
(83, '39', 'kasir@gmail.com', 'Nama', 10, 'Game moccacino buatan mu', 'Minuman Ice', 'Non Coffee', '20000', '2', '40000', '', 'belum bayar', '0000-00-00 00:00:00'),
(84, '39', 'kasir@gmail.com', 'Nama', 9, 'Pisang Goreng', 'Makanan', 'Makanan Ringan', '7000', '2', '14000', '', 'belum bayar', '0000-00-00 00:00:00'),
(85, '40', 'kasir@gmail.com', 'Nama', 2, 'Green Tea Hot', 'Minuman Hot', 'Non Coffe', '10000', '2', '20000', 'kasir', 'sudah bayar', '2022-08-14 23:42:36'),
(86, '40', 'kasir@gmail.com', 'Nama', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '4', '48000', 'kasir', 'sudah bayar', '2022-08-14 23:42:36'),
(87, '40', 'kasir@gmail.com', 'Nama', 7, 'Kentang Goreng', 'Makanan', 'Makanan Ringan', '6000', '5', '30000', 'kasir', 'sudah bayar', '2022-08-14 23:42:36'),
(88, '40', 'kasir@gmail.com', 'Nama', 8, 'Nasi Goreng', 'Makanan', 'Makanan Berat', '15000', '3', '45000', 'kasir', 'sudah bayar', '2022-08-14 23:42:36'),
(89, '40', 'kasir@gmail.com', 'Nama', 39, 'es kopi susu pisang', 'Minuman Ice', 'Coffee', '18000', '5', '90000', 'kasir', 'sudah bayar', '2022-08-14 23:42:36'),
(90, '41', 'kasir@gmail.com', 'Mun', 2, 'Green Tea Hot', 'Minuman Hot', 'Non Coffe', '10000', '3', '30000', '', 'belum bayar', '2022-08-14 23:49:24'),
(91, '42', 'kasir@gmail.com', 'Mun', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '1', '12000', '', 'belum bayar', '2022-08-14 23:50:04'),
(92, '43', 'muhammadjayadi536@gmail.com', 'jay', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '1', '12000', '', 'belum bayar', '2022-08-15 08:44:59'),
(93, '44', 'muhammadjayadi536@gmail.com', 'jay', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '1', '12000', '', 'belum bayar', '2022-08-15 08:47:28'),
(94, '45', 'muhammadjayadi536@gmail.com', 'jay', 0, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '1', '12000', '', 'belum bayar', '2022-08-15 08:49:04'),
(95, '46', 'muhammadjayadi536@gmail.com', 'jay', 2, 'Green Tea Hot', 'Minuman Hot', 'Non Coffe', '10000', '3', '30000', '', 'belum bayar', '2022-08-15 09:06:09'),
(96, '46', 'muhammadjayadi536@gmail.com', 'jay', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '1', '12000', '', 'belum bayar', '2022-08-15 09:06:09'),
(97, '47', 'muhammadjayadi536@gmail.com', 'jay', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '1', '12000', '', 'belum bayar', '2022-08-15 09:06:31'),
(98, '48', 'munir@gmail.com', 'Misbahul Munir', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '5', '60000', '', 'belum bayar', '2022-08-15 09:29:23'),
(99, '48', 'munir@gmail.com', 'Misbahul Munir', 5, 'Cappucino Hot', 'Minuman Hot', 'Coffee', '12345', '5', '61725', '', 'belum bayar', '2022-08-15 09:29:23'),
(100, '48', 'munir@gmail.com', 'Misbahul Munir', 7, 'Kentang Goreng', 'Makanan', 'Makanan Ringan', '6000', '4', '24000', '', 'belum bayar', '2022-08-15 09:29:23'),
(102, '50', 'munir@gmail.com', 'Misbahul Munir', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '1', '12000', '', 'belum bayar', '2022-08-15 10:16:41'),
(103, '51', 'munir@gmail.com', 'Misbahul Munir', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '4', '48000', '', 'belum bayar', '2022-08-15 10:16:58'),
(106, '51', 'munir@gmail.com', 'Misbahul Munir', 5, 'Cappucino Hot', 'Minuman Hot', 'Coffee', '12345', '5', '61725', '', 'belum bayar', '2022-08-15 10:16:58'),
(108, '52', 'jay', 'jay', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '1', '12000', 'kasir', 'sudah bayar', '2022-08-15 16:39:48'),
(109, '52', 'jay', 'jay', 5, 'Cappucino Hot', 'Minuman Hot', 'Coffee', '12345', '2', '24690', 'kasir', 'sudah bayar', '2022-08-15 16:39:48'),
(110, '52', 'jay', 'jay', 2, 'Green Tea Hot', 'Minuman Hot', 'Non Coffe', '10000', '2', '20000', 'kasir', 'sudah bayar', '2022-08-15 16:39:48'),
(111, '52', 'jay', 'jay', 7, 'Kentang Goreng', 'Makanan', 'Makanan Ringan', '6000', '2', '12000', 'kasir', 'sudah bayar', '2022-08-15 16:39:48'),
(112, '53', 'jay', 'jay', 0, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '1', '12000', '', 'belum bayar', '2022-08-15 19:11:53'),
(113, '53', 'jay', 'jay', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '1', '12000', '', 'belum bayar', '2022-08-15 19:11:53'),
(114, '53', 'jay', 'jay', 2, 'Green Tea Hot', 'Minuman Hot', 'Non Coffe', '10000', '1', '10000', '', 'belum bayar', '2022-08-15 19:11:53'),
(115, '53', 'jay', 'jay', 5, 'Cappucino Hot', 'Minuman Hot', 'Coffee', '12345', '1', '12345', '', 'belum bayar', '2022-08-15 19:11:53'),
(116, '54', 'jay', 'jay', 2, 'Green Tea Hot', 'Minuman Hot', 'Non Coffe', '10000', '1', '10000', '', 'belum bayar', '2022-08-16 08:07:44'),
(117, '55', 'jay', 'jay', 43, 'Capucinno', 'Minuman Hot', 'Coffee', '20000', '1', '20000', '', 'belum bayar', '2022-08-16 08:08:46'),
(118, '56', 'jay', 'jay', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '1', '12000', '', 'belum bayar', '2022-08-16 21:11:36'),
(119, '57', 'munir@gmail.com', 'Misbahul Munir', 2, 'Green Tea Hot', 'Minuman Hot', 'Non Coffe', '13000', '4', '52000', '', 'belum bayar', '2022-08-16 22:59:45'),
(120, '57', 'munir@gmail.com', 'Misbahul Munir', 3, 'Thai Tea Ice', 'Minuman Ice', 'Non Coffe', '12000', '4', '48000', '', 'belum bayar', '2022-08-16 22:59:45'),
(121, '', 'jay', 'jay', 5, 'Cappucino Hot', 'Minuman Hot', 'Coffee', '12345', '1', '12345', '', 'belum pesan', '2022-08-16 23:46:46');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `nama` varchar(30) NOT NULL,
  `nomor_wa` varchar(14) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(12) NOT NULL,
  `sebagai` varchar(9) NOT NULL,
  `shift` varchar(6) NOT NULL,
  `waktu` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id_user`, `nama`, `nomor_wa`, `email`, `password`, `sebagai`, `shift`, `waktu`) VALUES
(1, 'admin', '0895637846750', 'admin@admin.com', 'admin', 'admin', 'none', '2022-07-21 21:48:43'),
(2, 'Ornald Santuiro', '08123456789', 'ornaldsantuiro@gmail.com', 'ornald', 'kasir', 'malam', '2022-07-21 21:48:43'),
(3, 'Andi. Aco Fadel', '08123456789', 'andiacofadel@gmail.com', 'andiaco', 'kasir', 'siang', '2022-07-21 21:48:43'),
(4, 'Dewi Aco Michael', '081222333444', 'dewiacomichael@gmail.com', 'dewi', 'pelanggan', 'none', '2022-07-21 21:48:43'),
(5, 'Syahrir zulkaidah', '081222333444', 'syahrirzulkaidah@gmail.com', 'syahrir', 'pelanggan', 'none', '2022-07-21 21:48:43'),
(6, 'Nur123', '089123', 'nur@gmail.com', 'nur', 'pelanggan', 'none', '2022-07-21 23:59:53'),
(27, 'kasir', '0898', 'kasir@gmail.com', 'kasir', 'kasir', 'malam', '2022-08-13 00:28:13'),
(28, 'Rio', '082154838046', 'mariomad2296@gmail.com', '12345', 'pelanggan', 'none', '2022-08-13 07:50:37'),
(29, 'jay', '085752239246', 'muhammadjayadi536@gmail.con', 'teknik017', 'pelanggan', 'none', '2022-08-13 08:07:41'),
(30, 'jay', '085752239246', 'muhammadjayadi536@gmail.com', 'teknik017', 'pelanggan', 'none', '2022-08-13 08:08:59'),
(31, 'bhg', '123', 'agjj', 'bhg', 'pelanggan', 'none', '2022-08-13 14:53:32'),
(32, '123', '123', '123', '123', 'pelanggan', 'none', '2022-08-13 14:54:00'),
(33, 'Mario', '082155666999', 'mario@mario.com', '123456', 'pelanggan', 'none', '2022-08-14 01:07:41'),
(34, 'wahyu', '123456789', 'wwwewww@gmail', '12345', 'pelanggan', 'none', '2022-08-15 08:39:33'),
(35, 'wahyu', '123456789', 'wwwewww@gmail', '12345', 'pelanggan', 'none', '2022-08-15 08:39:33'),
(36, 'wahyu', '123456789', 'wwwewww@gmail', '12345', 'pelanggan', 'none', '2022-08-15 08:39:33'),
(37, 'wahyu', '123456789', 'wwwewww@gmail', '12345', 'pelanggan', 'none', '2022-08-15 08:39:33'),
(38, 'jayadi', '085752239246', 'jayadi', 'teknik17', 'pelanggan', 'none', '2022-08-15 16:38:20'),
(39, 'jay', '085752239246', 'jay', '12345', 'pelanggan', 'none', '2022-08-15 16:38:45');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `jenis_menu`
--
ALTER TABLE `jenis_menu`
  ADD PRIMARY KEY (`id_jenis_menu`);

--
-- Indexes for table `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`id_menu`);

--
-- Indexes for table `pesanan`
--
ALTER TABLE `pesanan`
  ADD PRIMARY KEY (`no`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `jenis_menu`
--
ALTER TABLE `jenis_menu`
  MODIFY `id_jenis_menu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `menu`
--
ALTER TABLE `menu`
  MODIFY `id_menu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- AUTO_INCREMENT for table `pesanan`
--
ALTER TABLE `pesanan`
  MODIFY `no` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=122;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
