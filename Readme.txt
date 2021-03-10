-----------------------------
About the Climate Summary project  
------------------------------
- Java version :1.8
- Spring Boot: (v2.4.3)
- SpringVersion:5.3.4

- Project load and the CSV file (resources/eng-climate-summary.csv) on application start up.
- Store the the data fromCSV file H2 (inMemeory) database for ease of use and to perform some query on data
- Project follow the MVC arichtecture
- FrontEnd uses the thymeleaf, HTMl, CSS, jQuery, ajax and some bootstraps
- it has used daterange picker to find the data between selected date.
- user can sort the data from the column header, it has pagination with pageSize (number of record per page to view)
- open the detail page by clcking on the any 'mean Temp' data. on clicking 'Go Back' button in detail page will take back to main page 
  by maintaining the search criteria alogn with pagination and sorting


Mockito Test and JUnit test using Junit 5
---------------------
To run from command prompt:
--------------------
Download the project and extract it.
Take a build by using command mvn clean build
Go to build/libs directory, run the jar file by using the command JAVA -JAR
1) go to project folder (cd  climate_demo)
2) mvn spring-boot:run
