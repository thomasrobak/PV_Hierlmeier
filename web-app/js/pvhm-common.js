
var pvhm_formatDate = function(parsethis) {
    if(parsethis == null)
        return null;
    if(app_locale == 'de')
        return $.datepicker.formatDate($.datepicker.regional['de'].dateFormat, new Date(Date.parse(parsethis)));
    else
        return $.datepicker.formatDate($.datepicker.regional[''].dateFormat, new Date(Date.parse(parsethis)));
}

var pvhm_formatNumber = function(parsethis) {
    if(parsethis == null)
        return "0,00"
    if(app_locale == 'de')
        return parseFloat(parsethis).toFixed(2).toString().replace(".", ",")
    else
        return parseFloat(parsethis).toFixed(2).toString()
}
  

 /**************
 *
 * Misc Configs and Inits
 * 
 *************/

/**************
 * Widget Locales Config and init
 *************/

    $.datepicker.regional['de'] = {
        closeText: 'schließen',
        prevText: '&#x3c;zurück',
        nextText: 'Vor&#x3e;',
        currentText: 'heute',
        monthNames: ['Januar','Februar','März','April','Mai','Juni',
        'Juli','August','September','Oktober','November','Dezember'],
        monthNamesShort: ['Jan','Feb','Mär','Apr','Mai','Jun',
        'Jul','Aug','Sep','Okt','Nov','Dez'],
        dayNames: ['Sonntag','Montag','Dienstag','Mittwoch','Donnerstag','Freitag','Samstag'],
        dayNamesShort: ['So','Mo','Di','Mi','Do','Fr','Sa'],
        dayNamesMin: ['So','Mo','Di','Mi','Do','Fr','Sa'],
        weekHeader: 'Wo',
        dateFormat: 'dd.mm.yy',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: ''
    };

var dt_locale_file = app_base_dir + "txt/datatable_?.txt".replace("?", app_locale);
var datepicker_locale

    switch(app_locale) {
        case 'de':
            datepicker_locale = $.datepicker.regional['de'];
            break;
        case 'en':
            datepicker_locale = $.datepicker.regional[''];
            break;
        default:
            datepicker_locale = $.datepicker.regional['de'];
            break;
    }
    


    /**************
 ** position erstellen, selectbox für typ ändert ein feld das den katalogpreis anzeigt
 **************/

$(function() {
    if ($("#typ").length) {        
        $("#typ").change(function() {
            $.ajax({
                "dataType": 'json', 
                "type": "GET", 
                "url": selectbox_datasource,
                "data": {
                    id: $("#typ").val()
                },
                "success": function(json) {
                    $("#typ-preis").val(pvhm_formatNumber(json.preis))
                }
            });
        });
    }
});