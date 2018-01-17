<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<tiles:insertDefinition name="d2d.dashboard">

	<tiles:putAttribute name="title">
		Usuarios
	</tiles:putAttribute>

	<tiles:putAttribute name="body">
		<section class="content">
			<div class="row">
		        <div class="col-md-12">
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
							                <th>ID</th>
							                <th>Número Tel</th>
							                <th>Nombre</th>
							                <th>Apellido</th>
							                <th>Email</th>
							                <th>Estado</th>
							                <th>Tipo Suscripción</th>
							                <th>Estado Suscripción</th>
							                <th>Acción</th>
							            </tr>
							        </thead>
							        <tfoot>
							            <tr>
							                <th>ID</th>
							                <th>Número Tel</th>
							                <th>Nombre</th>
							                <th>Apellido</th>
							                <th>Email</th>
							                <th>Estado</th>
							                <th>Tipo Suscripción</th>
							                <th>Estado Suscripción</th>
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
   	 dom: 'Bfrtip',
     buttons: [
    	 'excelHtml5','csvHtml5','copyHtml5' 
     ],
        "ajax": '<c:url value="/admin/list/public-users" />',
        "processing": true, // for show progress bar 
        "serverSide": true, // for process server side 
        "iDisplayLength": 20, 
        "start":0, 
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
					{ "data": "mobilePhone" },
                    { "data": "name" },
                    { "data": "lastname" },
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
                         "data": "userB",
                         "render": function ( data, type, full, meta ) {
                         	 if(data==true){
                         		 return 'Candidato';
                         	 } else {
                         		 return 'Oferente';
                         	 }
                         }
                      },
                      {
                          "data": "hasActiveSuscription",
                          "render": function ( data, type, full, meta ) {
                        	 if(full.userB==true){ 
	                          	 if(data==true){
	                          		 return 'Suscripto';
	                          	 } else {
	                          		 return 'No suscripto';
	                          	 }
                        	 }

                          	 return "-";
                          }
                       },
                    //{ "data": "expirationDate" },
                    {
                        "data": "id",
                        "render": function ( data, type, full, meta ) {
                              return '<a href="public-users/'+data+'">Ver Datos</a>';
                            }
                     }  
                ],
         "bLengthChange": false        
    } );
} );
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>

