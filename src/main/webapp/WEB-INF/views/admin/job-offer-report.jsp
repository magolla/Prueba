<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<tiles:insertDefinition name="d2d.dashboard">

	<tiles:putAttribute name="title">
		Reporte de avisos
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
	
		<section class="content">
			<form:form method="POST" modelAttribute="filterForm" autocomplete="off" action="${pageContext.request.contextPath}/admin/reports/jobofferstats?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data">
				<div class="row">
					<div class="col-md-6">
						<div class="form-group form-group-job-offer">
							<label for="content" class="control-label">Fecha desde</label>
							<div class="input-group date">
								<div class="input-group-addon">
									<i class="fa fa-calendar"></i>
								</div>
								<input type="text" class="form-control pull-right" id="publishingDateForView" name="publishingDateForView" value="${noteForm.publishingDateForView}" data-inputmask="'alias': 'dd/mm/yyyy'" data-mask>
							</div>
						</div>
					</div>
					
					<div class="col-md-6">
						<div class="form-group form-group-job-offer">
							<label for="content" class="control-label">Fecha hasta</label>
							<div class="input-group date">
								<div class="input-group-addon">
									<i class="fa fa-calendar"></i>
								</div>
								<input type="text" class="form-control pull-right" id="publishingDateForView" name="publishingDateForView" value="${noteForm.publishingDateForView}" data-inputmask="'alias': 'dd/mm/yyyy'" data-mask>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						 <div class="form-group form-group-job-offer-combos">
							<label for="name" class="control-label">&nbsp;</label>
									
							<div class="checkbox checkbox-primary">
								<input id="checkbox-android" name="android" type="checkbox" value="true">
								<label for="checkbox-android">
									Avisos activos solamente
								</label>
							</div>
							<div class="checkbox checkbox-primary">
								<input id="checkbox-sponsor" name="sponsor" type="checkbox" value="true">
								<label for="checkbox-sponsor">
									Avisos temporales
								</label>
							</div>
							<div class="checkbox checkbox-primary">
								<input id="checkbox-inapp" name="ios" type="checkbox" value="true" >
								<label for="checkbox-inapp">
									Avisos permanentes
								</label>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group form-group-job-offer-combos">
							<label for="name" class="control-label">Filtrar por Ocupaci&oacute;n principal</label>
													
							<select id="occupationsSelect" class="selectpicker">
								<option value="-1">TODAS</option>
								<c:forEach var="occupation" items="${occupationList}">
									<option value="<c:out value="${occupation.id}"/>"><c:out value="${occupation.name}"/></option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group form-group-job-offer-combos">
							<label for="name" class="control-label">Filtrar por Especialidad</label>
							
							<div id="specialtiesSelectBox">
								<select id="specialtiesSelect" class="selectpicker">
									<option value="-1">TODAS</option>
									<c:forEach var="geo" items="${geoList}">
										<option value="<c:out value="${geo.id}"/>"><c:out value="${geo.name}"/></option>
									</c:forEach>
								</select>
							</div>					
						</div>
						<div class="form-group form-group-job-offer-combos">
							<label for="name" class="control-label">Filtrar por Tarea</label>
							
							<div id="tasksSelectBox">					
								<select id="tasksSelect" class="selectpicker">
									<option value="-1">TODAS</option>
									<c:forEach var="geo" items="${geoList}">
										<option value="<c:out value="${geo.id}"/>"><c:out value="${geo.name}"/></option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<label for="name" class="control-label">Seleccionar Provincias</label>
													
							<select id="geosSelect" class="selectpicker" multiple>
								<c:forEach var="geo" items="${geoList}">
									<option value="<c:out value="${geo.id}"/>"><c:out value="${geo.name}"/></option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-offset-6 col-md-6">
						<button type="submit" class="btn btn-success pull-left">Lanzar Reporte</button>
					</div>
				</div>
				
				<div id="geoLevelIdsBox"></div>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form:form>
		</section>
		
		<div class="line-chart-separator"></div>
		
		<div class="row">
			<div class="col-lg-12">
				<div class="wrapper">
					<canvas id="chart-0" style="height:400px;padding: 15px;"></canvas>
				</div>
			</div>
		</div>
		
		<script>
			$('#occupationsSelect').on('changed.bs.select', function (e) {
				loadSpecialties();
			});
			
			$('#geosSelect').selectpicker('val', ${filterForm.geoLevels2});
			
			$('#geosSelect').on('changed.bs.select', function (e) {
				loadGeosInput();
			});
			
			arrayGeosIds = [];
			
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
			        url: 'specialties/' + occupation_id,
			        type: 'GET',
			        success: function(data) {
			        	$("#specialtiesSelectBox").html(data);
			        }
			    });
			}
			
			function loadTasks() {
				specialty_id = $("#specialtiesSelect").val();
				$.ajax({
			        url: 'tasks/' + specialty_id,
			        type: 'GET',
			        success: function(data) {
			        	$("#tasksSelectBox").html(data);
			        }
			    });
			}
			
			loadGeosInput();
		</script>
		
		<script>
			var presets = window.chartColors;
			var inputs = {
				min: 20,
				max: 80,
				count: 12,
				decimals: 2,
				continuity: 1
			};
			
			labels = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];
			
			var first =  
			[30, 40, 50, 60, 
			 70, 80, 70, 60, 
			 50, 30, 40, 50];
			
			var second =  
			[10, 20, 30, 40, 
			 50, 60, 50, 40, 
			 20, 10, 20, 30];
			
			var third =  
			[95, 85, 75, 75, 
			 85, 95, 115, 125, 
			 125, 115, 125, 135];
			
			var fourth =  
			[110, 120, 130, 140, 
			 150, 160, 150, 140, 
			 120, 110, 120, 130];
			
			var fifth =  
			[210, 220, 230, 240, 
			 250, 260, 250, 240, 
			 220, 210, 220, 230];
		
			var data = {
				labels: labels,
				datasets: [{
					borderColor: presets.green,
					data: first,
					label: 'Avisos creados TOTALES',
					fill: 'false'
				}, {
					borderColor: presets.red,
					data: second,
					label: 'Temporales',
					fill: 'false'
				}, {
					borderColor: presets.blue,
					data: third,
					label: 'Permanentes',
					fill: 'false'
				}, {
					borderColor: presets.yellow,
					data: fourth,
					label: 'Avisos creados Activos',
					fill: 'false'
				}, {
					borderColor: presets.purple,
					data: fifth,
					label: 'Contrataciones',
					fill: 'false'
				}]
			};
			var options = {
				maintainAspectRatio: false,
				spanGaps: false,
				elements: {
					line: {
						tension: 0.000001
					}
				},
				scales: {
				}
			};
			var chart = new Chart('chart-0', {
				type: 'line',
				data: data,
				options: options
			});
			
		</script>
		
	</tiles:putAttribute>

</tiles:insertDefinition>

