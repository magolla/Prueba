<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>

<tiles:insertDefinition name="d2d.login">
	<tiles:putAttribute name="body">
	
		<section class="content">
			<div class="row">
				<div class="col-md-offset-3 col-md-6">
		
					<div class="box box-info">
						<div class="box-header with-border">
							<h3 class="box-title">Login Form</h3>
						</div>
						
						<c:if test="${not empty error}">
							<div class="error">${error}</div>
						</c:if>
						<c:if test="${not empty msg}">
							<div class="msg">${msg}</div>
						</c:if>
		
						<form name='loginForm' action="<c:url value='/admin/login' />" method='POST'>
							<div class="box-body">
								<div class="form-group">
									<label for="username" class="col-sm-2 control-label">Email</label>
		
									<div class="col-sm-10">
										<input type="email" class="form-control" id="username" name="username" placeholder="Email">
									</div>
								</div>
								<div class="form-group">
									<label for="password" class="col-sm-2 control-label">Password</label>
		
									<div class="col-sm-10">
										<input type="password" class="form-control" id="password" name="password"  placeholder="Password">
									</div>
								</div>
								
							</div>
							
							<div class="box-footer">
								<button type="submit" class="btn btn-info pull-right">Sign in</button>
							</div>
							
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						</form>
					</div>
		
				</div>
			</div>
		</section>
		
	</tiles:putAttribute>
</tiles:insertDefinition>