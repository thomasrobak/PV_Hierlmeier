<%@ page import="hierlmeier.Zahlung" %>
<!doctype html>
<html>
  <head>
    <meta name="layout" content="main">
  <g:set var="entityName" value="${message(code: 'zahlung.label', default: 'Zahlung')}" />
  <title><g:message code="default.create.label" args="[entityName]" /></title>
</head>
<body>
  <div id="create-zahlung" class="content scaffold-create">
    <h1><g:message code="default.create.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
      <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${zahlungInstance}">
      <ul class="errors" role="alert">
        <g:eachError bean="${zahlungInstance}" var="error">
          <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
        </g:eachError>
      </ul>
    </g:hasErrors>
    <g:form action="save" >
      <fieldset class="form">
        <g:render template="form"/>
      </fieldset>
      <fieldset class="buttons">
        <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
      </fieldset>
    </g:form>
  </div>
<g:javascript src="datepicker.js"/>
<g:javascript src="jquery.ui.datepicker-de.js"/>
</body>
</html>
