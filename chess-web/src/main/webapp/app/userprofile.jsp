<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page session="true" %>

<html>
 <head>
  <meta name = "keywords" content="text/html" charset="utf-8"  >
   <title>User Profile page</title>
   <link href="styles/style.css" rel="stylesheet" type="text/css">
 </head>
<body align="center">
    <p class="heads"> <span> JavaMonkeys </span> </p>
     <table align="center" class="col">
     <tr>
     <th class ="col0"> <h2>User Profile </h2> </th>
     </tr>

     <tr>
         <td class="col1" >
         <h3>Menu</h3>
         <p> <button  type="button" name="newgame" value ="">New game </button></p>
         <p> <button type="button" name="joingame" value ="">Join game </button></p>
         <p> <button type="button" name="exitgame" value ="">Exit game </button></p>
         </td>

         <td  class="col2">
         <h2>
             <c:if test="${pageContext.request.userPrincipal.name != null}">
                 <h2>
                     User : ${pageContext.request.userPrincipal.name} | <a
                     href="javascript:formSubmit()"> Logout</a>
                 </h2>
             </c:if>
         </h2>
         <form name ="userInfo" id = "userInfo" >
         <p><img src="images/images1.jpg" alt="UserPhoto"   class="rightimg">   </p>
         <p>Birth Date: <input name ="dateOfBirth" type="date" value="">  </p>
         <p>Male/Female:  <input name ="state" type="radio"> Male <input name ="state" type="radio"> Female </p>
         <p>Active e-mail <input name ="usermail" type="email" value=" " placeholder="User@email.com"> </p>
         </form>
         <p align="right">  <button form = "userInfo"  type="button" name="ok" value ="">Confirm </button> </p>
         </td>

     </tr>
    </table >

   <!-- <footer>   -->
    <table class="col" align="center" >
    <tr>
    <td  class ="col00">
    <nav>
    <a href="">About</a> | <a href="">Contacts</a> | <a href="">FeedBack</a>
    </nav>
    </td>
    </tr>
    </table>
   <!-- </footer>
   <script src="http://code.jquery.com/jquery-2.0.3.min.js"></script>
    <script src="app.js"></script>-->

    <!-- For login user -->
    <c:url value="/j_spring_security_logout" var="logoutUrl" />
    <form action="${logoutUrl}" method="post" id="logoutForm">
        <input type="hidden" name="${_csrf.parameterName}"
               value="${_csrf.token}" />
    </form>
    <script>
        function formSubmit() {
            document.getElementById("logoutForm").submit();
        }
    </script>

  </body>
</html>
