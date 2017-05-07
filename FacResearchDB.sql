-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: research
-- ------------------------------------------------------
-- Server version	5.5.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

DROP DATABASE IF EXISTS FacResearchDB;
CREATE DATABASE FacResearchDB;
USE FacResearchDB;

--
-- Table structure for table `authorship`
--

DROP TABLE IF EXISTS `authorship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authorship` (
  `facultyId` int(11) NOT NULL,
  `paperId` int(11) NOT NULL,
  PRIMARY KEY (`facultyId`,`paperId`),
  KEY `fk_a_f` (`facultyId`),
  KEY `fk_a_p` (`paperId`),
  CONSTRAINT `fk_a_f` FOREIGN KEY (`facultyId`) REFERENCES `faculty` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_a_p` FOREIGN KEY (`paperId`) REFERENCES `papers` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authorship`
--

LOCK TABLES `authorship` WRITE;
/*!40000 ALTER TABLE `authorship` DISABLE KEYS */;
INSERT INTO `authorship` VALUES (1,1),(1,2),(1,3),(1,4),(2,3),(1,5),(2,5),(4,5),(6,6),(5,7),(3,8),(7,9),(8,9),(9,9),(8,10),(10,11),(7,12),(9,12);
/*!40000 ALTER TABLE `authorship` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faculty`
--

DROP TABLE IF EXISTS `faculty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `faculty` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fName` varchar(45) DEFAULT NULL,
  `lName` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faculty`
--

LOCK TABLES `faculty` WRITE;
/*!40000 ALTER TABLE `faculty` DISABLE KEYS */;
INSERT INTO `faculty` (fName,lName,password,email) VALUES ('Steve','Zilora','5f47859188a602594556580532e814a3','sjz@it.rit.edu'),
														  ('Dan','Bogaard','f4f6172eb26581952a70d7199bfd2ddb','dsb@it.rit.edu'),
                                                          ('Vicki','Hanson','9de3c9bf7efa9dc868904da05864c924','vlh@it.rit.edu'),
                                                          ('Jim','Leone','617b94886b46c84543331ea15fd47855','jal@it.rit.edu'),
                                                          ('Brian','Tomaszewski','4479459e822346f1d9db6301092026ef','bmt@it.rit.edu'),
                                                          ('Elissa','Weeden','8176dfcfdee1aef1aaa60b52c298b1a3','emw@it.rit.edu'),
                                                          ('Edward','Holden','b2c4bdc9078b3e51ac0e7a7c10902112','eph@it.rit.edu'),
                                                          ('Qi','Yu','522fd20bb7451a705a380b1c3baa42a1','qyu@it.rit.edu'),
                                                          ('Jai','Kang','2e03179e508d300209ae776c8844af7d','jwk@it.rit.edu'),
                                                          ('Ronald','Vullo','f6c3fcbd15baf94d6b6e70d2aab0588c','rpv@it.rit.edu');
/*!40000 ALTER TABLE `faculty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paper_keywords`
--

DROP TABLE IF EXISTS `paper_keywords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paper_keywords` (
  `id` int(11) NOT NULL,
  `keyword` varchar(45) NOT NULL,
  PRIMARY KEY (`id`,`keyword`),
  KEY `pk_p` (`id`),
  CONSTRAINT `pk_p` FOREIGN KEY (`id`) REFERENCES `papers` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paper_keywords`
--

LOCK TABLES `paper_keywords` WRITE;
/*!40000 ALTER TABLE `paper_keywords` DISABLE KEYS */;
INSERT INTO `paper_keywords` VALUES (1,'cognitive science'),(1,'data mining'),(1,'database'),(1,'human memory'),
									(2,'C#'),(2,'IIS'),(2,'Java'),(2,'performance'),(2,'PHP'),(2,'SOAP'),(2,'Tomcat'),(2,'web services'),(2,'XML'),
                                    (3,'course assignment'),(3,'department management'),(3,'faculty'),(3,'tools'),(3,'Web 2.0'),
                                    (4,'abduction'),(4,'curriculum'),(4,'education'),(4,'FITness'),(4,'informatics'),(4,'IT fluency'),
                                    (5,'IT'),(6,'accessibility'),(7,'GIS'),(7,'web'),(8,'accessibility'),(8,'web'),(9,'analytics'),(9,'education'),
                                    (10,'web services'),(10,'web'),(10,'bioinformatics'),(11,'art'),(11,'education'),(11,'curriculum'),
                                    (12,'database'),(12,'cloud computing');
/*!40000 ALTER TABLE `paper_keywords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `papers`
--

DROP TABLE IF EXISTS `papers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `papers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `abstract` text,
  `citation` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `papers`
--

LOCK TABLES `papers` WRITE;
/*!40000 ALTER TABLE `papers` DISABLE KEYS */;
INSERT INTO `papers` (title,abstract,citation) VALUES ('TED','A new database architecture that is modeled after the human brain. It will overcome the limitations the RDBMS architecture places on data mining.',''),
													  ('Think Inside the Box! Optimizing Web Service Performance, Today','While Web Services Technology holds great promise for universal integration, several obstacles stand in the way of its acceptance. Work is being done to address these obstacles to allow adoption of web services technology in the future, but where do we stand today? In particular, what can be done today to combat the often cited problem of slow response times for web services? While XML hardware acceleration and SOAP compression schemes can improve the overall response, the authors have found that appropriate selection of client software, server software, and data structures can have a substantial impact. It is possible to have a profound impact on performance using tools that are routinely and dependably available to us now.','Zilora, Stephen, and Sai Sanjay Ketha. \"Think Inside the Box! Optimizing Web Services Performance Today.\" IEEE Communications Magazine, 46.3 (2008): 112-117.'),
                                                      ('Work in Progress - Bringing Sanity to the Course Assignment Process','The floor of the NY Stock Exchange, with its noise and chaos, is an apt depiction of the typical course selection meeting that many universities experience. Professors attempt to shout over their colleagues or broker deals with one another in small cabals in an attempt to garner the sections they hope to teach. When first choices fall by the wayside, quick recalculations of schedules are necessary in order to determine the next best scenario and sections to request. As inexperienced junior faculty members, the authors found this experience daunting. In response, they wrote a simple web application that allowed faculty to make their selections, and broker deals, in a calm manner over an extended time period. The application was originally written for one sub-group of about 20 faculty within the department, but its popularity quickly spread to the rest of the department and then on to other departments within the college. The course assignment and reservation system (CARS) has grown steadily over the past several years in number of users, functionality, and scope. Today, faculty can plan their teaching load, work with colleagues to find mutually beneficial schedules, and easily retrieve historical information in preparation for annual reviews, promotion, or tenure appointments. Department administrators can manage course information, prepare information for certification agencies, assign faculty to courses, and monitor faculty loads. Staff and students also benefit from interfaces permitting access to appropriate information to assist them in their planning activities. Utilizing Web 2.0 technologies, the application is enjoyable to use and gives all of the disparate users a satisfying experience.','Zilora, S.J, and D.S Bogaard. \"Work in Progress - Bringing Sanity to the Course Assignment Process.\" Frontiers in Education Conference, 2008. FIE 2008. 38th Annual, (2008)'),
                                                      ('Informatics minor for non-computer students','The Rochester Institute of Technology\'s School of Informatics has developed a minor in Applied Informatics that allows non-computing students from throughout the university to learn problem solving, data retrieval, and information processing and presentation skills so that they can be productive knowledge workers in the 21st century. The minor is strongly problem-oriented with students being taught how to apply deductive, inductive, and abductive reasoning, as well as fundamental information technology skills, to problems in their specific domains. It is the coursework\'s relevance and applicability to the students\' majors that eases the acquisition of these skills.','Zilora, S.J. \"Informatics minor for non-computer students\" ACM SIGITE 2011 Conference (2011)'),
                                                      ('The Changing Face of Information Technology','Information technology as an academic discipline began in the early 90\'s. Since then, there have been many changes in how industry views the discipline. Today, information technology is about large-scale operations. This may be manifested as supporting enterprise services, working with big data, or supporting massive multi-user systems. In this paper, we describe a new curriculum that is based upon the original work in the "2008 Curriculum Guidelines for Undergraduate Degree Programs in Information Technology" document, but addresses modern information technology demands. We discuss a new curricular model for teaching information technology and also the addition of analytics as an overarching theme for the curriculum.','Zilora, Stephen J, Daniel S Bogaard, and Jim Leone. "The Changing Face of Information Technology." Proceedings of the SIGITE/RIIT 2013. Ed. William D Armitage, Rob Friedman, and Ken Baker. Orlando, FL: ACM, 2013. Print.'),
                                                      ('How Accessible Are Open Education Websites?','Open education, the movement where educational materials are posted online for anyone to access for free, has the potential to remove many barriers to education. For example, anyone with an Internet connection can view lectures from various universities, whose resources were previously unavailable to non-students. No longer are the teachings of subject matter experts in a particular domain reserved for those who are in close proximity to an educational institution or can afford the cost of tuition.  Several higher education institutions, consortiums, and organizations have taken the initiative to give anyone access to their educational resources or have provided a means for people to post educational content. However, are these online resources and materials usable by everyone? For this to be true, Web accessibility must be considered. Web accessibility guarantees that individuals with "visual, auditory, physical, speech, cognitive, and neurological disabilities" are able to "perceive, understand, navigate, and interact with the Web, and that they can contribute to the Web" (Introduction to Web Accessibility, 2005). This study includes an analysis of several open education websites to determine whether open education materials are accessible to all. The sites are examined against the World Wide Web Consortium\'s (W3C) Web Content Accessibility Guidelines (WCAG). The findings will be reported and recommendations for improving the accessibility of open education websites will be discussed.','Weeden, Elissa. "How Accessible Are Open Education Websites?" International Conference of Education, Research and Innovation (iCERI 2011). International Association for Technology, Education and Development. Melia Castilla Madrid, Madrid, Spain. 14-16 Nov. 2011. Conference Presentation.'),
                                                      ('Supporting Geographically-aware Web Document Foraging and Sensemaking','This paper reports on the development and application of strategies and tools for geographic information seeking and knowledge building that leverages unstructured text resources found on the web. Geographic knowledge building from unstructured web sources starts with web document foraging during which the quantity, scope and diversity of web-based information create incredible cognitive burdens on an analyst\'s or researcher\'s ability to judge information relevancy. Determining information relevancy is ultimately a process of sensemaking. In this paper, we present our research on visually supporting web document foraging and sensemaking. In particular, we present the Senseof-Place (SensePlace) analytic environment. The scientific goal of SensePlace is to visually and computationally support analyst sensemaking with text artifacts that have potential place, time, and thematic relevance to an analytical problem through identification and visual highlighting of named entities (people, places, times, and organizations) in documents, automated inference to determine document relevance using stored knowledge, and a visual interface with coupled geographic map, timeline, and concept graph displays that are used to contextualize the contexts of potentially relevant documents. We present the results of a case study analysis using SensePlace to uncover potential population migration, geopolitical, and other infectious disease dynamics drivers for measles and other epidemics in Niger. Our analysis allowed us to demonstrate how our approach can support analysis of complex situations along (a) multi-scale geographic dimensions (i.e., vaccine coverage areas), (b) temporal dimensions (i.e., seasonal population movement and migrations), and (c) diverse thematic dimensions (effects of political upheaval, food security, transient movement, etc.).','Tomaszewski, Brian, et al. "Supporting Geographically-aware Web Document Foraging and Sensemaking." Computers, Environment and Urban Systems 35. 3 (2011): 192-201. Print.'),
                                                      ('Progress on Website Accessibility?','Over 100 top-traffic and government websites from the United States and United Kingdom were examined for evidence of changes on accessibility indicators over the 14-year period from 1999 to 2012, the longest period studied to date. Automated analyses of WCAG 2.0 Level A Success Criteria found high percentages of violations overall. Unlike more circumscribed studies, however, these sites exhibited improvements over the years on a number of accessibility indicators, with government sites being less likely than topsites to have accessibility violations. Examination of the causes of success and failure suggests that improving accessibility may be due, in part, to changes in website technologies and coding practices rather than a focus on accessibility per se.','Hanson, Vicki L. and John T. Richards. "Progress on Website Accessibility?" ACM Transactions on the Web 7. 1 (2013): Article 2, (March 2013), 30 pages. Print.'),
                                                      ('Design of an Analytic Centric MS Degree in Information Sciences and Technologies.','In this paper, we present the design, development, and offering of a new Master of Science (MS) curriculum in Information Sciences and Technologies (IST) at Rochester Institute of Technology (RIT). The new curriculum resulted as we enhanced our original MS curriculum to accommodate to the key technological advances in computing as well as the new demands in IT industry. The new curriculum is featured by its analytic centric foundation that provides students a systematic training in analytical thinking and equips them with a solid skill set to manage and analyze different types of data in scale. A number of concentration tracks are also offered that are build upon the foundation to further advance students\' learning and expose them to the state-of-the-art in key computing domains.','Kang, Jai, Edward P. Holden, and Qi Yu. "Design of an Analytic Centric MS Degree in Information Sciences and Technologies." Proceedings of the SIG-ITE. Ed. ACM. Atlanta, GA: ACM, 2014. Web.'),
                                                      ('Web Service Management System for Bioinformatics Research: A Case Study','In this paper, we present a case study of the design and development of a Web Service management system for bioinformatics research. The described system is a prototype that provides a complete solution to manage the entire life cycle of Web Services in bioinformatics domain, which include semantic service description, service discovery, service selection, service composition, service execution, and service result presentation. A challenging issue we encountered is to provide the system capability to assist users to select the “right” service based on not only functionality but also properties such as reliability, performance, and analysis quality. As a solution, we used both bioinformatics and service ontology to provide these two types of service descriptions. A service selection algorithm based on skyline query algorithm is proposed to provide users with a short list of candidates of the “best” service. The evaluation results demonstrate the efficiency and scalability of the service selection algorithm. Finally, the important lessons we learned are summarized, and remaining challenging issues are discussed as possible future research directions.','Xu, Kai, et al(Yu, Qi). "Web Service Management System for Bioinformatics Research: A Case Study." Service Oriented Computing and Applications 5. 1 (2011): 1-15. Print.'),
                                                      ('Blending Art and Technology: Two Courses and Some MAGIC','Universities generally separate art and technology, erecting physical and administrative walls between them.  This division is artificial and was not always extant.  Leonardo Da Vinci found no division between art and technology, and neither do we.  We blend them in both the classroom and in our scholarship.  Developing technology requires creativity and art requires understanding technology.','Vullo, Ronald P., et al. "Blending Art and Technology: Two Courses and Some MAGIC." Proceedings of the International Conference on Frontiers in Education: Computer Science and Computer Engineering. Ed. Hamid R. Arabnia, et al. Las Vegas, NV: n.p., 2014. Print.'),
                                                      ('Databases in the Cloud: A status report','This paper updates an earlier paper on the use of cloud computing in database curriculum. That paper described a curricular initiative in cloud computing initially intended to keep our information technology (IT) curriculum at the forefront of technology and to give students the flexibility to work at any location, not just our labs. Currently, our IT degrees offer extensive database concentrations at both the undergraduate (BS-IT) and graduate (MS-IT) levels. This paper reports on the results of two years of operation using a cloud provider for lab exercises in our Database Architecture and Implementation course. It discusses the benefits gained and concludes with an overview of a new cloud deployment strategy that improves disaster planning for our curricular infrastructure and provides an extension to another campus. We discuss how the Cloud Vendor Selection Model, proposed in our previous paper, shows the ways in which the different layers of cloud services interact with each other. Plus we show how the different categories of cloud users in this model can be supported by different educational tools to meet course objectives. Finally, we discuss the various issues and challenges that we have experienced when implementing cloud solutions in an educational environment.','Holden, E.P., J.W. Kang, Anderson, G.R., D.P. Bills, M. Databases in the Cloud: A status report. ACM SIGITE Conference 2011, October 20-22, 2011 West Point, NY.');
/*!40000 ALTER TABLE `papers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `speakingrequest`
--

DROP TABLE IF EXISTS `speaking_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `speaking_requests` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `requesterName` varchar(50) NOT NULL,
  `email` varchar(45) NOT NULL,
  `phone` varchar(10),
  `request` varchar(250) NOT NULL,
  `facultyId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_f` (`facultyId`),
  CONSTRAINT `fk_f` FOREIGN KEY (`facultyId`) REFERENCES `faculty` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-01-07 13:50:42
