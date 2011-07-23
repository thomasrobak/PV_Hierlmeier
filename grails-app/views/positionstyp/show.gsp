
<%@ page import="hierlmeier.Positionstyp" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'positionstyp.label', default: 'Positionstyp')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-positionstyp" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-positionstyp" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list positionstyp">
			
				<g:if test="${positionstypInstance?.bezeichnung}">
				<li class="fieldcontain">
					<span id="bezeichnung-label" class="property-label"><g:message code="positionstyp.bezeichnung.label" default="Bezeichnung" /></span>
					
						<span class="property-value" aria-labelledby="bezeichnung-label"><g:fieldValue bean="${positionstypInstance}" field="bezeichnung"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${positionstypInstance?.preis}">
				<li class="fieldcontain">
					<span id="preis-label" class="property-label"><g:message code="positionstyp.preis.label" default="Preis" /></span>
					
						<span class="property-value" aria-labelledby="preis-label"><g:fieldValue bean="${positionstypInstance}" field="preis"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${positionstypInstance?.id}" />
					<g:link class="edit" action="edit" id="${positionstypInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
