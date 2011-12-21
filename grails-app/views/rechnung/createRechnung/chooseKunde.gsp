
<%@ page import="hierlmeier.Kunde" %>
<!doctype html>
<html>
  <head>
    <meta name="layout" content="main">
    <title>Rechnung Erstellen - Kunde wählen (hardcoded)</title>
  </head>
  <body>
    <h1>Rechnung Erstellen - Kunde wählen (hardcoded)</h1>
    <div style="margin: 12px; font-size: 12px">
      <g:message code="option.rechnung.choose.from.kunde.list" default="Hinweis: Um eine Rechnung nur für einen bestimmten Kunden zu Erstellen diesen Kunden per Klick aus der Liste wählen" />
    </div>
  <g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
  </g:if>
  <g:form name="formzahllastfilter" action="createRechnung">
    <fieldset class="form">
      <div class="fieldcontain required">
        <label>
          <g:message code="default.minimum.debt.label" default="Zahllast mindestens (€)" />
        </label>
        <g:textField name="minzahllast" value="0" />
        </span>
      </div>
    </fieldset>

    <div class="buttons">
      <span class="button">
        <g:submitButton name="batch" class="save" value="${message(code: 'default.batch.create.rechnung.label', default: 'Wähle alle unten aufgeführte Kunden')}" />
      </span>
    </div>
  </g:form>
  <div style="margin-top: 1em">
    <table id="dt-kunde" class="display"
           datasource="${createLink(controller:'kunde', action:'dataTableJSON')}"
           filter="${message(code: 'filter.NPB')}"
           rowclickaction="${createLink(controller:'rechnung', action:'createRechnung', event:'single', id:'_x_')}">
      <thead>
        <tr>
          <th class="dt-kunde-th-nachname">Nachname</th>
          <th class="dt-kunde-th-bemerkung">Bemerkung</th>
          <th class="dt-kunde-th-wohnort">Wohnort</th>
          <th class="dt-kunde-th-mwst">MwSt</th>
          <th class="dt-kunde-th-telefonnummer">Telefonnummer</th>
          <th class="dt-kunde-th-zahllast">Zahllast (€)</th>
        </tr>
      </thead>
      <tbody></tbody>
    </table>
  </div>
  <g:javascript src="pvhm-datatables.js"/>
</body>
</html>
