
<%@ page import="hierlmeier.Leistung" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'leistung.label', default: 'Leistung')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="show-leistung" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list leistung">
			
				<g:if test="${leistungInstance?.bezeichnung}">
				<li class="fieldcontain">
					<span id="bezeichnung-label" class="property-label"><g:message code="leistung.bezeichnung.label" default="Bezeichnung" /></span>
					
						<span class="property-value" aria-labelledby="bezeichnung-label"><g:fieldValue bean="${leistungInstance}" field="bezeichnung"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${leistungInstance?.preis}">
				<li class="fieldcontain">
					<span id="preis-label" class="property-label"><g:message code="leistung.preis.label" default="Preis" /></span>
					
						<span class="property-value" aria-labelledby="preis-label"><g:fieldValue bean="${leistungInstance}" field="preis"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${leistungInstance?.id}" />
					<g:link class="edit" action="edit" id="${leistungInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
