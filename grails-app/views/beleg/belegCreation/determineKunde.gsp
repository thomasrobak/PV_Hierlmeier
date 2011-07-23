
<!doctype html>
<html>
  <head>
    <meta name="layout" content="main">
    <title>Kunde wählen</title>
  </head>
  <body>
    <h1>Kunde wählen</h1>
    <div id="kunde-label" class="property-label"><g:message code="kunde.label" default="Kunde" /></div>
    <div>
      <g:each in="${kundenlist}" var="kunde">
        <span class="property-value" aria-labelledby="kunde-label">
          <g:link action="belegCreation" event="submit" id="${kunde?.id}">${kunde?.encodeAsHTML()}</g:link>
        </span>
      </g:each>
    </div>
  </body>
</html>
