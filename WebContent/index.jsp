<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="jquery-3.2.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		$("button").click(function(){
			var url = "GetTicket";
			$.get(url,function(ticket){
				var src="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+ticket;
				$("img").attr("src",src);
			});
		});
	});
</script>
</head>
<body>
	<button>生成二维码</button><br>
	<img alt="" src="">
</body>
</html>