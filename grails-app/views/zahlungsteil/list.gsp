
<%@ page import="hierlmeier.Zahlungsteil" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'zahlungsteil.label', default: 'Zahlungsteil')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="list-zahlungsteil" class="content scaffold-list">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table class="grails">
				<thead>
					<tr>
					
						<th><g:message code="zahlungsteil.beleg.label" default="Beleg" /></th>
					
						<g:sortableColumn property="betrag" title="${message(code: 'zahlungsteil.betrag.label', default: 'Betrag')}" />
					
						<th><g:message code="zahlungsteil.zahlung.label" default="Zahlung" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${zahlungsteilInstanceList}" status="i" var="zahlungsteilInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${zahlungsteilInstance.id}">${fieldValue(bean: zahlungsteilInstance, field: "beleg")}</g:link></td>
					
						<td>${fieldValue(bean: zahlungsteilInstance, field: "betrag")}</td>
					
						<td>${fieldValue(bean: zahlungsteilInstance, field: "zahlung")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${zahlungsteilInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
