<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<tiles:insertDefinition name="d2d.dashboard">

	<tiles:putAttribute name="title">Usuarios de Sistema</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<section class="content">
			<div class="row">
		        <div class="col-md-12">
		          <!-- Custom Tabs -->
		          <div class="nav-tabs-custom">
		            <ul class="nav nav-tabs">
		              <li class="active"><a href="#tab_1" data-toggle="tab">Usuarios de Sistema</a></li>
		            </ul>
		            <div class="tab-content">
		             
		              <div class="tab-pane active" id="tab_1">
		              			<table id="users" class="display" cellspacing="0" width="100%">
							        <thead>
							            <tr>
							                <th>Nombre</th>
							                <th>Email</th>
							                <th>Estado</th>
							                <th>Roles</th>
							                <th>Acción</th>
							            </tr>
							        </thead>
							        <tfoot>
							            <tr>
							                <th>Nombre</th>
							                <th>Email</th>
							                <th>Estado</th>
							                <th>Roles</th>
							                <th>Acción</th>
							            </tr>
							        </tfoot>
							    </table>
							
							    <div class="box-footer" style="margin-top:15px">
									<a type="submit" class="btn btn-info pull-right" href="<c:url value='/admin/new-user'/>">Nuevo</a>
								</div>
		              </div>
		             <!-- /.tab-pane -->
		            </div>
		            <!-- /.tab-content -->
		          </div>
		          <!-- nav-tabs-custom -->
		        </div>
	        </div>
		</section>

<script>
$(document).ready(function() {
	
    $('#users').DataTable( {
        "ajax": '<c:url value="/admin/list/bo-users" />',
        "language": {
            "search": "Buscar:",
            "info": "Página _PAGE_ de _PAGES_",
            "paginate": {
                "previous": "Previa",
                "next": "Siguiente",
              }
          },
        "columns": [
                    { "data": "name" },
                    { "data": "email" },
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
                        "data": "roles",
                        "render": "[, ].description"
                      },
                    {
                        "data": "id",
                        "render": function ( data, type, full, meta ) {
                              return '<a href="users/'+data+'">Editar</a>';
                            }
                     }  
                ],
         "bLengthChange": false        
    } );
} );
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>

