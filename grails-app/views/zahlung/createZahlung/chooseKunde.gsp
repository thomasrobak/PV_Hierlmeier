
<%@ page import="hierlmeier.Kunde" %>
<!doctype html>
<html>
  <head>
    <meta name="layout" content="main">
    <title>Kunde wählen (hardcoded)</title>
  </head>
  <body>
    <h1>Kunde wählen (hardcoded)</h1>
  <g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
  </g:if>
  <div style="margin: 1em">
    <table id="dt-kunde" class="display"
           datasource="${createLink(controller:'kunde', action:'dataTableJSON')}"
           filter="${message(code: 'filter.NPB')}"
           rowclickaction="${createLink(controller:'zahlung', action:'createZahlung', event:'submit', id:'_x_')}">
      <thead>
        <tr>
          <th class="dt-kunde-th-nachname">Nachname</th>
          <th class="dt-kunde-th-bemerkung">Bemerkung</th>
          <th class="dt-kunde-th-wohnort">Wohnort</th>
          <th class="dt-kunde-th-mwst">MwSt</th>
          <th class="dt-kunde-th-telefonnummer">Telefonnummer</th>
        </tr>
      </thead>
      <tbody></tbody>
    </table>
  </div>
  <g:javascript src="pvhm.js"/>
</body>
</html>
