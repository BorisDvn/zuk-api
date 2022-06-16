package com.thb.zukapi.exception;

public class ApiRequestException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7069834071822986750L;

	public ApiRequestException(String message) {
        super(message);
    }

}
