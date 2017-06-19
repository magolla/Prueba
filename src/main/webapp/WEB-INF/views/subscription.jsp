<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<title>Suscribirme</title>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge;chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<link rel="stylesheet" href="css/style.css">
	<!-- Bootstrap 3.3.6 -->
	<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
	
	<script src="plugins/jQuery/jquery-2.2.3.min.js"></script>
    <script src="bootstrap/js/bootstrap.min.js"></script>

</head>
<body>
<br/> <br/>
		<div class="row">
				<div class="col-md-offset-3 col-md-6">
		
					<div class="box box-info">
						<div class="box-header with-border" style="text-align: center">
							<h3 class="box-title">Ingresá tu número de celular y el código de suscripción</h3><br>
						</div>
						
						<c:if test="${not empty error}">
							<div class="error">${error}</div>
						</c:if>
						<c:if test="${not empty msg}">
							<div class="msg">${msg}</div>
						</c:if>
		
						<form name='loginForm' action="<c:url value='/suscribirme' />" method='POST'  modelAttribute="formRequest">
							<div class="box-body">
								<div class="form-group">
									<label for="number" class="col-md-offset-2 col-sm-2 control-label">Número</label>
		
									<div class="col-sm-6">
										<input type="text" class="form-control" id="number" name="number">
									</div>
								</div>
								
								<div class="form-group">
									<label for="password" class="col-md-offset-2  col-sm-2 control-label" style="margin-top: 15px">Código</label>
		
									<div class="col-sm-6">
										<input type="text" class="form-control" id="code" name="code" style="margin-top: 15px">
									</div>
								</div>
								
							</div>
							<div class="box-footer">
							    <div class="col-md-10">
									<button type="submit" class="btn btn-info pull-right" style="margin-top: 15px">Suscribirme</button>
								</div>	
							</div>
						</form>
					</div>
		
				</div>
			</div>
</body>
</html>			