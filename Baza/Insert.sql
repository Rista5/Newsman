--USER INSERT SQL
INSERT INTO `aips`.`user` (`Username`, `Password`) VALUES ('Sreten', 'Popokatepetl');
INSERT INTO `aips`.`user` (`Username`, `Password`) VALUES ('Vukic', 'lisp4life');

INSERT INTO `aips`.`user` (`Username`, `Password`) VALUES ('Zika_Sarenica', 'nedostajemiRTS');

INSERT INTO `aips`.`user` (`Username`, `Password`) VALUES ('KalasNosimBotoveKosim', '@lkaiD@');



--News INSERT SQL
INSERT INTO `aips`.`news` (`Title`, `Content`, `Last_modified`) VALUES ('Kako sam upisivao u bazu', 'Upisivah ja tako u bazu , slovo po slovo, rec po rec. U glavi mi se motaju ruzicasti slonovi i cirkuski misevi. A ja kucam. ', '2018-12-19');

INSERT INTO `aips`.`news` (`Title`, `Content`, `Last_modified`) VALUES ('Haiku', 'Vatra pod pepelom. Kuca pod snegom. Ponoc.', '2018-11-14');

INSERT INTO `aips`.`news` (`Title`, `Content`, `Last_modified`) VALUES ('Bosanac u kanadi ubio cistaca snega.', '\"A onaj majmun s grtalicom dosao kod mene da trazi lopatu...\"', '2018-12-08');



--Comment INSERT SQL
INSERT INTO `aips`.`comment` (`Content`, `Id_news`, `Id_user`, `Post_date`) VALUES ('Najveca glupost koju sam cuo u poslednje vreme', '2', '2', '2018-12-19');

INSERT INTO `aips`.`comment` (`Content`, `Id_news`, `Id_user`, `Post_date`) VALUES ('Toliko je lepo da sam se okrenula zen budizmu.', '3', '4', '2018-11-20');

INSERT INTO `aips`.`comment` (`Content`, `Id_news`, `Id_user`, `Post_date`) VALUES ('Bosna uzvraca udarac!!!', '4', '1', '2018-12-31');

INSERT INTO `aips`.`comment` (`Content`, `Id_news`, `Id_user`, `Post_date`) VALUES ('Ocu intervju!', '4', '3', '2018-12-30');



--Created INSERT SQL
INSERT INTO `aips`.`created` (`Id_user`, `Id_news`, `Creation_date`) VALUES ('1', '2', '2018-11-20');

INSERT INTO `aips`.`created` (`Id_user`, `Id_news`, `Creation_date`) VALUES ('2', '4', '2018-11-23');

INSERT INTO `aips`.`created` (`Id_user`, `Id_news`, `Creation_date`) VALUES ('3', '4', '2018-12-11');

INSERT INTO `aips`.`created` (`Id_user`, `Id_news`, `Creation_date`) VALUES ('4', '3', '2018-12-12');

INSERT INTO `aips`.`created` (`Id_user`, `Id_news`, `Creation_date`) VALUES ('1', '1', '2018-12-24');

INSERT INTO `aips`.`created` (`Id_user`, `Id_news`, `Creation_date`) VALUES ('3', '3', '2018-12-17');



--audio INSERT SQL
INSERT INTO `aips`.`audio` (`Description`, `Name`, `Id_news`) VALUES ('Led Zepplin-Ramble On', 'Haiku', '3');

INSERT INTO `aips`.`audio` (`Description`, `Name`, `Id_news`) VALUES ('Mali zvuk', 'Sound of Silance', '2');

INSERT INTO `aips`.`audio` (`Description`, `Name`, `Id_news`) VALUES ('IDEMO BOSNA!', '123gb2', '4');


--pictures INSERT SQL
INSERT INTO `aips`.`picture` (`Name`, `Description`, `Id_news`) VALUES ('Portret', 'Portret sa slonovima', '2');
