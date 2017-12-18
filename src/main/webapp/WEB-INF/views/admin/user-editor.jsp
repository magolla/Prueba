<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
						
						<c:if test="${not empty msg}">
							<div class="msg">${msg}</div>
						</c:if>
						
						<c:if test="${not empty errors}">
						    <c:forEach var="error" items="${errors}">
								<div class="error">${error}</div>
							</c:forEach>
						</c:if>
		
						<form:form method="POST" modelAttribute="userForm" autocomplete="off"  action="${pageContext.request.contextPath}/admin/users/save?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data">
							<div class="box-body">
								<div class="form-group">
									<label for="email" class="col-sm-2 control-label">Email</label>
									<div class="col-sm-10">
									    <form:input type="email" path="email" class="form-control"  placeholder="Email" required="true"/>
									</div>
								</div>
								<div class="form-group">
									<label for="name" class="col-sm-2 control-label">Nombre</label>
		
									<div class="col-sm-10">
										<form:input path="name" class="form-control"  placeholder="Nombre"  
										autocomplete="false" required="true"/>
									</div>
								</div>
								<div class="form-group">
									<label for="password" class="col-sm-2 control-label">Password</label>
		
									<div class="col-sm-10">
										<form:input path="password" class="form-control" type="password"  placeholder="Password" 
										 id="password" autocomplete="new-password"/>
										<!-- form:input path="password" class="form-control" type="password"  placeholder="Password" 
										 readonly="true"  onfocus="this.removeAttribute('readonly'); this.val('')" id="password" autocomplete="off"/ -->
									</div>
								</div>
								<div class="form-group">
								    <label for="state" class="col-sm-2 control-label">Estado</label>
									<div class="col-sm-10">
									     <form:radiobutton path="active" value="true" element="span class='radio'"/>Activo <br>
									     <form:radiobutton path="active" value="false" element="span class='radio'"/>Inactivo
									</div>
								</div>
								<div class="form-group">
								    <label for="state" class="col-sm-2 control-label">Roles</label>
								    <div class="col-sm-10">
								        <form:checkboxes path="rolesIds" items="${roles}"  itemValue="id" itemLabel="description" element="span class='checkbox'"/>
									</div>
								</div>
								
							</div>
							
							<div class="box-footer">
								<button type="submit" class="btn btn-info pull-right">Guardar</button>
							</div>
							<form:input path="id" type="hidden" />
							<form:input path="passwordChanged" type="hidden"/>
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						</form:form>
					</div>
		
				</div>
			</div>
		</section>
		<script>
			$('#password').on('input',function(e){
			  if($('#passwordChanged').val() == "false"){
				  $('#passwordChanged').val("true");
				  if($('#password').val().length>0==true){
					  $('#password').val("");  
				  }
			  }	
		    });
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>

