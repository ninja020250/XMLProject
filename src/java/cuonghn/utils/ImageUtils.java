/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuonghn.utils;

import cuonghn.parseData.Internet;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author nhatc
 */
public class ImageUtils {

    public static boolean saveImageByURL(String filePath, String imageURL) {

        try {
            imageURL = imageURL.replaceAll(" ", "%20lcd%20");
            InputStream in = new URL(imageURL).openStream();
            Files.copy(in, Paths.get(TextUtilities.deAccent(filePath)));
            return true;
        } catch (Exception e) {
            System.out.println("loi chuyen doi link anh");
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static String getNameImageFromUrl(String url) {

        String fileName = url.substring(url.lastIndexOf('/') + 1);

//        fileName = fileName.replaceAll(".", "");
        return fileName;
    }
}
