package core;

import constant.Constant;
import util.HttpUtils;
import util.LogUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 下载器
 */
public class Downloader {

    public ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    public void download(String url){
        //获取文件名
        String fileName = HttpUtils.getHttpFileName(url);
        //文件下载后存放地址
        String downloadToPath = Constant.PATH + fileName;
        //获取连接对象
        HttpURLConnection httpURLConnection = HttpUtils.getHttpUrlConnection(url);
        //获取待下载文件的总大小
        long contentLength = httpURLConnection.getContentLengthLong();
        //创建获取下载信息的任务对象
        DownloadInfoThread downloadInfoThread = new DownloadInfoThread(contentLength, downloadToPath);
        //将任务交给线程执行
        scheduledExecutorService.scheduleAtFixedRate(downloadInfoThread, 0, 1, TimeUnit.SECONDS);
        //开始下载时间
        long startTime = System.currentTimeMillis();
        try (
                InputStream is = httpURLConnection.getInputStream();
                FileOutputStream fos = new FileOutputStream(downloadToPath);
        ){
            int len = -1;
            byte[] buffer = new byte[10 * 1024 * 1024];
            while((len = is.read(buffer)) != -1){
                fos.write(buffer, 0, len);
            }
        }catch (FileNotFoundException e){
            LogUtils.error("下载文件不存在：{}", url);
        }catch (Exception e){
            LogUtils.error("下载失败：{}", httpURLConnection);
        }finally {
            httpURLConnection.disconnect();
            //关闭
            scheduledExecutorService.shutdownNow();
            //下载完成时间
            long endTime = System.currentTimeMillis();
            System.out.print("\r");
            System.out.print("下载完成，用时" + (endTime - startTime) / 1000 + "s");
        }
    }
}
