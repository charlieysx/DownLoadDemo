package com.smile.download.dao;

/**
 * 文件信息
 * 
 * @author smile
 * 
 */
public class FileInfo {
	private int id;
	private String url = null;
	private String fileName = null;
	private int length;

	public FileInfo() {
		super();
	}

	public FileInfo(int id, String url, String fileName, int length) {
		super();
		this.id = id;
		this.url = url;
		this.fileName = fileName;
		this.length = length;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public String toString() {
		return "FileInfo [id=" + id + ", url=" + url + ", fileName=" + fileName
				+ ", length=" + length + "]";
	}

}
