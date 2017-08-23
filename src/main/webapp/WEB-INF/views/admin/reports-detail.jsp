<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<tiles:insertDefinition name="d2d.dashboard">

	<tiles:putAttribute name="title">
		Reporte: Detalle de avisos
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<section class="content">
			<div class="row">
				<div class="col-md-12">
					<div class="nav-tabs-custom">
						<div class="tab-content">
							<div class="tab-pane active" id="tab_1">
								<table id="reports-detail" class="display" cellspacing="0"
									width="100%">
									<thead>
										<tr>
											<th>Id</th>
											<th>Tipo de Oferta</th>
											<th>Ocupación</th>
											<th>Especialidad</th>
											<th>Tarea</th>
											<th>Titulo</th>
											<th>Subtitulo</th>
											<th>Comentario</th>
											<th>Detalle del Oferente</th>
											<th>Empresa/Profesional</th>
											<th>Fecha de creación</th>
											<th>Zona</th>
											<th>Fecha de oferta</th>
											<th>Horario de la oferta</th>
											<th>Tipo de institución</th>
											<th>Aplicantes</th>
											<th>Usuario seleccionado</th>
											<th>Estado</th>
											<th>Vacantes</th>
										</tr>
									</thead>
									<tfoot>
										<tr>
											<th>Id</th>
											<th>Tipo de Oferta</th>
											<th>Ocupación</th>
											<th>Especialidad</th>
											<th>Tarea</th>
											<th>Titulo</th>
											<th>Subtitulo</th>
											<th>Comentario</th>
											<th>Detalle del Oferente</th>
											<th>Empresa/Profesional</th>
											<th>Fecha de creación</th>
											<th>Zona</th>
											<th>Fecha de oferta</th>
											<th>Horario de la oferta</th>
											<th>Tipo de institución</th>
											<th>Aplicantes</th>
											<th>Usuario seleccionado</th>
											<th>Estado</th>
											<th>Vacantes</th>
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
    $('#reports-detail').DataTable( {
    	 dom: 'Bfrtip',
         buttons: [
        	 'excelHtml5','csvHtml5','copyHtml5' 
         ],
    	"scrollX": true,
        "ajax": '<c:url value="/admin/reports/all-jobsoffer" />',
        "language": {
            "search": "Buscar:",
            "info": "Página _PAGE_ de _PAGES_",
            "paginate": {
                "previous": "Previa",
                "next": "Siguiente",
              }
          },
        "columns": [
                    { "data": "id" },
                    {
                        "data": "permanent",
                        "render": function ( data, type, full, meta ) {
                        	 if(data==true){
                        		 return 'Permanente';
                        	 } else {
                        		 return 'Temporal';
                        	 }
                        }
                    },  
                    { "data": "occupationName" },
                    { "data": "specialtyName" },
                    { "data": "taskName" },
                    { "data": "title" },
                    { "data": "subTitle" },
                    { "data": "comment" },
                    { "data": "offerent_detail" },
                    { "data": "companyScreenName" },
                    { "data": "creationDate" },
                    { "data": "geoLevelName" },
                    { "data": "offerDate" },
                    { "data": "offerHour" },
                    {
                        "data": "institutionType",
                        "render": function ( data, type, full, meta ) {
                        	 if(data=="PUBLIC"){
                        		 return 'Publica';
                        	 } else {
                        		 return 'Privada';
                        	 }
                        }
                    },  
                    { "data": "applications" },
                    { "data": "jobApplication_detail" },
                    {
                        "data": "status",
                        "render": function ( data, type, full, meta ) {
                        	 if(data=="VACANT"){
                        		 return 'Vacante';
                        	 } else {
                        		 return 'Cerrado';
                        	 }
                        }
                    },  
                    { "data": "vacants" }
                ],
         "bLengthChange": false        
    } );
} );
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>

