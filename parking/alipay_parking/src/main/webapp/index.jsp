<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>接口测试</title>
<script type="text/javascript">
function carIn(){
	$.ajax({
		type : "POST",
		url : "../parking/carEnterInfo?r=" + Math.random() + "&parking_id=PI1486347449056133245&car_number=冀B851LZ",
		dataType : "json",
		cache : false,
		success : function(data) {
			showMessage(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
}
</script>
</head>
<body>
	<input type="button" name="车辆入场" onclick="carIn();">
</body>
</html>