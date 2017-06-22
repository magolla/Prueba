<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<tiles:insertDefinition name="d2d.dashboard">

	<tiles:putAttribute name="title">
		Usuarios
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<section class="content">
			<div class="row">
		        <div class="col-md-10">
		          <!-- Custom Tabs -->
		          <div class="nav-tabs-custom">
		            <ul class="nav nav-tabs">
		              <li><a href="users">Usuarios de Sistema</a></li>
		              <li  class="active"><a href="#tab_1" >Usuarios Publicos</a></li>
		            </ul>
		            <div class="tab-content">
		             
		              <div class="tab-pane active" id="tab_1" style="margin-top:15px">
		              
		              			<table id="users" class="display" cellspacing="0" width="100%">
							        <thead>
							            <tr>
							                <th>Nombre publico</th>
							                <th>Email</th>
							                <th>Estado</th>
							                <th>Tipo Suscripción</th>
							                <th>Estado Suscripción</th>
							                <th>Fecha Expiración</th>
							                <th>Acción</th>
							            </tr>
							        </thead>
							        <tfoot>
							            <tr>
							                <th>Nombre publico</th>
							                <th>Email</th>
							                <th>Estado</th>
							                <th>Tipo Suscripción</th>
							                <th>Estado Suscripción</th>
							                <th>Fecha Expiración</th>
							                <th>Acción</th>
							            </tr>
							        </tfoot>
							    </table>
							
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
        "ajax": '/d2d/admin/list/public-users',
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
                    { "data": "state" },
                    { "data": "subscriptionType" },
                    { "data": "subscriptionState" },
                    { "data": "expirationDate" },
                    {
                        "data": "id",
                        "render": function ( data, type, full, meta ) {
                              return '<a href="users/'+data+'">Ver Datos</a>';
                            }
                     }  
                ],
         "bLengthChange": false        
    } );
} );
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>

