README

--Title--
Appointment Scheduler

--Purpose--
A GUI based application that allows for users to track customers and assigned appointments.

--Author--
Jacob Wallace
Student #009925162
jwa1305@wgu.edu
Completed on 05/25/2023

--Built With--
IDE: IntelliJ IDEA 2021.1.3 (Community Edition)
Java JDK: java SE 17.0.6 [LTS]
JavaFx: javafx-SDK-17.0.7 [LTS]
MySQL Driver: mysql-connector-j-8.0.33

--How to Run--
Links for necessary downloads:
Download IntelliJ IDEA 2021.1.3 here: https://www.jetbrains.com/idea/download/other.html
I used the 2021.1.3 - macOS Apple Silicon (dmg) download
Download the MySQl Driver for Java here: https://dev.mysql.com/downloads/connector/j/
I used the platform independent zip file
Download the JavaFX 17.0.7 [LTS] library here: https://gluonhq.com/products/javafx/
I used the MacOS aarch64 SDK

Setting up IntelliJ:
Open the project folder in IntelliJ
Navigate to File -> Project Structure -> Libraries
Click the + icon and select java, then navigate to your MySQL Jar file
Repeat these steps for the JavaFX lib folder
You will now need to set your PATH_TO_FX variable to your JavaFX directory
Navigate to IntelliJ IDEA -> Settings -> Appearance and Behavior -> Path Variables
Click the + Icon and name the variable PATH_TO_FX and have it point to wherever javafx-sdk-17.0.7/lib was installed

Select Run -> Run Main

--Additional Report--

The additional report that I chose to implement was a user report.  It retrieves all of the appointments
for a selected user and displays them in a tableview.

