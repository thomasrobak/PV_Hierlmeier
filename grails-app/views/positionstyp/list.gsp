
<%@ page import="hierlmeier.Positionstyp" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'positionstyp.label', default: 'Positionstyp')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="list-positionstyp" class="content scaffold-list">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table class="grails">
				<thead>
					<tr>
					
						<g:sortableColumn property="bezeichnung" title="${message(code: 'positionstyp.bezeichnung.label', default: 'Bezeichnung')}" />
					
						<g:sortableColumn property="preis" title="${message(code: 'positionstyp.preis.label', default: 'Preis')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${positionstypInstanceList}" status="i" var="positionstypInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${positionstypInstance.id}">${fieldValue(bean: positionstypInstance, field: "bezeichnung")}</g:link></td>
					
						<td>${fieldValue(bean: positionstypInstance, field: "preis")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${positionstypInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
