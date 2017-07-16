<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<tiles:insertDefinition name="d2d.dashboard">

	<tiles:putAttribute name="title">
		Otros Contenidos
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
		
						<form:form method="POST" modelAttribute="noteForm" autocomplete="off"  action="${pageContext.request.contextPath}/admin/notes/save?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data">
							<div class="box-body">
								<div class="form-group">
									<div class="col-sm-2">
										<label for="name" class="control-label">T&iacute;tulo</label>
									</div>
									<div class="col-sm-10">
										<form:input path="title" class="form-control"  placeholder="Título"  
										autocomplete="false" required="true"/>
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-2">
										<label for="subtitle" class="control-label">Subt&iacute;tulo</label>
									</div>
		
									<div class="col-sm-10">
										<form:input path="subtitle" class="form-control"  placeholder="Subtítulo"  
										autocomplete="false" required="true"/>
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-2">
										<label for="subtitle" class="control-label">Categor&iacute;a</label>
									</div>
		
									<div class="col-sm-10">
										<form:select  path="category" class="form-control" required="true">
											<form:option value="NONE"> -- Elegir Categor&iacute;a --</form:option>
											<form:options items="${categoryList}"></form:options>
										</form:select>
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-2">
										<label for="state" class="control-label">Estado</label>
									</div>
									<div class="col-sm-10">
									     <form:radiobutton path="active" value="true" element="span class='radio'"/>Activo <br>
									     <form:radiobutton path="active" value="false" element="span class='radio'"/>Inactivo
									</div>
								</div>
								
								<div class="form-group">
									<div class=col-sm-2>
										<label for="content" class="control-label">Publicaci&oacute;n</label>
									</div>
									<div class=col-sm-10>
										<div class="input-group date">
											<div class="input-group-addon">
												<i class="fa fa-calendar"></i>
											</div>
											<input type="text" class="form-control pull-right" id="publishingDateForView" name="publishingDateForView" value="${noteForm.publishingDateForView}" data-inputmask="'alias': 'dd/mm/yyyy'" data-mask>
										</div>
									</div>
								</div>
								
								<div class="form-group">
									<div class=col-sm-2>
										<label for="content" class="control-label">Expiraci&oacute;n</label>
									</div>
									<div class=col-sm-10>
										<div class="input-group date">
											<div class="input-group-addon">
												<i class="fa fa-calendar"></i>
											</div>
											<input type="text" class="form-control pull-right" id="expirationDateForView" name="expirationDateForView" value="${noteForm.expirationDateForView}" data-inputmask="'alias': 'dd/mm/yyyy'" data-mask>
										</div>
									</div>
								</div>
								
								<div class="form-group">
									<div class=col-sm-2>
										<label for="content" class="control-label">Contenido</label>
									</div>
		
									<div class="col-sm-10">
										<form:textarea path="content" class="form-control"  placeholder="Contenido"  
										autocomplete="false" required="true" rows="10" cssStyle="margin-bottom: 10px;"/>
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-2">
										<label for="image" class="control-label">Imagen</label>
									</div>
									
									<div class="col-sm-10">
										<input class="form-control" type="file" name="file">
									</div>
									<div class="col-sm-offset-2 col-sm-10">
										<c:choose>
										    <c:when test="${noteForm.image != null && !noteForm.image.equals('')}">
												<div>Imagen actual</div>
												<img alt="image" src="data:image/png;base64,${noteForm.image}" style="max-width: 100%;">
										    </c:when>
										</c:choose>
									</div>
								</div>
							</div>
							
							<div class="box-footer">
								<button type="submit" class="btn btn-info pull-right">Guardar</button>
							</div>
							<form:input path="id" type="hidden" />
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						</form:form>
					</div>
		
				</div>
			</div>
		</section>
		
		<script>
			$('#publishingDateForView').datepicker({
				autoclose: true,
				format: "dd-mm-yyyy"
			});
			$('#expirationDateForView').datepicker({
				autoclose: true,
				format: "dd-mm-yyyy"
			});
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>

