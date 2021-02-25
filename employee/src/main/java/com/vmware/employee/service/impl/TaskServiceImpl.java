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

	@Override
	public long getUniqueTaskId() {
		TaskEntity taskEntity = new TaskEntity();
		taskEntity.setTaskStatus("Not Started");
		taskEntity = taskDao.save(taskEntity);
		return taskEntity.getTaskId();
	}

	@Override
	public void updateTaskStatus(long taskId, String status) {
		Optional<TaskEntity> taskEntityValue = taskDao.findById(taskId);
		if (taskEntityValue.isPresent()) {
			TaskEntity task = taskEntityValue.get();
			task.setTaskStatus(status);
			taskDao.save(task);
		}
	}

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
