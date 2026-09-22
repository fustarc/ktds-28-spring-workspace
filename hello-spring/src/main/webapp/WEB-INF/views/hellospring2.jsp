<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hello Boot</title>
</head>
<body>
	<div>안녕하세요!</div>
	<div>Boot JSP 입니다!</div>
	<div>전달받은 모델 =></div>
	<ul>
	   <li>Name: ${name}</li>
	   <li>Age: ${age}</li>
	   <li>IsDeveloper: ${isDeveloper}</li>
	</ul>
</body>
</html>