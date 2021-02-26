package com.vmware.employee.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vmware.employee.dao.ITaskDao;
import com.vmware.employee.error.TaskException;
import com.vmware.employee.models.TaskEntity;
import com.vmware.employee.service.ITaskService;

@Service
public class TaskServiceImpl implements ITaskService {
	
	@Autowired
	private ITaskDao taskDao;

	/**
	 * Creates a new entry for a task in the database and returns the unique id generated.
	 * 
	 * @return Unique task id.
	 */
	@Override
	public long getUniqueTaskId() {
		TaskEntity taskEntity = new TaskEntity();
		taskEntity.setTaskStatus("Not Started");
		taskEntity = taskDao.save(taskEntity);
		return taskEntity.getTaskId();
	}

	/**
	 * Updates the task with the given id with the given status value.
	 * 
	 * @param taskId Task id.
	 * @param status Status to be updated.
	 */
	@Override
	public void updateTaskStatus(long taskId, String status) {
		Optional<TaskEntity> taskEntityValue = taskDao.findById(taskId);
		if (taskEntityValue.isPresent()) {
			TaskEntity task = taskEntityValue.get();
			task.setTaskStatus(status);
			taskDao.save(task);
		}
	}

	/**
	 * Returns the status of the given task id.
	 * 
	 * @param taskId Task id.
	 * @return Status of the task.
	 */
	@Override
	public String getTaskStatus(long taskId) {
		Optional<TaskEntity> taskEntityValue = taskDao.findById(taskId);
		if (taskEntityValue.isPresent()) {
			return taskEntityValue.get().getTaskStatus();
		} else {
			throw new TaskException(String.format("Could not find task with taskid: %d", taskId));
		}
	}

}
