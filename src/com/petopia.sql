-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Nov 12, 2023 at 08:27 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `petopia`
--

-- --------------------------------------------------------

--
-- Table structure for table `access`
--

CREATE TABLE `access` (
  `username` varchar(25) NOT NULL,
  `password` varchar(25) NOT NULL,
  `level` varchar(25) NOT NULL,
  `emp_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `access`
--

INSERT INTO `access` (`username`, `password`, `level`, `emp_id`) VALUES
('Chupi', 'Chups', 'Staff', 27),
('daff', 'daff', 'Admin', 1),
('dak', 'ney', 'Admin', 38),
('tim', 'tim', 'Admin', 26);

-- --------------------------------------------------------

--
-- Table structure for table `appointment`
--

CREATE TABLE `appointment` (
  `appointment_id` int(11) NOT NULL,
  `serv_id` int(11) DEFAULT NULL,
  `cust_id` int(11) NOT NULL,
  `appointment_date` date NOT NULL DEFAULT current_timestamp(),
  `total_cost` float NOT NULL DEFAULT 0,
  `status` bit(1) NOT NULL DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `appointment`
--

INSERT INTO `appointment` (`appointment_id`, `serv_id`, `cust_id`, `appointment_date`, `total_cost`, `status`) VALUES
(15, 1, 5, '2023-11-08', 300, b'0'),
(16, 4, 7, '2023-11-08', 60, b'0'),
(17, 3, 4, '2023-11-08', 60, b'0'),
(18, 2, 5, '2023-11-10', 150, b'0');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `cust_id` int(11) NOT NULL,
  `fullname` varchar(25) NOT NULL,
  `email` varchar(25) NOT NULL,
  `phone` int(15) NOT NULL,
  `address` varchar(60) NOT NULL,
  `archived` int(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`cust_id`, `fullname`, `email`, `phone`, `address`, `archived`) VALUES
(4, 'Suga Girl', 'suga@milkyway', 123456789, 'Alpha Centauri', 0),
(5, 'Kimpi Kim', 'kimpi@juno', 12451245, 'Jupiter, Jovian System', 0),
(6, 'Pangsy', 'pangsy@stonehenge.com', 65321245, 'UK', 0),
(7, 'Snowbelle', 'sno@bel', 234234, 'Earth, Solar System', 0);

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `emp_id` int(11) NOT NULL,
  `fullname` varchar(25) NOT NULL,
  `email` varchar(25) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `position` varchar(25) NOT NULL,
  `archived` int(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`emp_id`, `fullname`, `email`, `address`, `position`, `archived`) VALUES
(1, 'Daffy Duck', 'daffy@ducky', 'Mercury, Solar System', 'Master Cockroach Punisher', 0),
(26, 'Sir Timothy Poknat', 'tim@poks', 'Pluto, Solar System', 'Grand Sleeper', 0),
(27, 'Chups Jorda', 'chups@jovian.system', 'Mercury, Solar System', 'Scottish Fold Imitator', 0),
(34, 'Butiks', 'tiks@jovian.system', 'Neptune, Solar System', 'Head Zookeeper', 0),
(36, 'Hachi Ko', 'hachi@wanted.com', 'Milky Way, Universe', 'Sentinel', 0),
(38, 'Dakney', 'dak@ney.to', 'Asteroid Belt, Solar System', 'CFO', 0),
(39, 'Mochi Chi', 'moch@moch', 'Uranus, Solar System', 'Organic devourer', 0);

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `pay_id` int(11) NOT NULL,
  `desc` varchar(25) NOT NULL,
  `available` int(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`pay_id`, `desc`, `available`) VALUES
(1, 'Cash', 1),
(2, 'GCash', 1),
(3, 'Maya', 1),
(4, 'Debit Card', 1),
(5, 'Credit Card', 1);

-- --------------------------------------------------------

--
-- Table structure for table `pet`
--

CREATE TABLE `pet` (
  `pet_id` int(11) NOT NULL,
  `name` varchar(25) NOT NULL,
  `type_id` int(11) NOT NULL,
  `age` int(11) NOT NULL,
  `breed` varchar(25) NOT NULL,
  `cust_id` int(11) NOT NULL,
  `archived` int(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pet`
--

INSERT INTO `pet` (`pet_id`, `name`, `type_id`, `age`, `breed`, `cust_id`, `archived`) VALUES
(6, 'Okok', 2, 5, 'Pomeranian', 5, 0),
(7, 'El Chupacabra', 1, 1, 'British Short Hair', 4, 0),
(8, 'Mochi', 2, 3, 'Pomeranian', 6, 0),
(9, 'Kiko', 1, 9, 'Belgian Malinois', 7, 0);

-- --------------------------------------------------------

--
-- Table structure for table `receipt`
--

CREATE TABLE `receipt` (
  `inv_id` int(6) NOT NULL,
  `date` date NOT NULL,
  `cust_id` int(11) NOT NULL,
  `pet_id` int(11) NOT NULL,
  `serv_id` int(11) NOT NULL,
  `pay_id` int(11) NOT NULL,
  `total` float NOT NULL,
  `emp_id` int(11) NOT NULL,
  `void` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `receipt`
--

INSERT INTO `receipt` (`inv_id`, `date`, `cust_id`, `pet_id`, `serv_id`, `pay_id`, `total`, `emp_id`, `void`) VALUES
(6, '2023-10-10', 6, 7, 1, 5, 350, 26, 0),
(7, '2023-11-27', 5, 6, 5, 4, 350, 1, 0),
(8, '2023-11-02', 4, 8, 2, 2, 600, 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `service`
--

CREATE TABLE `service` (
  `serv_id` int(11) NOT NULL,
  `desc` varchar(60) NOT NULL,
  `price` float NOT NULL,
  `available` int(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `service`
--

INSERT INTO `service` (`serv_id`, `desc`, `price`, `available`) VALUES
(1, 'BATHING & SHAMPOOING', 350, 1),
(2, 'HAIRCUT & STYLING', 300, 1),
(3, 'NAIL TRIMMING & FILING', 150, 1),
(4, 'EAR CLEANING', 60, 0),
(5, 'DENTAL', 600, 1);

-- --------------------------------------------------------

--
-- Table structure for table `type`
--

CREATE TABLE `type` (
  `type_id` int(11) NOT NULL,
  `desc` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `type`
--

INSERT INTO `type` (`type_id`, `desc`) VALUES
(1, 'Cat'),
(2, 'Dog'),
(3, 'Fish'),
(4, 'Bird');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `access`
--
ALTER TABLE `access`
  ADD PRIMARY KEY (`username`,`password`),
  ADD KEY `access_emp_id_fk` (`emp_id`);

--
-- Indexes for table `appointment`
--
ALTER TABLE `appointment`
  ADD PRIMARY KEY (`appointment_id`),
  ADD KEY `appointment_serv_fk` (`serv_id`),
  ADD KEY `appointment_cust_fk` (`cust_id`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`cust_id`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`emp_id`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`pay_id`);

--
-- Indexes for table `pet`
--
ALTER TABLE `pet`
  ADD PRIMARY KEY (`pet_id`),
  ADD KEY `type_id` (`type_id`),
  ADD KEY `cust_id` (`cust_id`);

--
-- Indexes for table `receipt`
--
ALTER TABLE `receipt`
  ADD PRIMARY KEY (`inv_id`),
  ADD KEY `cust_id` (`cust_id`),
  ADD KEY `receipt_ibfk_2` (`pet_id`),
  ADD KEY `receipt_ibfk_3` (`emp_id`),
  ADD KEY `receipt_ibfk_4` (`pay_id`),
  ADD KEY `serv_id` (`serv_id`);

--
-- Indexes for table `service`
--
ALTER TABLE `service`
  ADD PRIMARY KEY (`serv_id`);

--
-- Indexes for table `type`
--
ALTER TABLE `type`
  ADD PRIMARY KEY (`type_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `appointment`
--
ALTER TABLE `appointment`
  MODIFY `appointment_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `cust_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `emp_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT for table `payment`
--
ALTER TABLE `payment`
  MODIFY `pay_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `pet`
--
ALTER TABLE `pet`
  MODIFY `pet_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `receipt`
--
ALTER TABLE `receipt`
  MODIFY `inv_id` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `service`
--
ALTER TABLE `service`
  MODIFY `serv_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `type`
--
ALTER TABLE `type`
  MODIFY `type_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `access`
--
ALTER TABLE `access`
  ADD CONSTRAINT `access_emp_id_fk` FOREIGN KEY (`emp_id`) REFERENCES `employee` (`emp_id`) ON UPDATE CASCADE;

--
-- Constraints for table `appointment`
--
ALTER TABLE `appointment`
  ADD CONSTRAINT `appointment_ibfk_1` FOREIGN KEY (`serv_id`) REFERENCES `service` (`serv_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `appointment_ibfk_2` FOREIGN KEY (`cust_id`) REFERENCES `customer` (`cust_id`) ON UPDATE CASCADE;

--
-- Constraints for table `pet`
--
ALTER TABLE `pet`
  ADD CONSTRAINT `pet_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `type` (`type_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `pet_ibfk_2` FOREIGN KEY (`cust_id`) REFERENCES `customer` (`cust_id`);

--
-- Constraints for table `receipt`
--
ALTER TABLE `receipt`
  ADD CONSTRAINT `receipt_ibfk_1` FOREIGN KEY (`cust_id`) REFERENCES `customer` (`cust_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `receipt_ibfk_2` FOREIGN KEY (`pet_id`) REFERENCES `pet` (`pet_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `receipt_ibfk_3` FOREIGN KEY (`emp_id`) REFERENCES `employee` (`emp_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `receipt_ibfk_4` FOREIGN KEY (`pay_id`) REFERENCES `payment` (`pay_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `receipt_ibfk_5` FOREIGN KEY (`serv_id`) REFERENCES `service` (`serv_id`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
