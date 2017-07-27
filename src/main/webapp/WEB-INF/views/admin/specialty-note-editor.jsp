<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="specialtiesBox-${occupation.id}" style="margin-top: 10px;">
	<span>${occupation.name}</span>
	<select id="specialtiesSelect-${occupation.id}" class="selectpicker specialtiesCombo" multiple>
		<c:forEach var="specialty" items="${specialtyList}">
			<option value="<c:out value="${specialty.id}"/>"><c:out value="${specialty.name}"/></option>
		</c:forEach>
	</select>
</div>					
							
<script>
	$("#specialtiesSelect-${occupation.id}").selectpicker();
	
	$("#specialtiesSelect-${occupation.id}").on('changed.bs.select', function (e) {
		loadSpecialtiesInput();
	});
</script>
