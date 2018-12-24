drop table if exists comment, Picture, Audio, User, News, Modified;

CREATE TABLE News(
Id_news         int(32) NOT NULL AUTO_INCREMENT,
Title         varchar(40),
Content         varchar(1000),
Last_modified         date,
PRIMARY KEY (Id_news)
);


CREATE TABLE User(
Username         varchar(30),
Password         varchar(30),
Id_user         int(32) NOT NULL AUTO_INCREMENT,
PRIMARY KEY(Id_user),
INDEX(Username)
);

CREATE TABLE Comment(
Id_comment         int(32) NOT NULL AUTO_INCREMENT,
Content         varchar(200),
Id_news         int(32) NOT NULL,
Id_user         int(32) NOT NULL,
Post_date         date,
PRIMARY KEY(Id_comment)
);


ALTER TABLE Comment ADD CONSTRAINT Id_news_FK_comment FOREIGN KEY(Id_news) REFERENCES News(Id_news);

ALTER TABLE Comment ADD CONSTRAINT Id_user_FK_comment FOREIGN KEY(Id_user) REFERENCES User(Id_user);

CREATE TABLE Picture(
Id_picture         int(32) NOT NULL AUTO_INCREMENT,
Name         varchar(30),
Description         varchar(40),
Id_news         int(32) NOT NULL,
PRIMARY KEY(Id_picture)
);


ALTER TABLE Picture ADD CONSTRAINT Id_news_FK_picture FOREIGN KEY(Id_news) REFERENCES News(Id_news);

CREATE TABLE Audio(
Description         varchar(30),
Name         varchar(40),
Id_audio         int(32) NOT NULL AUTO_INCREMENT,
Id_news         int(32) NOT NULL,
PRIMARY KEY(Id_audio)
);

ALTER TABLE Audio ADD CONSTRAINT Id_news_FK_audio FOREIGN KEY(Id_news) REFERENCES News(Id_news);

CREATE TABLE Modified(
Id_modified		int(32) NOT NULL AUTO_INCREMENT,
Id_user         int(32) NOT NULL,
Id_news         int(32) NOT NULL,
Modified_date         date,
PRIMARY KEY(Id_modified)
);


ALTER TABLE Modified ADD CONSTRAINT Id_user_FK_created FOREIGN KEY(Id_user) REFERENCES User(Id_user);

ALTER TABLE Modified ADD CONSTRAINT Id_news_FK_created FOREIGN KEY(Id_news) REFERENCES News(Id_news);

