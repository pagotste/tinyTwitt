<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="tinytwitt.Message" %>


<!DOCTYPE html>

<html>
	<head>
		<title>Livre d'or</title>
		<meta charset="utf-8" />
	</head>

	<body>
		<h1>Vous avez aimé mon site ? Dites-le dans le Datastore !</h1>
		<form action="/post" method="post">
			<p>
				<label>Votre nom : <input type="text" name="name" /></label>
			</p>
			<p>
				<label>Votre message :<br />
				<textarea name="message" style="width: 200px; height: 100px;"></textarea></label>
			</p>
			<p>
				<input type="submit" />
			</p>
		</form>
	
		<h1>Ils ont aimé :</h1>
		<%
			List<Message> messages = (List<Message>) request.getAttribute("messages");
			for (Message message : messages) {
		%>
		<p>
			<strong><%= message.getName() %></strong> a écrit :<br />
			<%= message.getMessage() %>
		</p>
		<%
			}
		%>
	</body>
</html>