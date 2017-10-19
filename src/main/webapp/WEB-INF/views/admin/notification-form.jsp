<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<tiles:insertDefinition name="d2d.dashboard">

	<tiles:putAttribute name="title">
		Usuarios
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<section class="content">
			<div class="row">
				<div class="col-md-12">
					<!-- Custom Tabs -->
					<div class="nav-tabs-custom">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#tab_1" data-toggle="tab">Formulario
									de Notificaciones</a></li>
						</ul>
						
						<div class="tab-content">
							<form:form method="POST" id="pushForm" modelAttribute="notificationForm" action="${pageContext.request.contextPath}/admin/BoNotification/send?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data">
								<div class="tab-pane active" id="tab_1">
									<label>IDs de usuarios especificos</label>
									<label>(Solo se aceptan numeros separados por coma)</label><br>
									<form:input id="userIds" path="userIds" name="userIds"></form:input>
									<c:if test="${not empty errors['idsError']}">
										<br>
										<c:out value="${errors['idsError']}"/>
									</c:if>
									<br>
									<div>
										<form:checkbox id="allUser" path="allUser" onchange="alluserAction(this)"/>
										<form:label  path="allUser" style="">Enviar a toda la base de usuario</form:label>
									</div>
									<!-- Segundo input-->
									<label>IDs de usuarios especificos para <big>Test</big></label>
									<label>(Solo se aceptan numeros separados por coma)</label><br>
									<form:input id="userTestIds" path="userTestIds" name="userTestIds"></form:input>
									<c:if test="${not empty errors['idsTestError']}">
										<br>
										<c:out value="${errors['idsTestError']}"/>
									</c:if>
									<br>
									<label>Titulo</label>
									<br>
									<form:input path="titulo" required="true" oninvalid="this.setCustomValidity('El titulo no puede estar vacio.')" oninput="setCustomValidity('')"></form:input>
									<c:if test="${not empty errors['titleError']}">
										<br>
										<c:out value="${errors['titleError']}"/>
									</c:if>
									<br>
									<form:label path="message">Message</form:label>
									<br>
									<form:input path="message" required="true" oninvalid="this.setCustomValidity('El mensaje no puede estar vacio.')" oninput="setCustomValidity('')"></form:input>
									<c:if test="${not empty errors['messageError']}">
										<br>
										<c:out value="${errors['messageError']}"/>
									</c:if>
									<!-- Ocupaciones y Especialidades-->
									<section id="note-editor" class="content">
										<div class="row">
											<div class="col-md-offset-3 col-md-6">
									
												<div class="nav-tabs-custom">
										
													<c:if test="${not empty msg}">
														<div class="msg">${msg}</div>
													</c:if>
							<%-- 						<form:form method="POST" modelAttribute="noteForm" autocomplete="off"  action="${pageContext.request.contextPath}/admin/notes/save?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data"> --%>
														<div class="tab-content">
															
																<div class="box-body">
																<label for="name" class="control-label">Elegir Ocupaciones</label>
																	
																	<select id="occupationsSelect" class="selectpicker" multiple>
																		<c:forEach var="occupation" items="${occupationList}">
																			<option value="<c:out value="${occupation.id}"/>"><c:out value="${occupation.name}"/></option>
																		</c:forEach>
																	</select>
																
																	<div id="specialtiesBox" style="margin-top: 20px;">
																		<label for="name" class="control-label">Elegir Especialidades</label>
																		
																		
																		<c:forEach var="obj" items="${specialtiesLists}">
																			<div id="specialtiesBox-${obj.key.id}" style="margin-top: 10px;">
																				<span>${obj.key.name}</span>
																				<select id="specialtiesSelect-${obj.key.id}" class="selectpicker specialtiesCombo" multiple>
																					<c:forEach var="specialty" items="${obj.value}">
																						<option value="<c:out value="${specialty.id}"/>"><c:out value="${specialty.name}"/></option>
																					</c:forEach>
																				</select>
																			</div>
																			
																			<script>
																				$('#specialtiesSelect-${obj.key.id}').selectpicker('val', ${noteForm.specialties});
																			</script>
																		</c:forEach>
																		
																		<script>
																			$(".specialtiesCombo").on('changed.bs.select', function (e) {
																				loadSpecialtiesInput();
																			});
																		</script>
																	</div>
																</div>
																
																<div id="occupationIdsBox"></div>
																<div id="specialtyIdsBox"></div>
														</div>
														<input path="id" type="hidden" />
														<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
													
							<%-- 						</form:form> --%>
												</div>
											</div>
										</div>
									</section>
									<!--Fin Ocupaciones y Especialidades -->
									<div class="box-footer" style="margin-top: 15px">
										<div class="box-footer">
											<button type="submit"class="btn btn-info pull-right">Guardar Notificacion como Template</button>
										</div>
										<div class="box-footer">
											<button type="submit" formaction="${pageContext.request.contextPath}/admin/BoNotification/send?${_csrf.parameterName}=${_csrf.token}&sendTest=true" class="btn btn-info pull-right">Enviar solo prueba</button>
										</div>
										<div class="box-footer">
											<button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal">Enviar Notificaciones</button>
										</div>
										<c:if test="${not empty errors['pushResult']}">
										<br>
											<c:out value="${errors['pushResult']}"/>
										</c:if>
									</div>
								</div>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<div id="occupationIdsBox"></div>
								<div id="specialtyIdsBox"></div>
							</form:form>
							<!-- /.tab-pane -->
						</div>
						<!-- /.tab-content -->
					</div>
					<!-- nav-tabs-custom -->
				</div>
			</div>
		</section>
	</tiles:putAttribute>
</tiles:insertDefinition>

<!-- Modal de Intereses -->
<!-- <div id="myModal" class="modal fade" role="dialog"> -->
<!--   <div class="modal-dialog"> -->

<!--     Modal content -->
<!--     <div class="modal-content"> -->
<!--       <div class="modal-header"> -->
<!--         <button type="button" class="close" data-dismiss="modal">&times;</button> -->
<!--         <h4 class="modal-title">Definir Intereses</h4> -->
<!--       </div> -->
<!--       <div class="modal-body"> -->
<!--         <section id="note-editor" class="content"> -->
<!-- 			<div class="row"> -->
<!-- 				<div class="col-md-offset-3 col-md-6"> -->
		
<!-- 					<div class="nav-tabs-custom"> -->
			
<%-- 						<c:if test="${not empty msg}"> --%>
<%-- 							<div class="msg">${msg}</div> --%>
<%-- 						</c:if> --%>
<%-- <%-- 						<form:form method="POST" modelAttribute="noteForm" autocomplete="off"  action="${pageContext.request.contextPath}/admin/notes/save?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data"> --%> --%>
<!-- 							<div class="tab-content"> -->
								
<!-- 									<div class="box-body"> -->
<!-- 									<label for="name" class="control-label">Elegir Ocupaciones</label> -->
										
<!-- 										<select id="occupationsSelect" class="selectpicker" multiple> -->
<%-- 											<c:forEach var="occupation" items="${occupationList}"> --%>
<%-- 												<option value="<c:out value="${occupation.id}"/>"><c:out value="${occupation.name}"/></option> --%>
<%-- 											</c:forEach> --%>
<!-- 										</select> -->
									
<!-- 										<div id="specialtiesBox" style="margin-top: 20px;"> -->
<!-- 											<label for="name" class="control-label">Elegir Especialidades</label> -->
											
											
<%-- 											<c:forEach var="obj" items="${specialtiesLists}"> --%>
<%-- 												<div id="specialtiesBox-${obj.key.id}" style="margin-top: 10px;"> --%>
<%-- 													<span>${obj.key.name}</span> --%>
<%-- 													<select id="specialtiesSelect-${obj.key.id}" class="selectpicker specialtiesCombo" multiple> --%>
<%-- 														<c:forEach var="specialty" items="${obj.value}"> --%>
<%-- 															<option value="<c:out value="${specialty.id}"/>"><c:out value="${specialty.name}"/></option> --%>
<%-- 														</c:forEach> --%>
<!-- 													</select> -->
<!-- 												</div> -->
												
<!-- 												<script> -->
// 													$('#specialtiesSelect-${obj.key.id}').selectpicker('val', ${noteForm.specialties});
<!-- 												</script> -->
<%-- 											</c:forEach> --%>
											
<!-- 											<script> -->
// 												$(".specialtiesCombo").on('changed.bs.select', function (e) {
// 													loadSpecialtiesInput();
// 												});
<!-- 											</script> -->
<!-- 										</div> -->
<!-- 									</div> -->
									
<!-- 									<div id="occupationIdsBox"></div> -->
<!-- 									<div id="specialtyIdsBox"></div> -->
<!-- 							</div> -->
<!-- 							<input path="id" type="hidden" /> -->
<%-- 							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> --%>
						
<%-- <%-- 						</form:form> --%> --%>
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</section> -->
<!--       </div> -->
<!--       <div class="modal-footer"> -->
<!--         <button type="button" class="btn btn-default" data-dismiss="modal">Aceptar</button> -->
<!--       </div> -->
<!--     </div> -->

<!--   </div> -->
<!-- </div> -->

<!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<!--     Modal content -->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Confirmar Envio</h4>
			</div>
			<div class="modal-body">
				<h4>Se enviara una notificacion Push a los usuarios ingresados y a los intereses seleccionados. ¿Esta seguro que quiere realizar el envio o 
				prefiere editar algunos datos antes de enviar?</h4>
			
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Editar datos</button>
				<button type="button" class="btn btn-default" onclick="submitFormModal();">Enviar</button>
			</div>
		</div>
	</div>
</div>


<script>

$( document ).ready(function() {
	alluserAction($("#allUser").get(0));
	arrayOccupationIds = $("#occupationsSelect").val();
	for (var i = 0; i < arrayOccupationIds.length; i++) {
		id = arrayOccupationIds[i];
		$("#occupationIdsBox").append('<input type="hidden" name="occupations" value="' + id + '">');
	}
	loadSpecialtiesInput();
});


function submitFormModal() {
	$("#pushForm").submit();
}


function alluserAction(checkboxElem) {
	console.log(checkboxElem);
	  if (checkboxElem.checked) {
		  $("#userIds").prop('disabled', true);
	  } else {
		  $("#userIds").prop('disabled', false);
	  }
	}
	
$('#occupationsSelect').selectpicker('val', ${noteForm.occupations});

arraySpecialtiesIds = [];
lastArrayOccupationIds = [];

$("#occupationsBox").empty();

$('#occupationsSelect').on('changed.bs.select', function (e) {
	
	$("#occupationIdsBox").empty();
	arrayOccupationIds = $("#occupationsSelect").val();

	//Eliminar Box de Especialidades que no están elegidas
	for (var i = 0; i < lastArrayOccupationIds.length; i++) {
		if(arrayOccupationIds == null || arrayOccupationIds.includes(lastArrayOccupationIds[i]) == false) {
			$("#specialtiesBox-" + lastArrayOccupationIds[i]).remove();
		}
	}
	
	if(arrayOccupationIds == null) {
		arrayOccupationIds = [];
	}
	lastArrayOccupationIds = arrayOccupationIds;
	
	for (var i = 0; i < arrayOccupationIds.length; i++) {
		id = arrayOccupationIds[i]
		
		if($('#specialtiesSelect-' + id).size() <= 0) {
			
			$.ajax({
		        url: 'specialties/' + id,
		        type: 'GET',
		        success: function(data) {
		        	$("#specialtiesBox").append(data);
		        }
		    });
		}
		
		$("#occupationIdsBox").append('<input type="hidden" name="occupations" value="' + id + '">');
	}

	loadSpecialtiesInput();
});

function loadSpecialtiesInput() {
	$("#specialtyIdsBox").empty();
	
	$(".specialtiesCombo").each(function() {
		currentArray = $(this).val();
		
		if(currentArray != null && currentArray.length > 0) {
			for (var i = 0; i < currentArray.length; i++) {
				$("#specialtyIdsBox").append('<input type="hidden" name="specialties" value="' + currentArray[i] + '">');
			}
		}
		
	});
}
	
</script>