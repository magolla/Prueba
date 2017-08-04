<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<select id="tasksSelect" class="selectpicker specialtiesCombo">
	<option value="-1">TODAS</option>
	<c:forEach var="task" items="${taskList}">
		<option value="<c:out value="${task.id}"/>"><c:out value="${task.name}"/></option>
	</c:forEach>
</select>
						
<script>
	$("#tasksSelect").selectpicker();
	
	$("#tasksSelect").on('changed.bs.select', function (e) {
	});
</script>
