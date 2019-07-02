/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuonghn.parseData;

import cuonghn.utils.TextUtilities;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nhatc
 */
public class Internet {

    public static void parseListHTML(String filePath, String uri) {
//        declare variable
        Writer writer = null;
        boolean check = false;
        int count = 1;
        int countDiv = 1;
        int countUl = 0;
        int coutLi = 0;
//       end declare variable
        try {
            // for hết toàn bộ uri mà đã định danh sẵn để đọc nội dung từng uri đó ra thành file html

            //khai báo connection với phiên bản chrome sử dụng để đọc từng line của html ra đưa vô inputLine
            URL url = new URL(uri);
            URLConnection con = url.openConnection();
            con.addRequestProperty("User-agent", "Chrome/61.0.3163.100 (compatible; MSIE 6.0; Windows NT 5.0");
            InputStream is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String inputLine;
            String htmlContent = "";
            boolean begin = false, end = false;
            while ((inputLine = br.readLine().trim()) != null) {
                if (inputLine.contains("<body>")) {
                    begin = true;
                    htmlContent = htmlContent + inputLine;
                    continue;
                }
                if (begin && !end) {
                    if (inputLine.contains("</body>")) {

                        break;
                    }
                    htmlContent = htmlContent + inputLine;
                }
            }
            String welformedHTML = TextUtilities.refineHtml(htmlContent); //welform html truoc khi parse 
            String[] wellformedLine = welformedHTML.split("\\r?\\n");
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filePath), "UTF-8"));
            for (int j = 0; j < wellformedLine.length; j++) {
                writer.write(wellformedLine[j] + "\n");
            }
            br.close();
            writer.close();
        } catch (Exception ex) {
            Logger.getLogger(Internet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String parseListHTMLToString(String uri) {
//        declare variable
        Writer writer = null;
        boolean check = false;
        int count = 1;
        int countDiv = 1;
        int countUl = 0;
        int coutLi = 0;
//       end declare variable
        try {
            // for hết toàn bộ uri mà đã định danh sẵn để đọc nội dung từng uri đó ra thành file html

            //khai báo connection với phiên bản chrome sử dụng để đọc từng line của html ra đưa vô inputLine
            URL url = new URL(uri);
            URLConnection con = url.openConnection();
            con.addRequestProperty("User-agent", "Chrome/61.0.3163.100 (compatible; MSIE 6.0; Windows NT 5.0");
            InputStream is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String inputLine;
            String htmlContent = "";
            boolean begin = false, end = false;
            while ((inputLine = br.readLine().trim()) != null) {
                if (inputLine.contains("<body>")) {
                    begin = true;
                    htmlContent = htmlContent + inputLine;
                    continue;
                }
                if (begin && !end) {
                    if (inputLine.contains("</body>")) {

                        break;
                    }
                    htmlContent = htmlContent + inputLine;
                }
            }
            String welformedHTML = TextUtilities.refineHtml(htmlContent); //welform html truoc khi parse 
            br.close();
            is.close();
            return welformedHTML;
        } catch (Exception ex) {
            Logger.getLogger(Internet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String parseStringToUTF8Uri(String uri) throws UnsupportedEncodingException {
        String[] arr = uri.split("\\=");
        String url = arr[0] +"="+ URLEncoder.encode(arr[1], "UTF-8");
        return url;
    }

}
