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

							<form:form method="POST" modelAttribute="jobForm"
							autocomplete="off"
							action="${pageContext.request.contextPath}/admin/BoOffers/save?${_csrf.parameterName}=${_csrf.token}"
							enctype="multipart/form-data">
									<div class="box-body">
										<div class="form-group">
											<label id="selectedUser" for="email" class="col-sm-10 control-label">Usuario al que se le cargará la oferta: [??????????]</label>
											<form:input path="userId" id="userId" hidden="true"></form:input>
											<div class="col-sm-2">
 												<button type="button" class="btn btn-default pull-right" data-toggle="modal" data-target="#userModal">Seleccionar Usuario</button>
											</div>
										</div>
										<div class="form-group">
											<label for="name" ">Tipo de oferta</label>
												<div >
													<form:radiobutton id="temporalRadio" path="permanent"  name="permanent" value="false" element="span class='radio'"></form:radiobutton><label>Temporal</label>
												</div>
												<div>
													<form:radiobutton id="permanentRadio"  path="permanent" name="permanent" value="true" element="span class='radio'"></form:radiobutton><label>Permanente</label>
												</div>
										</div>
									<!-- Inicio del bloque de ocupaciones -->
										<div class="row">
											<div class="col-md-6">
												<div class="form-group form-group-job-offer-combos">
													<label for="name" class="control-label">Filtrar por Ocupaci&oacute;n principal</label>
																			
													<select id="occupationsSelect" class="selectpicker"  title="Seleccione una ocupacion" name="occupationId" required oninvalid="this.setCustomValidity('Debes seleccionar una ocupacion')">
														<c:forEach var="occupation" items="${occupationList}">
															<option value="<c:out value="${occupation.id}"/>"><c:out value="${occupation.name}"/></option>
														</c:forEach>
													</select>
												</div>
												<div id="filterBySpecialtiesBox" class="form-group form-group-job-offer-combos <c:if test="${specialtyList == null}">hide</c:if>">
													<label for="name" class="control-label">Filtrar por Especialidad</label>
													
													<div id="specialtiesSelectBox">
														<select id="specialtiesSelect" class="selectpicker" title="" name="specialtyId">
															<c:forEach var="specialty" items="${specialtyList}">
																<option value="<c:out value="${specialty.id}"/>"><c:out value="${specialty.name}"/></option>
															</c:forEach>
														</select>
													</div>					
												</div>
												<div id="filterByTasksBox" class="form-group form-group-job-offer-combos <c:if test="${taskList == null}">hide</c:if>">
													<label for="name" class="control-label">Filtrar por Tarea</label>
													
													<div id="tasksSelectBox">					
														<select id="tasksSelect" class="selectpicker" title="" name="taskId">
															<c:forEach var="task" items="${taskList}">
																<option value="<c:out value="${task.id}"/>"><c:out value="${task.name}"/></option>
															</c:forEach>
														</select>
													</div>
												</div>
											</div>
											<div class="col-md-6">
												<div class="form-group">
													<label for="zones" class="control-label">Seleccionar Ubicacion: </label>
													<input id="zones" required oninvalid="this.setCustomValidity('Este campo no puede quedar vacio.')" oninput="setCustomValidity('')">
													<button id="clearButton" type="button">Vaciar</button>
												<form:hidden id="zoneName" path="geoDto.name" />
												<form:hidden id="zoneId" path="geoDto.id" />
												<form:hidden id="zoneLevel" path="geoDto.level" />
												</div>
											</div>
										</div>
										<div class="form-group">
											<div class="col-sm-10">
												<div class="form-group">
													<label for="name" ">Tipo de institucion</label>
														<div >
<!-- 															<input type="radio" name="institutionType" value="private"><label>Privada</label> -->
															<form:radiobutton path="privateInstitution" value="true" element="span class='radio'"></form:radiobutton><label>Privada</label>
														</div>
														<div>
<!-- 															<input type="radio" name="institutionType" value="public" ><label>Publica</label> -->
																<form:radiobutton path="privateInstitution" value="false" element="span class='radio'"></form:radiobutton><label>Publica</label>
														</div>
												</div>
											</div>
										</div>
										<div class="form-group">
											<label>Nombre del profesional o empresa(Opcional): </label>
											<form:input id="companyScreenName" path="companyScreenName"/>
										</div>
										<div id="titlesBox" hidden="true">
											<div class="col-sm-4">
												<label>Ingrese un Titulo <input></label>
											</div>
											<div class="col-sm-12">
												<label>Ingrese un Subtitulo <input></label>
											</div>
										</div>
									<!--Fecha de Publicacion y de Expiracion -->
										<div id="publishDate" class="form-group">
											<div class=col-sm-2>
												<label for="content" class="control-label">Fecha</label>
											</div>
											<div class=col-sm-10>
												<div class="input-group date" id="publishDatePicker">
													<div class="input-group-addon">
														<i class="fa fa-calendar"></i>
													</div>
													<input type="text" class="form-control pull-right"
														id="offerDateForView" name="offerDateForView" required oninvalid="this.setCustomValidity('Este campo no puede quedar vacio.')" oninput="setCustomValidity('')"
														value="${noteForm.publishingDateForView}"
														data-inputmask="'alias': 'dd/mm/yyyy'" data-mask>
												</div>
											</div>
										</div>

										<div id="expirationDate" class="form-group">
											<div class=col-sm-2>
												<label for="content" class="control-label">Hora</label>
											</div>
											<div class=col-sm-10>
												<div class="input-group date">
													<div class="input-group-addon">
														<i class="fa fa-calendar"></i>
													</div>
													<input type="text" name="offerHour" id="offerHour" required oninvalid="this.setCustomValidity('Este campo no puede quedar vacio.')" oninput="setCustomValidity('')">
												</div>
											</div>
										</div>
										<!--Fecha de Publicacion y de Expiracion FIN -->
										
										<div class="col-sm-4">
											<form:textarea path="offerText" rows="20" cols="155" required="true" oninvalid="this.setCustomValidity('Este campo no puede quedar vacio.')" oninput="setCustomValidity('')"></form:textarea>
										</div>
									</div>
									
									<div class="box-footer">
										<button type="submit" class="btn btn-info pull-right">Guardar y publicar</button>
									</div>
									</form:form>
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
		
		
		<!-- Modal de usuarios -->
		<div id="userModal" class="modal fade bd-example-modal-lg" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
		  <div class="modal-dialog modal-lg">
		
		    <!-- Modal content-->
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal">&times;</button>
		        <h4 class="modal-title">Modal Header</h4>
		      </div>
		      <div class="modal-body">
			  	<section class="content">
				 <div class="row">
			        <div class="col-md-12">
			          <!-- Custom Tabs -->
			          <div class="nav-tabs-custom">
			            <ul class="nav nav-tabs">
			              <li  class="active"><a href="#tab_1" >Usuarios Publicos</a></li>
			            </ul>
			            <div class="tab-content">
			             
			              <div class="tab-pane active" id="tab_1" style="margin-top:15px">
			              
			              			<table id="users" class="display" cellspacing="0" width="100%">
								        <thead>
								            <tr>
								                <th>ID</th>
								                <th>Número Tel</th>
								                <th>Nombre</th>
								                <th>Apellido</th>
								                <th>Email</th>
								                <th>Estado</th>
								                <th>Tipo Suscripción</th>
								                <th>Estado Suscripción</th>
								                <th>Acción</th>
								            </tr>
								        </thead>
								        <tfoot>
								            <tr>
								                <th>ID</th>
								                <th>Número Tel</th>
								                <th>Nombre</th>
								                <th>Apellido</th>
								                <th>Email</th>
								                <th>Estado</th>
								                <th>Tipo Suscripción</th>
								                <th>Estado Suscripción</th>
								                <th>Acción</th>
								            </tr>
								        </tfoot>
								    </table>
								
			              </div>
			             <!-- /.tab-pane -->
			            </div>
			            <!-- /.tab-content -->
			          </div>
			          <!-- nav-tabs-custom -->
			        </div>
		        </div>
			</section>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		      </div>
		    </div>
		  </div>
		</div>
		<script>
		
		$('#offerDateForView').datepicker({
			autoclose: true,
			endDate: 'now',
			format: "dd-mm-yyyy"
		});
// 		$('#expirationDateForView').datepicker({
// 			autoclose: true,
// 			format: "HH:mm:ss"
// 		});
		
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
		            "info": "Página _PAGE_ de _PAGES_",
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
			                	   "defaultContent": "<button>Seleccionar</button>"
		                       }  
		                ],
		         "bLengthChange": false        
		    } );
		    
			$('#users tbody').on( 'click', 'button', function (e) {
				
				
// 				console.log("val anterior: " + $('#userId').val());
				
				var table = $('#users').DataTable();
				var data = table.row( $(this).parents('tr') ).data();
				$('#selectedUser').text("Usuario al que se le cargará la oferta: " + data.name + " " + data.lastname);
				$('#userId').text(data.id);
				$('#userId').val(data.id);
				$('#userModal').modal('toggle');
				$('#previewName').text(data.name + " " + data.lastname);
				
				
			} );
			
			
			
			// Esto se encarga de hacer visible Titulo y Subtitulo en caso de que la oferta sea permanente.
			 $('#permanentRadio').change(function() {
				 console.log(this.value)
				 checkOfferTypeRadio('Permanent');
			  });
			
			 $('#temporalRadio').change(function() {
				 console.log(this.value)
				 checkOfferTypeRadio('Temporal');
			  });
			
			  
			
			
			//SET OCCUPATION
			$('#occupationsSelect').selectpicker('val', ${filterForm.occupationId});
		
			$('#occupationsSelect').on('changed.bs.select', function (e) {
				loadSpecialties();
			});
			
			<c:if test="${specialtyList != null}">
				$('#specialtiesSelect').selectpicker('val', ${filterForm.specialtyId});
				$("#specialtiesSelect").on('changed.bs.select', function (e) {
					loadTasks();
				});
			</c:if>
			
			<c:if test="${taskList != null}">
				$('#tasksSelect').selectpicker('val', ${filterForm.taskId});
			</c:if>
			
			//Set GEOS
			$('#geosSelect').selectpicker('val', ${filterForm.geoLevels2});
			
			$('#geosSelect').on('changed.bs.select', function (e) {
				loadGeosInput();
			});
			
			arrayGeosIds = [];
			
			loadGeosInput();
			
			$( "#clearButton" ).click(function() {
		    	$('#zoneId').val("");
		    	$('#zoneName').val("");
		    	$('#zoneLevel').val("");
		    	$('#zones').val("");
		    	$("#zones").prop('disabled', false);
			});
			
			
			// Funciones encargadas de actualizar el preview Mobile
			
			$( "#companyScreenName" ).keyup(function() {
				console.log($(this).val())
				  $('#previewCompanyScreen').text($(this).val());
			});
			
			// La funcion con el autoComplete de Georeferencia
			$("#zones").autocomplete({
			    source: function (request, response) {
			        $.ajax({
			            url: "BoOffers/countries/",
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
			   	},
			   	minLength: 3
			});

			
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
			$.ajax({
		        url: 'BoOffers/specialties/' + occupation_id,
		        type: 'GET',
		        success: function(data) {
		        	$("#specialtiesSelectBox").html(data);
		        }
		    });
		}
		
		
		// Funcion que muestra los campos Fecha o Titulo/Subtitulo dependiendo si se selecciona Permanente o Temporal
		function checkOfferTypeRadio(value) {
		      if(value == 'Permanent') {
		            $('#titlesBox').attr("hidden", false)
		            $('#publishDate').attr("hidden", true)
		            $('#expirationDate').attr("hidden", true)
					$('#mobileOfferType').text("Publicar oferta permanente")
		        } else {
		        	$('#titlesBox').attr("hidden", true)
		        	$('#publishDate').attr("hidden", false)
		        	$('#expirationDate').attr("hidden", false)
		        	$('#mobileOfferType').text("Publicar oferta temporal")
		        }
		}
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>

