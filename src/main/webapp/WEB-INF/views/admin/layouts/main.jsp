<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>Doc To Doc</title>
	<!-- Tell the browser to be responsive to screen width -->
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<!-- Bootstrap 3.3.6 -->
	<link rel="stylesheet" href="<c:url value="/admin/bootstrap/css/bootstrap.min.css" />">
	<!-- Font Awesome -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
	<link rel="stylesheet" href="<c:url value="/admin/adminlte/dist/css/AdminLTE.css" />">
	<link rel="stylesheet" href="<c:url value="/admin/adminlte/dist/css/skins/_all-skins.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/admin/adminlte/plugins/datatables/jquery.dataTables.min.css" />">
    <link rel="stylesheet" href="<c:url value="/admin/adminlte/plugins/datatables/buttons.dataTables.min.css" />">
	<link rel="stylesheet" href="<c:url value="/css/admin.css" />">
	<link rel="stylesheet" href="<c:url value="/admin/adminlte/plugins/datepicker/datepicker3.css" />">
	<link rel="stylesheet" href="<c:url value="/bootstrap-select/css/bootstrap-select.min.css" />">
   
	<script src="<c:url value="/admin/adminlte/plugins/jQuery/jquery-2.2.3.min.js" />"></script>
	<script src="<c:url value="/admin/adminlte/plugins/jQueryUI/jquery-ui.min.js" />"></script>
	<script src="<c:url value="/admin/bootstrap/js/bootstrap.min.js" />"></script>
	<script src="<c:url value="/admin/bootstrap/js/bootstrap3-typeahead.min.js" />"></script>
	<script src="<c:url value="/admin/adminlte/plugins/fastclick/fastclick.js" />"></script>
	<script src="<c:url value="/admin/adminlte/dist/js/app.min.js" />"></script>
	<script src="<c:url value="/admin/adminlte/plugins/datatables/jquery.dataTables.min.js" />" ></script>
	<script src="<c:url value="/admin/adminlte/plugins/datatables/buttons.html5.js" />" ></script>
	<script src="<c:url value="/admin/adminlte/plugins/datatables/jszip.min.js" />" ></script>
	<script src="<c:url value="/admin/adminlte/plugins/datatables/dataTables.buttons.min.js" />" ></script>
	<script src="<c:url value="/admin/adminlte/plugins/datepicker/bootstrap-datepicker.js" />"></script>
	<script src="<c:url value="/admin/adminlte/plugins/datepicker/locales/bootstrap-datepicker.es.js" />" charset="UTF-8"></script>

	<script src="<c:url value="/admin/adminlte/plugins/input-mask/jquery.inputmask.js" />"></script>
	<script src="<c:url value="/admin/adminlte/plugins/input-mask/jquery.inputmask.date.extensions.js" />"></script>
	<script src="<c:url value="/admin/adminlte/plugins/input-mask/jquery.inputmask.extensions.js" />"></script>
	<script src="<c:url value="/bootstrap-select/js/bootstrap-select.min.js" />"></script>
	<script src="<c:url value="/chartjs/Chart.bundle.min.js" />"></script>
	<script src="<c:url value="/chartjs/utils.js" />"></script>
	<link rel="icon" href="<c:url value="/images/favicon.ico" />"  type="image/x-icon">
</head>
<body class="hold-transition skin-black-light sidebar-mini">
<div class="wrapper">
	<header class="main-header">
	<!-- Logo -->
	<a href="<c:url value="/admin/dashboard" />" class="logo">
		<!-- mini logo for sidebar mini 50x50 pixels -->
		<span class="logo-mini"><img src="<c:url value="/images/favicon-32x32.png" />"></span>
		<!-- logo for regular state and mobile devices -->
		<span class="logo-lg"><img src="<c:url value="/images/navbar-logo-ligth-183x32.png" />"></span>
	</a>
	<!-- Header Navbar: style can be found in header.less -->
	<nav class="navbar navbar-static-top">
		<!-- Sidebar toggle button-->
		<a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
			<span class="sr-only">Toggle navigation</span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
		</a>

		<div class="navbar-custom-menu">
			<ul class="nav navbar-nav">
			
				<c:url value="/logout" var="logoutUrl" />
				<form style="display: none;" action="${logoutUrl}?${_csrf.parameterName}=${_csrf.token}" method='POST' enctype="multipart/form-data" method="post" id="logoutForm">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form>
				<script>
					function formSubmit() {
						document.getElementById("logoutForm").submit();
					}
				</script>
				
				<!-- User Account: style can be found in dropdown.less -->
				<li class="dropdown user user-menu">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown">
						<span class="hidden-xs">${pageContext.request.userPrincipal.name}</span>
					</a>
				</li>
				
				
				<li class="dropdown messages-menu">
					<a href="javascript:void(0);" onclick="javascript:formSubmit()" class="dropdown-toggle" data-toggle="dropdown">
						<i class="fa fa-sign-out"></i>
					</a>
				</li>
				
			</ul>
		</div>
	</nav>
	
	</header>
	
	<aside class="main-sidebar">
		<!-- sidebar: style can be found in sidebar.less -->
		<section class="sidebar">
			
			<!-- /.search form -->
			<!-- sidebar menu: : style can be found in sidebar.less -->
			<ul class="sidebar-menu">
				<li class="treeview">
					<a href="<c:url value="/admin/dashboard" />">
						<i class="fa fa-dashboard"></i> <span>Inicio</span>
					</a>
				</li>
				<!--Roles para Usuarios -->
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<li class="treeview">
					<a href="<c:url value="/admin/users" />">
						<i class="fa fa-user"></i> <span>Usuarios</span>
					</a>
				</li>
				</sec:authorize>
				
				<!--Roles para Notas -->
				<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_EDITOR')">
					<li class="treeview">
						<a href="<c:url value="/admin/BoNotes" />">
							<i class="fa fa-book"></i> <span>ABM de Notas</span>
						</a>
					</li>
				</sec:authorize>
				
				<!-- TODO: Roles para Offers -->
				<li class="treeview">
					<a href="<c:url value="/admin/BoOffers" />">
						<i class="fa fa-book"></i> <span>ABM de Ofertas</span>
					</a>
				</li>
				
				<!-- TODO: Roles para categories -->
				<li class="treeview">
					<a href="<c:url value="/admin/BoCategory" />">
						<i class="fa fa-book"></i> <span>ABM de Categorias</span>
					</a>
				</li>
				
				<!-- Roles para Notificaciones -->
				<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_NOTIFICATIONS')">
					<li class="treeview">
							<a href="<c:url value="/admin/BoNotification" />">
								<i class="fa fa-book"></i> <span>Envio de notificaciones</span>
							</a>
					</li>
				</sec:authorize>

				<!--Roles para Reportes -->
				<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_REPORTS')">
					<li class="treeview">
						<a href="<c:url value="/admin/reports/subscription" />">
							<i class="fa fa-book"></i> <span>Reporte: Suscripciones</span>
						</a>
					</li>
					<li class="treeview">
						<a href="<c:url value="/admin/reports/jobofferstats" />">
							<i class="fa fa-book"></i> <span>Reporte: Avisos mensual</span>
						</a>
					</li>
					<li class="treeview">
						<a href="<c:url value="/admin/reports/jobofferstats/daily" />">
							<i class="fa fa-book"></i> <span>Reporte: Avisos 30 d&iacute;as</span>
						</a>
					</li>
					<li class="treeview">
						<a href="<c:url value="/admin/reports/details" />">
							<i class="fa fa-book"></i> <span>Reporte: Detalle de avisos</span>
						</a>
					</li>
				</sec:authorize>
				
				<!--Roles para Logs -->
				<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_LOGS')">
					<li class="treeview">
						<a href="<c:url value="/admin/logs" />">
							<i class="fa fa-book"></i> <span>Logs</span>
						</a>
					</li>
				</sec:authorize>
			</ul>
		</section>
		<!-- /.sidebar -->
	</aside>

	<!-- Content Wrapper. Contains page content -->
	<div class="content-wrapper">
		<!-- Content Header (Page header) -->
		<section class="content-header">
			<h1>
				<tiles:insertAttribute name="title"/>
			</h1>
		</section>

		<tiles:insertAttribute name="body"/>
	</div>
	
	<footer class="main-footer">
		<div class="pull-right hidden-xs">WebApp DTD v 1.03.07</div>
		<strong>Copyright &copy; 2017 <a href="http://www.doctodocapp.com/">Doc To Doc</a>.</strong> All rights reserved.
	</footer>

	<!-- Control Sidebar -->
	<aside class="control-sidebar control-sidebar-dark">

		<ul class="nav nav-tabs nav-justified control-sidebar-tabs">
			<li><a href="#control-sidebar-home-tab" data-toggle="tab"><i class="fa fa-home"></i></a></li>
			<li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i class="fa fa-gears"></i></a></li>
		</ul>

		<div class="tab-content">

			<div class="tab-pane" id="control-sidebar-home-tab">
				<h3 class="control-sidebar-heading">Recent Activity</h3>
				<ul class="control-sidebar-menu">
					<li>
						<a href="javascript:void(0)">
							<i class="menu-icon fa fa-birthday-cake bg-red"></i>

							<div class="menu-info">
								<h4 class="control-sidebar-subheading">Langdon's Birthday</h4>

								<p>Will be 23 on April 24th</p>
							</div>
						</a>
					</li>
					<li>
						<a href="javascript:void(0)">
							<i class="menu-icon fa fa-user bg-yellow"></i>

							<div class="menu-info">
								<h4 class="control-sidebar-subheading">Frodo Updated His Profile</h4>

								<p>New phone +1(800)555-1234</p>
							</div>
						</a>
					</li>
					<li>
						<a href="javascript:void(0)">
							<i class="menu-icon fa fa-envelope-o bg-light-blue"></i>

							<div class="menu-info">
								<h4 class="control-sidebar-subheading">Nora Joined Mailing List</h4>

								<p>nora@example.com</p>
							</div>
						</a>
					</li>
					<li>
						<a href="javascript:void(0)">
							<i class="menu-icon fa fa-file-code-o bg-green"></i>

							<div class="menu-info">
								<h4 class="control-sidebar-subheading">Cron Job 254 Executed</h4>

								<p>Execution time 5 seconds</p>
							</div>
						</a>
					</li>
				</ul>
				<!-- /.control-sidebar-menu -->

				<h3 class="control-sidebar-heading">Tasks Progress</h3>
				<ul class="control-sidebar-menu">
					<li>
						<a href="javascript:void(0)">
							<h4 class="control-sidebar-subheading">
								Custom Template Design
								<span class="label label-danger pull-right">70%</span>
							</h4>

							<div class="progress progress-xxs">
								<div class="progress-bar progress-bar-danger" style="width: 70%"></div>
							</div>
						</a>
					</li>
					<li>
						<a href="javascript:void(0)">
							<h4 class="control-sidebar-subheading">
								Update Resume
								<span class="label label-success pull-right">95%</span>
							</h4>

							<div class="progress progress-xxs">
								<div class="progress-bar progress-bar-success" style="width: 95%"></div>
							</div>
						</a>
					</li>
					<li>
						<a href="javascript:void(0)">
							<h4 class="control-sidebar-subheading">
								Laravel Integration
								<span class="label label-warning pull-right">50%</span>
							</h4>

							<div class="progress progress-xxs">
								<div class="progress-bar progress-bar-warning" style="width: 50%"></div>
							</div>
						</a>
					</li>
					<li>
						<a href="javascript:void(0)">
							<h4 class="control-sidebar-subheading">
								Back End Framework
								<span class="label label-primary pull-right">68%</span>
							</h4>

							<div class="progress progress-xxs">
								<div class="progress-bar progress-bar-primary" style="width: 68%"></div>
							</div>
						</a>
					</li>
				</ul>
				<!-- /.control-sidebar-menu -->
			</div>
			<!-- /.tab-pane -->
			<!-- Stats tab content -->
			<div class="tab-pane" id="control-sidebar-stats-tab">Stats Tab Content</div>
				<!-- /.tab-pane -->
				<!-- Settings tab content -->
			<div class="tab-pane" id="control-sidebar-settings-tab">
				<form method="post">
					<h3 class="control-sidebar-heading">General Settings</h3>

					<div class="form-group">
						<label class="control-sidebar-subheading">
							Report panel usage
							<input type="checkbox" class="pull-right" checked>
						</label>

						<p>
							Some information about this general settings option
						</p>
					</div>
					<!-- /.form-group -->

					<div class="form-group">
						<label class="control-sidebar-subheading">
							Allow mail redirect
							<input type="checkbox" class="pull-right" checked>
						</label>

						<p>
							Other sets of options are available
						</p>
					</div>
					<!-- /.form-group -->

					<div class="form-group">
						<label class="control-sidebar-subheading">
							Expose author name in posts
							<input type="checkbox" class="pull-right" checked>
						</label>

						<p>
							Allow the user to show his name in blog posts
						</p>
					</div>
					<!-- /.form-group -->

					<h3 class="control-sidebar-heading">Chat Settings</h3>

					<div class="form-group">
						<label class="control-sidebar-subheading">
							Show me as online
							<input type="checkbox" class="pull-right" checked>
						</label>
					</div>
					<!-- /.form-group -->

					<div class="form-group">
						<label class="control-sidebar-subheading">
							Turn off notifications
							<input type="checkbox" class="pull-right">
						</label>
					</div>
					<!-- /.form-group -->

					<div class="form-group">
						<label class="control-sidebar-subheading">
							Delete chat history
							<a href="javascript:void(0)" class="text-red pull-right"><i class="fa fa-trash-o"></i></a>
						</label>
					</div>
					<!-- /.form-group -->
				</form>
			</div>
			<!-- /.tab-pane -->
		</div>
	</aside>
	<!-- /.control-sidebar -->
	<!-- Add the sidebar's background. This div must be placed immediately after the control sidebar -->
	<div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->
</body>
</html>
