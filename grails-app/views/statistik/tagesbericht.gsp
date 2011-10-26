
<!doctype html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <title>Patientenverwaltung Hierlmeier</title>
    <script type="text/javascript">
      var data_tagesbericht_position = ${tabledata.position};
      var data_tagesbericht_zahlung = ${tabledata.zahlung};
    </script>
  </head>
  <body>
    <h1><g:message code="tagesbericht.label" default="Tagesbericht" /></h1>
    <g:if test="${flash.message}">
      <div class="message" role="status">${flash.message}</div>
    </g:if>
  <g:form name="form" action="tagesbericht">
    <fieldset class="form">
      <div class="fieldcontain required">
        <label>
          <g:message code="default.date.label" default="Datum" />
          <span class="required-indicator">*</span>
        </label>
        <g:textField id="datepicker" name="datum" value="${datum}"/>
        </span>
      </div>
    </fieldset>
    <div class="buttons">
      <span class="button">
        <g:submitButton name="submit" class="search" value="${message(code: 'default.search.label', default: 'Suchen')}" />
      </span>
    </div>
  </g:form>
  <div style="margin: 1em 0">
    <table>
      <thead>
        <tr>
          <th style="width: 50%">Positionen - Summe: ${sums.positionsumme}</th>
          <th style="width: 50%">Zahlungen - Summe: ${sums.zahlungsumme}</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td>
            <table id="dt-tagesbericht-position" class="display">
              <thead>
                <tr>
                  <th class="dt-tagesbericht-position-th-kunde">Kunde</th>
                  <th class="dt-tagesbericht-position-th-typ">Typ</th>
                  <th class="dt-tagesbericht-position-th-betrag">Betrag (€)</th>
                </tr>
              </thead>
              <tbody></tbody>
            </table>
          </td>
          <td>
            <table id="dt-tagesbericht-zahlung" class="display">
              <thead>
                <tr>
                  <th class="dt-tagesbericht-zahlung-th-kunde">Kunde</th>
                  <th class="dt-tagesbericht-zahlung-th-betrag">Betrag (€)</th>
                </tr>
              </thead>
              <tbody></tbody>
            </table>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
  <g:javascript src="pvhm-datatables.js"/>
  <g:javascript src="pvhm-datepicker.js"/>
  </body>
</html>
