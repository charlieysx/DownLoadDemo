package com.smile.download.ui;

/**
 * 界面更新的接口
 * @author smile
 *
 */
public interface UpdateUi {
	/**
	 * 设置文件名称
	 * 
	 * @param name
	 */
	public void setFileName(String name);

	/**
	 * 设置文件大小
	 * 
	 * @param length
	 */
	public void setFileLength(int length);

	/**
	 * 更新进度条
	 * 
	 * @param len
	 */
	public void updateProgress(int len);
}
