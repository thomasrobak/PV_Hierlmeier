
<%@ page import="hierlmeier.Rechnung" %>
<html>
  <head>
    <meta name="layout" content="main" />
    <title><g:message code="rechnung.show" default="Rechnung Anzeigen" /></title>
  </head>
  <body>
    <div class="show-rechnung">
      <h1><g:message code="rechnung.show" default="Rechnung Anzeigen" /></h1>
      <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
      </g:if>
      <g:form>
        <ol class="property-list rechnung">

          <g:if test="${rechnungInstance?.rechnungnummer}">
            <li class="fieldcontain">
              <span id="rechnungnummer-label" class="property-label"><g:message code="rechnung.rechnungnummer.label" default="rechnungnummer" /></span>

              <span class="property-value" aria-labelledby="rechnungnummer-label"><g:fieldValue bean="${rechnungInstance}" field="rechnungnummer"/></span>

            </li>
          </g:if>

          <g:if test="${rechnungInstance?.kunde}">
            <li class="fieldcontain">
              <span id="kunde-label" class="property-label"><g:message code="rechnung.kunde.label" default="Kunde" /></span>

              <span class="property-value" aria-labelledby="kunde-label"><g:link controller="kunde" action="show" id="${rechnungInstance?.kunde?.id}">${rechnungInstance?.kunde?.encodeAsHTML()}</g:link></span>

            </li>
          </g:if>

          <table id="dt-beleg" class="display"
                 datasource="${createLink(controller:'beleg', action:'dataTableJSON')}"
                 filter="${message(code: 'filter.NOFILTER')}"
                 rechnungId="${rechnungInstance.id}"
                 rowclickaction="${createLink(controller:'position', action:'show', id:'_x_')}"
                 >
            <thead>
              <tr>
                <th class="dt-position-th-typ">Typ</th>
                <th class="dt-position-th-anmerkung">Anmerkung</th>
                <th class="dt-position-th-tier">Tier</th>
                <th class="dt-position-th-preis">Einzelpreis (€)</th>
                <th class="dt-position-th-menge">Menge</th>
                <th class="dt-position-th-betrag">Betrag (€)</th>
                <th class="dt-position-th-datum">Datum</th>
              </tr>
            </thead>
            <tbody></tbody>
          </table>

        </ol>
        <div class="buttons">
          <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'edit', 'default': 'Edit')}" /></span>
          <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'delete', 'default': 'Delete')}" onclick="return confirm('${message(code: 'delete.confirm', 'default': 'Are you sure?')}');" /></span>
        </div>
      </g:form>
    </div>
  </body>
</html>
