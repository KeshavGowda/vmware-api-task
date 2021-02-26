package com.vmware.employee.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vmware.employee.dao.IEmployeeDao;
import com.vmware.employee.models.Employee;
import com.vmware.employee.service.IEmployeeService;
import com.vmware.employee.service.ITaskService;

@Service
public class EmployeeServiceImpl implements IEmployeeService {
	
	private static final Logger loggger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private IEmployeeDao employeeDao;
	
	@Autowired
	private ITaskService taskService;

	/**
	 * Saves the employee information in the database. This method takes in a task id and an input file containing Employee info.
	 * This method asynchronously processes each request.
	 * @param taskId Task id to be used to process this request.
	 * @param File file containing Employee info.
	 */
	@Async
	@Override
	public void saveEmployees(final long taskId, final MultipartFile file) {
		loggger.info(String.format("Processing task id %d", taskId));
		taskService.updateTaskStatus(taskId, "In Progress");
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
			final List<Employee> employees = new ArrayList<>();
			String row;
			while ((row = reader.readLine()) != null) {
				row = row.trim().replaceAll("\\s+", " ");
				int ageColumnIndex = row.lastIndexOf(' ');
				String employeeName = row.substring(0, ageColumnIndex);
				int age = Integer.valueOf(row.substring(ageColumnIndex + 1));
				Employee emp = new Employee();
				emp.setName(employeeName);
				emp.setAge(age);
				employees.add(emp);
			}
			employeeDao.saveAll(employees);
			taskService.updateTaskStatus(taskId, "Success");
			loggger.info(String.format("Successfully processed task id %d", taskId));
		} catch (Exception e) {
			taskService.updateTaskStatus(taskId, "Failed");
			String message = String.format("Error when processing task id : %d. Cause: %s", taskId, e.getMessage());
			loggger.error(message, e);
		}
	}

}
