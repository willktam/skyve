package org.skyve.metadata;

/**
 * 
 */
public class MetaDataException extends RuntimeException {
	private static final long serialVersionUID = 1447684367914523647L;

	/**
	 * 
	 * @param cause
	 */
	public MetaDataException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 * @param message
	 */
	public MetaDataException(String message) {
		super(message);
	}

	/**
	 * 
	 * @param message
	 * @param cause
	 */
	public MetaDataException(String message, Throwable cause) {
		super(message, cause);
	}
}
