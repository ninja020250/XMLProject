<%-- 
    Document   : productsPage
    Created on : Jul 4, 2019, 3:50:03 PM
    Author     : nhatc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes" />
        <link rel="stylesheet" href="CSS/productPage.css" />
        <link rel="stylesheet" href="font-awesome/css/font-awesome.min.css" />
        <title>JSP Page</title>
    </head>
    <body>
        <script>
            products = '${requestScope.PRODUCTS}';
        </script>
        <h1>Hello World!</h1>
    </body>
</html>
