<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<tiles:insertDefinition name="d2d.dashboard">

	<tiles:putAttribute name="title">
		Administrar de ofertas
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
	
		<section id="note-editor" class="content">
			<div class="row">
				<div class="form-wpp-container">
					<div class="form-wpp-split-left">
						<div class="col-md-12">
							<div class="box box-info">
								<div class="box-header with-border">
									<h3 class="box-title">Crear o modificar Oferta de trabajo</h3>
								</div>
								
<%-- 								<c:if test="${not empty msg}"> --%>
<%-- 									<div class="msg">${msg}</div> --%>
<%-- 								</c:if> --%>
								
<%-- 								<c:if test="${not empty errors}"> --%>
<%-- 								    <c:forEach var="error" items="${errors}"> --%>
<%-- 										<div class="error">${error}</div> --%>
<%-- 									</c:forEach> --%>
<%-- 								</c:if> --%>
				
<%-- 								<form:form method="POST" modelAttribute="userForm" autocomplete="off"  action="${pageContext.request.contextPath}/admin/users/save?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data"> --%>

 (Determinar metodología: podría ser con un buscador)
Seleccionar tipo (Temporal/Permanente)
Seleccionar Ocupación
Seleccionar Especialidad (si aplica)
Seleccionar Tarea
Determinar tipo de institución
Ingresar un Título (Si es Permanente)
Ingresar un subtítulo (Si es Permanente)
Seleccionar Zona
Determinar Fecha del trabajo (Si es Temporal)
Determinar Hora del trabajo (Si es Temporal)
Texto del aviso

									<div class="box-body">
										<div class="form-group">
											<label for="email" class="col-sm-10 control-label">Usuario al que se le cargará la oferta: [??????????]</label>
											<div class="col-sm-2">
 												<button type="button" class="btn btn-default pull-right">Seleccionar Usuario</button>
											</div>
										</div>
										<div class="form-group">
											<label for="name" class="col-sm-2 control-label">Tipo de oferta</label>
				
											<div class="col-sm-10">
<%-- 												<form:radiobutton path="active" value="true" element="span class='radio'"/>Temporal <br> --%>
<%-- 												<form:radiobutton path="active" value="false" element="span class='radio'"/>Permanente --%>
<%-- 												<form:input path="name" class="form-control"  placeholder="Nombre" autocomplete="false" required="true"/> --%>
											</div>
										</div>
										<div class="form-group">
											<label for="password" class="col-sm-2 control-label">Password</label>
				
											<div class="col-sm-10">
<%-- 												<form:input path="password" class="form-control" type="password"  placeholder="Password"
												 id="password" autocomplete="new-password"/>  --%>
												<!-- form:input path="password" class="form-control" type="password"  placeholder="Password" 
												 readonly="true"  onfocus="this.removeAttribute('readonly'); this.val('')" id="password" autocomplete="off"/ -->
											</div>
										</div>
										<div class="form-group">
										    <label for="state" class="col-sm-2 control-label">Estado</label>
											<div class="col-sm-10">
<%-- 											     <form:radiobutton path="active" value="true" element="span class='radio'"/>Activo <br> --%>
<%-- 											     <form:radiobutton path="active" value="false" element="span class='radio'"/>Inactivo --%>
											</div>
										</div>
										<div class="form-group">
										    <label for="state" class="col-sm-2 control-label">Roles</label>
										    <div class="col-sm-10">
<%-- 										        <form:checkboxes path="rolesIds" items="${roles}"  itemValue="id" itemLabel="description" element="span class='checkbox'"/> --%>
											</div>
										</div>
										
									</div>
									
									<div class="box-footer">
										<button type="submit" class="btn btn-info pull-right">Guardar y publicar</button>
									</div>
<%-- 									<form:input path="id" type="hidden" /> --%>
<%-- 									<form:input path="passwordChanged" type="hidden"/> --%>
<%-- 									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> --%>
<%-- 								</form:form> --%>
							</div>
				
						</div>
					</div>
					<div class="form-wpp-split-right">
						<div class="form-wpp-phone"><img src="<c:url value="/images/admin/abm-offers/phone-body.png" />"></div>
						<div class="form-wpp-screen"><img src="<c:url value="/images/admin/abm-offers/phone-preview.png" />"></div>
						<div class="form-wpp-offer">
							<div>
								<div class="form-wpp-offer-top">
									<div class="form-wpp-offer-top-avatar"><img src="<c:url value="/images/user-philip-more-80x80.jpg" />"></div>
									<div class="form-wpp-offer-top-mask"><img src="<c:url value="/images/admin/abm-offers/mask-avatar.png" />"></div>
									<div class="form-wpp-offer-top-title-container">
										<span class="form-wpp-title">Publicar oferta temporal</span>
										<span class="form-wpp-subtitle">vista previa del aviso. Paso 3 de 3</span>
									</div>
									<div class="form-wpp-offer-top-names-container">
										<span class="form-wpp-title form-wpp-title-sm">Nombre y Apellido</span>
										<span class="form-wpp-subtitle">Company name</span>
									</div>
								</div>
								<div class="form-wpp-offer-bottom">
									<div class="form-wpp-offer-bottom-title-container">
										<span class="form-wpp-title form-wpp-title-sm">Occupation, Specialty</span>
										<span class="form-wpp-subtitle">Para trabajos de Task</span>
									</div>
									<div style="padding:0 17px 0; display:inline-block;">
										<span class="form-wpp-body-text">Fecha: 10/11/2017</span>
										<span class="form-wpp-body-text">Hora: 10:29hs</span>
										<span class="form-wpp-body-text">Zona: SECTOR ANTARTICO ARGENTINO, TIERRA DEL FUEGO</span>
										<span class="form-wpp-body-text" style="margin:18px 0 0;">Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso Texto del aviso </span>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>

		<script>
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>

