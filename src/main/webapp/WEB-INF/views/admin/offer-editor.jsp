<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<tiles:insertDefinition name="d2d.dashboard">

	<tiles:putAttribute name="title">
		ABM de ofertas
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
	
		<section id="note-editor" class="content">
			<div class="row">
				<div class="form-wpp-container">
					<div class="form-wpp-split-left">
						<div class="col-md-12">
							<div class="box box-info">
								<div class="box-header with-border">
									<h3 class="box-title">Crear o modificar ofertas laborales</h3>
								</div>
								
								<c:if test="${not empty param.confirmed}">
									<div class="msg" style="margin:20px;">${param.confirmed}</div>
								</c:if>
								
<%-- 								<c:if test="${not empty errors}"> --%>
<%-- 								    <c:forEach var="error" items="${errors}"> --%>
<%-- 										<div class="error">${error}</div> --%>
<%-- 									</c:forEach> --%>
<%-- 								</c:if> --%>
				
								<form:form method="POST" modelAttribute="boJobDTO" autocomplete="off" enctype="multipart/form-data">
									<div class="box-body">
										<div class="form-group">
											<c:if test="${empty offerId}">
												<label class="col-sm-10 control-label">Usuario al que se le cargar&aacute; la oferta: <span id="selectedUser" class="unselectedUser">NO SELECCIONADO</span></label>
												<div class="col-sm-2">
													<button type="button" class="btn btn-success pull-right" data-toggle="modal" data-target="#userModal">Seleccionar Usuario</button>
													<form:errors path="userId" class="error-text"></form:errors>
												</div>
											</c:if>
											<form:input path="userId" id="userId" val='${boJobDTO.userId}' hidden="true"></form:input>
											<form:hidden id="userName" path="name" value="${boJobDTO.name}"/>
											<form:hidden id="userLastName" path="lastName" value="${boJobDTO.lastName}"/>
										</div>
										<!--El siguiente div se oculta hasta que no se selecci&oacute;ne un usuario -->
										<c:choose>
											<c:when test="${boJobDTO.userId != 0}">
												<div id="hideableFormPart">
											</c:when>
											<c:otherwise>
												<div id="hideableFormPart" hidden="true">
											</c:otherwise>
										</c:choose>
										<!-- Hay un TAB para adentro porque en el condici&oacute;nal anterior se agrega un div -->
										<!-- Tipo de oferta -->
											<c:if test="${empty offerId}">
												<h3>Tipo de oferta</h3>
												<div class="form-group">
													<c:choose>
														<c:when test="${boJobDTO.permanent eq true}">
															<div class="form-check">
																<label class="form-check-label">
																<form:radiobutton id="temporalRadio" path="permanent" name="permanent" value="false" element="span class='radio'" class="form-check-input"></form:radiobutton>
																Temporal</label>
															</div>
															<div class="form-check">
																<label class="form-check-label">
																<form:radiobutton id="permanentRadio" path="permanent" name="permanent" value="true"  checked="checked" element="span class='radio'" class="form-check-input"></form:radiobutton>
																Permanente</label>
															</div>
														</c:when>
														<c:otherwise>
															<div class="form-check">
																<label class="form-check-label">
																<form:radiobutton id="temporalRadio" path="permanent" name="permanent" value="false"  element="span class='radio'"></form:radiobutton>
																Temporal</label>
															</div>
															<div class="form-check">
																<label class="form-check-label">
																<form:radiobutton id="permanentRadio" path="permanent" name="permanent" value="true" element="span class='radio'"></form:radiobutton>
																Permanente</label>
															</div>
														</c:otherwise>
													</c:choose>
												</div>
											</c:if>
											<c:if test="${not empty offerId}">
												<div class="form-group" hidden="true">
													<c:choose>
														<c:when test="${boJobDTO.permanent eq true}">
															<div class="form-check">
																<label class="form-check-label">
																<form:radiobutton id="temporalRadio" path="permanent" name="permanent" value="false" element="span class='radio'"></form:radiobutton>
																Temporal</label>
															</div>
															<div class="form-check">
																<label class="form-check-label">
																<form:radiobutton id="permanentRadio" path="permanent" name="permanent" value="true"  checked="checked" element="span class='radio'"></form:radiobutton>
																Permanente</label>
															</div>
														</c:when>
														<c:otherwise>
															<div class="form-check">
																<label class="form-check-label">
																<form:radiobutton id="temporalRadio" path="permanent" name="permanent" value="false"  element="span class='radio'"></form:radiobutton>
																Temporal</label>
															</div>
															<div class="form-check">
																<label class="form-check-label">
																<form:radiobutton id="permanentRadio" path="permanent" name="permanent" value="true" element="span class='radio'"></form:radiobutton>
																Permanente</label>
															</div>
														</c:otherwise>
													</c:choose>
												</div>
											</c:if>
											<!-- Nombre del profesional o empresa -->
											<div class="form-group">
												<label class="control-label">Nombre del profesional o empresa(Opcional): </label>
												<form:input id="companyScreenName" path="companyScreenName" class="form-control" />
												<form:errors path="companyScreenName" class="error-text"></form:errors>
											</div>
											<!-- Título y subtítulo -->
											<div id="titlesBox" hidden="true">
												<div class="form-group">
													<label for="offerTitle" class="control-label">Ingrese un T&iacute;tulo</label>
													<form:input id="offerTitle" path="title" var="${boJobDTO.title}" class="form-control"/>
												</div>
												<form:errors path="title" class="col-sm-10 error-text"></form:errors>
												<div class="form-group">
													<label for="offerSubtitle" class="control-label">Ingrese un Subt&iacute;tulo</label>
													<form:input id="offerSubtitle" path="subtitle" var="${boJobDTO.subtitle}" class="form-control"/>
												</div>
												<form:errors path="subtitle" class="col-sm-10 error-text"></form:errors>
											</div>
											<!-- Inicio del bloque de ocupaciones -->
											<h3>Determinar tareas</h3>
											<div class="form-group form-group-job-offer-combos">
												<label for="occupationsSelect" class="control-label">Ocupaci&oacute;n</label>
												<form:select id="occupationsSelect" class="selectpicker" path="occupationId">
												<form:option value="0">Seleccione una ocupaci&oacute;n</form:option>
													<c:forEach var="occupation" items="${occupationList}">
														<option value="<c:out value="${occupation.id}"/>"><c:out value="${occupation.name}"/></option>
													</c:forEach>
												</form:select>
											</div>
											<div id="filterBySpecialtiesBox" class="form-group form-group-job-offer-combos <c:if test="${(specialtyList == null) || (specialtyList[0].name == '')}">hide</c:if>">
												<label for="specialtiesSelect" class="control-label">Especialidad</label>
												<div id="specialtiesSelectBox">
													<select id="specialtiesSelect" class="selectpicker" title="" name="specialtyId">
														<c:forEach var="specialty" items="${specialtyList}">
															<option value="<c:out value="${specialty.id}"/>"><c:out value="${specialty.name}"/></option>
														</c:forEach>
													</select>
												</div>					
											</div>
											<div id="filterByTasksBox" class="form-group form-group-job-offer-combos <c:if test="${taskList == null}">hide</c:if>">
												<label for="tasksSelect" class="control-label">Tarea</label>
												<div id="tasksSelectBox">					
													<select id="tasksSelect" class="selectpicker" title="" name="taskId">
														<c:forEach var="task" items="${taskList}">
															<option value="<c:out value="${task.id}"/>"><c:out value="${task.name}"/></option>
														</c:forEach>
													</select>
												</div>
											</div>
											<form:errors path="occupationId" class="error-text"></form:errors>
											<!-- Tipo de institucion -->
											<h3>Tipo de instituci&oacute;n</h3>
											<div class="form-group">
												<c:choose>
													<c:when test="${boJobDTO.privateInstitution eq true}">
														<div class="form-check">
															<label class="form-check-label" >
																<form:radiobutton path="privateInstitution" checked="checked" value="true" element="span class='radio'"></form:radiobutton>
															Privada</label>
														</div>
														<div class="form-check">
															<label class="form-check-label">
																<form:radiobutton path="privateInstitution" value="false" element="span class='radio'"></form:radiobutton>
															P&uacute;blica</label>
														</div>
													</c:when>
													<c:otherwise>
														<div class="form-check">
															<label class="form-check-label">
																<form:radiobutton path="privateInstitution" value="true" element="span class='radio'"></form:radiobutton>
															Privada</label>
														</div>
														<div class="form-check">
															<label class="form-check-label">
																<form:radiobutton path="privateInstitution" value="false" checked="checked" element="span class='radio'"></form:radiobutton>
															P&uacute;blica</label>
														</div>
													</c:otherwise>
												</c:choose>
											</div>
											<!--Ubicación por zonas -->
											<h3>Zona del trabajo</h3>
											<div class="form-group">
												<label for="zones" class="control-label">Seleccionar Ubicaci&oacute;n: </label>
												<div class="input-group">
													<input id="zones" value="${boJobDTO.geoDto.name}" class="form-control ">
													<span class="input-group-btn">
														<button class="btn btn-danger" id="clearButton" type="button">Modificar</button>
													</span>
												</div>
												<form:hidden id="zoneName" path="geoDto.name" value="${boJobDTO.geoDto.name}"/>
												<form:hidden id="zoneId" path="geoDto.id" value="${boJobDTO.geoDto.id}"/>
												<form:hidden id="zoneLevel" path="geoDto.level" value="${boJobDTO.geoDto.level}"/>
												<form:errors path="geoDto.name" class="col-md-6 error-text"></form:errors>
											</div>
											<!-- Fecha de Publicación y de Expiración -->
											<div class="form-group">
												<div id="dateForOffer" class="col-md-6" style="padding-left:0px;">
													<label for="content" class="control-label">Fecha</label>
													<div class="input-group date" id="publishDatePicker">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<input type="text" class="form-control pull-right"
															id="offerDateForView" name="offerDateForView" 
															value="${boJobDTO.offerDateForView}"
															data-inputmask="'alias': 'dd/mm/yyyy'" data-mask>
													</div>
													<form:errors path="offerDate" class="error-text"></form:errors>
												</div>
												<div id="hourForOffer" class="col-md-6">
													<label for="content" class="control-label">Hora</label>
													<div class="input-group date">
														<div class="input-group-addon">
															<i class="fa fa-calendar"></i>
														</div>
														<form:input type="text" class="form-control pull-right" path="offerHour" id="offerHour"></form:input>
													</div>
													<form:errors path="offerHour" class="error-text"></form:errors>
												</div>
											</div>
											<!--Fecha de Publicacion y de Expiracion FIN -->
											<div class="form-group" style="padding-bottom:15px!important; margin:70px 0 0; display:flow-root;">
												<label for="offerText" class="control-label">Contenido de la publicaci&oacute;n</label>
												<form:textarea id="offerText" path="offerText" rows="11" class="form-control col-md-12"></form:textarea>
												<form:errors path="offerText" class="error-text"></form:errors>
											</div>
											<div class="form-group">
											<c:choose>
												<c:when test="${empty offerId}">
													 <div class="box-footer">
														<button type="submit" class="btn btn-info pull-right" formaction="${pageContext.request.contextPath}/admin/BoOffers/save?${_csrf.parameterName}=${_csrf.token}">Guardar y publicar</button>
													</div>
												</c:when>
												<c:otherwise>
													<div class="box-footer">
														<button type="submit" class="btn btn-info pull-right" formaction="${pageContext.request.contextPath}/admin/BoOffers/edit/${offerId}?${_csrf.parameterName}=${_csrf.token}">Guardar y publicar</button>
													</div>
												</c:otherwise>
											</c:choose>
											</div>
										</div>
									</form:form>
								</div>
							</div>
						</div>
					</div>
					<div class="form-wpp-split-right">
						<div class="form-wpp-phone"><img src="<c:url value="/images/admin/abm-offers/phone-body.png" />"></div>
						<div class="form-wpp-screen"><img src="<c:url value="/images/admin/abm-offers/phone-preview.png" />"></div>
						<div class="form-wpp-offer">
							<div>
								<div class="form-wpp-offer-top">
								<c:choose>
									<c:when test="${empty user.avatar}">
										<div  class="form-wpp-offer-top-avatar"><img id="userAvatar" src="<c:url value="/images/ic_avatar.png" />"></div>
									</c:when>
									<c:otherwise>
										<div  class="form-wpp-offer-top-avatar"><img id="userAvatar" src="<c:url value="data:image/png;base64,${user.avatar}" />"></div>
									</c:otherwise>
								</c:choose>
									
									<div class="form-wpp-offer-top-mask"><img src="<c:url value="/images/admin/abm-offers/mask-avatar.png" />"></div>
									<div class="form-wpp-offer-top-title-container">
										<span class="form-wpp-title" id="mobileOfferType">Publicar oferta temporal</span>
										<span class="form-wpp-subtitle">vista previa del aviso. Paso 3 de 3</span>
									</div>
									<div class="form-wpp-offer-top-names-container">
										<span id="previewName" class="form-wpp-title form-wpp-title-sm">Nombre y Apellido</span>
										<span id="previewCompanyScreen" class="form-wpp-subtitle">Company name</span>
									</div>
								</div>
								<div class="form-wpp-offer-bottom">
									<div class="form-wpp-offer-bottom-title-container">
										<span id="previewInterest" class="form-wpp-title form-wpp-title-sm">Occupation, Specialty</span>
										<span id="previewTask" class="form-wpp-subtitle">Para trabajos de Task</span>
									</div>
									<div style="padding:0 17px 0; display:inline-block;">
											<div id="previewTitlesBox">
												<span id="previewTitle" class="form-wpp-body-text">T&iacute;tulo: </span>
												<span id="previewSubtitle" class="form-wpp-body-text">Subt&iacute;tulo: </span>
											</div>
											<div id="previewDateBox">
												<span id="previewDate" class="form-wpp-body-text">Fecha: </span>
												<span id="previewHour" class="form-wpp-body-text">Hora: </span>
											</div>
										<span id="previewZone" class="form-wpp-body-text">Zona: </span>
										<span id="previewOfferText" class="form-wpp-body-text" style="margin:18px 0 0;"></span>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
		</section>
		
		
		<!-- Modal de usuarios -->
		<div id="userModal" class="modal fade bd-example-modal-lg" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
		  <div class="modal-dialog modal-lg">
		    <!-- Modal content-->
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal">&times;</button>
		        <h4 class="modal-title">Seleccionar usuario</h4>
		      </div>
		      <div id="bodyModalBootstrap" class="modal-body">
				<table id="users" class="display" cellspacing="0" width="100%">
					<thead>
					    <tr>
					        <th>ID</th>
					        <th>N&uacute;mero Tel</th>
					        <th>Nombre</th>
					        <th>Apellido</th>
					        <th>Email</th>
					        <th>Estado</th>
					        <th>Tipo Suscripci&oacute;n</th>
					        <th>Estado Suscripci&oacute;n</th>
					        <th>Acci&oacute;n</th>
					    </tr>
					</thead>
					<tfoot>
					    <tr>
					        <th>ID</th>
					        <th>N&uacute;mero Tel</th>
					        <th>Nombre</th>
					        <th>Apellido</th>
					        <th>Email</th>
					        <th>Estado</th>
					        <th>Tipo Suscripci&oacute;n</th>
					        <th>Estado Suscripci&oacute;n</th>
					        <th>Acci&oacute;n</th>
					    </tr>
					</tfoot>
				</table>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button>
		      </div>
		    </div>
		  </div>
		</div>
		<script>
		
		$('#offerDateForView').datepicker({
			autoclose: true,
			startDate: 'now',
			format: "dd-mm-yyyy"
		}).on("changeDate", function (e) {
			$('#previewDate').text('Fecha:' + " " + $('#offerDateForView').val())
		});
		
		$('input[id$="offerHour"]').inputmask(
	        "hh:mm", {
		        placeholder: "HH:MM", 
		        insertMode: false, 
		        showMaskOnHover: false,
		        hourFormat: 12
	      	}
    	);
		
		
		$(document).ready(function() {
		    $('#users').DataTable( {
		        "ajax": '<c:url value="/admin/list/public-users" />',
		        "language": {
		            "search": "Buscar:",
		            "info": "P&aacute;gina _PAGE_ de _PAGES_",
		            "paginate": {
		                "previous": "Previa",
		                "next": "Siguiente",
		              }
		          },
		        "columns": [
							{ "data": "id" },
							{ "data": "mobilePhone" },
		                    { "data": "name" },
		                    { "data": "lastname" },
		                    { "data": "email" },
		                    {
		                        "data": "active",
		                        "render": function ( data, type, full, meta ) {
		                        	 if(data==true){
		                        		 return 'Activo';
		                        	 } else {
		                        		 return 'Inactivo';
		                        	 }
		                        }
		                     },  
		                     {
		                         "data": "userB",
		                         "render": function ( data, type, full, meta ) {
		                         	 if(data==true){
		                         		 return 'Candidato';
		                         	 } else {
		                         		 return 'Oferente';
		                         	 }
		                         }
		                      },
		                      {
		                          "data": "hasActiveSuscription",
		                          "render": function ( data, type, full, meta ) {
		                        	 if(full.userB==true){ 
			                          	 if(data==true){
			                          		 return 'Suscripto';
			                          	 } else {
			                          		 return 'No suscripto';
			                          	 }
		                        	 }

		                          	 return "-";
		                          }
		                       },
			                   {
			                	   "defaultContent": "<button class=\"btn btn-primary\">Seleccionar</button>"
		                       }  
		                ],
		         "bLengthChange": false        
		    } );
		    
			$('#users tbody').on( 'click', 'button', function (e) {
				var table = $('#users').DataTable();
				var data = table.row( $(this).parents('tr') ).data();
				$('#selectedUser').text(data.name + " " + data.lastname);
				$('#selectedUser').addClass("overwriteSelectedUser");
				$('#userId').text(data.id);
				$('#userId').val(data.id);
				$('#userName').val(data.name);
				$('#userLastName').val(data.lastname);
				$('#userModal').modal('toggle');
				$('#hideableFormPart').attr("hidden", false)
				if(data.avatar != null) {
					var src = "data:image/png;base64," + data.avatar;
					$("#userAvatar").attr("src",src);
				} else {
					$("#userAvatar").attr("src","/d2d/images/ic_avatar.png");
				}
				$('#previewName').text(" " + data.name + " " + data.lastname);
			} );
			
			if($('#permanentRadio').is(":checked")) {
				checkOfferTypeRadio('Permanent');
			}	else {
				checkOfferTypeRadio('Temporal');
			}
			
			
			// Esto se encarga de hacer visible T&iacute;tulo y Subt&iacute;tulo en caso de que la oferta sea permanente.
			 $('#permanentRadio').change(function() {
				 checkOfferTypeRadio('Permanent');
			  });
			
			 $('#temporalRadio').change(function() {
				 checkOfferTypeRadio('Temporal');
			  });
			
			
			//SET OCCUPATION
			$('#occupationsSelect').selectpicker('val', ${boJobDTO.occupationId});
		
			$('#occupationsSelect').on('changed.bs.select', function (e) {
				loadSpecialties();
			});
			
			<c:if test="${specialtyList != null}">
			
				$('#specialtiesSelect').selectpicker('val', ${boJobDTO.specialtyId});
				$("#specialtiesSelect").on('changed.bs.select', function (e) {
					var occupation_name = $("#occupationsSelect option:selected").text();
					var specialty_name = $("#specialtiesSelect option:selected").text();
					if(specialty_name != '' && specialty_name != 'Seleccione una Especialidad') {
						$('#previewInterest').text(occupation_name + ', ' + specialty_name)
					}
					loadTasks();
				});
			</c:if>
			
			<c:if test="${taskList != null}">
				$('#tasksSelect').selectpicker('val', ${boJobDTO.taskId});
			</c:if>
			
			$("#tasksSelect").on('changed.bs.select', function (e) {
				
				var task_name = $("#tasksSelect option:selected").text();
				if(task_name != '' && task_name != 'Seleccione una Especialidad') {
					$('#previewTask').text('Para trabajos de ' + task_name)
				}
				
			});	
			
			
			
			// Zonas
			$( "#clearButton" ).click(function() {
		    	$('#zoneId').val("");
		    	$('#zoneName').val("");
		    	$('#zoneLevel').val("");
		    	$('#zones').val("");
		    	$("#zones").prop('disabled', false);
			});
			
			<c:if test="${boJobDTO.geoDto.id != 0}">
				$("#zones").prop('disabled', true);
			</c:if>
			
			
			
			// Funci&oacute;nes encargadas de actualizar el preview Mobile
			
			$( "#companyScreenName" ).keyup(function() {
				  $('#previewCompanyScreen').text($(this).val());
			});
			
			$( "#offerText" ).keyup(function() {
				  $('#previewOfferText').text($(this).val());
			});
			
			
			$( "#offerHour").keyup(function() {
				hourBlur(this.text)
			});
			
			$( "#offerTitle" ).keyup(function() {
				  $('#previewTitle').text("Titulo: " + $(this).val());
			});
			
			$( "#offerSubtitle" ).keyup(function() {
				  $('#previewSubtitle').text("Subtitulo: " + $(this).val());
			});
			
			// La funci&oacute;n con el autoComplete de Georeferencia
			$("#zones").autocomplete({
			    source: function (request, response) {
			        $.ajax({
			            url: "countries/",
			            data: { query: request.term },
			            success: function (data) {
			                var transformed = $.map(data, function (el) {
			                    return {
			                        label: el.name,
			                        id: el.id,
			                        level : el.level 
			                    };
			                });
			                response(transformed);
			            },
			            error: function () {
			                response([]);
			            }
			        });
			    },
			    select: function (suggestion,ui) {
			    	$('#zoneId').val(ui.item.id);
			    	$('#zoneName').val(ui.item.label);
			    	$('#zoneLevel').val(ui.item.level);
			    	$("#zones").prop('disabled', true);
			    	$('#previewZone').text('Zona: ' + ui.item.label)
			   	},
			   	minLength: 3
			});

			//Se carga el preview
			loadPreviewEdit();
			
		} );
		
		
		function loadTasks() {
			specialty_id = $("#specialtiesSelect").val();
			$.ajax({
		        url: 'BoOffers/tasks/' + specialty_id,
		        type: 'GET',
		        success: function(data) {
		        	$("#tasksSelectBox").html(data);
		        }
		    });
		}
		
		
		function loadGeosInput() {
			$("#geoLevelIdsBox").empty();
			arrayGeosIds = $("#geosSelect").val();
			if(arrayGeosIds != null) {
				for (var i = 0; i < arrayGeosIds.length; i++) {
					id = arrayGeosIds[i]
					$("#geoLevelIdsBox").append('<input type="hidden" name="geoLevels2" value="' + id + '">');
				}
			}
		}
		
		function loadSpecialties() {
			occupation_id = $("#occupationsSelect").val();
			var occupation_name = $("#occupationsSelect option:selected").text();
			$('#previewInterest').text(occupation_name);
			$.ajax({
		        url: 'BoOffers/specialties/' + occupation_id,
		        type: 'GET',
		        success: function(data) {
		        	$("#specialtiesSelectBox").html(data);
		        }
		    });
		}
		
		
		// Funci&oacute;n que muestra los campos Fecha o T&iacute;tulo/Subt&iacute;tulo dependiendo si se selecci&oacute;na Permanente o Temporal
		function checkOfferTypeRadio(value) {
		      if(value == 'Permanent') {
		            $('#titlesBox').attr("hidden", false)
		            $('#previewTitlesBox').attr("hidden", false)
		            $('#dateForOffer').attr("hidden", true)
		            $('#previewDateBox').attr("hidden", true)
		            $('#hourForOffer').attr("hidden", true)
					$('#mobileOfferType').text("Publicar oferta permanente")
		        } else {
		        	$('#titlesBox').attr("hidden", true)
		        	$('#previewTitlesBox').attr("hidden", true)
		        	$('#dateForOffer').attr("hidden", false)
		        	$('#previewDateBox').attr("hidden", false)
		        	$('#hourForOffer').attr("hidden", false)
		        	$('#mobileOfferType').text("Publicar oferta temporal")
		        }
		}
		
		function hourBlur(hour) {
			var hour = $('#offerHour');
				
			var res = hour.val().split(":");
			
			if($.isNumeric(res[0]) &&  $.isNumeric(res[1]))
			{
			   $('#previewHour').text('Hora: ' + hour.val() + 'hs')
			}
			
		}
		
		
		function loadPreviewEdit() {
			<c:if test="${not empty boJobDTO.geoDto.name}">
				$('#previewZone').text('Zona: ' + "${boJobDTO.geoDto.name}");
			</c:if>
			
			<c:if test="${not empty boJobDTO.offerHour}">
				var hour = '${boJobDTO.offerHour}'
				hour = hour.toString();
				$('#previewHour').text('Hora: ' + hour.slice(0, 2) + ":" + hour.slice(2, 4) + " " + 'hs');
			</c:if>
			
				
			<c:if test="${not empty boJobDTO.offerDateForView}">
				$('#previewDate').text('Fecha:' + " " + $('#offerDateForView').val())
			</c:if>
			<c:if test="${not empty boJobDTO.taskId}">
			
				var occupation = $("#occupationsSelect  option:selected").text();
				var specialty = $('#specialtiesSelect  option:selected').text();
				var task = $('#tasksSelect  option:selected').text();
			
				if(specialty == ''){
					$('#previewInterest').text(occupation)	
				} else {
					$('#previewInterest').text(occupation + ', ' + specialty)
				}
				
				$('#previewTask').text('Para trabajos de ' + task)
			</c:if>
			<c:if test="${not empty boJobDTO.offerText}">
				$('#previewOfferText').text('${boJobDTO.offerText}')
			</c:if>
				
			
			<c:if test="${not empty boJobDTO.name}">
					$('#selectedUser').text("${boJobDTO.name}" + " " + "${boJobDTO.lastName}");
			</c:if>
			
			<c:if test="${not empty boJobDTO.name}">
				$('#previewName').text('${boJobDTO.name}' + " " + '${boJobDTO.lastName}');
			</c:if>
			
			<c:if test="${not empty boJobDTO.companyScreenName}">
				$('#previewCompanyScreen').text('${boJobDTO.companyScreenName}');
			</c:if>
			
			<c:if test="${not empty boJobDTO.title}">
				$('#previewTitle').text("T&iacute;tulo: " + '${boJobDTO.title}');
			</c:if>
			
			<c:if test="${not empty boJobDTO.subtitle}">
				$('#previewSubtitle').text("Subt&iacute;tulo: " + '${boJobDTO.subtitle}');
			</c:if>
			
		}
			
		
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>

