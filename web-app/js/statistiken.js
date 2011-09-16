$(function() {  
    $.ajax({
        "dataType": 'json', 
        "type": "GET", 
        "url": statistik_overview, 
        "cache": false,
        "success": function(json) {
            $("#kundecount").html(json.kundeTotal)
            $("#belegcount").html(json.belegTotal)
            $("#belegunbeglichen").html(json.belegOpen)
            $("#positioncount").html(json.positionTotal)
            $("#positionunzugewiesen").html(json.positionOpen)
            $("#medikamentcount").html(json.medikamentTotal)
            $("#leistungcount").html(json.leistungTotal)
            $("#tiercount").html(json.tierTotal)
            $("#zahlungcount").html(json.zahlungTotal)
        }
    });
});