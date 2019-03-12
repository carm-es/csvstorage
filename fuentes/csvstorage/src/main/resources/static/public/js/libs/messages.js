$(document).ready(function() {
    showMessage();
});

// muestra mensaje de usuario en la pantalla
function showMessage() {

    deleteLevelMessageStyles();

    var tipo = $("#tipoMensaje").val();
    var mensaje = $("#valorMensaje").val();
    if (tipo != "" && mensaje != "") {
        $("#divMessage").removeClass("dsp_n");
        if (tipo == "1") {
            $("#divMessage").addClass("msg-success");
        } else if (tipo == "2") {
            $("#divMessage").addClass("msg-info");
        } else if (tipo == "3") {
            $("#divMessage").addClass("msg-warning");
        } else {
            $("#divMessage").addClass("msg-error");
        }
        $("#mensajeMostrar").text(mensaje);
    }
}

// oculta el mensaje de usuario en la pantalla
function ocultarMensaje() {
    deleteLevelMessageStyles();
    deleteErrorMessages();
    $("#divMessage").addClass("dsp_n");
}

// borra los estilos por niveles definidos para los mensajes de usuario
function deleteLevelMessageStyles() {
    $("#divMessage").removeClass("msg-success msg-info msg-warning msg-error");
}

// borra los mensajes y estilos de error de la pantalla
function deleteErrorMessages() {
    $("input").removeClass("error");
    $("label[class=error]").remove();
}
