<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Fish Count</title>
<link rel="stylesheet" href="<c:url value="/resources/cbms.css" />" />
<link rel="shortcut icon" href="<c:url value="/resources/logo.png" />" /> 
</head>
<body>
	<jsp:include page="header.jsp" />
	<jsp:include page="selectQuery.jsp" />
	<script>
		document.getElementById("querySel").value = 4;
		formManager.updateForm({value : 4});
		document.getElementById("queryType").value = ${type };
		document.getElementById("val").value = ${val };
		document.getElementById("aquanitype").value = ${aquanitype };
	</script>
	<div class="container">
		<h1>Fish Count</h1>
		<h5>Total Row Count: ${fn:length(fishcount) }</h5>
		<h5>Query Execution Time: ${time }</h5>
		<table>
			<tr>
				<th>Municipality</th>
				<th>Zone</th>
				<th>Barangay</th>
				<th>Fish Count</th>
			</tr>
			<c:forEach var="fish" items="${fishcount }">
				<tr>
					<td>${fish.mun }</td>
					<td>${fish.zone }</td>
					<td>${fish.brgy }</td>
					<td>${fish.fishcount }</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>