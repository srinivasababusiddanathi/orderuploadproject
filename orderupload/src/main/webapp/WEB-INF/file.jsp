<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored="false"%>
<html>
<head>
<style>
table {
	font-family: arial, sans-serif;
	border-collapse: collapse;
	width: 60%;
}

td, th {
	border: 1px solid #dddddd;
	text-align: left;
	padding: 8px;
}

tr:nth-child(odd) {
	background-color: #dddddd;
}
</style>

</head>

<body>
<form:form method="POST" commandName="uploadFile" enctype="multipart/form-data">
		<div style="padding-top: 50px; padding-left: 100px;">
			<table>
				<tr>
				<font color="green">${successMsg}</font>
				<font color="red">${errorMsg}</font>
				</tr>
				<tr>
					<th>Upload Order</th>
				</tr>
				<tr>
					<td>
						<b style="padding-right: 30px;">Please select a CSV file: </b>
						<input type="file" name="file" />
					</td>
				</tr>
				<tr>
				</tr>
				<tr>
					<td><b><input type="submit" value="Upload File" /></b>
						<form:errors path="file" cssStyle="color: #ff0000;" />
					</td>
				</tr>
			</table>
		</div>
	</form:form>
</body>
</html>