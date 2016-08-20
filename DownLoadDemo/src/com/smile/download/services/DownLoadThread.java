package com.smile.download.services;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import com.smile.download.constant.DownLoadConstant;
import com.smile.download.dao.FileInfo;
import com.smile.download.dao.ThreadInfo;
import com.smile.download.db.ThreadDAO;
import com.smile.download.ui.UpdateUi;

public class DownLoadThread extends Thread {

	private FileInfo mFileInfo = null;
	private ThreadDAO mDao = null;
	private UpdateUi updateUi = null;
	private ThreadInfo threadInfo = null;
	private boolean isPause = false;
	private int mFinished = 0;

	public DownLoadThread(FileInfo mFileInfo, ThreadDAO mDao,
			UpdateUi updateUi, ThreadInfo threadInfo) {
		super();
		this.mFileInfo = mFileInfo;
		this.mDao = mDao;
		this.updateUi = updateUi;
		this.threadInfo = threadInfo;
	}

	public void pause() {
		this.isPause = true;
	}

	@SuppressWarnings("resource")
	@Override
	public void run() {
		// 向数据库插入线程信息
		if (!mDao.isExists(threadInfo.getUrl(), threadInfo.getId())) {
			mDao.insertThread(threadInfo);
		}
		HttpURLConnection conn = null;
		RandomAccessFile raf = null;
		InputStream input = null;
		try {
			URL url = new URL(threadInfo.getUrl());
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(3000);
			conn.setRequestMethod("GET");

			// 设置下载位置
			int start = threadInfo.getStart() + threadInfo.getFinished();
			conn.setRequestProperty("Range", "bytes=" + start + "-"
					+ threadInfo.getEnd());

			// 设置文件写入位置
			File file = new File(DownLoadConstant.DOWNLOAD_PATH,
					mFileInfo.getFileName());
			raf = new RandomAccessFile(file, "rwd");
			raf.seek(start);

			mFinished += threadInfo.getFinished();
			updateUi.updateProgress(mFinished);
			// 开始下载
			if (conn.getResponseCode() == 206) {
				// 读取数据
				input = conn.getInputStream();
				byte[] buffer = new byte[1024 * 4];
				int len = -1;
				while ((len = input.read(buffer)) != -1) {
					// 写入文件
					raf.write(buffer, 0, len);
					mFinished += len;
					// 把下载进度发送给UI界面更新
					updateUi.updateProgress(len);
					// 在下载暂停时，保存下载进度
					if (isPause) {
						mDao.updateThread(threadInfo.getUrl(),
								threadInfo.getId(), mFinished);

						return;
					}
				}
				// 删除线程信息
				mDao.deleteThread(threadInfo.getUrl(), threadInfo.getId());
//				 System.out.println("下载完成");
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (raf != null) {
					raf.close();
				}
				if (input != null) {
					input.close();
				}
				if (conn != null) {
					conn.disconnect();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
