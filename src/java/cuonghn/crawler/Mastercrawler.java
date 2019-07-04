/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuonghn.crawler;

import cuonghn.dao.StoreDAO;
import cuonghn.utils.Constant;
import cuonghn.utils.ImageUtils;
import cuonghn.utils.TextUtilities;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

/**
 *
 * @author nhatc
 */
public class Mastercrawler {

    private String realPath;

    public Mastercrawler(String realPath) {
        this.realPath = realPath;
    }

    public void startCrawl() {
//        try {
//            System.setProperty("http.agent", "Chrome");
//            String laptopImage = "https://ben.com.vn/cdn/website/template/304/contentKey/1918/banner_tu_van.gif";
//            URL url = new URL(laptopImage);
//            BufferedImage img = ImageIO.read(url);
//            File file = new File(new File("web/imgs/cuong.jpg").getAbsolutePath() );
//            ImageIO.write(img, "jpg", file);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String expression = "([0-9].*)( *x *)([0-9]*)";
//        String src = "FullHD 1920 x 1080 @(wwww)";
//        src = src.trim();
//        String result = TextUtilities.getByExpression(src, expression);
//        System.out.println(result);
        StoreDAO dao = new StoreDAO();
        dao.clearDB();
        BenCrawler BenCrawler = new BenCrawler(realPath);
        BenCrawler.run();
    }

}
