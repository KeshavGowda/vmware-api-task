package com.vmware.employee.service;

public interface ITaskService {
	
	long getUniqueTaskId();
	
	void updateTaskStatus(long taskId, String status);
	
	String getTaskStatus(long taskId);

}
