package com.vmware.employee.error;

/**
 *Custom Exception that is thrown when an invalid task id is given by user.
 */
public class TaskException extends RuntimeException {
	
	public TaskException(final String message) {
		super(message);
	}

}
