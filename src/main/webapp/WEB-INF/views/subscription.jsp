<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en" class="wide wow-animation smoothscroll scrollTo">
<head>
	<title>Doc to Doc App :: Suscripción</title>
	<meta charset="utf-8">
	<meta name="format-detection" content="telephone=no">
	<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<meta name="keywords" content="intense web design multipurpose template">
	<meta name="date" content="Dec 26">
	<link rel="icon" href="images/favicon.ico" type="image/x-icon">
	<link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Open+Sans:400,700,900">
	<link rel="stylesheet" href="css/style.css">
	<link rel="stylesheet" href="css/subscription.css">
	<!-- Bootstrap 3.3.6 -->
	<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
	<script src="plugins/jQuery/jquery-2.2.3.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script>
		(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
		(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
		m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
		})(window,document,'script','https://www.google-analytics.com/analytics.js','ga');
		
		ga('create', 'UA-90094059-1', 'auto');
		ga('send', 'pageview');
		
	</script>
</head>
<!-- Versión de la aplicación backend: WebApp DTD v 1.03.03.B3 -->
<body class="bg-macaroni">
<div class="row">
	<div class="col-md-offset-3 col-md-6">
		<div class="box box-info">
			<div class="box-header with-border" style="text-align: center">
				<h1 class="maintitle">Suscribite a DOC TO DOC</h1>
				<h2 class="mainsubtitle">Ingresá tu número de celular y el código de suscripción</h2>
			</div>
			
			<div class="containerbox">
				<h3 class="advertencia">IMPORTANTE:</h3>
				<ul class="advertencia">
					<li>El número de teléfono celular que utilices será el "USUARIO" que te identificará en <b>DOC TO DOC</b>.</li>
					<li>El número de teléfono deberá ser el que utilices en el equipo en el que vas a instalar o tengas instalada la aplicación.</li>
					<li>Si ya estás registrado y tenés instalada la aplicación, también podes usar el código de suscripción, pero te aconsejamos revisar si tenes alguna suscripción vigente.</li>
					<li>Una vez cargado el código, no se podrá volver a utilizar.</li>
					<li>Revisá cuidadosamente que número de teléfono coincida con el que tenes cargado o vayas a cargar en la aplicación. Si tu número de celular es de la Ciudad de Buenos Aires y alrededores, deberás cargarlo de la siguiente manera: Ejemplo: 1199990000</li>
					<li>Si ya creaste tu cuenta con tu número de celular de la siguiente manera: 0119999000, asegurate de cargalo igual en esta página.</li>
				</ul>
				<c:if test="${not empty error}">
					<div class="error">${error}</div>
				</c:if>
				<c:if test="${not empty msg}">
					<div class="msg">${msg}</div>
				</c:if>
	
				<form name='loginForm' action="<c:url value='/suscribirme?${_csrf.parameterName}=${_csrf.token}' />" method='POST'  modelAttribute="formRequest">
					<div class="box-body">
						<div class="form-group">
							<label for="number" class="col-md-offset-2 col-sm-2 control-label">Celular</label>
		
							<div class="col-sm-6">
								<input type="text" class="form-control" id="number" name="number" placeholder="Ejemplo: 1199990000">
							</div>
						</div>
						
						<div class="form-group" style="margin-top: 15px">
							<label for="password" class="col-md-offset-2  col-sm-2 control-label">Código</label>
		
							<div class="col-sm-6">
								<input type="text" class="form-control" id="code" name="code" >
							</div>
						</div>
						
					</div>
					<div class="box-footer">
					    <div class="col-md-10">
							<button type="submit" class="btn btn-info pull-right" style="margin-top: 15px">Validar código</button>
						</div>	
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
</body>
</html>			