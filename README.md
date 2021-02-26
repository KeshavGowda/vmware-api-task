# vmware-api-task

## Pre-requisites
- Java 11
- Maven

## Steps to run the project
- Clone the repository and import the employee project into an eclipse workspace.
- Run the main class EmployeeApplication.java.
- To run the test cases, run the file EmployeeApplicationTests.java as junit test.

The application has 2 endpoints-

#####  POST endpoint located at /api/employee?action=upload
- This takes a multipart file as input. I have used both MockMvc and Postman client to test this api.
- Calling this api using MockMvc is demostrated in test cases present in EmployeeApplicationTests.java.  
- To call this api using Postman, follow the below steps
	* create a new post api call and enter the url. For example: http://localhost:8080/api/employee
	* In the parm section, add "action" in key field and "upload" in value field. 
	* In the body section, select form-data. Then enter "file" as key and in the value field select the desired input file.
	* Submit the request. Each request is assigned a unique task id. This request will be processed asynchronously in the backend and processed result will be saved in the database. 
	* After submitting the request we get a response object containing a taskid and url to check the status of the request. <br />
	  {
		  "taskId": 4,
		  "statusCheckUrl": "/api/getStatus/4"
	  }

- statusCheckUrl is a get request to track the progress. More on this below.
- The application uses H2 in-memory database to facilitate the ease of testing. In real world, this can be replaced with a complete database solution such as MySql.
We can access the H2 console from the browser using the url http://localhost:8080/h2-console/ and view the updates in employee and task tables.

##### GET endpoint located at /api/getStatus
- This takes task id as path variable input. For example: http://localhost:8080/api/getStatus/123
- Returns the current status of the task. Possible values are follows
  * Not Started
  * In Progress
  * Success
  * Failed
