
<!doctype html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <title>Patientenverwaltung Hierlmeier</title>
    <script type="text/javascript">
      var statistik_overview = '${createLink(controller:"statistik", action:"overviewJSON")}'
    </script>
  </head>
  <body>
  <g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
  </g:if>
  <h1><g:message code="overview.label" default="Übersicht" /></h1>
  <ol class="property-list">
    <li class="fieldcontain">
      <span class="property-label">Kunden in db:</span>
      <span id="kundecount" class="property-value"></span>
    </li>
    <li class="fieldcontain">
      <span class="property-label">Belege in db:</span>
      <span id="belegcount" class="property-value"></span>
      <span class="property-label">davon unbeglichen:</span>
      <span id="belegunbeglichen" class="property-value"></span>
    </li>
    <li class="fieldcontain">
      <span class="property-label">Positionen in db:</span>
      <span id="positioncount" class="property-value"></span>
      <span class="property-label">davon nicht zugewiesen:</span>
      <span id="positionunzugewiesen" class="property-value"></span>
    </li>
    <li class="fieldcontain">
      <span class="property-label">Zahlungen in db:</span>
      <span id="zahlungcount" class="property-value"></span>
    </li>
    <li class="fieldcontain">
      <span class="property-label">Medikamente in db:</span>
      <span id="medikamentcount" class="property-value"></span>
    </li>
    <li class="fieldcontain">
      <span class="property-label">Leistungen in db:</span>
      <span id="leistungcount" class="property-value"></span>
    </li>
    <li class="fieldcontain">
      <span class="property-label">Tiere in db:</span>
      <span id="tiercount" class="property-value"></span>
    </li>
  </ol>
  <g:javascript src="statistiken.js"/>
</body>
</html>
