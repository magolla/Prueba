<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<tiles:insertDefinition name="d2d.dashboard">

	<tiles:putAttribute name="title">
		ABM de categorías
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<section class="content">
			<div class="row">
				<div class="col-md-12">
					<div class="nav-tabs-custom">
			            <ul class="nav nav-tabs">
			              <li class="active"><a href="#tab_1" data-toggle="tab">Listado de categorías</a></li>
			            </ul>
			            <div class="tab-content">
							<div class="tab-pane active" id="tab_1">
								<div class="col-md-12" style="margin-bottom:50px;">
									<div class="col-md-4">
										<a type="submit" class="btn btn-default col-md-12" data-toggle="modal" data-target="#occupationModal" >Crear nueva Ocupación</a>
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
											<th>Ocupación</th>
											<th>Especialidad</th>
											<th>Tarea</th>
											<th>OcupacionId</th>
											<th>EspecialidadId</th>
											<th>TareaId</th>
											<th>Editar Ocupación</th>
											<th>Editar Especialidad</th>
											<th>Editar Tarea</th>
										</tr>
									</thead>
									<tfoot>
										<tr>
											<th>Ocupación</th>
											<th>Especialidad</th>
											<th>Tarea</th>
											<th>OcupacionId</th>
											<th>EspecialidadId</th>
											<th>TareaId</th>
											<th>Editar Ocupación</th>
											<th>Editar Especialidad</th>
											<th>Editar Tarea</th>
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
						<h4 class="modal-title">Crear ocupación</h4>
					</div>
					<div id="bodyModalBootstrap" class="modal-body">
						<div class="row" >
							<div id="occupationSuccess" class="msg" style="display: none;" >error</div>
							<div id="occupationError" class="error"  style="display: none;">error</div>
						</div>
						
						
						<div class="form-group row">
							<label for="occupationTxt" class="col-2 col-form-label">Nombre</label>
							<div class="col-10">
								<input class="form-control" id="occupationTxt" placeholder="Ingresá el nombre, ejemplo: Médico">
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
						<button onclick="addOccupation()" class="btn btn-success">Agregar</button>
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
						<div class="form-group row">
							<label for="espOccupationName" class="col-2 col-form-label">Seleccionar ocupación</label>
							<div class="col-10">
								<div class="input-group">
									<input id="espOccupationName" type="text" class="typeahead form-control" placeholder="Buscar ocupación...">
									<span class="input-group-btn">
										<button id="espCleanButton" class="btn btn-danger" type="button">Limpiar</button>
									</span>
								</div>
							</div>
							<input id="espOccupationId" class="col-md-2" style="display: none;">
						</div>
						<div class="form-group row">
							<label for="espSpecialtyName" class="col-2 col-form-label">Especialidad</label>
							<div class="col-10">
								<input id="espSpecialtyName" class="form-control" placeholder="Ingresá el nombre de la especialidad, ejemplo: Alergista">
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
						<button onclick="addSpecialty()" class="btn btn-success">Agregar</button>
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

						<div class="form-group row">
							<label for="taskOccupationName" class="col-2 col-form-label">Seleccionar ocupación</label>
							<div class="col-10">
 								<div class="input-group">
									<input id="taskOccupationName" type="text" class="typeahead form-control" data-provide="typeahead" placeholder="Buscar ocupación...">
									<span class="input-group-btn"> 
										<button id="TaskOccuCleanButton" class="btn btn-danger" type="button">Limpiar</button>
									</span>
								</div>
							</div>
						</div>
						<input id="taskOccupationId" class="col-md-2" style="display: none;">
						<div class="form-group row">
							<label for="taskSpecialtyName" class="col-2 col-form-label">Seleccionar especialidad</label>
							<div class="col-10">
 								<div class="input-group">
									<input id="taskSpecialtyName" type="text" class="typeahead form-control" data-provide="typeahead" placeholder="Buscar especialidad...">
									<span class="input-group-btn"> 
										<button id="taskEspCleanButton" class="btn btn-danger" type="button">Limpiar</button>
									</span>
								</div>
							</div>
						</div>
						<input id="taskSpecialtyId" class="col-md-2" style="display: none;">
						<div class="form-group row">
							<label for="taskTaskName" class="col-2 col-form-label">Tarea</label>
							<div class="col-10">
								<input id="taskTaskName" class="form-control" placeholder="Ingresá el nombre de la tarea, ejemplo: Guardia">
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
						<button onclick="addTask()" class="btn btn-success">Agregar</button>
					</div>
				</div>
			</div>
		</div>
		<!-- EDITORES -->
		<!-- Modal de edit para ocupacion -->
		<div id="occupationEditModal" class="modal fade bd-example-modal-lg" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Editar ocupación</h4>
					</div>
					<div id="bodyModalBootstrap" class="modal-body">
						<div class="row" >
							<div id="occupationEditSuccess" class="msg" style="display: none;" >error</div>
							<div id="occupationEditError" class="error"  style="display: none;">error</div>
						</div>

						<div class="form-group row">
							<label for="occupationEditName" class="col-2 col-form-label">Nombre</label>
							<div class="col-10">
								<input class="form-control" id="occupationEditName" placeholder="Ingresá el nombre, ejemplo: Médico">
								<input id="occupationEditId" class="col-md-2" style="display: none;">
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
						<button onclick="editOccupation()" class="btn btn-success">Modificar</button>
					</div>
				</div>
			</div>
		</div>
		<!-- Modal de edit para especialidades -->
		<div id="specialtyEditModal" class="modal fade bd-example-modal-lg" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Editar especialidad</h4>
					</div>
					<div id="bodyModalBootstrap" class="modal-body">
						<div class="row" >
							<div id="specialtyEditSuccess" class="msg" style="display: none;" >sfas</div>
							<div id="specialtyEdit	Error" class="error"  style="display: none;">sfas</div>
						</div>
						<div class="form-group row">
							<label for="specialtyEditName" class="col-2 col-form-label">Especialidad</label>
							<div class="col-10">
								<input id="specialtyEditName" class="form-control" placeholder="Ingresá el nombre, ejemplo: Alergista">
							</div>
						</div>
						<input id="specialtyEditId" class="col-md-2" style="display: none;">
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
						<button onclick="editSpecialty()" class="btn btn-success">Modificar</button>
					</div>
				</div>
			</div>
		</div>
		<!-- Modal de edit para task -->
		<div id="taskEditModal" class="modal fade bd-example-modal-lg"
			role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Editar tarea</h4>
					</div>
					<div id="bodyModalBootstrap" class="modal-body">
						<div class="row" >
							<div id="taskEditSuccess" class="msg" style="display: none;" >sfas</div>
							<div id="taskEditError" class="error"  style="display: none;">sfas</div>
						</div>

						<div class="form-group row">
							<label for="taskEditName" class="col-2 col-form-label">Especialidad</label>
							<div class="col-10">
								<input id="taskEditName" class="form-control" placeholder="Ingresá el nombre, ejemplo: Alergista">
							</div>
						</div>
						<input id="taskEditId" class="col-md-2" style="display: none;">
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
						<button onclick="editTask()" class="btn btn-success">Modificar</button>
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
				    	"url" : '<c:url value="/admin/BoCategory/getCategories" />',
						"type" : "GET",
						"datatype" : "json"
					},
					"language" : {
						"search" : "Filtrar:",
						"info" : "PÃ¡gina _PAGE_ de _PAGES_",
						"paginate" : {
							"previous" : "Previa",
							"next" : "Siguiente",
						}
					},
					"columns" : [
						{"data" : "occupationName"},
						{"data" : "specialtyName"}, 
						{"data" : "taskName"},
						{"data" : "occupationId"},
						{"data" : "specialtyId"},
						{"data" : "taskId"},
	                    {
	                        "data": "status",
	                        "render": function ( data, type, full, meta ) {
	                        		 return "<button id='editOccupationBtn' class='btn btn-default'>Editar Ocupacion</button>";
	                        }
	                    },
	                    {
	                        "data": "status",
	                        "render": function ( data, type, full, meta ) {
	                        	if(full.specialtyName == '' && full.taskName == ''){
	                        		return null;
	                        	} else {
	                        		return "<button id='editSpecialtyBtn' class='btn btn-warning'>Editar Especialidad</button>";
	                        	}
	                        }
	                    },
	                    {
	                        "data": "status",
	                        "render": function ( data, type, full, meta ) {
	                        	if(full.taskName == ''){
	                        		return null;
	                        	} else {
	                        		 return "<button id='editTaskBtn' class='btn btn-info'>Editar Tarea</button>";
	                        	}
	                        }
	                    }
						
						],
					"columnDefs" : [ {
						"targets" : [ 3, 4, 5 ],
						"visible" : false,
						"searchable" : false
					}, ],
					"bLengthChange" : false
				});
				
				var table = $('#categories-table').DataTable();

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
			    hideEditMessages();
			    
			
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
			
			$('#occupationEditModal').on('hidden.bs.modal', function () {
			    table.ajax.reload();
			    hideEditMessages()
			})
			
			$('#specialtyEditModal').on('hidden.bs.modal', function () {
			    table.ajax.reload();
			    hideEditMessages()
			})
			
			$('#taskEditModal').on('hidden.bs.modal', function () {
			    table.ajax.reload();
			    hideEditMessages()
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
			
			
		    $('#categories-table tbody').on( 'click', 'button', function (e) {
		    	
		    	
		        var data = table.row( $(this).parents('tr') ).data();
		    	
		    	if(e.target.id == "editOccupationBtn") {
		    		$('#occupationEditModal').modal('show');
		    		$("#occupationEditId").val(data.occupationId);
		    		$("#occupationEditName").val(data.occupationName);
		    		
		    	} else if(e.target.id == "editSpecialtyBtn") {
		    		$('#specialtyEditModal').modal('show');
		    		$("#specialtyEditId").val(data.specialtyId);
		    		$("#specialtyEditName").val(data.specialtyName);
		    	} else {
		    		$('#taskEditModal').modal('show');
		    		$("#taskEditId").val(data.taskId);
		    		$("#taskEditName").val(data.taskName);
		    	}
		    	

		        
		    } );

			});
			
			function addOccupation(){
				
				var newOccupation = $("#occupationTxt").val();
				hideMessages();
				
				$("#occupationTxt").val("");
				
				var data = {}
				data["name"] = newOccupation;
				data["id"] = "";
				
				$.ajax({ 
	 			    type: 'POST', 
	 			    contentType: "application/json",
				    url: '<c:url value="/admin/BoCategory/saveOccupation?${_csrf.parameterName}=${_csrf.token}" />',  
				    data: JSON.stringify(data),
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
				
				var data = {}
				data["name"] = specialtyName;
				data["id"] = occupationId;

				hideMessages();
				
				$.ajax({ 
				    type: 'POST', 
				    contentType: "application/json",
				    url: '<c:url value="/admin/BoCategory/saveSpecialty?${_csrf.parameterName}=${_csrf.token}" />',
				    data: JSON.stringify(data),
				    dataType: 'json',
				    success: function (data) { 
						if(data.status == 200){
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
				
				var data = {}
				data["name"] = taskTaskName;
				data["id"] = taskSpecialtyId;
				
				if(taskOccupationId == "" || taskSpecialtyId == "" || taskTaskName == "") {
					taskShowError("Los campos no pueden estar vacios.");
					return;
				}
				
				$.ajax({ 
				    type: 'POST', 
				    contentType: "application/json",
				    url: '<c:url value="/admin/BoCategory/saveTask?${_csrf.parameterName}=${_csrf.token}" />',
				    data: JSON.stringify(data),
				    dataType: 'json',
				    success: function (data) { 
						if(data.status == 200){
						    $("#taskTaskName").val("");
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
				
				$.get("<c:url value='/admin/BoCategory/occupations/' />", function(data){
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
				
				$.get("<c:url value='/admin/BoCategory/occupations/' />", function(data){
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
				
				$.get("<c:url value='/admin/BoCategory/specialties/' />" + id, function(data){
					
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
			
			function editOccupation() {
				var occupationName = $("#occupationEditName").val();
				var occupationId = $("#occupationEditId").val();
				
				var data = {}
				data["name"] = occupationName;
				data["id"] = occupationId;
				
				$.ajax({ 
				    type: 'POST', 
				    contentType: "application/json",
				    url: '<c:url value="/admin/BoCategory/editOccupation?${_csrf.parameterName}=${_csrf.token}" />',
				    data: JSON.stringify(data),
				    dataType: 'json',
				    success: function (data) { 
						if(data.status == 200){
							$("#occupationName").val("");
						    $("#occupationId").val("");
						    occupationEditShowSuccess(data.data);
						} else {
							occupationEditShowError("Hubo un error al editar la ocupacion");
					    }
				    },
				    error: function(XMLHttpRequest, textStatus, errorThrown) { 
				    	occupationEditShowError("Hubo un error al editar la ocupacion");
	                }
				});
			}
			
			function editSpecialty() {
				var specialtyName = $("#specialtyEditName").val();
				var specialtyId = $("#specialtyEditId").val();
				
				var data = {}
				data["name"] = specialtyName;
				data["id"] = specialtyId;
				
				$.ajax({ 
				    type: 'POST', 
				    contentType: "application/json",
				    url: '<c:url value="/admin/BoCategory/editSpecialty?${_csrf.parameterName}=${_csrf.token}" />',
				    data: JSON.stringify(data),
				    dataType: 'json',
				    success: function (data) { 
						if(data.status == 200){
							$("#specialtyName").val("");
						    $("#specialtyId").val("");

						    specialtyEditShowSuccess(data.data);
						} else {
							specialtyEditShowError("Hubo un error al editar la especialidad");
					    }
				    },
				    error: function(XMLHttpRequest, textStatus, errorThrown) { 
				    	specialtyEditShowError("Hubo un error al editar la especialidad");
	                }
				});
			}
						
			function editTask() {
				var taskName = $("#taskEditName").val();
				var taskId = $("#taskEditId").val();
				var data = {}
				data["name"] = taskName;
				data["id"] = taskId;
				
				$.ajax({ 
				    type: 'POST', 
				    contentType: "application/json",
				    url: '<c:url value="/admin/BoCategory/editTask?${_csrf.parameterName}=${_csrf.token}" />',
				    data: JSON.stringify(data),
				    dataType: 'json',
				    success: function (data) { 
						if(data.status == 200){
							$("#specialtyName").val("");
						    $("#specialtyId").val("");

						    taskEditShowSuccess(data.data);
						} else {
							taskEditShowError("Hubo un error al editar la tarea");
					    }
				    },
				    error: function(XMLHttpRequest, textStatus, errorThrown) { 
				    	taskEditShowError("Hubo un error al editar la tarea");
	                }
				});
			}
			
			//Funciones para edit ocupacion
			function occupationEditShowSuccess(message) {
				$("#occupationEditSuccess").show();
				$("#occupationEditSuccess").text(message);
			}
			function occupationEditShowError(message) {
				$("#occupationEditError").show();
				$("#occupationEditError").text(message);
			}
			
			//Funciones para edit tarea
			function specialtyEditShowSuccess(message) {
				$("#specialtyEditSuccess").show();
				$("#specialtyEditSuccess").text(message);
			}
			function specialtyEditShowError(message) {
				$("#specialtyEditError").show();
				$("#specialtyEditError").text(message);
			}
			
			//Funciones para edit tarea
			function taskEditShowSuccess(message) {
				$("#taskEditSuccess").show();
				$("#taskEditSuccess").text(message);
			}
			function taskEditShowError(message) {
				$("#taskEditError").show();
				$("#taskEditError").text(message);
			}
			
			//Funcion ocultar mensajes modales de edicion
			function hideEditMessages() {
				$("#occupationEditSuccess").hide();
				$("#occupationEditError").hide();
				
				$("#specialtyEditSuccess").hide();
				$("#specialtyEditError").hide();
				
				$("#taskEditSuccess").hide();
				$("#taskEditError").hide();
			}
			
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
