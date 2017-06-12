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
		              <li class="active"><a href="#tab_1" data-toggle="tab">Usuarios de Sistema</a></li>
		              <li><a href="public-users">Usuarios Publicos</a></li>
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
							            </tr>
							        </thead>
							        <tfoot>
							            <tr>
							                <th>Nombre</th>
							                <th>Email</th>
							                <th>Estado</th>
							                <th>Roles</th>
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
        "ajax": '/d2d/admin/list/bo-users',
        "columns": [
                    { "data": "name" },
                    { "data": "email" },
                    { "data": "state" },
                    { "data": "roles" },
                ],
         "bLengthChange": false        
    } );
} );
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>

