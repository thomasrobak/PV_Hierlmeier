
<%@ page import="hierlmeier.Position" %>
<!doctype html>
<html>
  <head>
    <meta name="layout" content="main">
  <g:set var="entityName" value="${message(code: 'position.label', default: 'Position')}" />
  <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
  <h1><g:message code="default.list.label" args="[entityName]" /></h1>
<g:if test="${flash.message}">
  <div class="message" role="status">${flash.message}</div>
</g:if>
<div style="margin: 1em">
  <span>Von:</span>
  <span><input type="text" id="from-date"/></span>
  <span>Bis:</span>
  <span><input type="text" id="to-date"/></span>
</div>
<table id="dt-position-list" class="display" 
       datasource="${createLink(controller:'position', action:'dataTableJSON')}"
       filter="${message(code: 'filter.NOFILTER')}"
       rowclickaction="${createLink(controller:'position', action:'show', id:'_x_')}"
       >
  <thead>
    <tr>
      <th class="dt-position-th-typ">Typ</th>
      <th class="dt-position-th-anmerkung">Anmerkung</th>
      <th class="dt-position-th-tier">Tier</th>
      <th class="dt-position-th-menge">Menge</th>
      <th class="dt-position-th-preis">Preis</th>
      <th id="dt-datum-column" class="dt-position-th-datum">Datum</th>
      <th class="dt-position-th-beleg">Beleg</th>
    </tr>
  </thead>
  <tbody></tbody>
</table>
<g:javascript src="datepicker.js"/>
<g:javascript src="datatable.js"/>
</body>
</html>
