package com.smile.download.services;

import java.util.ArrayList;
import java.util.List;

import com.smile.download.dao.FileInfo;
import com.smile.download.dao.ThreadInfo;
import com.smile.download.db.ThreadDAO;
import com.smile.download.db.ThreadDAOImpl;
import com.smile.download.ui.UpdateUi;

public class DownLoad implements DownLoadInterface {
	private FileInfo mFileInfo = null;
	private ThreadDAO mDao = null;
	private UpdateUi updateUi = null;
	private List<DownLoadThread> downLoadThreads = null;

	public DownLoad(UpdateUi updateUi) {
		this.updateUi = updateUi;
		mDao = new ThreadDAOImpl();
		downLoadThreads = new ArrayList<>();
	};

	public FileInfo getmFileInfo() {
		return mFileInfo;
	}

	public void setmFileInfo(FileInfo mFileInfo) {
		this.mFileInfo = mFileInfo;
	}

	public void startDownLoad(FileInfo mFileInfo) {
		this.mFileInfo = mFileInfo;
		new InitThread(this.mFileInfo, this).start();
	}

	public void stopDownLoad() {
		updateUi.setFileName("暂停下载：" + mFileInfo.getFileName());
		for (int i = 0; i < downLoadThreads.size(); ++i) {
			downLoadThreads.get(i).pause();
		}
	}

	@Override
	public void hadGetMessage(FileInfo fileInfo) {
		updateUi.setFileName("正在下载：" + fileInfo.getFileName());
		updateUi.setFileLength(fileInfo.getLength());
		// 获取文件信息成功，启动下载任务
		// 读取数据库中的线程信息
		List<ThreadInfo> threadInfos = mDao.getThreads(fileInfo.getUrl());

		if (threadInfos.size() == 0) {
			// 初始化线程信息对象
//			threadInfo = new ThreadInfo(0, fileInfo.getUrl(), 0,
//					fileInfo.getLength(), 0);
			int length = fileInfo.getLength();
//			int len1 = length / 2;
//			int len2 = len1 * 2;
			ThreadInfo threadInfo1 = new ThreadInfo(0, fileInfo.getUrl(), 0,
					length, 0);
//			ThreadInfo threadInfo2 = new ThreadInfo(1, fileInfo.getUrl(), len1 + 1,
//					length, 0);
//			ThreadInfo threadInfo3 = new ThreadInfo(2, fileInfo.getUrl(), len2 + 1,
//					length, 0);
			threadInfos.add(threadInfo1);
//			threadInfos.add(threadInfo2);
//			threadInfos.add(threadInfo3);

		}
		for (int i = 0; i < threadInfos.size(); ++i) {
			DownLoadThread downLoadThread = new DownLoadThread(mFileInfo, mDao,
					updateUi, threadInfos.get(i));
			downLoadThread.start();
			downLoadThreads.add(downLoadThread);
		}
	}
}
