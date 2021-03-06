<%@ page import="hierlmeier.Position" %>
<!doctype html>
<html>
  <head>
    <meta name="layout" content="main">
  <g:set var="entityName" value="${message(code: 'position.label', default: 'Position')}" />
  <title><g:message code="default.create.label" args="[entityName]" /></title>
  <script type="text/javascript">
      var selectbox_datasource = '${createLink(controller:"positionstyp", action:"preisJSON")}'
  </script>
</head>
<body>
  <div id="create-position" class="content scaffold-create">
    <h1><g:message code="default.create.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
      <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${positionInstance}">
      <ul class="errors" role="alert">
        <g:eachError bean="${positionInstance}" var="error">
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
<g:javascript src="pvhm-datepicker.js"/>
</body>
</html>
