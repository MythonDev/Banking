<%@ page import="org.springframework.web.context.request.SessionScope" %>
<%@ page import="banking_system.AccountData" %>
<%@ page import="java.util.List" %>
<%@ page import="banking_system.TransactionData" %>
<%@ page import="banking_system.DataBaseController" %><%--
  Created by IntelliJ IDEA.
  User: michalbugno
  Date: 08/01/2020
  Time: 23:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="pl">
<head>

    <meta charset="utf-8">
    <title>Account</title>
    <meta name="description" content="Prosty, intuicyjny system bankowy">
    <meta name="keywords" content="bank, system">
    <meta name="author" content="Kamil Osiński">

    <meta http-equiv="X-Ua-Compatible" content="IE=edge">

    <link href="https://fonts.googleapis.com/css?family=Open+Sans:400,700&amp;subset=latin-ext" rel="stylesheet">

    <!--[if lt IE 9]>
    <script src="//cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
    <![endif]-->

    <style>
        #container
        {
            background-color: grey;
            width: 1000px;
            margin-left: auto;
            margin-right: auto;
        }

        #logo
        {
            background-color: blue;
            color: white;
            text-align: center;
            padding: 15px;
        }
        #przelew
        {
            background-color: red;
            color: white;
            text-align: center;
            padding: 15px;
        }
    </style>
</head>

<body>

<div id="container">
    <div id="logo">
        <h1>Obsługa konta</h1>
    </div>


    <header>
        <div id="przelew">
            <h1>Wykonywanie przelewu</h1>

        </div>


    </header>

    <main>

        <article>
            <form action="transfer" method = POST>

                <fieldset>

                    <legend>Przelew</legend>

                    <label>Numer konta odbiorcy</label><br>
                    <input type="text" name="destination">

                    <div class="row">
                        <label>Kwota</label><br>
                        <input type="number" name="value">
                    </div>

                    <div class="row">
                        <input type="submit" value="Wykonaj przelew!">
                    </div>


                </fieldset>

            </form>

        <fieldset>


            <% List<AccountData> list = (List<AccountData>) session.getAttribute("customer_accounts");
                AccountData account = null;
                for(AccountData a : list){
                    if(a.getAccountNumber() == (long) request.getAttribute("account_number")){
                        account = a;
                        session.setAttribute("account_number", a.getAccountNumber());
                        break;
                    }
                }

            %>

            <p> <%=account.toString()%> </p>


            <p><a href = "http://localhost:8080/main_interface">Powrót</a></p>

        </article>

    </main>
</div>
</body>
</html>
