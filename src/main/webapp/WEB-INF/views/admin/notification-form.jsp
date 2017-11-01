<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<tiles:insertDefinition name="d2d.dashboard">

	<tiles:putAttribute name="title">
		Usuarios
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<section class="content">
			<!-- Custom Tabs -->
			<div class="nav-tabs-custom">
				<ul class="nav nav-tabs">
					<li class="active"><a href="#tab_1" data-toggle="tab">Formulario de Notificaciones</a></li>
				</ul>
				
				<div class="row" style="margin:35px!important;">
					<span class="msg">
						<h3>Recomendaciones para la carga del formulario</h3>
						<p>En la sección "A quién se envía" hay varias subsecciones para determinar si se le envía a:</p>
						<ul style="text-align:left;">
							<li style="margin-bottom:10px;">El Checkbox "Enviar a toda la base de usuarios". Selecciona a todos los usuarios de la base. Esta opción es mandatoria e ignora a otras validaciones.</li>
							<li style="margin-bottom:10px;">La opción de "IDs de usuarios especificos" le enviará a todas los usuarios cuyos ID se escriban separados por "," en este campo, ignorando cualquier otra validación. El objetivo de este campo es cuando queremos enviar un pequeño grupo de usuarios que no compartan configuraciones de otro filtro.</li>
							<li style="margin-bottom:10px;">En el bloque "Seleccionar ocupaciones" se permite la selección de usuario por las categorías seleccionadas. La lógica utilizada en este apartado es similar a la edición de notas. Si seleccionás "Médico" y ninguna "especialidad" le enviará a todos los médicos. En cambio, si seleccionas "Médico"-"Alergista", solamente enviará a los Médicos que tengan la especialidad Alergista y a nadie más.</li>
							<li style="margin-bottom:10px;">En "Seleccionar usuario por Sponsors" el sistema buscará a los usuarios cuyo suscripción activa (no vencida) corresponda al ID de Sponsor seleccionado. Se puede chequear más de una opción. Se pueden chequear todos los sponsors.</li>
							<li style="margin-bottom:10px;">Como configuración adicional se puede determinar si se le envía a los usuario B sin Sponsor, que incluye a todos los B que no tengan suscripciones Activas de ningún tipo.</li>
							<li style="margin-bottom:10px;">La última configuración adicional agrega al grupo de los receptores del mensaje a los usuario A.</li>
							<li style="margin-bottom:10px;">Envío de Test, permite probar el mensaje. En esa sección se agregarán los IDs de usuarios separados por comas y tiene su propio botón para el envío.</li>
							<li style="margin-bottom:10px;">Complete el "Contenido de la notificación" y presione "Enviar Notificaciones".</li>
						</ul>
					</span>
				</div>
				
				<div class="tab-content">
					<form:form method="POST" id="pushForm" modelAttribute="notificationForm" action="${pageContext.request.contextPath}/admin/BoNotification/send?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data">
						<div class="tab-pane active" id="tab_1">
							<div class="row" style="margin-bottom:15px;">
								<div class="col-md-12">
									<h2>A quién se envía:</h2>
								</div>
							</div>
							<div class="col-md-12">
								<c:if test="${not empty errors['idsError']}">
									<span class="error"><c:out value="${errors['idsError']}"/></span>
								</c:if>
							</div>
							<div class="row" style="margin-bottom:10px;">
								<div class="col-md-12">
									<form:checkbox id="allUser" path="allUser" onchange="alluserAction(this)"/>
									<form:label path="allUser">Enviar a toda la base de usuarios</form:label>
								</div>
							</div>
							<div style="border: 1px solid #999; padding: 20px; display: inline-block; width: 95%; margin:10px;">
								<div class="row">
									<div class="col-md-12">
										<label>IDs de usuarios especificos</label>
									</div>
									<div class="col-md-12">
										<form:textarea id="userIds" path="userIds" name="userIds" class="col-md-12" placeholder="Ejemplo: 1,2,55,678"></form:textarea>
									</div>
								</div>
								<div class="row" style="margin-bottom:10px;">
									<div class="col-md-12">									
										<label style="font-weight:400;">(Solo se aceptan numeros separados por comas)</label>
									</div>
								</div>
							</div>
			
							<!-- Ocupaciones y Especialidades-->
							<div style="border: 1px solid #999; padding: 20px; display: inline-block; width: 95%; margin:10px;">
								<div class="row">
									<div class="col-md-12">
										<h3>Seleccionar ocupaciones</h3>
									</div>
								</div>
								<div class="row">
									<div class="col-md-2">
										<c:if test="${not empty msg}">
											<div class="msg">${msg}</div>
										</c:if>
										<label>Ocupaciones: </label>
									</div>
									<div class="col-md-10">
										<select id="occupationsSelect" class="selectpicker col-sm-12" multiple>
											<c:forEach var="occupation" items="${occupationList}">
												<option value="<c:out value="${occupation.id}"/>"><c:out value="${occupation.name}"/></option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="row" style="margin-top:25px;">
									<div class="col-md-12">
										<h3>Seleccionar Especialidades</h3>
									</div>
								</div>
								<div id="specialtiesBox">
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
									<div id="occupationIdsBox"></div>
									<div id="specialtyIdsBox"></div>
								
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								</div>
							</div>
							<!-- Fin de Ocupaciones y Especialidades -->
							<!--Sponsors -->
							<div style="border: 1px solid #999; padding: 20px; display: inline-block; width:95%; margin:10px;">
								<div class="box-body">
									<h3>Seleccionar usuario por Sponsors</h3>
									<div class="row">
										<input id="sendUserBAllSponsor" type="checkbox" name="sendUserBAllSponsor" onchange="alluserAction(this)"/>
										<span><b>Enviar a todos los Sponsors</b></span>
									</div>
									<div id="sponsorsBox" style="display: block;">
										<c:forEach var="obj" items="${sponsorList}">
											<div class="row">
												<div>
													<input type="checkbox" name="sponsors" value="${obj.id}" />
													<span>${obj.name}</span>
												</div>
											</div>
										</c:forEach>
									</div>
									<hr style="width:100%; color: black; height:1px; background-color:black;" />
									
									<h4>Configuraciones adicionales</h4>
									<div class="row">
										<input type="checkbox" name="sendUserB"/>
										<span>Enviar a los Usuarios B sin Sponsor</span>
									</div>
									<div class="row">
										<input type="checkbox" name="sendUserA"/>
										<span>Enviar a los Usuarios A</span>
									</div>
								</div>
							</div>
							<!-- Fin Sponsors -->
							<div style="border: 1px solid #999; padding: 20px; display: inline-block; width:95%; margin:10px; background-color: #d9edf7; border-color: #bce8f1;">
								<div class="row">
									<h4>Envío de test</h4>
								</div>
								<div class="row">
									<div class="col-md-2">
										<label>IDs de usuarios</label>
									</div>
									<div class="col-md-8">
										<form:input id="userTestIds" path="userTestIds" name="userTestIds" class="col-md-12" placeholder="Ejemplo: 1,2,55,678"></form:input>
									</div>
									<div class="col-md-2">
										<button type="submit" formaction="${pageContext.request.contextPath}/admin/BoNotification/send?${_csrf.parameterName}=${_csrf.token}&sendTest=true"  class="btn btn-info pull-right">Enviar solo prueba</button>
									</div>
								</div>
								<div class="row" style="margin-bottom:10px;">
									<div class="col-md-12">									
										<label style="font-weight:400;">(Usuarios para envios de test. Solo se aceptan numeros separados por coma)</label>
									</div>
								</div>
								<div class="row">
									<c:if test="${not empty errors['idsTestError']}">
										<span class="error"><c:out value="${errors['idsTestError']}"/></span>
									</c:if>
								</div>
							</div>
							
							<hr style="width: 100%; color: black; height: 2px; background-color:black;" />
							
							<div class="row">
								<h2>Contenido de la notificación:</h2>
							</div>
							<div class="row" style="margin-top:25px;">
								<div class="col-md-2">
									<label>Título</label>
								</div>
								<div class="col-md-10">
									<form:input class="col-md-12" path="titulo" required="true" oninvalid="this.setCustomValidity('El titulo no puede estar vacio.')" oninput="setCustomValidity('')"></form:input>
								</div>
							</div>
							<c:if test="${not empty errors['titleError']}">
								<div class="row">
									<div class="col-md-12"><span class="error"><c:out value="${errors['titleError']}"/></span></div>
								</div>
							</c:if>
							<div class="row" style="margin-top:25px;">
								<div class="col-md-2">
									<form:label path="message">Mensaje</form:label>
								</div>
								<div class="col-md-10">
									<form:input path="message" class="col-md-12" required="true" oninvalid="this.setCustomValidity('El mensaje no puede estar vacio.')" oninput="setCustomValidity('')"></form:input>
								</div>
							</div>
							<c:if test="${not empty errors['messageError']}">
								<div class="row">
									<div class="col-md-12"><span class="error"><c:out value="${errors['messageError']}"/></span></div>
								</div>
							</c:if>
							
							<div class="box-footer">
								<div class="pull-left">
									<button type="submit"class="btn btn-info" style="display:none;">Guardar Notificacion como Template</button>
								</div>
								
								<div class="pull-right">
									<button type="button" class="btn btn-info" data-toggle="modal" data-target="#myModal">Enviar Notificaciones</button>
								</div>
							</div>
							
							<c:if test="${not empty errors['pushSuccess']}">
								<div class="row" style="margin-top:25px;">
									<span class="msg"><c:out value="${errors['pushSuccess']}"/></span>
								</div>
							</c:if>
							
							<c:if test="${not empty errors['pushFailed']}">
								<div class="row" style="margin-top:25px;">
									<span class="error"><c:out value="${errors['pushFailed']}"/></span>
								</div>
							</c:if>
								
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<div id="occupationIdsBox"></div>
							<div id="specialtyIdsBox"></div>
						</div>
					</form:form>		
				</div>
			</div>
		</section>
	</tiles:putAttribute>
</tiles:insertDefinition>	


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
				<button type="button" class="btn btn-success" onclick="submitFormModal();">Enviar</button>
			</div>
		</div>
	</div>
</div>


<script>
$( document ).ready(function() {
	alluserAction($("#sendUserBAllSponsor").get(0));
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

function alluserAction(checkboxElem) {
	console.log(checkboxElem);
	  if (checkboxElem.checked) {
		  console.log("INVI");
		  $("#sponsorsBox").css('display','none');
	  } else {
		  console.log("NO INVI");
		  $("#sponsorsBox").css('display','block');
	  }
}

</script>


