
<!doctype html>
<html>
  <head>
    <meta name="layout" content="main"/>
    <title>Patientenverwaltung Hierlmeier</title>

    <style type="text/css">
    #action-list {
      margin: 2em 5em 1.25em;
    }

    #action-list h1 {
      margin-top: 0;
    }

    h2 {
      margin-top: 1em;
      margin-bottom: 0.3em;
      font-size: 1em;
    }
    </style>

  </head>
  <body>
    <div id="action-list">
      <span style="display:table-cell">
        <h2>Kunden</h2>
        <ul>
          <li class="controller"><g:link controller="kunde" action="list">Kunden ansehen</g:link></li>
          <li class="controller"><g:link controller="kunde" action="create">Kunde hinzufügen</g:link></li>
        </ul>
        <h2>Tiere</h2>
        <ul>
          <li class="controller"><g:link controller="tier" action="list">Tiere ansehen</g:link></li>
          <li class="controller"><g:link controller="tier" action="create">Tier hinzufügen</g:link></li>
        </ul>
        <h2>Medikamente/Leistungen</h2>
        <ul>
          <li class="controller"><g:link controller="positionstyp" action="list">Medikamente/Leistungen ansehen</g:link></li>
          <li class="controller"><g:link controller="medikament" action="create">Medikament hinzufügen</g:link></li>
          <li class="controller"><g:link controller="leistung" action="create">Leistung hinzufügen</g:link></li>
        </ul>

        <h2>Positionen</h2>
        <ul>
          <li class="controller"><g:link controller="position" action="list">Positionen ansehen</g:link></li>
          <li class="controller"><g:link controller="position" action="create">Position erstellen</g:link></li>
        </ul>
      </span>
      <span style="display:table-cell;padding-left:4em">
        <h2>Belege</h2>
        <ul>
          <li class="controller"><g:link controller="beleg" action="list">Belege ansehen</g:link></li>
          <li class="controller"><g:link controller="beleg" action="create">Beleg erstellen</g:link></li>
          <li class="controller"><g:link controller="beleg" action="list">Beleg drucken</g:link></li>
          <li class="controller">Mahnung erstellen</li>
        </ul>
        <h2>Zahlungen</h2>
        <ul>
          <li class="controller">Zahlungen ansehen</li>
          <li class="controller"><g:link controller="zahlung" action="zahlungCreation">Zahlung erfassen</g:link></li>   
        </ul>
        <h2>Statistiken</h2>
        <ul>
          <li class="controller">Tagesbericht</li>
          <li class="controller">Leistungen</li>
          <li class="controller">Medikamente</li>
          <li class="controller">Kunden</li>
          <li class="controller">Offene Positionen</li>
        </ul>
      </span>
    </div>
  </body>
</html>
