
<!doctype html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <title>Patientenverwaltung Hierlmeier</title>
    <script type="text/javascript">
      var table_medikament_data = ${tabledata.medikament};
      var table_leistung_data = ${tabledata.leistung};
    </script>
  </head>
  <body>
    <h1><g:message code="erbracht.label" default="Erbrachte Leistungen & Medikamente" /></h1>
    <g:if test="${flash.message}">
      <div class="message" role="status">${flash.message}</div>
    </g:if>
  <g:form name="form" action="erbracht">
    <fieldset class="form">
      <div class="fieldcontain required">
        <label>
          <g:message code="default.date.range.label" default="Zeitraum" />
          <span class="required-indicator">*</span>
        </label>
        <g:select name="listrange.jahr" value="${listrange.jahr}" from="${selectbox.jahrliste}" />
        <g:select name="listrange.monat" value="${listrange.monat}" from="${selectbox.monatliste}" noSelection="${['':'-Alle Monate-']}"/>
        </span>
      </div>
    </fieldset>
    
    <div class="buttons">
      <span class="button">
        <g:submitButton name="submit" class="search" value="${message(code: 'default.search.label', default: 'Suchen')}" />
      </span>
    </div>
  </g:form>
    <table>
      <thead>
        <tr>
          <th style="width: 50%">Medikamente - Summe: ${sums.medikamentsumme}</th>
          <th style="width: 50%">Leistungen - Summe: ${sums.leistungsumme}</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>
            <div style="width: 100%">
              <table id="dt-erbracht-medikament" class="display">
                <thead>
                  <tr>
                    <th class="dt-erbracht-medikament-th-typ">Bezeichnung</th>
                    <th class="dt-erbracht-medikament-th-menge">Menge</th>
                    <th class="dt-erbracht-medikament-th-summe">Summe (€)</th>
                  </tr>
                </thead>
                <tbody></tbody>
              </table>
            </div>
          </td>
          <td>
            <div style="width: 100%">
              <table id="dt-erbracht-leistung" class="display">
                <thead>
                  <tr>
                    <th class="dt-erbracht-leistung-th-typ">Bezeichnung</th>
                    <th class="dt-erbracht-leistung-th-menge">Menge</th>
                    <th class="dt-erbracht-leistung-th-summe">Summe (€)</th>
                  </tr>
                </thead>
                <tbody></tbody>
              </table>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  <g:javascript src="pvhm-datatables.js"/>
</body>
</html>
