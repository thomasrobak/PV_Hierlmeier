

<div id="list-position" class="content scaffold-list">
  <h1>Positionen ohne Beleg</h1>
  <table>
    <thead>
      <tr>
        <g:sortableColumn property="typ" title="${message(code: 'position.typ.label', default: 'Typ')}" />
        
        <g:sortableColumn property="anmerkung" title="${message(code: 'position.anmerkung.label', default: 'Anmerkung')}" />

        <g:sortableColumn property="menge" title="${message(code: 'position.menge.label', default: 'Menge')}" />

        <g:sortableColumn property="preis" title="${message(code: 'position.preis.label', default: 'Preis')}" />
        
        <g:sortableColumn property="datum" title="${message(code: 'position.datum.label', default: 'Datum')}" />
      </tr>
    </thead>
    <tbody>
      <g:each in="${positionen}" status="i" var="position">
        <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
          
          <g:hiddenField name="id" value="${position?.id}" />
          
          <td>${fieldValue(bean: position, field: "typ")}</td>
      
          <td>${fieldValue(bean: position, field: "anmerkung")}</td>

          <td>${fieldValue(bean: position, field: "menge")}</td>

          <td>${fieldValue(bean: position, field: "preis")}</td>
          
          <td><g:formatDate date="${position.datum}" /></td>

        </tr>
      </g:each>
    </tbody>
  </table>
  <div class="pagination">
    <g:paginate total="${positionenTotal}" />
  </div>
</div>