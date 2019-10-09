README:
Please note that the project has a javadoc generated for your convenience in the ~/doc directory.

The following are some of the technologies or features added to this application in some form (whether used at the end or not):
	Spring; HTML/CSS; JavaScript; Mithril; Spring Boot; Java; Multithreading; AWS RDS; JUnit; Maven;
	
The AWS RDS database should be running during your code review.

Regarding our interview, I added a configuration file for implementing Spring beans as a dependency injection tool.  There are not any beans
currently in the file being used but I added an example using the Player class.  This is in the servletConfig/RootApplicationContextConfig.java file.

The blackjack game is not where I would like for it to be.  Unfortunately, I had to spend a chunk of time on college assignments for my two courses.
That took a good amount of time as I am in a project management course that takes up more time that I'd like as well as a Javascript course.  There are still a few bugs that I have not had time to work out.  That being said, I was able to implement a lot of code in about 4 - 4.5 days.  Please consider that in your review and imagine what I could have done in a typical sprint on this project.

This next block describes some of the features I included in the application.

Using utils/GameState.java and utils/GameStateUpdater.java I was able to implement a multithreaded solution to continuously update the the GameState objects
and is designed to update the database.  The current default is set in the yaml configuration files and runs at an interval of 3.5 seconds.



PACKAGES:

A little about each package in the application:

The config package contains the configurations for various pieces of the application stored in yaml files

The controllers package was one of the packages meant for the UI I was building but ran out of time on.  It was meant to have a Spring w/Spring Boot
backend and a pure HTML and Javascript frontend with requests done using Mithril.  I, however, ran out of time to develop it.

The db package contains the database implementations.  I am currently using PostgreSQL stored AWS RDS.  The credentials are already stored in the 
application in a config files

The domain package contains all my game objects used throughout the application

The exceptions package stores the custom exceptions.

The game package holds the entry point for the application and the console application that serves as the user interface.

The managers package keeps the two classes designed to maintain certain parts of the game like the database.

I was using the security package to look into implementing AWS security features.

The servletConfig package was part of the Spring backend for the UI

The tests package has some JUnit tests I have been building.

The utils package has various utilities used to monitor the application and to ensure greater efficiency.

Finally, the wager package has the code for the wagering system.
