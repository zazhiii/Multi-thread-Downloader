import core.Downloader;
import util.LogUtils;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // test: https://dldir1v6.qq.com/weixin/Windows/WeChatSetup.exe
        //下载连接
        String url = "";
        while(url == null || url.length() == 0){
            LogUtils.info(" 输入下载地址：");
            Scanner sc = new Scanner(System.in);
            url = sc.next();
        }
        new Downloader().download(url);
    }
}