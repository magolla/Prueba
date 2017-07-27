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
								              <label for="name" class="control-label">Nombre:</label>
											</div>
											<div class="col-md-8">
												${user.name}
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
								              <label for="name" class="control-label">�ltimo Login:</label>
											</div>
											<div class="col-md-8">
												${user.lastLoginDate}
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
								              <label for="name" class="control-label">Estado Suscripci�n:</label>
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
										<div class="row">
						                	<div class="col-md-4">
								              <label for="name" class="control-label">Fecha Expiraci�n:</label>
											</div>
											<div class="col-md-8">
											      <c:if test="${user.userB}">
													${user.expirationDate}
												</c:if>
												
											</div>
										</div>		
										
						               <div class="row">
						                	<div class="col-md-4">
								              <label for="name" class="control-label">Avatar:</label>
											</div>
											<div class="col-md-8">
											   <img src="data:image/jpg;base64,<c:out value='${user.avatar}'/>" />
												
											</div>
										</div>								
							</div>	
							<div  class="col-md-6">
							
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
							
							</div>			
						</div>
					</div>	
		
				</div>
			</div>
		</section>
		<script>
$(document).ready(function() {
    $('#users').DataTable( {
        "ajax": '/d2d/admin/public-users/' + "<c:out value='${user.id}'/>" + '/activity',
        "language": {
            "search": "Buscar:",
            "info": "P�gina _PAGE_ de _PAGES_",
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

