package org.terse.file;

import org.terse.struts.BaseAction;
import org.terse.util.RemoteFileManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 用于上传的基类action
 * 
 */
public abstract class UploadAction extends BaseAction {

	private static final long serialVersionUID = -2230908892806369334L;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Fields ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	protected List<UploadFile> uploadFiles = new ArrayList<UploadFile>();
	
	protected List<File> upload;

	protected List<String> uploadContentType; 

	protected List<String> uploadFileName;
	
	protected List<String> fileTypes;
	
	protected String attachment_creator;
	protected String attachment_creatorId;
	protected String attachment_editor;
	protected String attachment_editorId;
	protected String attachement_fileId;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Methods ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * 有参构造函数，需要传入配置名和上传路径
	 * 
	 * @param remoteFileServerCfgName 配置名
	 * @param dir 上传路径
	 * @return 文件集合
	 * @throws java.io.IOException
	 */
	protected List<UploadFile> uploadFile(Map<String, String> cfg, String dir) throws IOException {
		
		try {
			if(upload==null || upload.size()<=0){
				return null;
			}
			
			RemoteFileManager mgr = new RemoteFileManager();
			mgr.setCfg(cfg);
			for(int i=0;i<upload.size();i++){
				File file=upload.get(i);
				
				UploadFile uf = new UploadFile();
				String _uploadFileName=uploadFileName.get(i);
				String suffix = "";
				if(_uploadFileName.lastIndexOf(".") > 0) {
					suffix = _uploadFileName.substring(_uploadFileName.lastIndexOf("."),_uploadFileName.length());
				}
				
				// 上传至远程文件服务器
				String[] status;
				if(dir == null || "".equals(dir)) {
					status = mgr.upload(_uploadFileName, file);
				} else {
					status = mgr.upload(_uploadFileName, file, dir);
				}

				uf.setName(status[0]);
				uf.setPath(status[1]);
				uf.setSize(Long.parseLong(status[2]));
				uf.setSuffix(suffix);
				uf.setFileServerId(status[3]);
				if(fileTypes != null && fileTypes.size() > 0) {
					uf.setFileType(fileTypes.get(i));
				}
				uf.setCreator(attachment_creator);
				uf.setCreatorId(attachment_creatorId);
				uf.setEditor(attachment_editor);
				uf.setEditorId(attachment_editorId);
				uf.setFileId(attachement_fileId);
				
				uploadFiles.add(uf);
			}
			
			return uploadFiles;
		} catch (Exception e) {
			throw new IOException(e);
		}
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Getter & Setter ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	public List<File> getUpload() {
		return upload;
	}

	public void setUpload(List<File> upload) {
		this.upload = upload;
	}

	public List<String> getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(List<String> uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public List<String> getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(List<String> uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public List<String> getFileTypes() {
		return fileTypes;
	}

	public void setFileTypes(List<String> fileTypes) {
		this.fileTypes = fileTypes;
	}

	public String getAttachment_creator() {
		return attachment_creator;
	}

	public void setAttachment_creator(String attachment_creator) {
		this.attachment_creator = attachment_creator;
	}

	public String getAttachment_creatorId() {
		return attachment_creatorId;
	}

	public void setAttachment_creatorId(String attachment_creatorId) {
		this.attachment_creatorId = attachment_creatorId;
	}

	public String getAttachment_editor() {
		return attachment_editor;
	}

	public void setAttachment_editor(String attachment_editor) {
		this.attachment_editor = attachment_editor;
	}

	public String getAttachment_editorId() {
		return attachment_editorId;
	}

	public void setAttachment_editorId(String attachment_editorId) {
		this.attachment_editorId = attachment_editorId;
	}

	public String getAttachement_fileId() {
		return attachement_fileId;
	}

	public void setAttachement_fileId(String attachement_fileId) {
		this.attachement_fileId = attachement_fileId;
	}

}
