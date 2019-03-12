var isloadApplications = false;

// Carga los endpoints
function loadApplications() {
    if (!isloadApplications) {

        $.ajax({
            url : "admin/aplicacion",
            dataType : 'html',
            type : 'POST',
            success : function(model) {

                $("#tab1").html(model);
                limpiarCamposAplicacion();
                loadApplicationFieldsAutocomplete();
            },
            error : function error() {
                $("#tipoMensaje").val(4);
                $("#valorMensaje").val($("#generic_error").val());
                showMessage();

            }
        });

        isloadApplications = true;
    } else {
        reloadApplications();
    }
}

//Recarga los endpoints
function reloadApplications() {

    $.ajax({
        url : "admin/aplicacion/reload",
        dataType : 'html',
        type : 'POST',
        async : true,
        success : function(model) {

            $("#listaplicaciones").html(model);
            limpiarCamposAplicacion();
            loadApplicationFieldsAutocomplete();
            loadApplicationsDatatable();

        },
        error : function error() {
            $("#tipoMensaje").val(4);
            $("#valorMensaje").val($("#generic_error").val());
            showMessage();

        }
    });

}

/**
 * Función que guarda el identificador del usuario y hace submit del formulario
 * para editar la aplicación.
 * 
 * @param idEndpoint
 */
function editApplication(idAplicacion) {
    $.ajax({
        url : "admin/aplicacion/edit",
        dataType : 'html',
        type : 'POST',
        async : true,
        data : {
            'idAplicacion' : idAplicacion
        },
        async : true,
        success : function(model) {
            $("#edit-applications").html(model);
            $("#idAplicacion").prop('disabled', true);
            $("[name='documentosPdfYEni']")[0].checked = true;
            loadApplicationFieldsAutocomplete();
            habilitarEscrituraCmis();
        },
        error : function error() {
            $("#tipoMensaje").val(4);
            $("#valorMensaje").val($("#generic_error").val());
            showMessage();
            $("#idAplicacion").prop('disabled', false);

        }
    });

}

/**
 * Preparación para el borrado de aplicación.
 * 
 * @param index
 * @param idAplicacion
 */
function prepareDeleteApplication(index, id) {
    //mensaje de confirmación para borrar
    $('#question_type').html(
            $("#btn_delete_application_" + index).data('confirm-question-type') + $("#id_aplicacion_" + index).text());
    $('#yes_button').attr('onclick', "deleteApplication('" + id + "');");
    $('#confirm_message').show();
}

//Elimina la aplicación consumidora
function deleteApplication(id, idAplicacion) {

    $('#confirm_message').hide();
    $("#hiddenIdAplicacion").val(id);
    $("#idAplicacion").prop('disabled', false);

    $.ajax({
        url : "admin/aplicacion/borrar",
        type : 'POST',
        dataType : 'json',
        data : {
            'id' : id

        },
        async : true,
        success : function(data) {

            var mensaje = eval(data);

            var error_level = mensaje.level;
            var error_message = mensaje.message;

            if (error_level != '' && error_message != '') {
                $("#tipoMensaje").val(error_level);
                $("#valorMensaje").val(error_message);
                showMessage();
            }

            if (error_level != '' && error_level == '1') {
                reloadApplications();
                $("#idAplicacion").prop('disabled', true);
            }

        },
        error : function error() {
            $("#tipoMensaje").val(4);
            $("#valorMensaje").val($("#generic_error").val());
            showMessage();
            $("#idAplicacion").prop('disabled', true);

        }
    });

}

//Guarda la aplicación
function saveAplicacion() {
    $("[name='documentosPdfYEni']")[0].checked = true;
    $("#documentosPdfYEni").prop('disabled', false);
    $('#confirm_message').hide();
    $("#idAplicacion").prop('disabled', false);
    $("#permisoLectura").prop('disabled', false);

    $.ajax({
        url : "admin/aplicacion/guardar",
        type : 'POST',
        dataType : 'html',
        data : $("#form_save_application").serialize(),
        async : true,
        success : function(data) {
            $("#edit-applications").html(data);
            reloadApplications();

            //loadApplicationFieldsAutocomplete();

        },
        error : function error() {
            $("#tipoMensaje").val(4);
            $("#valorMensaje").val($("#generic_error").val());
            showMessage();
            $("#idAplicacion").prop('disabled', true);

        }
    });

}

function habilitarEscrituraCmis() {
    if ($("[name='lecturaCmis']")[0].checked) {
        $("[name='escrituraCmis']").prop('disabled', false);
    } else {
        $("[name='escrituraCmis']")[0].checked = false;
        $("[name='escrituraCmis']").prop('disabled', true);
    }
}

function limpiarCamposAplicacion() {

    $("#idAplicacion").prop('disabled', false);
    $("#hiddenIdAplicacion").val("");
    $("[id='id']").val("");
    $("[id='idAplicacionPublico']").val("");
    $("[id='idAplicacion']").val("");
    $("[id='descripcion']").val("");
    $("[id='password']").val("");
    $("[id='unidadApplication']").val("");
    $("[name='admin']")[0].checked = false;
    $("[name='activo']")[0].checked = false;
    $("#nombreResponsable").val("");
    $("#emailResponsable").val("");
    $("#telefonoResponsable").val("");
    $("[name='validarDocumentoENI']")[0].checked = false;
    $("#serialNumberCertificado").val("");
    $("[name='lecturaCmis']")[0].checked = false;
    $("[name='escrituraCmis']")[0].checked = false;
    $("#escrituraCmis").prop('disabled', true);
    $("#permisoLectura").prop('disabled', true);
    $('#permisoLectura').val(1);
    $("[name='documentosPdfYEni']")[0].checked = true;
    $("#documentosPdfYEni").prop('disabled', true);
}

function showInfoApplication(texto) {
    $("#applicationInfo").html("<div class='fld'>" + texto + "</div>");
    $("#applicationInfo").removeClass("hidden");

    dialog = $("#applicationInfo").dialog({
        autoOpen : false,
        dialogClass : "no-close",
        height : 200,
        width : 250,
        modal : true,
        buttons : {
            "Aceptar" : function() {
                dialog.dialog("close");
            }
        },
    });
    dialog.dialog("open");

}

function seleccionarCertificado() {

    $("#popupSelecAlias").val("");
    $("#popupSelecCertificado").val("");
    $("#errorPopupCertificado").html("");

    $("#popupImportCertificate").removeClass("hidden");

    dialog = $("#popupImportCertificate")
            .dialog(
                    {
                        autoOpen : false,
                        dialogClass : "no-close",
                        height : 300,
                        width : 400,
                        modal : true,
                        buttons : {
                            "Aceptar" : function() {
                                $
                                        .ajax({
                                            url : "admin/aplicacion/importCertificate",
                                            type : 'POST',
                                            processData : false,
                                            contentType : false,
                                            cache : false,
                                            enctype : 'multipart/form-data',
                                            data : new FormData($("#form_popup_save_cert")[0]),
                                            async : true,
                                            success : function(data) {
                                                //var resp = JSON.parse(data);
                                                var resp = data;
                                                if (resp.level == "1") {
                                                    var serialNumber = resp.message;
                                                    $("#serialNumberCertificado").val(serialNumber);
                                                    dialog.dialog("close");
                                                } else {
                                                    var msgError = "<h5 style='color:red'>Error al importar certificado:</h5> <p style='color:red'>"
                                                            + resp.message + "</p>";
                                                    $("#errorPopupCertificado").html(msgError);
                                                }

                                            },
                                            error : function error() {
                                                $("#tipoMensaje").val(4);
                                                $("#valorMensaje").val($("#generic_error").val());
                                                $("#errorExp").html("Error al importar el certificado");
                                                showMessage();

                                            }
                                        });
                            },

                            "Cerrar" : function() {
                                dialog.dialog("close");
                            }
                        },
                    });
    dialog.dialog("open");

}

function cleanSerialNumber() {
    $("#serialNumberCertificado").val("");
}

function loadApplicationFieldsAutocomplete() {
    // Autocompletado del campo codigo DIR3 del organismo.
    $('#unidadApplication').autocomplete({
        source : $("#context").val() + '/autocomplete/dir3',
        focus : function(event, ui) {
            $("#unidadApplication").val(ui.item.label + " - " + ui.item.desc);
            event.preventDefault();
        },
        select : function(event, ui) {
            $('#unidadApplication').val(ui.item.label + " - " + ui.item.desc);
            event.preventDefault();
        }
    }).data("ui-autocomplete")._renderItem = function(ul, item) {
        return $("<li>").append("<a>" + item.label + " - " + item.desc + "</a>").appendTo(ul);
    };
}