<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<form action="downloadExcel" method="post" id="formData">
		 <label>Mốc thời gian: </label>
		 <input type="date" id="dateData" name="dateData" required><br> 
		 <label>Danh sách người được quản lý: </label><br>
		 <div id="divStaff">
		 <c:forEach items="#{lstStaff}" var="item">
			<input type="text" class="" name="staffData" value="${item}"><br>
		 </c:forEach>
		 <input type="text" class="" name="staffData"><br>
		 </div>
		  <input type="submit" value="Download">
	</form>
	<script>
	    function submitForm() {
	        var formData = new FormData(document.getElementById("formData"));
	        var xhttp = new XMLHttpRequest();
	        xhttp.onreadystatechange = function() {
	            if (this.readyState == 4) {
	                if (this.status != 200) {
	                    alert(this.responseText);
	                }
	            }
	        };
	        xhttp.open("POST", "downloadExcel", true);
	        xhttp.send(formData);
	    }
    </script>
</body>
</html>