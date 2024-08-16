package util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * http工具类
 */
public class HttpUtils {

    /**
     * 获取httpURLConnection
     * @param url
     * @return
     * @throws IOException
     */
    public static HttpURLConnection getHttpUrlConnection(String url) /*throws IOException*/ {
        URL httpUrl = null;
        HttpURLConnection urlConnection = null;
        try {
            httpUrl = new URL(url);
            urlConnection = (HttpURLConnection)httpUrl.openConnection();
        } catch (Exception e) {
            LogUtils.error("获取http连接出错:{}", url);
            throw new RuntimeException();
        }
        urlConnection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36 Edg/127.0.0.0");
        return urlConnection;
    }

    /**
     * 获取下载文件的名字
     * @param url
     * @return
     */
    public static String getHttpFileName(String url){
        return url.substring(url.lastIndexOf("/") + 1);
    }

}
