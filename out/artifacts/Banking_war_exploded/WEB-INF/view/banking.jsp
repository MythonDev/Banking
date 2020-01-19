<%--
  Created by IntelliJ IDEA.
  User: michalbugno
  Date: 06/01/2020
  Time: 13:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pl">
<head>

    <meta charset="utf-8">
    <title>System bankowy</title>
    <meta name="description" content="System do prowadzenia kont bankowych">
    <meta name="keywords" content="bank">
    <meta name="author" content="Jan Nowak">

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
        #register
        {
            float: left;
            background-color: green;
            width: 450px;
            height: 500px;
            padding: 10px;
        }
        #login
        {
            float: left;
            background-color: yellow;
            width: 450px;
            height: 500px;
            padding: 10px;
        }

        .row
        {
            margin: 15px 0px;
        }
    </style>
</head>

<body>

<div id="container">
    <div id="logo">
        <h1>Obsługa systemu bankowego</h1>
    </div>


    <header>



    </header>

    <main>

        <article>


            <form action="new_user" method =  "POST">

            <div id="register">
                <fieldset>

                    <legend>Rejestracja</legend>


                    <label>Imię</label><br>
                    <input type="text" name="first_name">


                    <div class="row">
                        <label>Nazwisko</label><br>
                        <input type="text" name="last_name">
                    </div>

                    <div class="row">
                        <label>PESEL</label><br>
                        <input type="text" name="personal_ID">
                    </div>

                    <div class="row">
                        <label>Data urodzenia ("RRRR-MM-DD")</label><br>
                        <input type="text" name="date">
                    </div>

                    <div class="row">
                        <label>HASŁO</label><br>
                        <input type="text" name="password">
                    </div>

                    <div class="row">
                        <input type="submit" value="Zarejestruj się!">
                    </div>


                </fieldset>
            </div>




            </form>

            <form action="login" method = "POST">
            <div id="login">
                <fieldset>

                    <legend>Logowanie</legend>

                    <label>Numer użytkownika</label><br>
                    <input type="text" name="login_number">

                    <label>HASŁO</label><br>
                    <input type="text" name="password">


                    <div class="row">
                        <input type="submit" value="Login">
                    </div>

                </fieldset>


            </div>

        </article>

    </main>
</div>
</body>
</html>
