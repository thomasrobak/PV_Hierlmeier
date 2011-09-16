$.fn.dataTableExt.oSort['numeric-comma-asc']  = function(a,b) {
    var x = (a == "-") ? 0 : a.replace( /,/, "." );
    var y = (b == "-") ? 0 : b.replace( /,/, "." );
    x = parseFloat( x );
    y = parseFloat( y );
    return ((x < y) ? -1 : ((x > y) ?  1 : 0));
};

$.fn.dataTableExt.oSort['numeric-comma-desc'] = function(a,b) {
    var x = (a == "-") ? 0 : a.replace( /,/, "." );
    var y = (b == "-") ? 0 : b.replace( /,/, "." );
    x = parseFloat( x );
    y = parseFloat( y );
    return ((x < y) ?  1 : ((x > y) ? -1 : 0));
};



addCheckbox = function(data) {
    data.aoData.forEach(function(it){
        it.checkbox = "<input type='checkbox' " +
        "name='selected' " +
        "value='" + it.id + "' " +
        "/>"
    });
};

$(function() {
    var dt_locale_file
    /*
    switch(app_locale) {
        case "de":
            dt_locale_file = app_base_dir + "txt/datatable_de.txt";
            break;
        case "en":
            dt_locale_file = app_base_dir + "txt/datatable_en.txt";
            break;
        default:
            dt_locale_file = app_base_dir + "txt/datatable_en.txt";
            break;
    }*/
    dt_locale_file = app_base_dir + "txt/datatable_?.txt".replace("?", app_locale);
    
    var table_datasource
    var table_row_click_action
    var table_filter
    var table_request_params

    
    /**********************************
 *
 *  DataTable Konfiguration für Kunden Table
 *                                
 *********************************/
    
    if ($("#dt-kunde").length) {
        table_datasource = $("#dt-kunde").attr("datasource");
        table_row_click_action = $("#dt-kunde").attr("rowclickaction");
        table_filter = $("#dt-kunde").attr("filter");
        table_request_params = []
        table_request_params.push({
            "name": "filter", 
            "value": table_filter
        });
        
        var table_kunde = $("#dt-kunde").dataTable({
            "bAutoWidth": true,
            "bDeferRender": true,
            //"bStateSave": true,  @todo this enables the cookie
            "bProcessing": true,
            "sPaginationType": "two_button",
            "iCookieDuration": 60*60*12,
            "sCookiePrefix": "pvhm_datatable_",
            "sAjaxSource": table_datasource,
            "sAjaxDataProp": "aoData",
            "oLanguage": {
                "sUrl": dt_locale_file
            },
            "fnServerData": function ( sSource, aoData, fnCallback ) {
                table_request_params.forEach(function(value){
                    aoData.push(value)
                });
                $.ajax( {
                    "dataType": 'json', 
                    "type": "POST", 
                    "url": sSource, 
                    "data": aoData, 
                    "success": fnCallback
                } );
            },
            "aoColumnDefs": [
            {   /* kunde.id */
                "mDataProp": "id",
                "bSearchable": false,
                "aTargets": ["dt-kunde-th-id"]
            },
            {   /* kunde.nachname */
                "mDataProp": "nachname", 
                "sWidth": "70px", 
                "aTargets": ["dt-kunde-th-nachname"]
            },

            { /* kunde.bemerkung */
                "mDataProp": "bemerkung", 
                "bSortable": false,
                "sWidth": "150px", 
                "aTargets": ["dt-kunde-th-bemerkung"]
            },

            { /* kunde.wohnort */
                "mDataProp": "wohnort", 
                "bSortable": false, 
                "sWidth": "70px", 
                "aTargets": ["dt-kunde-th-wohnort"]
            },

            { /* kunde.mwst */
                "mDataProp": "mwst", 
                "bSortable": false,
                "sWidth": "10px",
                "aTargets": ["dt-kunde-th-mwst"]
            },

            { /* kunde.telefonnummer */
                "mDataProp": "telefonnummer", 
                "bSortable": false, 
                "sWidth": "60px", 
                "aTargets": ["dt-kunde-th-telefonnummer"]
            }      
            ]
        });
    
        if(table_row_click_action != null) {   
            $("#dt-kunde tbody tr").live("click",function(){
                var row_obj = table_kunde.fnGetData(this);
                var link = table_row_click_action.replace("_x_", row_obj.id);
                window.location.href = link;
            });
        }
    }
    
    
    /**********************************
 *
 *  DataTable Konfiguration für Positionen Table
 *                                
 *********************************/
    /***************
 *  table mit auswählbaren rows (per checkbox)
 ***************/
    
    if ($("#dt-position-interactive").length) {
        table_datasource = $("#dt-position-interactive").attr("datasource");
        table_row_click_action = $("#dt-position-interactive").attr("rowclickaction");
        table_filter = $("#dt-position-interactive").attr("filter");
        table_request_params = []
        table_request_params.push({
            "name": "filter", 
            "value": table_filter
        });
        if($("#dt-position-interactive").attr("kundeId")) {
            table_request_params.push({
                "name": "kundeId", 
                "value": $("#dt-position-interactive").attr("kundeId")
            });
        }
        
        var table_position_interactive = $("#dt-position-interactive").dataTable({
            "bAutoWidth": true,
            "bDeferRender": true,
            //"bStateSave": true,  @todo this enables the cookie
            "bProcessing": true,
            "sPaginationType": "two_button",
            "iCookieDuration": 60*60*12,
            "sCookiePrefix": "pvhm_datatable_",
            "sAjaxSource": table_datasource,
            "sAjaxDataProp": "aoData",
            "oLanguage": {
                "sUrl": dt_locale_file
            },
            "fnServerData": function ( sSource, aoData, fnCallback ) {
                table_request_params.forEach(function(value){
                    aoData.push(value)
                });
                $.ajax( {
                    "dataType": 'json', 
                    "type": "POST", 
                    "url": sSource, 
                    "data": aoData, 
                    "success": [addCheckbox, fnCallback]
                } );
            },
            "aoColumnDefs": [
            {   /* position.id */
                "mDataProp": "id",
                "bSearchable": false,
                "aTargets": ["dt-position-th-id"]
            },
            {   /* position.checkbox */
                "mDataProp": "checkbox",
                "sWidth": "20px",
                "bSearchable": false,
                "sSortDataType": "dom-checkbox",
                "aTargets": ["dt-position-th-checkbox"]
            },
            {   /* position.typ */
                "mDataProp": "typ.bezeichnung", 
                "sWidth": "70px", 
                "aTargets": ["dt-position-th-typ"]
            },
            { /* position.anmerkung */
                "mDataProp": "anmerkung", 
                "bSortable": false,
                "sWidth": "150px", 
                "aTargets": ["dt-position-th-anmerkung"]
            },
            { /* position.menge */
                "mDataProp": "menge", 
                "bSortable": false,
                "bSearchable": false,
                "sWidth": "30px", 
                "aTargets": ["dt-position-th-menge"]
            },
            { /* position.preis */
                "mDataProp": "preis", 
                "bSortable": false,
                "sWidth": "30px",
                "aTargets": ["dt-position-th-preis"]
            },
            { /* position.tier */
                "mDataProp": "tier.bezeichnung", 
                "sWidth": "50px", 
                "aTargets": ["dt-position-th-tier"]
            },
            { /* position.datum */
                "mDataProp": "datum", 
                "sType": "date",
                "bSortable": true,
                "bUseRendered": false,
                "sWidth": "60px", 
                "aTargets": ["dt-position-th-datum"]
            },
            { /* position.beleg */
                "mDataProp": "beleg",
                "sDefaultContent": "",
                "sWidth": "50px",
                "aTargets": ["dt-position-th-beleg"]
            },
            { /* position.kunde */
                "mDataProp": "kunde.name", 
                "sWidth": "60px", 
                "aTargets": ["dt-position-th-kunde"]
            }
            ]
        });
        
        if ($("#formBeleg").length) {
            $('#formBeleg').submit( function() {
                $("input", table_position_interactive.fnGetNodes()).each(function(){
                    if($(this).is(':checked')) {
                        $(this).appendTo('#formBeleg');
                    }
                });
            });
        }
        
        if(table_row_click_action != null) {   
            $("#dt-position-interactive tbody tr").live("click",function(){
                var row_obj = table_position_interactive.fnGetData(this);
                var link = table_row_click_action.replace("_x_", row_obj.id);
                window.location.href = link;
            });
        }
    }
    
    /***************
 *  positionen table standard liste ohne additional elements
 ***************/
    if ($("#dt-position-list").length) {
        table_datasource = $("#dt-position-list").attr("datasource");
        table_row_click_action = $("#dt-position-list").attr("rowclickaction");
        table_filter = $("#dt-position-list").attr("filter");
        table_request_params = []
        table_request_params.push({
            "name": "filter", 
            "value": table_filter
        });
        if($("#dt-position-list").attr("kundeId")) {
            table_request_params.push({
                "name": "kundeId", 
                "value": $("#dt-position-list").attr("kundeId")
            });
        }
        
        var table_position_list = $("#dt-position-list").dataTable({
            "bAutoWidth": true,
            "bDeferRender": true,
            //"bStateSave": true,  @todo this enables the cookie
            "bProcessing": true,
            "sPaginationType": "two_button",
            "iCookieDuration": 60*60*12,
            "sCookiePrefix": "pvhm_datatable_",
            "sAjaxSource": table_datasource,
            "sAjaxDataProp": "aoData",
            "oLanguage": {
                "sUrl": dt_locale_file
            },
            "fnServerData": function ( sSource, aoData, fnCallback ) {
                table_request_params.forEach(function(value){
                    aoData.push(value)
                });
                $.ajax( {
                    "dataType": 'json', 
                    "type": "POST", 
                    "url": sSource, 
                    "data": aoData, 
                    "success": fnCallback
                } );
            },
            "aoColumnDefs": [
            {   /* position.id */
                "mDataProp": "id",
                "bSearchable": false,
                "aTargets": ["dt-position-th-id"]
            },
            {   /* position.typ */
                "mDataProp": "typ.bezeichnung", 
                "sWidth": "70px", 
                "aTargets": ["dt-position-th-typ"]
            },
            { /* position.anmerkung */
                "mDataProp": "anmerkung", 
                "bSortable": false,
                "sWidth": "150px", 
                "aTargets": ["dt-position-th-anmerkung"]
            },
            { /* position.menge */
                "mDataProp": "menge", 
                "bSortable": false,
                "bSearchable": false,
                "sWidth": "30px", 
                "aTargets": ["dt-position-th-menge"]
            },
            { /* position.preis */
                "mDataProp": "preis", 
                "bSortable": false,
                "sWidth": "30px",
                "aTargets": ["dt-position-th-preis"]
            },
            { /* position.tier */
                "mDataProp": "tier.bezeichnung", 
                "sWidth": "50px", 
                "aTargets": ["dt-position-th-tier"]
            },
            { /* position.datum */
                "mDataProp": "datum", 
                "sType": "date",
                "bSortable": true,
                "bUseRendered": false,
                "sWidth": "60px", 
                "aTargets": ["dt-position-th-datum"]
            },
            { /* position.beleg */
                "sDefaultContent": "keiner",
                "sWidth": "50px",
                "fnRender": function(oObj){
                    if(oObj.aData['beleg'] == null)
                        return null
                    return oObj.aData['beleg']['belegnummer'];
                },
                "aTargets": ["dt-position-th-beleg"]
            },
            { /* position.kunde */
                "mDataProp": "kunde.name", 
                "sWidth": "60px", 
                "aTargets": ["dt-position-th-kunde"]
            }
            ]
        });
        
        if(table_row_click_action != null) {   
            $("#dt-position-list tbody tr").live("click",function(){
                var row_obj = table_position_list.fnGetData(this);
                var link = table_row_click_action.replace("_x_", row_obj.id);
                window.location.href = link;
            });
        }
        
        if ($("#from-date").length) {
            var from_date = $('#from-date')
            $("#from-date").datepicker({ showOn: "both" }); 
            $('#from-date').change( function() {
                table_position_list.fnDraw();
            } );
            
            var to_date = $('#to-date')
            $("#to-date").datepicker({ showOn: "both" }); 
            $('#to-date').change( function() {
                table_position_list.fnDraw();
            } );
            
            var datum_column_index
            var datum_column = $("#dt-datum-column").get()
            datum_column_index = table_position_list.fnGetPosition( datum_column );
                    
            $.fn.dataTableExt.afnFiltering.push(
                function (oSettings, aData, iDataIndex) {
                    var dStartDate = from_date.datepicker("getDate");
                    var dEndDate = to_date.datepicker("getDate");
                    var dCellDate = $.datepicker.parseDate($.datepicker.regional["de"].dateFormat, aData[datum_column_index]);

                    if (dCellDate == null)
                        return false;

                    if (dStartDate == null && dEndDate == null) {
                        return true;
                    }
                    else if (dStartDate == null && dCellDate < dEndDate) {
                        return true;
                    }
                    else if (dStartDate < dCellDate && dEndDate == null) {
                        return true;
                    }
                    else if (dStartDate < dCellDate && dCellDate < dEndDate) {
                        return true;
                    }
                    return false;
                });
        }
    }
    
            
    /**********************************
 *
 *  DataTable Konfiguration für Belege Table
 *                                
 *********************************/
    
    if ($("#dt-beleg").length) {
        table_datasource = $("#dt-beleg").attr("datasource");
        table_row_click_action = $("#dt-beleg").attr("rowclickaction");
        table_filter = $("#dt-beleg").attr("filter");
        table_request_params = []
        table_request_params.push({
            "name": "filter", 
            "value": table_filter
        }); 
        if($("#dt-beleg").attr("kundeId")) {
            table_request_params.push({
                "name": "kundeId", 
                "value": $("#dt-beleg").attr("kundeId")
            });
        }

        var table_beleg = $("#dt-beleg").dataTable({
            "bAutoWidth": true,
            "bDeferRender": true,
            //"bStateSave": true,  @todo this enables the cookie
            "bProcessing": true,
            "sPaginationType": "two_button",
            "iCookieDuration": 60*60*12,
            "sCookiePrefix": "pvhm_datatable_",
            "sAjaxSource": table_datasource,
            "sAjaxDataProp": "aoData",
            "oLanguage": {
                "sUrl": dt_locale_file
            },
            "fnServerData": function ( sSource, aoData, fnCallback ) {
                table_request_params.forEach(function(value){
                    aoData.push(value)
                });
                $.ajax( {
                    "dataType": 'json', 
                    "type": "POST", 
                    "url": sSource, 
                    "data": aoData, 
                    "success": [addCheckbox, fnCallback]
                } );
            },
            "aoColumnDefs": [
            {   /* beleg.id */
                "mDataProp": "id",
                "bSearchable": false,
                "aTargets": ["dt-beleg-th-id"]
            },
            {   /* beleg.checkbox */
                "mDataProp": "checkbox",
                "sWidth": "20px",
                "bSearchable": false,
                "sSortDataType": "dom-checkbox",
                "aTargets": ["dt-beleg-th-checkbox"]
            },
            {   /* beleg.belegnummer */
                "mDataProp": "belegnummer", 
                "sWidth": "30px", 
                "aTargets": ["dt-beleg-th-belegnummer"]
            },
            { /* beleg.betrag */
                "mDataProp": "betrag",
                "sWidth": "30px",
                "aTargets": ["dt-beleg-th-betrag"]
            },
            { /* beleg.summeBezahlt */
                "mDataProp": "summeBezahlt", 
                "sWidth": "30px", 
                "aTargets": ["dt-beleg-th-summebezahlt"]
            },
            { /* beleg.datum */
                "mDataProp": "datum", 
                "sType": "date",
                "bSortable": true,
                "bUseRendered": false,
                "sWidth": "60px", 
                "aTargets": ["dt-beleg-th-datum"]
            },
            { /* beleg.kunde */
                "mDataProp": "kunde", 
                "sWidth": "60px", 
                "aTargets": ["dt-beleg-th-kunde"]
            }
            ]
        });
        
        if ($("#formZahlung").length) {
            $('#formZahlung').submit( function() {
                $("input", table_beleg.fnGetNodes()).each(function(){
                    if($(this).is(':checked')) {
                        $(this).appendTo('#formZahlung');
                    }
                });
            });
        }
    
        if(table_row_click_action != null) {   
            $("#dt-beleg tbody tr").live("click",function(){
                var row_obj = table_beleg.fnGetData(this);
                var link = table_row_click_action.replace("_x_", row_obj.id);
                window.location.href = link;
            });
        }
    }
});


