package com.smile.download.services;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import com.smile.download.constant.DownLoadConstant;
import com.smile.download.dao.FileInfo;

public class InitThread extends Thread {

    private FileInfo mFileInfo = null;
    private DownLoadInterface downLoadInterface = null;
    
	public InitThread(FileInfo mFileInfo, DownLoadInterface downLoadInterface) {
		super();
		this.mFileInfo = mFileInfo;
		this.downLoadInterface = downLoadInterface;
	}
    
    @Override
    public void run() {
//    	System.out.println(mFileInfo.toString());
//    	mFileInfo.setLength(1500);
//    	downLoadInterface.hadGetMessage(mFileInfo);

        HttpURLConnection conn = null;
        RandomAccessFile raf = null;
        try {
            URL url = new URL(mFileInfo.getUrl());
            conn = (HttpURLConnection) url.openConnection();
            //设置连接超时时间
            conn.setConnectTimeout(3000);
            //设置请求方法，因为我们是下载文件，不是提交数据，所以用get
            conn.setRequestMethod("GET");
            //初始化文件大小为-1
            int length = -1;
            //等于200说明请求响应成功
            if(conn.getResponseCode() == 200){
                //获取文件大小
                length = conn.getContentLength();
            }
            //如果length<=0，要么请求不成功，要么得不到文件
            //所以直接跳出，不执行后面的操作
            if(length <= 0){
            	System.out.println("文件大小为：" + length);
                return ;
            }
            //如果下载的路径不存在，创建下载路径
            File dir = new File(DownLoadConstant.DOWNLOAD_PATH);
            if(!dir.exists()){
            	dir.mkdir();
            }
            //新建文件
            File file = new File(dir, mFileInfo.getFileName());
            //r:read,  w:write,  d:delete
            raf = new RandomAccessFile(file, "rwd");
            //设置文件大小
            raf.setLength(length);
            mFileInfo.setLength(length);
            
            downLoadInterface.hadGetMessage(mFileInfo);
            
            
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            try {
                if(raf != null) {
                    raf.close();
                }
                if(conn != null) {
                    conn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
    	
    	
    }
    
}
