<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<tiles:insertDefinition name="d2d.dashboard">

	<tiles:putAttribute name="title">
		Reporte de suscripciones
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
	
		<section class="content">
			<form:form method="POST" modelAttribute="filterForm" autocomplete="off" action="${pageContext.request.contextPath}/admin/reports/subscription?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data">
				<div class="row">
					<div class="col-lg-6">
						<div class="form-group">
							<label for="name" class="control-label">Seleccionar provincias</label>
													
							<select id="geosSelect" class="selectpicker" multiple>
								<c:forEach var="geo" items="${geoList}">
									<option value="<c:out value="${geo.id}"/>"><c:out value="${geo.name}"/></option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="col-lg-6">
						<div class="form-group">
							<label for="name" class="control-label">&nbsp;</label>
									
							<div class="checkbox checkbox-primary">
								<input id="checkbox-android" name="android" type="checkbox" value="true" ${filterForm.android ? "checked" : ""}>
								<label for="checkbox-android">
									Suscripciones pagas android mercado pago
								</label>
							</div>
							<div class="checkbox checkbox-primary">
								<input id="checkbox-sponsor" name="sponsor" type="checkbox" value="true" ${filterForm.sponsor ? "checked" : ""}>
								<label for="checkbox-sponsor">
									Suscripciones por sponsor
								</label>
							</div>
							<div class="checkbox checkbox-primary">
								<input id="checkbox-inapp" name="ios" type="checkbox" value="true" ${filterForm.ios ? "checked" : ""}>
								<label for="checkbox-inapp">
									Suscripciones inapp iOS
								</label>
							</div>
							<div class="checkbox checkbox-primary">
								<input id="checkbox-free" name="free" type="checkbox" value="true" ${filterForm.free ? "checked" : ""}>
								<label for="checkbox-free">
									Suscripciones gratuitas dtd
								</label>
							</div>
						</div>
						
						<button type="submit" class="btn btn-success pull-left">Lanzar Reporte</button>
					</div>
					
				</div>
				
				<div id="geoLevelIdsBox"></div>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form:form>
		</section>
		
		<div class="line-chart-separator"></div>
		<c:if test="${subscriptionList != null}">
			<div class="row">
				<div class="col-md-offset-1 col-md-4">
					<div id="canvas-holder" class="center-block">
				        <canvas id="chart-area" ></canvas>
				    </div>
				</div>
				<div class="col-md-offset-1 col-md-6">
					<label class="chart-resume">Resumen</label>
					
					<div class="chart-resume-element"><b>Usuarios registrados: ${registeredUsers}</b></div>
					<!--div class="chart-resume-element"><b>Suscripciones activas: ${activeSubscriptions}</b></div-->
					
					<c:forEach var="obj" items="${subscriptionList}">
		        		<div class="chart-resume-element" style="color: ${obj.color}"><b>${obj.name}: ${obj.quantity}</b></div>
					</c:forEach>
				</div>
			</div>
		</c:if>
	    
	<script>
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
		
		loadGeosInput();
	</script>
	
	<c:if test="${subscriptionList != null}">	
	    <script>
	    var config = {
	        type: 'pie',
	        data: {
	            datasets: [{
	                data: [
	                	<c:forEach var="obj" items="${subscriptionList}">
	                		${obj.quantity},
						</c:forEach>
	                ],
	                
	                
	                backgroundColor: [
	                	<c:forEach var="obj" items="${subscriptionList}">
			        		"${obj.color}",
						</c:forEach>
		            ],
	                
	                label: 'Dataset 1'
	            }],
	            labels: [
	            	<c:forEach var="obj" items="${subscriptionList}">
		        		"${obj.name}",
					</c:forEach>
	            ]
	        },
	        options: {
	            responsive: true,
	            legend: {
	                display: false
	            }
	        }
	    };
	
	    window.onload = function() {
	        var ctx = document.getElementById("chart-area").getContext("2d");
	        window.myPie = new Chart(ctx, config);
	    };
	
	    </script>
    </c:if>
		
	</tiles:putAttribute>

</tiles:insertDefinition>

