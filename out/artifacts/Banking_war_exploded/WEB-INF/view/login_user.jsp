<%@ page import="banking_system.AccountData" %><%--
  Created by IntelliJ IDEA.
  User: michalbugno
  Date: 07/01/2020
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.List"%>
<html>
<head>
    <title>Customer dashboard</title>
</head>
<body>
<h2>Witaj, ${sessionScope.customer.firstName}</h2>

<h3>Dane osobowe:</h3>

<p>
    ${customer_description}
</p>

<h3>Wybór konta:</h3>



    <% List<AccountData> data = (List) session.getAttribute("customer_accounts");
    for(AccountData a : data){
        %>
    <p><a href = "http://localhost:8080/account?number=<%=a.getAccountNumber()%>"> <%= a.toString() %></a></p>
    <%}
    %>

<p><a href = "http://localhost:8080/logout">Wyloguj</a></p>

</body>
</html>
