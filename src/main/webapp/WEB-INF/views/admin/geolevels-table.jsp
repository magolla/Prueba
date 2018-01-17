<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<tiles:insertDefinition name="d2d.dashboard">

	<tiles:putAttribute name="title">
		ABM de Geolevels
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<section class="content">
			<div class="row">
				<div class="col-md-12">
					<div class="nav-tabs-custom">
			            <ul class="nav nav-tabs">
			              <li class="active"><a href="#tab_1" data-toggle="tab">Listado de Geolevels</a></li>
			            </ul>
			            <div class="tab-content">
							<div class="tab-pane active" id="tab_1">
								<div class="col-md-12" style="margin-bottom:50px;">
									<div class="col-md-4">
										<a type="submit" class="btn btn-default col-md-12" data-toggle="modal" data-target="#provinceModal" >Crear nueva Provincia</a>
									</div>
									<div class="col-md-4">
										<a type="submit" class="btn btn-warning col-md-12" data-toggle="modal" data-target="#regionModal" >Crear nueva Localidad/Partido</a>
									</div>
									<div class="col-md-4">
										<a type="submit" class="btn btn-info col-md-12"    data-toggle="modal" data-target="#taskModal" >Crear nueva Ciudad/Barrio</a>
									</div>
								</div>
								<hr>
								<table id="categories-table" class="display" cellspacing="0" width="100%">
									<thead>
										<tr>
											<th>Provincia</th>
											<th>Localidad/Partido</th>
											<th>Ciudad/Barrio</th>
											<th>geo2Id</th>
											<th>geo3Id</th>
											<th>geo4Id</th>
											<th>Editar Provincia</th>
											<th>Editar Localidad/Partido</th>
											<th>Editar Ciudad/Barrio</th>
										</tr>
									</thead>
									<tfoot>
										<tr>
											<th>Provincia</th>
											<th>Localidad/Partido</th>
											<th>Ciudad/Barrio</th>
											<th>geo2Id</th>
											<th>geo3Id</th>
											<th>geo4Id</th>
											<th>Editar Provincia</th>
											<th>Editar Localidad/Partido</th>
											<th>Editar Ciudad/Barrio</th>
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
		<div id="provinceModal" class="modal fade bd-example-modal-lg"
			role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Crear provincia</h4>
					</div>
					<div id="bodyModalBootstrap" class="modal-body">
					<div class="row" >
						<div id="provinceSuccess" class="msg" style="display: none;" >sfas</div>
						<div id="provinceError" class="error"  style="display: none;">sfas</div>
					</div>
						<div class="row">
							<label class="col-md-4">Nombre:</label>
							<input id="provinceTxt" class="col-md-4">
							<button onclick="addProvince()">Agregar</button>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button>
					</div>
				</div>
			</div>
		</div>
		<!-- Modal de Especialidad -->
		<div id="regionModal" class="modal fade bd-example-modal-lg"
			role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Crear Localidad/Partido</h4>
					</div>
					<div id="bodyModalBootstrap" class="modal-body">
					<div class="row" >
						<div id="regionSuccess" class="msg" style="display: none;" ></div>
						<div id="regionError" class="error"  style="display: none;"></div>
					</div>
						<div class="row">
							<label class="col-md-4">Provincia:</label>
							<input id="geo3ProvinceName" type="text" class="typeahead col-md-4" data-provide="typeahead" >
							<input id="geo3ProvinceId" class="col-md-2" style="display: none;">
							<button id="geo3CleanButton">Borrar</button>
						</div>
						<div class="row">
							<label class="col-md-4">Localidad/Partido:</label>
							<input id="geo3RegionName" class="col-md-4">
							<button onclick="addRegion()">Agregar</button>
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
		
		<!-- Modal de edit para ocupacion -->
		<div id="provinceEditModal" class="modal fade bd-example-modal-lg"
			role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Editar ocupacion</h4>
					</div>
					<div id="bodyModalBootstrap" class="modal-body">
					<div class="row" >
						<div id="provinceEditSuccess" class="msg" style="display: none;" >sfas</div>
						<div id="provinceEditError" class="error"  style="display: none;">sfas</div>
					</div>
						<div class="row">
							<label class="col-md-4">Nombre:</label>
							<input id="provinceEditName" class="col-md-4">
							<input id="provinceEditId" class="col-md-2" style="display: none;">
							<button onclick="editOccupation()">Editar</button>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button>
					</div>
				</div>
			</div>
		</div>
		
		<!-- Modal de edit para especialidades -->
		<div id="specialtyEditModal" class="modal fade bd-example-modal-lg"
			role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
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
						<div class="row">
							<label class="col-md-4">Nombre:</label>
							<input id="specialtyEditName" class="col-md-4">
							<input id="specialtyEditId" class="col-md-2" style="display: none;">
							<button onclick="editSpecialty()">Editar</button>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button>
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
						<div class="row">
							<label class="col-md-4">Nombre:</label>
							<input id="taskEditName" class="col-md-4">
							<input id="taskEditId" class="col-md-2" style="display: none;">
							<button onclick="editTask()">Editar</button>
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
						"url" : '/d2d/admin/BoGeolevel/getGeolevels',
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
					"columns" : [
						{"data" : "geo2Name"},
						{"data" : "geo3Name"}, 
						{"data" : "geo4Name"},
						{"data" : "geo2Id"},
						{"data" : "geo3Id"},
						{"data" : "geo4Id"},
	                    {
	                        "data": "status",
	                        "render": function ( data, type, full, meta ) {
	                        		 return "<button id='editOccupationBtn' class='btn btn-default'>Editar Provincia</button>";
	                        }
	                    },
	                    {
	                        "data": "status",
	                        "render": function ( data, type, full, meta ) {
	                        	if(full.specialtyName == '' && full.taskName == ''){
	                        		return null;
	                        	} else {
	                        		return "<button id='editSpecialtyBtn' class='btn btn-warning'>Editar Localidad/Partido</button>";
	                        	}
	                        }
	                    },
	                    {
	                        "data": "status",
	                        "render": function ( data, type, full, meta ) {
	                        	if(full.taskName == ''){
	                        		return null;
	                        	} else {
	                        		 return "<button id='editTaskBtn' class='btn btn-info'>Editar Ciudad/Barrio</button>";
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
				$("#geo3ProvinceName").val("");
			    $("#geo3ProvinceId").val("");
			    $("#geo3ProvinceName").prop('disabled', false);
			    $("#geo3RegionName").val("");
			    $("#taskOccupationName").val("");
			    $("#taskOccupationId").val("");
			    $("#taskSpecialtyName").val("");
			    $("#taskSpecialtyId").val("");
			    $("#taskTaskName").val("");
			    $("#taskOccupationName").prop('disabled', false);
			    $("#taskEspCleanButton").prop('disabled', false);
			    $("#taskSpecialtyName").prop('disabled', false);
			    hideEditMessages();
			    
			
			    refreshProvinces();

				
			//Listener que se ejecuta al cerrar el modal ya sea por medio del boton cerrar como con la cruz
			$('#provinceModal').on('hidden.bs.modal', function () {
				hideMessages();
			    table.ajax.reload();
				$("#provinceTxt").val("");
			})
			
			//Listener que se ejecuta al cerrar el modal ya sea por medio del boton cerrar como con la cruz
			$('#regionModal').on('hidden.bs.modal', function () {
				specialtyHideMessages();
			    table.ajax.reload();
				$("#geo3ProvinceName").val("");
			    $("#geo3ProvinceId").val("");
			    $("#geo3ProvinceName").prop('disabled', false);
			    $("#geo3RegionName").val("");
			})
			
			//Listener que se ejecuta al cerrar el modal ya sea por medio del boton cerrar como con la cruz
			$('#taskModal').on('hidden.bs.modal', function () {
				taskHideMessages();
			    table.ajax.reload();
			})
			
			$('#provinceEditModal').on('hidden.bs.modal', function () {
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
			
			
			$("#geo3CleanButton").click(function() {
			    $("#geo3ProvinceName").val("");
			    $("#geo3ProvinceId").val("");
			    $("#geo3ProvinceName").prop('disabled', false);
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
		    		$('#provinceEditModal').modal('show');
		    		$("#provinceEditId").val(data.provinceId);
		    		$("#provinceEditName").val(data.provinceName);
		    		
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
			
			function addProvince(){
				
				var newProvince = $("#provinceTxt").val();
				hideMessages();
				
				$("#provinceTxt").val("");
				
				$.ajax({ 
				    type: 'GET', 
				    url: '/d2d/admin/BoGeolevel/saveProvince', 
				    data: { provinceName: newProvince }, 
				    dataType: 'json',
				    success: function (data) { 
						if(data.status == 200){
							refreshProvinces();
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
			
			
			function addRegion(){
				specialtyHideMessages();
				var provinceId = $("#geo3ProvinceId").val();
				var regionName = $("#geo3RegionName").val();
				
				if(provinceId == "" || regionName == "") {
					specialtyShowError("Los campos no pueden estar vacios.");
					return;
				}
				
				hideMessages();
				
				$.ajax({ 
				    type: 'GET', 
				    url: '/d2d/admin/BoGeolevel/saveRegion', 
				    data: { geo3ProvinceId: provinceId,
				    		newRegionName: regionName}, 
				    dataType: 'json',
				    success: function (data) { 
						if(data.status == 200){
							$("#geo3ProvinceName").val("");
						    $("#geo3ProvinceId").val("");
						    $("#geo3ProvinceName").prop('disabled', false);
						    $("#geo3RegionName").val("");
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
				$("#provinceSuccess").show();
				$("#provinceSuccess").text(message);
			}
			
			function showError() {
				$("#provinceError").show();
				$("#provinceError").text("Ha ocurrido un error, por favor intente mas tarde.");
			}
			
			function showError(message) {
				$("#provinceError").show();
				$("#provinceError").text(message);
			}
			
			function hideMessages() {
				$("#provinceError").hide();
				$("#provinceSuccess").hide();
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

			
			function refreshProvinces(){
			    
				$('.typeahead').typeahead('destroy');
				
				$.get("/d2d/admin/BoGeolevel/provinces", function(data){
				  $("#geo3ProvinceName").typeahead({
					  source:data,
					  afterSelect: function(item) {
						    $("#geo3ProvinceName").val(item.name);
						    $("#geo3ProvinceId").val(item.id);
						    $("#geo3ProvinceName").prop('disabled', true);
						}
				  });
				},'json');
				// Fin autocomplete modal especialidades
				
				$.get("/d2d/admin/BoGeolevel/provinces", function(data){
				  $("#taskProvinceName").typeahead({
					  source:data,
					  afterSelect: function(item) {
						    $("#taskProvinceName").val(item.name);
						    $("#taskProvinceId").val(item.id);
						    $("#taskProvinceName").prop('disabled', true);
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
			
			function editOccupation() {
				
				var provinceName = $("#provinceEditName").val();
				var provinceId = $("#provinceEditId").val();
				
				var data = {}
				data["name"] = provinceName;
				data["id"] = provinceId;
				
				$.ajax({ 
				    type: 'POST', 
				    contentType: "application/json",
				    url: "/d2d/admin/BoCategory/editOccupation?${_csrf.parameterName}=${_csrf.token}", 
				    data: JSON.stringify(data),
				    dataType: 'json',
				    success: function (data) { 
						if(data.status == 200){
							$("#provinceName").val("");
						    $("#provinceId").val("");
						    provinceEditShowSuccess(data.data);
						} else {
							provinceEditShowError("Hubo un error al editar la ocupacion");
					    }
				    },
				    error: function(XMLHttpRequest, textStatus, errorThrown) { 
				    	provinceEditShowError("Hubo un error al editar la ocupacion");
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
				    url: "/d2d/admin/BoCategory/editSpecialty?${_csrf.parameterName}=${_csrf.token}", 
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
				    url: "/d2d/admin/BoCategory/editTask?${_csrf.parameterName}=${_csrf.token}", 
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
			function provinceEditShowSuccess(message) {
				$("#provinceEditSuccess").show();
				$("#provinceEditSuccess").text(message);
			}
			function provinceEditShowError(message) {
				$("#provinceEditError").show();
				$("#provinceEditError").text(message);
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
				$("#provinceEditSuccess").hide();
				$("#provinceEditError").hide();
				
				$("#specialtyEditSuccess").hide();
				$("#specialtyEditError").hide();
				
				$("#taskEditSuccess").hide();
				$("#taskEditError").hide();
			}
			
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>

