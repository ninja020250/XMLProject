/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuonghn.listener;

import cuonghn.dao.ProductDAO;
import cuonghn.jaxb.ListMonitor;
import cuonghn.utils.XMLUtilities;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Web application lifecycle listener.
 *
 * @author nhatc
 */
public class MyRequestListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {

    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        try {
            ServletRequest request = sre.getServletRequest();
            ServletContext context = sre.getServletContext();
            HttpServletRequest servletRequest = (HttpServletRequest) sre.getServletRequest();
            String path = context.getContextPath();
            String button = request.getParameter("btnAction");
            if (button == null) {
                ProductDAO productDAO = new ProductDAO();
                ListMonitor listmonitor = productDAO.getAllProductForSearching();
                if (listmonitor != null) {
                    String result = XMLUtilities.marsalData(listmonitor);
                    sre.getServletRequest().setAttribute("PRODUCTS", result);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(MyRequestListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
