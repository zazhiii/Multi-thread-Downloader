package core;

import util.FileUtils;

public class DownloadInfoThread implements Runnable{

    //下载存放地址
    private String downloadToPath;
    //下载文件的总大小 （字节)
    private long downloadFileContentLength;
    //已下载文件大小
    private double finishedSize;
    //本次累计下载大小
//    public volatile double downSize;
    //前一秒下载大小
    private double prevSize;


    public DownloadInfoThread(long downloadFileContentLength, String downloadToPath) {
        this.downloadFileContentLength = downloadFileContentLength;
        this.downloadToPath = downloadToPath;
    }

    @Override
    public void run() {
        //计算文件大小(MB)
        String httpFileSize = String.format("%.2f", downloadFileContentLength / 1024d / 1024d);
        //已下载文件大小(B)
        finishedSize = FileUtils.getFileContentLength(downloadToPath);
        //已下载文件大小(MB)
        String formatfinishedSize = String.format("%.2f", finishedSize / 1024d / 1024d);
        //计算每秒下载速度(KB)
        int speed = (int)((finishedSize - prevSize) / 1024d);
        prevSize = finishedSize;
        String remindTime = String.format("%.1f", (downloadFileContentLength - finishedSize) / 1024d / speed);
        //拼接下载信息
        String info = String.format("已下载:%sMB / %sMB, 下载速度:%sKB/s, 剩余时间:%ss", formatfinishedSize, httpFileSize, speed, remindTime);
        System.out.print("\r");
        System.out.print(info);
    }
}
