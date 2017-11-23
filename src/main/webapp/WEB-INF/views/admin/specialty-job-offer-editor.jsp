<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<select id="specialtiesSelect" class="selectpicker specialtiesCombo" name="specialtyId">
<option value="-1">Seleccione una Especialidad</option>
	<c:forEach var="specialty" items="${specialtyList}">
		<option value="<c:out value="${specialty.id}"/>"><c:out value="${specialty.name}"/></option>
	</c:forEach>
</select>
						
<script>
	$("#specialtiesSelect").selectpicker();
	
	$("#specialtiesSelect").on('changed.bs.select', function (e) {
		loadTasks();
	});

	<c:if test="${fn:length(specialtyList) == 0}">
		$("#filterBySpecialtiesBox").addClass("hide");
	</c:if>

	<c:if test="${fn:length(specialtyList) > 0}">
		$("#filterBySpecialtiesBox").removeClass("hide");
	</c:if>
	
	$("#filterByTasksBox").addClass("hide");
</script>
