<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile...</title>
    </head>
    
    <body>
        <center>
        <br /> <br/>
        <h1>Profile...</h1>
	        	<table>
	        			<tr>
	        				<td>userId:</td>
	        				<td>${userInfo.userId}</td>
	        			</tr>
	        			<tr>
	        				<td>username:</td>
	        				<td>${userInfo.username}</td>
	        			</tr>
	        			<tr>
	        				<td>displayName:</td>
	        				<td>${userInfo.displayName}</td>
	        			</tr>
	        			<tr>
	        				<td>avatar:</td>
	        				<td>${userInfo.avatar}</td>
	        			</tr>
	        	</table>
	        </center>
    </body>
    
</html>
