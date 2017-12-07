<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<select id="tasksSelect" class="selectpicker specialtiesCombo" Title="Seleccione una Tarea"  name="taskId" required oninvalid="this.setCustomValidity('Debes seleccionar una Tarea')" >
	<c:forEach var="task" items="${taskList}">
		<option value="<c:out value="${task.id}"/>"><c:out value="${task.name}"/></option>
	</c:forEach>
</select>
						
<script>
	$("#tasksSelect").selectpicker();
	
	$("#tasksSelect").on('changed.bs.select', function (e) {
		
		var task_name = $("#tasksSelect option:selected").text();
		if(task_name != '' && task_name != 'Seleccione una Especialidad') {
			$('#previewTask').text('Para trabajos de ' + task_name)
		}
		
	});
	
	<c:if test="${fn:length(taskList) == 0}">
		$("#filterByTasksBox").addClass("hide");
	</c:if>
	
	<c:if test="${fn:length(taskList) > 0}">
		$("#filterByTasksBox").removeClass("hide");
	</c:if>
</script>
