package com.smile.download.services;

import com.smile.download.dao.FileInfo;

/**
 * 
 * 下载的接口
 * Created by smile on 2016/8/19.
 */
public interface DownLoadInterface {
	
	/**
	 * 获取文件基本信息完成
	 * @param fileInfo
	 */
	public void hadGetMessage(FileInfo fileInfo);
}
