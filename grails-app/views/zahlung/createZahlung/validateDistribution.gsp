
<!doctype html>
<html>
  <head>
    <meta name="layout" content="main">
    <title>Zahlungsverteilung bestätigen (hardcoded)</title>
  </head>
  <body>
    <div id="show-kunde" class="content scaffold-show">
      <h1>Zahlungsverteilung bestätigen für (hardcoded):</h1>
      <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
      </g:if>
      <ol class="property-list zahlung">
        <li class="fieldcontain">
          <span id="kunde-label" class="property-label"><g:message code="zahlung.kunde.label" default="KundeXXX" /></span>
          <span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${zahlungInstance}" field="kunde"/></span>
        </li>
        <li class="fieldcontain">
          <span id="betrag-label" class="property-label"><g:message code="zahlung.betrag.label" default="BetragXXX" /></span>
          <span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${zahlungInstance}" field="betrag" /></span>
          <span id="datum-label" class="property-label"><g:message code="zahlung.datum.label" default="DatumXXX" /></span>
          <span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${zahlungInstance}" field="datum" /></span>
        </li>
      </ol>
      <h1>Zahlung auf Belege anwenden (hardcoded):</h1>
      <ol class="property-list zahlungsteil">
        <g:each in="${zahlungsteile}" status="i" var="zt">
          <li class="fieldcontain">
            <span class="property-label"><g:message code="zahlungsteil.beleg.label" default="BelegXXX" /></span>
            <span class="property-value"><g:fieldValue bean="${zt.beleg}" field="belegnummer"/></span>
          </li>
        </g:each>
      </ol>
    </div>

    <div class="buttons">
      <span class="button">
        <g:submitButton name="submit" class="save" value="${message(code: 'default.beleg.create.label', default: 'Beleg Erstellen')}" />
      </span>
    </div>
<g:javascript src="pvhm.js"/>
</body>
</html>
