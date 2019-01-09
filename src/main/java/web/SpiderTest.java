package web;

import util.HttpGetConnect;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpiderTest {
    private static String url = "https://weibo.com/u/5470832305?refer_flag=0000015010_&from=feed&loc=nickname&is_all=1";

    private static Pattern pattern = Pattern.compile("<div[^>]*class=\\\\\"WB_text[^>]*>(.*?)<\\\\/div>");

    public static void main(String[] args) throws IOException {
        String content = HttpGetConnect.connect(url, "UTF-8");
//        Path path = Paths.get("index.html");
//        String content = new String(Files.readAllBytes(path),"UTF-8");

        Matcher matcher = pattern.matcher(content);
        int i = 0;
        while (matcher.find()) {
            String text = matcher.group(1).replaceAll("\\\\n", "").trim();
            String text2 = text.replaceAll("<.*?>", "");
            System.out.println(i++ + ": " + text2);
        }
    }
}
