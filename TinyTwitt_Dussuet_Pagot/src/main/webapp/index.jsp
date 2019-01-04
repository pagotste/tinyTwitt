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
		<h1>Créer un utilisateur</h1>
		<form action="/post" method="post">
			<p>
				<label>Nom d'utilisateur : <input type="text" name="nom" /></label>
			</p>
			<p>
				<input type="submit" />
			</p>
		</form>

		<h1>Nouveau message</h1>
		<form action="/post" method="post">
			<p>
				<label> Message : <br />
				<textarea name="message" style="width: 200px; height: 100px;"></textarea></label>
				<label> Utilisateur : <input type="text" name="user" /></label>
			</p>
			<p>

			</p>
		</form>

		<h1>ls ont aimé :</h1>
		<%
			List<Message> messages = (List<Message>) request.getAttribute("messages");
			for (Message message : messages) {
		%>
		<p>
			<%= message.getMessage() %>
		</p>
		<%
			}
		%>
	</body>
</html>
