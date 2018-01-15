<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<tiles:insertDefinition name="d2d.dashboard">

	<tiles:putAttribute name="title">
		ABM de categorias
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<section class="content">
			<div class="row">
				<div class="col-md-12">
					<div class="nav-tabs-custom">
			            <ul class="nav nav-tabs">
			              <li class="active"><a href="#tab_1" data-toggle="tab">Listado de categorias</a></li>
			            </ul>
			            <div class="tab-content">
							<div class="tab-pane active" id="tab_1">
								<div class="col-md-12" style="margin-bottom:50px;">
									<div class="col-md-4">
										<a type="submit" class="btn btn-default col-md-12" data-toggle="modal" data-target="#occupationModal" >Crear nueva Ocupacion</a>
									</div>
									<div class="col-md-4">
										<a type="submit" class="btn btn-warning col-md-12" data-toggle="modal" data-target="#specialtyModal" >Crear nueva Especialidad</a>
									</div>
									<div class="col-md-4">
										<a type="submit" class="btn btn-info col-md-12"    data-toggle="modal" data-target="#taskModal" >Crear nueva Tarea</a>
									</div>
								</div>
								<hr>
								<table id="categories-table" class="display" cellspacing="0" width="100%">
									<thead>
										<tr>
											<th>Ocupacion</th>
											<th>Especialidad</th>
											<th>Tarea</th>
											<th>OcupacionId</th>
											<th>EspecialidadId</th>
											<th>TareaId</th>
										</tr>
									</thead>
									<tfoot>
										<tr>
											<th>Ocupacion</th>
											<th>Especialidad</th>
											<th>Tarea</th>
											<th>OcupacionId</th>
											<th>EspecialidadId</th>
											<th>TareaId</th>
										</tr>
									</tfoot>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>


		<!-- Modal de ocupacion -->
		<div id="occupationModal" class="modal fade bd-example-modal-lg"
			role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Crear ocupacion</h4>
					</div>
					<div id="bodyModalBootstrap" class="modal-body">
					<div class="row" >
						<div id="occupationSuccess" class="msg" style="display: none;" >sfas</div>
						<div id="occupationError" class="error"  style="display: none;">sfas</div>
					</div>
						<div class="row">
							<label class="col-md-4">Nombre:</label>
							<input id="occupationTxt" class="col-md-4">
							<button onclick="addOccupation()">Agregar</button>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button>
					</div>
				</div>
			</div>
		</div>
		<!-- Modal de Especialidad -->
		<div id="specialtyModal" class="modal fade bd-example-modal-lg"
			role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Crear Especialidad</h4>
					</div>
					<div id="bodyModalBootstrap" class="modal-body">
					<div class="row" >
						<div id="specialtySuccess" class="msg" style="display: none;" ></div>
						<div id="specialtyError" class="error"  style="display: none;"></div>
					</div>
						<div class="row">
							<label class="col-md-4">Ocupacion:</label>
							<input id="espOccupationName" type="text" class="typeahead col-md-4" data-provide="typeahead" >
							<input id="espOccupationId" class="col-md-2" style="display: none;">
							<button id="espCleanButton">Borrar</button>
						</div>
						<div class="row">
							<label class="col-md-4">Especialidad:</label>
							<input id="espSpecialtyName" class="col-md-4">
							<button onclick="addSpecialty()">Agregar</button>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button>
					</div>
				</div>
			</div>
		</div>
		
		<!-- Modal de Tareas -->
		<div id="taskModal" class="modal fade bd-example-modal-lg"
			role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Crear Tarea</h4>
					</div>
					<div id="bodyModalBootstrap" class="modal-body">
					<div class="row" >
						<div id="taskSuccess" class="msg" style="display: none;"></div>
						<div id="taskError" class="error"  style="display: none;"></div>
					</div>
						<div class="row">
							<label class="col-md-4">Ocupacion:</label>
							<input id="taskOccupationName" type="text" class="typeahead col-md-4" data-provide="typeahead" >
							<input id="taskOccupationId" class="col-md-2" style="display: none;">
							<button id="TaskOccuCleanButton">Borrar</button>
						</div>
						<div class="row">
							<label class="col-md-4">Especialidad:</label>
							<input id="taskSpecialtyName" type="text" class="typeahead col-md-4" data-provide="typeahead" >
							<input id="taskSpecialtyId" class="col-md-2" style="display: none;">
							<button id="taskEspCleanButton">Borrar</button>
						</div>
						<div class="row">
							<label class="col-md-4">Nombre:</label>
							<input id="taskTaskName" class="col-md-4">
							<button onclick="addTask()">Agregar</button>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button>
					</div>
				</div>
			</div>
		</div>

		<script>

			$(document).ready(function() {
				$('#categories-table').DataTable({
					dom : 'Bfrtip',
					buttons : [ 'excelHtml5', 'csvHtml5', 'copyHtml5' ],
					"scrollX" : true,
					"iDisplayLength" : 20,
					"start" : 0,
					"processing" : true, // for show progress bar
					"serverSide" : true, // for process server side
					"ajax" : {
						"url" : '/d2d/admin/BoCategory/getCategories',
						"type" : "GET",
						"datatype" : "json"
					},
					"language" : {
						"search" : "Filtrar:",
						"info" : "Página _PAGE_ de _PAGES_",
						"paginate" : {
							"previous" : "Previa",
							"next" : "Siguiente",
						}
					},
					"columns" : [ {
						"data" : "occupationName"
					}, {
						"data" : "specialtyName"
					}, {
						"data" : "taskName"
					}, {
						"data" : "occupationId"
					}, {
						"data" : "specialtyId"
					}, {
						"data" : "taskId"
					} ],
					"columnDefs" : [ {
						"targets" : [ 3, 4, 5 ],
						"visible" : false,
						"searchable" : false
					}, ],
					"bLengthChange" : false
				});
				
				var table = $('#categories-table').DataTable();
				
			//ACA TERMINA LA CONFIGURACION DE LA TABLA				
			
			
			
			// Comienzo autocomplete modal especialidades
// 			$("#espOccupationName").autocomplete({
// 			    source: function (request, response) {
// 			        $.ajax({
// 			            url: "/d2d/admin/BoCategory/occupations",
// 			            data: { query: request.term },
// 			            success: function (data) {
// 			                var transformed = $.map(data, function (el) {
// 			                    return {
// 			                        label: el.name,
// 			                        id: el.id,
// // 			                        level : el.level 
// 			                    };
// 			                });
// 			                response(transformed);
// 			            },
// 			            error: function () {
// 			                response([]);
// 			            }
// 			        });
// 			    },
// 			    select: function (suggestion,ui) {
// 			    	$('#espOccupationId').val(ui.item.id);
// 			    	$('#espOccupationName').val(ui.item.label);
// // 			    	$('#zoneLevel').val(ui.item.level);
// // 			    	$("#zones").prop('disabled', true);
// // 			    	$('#previewZone').text('Zona: ' + ui.item.label)
// 			   	},
// 			   	minLength: 3
// 			});
			
			// Inicio
				$("#espOccupationName").val("");
			    $("#espOccupationId").val("");
			    $("#espOccupationName").prop('disabled', false);
			    $("#espSpecialtyName").val("");
			    $("#taskOccupationName").val("");
			    $("#taskOccupationId").val("");
			    $("#taskSpecialtyName").val("");
			    $("#taskSpecialtyId").val("");
			    $("#taskTaskName").val("");
			    $("#taskOccupationName").prop('disabled', false);
			    $("#taskEspCleanButton").prop('disabled', false);
			    $("#taskSpecialtyName").prop('disabled', false);
			    
			
			    refreshOccupations();

				
			//Listener que se ejecuta al cerrar el modal ya sea por medio del boton cerrar como con la cruz
			$('#occupationModal').on('hidden.bs.modal', function () {
				hideMessages();
			    table.ajax.reload();
				$("#occupationTxt").val("");
			})
			
			//Listener que se ejecuta al cerrar el modal ya sea por medio del boton cerrar como con la cruz
			$('#specialtyModal').on('hidden.bs.modal', function () {
				specialtyHideMessages();
			    table.ajax.reload();
				$("#espOccupationName").val("");
			    $("#espOccupationId").val("");
			    $("#espOccupationName").prop('disabled', false);
			    $("#espSpecialtyName").val("");
			})
			
						//Listener que se ejecuta al cerrar el modal ya sea por medio del boton cerrar como con la cruz
			$('#taskModal').on('hidden.bs.modal', function () {
				taskHideMessages();
			    table.ajax.reload();
			})
			
			
			$("#espCleanButton").click(function() {
			    $("#espOccupationName").val("");
			    $("#espOccupationId").val("");
			    $("#espOccupationName").prop('disabled', false);
			});
			
			$("#TaskOccuCleanButton").click(function() {
			    $("#taskOccupationName").val("");
			    $("#taskOccupationId").val("");
			    $("#taskSpecialtyName").val("");
			    $("#taskSpecialtyId").val("");
			    $("#taskOccupationName").prop('disabled', false);
			    $("#taskEspCleanButton").prop('disabled', false);
			    $("#taskSpecialtyName").prop('disabled', false);
			    $('#taskSpecialtyName').typeahead('destroy');
			});
			
			$("#taskEspCleanButton").click(function() {
			    $("#taskSpecialtyName").val("");
			    $("#taskSpecialtyId").val("");
			    $("#taskSpecialtyName").prop('disabled', false);
			});
			
			

			});
			
			function addOccupation(){
				
				var newOccupation = $("#occupationTxt").val();
				hideMessages();
				
				$("#occupationTxt").val("");
				
				$.ajax({ 
				    type: 'GET', 
				    url: '/d2d/admin/BoCategory/saveOccupation', 
				    data: { occupationName: newOccupation }, 
				    dataType: 'json',
				    success: function (data) { 
						if(data.status == 200){
							refreshOccupations();
							showSuccess(data.data);
						} else if(data.status == 201){
							showError(data.data);
						} else {
							showError();
					    }
				    },
				    error: function(XMLHttpRequest, textStatus, errorThrown) { 
				    	showError();
	                }   
				    
				});
				
			}
			
			
			function addSpecialty(){
				specialtyHideMessages();
				var occupationId = $("#espOccupationId").val();
				var specialtyName = $("#espSpecialtyName").val();
				
				if(occupationId == "" || specialtyName == "") {
					specialtyShowError("Los campos no pueden estar vacios.");
					return;
				}
				
				hideMessages();
				
				$.ajax({ 
				    type: 'GET', 
				    url: '/d2d/admin/BoCategory/saveSpecialty', 
				    data: { espOccupationId: occupationId,
				    		newSpecialtyName: specialtyName}, 
				    dataType: 'json',
				    success: function (data) { 
						if(data.status == 200){
							$("#espOccupationName").val("");
						    $("#espOccupationId").val("");
						    $("#espOccupationName").prop('disabled', false);
						    $("#espSpecialtyName").val("");
						    specialtyShowSuccess(data.data);
						} else {
							specialtyShowError();
					    }
				    },
				    error: function(XMLHttpRequest, textStatus, errorThrown) { 
				    	specialtyShowError();
	                }   
				    
				});
				
			}
			
			
			function addTask(){
				taskHideMessages();
				var taskTaskName = $("#taskTaskName").val();
				var taskSpecialtyId = $("#taskSpecialtyId").val();
				var taskOccupationId = $("#taskOccupationId").val();
				
				if(taskOccupationId == "" || taskSpecialtyId == "" || taskTaskName == "") {
					taskShowError("Los campos no pueden estar vacios.");
					return;
				}
				
				$.ajax({ 
				    type: 'GET', 
				    url: '/d2d/admin/BoCategory/saveTask', 
				    data: { taskName: taskTaskName,
				    		specialtyId: taskSpecialtyId}, 
				    dataType: 'json',
				    success: function (data) { 
						if(data.status == 200){
							$("#taskOccupationName").val("");
						    $("#taskOccupationId").val("");
						    $("#taskSpecialtyName").val("");
						    $("#taskSpecialtyId").val("");
						    $("#taskTaskName").val("");
						    $("#taskOccupationName").prop('disabled', false);
						    $("#taskSpecialtyName").prop('disabled', false);

						    taskShowSuccess(data.data);
						} else {
							taskShowError();
					    }
				    },
				    error: function(XMLHttpRequest, textStatus, errorThrown) { 
				    	taskShowError();
	                }   
				    
				});
				
			}
			
			//Funciones para ocupacion
			function showSuccess(message) {
				$("#occupationSuccess").show();
				$("#occupationSuccess").text(message);
			}
			
			function showError() {
				$("#occupationError").show();
				$("#occupationError").text("Ha ocurrido un error, por favor intente mas tarde.");
			}
			
			function showError(message) {
				$("#occupationError").show();
				$("#occupationError").text(message);
			}
			
			function hideMessages() {
				$("#occupationError").hide();
				$("#occupationSuccess").hide();
			}
			
			//Funciones para especialidad
			function specialtyShowSuccess(message) {
				$("#specialtySuccess").show();
				$("#specialtySuccess").text(message);
			}
			
			function specialtyShowError() {
				$("#specialtyError").show();
				$("#specialtyError").text("Ha ocurrido un error, por favor intente mas tarde.");
			}
			
			function specialtyShowError(message) {
				$("#specialtyError").show();
				$("#specialtyError").text(message);
			}
			
			function specialtyHideMessages() {
				$("#specialtyError").hide();
				$("#specialtySuccess").hide();
			}
			
			//Funciones para tarea
			function taskShowSuccess(message) {
				$("#taskSuccess").show();
				$("#taskSuccess").text(message);
			}
			
			function taskShowError() {
				$("#taskError").show();
				$("#taskError").text("Ha ocurrido un error, por favor intente mas tarde.");
			}
			
			function taskShowError(message) {
				$("#taskError").show();
				$("#taskError").text(message);
			}
			
			function taskHideMessages() {
				$("#taskError").hide();
				$("#taskSuccess").hide();
			}

			
			function refreshOccupations(){
			    
				$('.typeahead').typeahead('destroy');
				
				$.get("/d2d/admin/BoCategory/occupations", function(data){
				  $("#espOccupationName").typeahead({
					  source:data,
					  afterSelect: function(item) {
						    $("#espOccupationName").val(item.name);
						    $("#espOccupationId").val(item.id);
						    $("#espOccupationName").prop('disabled', true);
						}
				  });
				},'json');
				// Fin autocomplete modal especialidades
				
				$.get("/d2d/admin/BoCategory/occupations", function(data){
				  $("#taskOccupationName").typeahead({
					  source:data,
					  afterSelect: function(item) {
						    $("#taskOccupationName").val(item.name);
						    $("#taskOccupationId").val(item.id);
						    $("#taskOccupationName").prop('disabled', true);
						    refreshSpecialty(item.id);
						}
				  });
				},'json');
				// Fin autocomplete modal Tareas Ocupaciones

			}
			
			function refreshSpecialty(id){
				$('#taskSpecialtyName').typeahead('destroy');
				
				$.get("/d2d/admin/BoCategory/specialties/" + id, function(data){
					
					if(data.length == 1 && data[0].name == ""){
						$("#taskSpecialtyName").prop('disabled', true);
						$("#taskSpecialtyId").val(data[0].id);
						$("#taskEspCleanButton").prop('disabled', true);
					} else {
						
						$("#taskSpecialtyName").typeahead({
							  source:data,
							  afterSelect: function(item) {
								    $("#taskSpecialtyName").val(item.name);
								    $("#taskSpecialtyId").val(item.id);
								    $("#taskSpecialtyName").prop('disabled', true);
								}
						  });
						}
						// Fin autocomplete modal Tareas Ocupaciones
					
					},'json');
					

			}
			
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>

