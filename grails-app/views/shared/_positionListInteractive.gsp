

<div id="list-position" class="content scaffold-list" role="main">
  <h1>Positionen ohne Beleg</h1>
  <g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
  </g:if>
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
          
          <!--td><g:link action="show" id="${position?.id}">${fieldValue(bean: position, field: "typ")}</g:link></td-->
      
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