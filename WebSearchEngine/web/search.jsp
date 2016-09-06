<%-- 
    Document   : search
    Created on : Aug 5, 2015, 7:05:49 PM
    Author     : emmafsy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<section>

<h1>Search Result:</h1>

<c:if test="${results == null}">
    <p>No search result is found</p>
</c:if>

<c:if test="${results != null}">
    <center

        <c:forEach var="result" items="${results}">
            <h2><a href=${result.getUrl()}>${result.getTitle()}</a></h2>
            <p>${result.getDescription()}</p>
            <p>${result.getUrl()}</p>
        </c:forEach>
    </center>
</c:if>
</section>