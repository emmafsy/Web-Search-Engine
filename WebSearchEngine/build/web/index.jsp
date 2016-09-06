<%-- 
    Document   : index
    Created on : Aug 5, 2015, 6:29:56 PM
    Author     : emmafsy
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<section>
    <form action="<c:url value='/SearchController'/>" method="post">
        <center>
            <p>
                <input type ="text" name ="keyword" size = 40>
                <input type ="submit" name ="search" value ="Search">&nbsp;
            </p>
        </center>
</section>