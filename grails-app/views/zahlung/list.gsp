
<!doctype html>
<html>
  <head>
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'zahlung.label', default: 'Zahlung')}" />
  <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
  <h1><g:message code="default.list.label" args="[entityName]" /></h1>
<g:if test="${flash.message}">
  <div class="message" role="status">${flash.message}</div>
</g:if>
  <table id="dt-zahlung" class="display"
         datasource="${createLink(controller:'zahlung', action:'dataTableJSON')}"
         filter="${message(code: 'filter.NOFILTER')}"
         rowclickaction="${createLink(controller:'zahlung', action:'show', id:'_x_')}"
         >
    <thead>
      <tr>
        <th class="dt-zahlung-th-kunde">Kunde</th>
        <th class="dt-zahlung-th-datum">Eingangsdatum</th>
        <th class="dt-zahlung-th-betrag">Betrag (€)</th>
      </tr>
    </thead>
    <tbody></tbody>
  </table>
<g:javascript src="pvhm-datatables.js"/>
</body>
</html>
