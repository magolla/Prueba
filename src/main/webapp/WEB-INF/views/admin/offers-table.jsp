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
								<table id="offers-table" class="display" cellspacing="0"
									width="100%">
									<thead>
										<tr>
											<th>Estado</th>
											<th>Tipo de Oferta</th>
											<th>Ocupaci&oacuten</th>
											<th>Especialidad</th>
											<th>Tarea</th>
											<th>Titulo</th>
											<th>Subtitulo</th>
											<th>Comentario</th>
											<th>Detalle del Oferente</th>
											<th>Fecha de creaci&oacuten</th>
											<th>Zona</th>
											<th>Fecha Oferta</th>
											<th>Editar</th>
											<th>Eliminar</th>
										</tr>
									</thead>
									<tfoot>
										<tr>
											<th>Estado</th>
											<th>Tipo de Oferta</th>
											<th>Ocupaci&oacuten</th>
											<th>Especialidad</th>
											<th>Tarea</th>
											<th>Titulo</th>
											<th>Subtitulo</th>
											<th>Comentario</th>
											<th>Detalle del Oferente</th>
											<th>Fecha de creaci&oacuten</th>
											<th>Zona</th>
											<th>Fecha Oferta</th>
											<th>Editar</th>
											<th>Eliminar</th>
										</tr>
										
										
									</tfoot>
								</table>
								
								<div class="box-footer" style="margin-top:15px">
										<select id="offerStatus">
										  <option value="All">Todos</option>
										  <option value="Closed">Cerrado</option>
										  <option value="Vacant">Vacante</option>
										</select>
								</div>
								
								<div class="box-footer" style="margin-top:15px">
										<select id="offerJobType">
										  <option value="All">Todos</option>
										  <option value="Temporal">Temporales</option>
										  <option value="Permanent">Permanentes</option>
										</select>
								</div>
								<div class="box-footer" style="margin-top:15px">
									<a type="submit" class="btn btn-info pull-right" href="<c:url value='/admin/new-offer'/>">Nuevo</a>
								</div>
							</div>
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
    $('#offers-table').DataTable( {
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
        			{ "data": "status" },
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
                    { "data": "creationDate" },
                    { "data": "geoLevelName" },
                    { data: null, render: function ( data, type, row ) {
                        return data.offerDate + ' ' + data.offerHour;
                    } 
                    },
                    {"defaultContent": "<button id='editButton' >Editar</button>"},
                    {
                        "data": "status",
                        "render": function ( data, type, full, meta ) {
                        	 if(data=='CLOSED'){
                        		 return '';
                        	 } else {
                        		 return "<button id='deleteButton' >Eliminar</button>";
                        	 }
                        }
                    }
                ],
                "columnDefs": [
                    {
                        "targets": [ 0 ],
                        "visible": false,
                        "searchable": true
                    },
                    {
                        targets: 7,
                        render: $.fn.dataTable.render.ellipsis(50)
                    },
                    {
                        targets: 6,
                        render: $.fn.dataTable.render.ellipsis(35)
                    },
                    {
                        targets: 5,
                        render: $.fn.dataTable.render.ellipsis(35)
                    },
                    { "width": "150px", "targets": 7 }
                ],
         "bLengthChange": false        
    } );
    
    
	//Se inicia el filtro de ofertas con status VACANTE
    var table = $('#offers-table').DataTable();
    table.column(0).search('VACANT', true, false).draw();
    $("#offerStatus").val('Vacant');
    
	//Se agrega funcion en el change del select para que filtre segun VACANTE, CERRADO y TODOS
   $('#offerStatus').on( 'change', function (selection) {
	   if(this.value == 'Vacant') {
		   table.column(0).search('VACANT', true, false).draw();
	   } else if(this.value == 'Closed') {
		   table.column(0).search('CLOSED', true, false).draw();   
	   } else {
		   table.column(0).search('', true, false).draw();   
	   }
    } );
	
	$('#offerJobType').on( 'change', function (selection) {
	   if(this.value == 'Permanent') {
		   table.column(1).search('Permanente', true, false).draw();
	   } else if(this.value == 'Temporal') {
		   table.column(1).search('Temporal', true, false).draw();   
	   } else {
		   table.column(1).search('', true, false).draw();   
	   }
    } );
	
    
    
    $('#offers-table tbody').on( 'click', 'button', function (e) {
    	
    	var selectedRow = $(this).parents('tr');
    	
    	e.preventDefault();    
        var table = $('#offers-table').DataTable();
        var rowData = table.row($(this).closest('tr')).data()	;
    	
    	if(this.id == 'editButton'){ 
    		console.log("Tocaste Edit");
    	} else {
            $.get('/d2d/admin/deleteOffer',{ offerId: rowData.id }, function(data, textStatus, xhr) {
            	if(xhr.status == 200) {
					table.row(selectedRow).remove().draw();
            	} 
            }).fail(function() {
            });
    	}
    } );
    
} );
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>

