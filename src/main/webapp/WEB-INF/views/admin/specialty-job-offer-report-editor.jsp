<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<select id="specialtiesSelect" class="selectpicker specialtiesCombo">
	<option value="-1">TODAS</option>
	<c:forEach var="specialty" items="${specialtyList}">
		<option value="<c:out value="${specialty.id}"/>"><c:out value="${specialty.name}"/></option>
	</c:forEach>
</select>
						
<script>
	$("#specialtiesSelect").selectpicker();
	
	$("#specialtiesSelect").on('changed.bs.select', function (e) {
		loadTasks();
	});
</script>
