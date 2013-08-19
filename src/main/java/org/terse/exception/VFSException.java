package org.terse.exception;

/**
 * 用于基于VFS的上传下载的异常类
 *
 */
public class VFSException extends Exception {

	private static final long serialVersionUID = 2365411513102039903L;

	public VFSException() {
		super();
	}
	
	public VFSException(String msg) {
		super(msg);
	}
	
	public VFSException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public VFSException(Throwable cause) {
		super(cause);
	}
}
