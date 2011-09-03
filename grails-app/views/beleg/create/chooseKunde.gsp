
<%@ page import="hierlmeier.Kunde" %>
<!doctype html>
<html>
  <head>
    <meta name="layout" content="main">
    <title>Kunde wählen (hardcoded)</title>
    <script type="text/javascript">
      var dt_row_click_action = '${createLink(controller:"beleg", action:"createBeleg", event: "submit", id:"_x_")}'
    </script>
  </head>
  <body>
    <h1>Kunde wählen (hardcoded)</h1>
  <g:if test="${flash.message}">
    <div class="message" role="status">${flash.message}</div>
  </g:if>
  <div style="margin: 1em">
    <table id="dt-kunde" class="display">
      <thead>
        <tr>
          <th class="dt-kunde-th-nachname">Nachname</th>
          <th class="dt-kunde-th-bemerkung">Bemerkung</th>
          <th class="dt-kunde-th-wohnort">Wohnort</th>
          <th class="dt-kunde-th-mwst">MwSt</th>
          <th class="dt-kunde-th-telefonnummer">Telefonnummer</th>
        </tr>
      </thead>
      <tbody></tbody>
    </table>
  </div>
  <g:javascript src="datatable.js"/>
  
  <%--
      <table>
        <thead>
          <tr>
            
            <g:sortableColumn property="nachname" title="${message(code: 'kunde.nachname.label', default: 'Nachname')}" />
            
            <g:sortableColumn property="vorname" title="${message(code: 'kunde.vorname.label', default: 'Vorname')}" />

            <g:sortableColumn property="bemerkung" title="${message(code: 'kunde.bemerkung.label', default: 'Bemerkung')}" />
            
            <g:sortableColumn property="wohnort" title="${message(code: 'kunde.wohnort.label', default: 'Wohnort')}" />
            
            <g:sortableColumn property="adresse" title="${message(code: 'kunde.adresse.label', default: 'Adresse')}" />

            <g:sortableColumn property="mwst" title="${message(code: 'kunde.mwst.label', default: 'Mwst')}" />

            <g:sortableColumn property="telefonnummer" title="${message(code: 'kunde.telefonnummer.label', default: 'Telefonnummer')}" />

          </tr>
        </thead>
        <tbody>
        <g:each in="${applicableKundeList}" status="i" var="kunde">
          <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
            
            <td><g:link action="create" event="submit" id="${kunde?.id}">${fieldValue(bean: kunde, field: "nachname")}</g:link></td>
        
            <td>${fieldValue(bean: kunde, field: "vorname")}</td>
        
            <td>${fieldValue(bean: kunde, field: "bemerkung")}</td>
            
            <td>${fieldValue(bean: kunde, field: "wohnort")}</td>
            
            <td>${fieldValue(bean: kunde, field: "adresse")}</td>

            <td><g:formatBoolean boolean="${kunde.mwst}" /></td>

            <td>${fieldValue(bean: kunde, field: "telefonnummer")}</td>

          </tr>
        </g:each>
        </tbody>
      </table>
      <div class="pagination">
        <g:paginate total="${applicableKundeListTotal}" />
      </div>
    --%>
  </body>
</html>
