﻿<!DOCTYPE HTML>
<html>
 <head>
  <meta http-equiv="Content-Type" content="text/html; charset="utf-8">

  <title>Registration page</title>
     <link href="styles/style.css" rel="stylesheet" type="text/css">
 </head>
<body align="center">

  <p class="heads"> <span> JavaMonkeys </span> </p>
  <table align="center" class="col">

     <th class="str0">  <h2>User Registration:</h2> </th>
    <tr>
    <td class="str1">
    <form name ="registration" method = "POST" action = "userprofile.jsp" >
    <ul>
      <li >
          <label   for="username">Your Name</label>
          <input  type="text" name="username" size="25"  title=" Enter your name " placeholder="your name" value="test" required>
      </li>
      <li >
          <label   for="usermail">Your E-mail</label>
          <input  type="EMAIL" name="usermail" size="25"  title=" Enter your email " placeholder="your mail@example.com" value="test@test.test" required>
      </li>
			<li>
          <label for="userpassword">Create Password</label>
          <input  type="password" name="userpassword" size="25"  title=" Create password with more than 8 chars  " placeholder="password" value="test" required>
      </li>
      <li>
        <input  type="submit" value="Confirm" >
      </li>
    </ul>
    </form>
    </td>
    </tr>

    <tr>
    <td class="str2">
    <span>For existing users:</span>
                  <br>     If You already have a login and password, please make login
                  <a href="login.jsp" target="_blank">
                    <button   type="button" name="ref-login" value="login" > Login </button>

  </td>
  </tr>
  </table>

  <table class="col" align="center" >
    <tr>
    <td  class ="col00">
    <nav>
    <a href="">About</a> | <a href="">Contacts</a> | <a href="">FeedBack</a>
    </nav>
    </td>
    </tr>
    </table>
 </body>
</html>
