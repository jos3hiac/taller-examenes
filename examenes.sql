-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 10-10-2014 a las 21:54:22
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
CREATE DATABASE IF NOT EXISTS `examenes` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Volcado de datos para la tabla `admin`
--

INSERT INTO `admin` (`id`, `name`, `lastname`, `user_id`) VALUES
(1, 'admin', 'admin', 8);

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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `asignature`
--

INSERT INTO `asignature` (`professor_id`, `course_id`, `section_id`) VALUES
(1, 1, 1),
(1, 2, 1),
(2, 3, 1),
(2, 4, 1);

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=30 ;

--
-- Volcado de datos para la tabla `choice`
--

INSERT INTO `choice` (`id`, `value`, `image`, `question_id`) VALUES
(1, NULL, 'preguntas/1/imagen.jpeg,-10,-60,40,10', 1),
(2, NULL, 'preguntas/1/imagen.jpeg,-60,-60,40,10', 1),
(3, NULL, 'preguntas/1/imagen.jpeg,-110,-60,40,10', 1),
(4, NULL, 'preguntas/1/imagen.jpeg,-160,-60,40,10', 1),
(5, NULL, 'preguntas/1/imagen.jpeg,-210,-60,40,10', 1),
(6, NULL, 'preguntas/2/imagen.jpeg,-10,-60,40,10', 2),
(7, NULL, 'preguntas/2/imagen.jpeg,-60,-60,40,10', 2),
(8, NULL, 'preguntas/2/imagen.jpeg,-110,-60,40,10', 2),
(9, NULL, 'preguntas/2/imagen.jpeg,-160,-60,40,10', 2),
(10, NULL, 'preguntas/2/imagen.jpeg,-210,-60,40,10', 2),
(11, NULL, 'preguntas/3/imagen.jpeg,-10,-60,40,10', 3),
(12, NULL, 'preguntas/3/imagen.jpeg,-60,-60,40,10', 3),
(13, NULL, 'preguntas/3/imagen.jpeg,-110,-60,40,10', 3),
(14, NULL, 'preguntas/3/imagen.jpeg,-160,-60,40,10', 3),
(15, NULL, 'preguntas/3/imagen.jpeg,-210,-60,40,10', 3),
(16, NULL, 'preguntas/4/imagen.jpeg,-10,-60,40,10', 4),
(17, NULL, 'preguntas/4/imagen.jpeg,-60,-60,40,10', 4),
(19, NULL, 'preguntas/4/imagen.jpeg,-160,-60,40,10', 4),
(25, NULL, 'part3.jpeg,-48,-199,50,16', 10),
(26, NULL, 'part3.jpeg,-115,-199,47,16', 10),
(27, NULL, 'part3.jpeg,-184,-199,47,16', 10),
(28, NULL, 'part3.jpeg,-47,-212,31,16', 10),
(29, NULL, 'part3.jpeg,-115,-213,43,16', 10);

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Volcado de datos para la tabla `cycle`
--

INSERT INTO `cycle` (`id`, `name`, `duration`, `startDate`, `lastDate`, `academy_id`) VALUES
(1, 'ciclo webeo UNI 2014', '2 meses', NULL, NULL, 1);

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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cycle_x_course`
--

INSERT INTO `cycle_x_course` (`cycle_id`, `course_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `exam`
--

CREATE TABLE IF NOT EXISTS `exam` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `cycle_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_exam_cycle1_idx` (`cycle_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Volcado de datos para la tabla `exam`
--

INSERT INTO `exam` (`id`, `date`, `cycle_id`) VALUES
(1, '2014-09-06', 1);

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
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `exam_x_theme`
--

INSERT INTO `exam_x_theme` (`exam_id`, `theme_id`) VALUES
(1, 1),
(1, 2),
(1, 4),
(1, 5),
(1, 7),
(1, 8),
(1, 10),
(1, 11);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `play_evolutions`
--

CREATE TABLE IF NOT EXISTS `play_evolutions` (
  `id` int(11) NOT NULL,
  `hash` varchar(255) NOT NULL,
  `applied_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `apply_script` text,
  `revert_script` text,
  `state` varchar(255) DEFAULT NULL,
  `last_problem` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Volcado de datos para la tabla `professor`
--

INSERT INTO `professor` (`id`, `code`, `name`, `lastname`, `user_id`) VALUES
(1, NULL, 'ADRIÁN GUILLERMO', 'ALIAGA LLANOS', 1),
(2, NULL, 'LUIS ENRIQUE', 'CARRILLO DÍAZ', 2);

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Volcado de datos para la tabla `professor_question`
--

INSERT INTO `professor_question` (`id`, `professor_id`, `exam_id`, `question_id`) VALUES
(1, 1, 1, 1),
(2, 1, 1, 2),
(3, 2, 1, 3),
(4, 2, 1, 4),
(5, 1, 1, 10);

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Volcado de datos para la tabla `question`
--

INSERT INTO `question` (`id`, `value`, `image`, `choice_id`, `theme_id`) VALUES
(1, NULL, 'imagen.jpeg,-10,-20,150,30', 1, 1),
(2, NULL, 'imagen.jpeg,-20,-20,100,20', 8, 4),
(3, NULL, 'imagen.jpeg,-10,-40,150,40', 15, 7),
(4, NULL, 'imagen.jpeg,-30,-60,200,50', 19, 10),
(10, NULL, 'part3.jpeg,-32,-141,208,58', 26, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `role`
--

CREATE TABLE IF NOT EXISTS `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Volcado de datos para la tabla `student`
--

INSERT INTO `student` (`id`, `code`, `name`, `lastname`, `user_id`, `section_id`) VALUES
(1, NULL, 'MARIA GABRIELA \n\nSTHEFANIE', 'GONZALES OLIVARES', 3, 1),
(2, NULL, 'ANTHONY BERNARDO', 'MUÑOZ APARE', 4, 1),
(3, NULL, 'JAMES RAUL', 'TANTA MENDOZA', 5, 1),
(4, NULL, 'BRANDON LEE', 'VITOR CALERO', 6, 1),
(5, NULL, 'GILBERTH VICENTE', 'ALVA CELMI', 7, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `test`
--

CREATE TABLE IF NOT EXISTS `test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `timeLeft` time DEFAULT NULL,
  `finish` int(11) DEFAULT NULL,
  `student_id` int(11) NOT NULL,
  `exam_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_test_student2_idx` (`student_id`),
  KEY `fk_test_exam1_idx` (`exam_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `test_question`
--

CREATE TABLE IF NOT EXISTS `test_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `score` int(11) DEFAULT NULL,
  `exam_question_id` int(11) NOT NULL,
  `choice_id` int(11) DEFAULT NULL,
  `test_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_test_question_exam_question1_idx` (`exam_question_id`),
  KEY `fk_test_question_choice1_idx` (`choice_id`),
  KEY `fk_test_question_test1_idx` (`test_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `test_result`
--

CREATE TABLE IF NOT EXISTS `test_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `result` float DEFAULT NULL,
  `test_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_test_result_test1_idx` (`test_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

--
-- Volcado de datos para la tabla `theme`
--

INSERT INTO `theme` (`id`, `name`, `course_id`) VALUES
(1, 'TEORÍA DE EXPONENTES', 1),
(2, 'POLINOMIOS', 1),
(3, 'PRODUCTOS NOTABLES', 1),
(4, 'TEORÍA DE CONJUNTOS', 2),
(5, 'NUMERACION', 2),
(6, 'DIVISIBILIDAD', 2),
(7, 'SEGMENTOS', 3),
(8, 'ANGULOS', 3),
(9, 'TRIANGULOS', 3),
(10, 'SISTEMA DE MEDIDA ANGULAR', 4),
(11, 'LONGITUD DE ARCO-SECTOR CIRCULAR', 4),
(12, 'RAZONES \r\n\r\nTRIGONOMETRICAS', 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(30) NOT NULL,
  `pass` varchar(30) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`email`),
  KEY `fk_user_role_idx` (`role_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`id`, `email`, `pass`, `role_id`) VALUES
(1, 'adrian.aliaga@gmail.com', '123', 1),
(2, 'luis.carrillo@gmail.com', '123', 1),
(3, '14200017@hotmail.com', '123', 2),
(4, '14200025@hotmail.com', '123', 2),
(5, '14200112@hotmail.com', '123', 2),
(6, '14200170@hotmail.com', '123', 2),
(7, '13200001@hotmail.com', '123', 2),
(8, 'admin@gmail.com', '123', 3);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `fk_admin_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `asignature`
--
ALTER TABLE `asignature`
  ADD CONSTRAINT `fk_professor_x_course_x_section_course1` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_professor_x_course_x_section_professor1` FOREIGN KEY (`professor_id`) REFERENCES `professor` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_professor_x_course_x_section_section1` FOREIGN KEY (`section_id`) REFERENCES `section` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `choice`
--
ALTER TABLE `choice`
  ADD CONSTRAINT `fk_choice_question1` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `cycle`
--
ALTER TABLE `cycle`
  ADD CONSTRAINT `fk_cycle_academy1` FOREIGN KEY (`academy_id`) REFERENCES `academy` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `cycle_x_course`
--
ALTER TABLE `cycle_x_course`
  ADD CONSTRAINT `fk_cycle_has_course_course1` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_cycle_has_course_cycle1` FOREIGN KEY (`cycle_id`) REFERENCES `cycle` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `exam`
--
ALTER TABLE `exam`
  ADD CONSTRAINT `fk_exam_cycle1` FOREIGN KEY (`cycle_id`) REFERENCES `cycle` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `exam_question`
--
ALTER TABLE `exam_question`
  ADD CONSTRAINT `fk_exam_question_professor_question1` FOREIGN KEY (`professor_question_id`) REFERENCES `professor_question` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `exam_x_theme`
--
ALTER TABLE `exam_x_theme`
  ADD CONSTRAINT `fk_exam_has_theme_exam1` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_exam_has_theme_theme1` FOREIGN KEY (`theme_id`) REFERENCES `theme` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `professor`
--
ALTER TABLE `professor`
  ADD CONSTRAINT `fk_professor_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `professor_question`
--
ALTER TABLE `professor_question`
  ADD CONSTRAINT `fk_professor_question_exam1` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_professor_question_professor1` FOREIGN KEY (`professor_id`) REFERENCES `professor` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_professor_question_question1` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `question`
--
ALTER TABLE `question`
  ADD CONSTRAINT `fk_exam_detail_choice1` FOREIGN KEY (`choice_id`) REFERENCES `choice` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_question_theme1` FOREIGN KEY (`theme_id`) REFERENCES `theme` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `section`
--
ALTER TABLE `section`
  ADD CONSTRAINT `fk_section_cycle1` FOREIGN KEY (`cycle_id`) REFERENCES `cycle` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `fk_student_section1` FOREIGN KEY (`section_id`) REFERENCES `section` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_student_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `test`
--
ALTER TABLE `test`
  ADD CONSTRAINT `fk_test_exam1` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_test_student2` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `test_question`
--
ALTER TABLE `test_question`
  ADD CONSTRAINT `fk_test_question_choice1` FOREIGN KEY (`choice_id`) REFERENCES `choice` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_test_question_exam_question1` FOREIGN KEY (`exam_question_id`) REFERENCES `exam_question` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_test_question_test1` FOREIGN KEY (`test_id`) REFERENCES `test` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `test_result`
--
ALTER TABLE `test_result`
  ADD CONSTRAINT `fk_test_result_test1` FOREIGN KEY (`test_id`) REFERENCES `test` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `theme`
--
ALTER TABLE `theme`
  ADD CONSTRAINT `fk_theme_course1` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `fk_user_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
