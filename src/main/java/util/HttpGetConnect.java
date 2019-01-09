package util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpGetConnect {
    private static Log log = LogFactory.getLog(HttpGetConnect.class);

    public static String connect(String url, String charsetName) throws IOException {
        BasicHttpClientConnectionManager connManager = new BasicHttpClientConnectionManager();

        CloseableHttpClient httpclient = HttpClients.custom()
                .setConnectionManager(connManager)
                .build();
        String content = "";

        try {
            HttpGet httpget = new HttpGet(url);

            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(5000)
                    .setConnectTimeout(50000)
                    .setConnectionRequestTimeout(50000)
                    .build();
            httpget.setConfig(requestConfig);
            httpget.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            httpget.setHeader("Accept-Encoding", "gzip,deflate,sdch");
            httpget.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
            httpget.setHeader("Connection", "keep-alive");
            httpget.setHeader("Upgrade-Insecure-Requests", "1");
            httpget.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
            httpget.setHeader("cache-control", "max-age=0");
            httpget.setHeader("Host", "weibo.com");
            httpget.setHeader("Cookie", "");

            CloseableHttpResponse response = httpclient.execute(httpget);

            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                InputStream instream = entity.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(instream, charsetName));
                StringBuffer sbf = new StringBuffer();
                String line = null;
                while ((line = br.readLine()) != null) {
                    sbf.append(line + "\n");
                }

                br.close();
                content = sbf.toString();
            } else {
                content = "";
                System.out.println(status);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpclient.close();
        }
        return content;
    }
}
