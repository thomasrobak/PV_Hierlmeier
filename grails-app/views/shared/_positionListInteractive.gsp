

<div id="list-position" class="content scaffold-list" role="main">
  <h1><g:message code="default.list.label" args="Positionen" /></h1>
  <g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
  </g:if>
  <table>
    <thead>
      <tr>
        <g:sortableColumn property="anmerkung" title="${message(code: 'position.anmerkung.label', default: 'Anmerkung')}" />

        <g:sortableColumn property="menge" title="${message(code: 'position.menge.label', default: 'Menge')}" />

        <g:sortableColumn property="datum" title="${message(code: 'position.datum.label', default: 'Datum')}" />

        <th><g:message code="position.kunde.label" default="Kunde" /></th>

        <g:sortableColumn property="preis" title="${message(code: 'position.preis.label', default: 'Preis')}" />
        
        <th><g:message code="position.userAction.add.label" default="Hinzufügen" /></th>
      </tr>
    </thead>
    <tbody>
      <g:each in="${positionen}" status="i" var="position">
        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

          <td>${fieldValue(bean: position, field: "anmerkung")}</td>

          <td>${fieldValue(bean: position, field: "menge")}</td>

          <td><g:formatDate date="${position.datum}" /></td>

          <td>${fieldValue(bean: position, field: "kunde")}</td>

          <td>${fieldValue(bean: position, field: "preis")}</td>
          
          <td><g:checkBox name="userAction" value="${position?.userAction}" /></td>

        </tr>
      </g:each>
    </tbody>
  </table>
  <div class="pagination">
    <g:paginate total="${positionen}" />
  </div>
</div>