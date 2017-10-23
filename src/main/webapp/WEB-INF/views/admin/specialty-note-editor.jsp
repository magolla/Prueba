<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:choose>
  <c:when test="${empty specialtyList[0].name}">
    <div id="specialtiesBox-${occupation.id}" style="display: none;" style="margin-top: 10px;">
	<span>${occupation.name}</span>
		<select id="specialtiesSelect-${occupation.id}" class="selectpicker specialtiesCombo" multiple>
			<c:forEach var="specialty" items="${specialtyList}">
				<option value="<c:out value="${specialty.id}"/>"><c:out value="${specialty.name}"/></option>
			</c:forEach>
		</select>
	</div>		
  </c:when>
  <c:otherwise>
    <div id="specialtiesBox-${occupation.id}" style="margin-top: 10px;">
	<span>${occupation.name}</span>
		<select id="specialtiesSelect-${occupation.id}" class="selectpicker specialtiesCombo" multiple>
			<c:forEach var="specialty" items="${specialtyList}">
				<option value="<c:out value="${specialty.id}"/>"><c:out value="${specialty.name}"/></option>
			</c:forEach>
		</select>
	</div>		
  </c:otherwise>
</c:choose>


			
							
<script>
	$("#specialtiesSelect-${occupation.id}").selectpicker();
	
	$("#specialtiesSelect-${occupation.id}").on('changed.bs.select', function (e) {
		loadSpecialtiesInput();
	});
</script>
