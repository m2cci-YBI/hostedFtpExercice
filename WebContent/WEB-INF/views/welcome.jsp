<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Welcome</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
  <div class="container mt-5">
    <div class="card">
      <div class="card-header d-flex justify-content-between align-items-center">
        <h1>Welcome, <%= request.getAttribute("firstName") %> <%= request.getAttribute("lastName") %>!</h1>
        <a href="${pageContext.request.contextPath}/logout" class="btn btn-danger">Logout</a>
      </div>
      <div class="card-body">
        <h5 class="card-title">Your Profile Information</h5>
        <table class="table table-bordered mt-3">
          <tbody>
            <tr>
              <th scope="row">Company</th>
              <td><%= request.getAttribute("company") %></td>
            </tr>
            <tr>
              <th scope="row">Salary</th>
              <td><%= String.format("$%,.2f", (java.math.BigDecimal)request.getAttribute("salary")) %></td>
            </tr>
            <tr>
              <th scope="row">Start Date</th>
              <td><%= request.getAttribute("startDate") %></td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</body>
</html>
