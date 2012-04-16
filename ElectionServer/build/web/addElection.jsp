<%-- 
    Document   : addElection
    Created on : 29.02.2012, 15:43:48
    Author     : chille
--%>

<%@page import="iaik.chille.electionprovider.ElectionManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
  </head>
  <body>
      <h1>Frage hinzufügen</h1>
      <% 
      if(request.getMethod().compareTo("POST")==0)
      {
        ElectionManager.getInstance().addElection(request);
      }
      %>
      <p>Diese Seite sollte normalerweise nicht öffentlich zugänglich sein.</p>
      <form method="POST" action="./addElection.jsp">
        <p>
        Frage: <input type="text" name="question" size="25" />
        Titel: <input type="text" name="title" size="25" />
        URL: <input type="text" name="url" size="25" />
        </p>

        <p>
          <b>Antwort 1:</b>
        Antwort: <input type="text" name="1_answer" size="25" />
        Details: <input type="text" name="1_detail" size="25" />
        URL: <input type="text" name="1_url" size="25" />
        </p>

        <p>
          <b>Antwort 2:</b>
        Antwort: <input type="text" name="2_answer" size="25" />
        Details: <input type="text" name="2_detail" size="25" />
        URL: <input type="text" name="2_url" size="25" />
        </p>

        <input type="submit" value="Speichern" />
      </form>
  </body>
</html>
