
<%@ page import="hierlmeier.Zahlung" %>
<!doctype html>
<html>
  <head>
    <meta name="layout" content="main">
  <g:set var="entityName" value="${message(code: 'zahlung.label', default: 'Zahlung')}" />
  <title><g:message code="default.show.label" args="[entityName]" /></title>
</head>
<body>
  <div id="show-zahlung" class="content scaffold-show">
    <h1><g:message code="default.show.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
      <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list zahlung">

      <g:if test="${zahlungInstance?.betrag}">
        <li class="fieldcontain">
          <span id="betrag-label" class="property-label"><g:message code="zahlung.betrag.label" default="Gesamtbetrag (€)" /></span>
          <span class="property-value" aria-labelledby="betrag-label"><g:fieldValue bean="${zahlungInstance}" field="betrag"/></span>
        </li>
      </g:if>
      
      <g:if test="${zahlungInstance?.datum}">
        <li class="fieldcontain">
          <span id="datum-label" class="property-label"><g:message code="zahlung.datum.label" default="Eingangsdatum" /></span>
          <span class="property-value" aria-labelledby="datum-label"><g:fieldValue bean="${zahlungInstance}" field="datum"/></span>
        </li>
      </g:if>

      <g:if test="${zahlungInstance?.kunde}">
        <li class="fieldcontain">
          <span id="kunde-label" class="property-label"><g:message code="zahlung.kunde.label" default="Kunde" /></span>
          <span class="property-value" aria-labelledby="kunde-label"><g:link controller="kunde" action="show" id="${zahlungInstance?.kunde?.id}">${zahlungInstance?.kunde?.encodeAsHTML()}</g:link></span>
        </li>
      </g:if>
      <li class="fieldcontain">
        <h1>Gesamtbetrag aufgeteilt auf folgende Belege:</h1>
        <table>
          <tr>
          <g:sortableColumn property="zahlungsteil.betrag" titleKey="zahlungsteil.betrag" />
          <g:sortableColumn property="zahlungsteil.beleg" titleKey="zahlungsteil.beleg" />
          <g:sortableColumn property="zahlungsteil.beleg.datum" titleKey="beleg.datum" />
          </tr>
          <tbody>
          <g:each in="${zahlungInstance.zahlungsteile}" status="i" var="zt">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
              <td>${fieldValue(bean: zt, field: "betrag")}</td>
              <td>${fieldValue(bean: zt, field: "beleg")}</td>
              <td>${fieldValue(bean: zt.beleg, field: "datum")}</td>
            </tr>
          </g:each>
          </tbody>
        </table>
      </li>
    </ol>
    <g:form>
      <fieldset class="buttons">
        <g:hiddenField name="id" value="${zahlungInstance?.id}" />
        <g:link class="edit" action="edit" id="${zahlungInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
        <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
      </fieldset>
    </g:form>
  </div>
</body>
</html>
