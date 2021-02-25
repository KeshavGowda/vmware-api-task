package com.vmware.employee.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tasks")
public class TaskEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long taskId;
	private String taskStatus;
	
	public long getTaskId() {
		return taskId;
	}
	
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	
	public String getTaskStatus() {
		return taskStatus;
	}
	
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	
}
