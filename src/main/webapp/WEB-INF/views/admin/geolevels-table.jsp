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
										<a type="submit" class="btn btn-info col-md-12"    data-toggle="modal" data-target="#cityModal" >Crear nueva Ciudad/Barrio</a>
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


		<!-- Modal de provincia -->
		<div id="provinceModal" class="modal fade bd-example-modal-lg" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Crear provincia</h4>
					</div>
					<div id="bodyModalBootstrap" class="modal-body">
						<div class="row" >
							<div id="provinceSuccess" class="msg" style="display:none;">error</div>
							<div id="provinceError" class="error" style="display:none;">error</div>
						</div>
						<div class="form-group row">
							<label for="provinceTxt" class="col-2 col-form-label">Nombre</label>
							<div class="col-10">
								<input id="provinceTxt" class="form-control" placeholder="Ingresá el nombre, ejemplo: Santa Cruz">
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
						<button onclick="addProvince()" class="btn btn-success">Agregar</button>
					</div>
				</div>
			</div>
		</div>		
		<!-- Modal de Localidad/Partido -->
		<div id="regionModal" class="modal fade bd-example-modal-lg" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
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
						<div class="form-group row">
							<label for="geo3ProvinceName" class="col-2 col-form-label">Seleccionar provincia</label>
							<div class="col-10">
								<div class="input-group">
									<input id="geo3ProvinceName" type="text" data-provide="typeahead" class="typeahead form-control" placeholder="Buscar provincia...">
									<span class="input-group-btn">
										<button id="geo3CleanButton" class="btn btn-danger" type="button">Limpiar</button>
									</span>
								</div>
							</div>
							<input id="geo3ProvinceId" class="col-md-2" style="display: none;">
						</div>
						<div class="form-group row">
							<label for="geo3RegionName" class="col-2 col-form-label">Localidad/Partido:</label>
							<div class="col-10">
								<input id="geo3RegionName" class="form-control" placeholder="Ingresá el nombre de la localidad o partido, ejemplo: Tigre">
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button>
						<button onclick="addRegion()" class="btn btn-success">Agregar</button>
					</div>
				</div>
			</div>
		</div>
		<!-- Modal de Barrio o Ciudad -->
		<div id="cityModal" class="modal fade bd-example-modal-lg" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Crear Ciudad o Barrio</h4>
					</div>
					<div id="bodyModalBootstrap" class="modal-body">
						<div class="row" >
							<div id="citySuccess" class="msg" style="display: none;"></div>
							<div id="cityError" class="error"  style="display: none;"></div>
						</div>
						<div class="form-group row">
							<label for="cityGeo2Name" class="col-2 col-form-label">Seleccionar Provincia</label>
							<div class="col-10">
 								<div class="input-group">
									<input id="cityGeo2Name" type="text" class="typeahead form-control" data-provide="typeahead" placeholder="Buscar provincia...">
									<span class="input-group-btn"> 
										<button id="CityGeo2CleanButton" class="btn btn-danger" type="button">Limpiar</button>
									</span>
								</div>
							</div>
							<input id="cityGeo2Id" class="col-md-2" style="display: none;">
						</div>
						<div class="form-group row">
							<label for="cityGeo3Name" class="col-2 col-form-label">Seleccionar Partido/Localidad</label>
							<div class="col-10">
 								<div class="input-group">
									<input id="cityGeo3Name" type="text" class="typeahead form-control" data-provide="typeahead" placeholder="Buscar Partido o Localidad...">
									<span class="input-group-btn"> 
										<button id="cityGeo3CleanButton" class="btn btn-danger" type="button">Limpiar</button>
									</span>
								</div>
							</div>
							<input id="cityGeo3Id" class="col-md-2" style="display: none;">
						</div>
						<div class="form-group row">
							<label for="cityName" class="col-2 col-form-label">Ciudad/Barrio:</label>
							<div class="col-10">
								<input id="cityName" class="form-control" placeholder="Ingresá el nombre de la Ciudad o Barrio, ejemplo: Caballito">
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button>
						<button onclick="addCity()" class="btn btn-success">Agregar</button>
					</div>
				</div>
			</div>
		</div>
		<!-- EDITORES -->
		<!-- Modal de edit para provincia -->
		<div id="provinceEditModal" class="modal fade bd-example-modal-lg" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Editar provincia</h4>
					</div>
					<div id="bodyModalBootstrap" class="modal-body">
						<div class="row" >
							<div id="provinceEditSuccess" class="msg" style="display: none;" >error</div>
							<div id="provinceEditError" class="error"  style="display: none;">error</div>
						</div>
						<div class="form-group row">
							<label for="provinceEditName" class="col-2 col-form-label">Nombre</label>
							<div class="col-10">
								<input id="provinceEditName" class="form-control" placeholder="Ingresá el nombre, ejemplo: Buenos Aires">
							</div>
						</div>
						<input id="provinceEditId" class="col-md-2" style="display: none;">
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-danger" data-dismiss="modal">Cerrar</button>
						<button onclick="editProvince()" class="btn btn-success">Modificar</button>
					</div>
				</div>
			</div>
		</div>
		<!-- Modal de edit para Localidades/Partidos -->
		<div id="regionEditModal" class="modal fade bd-example-modal-lg" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Editar Partido/Localidad</h4>
					</div>
					<div id="bodyModalBootstrap" class="modal-body">
						<div class="row" >
							<div id="regionEditSuccess" class="msg" style="display:none;">error</div>
							<div id="regionEditError" class="error" style="display:none;">error</div>
						</div>
						<div class="form-group row">
							<label for="regionEditName" class="col-2 col-form-label">Nombre</label>
							<div class="col-10">
								<input id="regionEditName" class="form-control" placeholder="Ingresá el nombre, ejemplo: Tigre">
							</div>
						</div>
						<input id="regionEditId" class="col-md-2" style="display: none;">
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
						<button onclick="editRegion()" class="btn btn-success">Modificar</button>
					</div>
				</div>
			</div>
		</div>
		<!-- Modal de edit para Ciudad/Barrio -->
		<div id="cityEditModal" class="modal fade bd-example-modal-lg" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Editar Ciudad o Barrio</h4>
					</div>
					<div id="bodyModalBootstrap" class="modal-body">
						<div class="row" >
							<div id="cityEditSuccess" class="msg" style="display:none;">error</div>
							<div id="cityEditError" class="error" style="display:none;">error</div>
						</div>
						<div class="row">
							<label for="cityEditName" class="col-2 col-form-label">Nombre:</label>
							<div class="col-10">
								<input id="cityEditName" class="form-control" placeholder="Ingresá el nombre, ejemplo: Caballito">
							</div>
							<input id="cityEditId" class="col-md-2" style="display: none;">
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
						<button onclick="editCity()" class="btn btn-success">Modificar</button>
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
						"info" : "PÃ¡gina _PAGE_ de _PAGES_",
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
	                        		 return "<button id='editProvinceBtn' class='btn btn-default'>Editar Provincia</button>";
	                        }
	                    },
	                    {
	                        "data": "status",
	                        "render": function ( data, type, full, meta ) {
	                        	if(full.geo3Name == '' && full.cityName == ''){
	                        		return null;
	                        	} else {
	                        		return "<button id='editRegionBtn' class='btn btn-warning'>Editar Localidad/Partido</button>";
	                        	}
	                        }
	                    },
	                    {
	                        "data": "status",
	                        "render": function ( data, type, full, meta ) {
	                        	if(full.geo4Name == ''){
	                        		return null;
	                        	} else {
	                        		 return "<button id='editCityBtn' class='btn btn-info'>Editar Ciudad/Barrio</button>";
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
			    $("#CityGeo2CleanButton").val("");
			    $("#cityGeo2Id").val("");
			    $("#cityGeo2Name").val("");
			    $("#cityGeo3Name").val("");
			    $("#cityGeo3Id").val("");
			    $("#cityName").val("");
			    $("#cityGeo2Name").prop('disabled', false);
			    $("#cityGeo3CleanButton").prop('disabled', false);
			    $("#cityGeo3Name").prop('disabled', false);
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
				regionHideMessages();
			    table.ajax.reload();
				$("#geo3ProvinceName").val("");
			    $("#geo3ProvinceId").val("");
			    $("#geo3ProvinceName").prop('disabled', false);
			    $("#geo3RegionName").val("");
			})
			
			//Listener que se ejecuta al cerrar el modal ya sea por medio del boton cerrar como con la cruz
			$('#cityModal').on('hidden.bs.modal', function () {
				cityHideMessages();
			    table.ajax.reload();
			})
			
			$('#provinceEditModal').on('hidden.bs.modal', function () {
			    table.ajax.reload();
			    hideEditMessages()
			})
			
			$('#regionEditModal').on('hidden.bs.modal', function () {
			    table.ajax.reload();
			    hideEditMessages()
			})
			
			$('#cityEditModal').on('hidden.bs.modal', function () {
			    table.ajax.reload();
			    hideEditMessages()
			})
			
			
			$("#geo3CleanButton").click(function() {
			    $("#geo3ProvinceName").val("");
			    $("#geo3ProvinceId").val("");
			    $("#geo3ProvinceName").prop('disabled', false);
			});
			
			$("#CityGeo2CleanButton").click(function() {
			    $("#cityGeo2Name").val("");
			    $("#cityGeo2Id").val("");
			    $("#cityGeo3Name").val("");
			    $("#cityGeo3Id").val("");
			    $("#cityGeo2Name").prop('disabled', false);
			    $("#cityGeo3CleanButton").prop('disabled', false);
			    $("#cityGeo3Name").prop('disabled', false);
			    $('#cityGeo3Name').typeahead('destroy');
			});
			
			$("#cityGeo3CleanButton").click(function() {
			    $("#cityGeo3Name").val("");
			    $("#cityGeo3Id").val("");
			    $("#cityGeo3Name").prop('disabled', false);
			});
			
			
		    $('#categories-table tbody').on( 'click', 'button', function (e) {
		    	
		    	
		        var data = table.row( $(this).parents('tr') ).data();
		    	
		    	if(e.target.id == "editProvinceBtn") {
		    		$('#provinceEditModal').modal('show');
		    		$("#provinceEditId").val(data.geo2Id);
		    		$("#provinceEditName").val(data.geo2Name);
		    		
		    	} else if(e.target.id == "editRegionBtn") {
		    		$('#regionEditModal').modal('show');
		    		$("#regionEditId").val(data.geo3Id);
		    		$("#regionEditName").val(data.geo3Name);
		    	} else {
		    		$('#cityEditModal').modal('show');
		    		$("#cityEditId").val(data.geo4Id);
		    		$("#cityEditName").val(data.geo4Name);
		    	}
		    	

		        
		    } );

			});
			
			function addProvince(){
				
				var newProvince = $("#provinceTxt").val();
				hideMessages();
				
				$("#provinceTxt").val("");
				
				var data = {}
				data["name"] = newProvince;
				data["id"] = "";
				
				$.ajax({ 
				    type: 'POST', 
				    contentType: "application/json",
				    url: '/d2d/admin/BoGeolevel/saveProvince?${_csrf.parameterName}=${_csrf.token}', 
				    data: JSON.stringify(data),
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
				regionHideMessages();
				var provinceId = $("#geo3ProvinceId").val();
				var regionName = $("#geo3RegionName").val();
				
				if(provinceId == "" || regionName == "") {
					regionShowError("Los campos no pueden estar vacios.");
					return;
				}
				
				
				var data = {}
				data["name"] = regionName;
				data["id"] = provinceId;
				
				hideMessages();
				
				$.ajax({ 
				    type: 'POST', 
				    contentType: "application/json",
				    url: '/d2d/admin/BoGeolevel/saveRegion?${_csrf.parameterName}=${_csrf.token}', 
				    data: JSON.stringify(data),
				    dataType: 'json',
				    success: function (data) { 
						if(data.status == 200){
							$("#geo3ProvinceName").val("");
						    $("#geo3ProvinceId").val("");
						    $("#geo3ProvinceName").prop('disabled', false);
						    $("#geo3RegionName").val("");
						    regionShowSuccess(data.data);
						} else {
							regionShowError();
					    }
				    },
				    error: function(XMLHttpRequest, textStatus, errorThrown) { 
				    	regionShowError();
	                }   
				});
				
			}
			
			
			function addCity(){
				cityHideMessages();
				var cityName = $("#cityName").val();
				var cityGeo3Id = $("#cityGeo3Id").val();
				var cityGeo2Id = $("#cityGeo2Id").val();
				
				if(cityGeo2Id == "" || cityGeo3Id == "" || cityName == "") {
					cityShowError("Los campos no pueden estar vacios.");
					return;
				}
				
				var data = {}
				data["name"] = cityName;
				data["id"] = cityGeo3Id;
				
				
				$.ajax({ 
				    type: 'POST', 
				    contentType: "application/json",
				    url: '/d2d/admin/BoGeolevel/saveCity?${_csrf.parameterName}=${_csrf.token}', 
				    data: JSON.stringify(data),
				    dataType: 'json',
				    success: function (data) { 
						if(data.status == 200){
							$("#cityGeo2Name").val("");
						    $("#cityGeo2Id").val("");
						    $("#cityGeo3Name").val("");
						    $("#cityGeo3Id").val("");
						    $("#cityName").val("");
						    $("#cityGeo2Name").prop('disabled', false);
						    $("#cityGeo3Name").prop('disabled', false);

						    cityShowSuccess(data.data);
						} else {
							cityShowError();
					    }
				    },
				    error: function(XMLHttpRequest, textStatus, errorThrown) { 
				    	cityShowError();
	                }   
				    
				});
				
			}
			
			//Funciones para provincia
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
			
			//Funciones para Localidad/Partido
			function regionShowSuccess(message) {
				$("#regionSuccess").show();
				$("#regionSuccess").text(message);
			}
			
			function regionShowError() {
				$("#regionError").show();
				$("#regionError").text("Ha ocurrido un error, por favor intente mas tarde.");
			}
			
			function regionShowError(message) {
				$("#regionError").show();
				$("#regionError").text(message);
			}
			
			function regionHideMessages() {
				$("#regionError").hide();
				$("#regionSuccess").hide();
			}
			
			//Funciones para tarea
			function cityShowSuccess(message) {
				$("#citySuccess").show();
				$("#citySuccess").text(message);
			}
			
			function cityShowError() {
				$("#cityError").show();
				$("#cityError").text("Ha ocurrido un error, por favor intente mas tarde.");
			}
			
			function cityShowError(message) {
				$("#cityError").show();
				$("#cityError").text(message);
			}
			
			function cityHideMessages() {
				$("#cityError").hide();
				$("#citySuccess").hide();
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
				// Fin autocomplete modal Localidades/Partidos
				
				$.get("/d2d/admin/BoGeolevel/provinces", function(data){
				  $("#cityGeo2Name").typeahead({
					  source:data,
					  afterSelect: function(item) {
						    $("#cityGeo2Name").val(item.name);
						    $("#cityGeo2Id").val(item.id);
						    $("#cityGeo2Name").prop('disabled', true);
						    refreshRegion(item.id);
						}
				  });
				},'json');
				// Fin autocomplete modal Tareas Provincias

			}
			
			function refreshRegion(id){
				$('#cityGeo3Name').typeahead('destroy');
				
				$.get("/d2d/admin/BoGeolevel/city/" + id, function(data){
					
					if(data.length == 1 && data[0].name == ""){
						$("#cityGeo3Name").prop('disabled', true);
						$("#cityGeo3Id").val(data[0].id);
						$("#cityGeo3CleanButton").prop('disabled', true);
					} else {
						
						$("#cityGeo3Name").typeahead({
							  source:data,
							  afterSelect: function(item) {
								    $("#cityGeo3Name").val(item.name);
								    $("#cityGeo3Id").val(item.id);
								    $("#cityGeo3Name").prop('disabled', true);
								}
						  });
						}
						// Fin autocomplete modal Tareas Provincias
					
					},'json');
					

			}
			
			function editProvince() {
				
				var provinceName = $("#provinceEditName").val();
				var provinceId = $("#provinceEditId").val();
				
				var data = {}
				data["name"] = provinceName;
				data["id"] = provinceId;
				
				$.ajax({ 
				    type: 'POST', 
				    contentType: "application/json",
				    url: "/d2d/admin/BoGeolevel/editProvince?${_csrf.parameterName}=${_csrf.token}", 
				    data: JSON.stringify(data),
				    dataType: 'json',
				    success: function (data) { 
						if(data.status == 200){
							$("#provinceName").val("");
						    $("#provinceId").val("");
						    provinceEditShowSuccess(data.data);
						} else {
							provinceEditShowError("Hubo un error al editar la provincia");
					    }
				    },
				    error: function(XMLHttpRequest, textStatus, errorThrown) { 
				    	provinceEditShowError("Hubo un error al editar la provincia");
	                }   
				    
				});
			}
			
			function editRegion() {
				var regionName = $("#regionEditName").val();
				var regionId = $("#regionEditId").val();
				
				var data = {}
				data["name"] = regionName;
				data["id"] = regionId;
				
				$.ajax({ 
				    type: 'POST', 
				    contentType: "application/json",
				    url: "/d2d/admin/BoGeolevel/editRegion?${_csrf.parameterName}=${_csrf.token}", 
				    data: JSON.stringify(data),
				    dataType: 'json',
				    success: function (data) { 
						if(data.status == 200){
							$("#regionEditName").val("");
						    $("#regionEditId").val("");

						    regionEditShowSuccess(data.data);
						} else {
							regionEditShowError("Hubo un error al editar la Localidad/Partido");
					    }
				    },
				    error: function(XMLHttpRequest, textStatus, errorThrown) { 
				    	regionEditShowError("Hubo un error al editar la Localidad/Partido");
	                }   
				    
				});
			}
						
			function editCity() {
				
				var cityName = $("#cityEditName").val();
				var cityId = $("#cityEditId").val();

				var data = {}
				data["name"] = cityName;
				data["id"] = cityId;
				
				$.ajax({ 
				    type: 'POST', 
				    contentType: "application/json",
				    url: "/d2d/admin/BoGeolevel/editCity?${_csrf.parameterName}=${_csrf.token}", 
				    data: JSON.stringify(data),
				    dataType: 'json',
				    success: function (data) { 
						if(data.status == 200){
							$("#regionName").val("");
						    $("#regionId").val("");

						    cityEditShowSuccess(data.data);
						} else {
							cityEditShowError("Hubo un error al editar la tarea");
					    }
				    },
				    error: function(XMLHttpRequest, textStatus, errorThrown) { 
				    	cityEditShowError("Hubo un error al editar la tarea");
	                }   
				    
				});
				
			}
			
			
			//Funciones para edit provincia
			function provinceEditShowSuccess(message) {
				$("#provinceEditSuccess").show();
				$("#provinceEditSuccess").text(message);
			}
			function provinceEditShowError(message) {
				$("#provinceEditError").show();
				$("#provinceEditError").text(message);
			}
			
			//Funciones para edit tarea
			function regionEditShowSuccess(message) {
				$("#regionEditSuccess").show();
				$("#regionEditSuccess").text(message);
			}
			function regionEditShowError(message) {
				$("#regionEditError").show();
				$("#regionEditError").text(message);
			}
			
			//Funciones para edit tarea
			function cityEditShowSuccess(message) {
				$("#cityEditSuccess").show();
				$("#cityEditSuccess").text(message);
			}
			function cityEditShowError(message) {
				$("#cityEditError").show();
				$("#cityEditError").text(message);
			}
			
			//Funcion ocultar mensajes modales de edicion
			function hideEditMessages() {
				$("#provinceEditSuccess").hide();
				$("#provinceEditError").hide();
				
				$("#regionEditSuccess").hide();
				$("#regionEditError").hide();
				
				$("#cityEditSuccess").hide();
				$("#cityEditError").hide();
			}
			
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>

