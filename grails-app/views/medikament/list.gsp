
<%@ page import="hierlmeier.Medikament" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'medikament.label', default: 'Medikament')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-medikament" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-medikament" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="bezeichnung" title="${message(code: 'medikament.bezeichnung.label', default: 'Bezeichnung')}" />
					
						<g:sortableColumn property="preis" title="${message(code: 'medikament.preis.label', default: 'Preis')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${medikamentInstanceList}" status="i" var="medikamentInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${medikamentInstance.id}">${fieldValue(bean: medikamentInstance, field: "bezeichnung")}</g:link></td>
					
						<td>${fieldValue(bean: medikamentInstance, field: "preis")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${medikamentInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
