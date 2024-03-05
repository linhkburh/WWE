<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Dành cho Thảo tớc nơ</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<style>
	input, label{
		margin-top: 8px;
	}
	#formData{
		text-align: center;
		margin-top: 50px;
	}
</style>
<body>
	<form action="downloadExcel" method="post" id="formData">
		 <label>Mốc thời gian: </label>
		 <input style="padding: 5px 2px; width: 15%; margin-bottom: 10px;" type="date" id="dateData" name="dateData" required><br> 
		 <label>Danh sách người được quản lý(Tên các sheet): </label>
		 <div id="divStaff">
		 <c:forEach items="#{lstStaff}" var="item">
			<input style="padding: 5px 2px; width: 20%;" type="text" name="staffData" value="${item}"><input type="checkbox" name="arrQim" class="selectRow"><br>
		 </c:forEach>
		 </div>
		  <input type="submit" value="Download">
		  <input type="button" value="Thêm" onclick="addStaff()">
		  <input type="button" value="Xóa" onclick="delStaff()">
		  <input type="button" value="Chọn tất cả" onclick='$(".selectRow").prop("checked", true);'>
		  <input type="button" value="Bỏ chọn tất cả" onclick='$(".selectRow").prop("checked", false);'>
	</form>
	<script type="text/javascript">
	    function addStaff() {
	        $('#divStaff').append('<input style="padding: 5px 2px; width: 20%;" type="text" name="staffData"><input type="checkbox" name="arrQim" class="selectRow"><br>');
	    }
	    function delStaff() {
	    	if ($('.selectRow:checked').length==0) {
				alert('Bạn chưa chọn bản ghi!');
				return;
			}else{
				let text = "Bạn có chắc chắn muốn xóa!";
				  if (confirm(text) == true) {
					  $('.selectRow:checked').each(function() {
				            $(this).prev().remove();
				            $(this).next().remove();
				            $(this).remove();
				        });
				  }
			}
	    }
    </script>
</body>
</html>