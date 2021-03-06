<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<tiles:insertDefinition name="d2d.dashboard">

	<tiles:putAttribute name="title">
		Administrar de Notas
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<section id="note-editor" class="content">
			<div class="row">
				<div class="col-md-12">
					<div class="nav-tabs-custom">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#tab_1" data-toggle="tab">Datos de la nota</a></li>
							<li><a href="#tab_2" data-toggle="tab">Definir intereses</a></li>
							<li><a href="#tab_3" data-toggle="tab">Sponsors</a></li>
						</ul>
			
						<c:if test="${not empty msg}">
							<div class="msg">${msg}</div>
						</c:if>
						
						<c:if test="${not empty errors}">
						    <c:forEach var="error" items="${errors}">
								<div class="error">${error}</div>
							</c:forEach>
						</c:if>

						<form:form method="POST" modelAttribute="noteForm"
							autocomplete="off"
							action="${pageContext.request.contextPath}/admin/notes/save?${_csrf.parameterName}=${_csrf.token}"
							enctype="multipart/form-data">
							<div class="tab-content">
							
								<div class="tab-pane active" id="tab_1">
									<div class="box-body">
										<div class="form-group">
											<div class="col-sm-2">
												<label for="name" class="control-label">T&iacute;tulo</label>
											</div>
											<div class="col-sm-10">
												<form:input path="title" class="form-control"
													placeholder="T�tulo" autocomplete="false" required="true" />
											</div>
										</div>
										<div class="form-group">
											<div class="col-sm-2">
												<label for="subtitle" class="control-label">Subt&iacute;tulo</label>
											</div>

											<div class="col-sm-10">
												<form:input path="subtitle" class="form-control"
													placeholder="Subt�tulo" autocomplete="false"
													required="true" />
											</div>
										</div>
										<div class="form-group">
											<div class="col-sm-2">
												<label for="subtitle" class="control-label">Categor&iacute;a</label>
											</div>

											<div class="col-sm-10">
												<form:select path="category" class="form-control"
													required="true">
													<form:option value="NONE"> -- Elegir Categor&iacute;a --</form:option>
													<form:options items="${categoryList}"></form:options>
												</form:select>
											</div>
										</div>
										<div class="form-group">
											<div class="col-sm-2">
												<label for="state" class="control-label">Estado</label>
											</div>
											<div class="col-sm-10">
												<form:radiobutton path="active" value="true" element="span class='radio'" />Activo <br>
												<form:radiobutton path="active" value="false" element="span class='radio'" />Inactivo
											</div>
										</div>

										<div class="form-group">
											<div class=col-sm-2>
												<label for="content" class="control-label">Publicaci&oacute;n</label>
											</div>
											<div class=col-sm-10>
												<div class="input-group date" id="publishDatePicker">
													<div class="input-group-addon">
														<i class="fa fa-calendar"></i>
													</div>
													<input type="text" class="form-control pull-right"
														id="publishingDateForView" name="publishingDateForView"
														value="${noteForm.publishingDateForView}"
														data-inputmask="'alias': 'dd/mm/yyyy'" data-mask>
												</div>
											</div>
										</div>

										<div class="form-group">
											<div class=col-sm-2>
												<label for="content" class="control-label">Expiraci&oacute;n</label>
											</div>
											<div class=col-sm-10>
												<div class="input-group date">
													<div class="input-group-addon">
														<i class="fa fa-calendar"></i>
													</div>
													<input type="text" class="form-control pull-right"
														id="expirationDateForView" name="expirationDateForView"
														value="${noteForm.expirationDateForView}"
														data-inputmask="'alias': 'dd/mm/yyyy'" data-mask>
												</div>
											</div>
										</div>

										<div class="form-group">
											<div class=col-sm-2>
												<label for="content" class="control-label">Contenido</label>
											</div>

											<div class="col-sm-10">
												<form:textarea path="content" class="form-control"
													placeholder="Contenido" autocomplete="false"
													required="true" rows="10" cssStyle="margin-bottom: 10px;" />
											</div>
										</div>
										<div class="form-group">
											<div class="col-sm-2">
												<label for="image" class="control-label">Imagen</label>
											</div>

											<div class="col-sm-10">
												<input class="form-control" type="file" name="file">
											</div>
											<div class="col-sm-offset-2 col-sm-10">
												<c:choose>
													<c:when
														test="${noteForm.image != null && !noteForm.image.equals('')}">
														<div>Imagen actual</div>
														<img alt="image"
															src="data:image/png;base64,${noteForm.image}"
															style="max-width: 100%;">
													</c:when>
												</c:choose>
											</div>
										</div>
									</div>
								</div>

								<div class="tab-pane" id="tab_2">
									<div class="box-body">
										<label for="name" class="control-label">Elegir
											Ocupaciones</label> <select id="occupationsSelect"
											class="selectpicker" multiple>
											<c:forEach var="occupation" items="${occupationList}">
												<option value="<c:out value="${occupation.id}"/>"><c:out value="${occupation.name}"/></option>
											</c:forEach>
										</select>

										<div id="specialtiesBox" style="margin-top: 20px;">
											<label for="name" class="control-label">Elegir
												Especialidades</label>
											<c:forEach var="obj" items="${specialtiesLists}">
												<c:choose>
													<c:when test="${empty obj.value[0].name}">
														<div style="display: none;">
															<div id="specialtiesBox-${obj.key.id}"
																style="margin-top: 10px;"></div>
															<div class="row">
																<div class="col-md-2">
																	<span>${obj.key.name}</span>
																</div>
																<div class="col-md-10">
																	<select id="specialtiesSelect-${obj.key.id}"
																		class="selectpicker specialtiesCombo" multiple>
																		<c:forEach var="specialty" items="${obj.value}">
																			<option value="<c:out value="${specialty.id}"/>"><c:out value="${specialty.name}"/></option>
																		</c:forEach>
																	</select>
																</div>
															</div>
														</div>
													</c:when>
													<c:otherwise>
														<div>
															<div id="specialtiesBox-${obj.key.id}"
																style="margin-top: 10px;"></div>
															<div class="row">
																<div class="col-md-2">
																	<span>${obj.key.name}</span>
																</div>
																<div class="col-md-10">
																	<select id="specialtiesSelect-${obj.key.id}"
																		class="selectpicker specialtiesCombo" multiple>
																		<c:forEach var="specialty" items="${obj.value}">
																			<option value="<c:out value="${specialty.id}"/>"><c:out value="${specialty.name}"/></option>
																		</c:forEach>
																	</select>
																</div>
															</div>
														</div>
													</c:otherwise>
												</c:choose>
												<script>
													$('#specialtiesSelect-${obj.key.id}').selectpicker('val', ${noteForm.specialties});
												</script>
											</c:forEach>

											<script>
												$(".specialtiesCombo").on('changed.bs.select', function (e) {
													loadSpecialtiesInput();
												});
											</script>
										</div>
									</div>

									<div id="occupationIdsBox"></div>
									<div id="specialtyIdsBox"></div>
								</div>
								<div class="tab-pane" id="tab_3">
									<div class="box-body">
										<h3>Seleccionar Sponsors de la publicaci�n</h3>
										<div class="row">
										<c:choose>
										    <c:when test="${noteForm.sendUserBAllSponsor eq true}">
												<input id="sendUserBAllSponsor" type="checkbox" checked="checked" name="sendUserBAllSponsor" onchange="alluserAction(this)"/>
										    </c:when>    
										    <c:otherwise>
												<input id="sendUserBAllSponsor" type="checkbox" name="sendUserBAllSponsor" onchange="alluserAction(this)"/>
										    </c:otherwise>
										</c:choose>
											<span><b>Enviar a todos los Sponsors</b></span>
										</div>
										<div id="sponsorsBox" style="display: block;">
											<c:forEach var="obj" items="${sponsorList}">
												<div class="row">
													<div>
														<c:choose>
														    <c:when test="${obj.active eq true}">
																<input type="checkbox" name="sponsors" checked="checked" value="${obj.id}" />
														    </c:when>    
														    <c:otherwise>
																<input type="checkbox" name="sponsors" value="${obj.id}" />
														    </c:otherwise>
														</c:choose>
														<span>${obj.name}</span>
													</div>
												</div>
											</c:forEach>
										</div>
										<hr style="width:100%; color: black; height:1px; background-color:black;" />
										
										<h4>Configuraciones adicionales</h4>
										<div class="row">
										<c:choose>
										    <c:when test="${noteForm.sendUserB eq true}">
												<input type="checkbox" name="sendUserB" checked="checked"/>
										    </c:when>    
										    <c:otherwise>
												<input type="checkbox" name="sendUserB"/>
										    </c:otherwise>
										</c:choose>
											<span>Enviar a los Usuarios B sin Sponsor</span>
										</div>
										<div class="row">
										<c:choose>
										    <c:when test="${noteForm.sendUserA eq true}">
												<input type="checkbox" name="sendUserA" checked="checked"/>
										    </c:when>    
										    <c:otherwise>
												<input type="checkbox" name="sendUserA"/>
										    </c:otherwise>
										</c:choose>
											<span>Enviar a los Usuarios A</span>
										</div>
									</div>
								</div>
							</div>
							<div class="box-footer">
								<button type="submit" class="btn btn-info pull-right">Guardar y Publicar</button>
							</div>
							<form:input path="id" type="hidden" />
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />

						</form:form>
					</div>
				</div>
			</div>
		</section>
		
		<script>
			$('#publishingDateForView').datepicker({
				autoclose: true,
				endDate: 'now',
				format: "dd-mm-yyyy"
			});
			$('#expirationDateForView').datepicker({
				autoclose: true,
				format: "dd-mm-yyyy"
			});
			
			$('#occupationsSelect').selectpicker('val', ${noteForm.occupations});

			arraySpecialtiesIds = [];
			lastArrayOccupationIds = [];
			
			$("#occupationsBox").empty();
			
			$('#occupationsSelect').on('changed.bs.select', function (e) {
				
				$("#occupationIdsBox").empty();
				arrayOccupationIds = $("#occupationsSelect").val();

				//Eliminar Box de Especialidades que no est�n elegidas
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
			
			$(document).ready(function() {
				
				alluserAction($("#sendUserBAllSponsor").get(0));
				arrayOccupationIds = $("#occupationsSelect").val();
	
				for (var i = 0; i < arrayOccupationIds.length; i++) {
					id = arrayOccupationIds[i]
					$("#occupationIdsBox").append('<input type="hidden" name="occupations" value="' + id + '">');
				}
				
				loadSpecialtiesInput();
			});
			

			function alluserAction(checkboxElem) {
				  if (checkboxElem.checked) {
					  $("#sponsorsBox").css('display','none');
				  } else {
					  $("#sponsorsBox").css('display','block');
				  }
			}

			
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>

