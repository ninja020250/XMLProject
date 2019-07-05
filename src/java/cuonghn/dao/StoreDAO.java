/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuonghn.dao;

import cuonghn.jaxb.Brand;
import cuonghn.jaxb.ListBrand;
import cuonghn.jaxb.ListMonitor;
import cuonghn.jaxb.Monitor;
import cuonghn.jaxb.Store;
import cuonghn.utils.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nhatc
 */
public class StoreDAO {

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

    public void clearDB() {
        System.out.println("Clear DB...");
        try {
            truncateTable("store_product_tbl");
            truncateTable("product_tbl");
            truncateTable("brand_tbl");
            truncateTable("store_tbl");
        } catch (Exception e) {
        }
    }

    public void masterInsertStore(Store store) {
        System.out.println("Insert DB ....");
        ListBrand listBrand = store.getListBrand();
        insertNewStore(store);
        for (int i = 0; i < listBrand.getBrand().size(); i++) {
            insertNewBrand(listBrand.getBrand().get(i));
            ListMonitor listmonitor = listBrand.getBrand().get(i).getListMonitor();
            for (int j = 0; j < listmonitor.getMonitor().size(); j++) {
                boolean insertProductSuccess = insertNewProduct(listmonitor.getMonitor().get(j), listBrand.getBrand().get(i));
                if (insertProductSuccess) {
                    mappingStoreAndProduct(store, listBrand.getBrand().get(i).getListMonitor().getMonitor().get(j));
                } else {
                    System.out.println("khong insert product duoc");
                }
            }
        }

    }

    public boolean mappingStoreAndProduct(Store store, Monitor product) {
        try {
            connection = DBUtil.makeDBConnection();
            if (connection != null) {
                String sql = "INSERT INTO store_product_tbl (website, productModel, url, price, imageURL) VALUES (?,?,?,?,?);";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, store.getHomeUrl());
                preparedStatement.setString(2, product.getModel());
                preparedStatement.setString(3, product.getUrl());
                preparedStatement.setString(4, product.getPrice());
                preparedStatement.setString(5, product.getImgURL());
                int result = preparedStatement.executeUpdate();
                if (result > 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            if (e.getMessage().contains("duplicate key")) {
                System.out.println("Error: This Store existed");
            } else {
                System.out.println(e.getMessage());
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public boolean insertNewStore(Store newStore) {
        try {
            connection = DBUtil.makeDBConnection();
            if (connection != null) {
                String sql = "INSERT INTO store_tbl(name, website) VALUES (?, ?);";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, newStore.getHomeUrl());
                preparedStatement.setString(2, newStore.getHomeUrl());
                int result = preparedStatement.executeUpdate();
                if (result > 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            if (e.getMessage().contains("duplicate key")) {
                System.out.println("Error: This Store existed");
            } else {
                System.out.println(e.getMessage());
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public boolean insertNewProduct(Monitor product, Brand dependency) {
        try {
            connection = DBUtil.makeDBConnection();
            if (connection != null) {
                String sql = "INSERT INTO product_tbl (model, description,  screenBackground, resolution, contrast,"
                        + " brightness, responseTime, screenColor, screenView, hubs,"
                        + "electricalCapacity, weight, brand_name) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
//                String sql = "INSERT INTO product_tbl (model, brand_name) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, product.getModel());
                preparedStatement.setString(2, product.getDescription());
                preparedStatement.setString(3, product.getScreenBackground());
                preparedStatement.setString(4, product.getResolution());
                preparedStatement.setString(5, product.getContrast());
                if (product.getBrightness() == null) {
                    preparedStatement.setNull(6, 0);
                } else {
                    preparedStatement.setFloat(6, (float) product.getBrightness().intValue());
                }
                if (product.getResponseTime() == null) {
                    preparedStatement.setNull(7, 0);
                } else {
                    preparedStatement.setInt(7, product.getResponseTime().intValue());
                }
                preparedStatement.setString(8, product.getScreenColor());
                if (product.getScreenView() == null) {
                    preparedStatement.setNull(9, 0);
                } else {
                    preparedStatement.setInt(9, product.getScreenView().intValue());
                }
                preparedStatement.setString(10, product.getHubs());
                if (product.getElectricalCapacity() == null) {
                    preparedStatement.setNull(11, 0);
                } else {
                    preparedStatement.setInt(11, product.getElectricalCapacity().intValue());
                }
                preparedStatement.setString(12, product.getWeight());
                preparedStatement.setString(13, dependency.getBrandName());
//                preparedStatement.setString(1, product.getModel());
//                preparedStatement.setString(2, dependency.getBrandName());
                int result = preparedStatement.executeUpdate();
                if (result > 0) {
                    System.out.println("insert thanh cong: " + product.getModel());
                    return true;
                }
            }
        } catch (Exception e) {
            if (e.getMessage().contains("duplicate key")) {
                System.out.println("Error: This product existed: " + product.getUrl());
                System.out.println("Error: This product existed Model: " + product.getModel());
                return true;
            } else {
                System.out.println("khong insert duoc product nay : " + product.getModel());
                e.printStackTrace();
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public boolean insertNewBrand(Brand newBrand) {
        try {
            connection = DBUtil.makeDBConnection();
            if (connection != null) {
                String sql = "INSERT INTO brand_tbl (name, description) VALUES (?,?);";

                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, newBrand.getBrandName());
                preparedStatement.setString(2, newBrand.getDescription());
                int result = preparedStatement.executeUpdate();
                if (result > 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            if (e.getMessage().contains("duplicate key")) {
                System.out.println("Error: This Brand existed");
            } else {
                System.out.println(e.getMessage());
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public void truncateTable(String tableName) throws Exception {

        try {
            String sql
                    = "truncate table " + tableName;
            connection = DBUtil.makeDBConnection();
            preparedStatement = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            preparedStatement.executeUpdate();
            connection.commit();

        } catch (Exception e) {
            Logger.getLogger(StoreDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeConnection();
        }
    }
}
