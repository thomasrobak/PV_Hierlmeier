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
                table_request_params.forEach(function(value){aoData.push(value)});
                $.ajax( {
                    "dataType": 'json', 
                    "type": "POST", 
                    "url": sSource, 
                    "data": aoData, 
                    "success": fnCallback
                } );
            },
            "aoColumnDefs": [
            {   /* Kunde.id */
                "mDataProp": "id",
                "bSearchable": false,
                "aTargets": ["dt-kunde-th-id"]
            },
            {   /* Kunde.nachname */
                "mDataProp": "nachname", 
                "sWidth": "70px", 
                "aTargets": ["dt-kunde-th-nachname"]
            },

            { /* Kunde.bemerkung */
                "mDataProp": "bemerkung", 
                "bSortable": false,
                "sWidth": "150px", 
                "aTargets": ["dt-kunde-th-bemerkung"]
            },

            { /* Kunde.wohnort */
                "mDataProp": "wohnort", 
                "bSortable": false, 
                "sWidth": "70px", 
                "aTargets": ["dt-kunde-th-wohnort"]
            },

            { /* Kunde.mwst */
                "mDataProp": "mwst", 
                "bSortable": false,
                "sWidth": "10px",
                "aTargets": ["dt-kunde-th-mwst"]
            },

            { /* Kunde.telefonnummer */
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
    
    if ($("#dt-position").length) {
        table_datasource = $("#dt-position").attr("datasource");
        table_row_click_action = $("#dt-position").attr("rowclickaction");
        table_filter = $("#dt-position").attr("filter");
        table_request_params = []
        table_request_params.push({
            "name": "filter", 
            "value": table_filter
        });
        if($("#dt-position").attr("kundeId").length) {
            table_request_params.push({
                "name": "kundeId", 
                "value": $("#dt-position").attr("kundeId")
            });
        }
        
        var table_position = $("#dt-position").dataTable({
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
                table_request_params.forEach(function(value){aoData.push(value)});
                $.ajax( {
                    "dataType": 'json', 
                    "type": "POST", 
                    "url": sSource, 
                    "data": aoData, 
                    "success": [addCheckbox, fnCallback]
                } );
            },
            "aoColumnDefs": [
            {   /* Position.id */
                "mDataProp": "id",
                "bSearchable": false,
                "aTargets": ["dt-position-th-id"]
            },
            {   /* Position.checkbox */
                "mDataProp": "checkbox",
                "sWidth": "20px",
                "bSearchable": false,
                "sSortDataType": "dom-checkbox",
                "aTargets": ["dt-position-th-checkbox"]
            },
            {   /* Position.typ */
                "mDataProp": "typ.bezeichnung", 
                "sWidth": "70px", 
                "aTargets": ["dt-position-th-typ"]
            },
            { /* Position.anmerkung */
                "mDataProp": "anmerkung", 
                "bSortable": false,
                "sWidth": "150px", 
                "aTargets": ["dt-position-th-anmerkung"]
            },
            { /* Position.menge */
                "mDataProp": "menge", 
                "bSortable": false,
                "bSearchable": false,
                "sWidth": "30px", 
                "aTargets": ["dt-position-th-menge"]
            },
            { /* Position.preis */
                "mDataProp": "preis", 
                "bSortable": false,
                "sWidth": "30px",
                "aTargets": ["dt-position-th-preis"]
            },
            { /* Position.tier */
                "mDataProp": "tier.bezeichnung", 
                "sWidth": "50px", 
                "aTargets": ["dt-position-th-tier"]
            },
            { /* Position.datum */
                "mDataProp": "datum", 
                "sType": "date",
                "bSortable": true,
                "bUseRendered": false,
                "sWidth": "60px", 
                "aTargets": ["dt-position-th-datum"]
            },
            { /* Position.beleg */
                "mDataProp": "beleg.belegnummer", 
                "sWidth": "60px", 
                "aTargets": ["dt-position-th-beleg"]
            },
            { /* Position.kunde */
                "mDataProp": "kunde.name", 
                "sWidth": "60px", 
                "aTargets": ["dt-position-th-kunde"]
            }
            ]
        });
        
        $('#formBeleg').submit( function() {
            $("input", otable.fnGetNodes()).each(function(){
                if($(this).is(':checked')) {
                    $(this).appendTo('#formBeleg');
                }
            });
        });
    
        if(table_row_click_action != null) {   
            $("#dt-position tbody tr").live("click",function(){
                var row_obj = table_position.fnGetData(this);
                var link = table_row_click_action.replace("_x_", row_obj.id);
                window.location.href = link;
            });
        }
    }
});


