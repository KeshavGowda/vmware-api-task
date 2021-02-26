package com.vmware.employee.service;

import org.springframework.web.multipart.MultipartFile;

public interface ITaskService {
	
	String saveInputFile(MultipartFile inputFile);
	
	long getUniqueTaskId();
	
	void updateTaskStatus(long taskId, String status);
	
	String getTaskStatus(long taskId);

}
