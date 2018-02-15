<%--
  Created by IntelliJ IDEA.
  User: Home
  Date: 01.02.2018
  Time: 20:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Ваше Хобби</title>
    <link href="css/style.css" rel="stylesheet" >
    <script>
      var request = new XMLHttpRequest();
      function searchInfo() {
          var name = document.userNameForm.username.value;
          var url = "pages/processor.jsp?val=" + name;
          try {
              request.onreadystatechange = function () {
                  if(request.readyState==4){
                      var respText = request.responseText;
                      document.getElementById('span-id').innerHTML=respText;
                  }
              }
          } catch (e){
              alert("Unable to connect to server");
          }
          request.open("GET", url, true);
          request.send();
      }
    </script>
  </head>
  <body>
  <div class="main">
    <p><img src="images/hello.jpg"></p>
    <p>Welcome, %username%</p>
    <form name="userNameForm" action="pages/main.jsp" method="post">
      <label>
        Name <input type="text" name="username" value="" size="20" id="user-name-label" onkeyup="searchInfo()">
        <input type="submit" value="Enter">
      </label>
    </form>
    <span id="span-id"></span>
  </div>
  </body>
</html>
