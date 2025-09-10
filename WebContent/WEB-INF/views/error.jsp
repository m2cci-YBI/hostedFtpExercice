<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html><head><meta charset="UTF-8"><title>Error</title></head>
<body>
  <h2>Error</h2>
  <p style="color:red;"><%= request.getAttribute("message") %></p>
  <a href="${pageContext.request.contextPath}/login">Back to login</a>
</body></html>
