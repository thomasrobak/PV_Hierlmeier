
<%@ page import="hierlmeier.Zahlung" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'zahlung.label', default: 'Zahlung')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="list-zahlung" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="betrag" title="${message(code: 'zahlung.betrag.label', default: 'Betrag')}" />
					
						<th><g:message code="zahlung.kunde.label" default="Kunde" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${zahlungInstanceList}" status="i" var="zahlungInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${zahlungInstance.id}">${fieldValue(bean: zahlungInstance, field: "betrag")}</g:link></td>
					
						<td>${fieldValue(bean: zahlungInstance, field: "kunde")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${zahlungInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
