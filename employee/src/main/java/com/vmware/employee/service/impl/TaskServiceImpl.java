package com.vmware.employee.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vmware.employee.dao.ITaskDao;
import com.vmware.employee.error.TaskException;
import com.vmware.employee.models.TaskEntity;
import com.vmware.employee.service.ITaskService;

@Service
public class TaskServiceImpl implements ITaskService {
	
	private static final Logger loggger = LoggerFactory.getLogger(TaskServiceImpl.class);
	
	@Autowired
	private ITaskDao taskDao;
	
	@Value("${file.upload-dir}")
	private String fileUploadDir;
	
	/**
	 * Copies the given input file locally for later processing.
	 * 
	 * @param inputFile User uploaded input file.
	 * @return File path of the copied file.
	 * @throws IOException 
	 */
	@Override
	public String saveInputFile(final MultipartFile inputFile) {
		final String fileName = fileUploadDir + inputFile.getOriginalFilename();
		File file  = new File(fileName);
		try {
			inputFile.transferTo(file);
		} catch (IOException e) {
			String message = String.format("Error when uploading input file", e.getMessage());
			loggger.error(message, e);
			throw new TaskException(message);
		} 
		return file.getAbsolutePath();
	}

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
