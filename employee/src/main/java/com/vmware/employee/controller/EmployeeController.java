package com.vmware.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vmware.employee.models.TaskResponse;
import com.vmware.employee.service.IEmployeeService;
import com.vmware.employee.service.ITaskService;

@RestController
@RequestMapping(path = "/api")
public class EmployeeController {
	
	@Autowired
	private ITaskService taskService;
	
	@Autowired
	private IEmployeeService employeeService;
	
	private static final String STATUS_CHECK_URL = "/api/getStatus/";
	
	@PostMapping(value = "/employee")
	public ResponseEntity<TaskResponse> saveEmployees(@RequestParam(name = "action") final String action, @RequestParam(name = "file") final MultipartFile file) {
		final long uniqueTaskId = taskService.getUniqueTaskId();
		final TaskResponse taskResponse = new TaskResponse(uniqueTaskId, STATUS_CHECK_URL + uniqueTaskId);
		employeeService.saveEmployees(uniqueTaskId, file);
		return new ResponseEntity<>(taskResponse, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getStatus/{taskId}")
	public String getStatus(@PathVariable long taskId) {
		return taskService.getTaskStatus(taskId);
	}
	
}
