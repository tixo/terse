package org.terse.file;

/**
 * 附件对象
 * 
 */
public class UploadFile {
	
	/**
	 * 文件
	 */
	private String name;
	
	/**
	 * 存储路径
	 */
	private String path;
	
	/**
	 * 文件大小
	 */
	private long size;
	
	/**
	 * 文件服务器ID
	 */
	private String fileServerId;
	
	/**
	 * 文件后缀
	 */
	private String suffix;
	
	private String fileType;
	
	private String creator;
	
	private String creatorId;
	
	private String editor;
	
	private String editorId;
	
	private String fileId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getFileServerId() {
		return fileServerId;
	}

	public void setFileServerId(String fileServerId) {
		this.fileServerId = fileServerId;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getEditorId() {
		return editorId;
	}

	public void setEditorId(String editorId) {
		this.editorId = editorId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
}
