package com.app.kowalski.project.exception;

@SuppressWarnings("serial")
public class ProjectServiceException extends Exception {

	public ProjectServiceException () {}

	public ProjectServiceException(String message) {
		super(message);
	}

	public ProjectServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}
