
<%@ page import="hierlmeier.Medikament" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'medikament.label', default: 'Medikament')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<div id="show-medikament" class="content scaffold-show">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list medikament">
			
				<g:if test="${medikamentInstance?.bezeichnung}">
				<li class="fieldcontain">
					<span id="bezeichnung-label" class="property-label"><g:message code="medikament.bezeichnung.label" default="Bezeichnung" /></span>
					
						<span class="property-value" aria-labelledby="bezeichnung-label"><g:fieldValue bean="${medikamentInstance}" field="bezeichnung"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${medikamentInstance?.preis}">
				<li class="fieldcontain">
					<span id="preis-label" class="property-label"><g:message code="medikament.preis.label" default="Preis" /></span>
					
						<span class="property-value" aria-labelledby="preis-label"><g:fieldValue bean="${medikamentInstance}" field="preis"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${medikamentInstance?.id}" />
					<g:link class="edit" action="edit" id="${medikamentInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
