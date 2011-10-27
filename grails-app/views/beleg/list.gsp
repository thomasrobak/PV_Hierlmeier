
<!doctype html>
<html>
  <head>
    <meta name="layout" content="main" />
  <g:set var="entityName" value="${message(code: 'beleg.label', default: 'Beleg')}" />
  <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
  <h1><g:message code="default.list.label" args="[entityName]" /></h1>
<g:if test="${flash.message}">
  <div class="message" role="status">${flash.message}</div>
</g:if>
<div style="margin: 1em">
  <table id="dt-beleg" class="display"
         datasource="${createLink(controller:'beleg', action:'dataTableJSON')}"
         filter="${message(code: 'filter.NOFILTER')}"
         rowclickaction="${createLink(controller:'beleg', action:'show', id:'_x_')}"
         >
    <thead>
      <tr>
        <th class="dt-beleg-th-belegnummer">Belegnummer</th>
        <th class="dt-beleg-th-kunde">Kunde</th>
        <th class="dt-beleg-th-datum">Datum</th>
        <th class="dt-beleg-th-betrag">Betrag (€)</th>
        <th class="dt-beleg-th-bezahlt">Bezahlt (€)</th>
        <th class="dt-beleg-th-offen">Offen (€)</th>
      </tr>
    </thead>
    <tbody></tbody>
  </table>
</div>
<g:javascript src="pvhm-datatables.js"/>
</body>
</html>
