<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<tiles:insertDefinition name="d2d.dashboard">

	<tiles:putAttribute name="title">
		Reporte de avisos
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
	
		<section class="content">
		
			<c:if test="${validations != null}">
				<div class="alert alert-danger">
					<c:forEach var="warningValidation" items="${validations}">
						- ${warningValidation} <br>
					</c:forEach>
				</div>
			</c:if>
		
			<form:form method="POST" id="filterForm" modelAttribute="filterForm" autocomplete="off" action="${pageContext.request.contextPath}/admin/reports/jobofferstats?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data">
				<input type="hidden" name="startMonth" id="startMonth">
				<input type="hidden" name="startYear" id="startYear">
				<input type="hidden" name="endMonth" id="endMonth">
				<input type="hidden" name="endYear" id="endYear">
				
				<div class="row">
					<div class="col-md-6">
						<div class="form-group form-group-job-offer">
							<label for="content" class="control-label">Per&iacute;odo desde</label>
							<div class="input-group date">
								<div class="input-group-addon">
									<i class="fa fa-calendar"></i>
								</div>
								<input type="text" class="form-control pull-right" id="startDateForView" name="startDateForView" data-inputmask="'alias': 'dd/mm/yyyy'" data-mask>
							</div>
						</div>
					</div>
					
					<div class="col-md-6">
						<div class="form-group form-group-job-offer">
							<label for="content" class="control-label">Per&iacute;odo hasta</label>
							<div class="input-group date">
								<div class="input-group-addon">
									<i class="fa fa-calendar"></i>
								</div>
								<input type="text" class="form-control pull-right" id="endDateForView" name="endDateForView" data-inputmask="'alias': 'dd/mm/yyyy'" data-mask>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						 <div class="form-group form-group-job-offer-combos">
							<label for="name" class="control-label">&nbsp;</label>
									
							<div class="checkbox checkbox-primary">
								<input id="checkbox-total-offers" name="totalOffers" type="checkbox"  value="true" ${filterForm.totalOffers ? "checked" : ""}>
								<label for="checkbox-total-offers">
									Avisos creados TOTALES
								</label>
							</div>
							<div class="checkbox checkbox-primary">
								<input id="checkbox-temporal-offers" name="temporalOffers" type="checkbox"  value="true" ${filterForm.temporalOffers ? "checked" : ""}>
								<label for="checkbox-temporal-offers">
									Avisos temporales
								</label>
							</div>
							<div class="checkbox checkbox-primary">
								<input id="checkbox-permanent-offers" name="permanentOffers" type="checkbox"  value="true" ${filterForm.permanentOffers ? "checked" : ""} >
								<label for="checkbox-permanent-offers">
									Avisos permanentes
								</label>
							</div>
							<div class="checkbox checkbox-primary">
								<input id="checkbox-active-offers" name="activeOffers" type="checkbox"  value="true" ${filterForm.activeOffers ? "checked" : ""}>
								<label for="checkbox-active-offers">
									Avisos creados ACTIVOS
								</label>
							</div>
							<div class="checkbox checkbox-primary">
								<input id="checkbox-contracted" name="contracted" type="checkbox"  value="true" ${filterForm.contracted ? "checked" : ""} >
								<label for="checkbox-contracted">
									Contrataciones
								</label>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="form-group form-group-job-offer-combos">
							<label for="name" class="control-label">Filtrar por Ocupaci&oacute;n principal</label>
													
							<select id="occupationsSelect" class="selectpicker" name="occupationId">
								<option value="-1">TODAS</option>
								<c:forEach var="occupation" items="${occupationList}">
									<option value="<c:out value="${occupation.id}"/>"><c:out value="${occupation.name}"/></option>
								</c:forEach>
							</select>
						</div>
						<div id="filterBySpecialtiesBox" class="form-group form-group-job-offer-combos <c:if test="${specialtyList == null}">hide</c:if>">
							<label for="name" class="control-label">Filtrar por Especialidad</label>
							
							<div id="specialtiesSelectBox">
								<select id="specialtiesSelect" class="selectpicker" title="" name="specialtyId">
									<option value="-1">TODAS</option>
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
									<option value="-1">TODAS</option>
									<c:forEach var="task" items="${taskList}">
										<option value="<c:out value="${task.id}"/>"><c:out value="${task.name}"/></option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="form-group">
							<label for="name" class="control-label">Seleccionar Provincias</label>
													
							<select id="geosSelect" class="selectpicker" multiple title="TODAS">
								<option value="-1" selected>TODAS</option>
								<c:forEach var="geo" items="${geoList}">
									<option value="<c:out value="${geo.id}"/>"><c:out value="${geo.name}"/></option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-offset-6 col-md-6">
						<button type="button" class="btn btn-success pull-left" onclick="submitForm();">Lanzar Reporte</button>
					</div>
				</div>
				
				<div id="geoLevelIdsBox"></div>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form:form>
		</section>
		
		<c:if test="${report != null}">
			<div class="row">
				<div class="col-md-12">
					<div class="wrapper">
						<canvas id="chart-0" style="height:400px;padding: 15px;"></canvas>
					</div>
				</div>
			</div>
			
			<div class="row" style="padding: 10px;">
				<div class="col-md-12">
					<label class="chart-resume">Resumen: valores totales del per&iacute;odo</label>
					
					<c:forEach var="reportItem" items="${report.list}">
						<div class="chart-resume-element" style="color: ${reportItem.color};"><b>${reportItem.name}: ${reportItem.total}</b></div>
					</c:forEach>
					
				</div>
			</div>
			
		</c:if>
		
		<script>
			function submitForm() {
				$("#startMonth").val($("#startDateForView").data('datepicker').getFormattedDate('mm'));
				$("#startYear").val($("#startDateForView").data('datepicker').getFormattedDate('yyyy'));
				
				$("#endMonth").val($("#endDateForView").data('datepicker').getFormattedDate('mm'));
				$("#endYear").val($("#endDateForView").data('datepicker').getFormattedDate('yyyy'));
				
				$("#filterForm").submit();
			}
		
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
			
			$('#startDateForView').datepicker({
				autoclose: true,
				format: "MM-yyyy",
			    startView: "months", 
			    minViewMode: "months",
			    language: 'es'
			});
			
			$('#endDateForView').datepicker({
				autoclose: true,
				format: "MM-yyyy",
			    startView: "months", 
			    minViewMode: "months",
			    language: 'es'
			});
			
			<c:if test="${filterForm.startYear != null && filterForm.startMonth != null}">
				$('#startDateForView').datepicker('update', '${filterForm.startMonth}-${filterForm.startYear}');
			</c:if>
			
			<c:if test="${filterForm.endYear != null && filterForm.endMonth != null}">
				$('#endDateForView').datepicker('update', '${filterForm.endMonth}-${filterForm.endYear}');
			</c:if>
		</script>
		
		<c:if test="${report != null}">
			<script>
				var presets = window.chartColors;
				var inputs = {
					min: 20,
					max: 80,
					count: 12,
					decimals: 2,
					continuity: 1
				};
				
				labels = [
				<c:forEach var="monthString" items="${report.textMonths}">
				"${monthString}",
				</c:forEach>
				];
			
				var data = {
					labels: labels,
					datasets: [
					<c:forEach var="reportItem" items="${report.list}">
						{
							borderColor: '${reportItem.color}',
							data: [
								<c:forEach var="numberValue" items="${reportItem.values}">
									${numberValue},
								</c:forEach>
							],
							label: '${reportItem.name}',
							fill: 'false'
						},
					</c:forEach>
					]
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
		</c:if>
		
	</tiles:putAttribute>

</tiles:insertDefinition>

