<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<tiles:insertDefinition name="d2d.dashboard">

	<tiles:putAttribute name="title">
		ABM de Notas
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<section class="content">
			<div class="row">
		        <div class="col-md-10">
					<div class="nav-tabs-custom">
			            <ul class="nav nav-tabs">
			              <li class="active"><a href="#tab_1" data-toggle="tab">Notas</a></li>
			            </ul>
			            <div class="tab-content">
		              		<div class="tab-pane active" id="tab_1">
		              			<table id="notes" class="display" cellspacing="0" width="100%">
							        <thead>
							            <tr>
							                <th>Title</th>
							                <th>Categor&iacute;a</th>
							                <th>Estado</th>
							                <th>Acción</th>
							            </tr>
							        </thead>
							        <tfoot>
							            <tr>
							                <th>Title</th>
							                <th>Categor&iacute;a</th>
							                <th>Estado</th>
							                <th>Acción</th>
							            </tr>
							        </tfoot>
							    </table>
							
							    <div class="box-footer" style="margin-top:15px">
									<a type="submit" class="btn btn-info pull-right" href="<c:url value='/admin/notes/new-note'/>">Nuevo</a>
								</div>
							</div>
						</div>
					</div>
		        </div>
			</div>
		</section>

<script>
$(document).ready(function() {
    $('#notes').DataTable( {
        "ajax": '/d2d/admin/list/bo-notes',
        "language": {
            "search": "Buscar:",
            "info": "Página _PAGE_ de _PAGES_",
            "paginate": {
                "previous": "Previa",
                "next": "Siguiente",
              }
          },
        "columns": [
                    { "data": "title" },
                    { "data": "category" },
                    {
                        "data": "active",
                        "render": function ( data, type, full, meta ) {
                        	 if(data==true){
                        		 return 'Activo';
                        	 } else {
                        		 return 'Inactivo';
                        	 }
                        }
                     },  
                    {
                        "data": "id",
                        "render": function ( data, type, full, meta ) {
                              return '<a href="notes/'+data+'">Editar</a>';
                            }
                     }  
                ],
         "bLengthChange": false        
    } );
} );
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>

