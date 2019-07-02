/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuonghn.servlet;

import cuonghn.crawler.CPNCrawler;
import cuonghn.crawler.BenCrawler;
import cuonghn.crawler.PhuCanhCrawler;
import cuonghn.dao.StoreDAO;
import cuonghn.jaxb.Brand;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author nhatc
 */
@WebServlet(name = "CrawlServlet", urlPatterns = {"/CrawlSrvlet"})
public class CrawlServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet responsee
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String realPath = this.getServletContext().getRealPath("/");
            String btnAction = request.getParameter("btnAction");
            if ("phucanh".equals(btnAction)) {
//                PhuCanhCrawler crawler = new PhuCanhCrawler();
//                crawler.run();
            } else if ("ben".equals(btnAction)) {
                BenCrawler notice = new BenCrawler(realPath);
                notice.run();
            } else if ("cpn".equals(btnAction)) {
                CPNCrawler cpn = new CPNCrawler(realPath);
                cpn.run();
            } else if ("benvalid".equals(btnAction)) {
                BenCrawler cpn = new BenCrawler(realPath);
                cpn.testValidate();
            } else if ("benSQL".equals(btnAction)) {
                StoreDAO dao = new StoreDAO();
                dao.insertNewBrand(new Brand("Chill"));
            } else if ("clearDB".equals(btnAction)) {
                StoreDAO dao = new StoreDAO();
                dao.clearDB();
            } else {
            }

            response.sendRedirect("index.html");
        } catch (Exception e) {
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
