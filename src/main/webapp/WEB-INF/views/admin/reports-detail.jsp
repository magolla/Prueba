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
											<th>Cantidad de Matches en el momento de la creacion</th>
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
											<th>Cantidad de Matches en el momento de la creacion</th>
										</tr>
									</tfoot>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			
					<!-- Modal de Candidatos -->
		<div id="candidatesModal" class="modal fade bd-example-modal-lg" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
		  <div class="modal-dialog modal-lg">
		    <!-- Modal content-->
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal">&times;</button>
		        <h4 class="modal-title">Seleccionar usuario</h4>
		      </div>
		      <div id="bodyModalBootstrap" class="modal-body">
				<table id="candidates" class="display" cellspacing="0" width="100%">
					<thead>
					    <tr>
					        <th>ID</th>
					        <th>N&uacute;mero Tel</th>
					        <th>Nombre</th>
					        <th>Apellido</th>
					        <th>Email</th>
					        <th>Profesion</th>
					    </tr>
					</thead>
					<tfoot>
					    <tr>
					        <th>ID</th>
					        <th>N&uacute;mero Tel</th>
					        <th>Nombre</th>
					        <th>Apellido</th>
					        <th>Email</th>
					        <th>Profesion</th>
					    </tr>
					</tfoot>
				</table>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-danger" onClick="clearTable()" data-dismiss="modal">Cerrar</button>
		      </div>
		    </div>
		  </div>
		</div>
			
			
		</section>

		<script>
		
		
// 		Esta funcion se usa para aplicar el ellipsis en Datatable
		 $.fn.dataTable.render.ellipsis = function ( cutoff, wordbreak, escapeHtml ) {
		        var esc = function ( t ) {
		            return t
		                .replace( /&/g, '&amp;' )
		                .replace( /</g, '&lt;' )
		                .replace( />/g, '&gt;' )
		                .replace( /"/g, '&quot;' );
		        };
		     
		        return function ( d, type, row ) {
		            // Order, search and type get the original data
		            if ( type !== 'display' ) {
		                return d;
		            }
		     
		            if ( typeof d !== 'number' && typeof d !== 'string' ) {
		                return d;
		            }
		     
		            d = d.toString(); // cast numbers
		     
		            if ( d.length < cutoff ) {
		                return d;
		            }
		     
		            var shortened = d.substr(0, cutoff-1);
		     
		            // Find the last white space character in the string
		            if ( wordbreak ) {
		                shortened = shortened.replace(/\s([^\s]*)$/, '');
		            }
		     
		            // Protect against uncontrolled HTML input
		            if ( escapeHtml ) {
		                shortened = esc( shortened );
		            }
		     
		            return '<span class="ellipsis" title="'+esc(d)+'">'+shortened+'&#8230;</span>';
		        };
		    };
		
		
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
                    {
                    	"data": "applications",
                        "render": function ( data, type, full, meta ) {
	                        	if(data == 0) {
	                        		return "";
	                        	} else {
	                        		return "<button class=\"btn btn-info\" onClick=showCandidates(" + full.id + ")  data-toggle=\"modal\" data-target=\"#candidatesModal\" >Ver candidatos: " + data + "</button>";
	                        	}
                            }
                    },
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
                    { "data": "vacants" },
                    { "data": "matchesAtCreation" }
                ],
                "columnDefs": [
                               {
                                   targets: 7,
                                   render: $.fn.dataTable.render.ellipsis(50)
                               }
                           ],
         "bLengthChange": false        
    } );
    
    

    

    $('#candidatesModal').on('hidden.bs.modal', function () {
    	var table = $('#candidates').DataTable();
    	table.destroy();

    })
    
} );


function showCandidates(data) {

    $('#candidates').DataTable( {
    	"ajax": "/d2d/admin/reports/getCandidates/" + data,
        "language": {
            "search": "Buscar:",
            "info": "P&aacute;gina _PAGE_ de _PAGES_",
            "paginate": {
                "previous": "Previa",
                "next": "Siguiente",
              }
          },
        "columns": [
					{ "data": "id" },
					{ "data": "mobilePhone" },
                    { "data": "name" },
                    { "data": "lastName" },
                    { "data": "email" },
                    { "data": "occupation" }
                ],
         "bLengthChange": false        
    } );
	
} 




</script>
	</tiles:putAttribute>
</tiles:insertDefinition>

