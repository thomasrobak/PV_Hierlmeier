

<div id="list-position" class="content scaffold-list" role="main">
  <h1><g:message code="default.list.label" args=[listlabel] /></h1>
  <g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
  </g:if>
  <table>
    <thead>
      <tr>
        <th><g:message code="position.beleg.label" default="Beleg" /></th>

        <g:sortableColumn property="anmerkung" title="${message(code: 'position.anmerkung.label', default: 'Anmerkung')}" />

        <g:sortableColumn property="menge" title="${message(code: 'position.menge.label', default: 'Menge')}" />

        <g:sortableColumn property="datum" title="${message(code: 'position.datum.label', default: 'Datum')}" />

        <th><g:message code="position.kunde.label" default="Kunde" /></th>

        <g:sortableColumn property="preis" title="${message(code: 'position.preis.label', default: 'Preis')}" />
      </tr>
    </thead>
    <tbody>
      <g:each in="${positionen}" status="i" var="position">
        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
          
          <td><g:link action="show" id="${position?.id}">${fieldValue(bean: position, field: "beleg")}</g:link></td>

          <td>${fieldValue(bean: position, field: "anmerkung")}</td>

          <td>${fieldValue(bean: position, field: "menge")}</td>

          <td><g:formatDate date="${position.datum}" /></td>

          <td>${fieldValue(bean: position, field: "kunde")}</td>

          <td>${fieldValue(bean: position, field: "preis")}</td>

        </tr>
      </g:each>
    </tbody>
  </table>
  <div class="pagination">
    <g:paginate total="${positionListTotal}" />
  </div>
</div>