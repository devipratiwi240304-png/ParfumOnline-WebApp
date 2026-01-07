<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Test JSTL</title>
        </head>

        <body>
            <h1>Test JSTL</h1>
            <c:set var="myVar" value="Hello Jakarta EE 10!" />
            <p>Message: ${myVar}</p>
            <c:if test="${not empty myVar}">
                <p>Condition met: ${myVar}</p>
            </c:if>
        </body>

        </html>