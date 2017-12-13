<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<select id="specialtiesSelect" class="selectpicker specialtiesCombo" title="Seleccione una especialidad" name="specialtyId">
	<c:forEach var="specialty" items="${specialtyList}">
		<option value="<c:out value="${specialty.id}"/>"><c:out value="${specialty.name}"/></option>
	</c:forEach>
</select>
						
<script>
	$("#specialtiesSelect").selectpicker();
	
	$("#specialtiesSelect").on('changed.bs.select', function (e) {
		var occupation_name = $("#occupationsSelect option:selected").text();
		var specialty_name = $("#specialtiesSelect option:selected").text();
		if(specialty_name != '' && specialty_name != 'Seleccione una Especialidad') {
			if(permanent) {
				$('#previewDate').text("Busco:" + occupation_name + ', ' + specialty_name);				
			} else {
				$('#previewInterest').text(occupation_name + ', ' + specialty_name);	
			}
		}
		loadTasks();
	});

	<c:if test="${specialtyList[0].name == ''}">
		$("#filterBySpecialtiesBox").addClass("hide");
		$("#specialtiesSelect").val("${specialtyList[0].id}");
		loadTasks();
	</c:if>

	<c:if test="${specialtyList[0].name != ''}">
		$("#filterBySpecialtiesBox").removeClass("hide");
	</c:if>
	
	$("#filterByTasksBox").addClass("hide");
</script>
