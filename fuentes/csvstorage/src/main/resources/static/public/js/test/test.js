// funciones javascript para la pantalla de prueba del servicio web

//-----------------------------> aqui empieza el document.ready
$(document).ready(function() {

    // Autocompletado del campo codigo DIR3 de unidad administrativa.
    $('#addDir3').autocomplete({
        source : $("#context").val() + '/autocomplete/dir3',
        focus : function(event, ui) {
            $(this).val(ui.item.label + " - " + ui.item.desc);
            $(this).next().val(ui.item.label);
            event.preventDefault();
        },
        select : function(event, ui) {
            $(this).val(ui.item.label + " - " + ui.item.desc);
            $(this).next().val(ui.item.label);
            event.preventDefault();
        }
    }).data("ui-autocomplete")._renderItem = function(ul, item) {
        return $("<li>").append("<a>" + item.label + " - " + item.desc + "</a>").appendTo(ul);
    };

});
//---> fin document.ready

//anade la unidad seleccionada en campo de texto
function nuevaUnidad() {

    var unidad = $("#addDir3").val();
    var codigoUnidad = $("#addDir3_codigo").val();

    if (unidad != '') {

        if (codigoUnidad == '') {
            // se setea el codigo de unidad igual que el nombre por si se ha escrito sin usar el autocompletado
            codigoUnidad = unidad;
        }

        var cont = 0;

        $(".unidades").find("input[type=text][readonly]").each(function() {
            cont++;
        });

        $("#li_addDir3").before(
                "<li id='li_unidad_" + cont + "'>" + "<input type=\"hidden\" id=\"codigo_unidad_" + cont
                        + "\" name=\"unidades[" + cont + "].codigo\" />" + "<input type=\"text\" id=\"unidad_" + cont
                        + "\" name=\"unidades[" + cont + "].nombre\" readonly=\"readonly\" />"
                        + "<a name=\"cancelUnidades\" class=\"secondary mf-icon-cancel\" id=\"cancel_unidad_" + cont
                        + "\" href=\"javascript:borrarUnidad('" + cont + "')\"></a>" + "</li>");

        $("#unidad_" + cont).val(unidad);
        $("#codigo_unidad_" + cont).val(codigoUnidad);
        $("#addDir3").val('');
        $("#addDir3_codigo").val('');
    }

}

//borra las unidades anadidas y reorganiza el resto para recoger bien los datos
function borrarUnidad(index) {

    $("#li_unidad_" + index).remove();

    var cont = 0;
    // reorganizar los indices en los name de las unidades de las cajas de texto
    $(".unidades").find("input[type=text][readonly]").each(function() {
        $(this).parent("li").attr("id", "li_unidad_" + cont);
        $(this).attr("name", "unidades[" + cont + "].nombre");
        $(this).attr("id", "unidad_" + cont);
        $(this).prev().attr("name", "unidades[" + cont + "].codigo");
        $(this).prev().attr("id", "codigo_unidad_" + cont);
        cont++;
    });

    cont = 0;
    $('[name="cancelUnidades"]').each(function() {
        $(this).attr("href", "javascript:borrarUnidad('" + cont + "')");
        $(this).attr("id", "cancel_unidad_" + cont);
        cont++;
    });
}