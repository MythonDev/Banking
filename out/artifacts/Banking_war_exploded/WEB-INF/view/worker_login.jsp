<%--
  Created by IntelliJ IDEA.
  User: michalbugno
  Date: 09/01/2020
  Time: 10:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Worker dashboard</title>
</head>
<body>
    <h2>Witaj, ${worker}</h2>

    <h3>Wpłata / Wypłata</h3>
    <form action = "operation" method = POST >
        <label>Numer konta</label>
        <input type = "text", name="account_number">
        <label>Kwota</label>
        <input type = "text", name = "amount">
        <input type = "submit", value = "Wykonaj">
    </form>

    <h3>Konta klienta</h3>
    <form action = "client_accounts" method = POST >
        <label>Nazwisko klienta</label>
        <input type = "text", name="surname">
        <input type = "submit", value = "Wykonaj">
    </form>
    <h3>Utwórz nowe konto</h3>
    <form action = "new_account" method = POST >
        <label>Numer klienta</label>
        <input type = "text", name="customer_number">
        <label>Waluta</label>
        <input type = "text", name="currency">
        <label>Produkt</label>
        <input type = "text", name="product_name">
        <input type = "submit", value = "Wykonaj">
    </form>
    <p><a href = "http://localhost:8080/logout">Wyloguj</a></p>
</body>
</html>
