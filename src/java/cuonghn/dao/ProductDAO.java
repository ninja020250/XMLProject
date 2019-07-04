/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuonghn.dao;

import cuonghn.jaxb.ListMonitor;
import cuonghn.jaxb.Monitor;
import cuonghn.utils.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author nhatc
 */
public class ProductDAO {

    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;

    private void closeConnection() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            System.out.println("some things happen prevent close connection");
        } finally {
        }
    }

    public ListMonitor getAllProductForSearching() {
        ListMonitor list = new ListMonitor();
        try {
            connection = DBUtil.makeDBConnection();
            if (connection != null) {
                String sql = "SELECT * FROM store_product_tbl sp, product_tbl p, store_tbl s WHERE sp.productModel = p.model and s.website = sp.website";
                preparedStatement = connection.prepareStatement(sql);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String storeWebsite = resultSet.getString("website");
                    String productModel = resultSet.getString("model");
                    String productUrl = resultSet.getString("url");
                    String productprice = resultSet.getString("price");
                    String resolution = resultSet.getString("resolution");
                    String brandName = resultSet.getString("brand_name");
                    String description = resultSet.getString("description");
                    String imgURL = resultSet.getString("imgURL");
                    Monitor mon = new Monitor(productModel);
                    mon.setUrl(productUrl);
                    mon.setPrice(productprice);
                    mon.setStoreName(storeWebsite);
                    mon.setResolution(resolution);
                    mon.setBrandName(brandName);
                    mon.setDescription(description);
                    mon.setImgURL(imgURL);
                    list.add(mon);
                }
                return list;
            }
        } catch (Exception e) {

            System.out.println("loi khi get product tu db " + e.getMessage());
        } finally {
            closeConnection();
        }
        return null;
    }
}
