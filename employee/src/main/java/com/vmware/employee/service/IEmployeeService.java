package com.vmware.employee.service;

import org.springframework.web.multipart.MultipartFile;

public interface IEmployeeService {
	
	void saveEmployees(long taskId, MultipartFile file);

}
