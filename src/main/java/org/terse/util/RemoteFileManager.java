package org.terse.util;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.Selectors;
import org.apache.commons.vfs.cache.DefaultFilesCache;
import org.apache.commons.vfs.impl.DefaultFileSystemManager;
import org.apache.commons.vfs.provider.FileProvider;
import org.apache.commons.vfs.provider.local.DefaultLocalFileProvider;

import org.terse.exception.VFSException;


/**
 * 远程文件管理
 * 
 */
public class RemoteFileManager {
	
	/**
	 * 配置信息
	 */
	private Map<String, String> cfg = null;
	
	/**
	 * 无参构造函数
	 */
	public RemoteFileManager() {
		
	}
	
	/**
	 * 文件下载
	 * 
	 * @param path 文件在服务器上的相对路径
	 * @return 输入流
	 * @throws Exception 
	 */
	public InputStream download(String path) throws Exception {
		
		DefaultFileSystemManager mgr = getDefaultFileSystemManager();
		FileObject srcObj = mgr.resolveFile(getProperties("fileServer.host") + path);
			
		return srcObj.getContent().getInputStream();	
	}
	
	/**
	 * 文件删除
	 * 
	 * @param path
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 * @throws FileSystemException 
	 * @return true or false
	 */
	public boolean delete(String path) throws Exception {
		
		DefaultFileSystemManager mgr = getDefaultFileSystemManager();
		FileObject srcObj = mgr.resolveFile(getProperties("fileServer.host") + path);
		
		return srcObj.delete();
	}
	
	public void update(String path, File file) throws VFSException {
		
		upload(null, file, getProperties("fileServer.host"), "", path);
	}

	/**
	 * 文件上传
	 * 
	 * @param fileName 文件原名称
	 * @param file 文件对象
	 * @param host 主机地址，包括登录信息和端口
	 * @param uploadPath 远程存储
	 * @param newFileName 文件新名称
	 * @return 状态信息<br/>
	 * 		   String[0] : 文件原名称
	 * 		   String[1] : 文件相对路径，包含文件服务器端名称<br/>
	 * 		   String[2] : 文件大小<br/>
	 * 		   String[3] : 文件服务器ID<br/>
	 * @throws VFSException 
	 */
	private String[] upload(String fileName, File file, String host,
			String uploadPath, String newFileName) throws VFSException {

		String code = getFileCode(new Date());	// 文件编码
		String oldFileName = fileName;	// 文件原名称
		if(newFileName == null || "".equals(newFileName)) {
			newFileName = getNewFileName(oldFileName, code);	// 文件新名称
		}
		String path = uploadPath;		// 上传的路径

		FileObject src;
		FileObject dist;
		try {
			DefaultFileSystemManager mgr = getDefaultFileSystemManager();
			src = mgr.resolveFile(file.getAbsolutePath());	// 本地源文件
			dist = mgr.resolveFile(host + path + "/" + newFileName);	// 远程目标文件
			
			// 若文件服务器已存在该文件
			if(dist.exists()) {
				dist.delete();
			}
			
			// 项目标文件中写入源文件内容
			dist.copyFrom(src, Selectors.SELECT_ALL);
			
			String[] array = new String[4];
			array[0] = oldFileName;
			array[1] = path + "/" + newFileName;
			array[2] = String.valueOf(dist.getContent().getSize());
			array[3] = getProperties("fileServer.cfgid");

			return array;
		} catch (Exception e) {
			throw new VFSException(e);
		} 
	}

	/**
	 * 初始化vfs文件管理对象
	 * 
	 * @return 文件管理对象
	 * @throws FileSystemException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private DefaultFileSystemManager getDefaultFileSystemManager()
			throws FileSystemException, ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		
		DefaultFileSystemManager mgr = new DefaultFileSystemManager();

		// 读取上传协议
		String protocol = getProperties("fileServer.protocol").trim();
		
		if(!"file".equals(protocol)) {
			// 装载文件提供类
			Class<?> clazz = Class.forName(getProperties("fileServer.fileProvider"));
			FileProvider provider = (FileProvider) clazz.newInstance();
			mgr.addProvider(protocol, provider);
		}
		
		// 缺省
		DefaultLocalFileProvider file = new DefaultLocalFileProvider();
		mgr.addProvider("file", file);
		
		mgr.setFilesCache(new DefaultFilesCache());
		mgr.init();

		return mgr;
	}

	/**
	 * 文件上传
	 * <p/>
	 * 使用默认host，在配置文件中配置
	 * 
	 * @param fileName 文件原名称
	 * @param file 文件对象
	 * @param path 远程存储路径
	 * @return 状态信息<br/>
	 * 		   String[0] : 文件原名称
	 * 		   String[1] : 文件相对路径，包含文件服务器端名称<br/>
	 * 		   String[2] : 文件大小<br/>
	 * 		   String[3] : 文件服务器ID<br/>
	 * @throws VFSException 
	 */
	public String[] upload(String fileName, File file, String path) throws VFSException {

		return upload(fileName, file, getProperties("fileServer.host"), path, null);
	}

	/**
	 * 文件上传
	 * <p/>
	 * 使用默认host和默认路径。host在配置文件中配置，默认路径由默认算法算得
	 * 
	 * @param fileName 文件原名称
	 * @param file 文件对象
	 * @return 状态信息<br/>
	 * 		   String[0] : 文件原名称
	 * 		   String[1] : 文件相对路径，包含文件服务器端名称<br/>
	 * 		   String[2] : 文件大小<br/>
	 * 		   String[3] : 文件服务器ID<br/>
	 * @throws VFSException 
	 */
	public String[] upload(String fileName, File file) throws VFSException {

		return upload(fileName, file, getProperties("fileServer.host"),getPath(), null);
	}

	/**
	 * 根据文件原名称计算文件新名称
	 * <p/>
	 * 新名字格式为：文件原名称_文件编码.后缀
	 * 
	 * @param fileName 文件原名称
	 * @param code 文件编码
	 * @return 文件新名称
	 */
	private String getNewFileName(String fileName, String code) {

		String name = "";
		String suffix = "";
		int index = fileName.lastIndexOf(".");
		if (index < 0) {
			name = fileName;
			suffix = "";
		} else {
			name = fileName.substring(0, index);
			suffix = fileName.substring(index);
		}

		String newName = "";
		try {
			newName = URLEncoder.encode(name + "_" + code + suffix, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			newName = name + "_" + code + suffix;
		}
		return newName;
	}

	/**
	 * 计算文件编码
	 * <p/>
	 * 算法为：当前时间的毫秒表达+4位随机数
	 * 
	 * @return 文件编码
	 */
	private String getFileCode(Date curDate) {

		Random rand = new Random();
		String r = String.valueOf(rand.nextInt(9999));
		if (r.length() < 4) {
			for (int i = r.length(); i < 4; i++) {
				r = "0" + r;
			}
		}

		return curDate.getTime() + r;
	}

	/**
	 * 获取上传路径
	 * <p/>
	 * 默认创建的路径为：/年/月/日
	 * 
	 * @return 相对路径
	 */
	private String getPath() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String path = sdf.format(new Date());

		return "/" + path;
	}

	/**
	 * 根据key在map中获取对应配置
	 * 
	 * @param key
	 * @return 配置值
	 */
	private String getProperties(String key) {

		return cfg.get(key);
	}
	
	/**
	 * 默认上传至服务端的路径
	 * <p/>
	 * 默认创建的路径为：/年/月/日
	 * 
	 * @return 相对路径
	 */
	public static String getDefaultPath() {
		
		return new RemoteFileManager().getPath();
	}

	public Map<String, String> getCfg() {
		return cfg;
	}

	public void setCfg(Map<String, String> cfg) {
		this.cfg = cfg;
	}

}
