Download the project from https://github.com/kirtimistry2001/climate_demo/tree/master and extract it.
make sure you have Maven installed. if not then download it from https://maven.apache.org/download.cgi
set the MAVEN_HOME and Path variable

-------------------------------
To run from command prompt:
-----------------------------

1) open the command prompt/Terminal and go to the extracted project Directory/Folder
2) check if Maven installed or not by using mvn -version command
3) Build the project by using command mvn clean install
4) type:  mvn spring-boot:run
5) To test the test cases: mvn test

----------------------------------------
About the Historic Climate Summary project  
------------------------------------------
- Java version :1.8
- Spring Boot: (v2.4.3)
- SpringVersion:5.3.4

- Project read data from the CSV file (resources/eng-climate-summary.csv) and write it to H2 database on application start up to perform some query on data
- Project follow the MVC arichtecture
- FrontEnd uses the thymeleaf, HTMl, CSS, jQuery, ajax and some bootstraps
- it has used daterange picker to find the data between selected date.
- user can sort the data from the column header, it has pagination with pageSize (number of record per page to view)
- open the detail page by clcking on the any 'mean Temp' data. on clicking 'Go Back' button in detail page will take back to main page 
  by maintaining the search criteria alogn with pagination and sorting
- display meaningful error page if user try to access invalid/worng url oe if there is any internal error occurs.

Mockito Test and JUnit test using Junit 5
