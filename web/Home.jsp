<%-- 
    Document   : Home
    Created on : Jul 3, 2019, 2:42:43 PM
    Author     : nhatc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="./css/homePage.css"/>

    </head>
    <body>

        <%--<x:parse var="doc" doc="${requestScope.PRODUCTS}" scope="session"/>--%>
        <script>
            products = '${requestScope.PRODUCTS}';
            console.log(products);
        </script>
        <section class="home">
            <h1>CHÀO MỪNG ĐẾN TRANG WEB TÌM KIẾM MÀN HÌNH MÁY TÍNH</h1>
            <div class="option-filter-container">
                <div class="option-filter">
                    <h3>Tìm kiếm theo tên sản phẩm</h3>
                    <div class="description">
                        Bạn đã có sản phẩm mong muốn trong đầu nhưng chưa biết mua ở đâu.
                        bạn muốn tìm kiếm một mức giá tốt cho màn hình máy tính dự định mua.
                        tìm kiếm ngay đây, chúng tôi sẽ cho bạn biết nơi bạn nên mua sản phẩm đó
                    </div>
                    <form action="Home.jsp">
                        <input type="text" name="txtSearchValue"  value="${param.txtSearchValue}"/>
                        <button  type="submit" >Search</button>
                    </form>
                    <c:set var="searchValue" value="${param.txtSearchValue}"/>

                </div>
                <div class="option-filter">
                    <h3>So sánh 2 sản phẩm</h3>
                </div>
                <div class="option-filter">
                    <h3>Tìm kiếm nâng cao</h3>
                </div>
            </div>
        </section>
        <div class="container">
            <h1>Bộ cào kích hoạt thủ công dùng để develop</h1>
            <form action="CrawlServlet">
                <button style="background-color:  #3498db;" type="submit" name="btnAction" value="phucanh">Click to Crawling PhuCanh.vn</button>
            </form>
            <form action="CrawlServlet">
                <button style="background-color:  #f1c40f;" type="submit" name="btnAction" value="ben">Click to Crawling ben.com.vn</button>
            </form>
            <form action="CrawlServlet">
                <button style="background-color:  #f1c40f;" type="submit" name="btnAction" value="benvalid">Click to Validate ben.com.vn</button>
            </form>
            <form action="CrawlServlet">
                <button style="background-color:  #f1c40f;" type="submit" name="btnAction" value="masterCraler">Click to start masterCrawler</button>
            </form>
            <form action="CrawlServlet">
                <button style="background-color:  #2ecc71;" type="submit" name="btnAction" value="cpn">Click to Crawling cpn.vn</button>
            </form>
            <form action="CrawlServlet">
                <button style="background-color:  #c0392b;" type="submit" name="btnAction" value="clearDB">Click to Clear Database</button>
            </form>
        </div>  

       
            <c:if test="${not empty searchValue}">
                <c:set var="productList" value="${requestScope.PRODUCTS}" />
                <c:import var="xsl" url="/styleSheet/productList.xsl" charEncoding="utf-8"/>
                <x:transform doc="${productList}"  xslt="${xsl}" >
                    <x:param name = "searchValue" value = "${searchValue}"/>
                </x:transform>  
            </c:if>

    </body>
</html>