<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<tiles:insertDefinition name="d2d.dashboard">

	<tiles:putAttribute name="title">
		Usuarios
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<section class="content">
			<div class="row">
				<div class="col-md-offset-3 col-md-6">
		
					<div class="box box-info">
						<div class="box-header with-border">
							<h3 class="box-title">Ingresar/Modificar</h3>
						</div>
						
						<c:if test="${not empty error}">
							<div class="error">${error}</div>
						</c:if>
						<c:if test="${not empty msg}">
							<div class="msg">${msg}</div>
						</c:if>
		
						<form name='loginForm' action="<c:url value='/user/${user_id}' />" method='POST'>
							<div class="box-body">
								<div class="form-group">
									<label for="username" class="col-sm-2 control-label">Email</label>
		
									<div class="col-sm-10">
										<input type="email" class="form-control" id="email" name="email" placeholder="Email" value="${user.email}">
									</div>
								</div>
								<div class="form-group">
									<label for="password" class="col-sm-2 control-label">Nombre</label>
		
									<div class="col-sm-10">
										<input type="text" class="form-control" id="name" name="name"  placeholder="Nombre"  value="${user.name}">
									</div>
								</div>
								<div class="form-group">
								    <label for="state" class="col-sm-2 control-label">Estado</label>
									<div class="radio col-sm-10" style="margin: 0px">
										  <label><input type="radio" name="state" value="1">Activo</label>
										  <label style="margin-left: 10px"><input type="radio" name="state" value="0">Inactivo</label>
									</div>
								</div>
								<div class="form-group">
								    <label for="state" class="col-sm-2 control-label">Roles</label>
								    <div class="checkbox col-sm-10" style="margin: 0px">
										<c:forEach var="role" items="${roles}">
										    <div>
												<label><input type="checkbox" name="roles" value="${role.id}">${role.description}</label>
											</div>
										</c:forEach>
									</div>
								</div>
								
							</div>
							
							<div class="box-footer">
								<button type="submit" class="btn btn-info pull-right">Enviar</button>
							</div>
							
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						</form>
					</div>
		
				</div>
			</div>
		</section>

<script>
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>

