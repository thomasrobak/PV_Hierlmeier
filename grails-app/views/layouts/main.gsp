<!doctype html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <title><g:layoutTitle default="Patientenverwaltung Hierlmeier"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/png"/>
    <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}"/>
    <link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'pvhm.css')}" type="text/css"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery-ui.css')}" type="text/css"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'demo_table_jui.css')}" type="text/css"/>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.menubar.css')}" type="text/css"/>
    <g:javascript library="jquery" plugin="jquery"/>
    <g:javascript src="jquery-ui-1.8.16.custom.min.js"/>
    <g:javascript src="jquery.dataTables.min.js"/>
    <g:javascript src="jquery.menubar.js"/>
    <g:javascript src="pvhm-common.js"/>
    <script type="text/javascript">
      var app_locale = '${message(code: "application.language")}'
      var app_base_dir = '${createLink(uri:"/")}'
      var dt_locale_file = app_base_dir + "txt/datatable_?.txt".replace("?", app_locale);
      $(function(){
        $("#navbar").menubar({
          items: [
            {
              name: "Home",
              selecton: function(){window.location.href='${createLink(uri:"/")}'},
              attr: { title: "Startseite." }
            },{
              name: "Anzeigen", 
              items: [
                { 
                  name: "Kunden", 
                  attr: { title: "${message(code: 'kunde.list')}"},
                  selecton: function(){window.location.href='${createLink(controller:"kunde", action:"list")}'} 
                },{ 
                  name: "Tiere", 
                  attr: { title: "Zeigt eine Liste aller Tiere." },
                  selecton: function(){window.location.href='${createLink(controller:"tier", action:"list")}'} 
                },{ 
                  name: "Medikamente/Leistungen", 
                  attr: { title: "Zeigt eine Liste aller Medikamente/Leistungen." },
                  selecton: function(){window.location.href='${createLink(controller:"positionstyp", action:"list")}'} 
                },{ 
                  name: "Positionen", 
                  attr: { title: "Zeigt eine Liste aller Positionen." },
                  selecton: function(){window.location.href='${createLink(controller:"position", action:"list")}'} 
                },{ 
                  name: "Belege", 
                  attr: { title: "Zeigt eine Liste aller Belege." },
                  selecton: function(){window.location.href='${createLink(controller:"beleg", action:"list")}'} 
                },{ 
                  name: "Zahlungen", 
                  attr: { title: "Zeigt eine Liste aller Zahlungen." },
                  selecton: function(){window.location.href='${createLink(controller:"zahlung", action:"list")}'} 
                }
              ]
            },{
              name: "Erfassen", 
              items: [
                { 
                  name: "Beleg", 
                  attr: { title: "Erstelle neuen Beleg." },
                  selecton: function(){window.location.href='${createLink(controller:"beleg", action:"create")}'} 
                },{ 
                  name: "Kunde", 
                  attr: { title: "Erstelle neuen Kunden." },
                  selecton: function(){window.location.href='${createLink(controller:"kunde", action:"create")}'} 
                },{ 
                  name: "Medikament", 
                  attr: { title: "Erstelle neues Medikament." },
                  selecton: function(){window.location.href='${createLink(controller:"medikament", action:"create")}'} 
                },{ 
                  name: "Leistung", 
                  attr: { title: "Erstelle neue Leistung." },
                  selecton: function(){window.location.href='${createLink(controller:"leistung", action:"create")}'} 
                },{ 
                  name: "Position", 
                  attr: { title: "Erstelle neue Position." },
                  selecton: function(){window.location.href='${createLink(controller:"position", action:"create")}'} 
                },{ 
                  name: "Tier", 
                  attr: { title: "Erstelle neues Tier." },
                  selecton: function(){window.location.href='${createLink(controller:"tier", action:"create")}'} 
                },{ 
                  name: "Zahlung", 
                  attr: { title: "Erstelle neue Zahlung." },
                  selecton: function(){window.location.href='${createLink(controller:"zahlung", action:"create")}'} 
                }
              ]
            },{
              name: "Drucken",
              items: [{ 
                  name: "Beleg", 
                  attr: { title: "Erstelle neuen Beleg." },
                  selecton: function(){window.location.href='${createLink(controller:"beleg", action:"list")}'} 
              }]              
            },{
              name: "Statistiken", 
              items: [
                { 
                  name: "Tagesbericht", 
                  attr: { title: "Ein- und Ausgang des Tages" },
                  selecton: function(){window.location.href='${createLink(controller:"statistik", action:"tagesbericht")}'} 
                },{ 
                  name: "Leistungen/Medikamente", 
                  attr: { title: "Erbrachte Leistungen & Medikamente" },
                  selecton: function(){window.location.href='${createLink(controller:"statistik", action:"erbracht")}'}
                }
              ]
            }
          ]
        });
      });
    </script>
  <g:layoutHead/>
</head>
<body>
  <div id="pvhm-logo" role="banner">
    <a href="${createLink(uri: '/')}">
      <img src="${resource(dir: 'images', file: 'pvhm_logo.png')}" alt="Patientenverwaltung Hierlmeier"/>
      <a/>
      <h1 style="font-size:1.5em;font-weight:bold;">Patientenverwaltung Hierlmeier</h1>
  </div><a href="#action-body" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
  <div id="navbar" role="navigation" style="font-size: 14px"></div>
  <div id="action-body" class="content" role="main">
    <g:layoutBody/>
    <div class="footer" role="contentinfo"></div>
    <div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
  </div>
</body>
  </html>