package com.vmware.employee.models;

/**
 * Task response object.
 */
public class TaskResponse {
	
	private long taskId;
	private String statusCheckUrl;
	
	public TaskResponse(long taskId, String statusCheckUrl) {
		super();
		this.taskId = taskId;
		this.statusCheckUrl = statusCheckUrl;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getStatusCheckUrl() {
		return statusCheckUrl;
	}

	public void setStatusCheckUrl(String statusCheckUrl) {
		this.statusCheckUrl = statusCheckUrl;
	}

}
