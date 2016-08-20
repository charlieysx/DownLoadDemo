package com.smile.download.db;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.smile.download.dao.ThreadInfo;

public class ThreadDAOImpl implements ThreadDAO {

	@Override
	public void insertThread(ThreadInfo threadInfo) {
		synchronized (this) {

			String sql = String
					.format("INSERT INTO `thread_info`(`t_id`, `t_url`, `t_start`, `t_end`, `t_finished`) VALUES (%d,%s,%d,%d,%d)",
							threadInfo.getId(),
							"'" + threadInfo.getUrl() + "'",
							threadInfo.getStart(), threadInfo.getEnd(),
							threadInfo.getFinished());
			try {
				DBHelper.update(sql);
				DBHelper.close();

			} catch (Exception e) {
				System.err.println("insertThread--" + e.getMessage());
			}
		}
	}

	@Override
	public void deleteThread(String url, int thread_id) {
		synchronized (this) {

			String sql = String
					.format("DELETE FROM `thread_info` WHERE `t_id` = %d and `t_url` = '%s'",
							thread_id, url);
			try {
				DBHelper.update(sql);
				DBHelper.close();

			} catch (Exception e) {
				System.err.println("deleteThread--" + e.getMessage());
			}
		}
	}

	@Override
	public void updateThread(String url, int thread_id, int finished) {
		synchronized (this) {

			String sql = String
					.format("UPDATE thread_info SET t_finished = %d WHERE t_id = '%d' and t_url = '%s'",
							finished, thread_id, url);
			try {
				DBHelper.update(sql);
				DBHelper.close();

			} catch (Exception e) {
				System.err.println("updateThread--" + e.getMessage());
			}
		}
	}

	@Override
	public List<ThreadInfo> getThreads(String url) {
		synchronized (this) {

			String sql = String.format(
					"SELECT * FROM `thread_info` WHERE `t_url` = '%s'", url);
			List<ThreadInfo> threadInfos = new ArrayList<>();

			try {
				ResultSet resultset = DBHelper.query(sql);

				while (resultset.next()) {
					ThreadInfo threadInfo = new ThreadInfo();
					threadInfo.setId(resultset.getInt("t_id"));
					threadInfo.setUrl(resultset.getString("t_url"));
					threadInfo.setStart(resultset.getInt("t_start"));
					threadInfo.setEnd(resultset.getInt("t_end"));
					threadInfo.setFinished(resultset.getInt("t_finished"));
					threadInfos.add(threadInfo);
				}

			} catch (Exception e) {
				System.err.println("getThreads--" + e.getMessage());
			}
			DBHelper.close();

			return threadInfos;
		}
	}

	@Override
	public boolean isExists(String url, int thread_id) {
		synchronized (this) {

			String sql = String
					.format("SELECT * FROM thread_info WHERE t_id = '%d' and t_url = '%s'",
							thread_id, url);

			// System.out.println(sql);

			try {
				ResultSet resultset = DBHelper.query(sql);
				if (resultset.next()) {
					return true;
				}

			} catch (Exception e) {
				System.err.println("isExists--" + e.getMessage());
			}
			DBHelper.close();

			return false;
		}
	}

}
