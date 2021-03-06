
<%@ page import="hierlmeier.Tier" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'tier.label', default: 'Tier')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="list-tier" class="content scaffold-list">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table class="grails">
				<thead>
					<tr>
					
						<g:sortableColumn property="bezeichnung" title="${message(code: 'tier.bezeichnung.label', default: 'Bezeichnung')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${tierInstanceList}" status="i" var="tierInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${tierInstance.id}">${fieldValue(bean: tierInstance, field: "bezeichnung")}</g:link></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${tierInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
