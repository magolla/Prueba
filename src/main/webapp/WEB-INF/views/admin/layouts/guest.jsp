<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>Doc to Doc</title>
	<!-- Tell the browser to be responsive to screen width -->
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<!-- Bootstrap 3.3.6 -->
	<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
	<!-- Font Awesome -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
	<!-- Ionicons -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
	<!-- Theme style -->
	<link rel="stylesheet" href="<c:url value="/admin/adminlte/dist/css/AdminLTE.css" />">
	

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
	
	<div class="content-wrapper" style="margin-left: 0px;">
		<tiles:insertAttribute name="body"/>
	</div>
	
	<!--<footer class="main-footer" style="margin-left: 0px;">
		<div class="pull-right hidden-xs">
			<b>Version</b> 1.0.0
		</div>
		<strong>Copyright &copy; 2017 <a href="http://www.doctodocapp.com/">Doc To Doc</a>.</strong> All rights reserved.
	</footer>-->

	<div class="control-sidebar-bg"></div>
</div>

</body>
</html>
