<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<tiles:insertDefinition name="d2d.dashboard">

	<tiles:putAttribute name="title">
		Usuarios
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<section class="content">
			<div class="row">
				<div class="col-md-12">
		
					<div class="box box-info">
						<div class="box-header with-border">
							<h3 class="box-title">Datos del usuario</h3>
							<a href="<c:url value="/admin/public-users" />" class="link-to-right"><< Volver al listado</a>
						</div>
						
						<div class="box-body">
							<div class="col-md-6">
								<h2>Perfil del usuario</h2>
								
								<div class="row">
									<c:choose>
									    <c:when test="${user.avatar != null}">
									        <img style="border:2px solid #000; max-height:180px; margin:15px 0 20px 15px;" src="data:image/jpg;base64,<c:out value='${user.avatar}'/>" />
									    </c:when>    
									    <c:otherwise>
										       <img style="border:2px solid #000; max-height:180px; margin:15px 0 20px 15px;" src="<c:url value="/images/ic_avatar.png" />" />
									    </c:otherwise>
									</c:choose>
								</div>
				                <div class="row">
				                    <div class="col-md-4">
						              <label for="name" class="control-label">Id Usuario:</label>
									</div>
									
									<div class="col-md-8">
										${user.id}
									</div>
								</div>
								<div class="row">
				                	<div class="col-md-4">
						              <label for="name" class="control-label">Nombre y apellido:</label>
									</div>
									<div class="col-md-8">
										${user.name} ${user.lastname}
									</div>
								</div>    
								<div class="row">
				                	<div class="col-md-4">
						              <label for="name" class="control-label">Email:</label>
									</div>
									<div class="col-md-8">
										${user.email}
									</div>
								</div>    
								<div class="row">
				                	<div class="col-md-4">
						              <label for="name" class="control-label">Sistema Operativo:</label>
									</div>
									<div class="col-md-8">
										${user.operativeSystem}
									</div>
								</div>   
								<div class="row">
				                	<div class="col-md-4">
						              <label for="name" class="control-label">Fecha de registro:</label>
									</div>
									<div class="col-md-8">
										${user.creationDate}
									</div>
								</div>
								<div class="row">
				                	<div class="col-md-4">
						              <label for="name" class="control-label">Último Login:</label>
									</div>
									<div class="col-md-8">
										${user.lastLoginDate}
									</div>
								</div>
								<div class="row">
				                	<div class="col-md-4">
						              <label for="name" class="control-label">Estado:</label>
									</div>
									<div class="col-md-8">
										<c:choose>
										    <c:when test="${user.active}">
										        Activo 
										    </c:when>    
										    <c:otherwise>
										        Inactivo 
										    </c:otherwise>
										</c:choose>
									</div>
								</div>
								<div class="row">
				                	<div class="col-md-4">
						              <label for="name" class="control-label">Tipo de Usuario:</label>
									</div>
									<div class="col-md-8">
										<c:choose>
										    <c:when test="${user.userB}">
										        Candidato 
										    </c:when>    
										    <c:otherwise>
										        Oferente
										    </c:otherwise>
										</c:choose>
									</div>
								</div>
								<div class="row">
				                	<div class="col-md-4">
						              <label for="name" class="control-label">Estado Suscripción:</label>
									</div>
									<div class="col-md-8">
									    <c:if test="${user.userB}">
											<c:choose>
										    <c:when test="${user.hasActiveSuscription}">
										        Suscripto 
										    </c:when>    
										    <c:otherwise>
										        No suscripto
										    </c:otherwise>
										</c:choose>
										</c:if>
									</div>
								</div>
								<c:if test="${user.userB}">
									<c:choose>
										<c:when test="${user.hasActiveSuscription}">
											<div class="row">
							                	<div class="col-md-4">
									              <label for="name" class="control-label">Fecha Expiración:</label>
												</div>
												<div class="col-md-8">${user.expirationDate}</div>
											</div>
										</c:when>
									</c:choose>
									
									<h2>Datos Profesionales</h2>

									<div class="row">
					                	<div class="col-md-4">
							              <label for="name" class="control-label">Profesión:</label>
										</div>
										<div class="col-md-8">
											${user.userOccupation}
										</div>
									</div>
									
									<div class="row">
					                	<div class="col-md-4">
							              <label for="name" class="control-label">Matricula:</label>
										</div>
										<div class="col-md-8">
											${user.license}
										</div>
									</div>
									
									<div class="row">
					                	<div class="col-md-4">
							              <label class="control-label">Tipo de Institución:</label>
										</div>
										<div class="col-md-8">
											<c:choose>
												<c:when test="${user.institutionType == 'PUBLIC'}">
													Públicas
												</c:when>
												<c:when test="${user.institutionType == 'PRIVATE'}">
													Privadas
												</c:when>
												<c:when test="${user.institutionType == 'BOTH'}">
													Publicas y Privadas
												</c:when>
												<c:otherwise>
													- No lo cargó -
												</c:otherwise>
											</c:choose>
										</div>
									</div>
									
									<table id="specialties" class="display" cellspacing="0" width="100%">
										<c:forEach items="${user.userSpecialty}" var="specialty">
											<tr>
												<td class="user-specialty-td-one"><span class="user-specialty">${specialty.name}</span></td>
												<td class="user-specialty-td-two">
												    <c:forEach items="${specialty.taskList}" var="tasks">
												    	<span class="user-task">${tasks.name}</span>
													</c:forEach>
												</td>
											</tr>
											<tr>
												<td colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
											</tr>
										</c:forEach>
									</table>
								</c:if>
							</div>

							<div  class="col-md-6">
								<h2>Actividad del usuario</h2>
								
								<table id="users" class="display" cellspacing="0" width="100%">
							        <thead>
							            <tr>
							                <th>Fecha</th>
							                <th>Actividad</th>
							            </tr>
							        </thead>
							        <tfoot>
							            <tr>
							                <th>Fecha</th>
							                <th>Actividad</th>
							            </tr>
							        </tfoot>
							    </table>
							    
							    <c:if test="${user.userB}">
							    	<h2>Zonas</h2>
									
									<c:forEach items="${user.geoLevels}" var="geolevels">
										<div class="row">
											<div class="col-md-12 geolevels">
												${geolevels.name}
											</div>
										</div>
									</c:forEach>
								</c:if>
								
								<hr/>
								
								<h4>Currículum vitae</h4>
								<div class="row">
				                	<div class="col-md-2">
						              <label for="name" class="control-label">PDF:</label>
									</div>
									<div class="col-md-10">
										<!-- IF tiene pdf -->
										<a href="">DESCARGAR PDF</a>
										<!-- Else
											QUEDA EL TEXTO "No cuenta con PDF"
										 -->
									</div>
								</div>
								<div class="row">
				                	<div class="col-md-2">
						              <label for="name" class="control-label">Linkedin:</label>
									</div>
									<div class="col-md-10">
										<!-- IF tiene linkedin -->
										<a href="" target="_blank">Ir al perfil en Linkedin</a>
										<!-- Else
											QUEDA EL TEXTO "No cargó perfil en linkedin"
										 -->
									</div>
								</div>
								<div class="row">
				                	<div class="col-md-2">
						              <label for="name" class="control-label">Texto:</label>
									</div>
									<div class="col-md-10">
										<!-- IF tiene texto guardado como cv 
										Insertar el texto
										<!-- Else
											QUEDA EL TEXTO "No cargó cv"
										 -->
									</div>
								</div>
							
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
		<script>
$(document).ready(function() {
    $('#users').DataTable( {
        "ajax": '<c:url value="/admin/public-users/" />' + "<c:out value='${user.id}'/>" + '/activity',
        "language": {
            "search": "Buscar:",
            "info": "Página _PAGE_ de _PAGES_",
            "paginate": {
                "previous": "Previa",
                "next": "Siguiente",
              }
          },
        "columns": [
					{ "data": "creationDate" },
					{ "data": "log" }
                ],
         "bLengthChange": false        
    } );
} );
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>

