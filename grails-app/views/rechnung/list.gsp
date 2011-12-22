
<!doctype html>
<html>
  <head>
    <meta name="layout" content="main" />
    <title><g:message code="rechnung.list" default="Erstellte Rechnungen Liste" /></title>
  </head>
  <body>
    <h1><g:message code="rechnung.list" default="Erstellte Rechnungen Liste" /></h1>
  <g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
  </g:if>
  <table id="dt-rechnung" class="display"
         datasource="${createLink(controller:'rechnung', action:'dataTableJSON')}"
         filter="${message(code: 'filter.NOFILTER')}"
         rowclickaction="${createLink(controller:'rechnung', action:'show', id:'_x_')}"
         >
    <thead>
      <tr>
        <th class="dt-rechnung-th-rechnungnummer">Rechnungnummer</th>
        <th class="dt-rechnung-th-kunde">Kunde</th>
        <th class="dt-rechnung-th-datum">Datum</th>
        <th class="dt-rechnung-th-betrag">Betrag (€)</th>
      </tr>
    </thead>
    <tbody></tbody>
  </table>
  <g:javascript src="pvhm-datatables.js"/>
</body>
</html>
