package com.smile.download.db;

import java.util.List;

import com.smile.download.dao.ThreadInfo;

/**
 * 数据库访问接口
 *
 * Created by smile on 2016/8/19.
 */
public interface ThreadDAO {
    /**
     * 插入线程信息
     * @param threadInfo
     */
    void insertThread(ThreadInfo threadInfo);

    /**
     * 删除线程信息
     * @param url
     * @param thread_id
     */
    void deleteThread(String url, int thread_id);

    /**
     * 更新下载进度
     * @param url
     * @param thread_id
     * @param finished
     */
    void updateThread(String url, int thread_id, int finished);

    /**
     * 查询文件的线程信息
     * @param url
     * @return
     */
    List<ThreadInfo> getThreads(String url);

    /**
     * 查询线程信息是否存在
     * @param url
     * @param thread_id
     * @return
     */
    boolean isExists(String url, int thread_id);
}
