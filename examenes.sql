-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 27-11-2014 a las 20:37:17
-- Versión del servidor: 5.6.11
-- Versión de PHP: 5.5.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `examenes`
--
CREATE DATABASE IF NOT EXISTS `examenes` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `examenes`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `academy`
--

CREATE TABLE IF NOT EXISTS `academy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `direction` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Volcado de datos para la tabla `academy`
--

INSERT INTO `academy` (`id`, `name`, `direction`) VALUES
(1, 'Arcoiris', 'SJM');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `admin`
--

CREATE TABLE IF NOT EXISTS `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_admin_user1_idx` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Volcado de datos para la tabla `admin`
--

INSERT INTO `admin` (`id`, `name`, `lastname`, `user_id`) VALUES
(1, 'José', NULL, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `asignature`
--

CREATE TABLE IF NOT EXISTS `asignature` (
  `professor_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `section_id` int(11) NOT NULL,
  PRIMARY KEY (`professor_id`,`course_id`,`section_id`),
  KEY `fk_professor_x_course_x_section_course1_idx` (`course_id`),
  KEY `fk_professor_x_course_x_section_section1_idx` (`section_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `asignature`
--

INSERT INTO `asignature` (`professor_id`, `course_id`, `section_id`) VALUES
(1, 1, 1),
(1, 2, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `choice`
--

CREATE TABLE IF NOT EXISTS `choice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `value` varchar(45) DEFAULT NULL,
  `image` varchar(45) DEFAULT NULL,
  `question_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_choice_question1_idx` (`question_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=14 ;

--
-- Volcado de datos para la tabla `choice`
--

INSERT INTO `choice` (`id`, `value`, `image`, `question_id`) VALUES
(1, 'no', NULL, 1),
(2, 'si', NULL, 1),
(3, NULL, '27.jpg,-21,-109,138,26', 2),
(4, NULL, '27.jpg,-11,-133,151,20', 2),
(7, NULL, '50.jpg,-13,-1,134,37', 5),
(8, NULL, '50.jpg,0,-110,159,46', 5),
(9, NULL, '31.jpg,-29,-22,106,82', 6),
(10, NULL, '31.jpg,-28,-102,106,97', 6),
(11, '2', NULL, 7),
(12, '8', NULL, 7),
(13, '4', NULL, 7);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `course`
--

CREATE TABLE IF NOT EXISTS `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(10) DEFAULT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Volcado de datos para la tabla `course`
--

INSERT INTO `course` (`id`, `code`, `name`) VALUES
(1, NULL, 'Álgebra'),
(2, NULL, 'Aritmética'),
(3, NULL, 'Geometría'),
(4, NULL, 'Trigonometría');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cycle`
--

CREATE TABLE IF NOT EXISTS `cycle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `duration` varchar(30) DEFAULT NULL,
  `startDate` date DEFAULT NULL,
  `lastDate` date DEFAULT NULL,
  `academy_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_cycle_academy1_idx` (`academy_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Volcado de datos para la tabla `cycle`
--

INSERT INTO `cycle` (`id`, `name`, `duration`, `startDate`, `lastDate`, `academy_id`) VALUES
(1, 'Anual San Marcos 2014', '8 meses', '2014-04-07', '2014-12-19', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cycle_x_course`
--

CREATE TABLE IF NOT EXISTS `cycle_x_course` (
  `cycle_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  PRIMARY KEY (`cycle_id`,`course_id`),
  KEY `fk_cycle_has_course_course1_idx` (`course_id`),
  KEY `fk_cycle_has_course_cycle1_idx` (`cycle_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `cycle_x_course`
--

INSERT INTO `cycle_x_course` (`cycle_id`, `course_id`) VALUES
(1, 1),
(1, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `exam`
--

CREATE TABLE IF NOT EXISTS `exam` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime DEFAULT NULL,
  `cycle_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_exam_cycle1_idx` (`cycle_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Volcado de datos para la tabla `exam`
--

INSERT INTO `exam` (`id`, `date`, `cycle_id`) VALUES
(1, '2014-11-24 10:00:00', 1),
(2, '2014-11-25 14:00:00', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `exam_question`
--

CREATE TABLE IF NOT EXISTS `exam_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `score_correct` float NOT NULL,
  `score_incorrect` float DEFAULT '0',
  `professor_question_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_exam_question_professor_question1_idx` (`professor_question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `exam_x_theme`
--

CREATE TABLE IF NOT EXISTS `exam_x_theme` (
  `exam_id` int(11) NOT NULL,
  `theme_id` int(11) NOT NULL,
  PRIMARY KEY (`exam_id`,`theme_id`),
  KEY `fk_exam_has_theme_theme1_idx` (`theme_id`),
  KEY `fk_exam_has_theme_exam1_idx` (`exam_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `exam_x_theme`
--

INSERT INTO `exam_x_theme` (`exam_id`, `theme_id`) VALUES
(1, 1),
(2, 1),
(1, 4),
(2, 4),
(2, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `professor`
--

CREATE TABLE IF NOT EXISTS `professor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(10) DEFAULT NULL,
  `name` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`),
  KEY `fk_professor_user1_idx` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Volcado de datos para la tabla `professor`
--

INSERT INTO `professor` (`id`, `code`, `name`, `lastname`, `user_id`) VALUES
(1, NULL, 'ADRIÁN GUILLERMO', 'ALIAGA LLANOS', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `professor_question`
--

CREATE TABLE IF NOT EXISTS `professor_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `professor_id` int(11) NOT NULL,
  `exam_id` int(11) NOT NULL,
  `question_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_professor_question_professor1_idx` (`professor_id`),
  KEY `fk_professor_question_exam1_idx` (`exam_id`),
  KEY `fk_professor_question_question1_idx` (`question_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=12 ;

--
-- Volcado de datos para la tabla `professor_question`
--

INSERT INTO `professor_question` (`id`, `professor_id`, `exam_id`, `question_id`) VALUES
(2, 1, 1, 2),
(5, 1, 1, 6),
(6, 1, 2, 5),
(7, 1, 2, 7),
(11, 1, 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `question`
--

CREATE TABLE IF NOT EXISTS `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `value` varchar(45) DEFAULT NULL,
  `image` varchar(45) DEFAULT NULL,
  `choice_id` int(11) DEFAULT NULL,
  `theme_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_exam_detail_choice1_idx` (`choice_id`),
  KEY `fk_question_theme1_idx` (`theme_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;

--
-- Volcado de datos para la tabla `question`
--

INSERT INTO `question` (`id`, `value`, `image`, `choice_id`, `theme_id`) VALUES
(1, '-(A U B) = -A ∩ -B', NULL, 2, 1),
(2, NULL, '27.jpg,-54,-2,80,76', 4, 4),
(5, NULL, '50.jpg,-14,-36,134,76', 8, 4),
(6, NULL, '19.jpg,-5,-25,217,116', 10, 4),
(7, '2 ^ ( 2 x 2^0+1) es ', NULL, 12, 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `role`
--

CREATE TABLE IF NOT EXISTS `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Volcado de datos para la tabla `role`
--

INSERT INTO `role` (`id`, `description`) VALUES
(1, 'professor'),
(2, 'student'),
(3, 'admin');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `section`
--

CREATE TABLE IF NOT EXISTS `section` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `cycle_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_section_cycle1_idx` (`cycle_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Volcado de datos para la tabla `section`
--

INSERT INTO `section` (`id`, `name`, `cycle_id`) VALUES
(1, 'A', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `student`
--

CREATE TABLE IF NOT EXISTS `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(10) DEFAULT NULL,
  `name` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `section_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`),
  KEY `fk_student_user1_idx` (`user_id`),
  KEY `fk_student_section1_idx` (`section_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `test`
--

CREATE TABLE IF NOT EXISTS `test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timeLeft` int(11) DEFAULT NULL,
  `finish` int(11) DEFAULT NULL,
  `score` float DEFAULT NULL,
  `student_id` int(11) NOT NULL,
  `exam_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_test_student2_idx` (`student_id`),
  KEY `fk_test_exam1_idx` (`exam_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `test_question`
--

CREATE TABLE IF NOT EXISTS `test_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exam_question_id` int(11) NOT NULL,
  `choice_id` int(11) DEFAULT NULL,
  `test_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_test_question_exam_question1_idx` (`exam_question_id`),
  KEY `fk_test_question_choice1_idx` (`choice_id`),
  KEY `fk_test_question_test1_idx` (`test_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `theme`
--

CREATE TABLE IF NOT EXISTS `theme` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `course_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_theme_course1_idx` (`course_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- Volcado de datos para la tabla `theme`
--

INSERT INTO `theme` (`id`, `name`, `course_id`) VALUES
(1, 'TEORÍA DE CONJUNTOS', 2),
(2, 'NUMERACIÓN', 2),
(3, 'DIVISIBILIDAD', 2),
(4, 'TEORÍA DE EXPONENTES', 1),
(5, 'POLINOMIOS', 1),
(6, 'PRODUCTOS NOTABLES', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(30) NOT NULL,
  `pass` varchar(30) NOT NULL,
  `active` int(11) DEFAULT '1',
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`email`),
  KEY `fk_user_role_idx` (`role_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`id`, `email`, `pass`, `active`, `role_id`) VALUES
(1, 'admin', '123', 1, 3),
(2, 'adrian@gmail.com', '123', 1, 1);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `fk_admin_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE SET NULL;

--
-- Filtros para la tabla `asignature`
--
ALTER TABLE `asignature`
  ADD CONSTRAINT `fk_professor_x_course_x_section_course1` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_professor_x_course_x_section_professor1` FOREIGN KEY (`professor_id`) REFERENCES `professor` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_professor_x_course_x_section_section1` FOREIGN KEY (`section_id`) REFERENCES `section` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `choice`
--
ALTER TABLE `choice`
  ADD CONSTRAINT `fk_choice_question1` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `cycle`
--
ALTER TABLE `cycle`
  ADD CONSTRAINT `fk_cycle_academy1` FOREIGN KEY (`academy_id`) REFERENCES `academy` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `cycle_x_course`
--
ALTER TABLE `cycle_x_course`
  ADD CONSTRAINT `fk_cycle_has_course_course1` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_cycle_has_course_cycle1` FOREIGN KEY (`cycle_id`) REFERENCES `cycle` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `exam`
--
ALTER TABLE `exam`
  ADD CONSTRAINT `fk_exam_cycle1` FOREIGN KEY (`cycle_id`) REFERENCES `cycle` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `exam_question`
--
ALTER TABLE `exam_question`
  ADD CONSTRAINT `fk_exam_question_professor_question1` FOREIGN KEY (`professor_question_id`) REFERENCES `professor_question` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `exam_x_theme`
--
ALTER TABLE `exam_x_theme`
  ADD CONSTRAINT `fk_exam_has_theme_exam1` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_exam_has_theme_theme1` FOREIGN KEY (`theme_id`) REFERENCES `theme` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `professor`
--
ALTER TABLE `professor`
  ADD CONSTRAINT `fk_professor_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE SET NULL;

--
-- Filtros para la tabla `professor_question`
--
ALTER TABLE `professor_question`
  ADD CONSTRAINT `fk_professor_question_exam1` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_professor_question_professor1` FOREIGN KEY (`professor_id`) REFERENCES `professor` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_professor_question_question1` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `question`
--
ALTER TABLE `question`
  ADD CONSTRAINT `fk_exam_detail_choice1` FOREIGN KEY (`choice_id`) REFERENCES `choice` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  ADD CONSTRAINT `fk_question_theme1` FOREIGN KEY (`theme_id`) REFERENCES `theme` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `section`
--
ALTER TABLE `section`
  ADD CONSTRAINT `fk_section_cycle1` FOREIGN KEY (`cycle_id`) REFERENCES `cycle` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `fk_student_section1` FOREIGN KEY (`section_id`) REFERENCES `section` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  ADD CONSTRAINT `fk_student_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE SET NULL ON UPDATE SET NULL;

--
-- Filtros para la tabla `test`
--
ALTER TABLE `test`
  ADD CONSTRAINT `fk_test_exam1` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_test_student2` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `test_question`
--
ALTER TABLE `test_question`
  ADD CONSTRAINT `fk_test_question_choice1` FOREIGN KEY (`choice_id`) REFERENCES `choice` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  ADD CONSTRAINT `fk_test_question_exam_question1` FOREIGN KEY (`exam_question_id`) REFERENCES `exam_question` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_test_question_test1` FOREIGN KEY (`test_id`) REFERENCES `test` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `theme`
--
ALTER TABLE `theme`
  ADD CONSTRAINT `fk_theme_course1` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `fk_user_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
