<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" %>

<html>
<head>
    <meta http-equiv="Content-Type" charset="utf-8">
    <title>Login page</title>
    <link href="styles/style.css" rel="stylesheet" type="text/css">
</head>

<body align="center">
<p class="heads"><span> JavaMonkeys </span></p>
<table align="center" class="col">
    <tr>
        <th class="str0"><h2>User Login: </h2></th>
    </tr>

    <tr>
        <td class="str1">

            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>
            <c:if test="${not empty msg}">
                <div class="msg">${msg}</div>
            </c:if>

            <form name="loginForm" action="<c:url value="/j_spring_security_check" />" method="post">
                <ul>
                    <li>
                        <label for="username">Your E-mail</label>
                        <input type="EMAIL" name="username" size="25" title=" Enter your email/login "
                               placeholder="yourmail@example.com" value="test@test.test" required>
                    </li>

                    <li>
                        <label for="password">Your Password</label>
                        <input type="password" name="password" size="25" title=" Enter your password "
                               placeholder="password" value="test" required>
                    </li>

                    <li>
                        <button type="submit" name="submit" value="submit"> Login</button>
                    </li>

                </ul>

                <input type="hidden" name="${_csrf.parameterName}"
                       value="${_csrf.token}"/>

            </form>
        </td>
    </tr>


</table>

<tr>
    <td class="str2">
        <span> For new users: </span>
        <br>If You don't have a login and password, please make it!
        <a href="/registration" target="_blank">
            <button type="button" name="ref-login"> Registration</button>
        </a>
    </td>
</tr>
</table>
<table class="col" align="center">
    <tr>
        <td class="col00">
            <nav>
                <a href="">About</a> | <a href="">Contacts</a> | <a href="">FeedBack</a>
            </nav>
        </td>
    </tr>
</table>

</body>
</html>
