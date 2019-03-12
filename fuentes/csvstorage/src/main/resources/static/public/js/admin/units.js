// Carga las unidades
function loadUnits() {

    $.ajax({
        url : "admin/units",
        dataType : 'html',
        type : 'POST',
        success : function(model) {
            $("#ajaxSpinnerImage").show();
            $("#tab2").html(model);
        },
        error : function error() {
            $("#tipoMensaje").val(4);
            $("#valorMensaje").val($("#generic_error").val());
            showMessage();

        }
    });

}

// it used for getting the organics units from VolcadoBasicoDatos and
// IncrementalDatos Services
function saveUnits() {

    $.ajax({
        url : "admin/units/save",
        dataType : 'html',
        type : 'POST',
        success : function(model) {

            $("#tab2").html(model);

        },
        error : function error() {
            $("#tipoMensaje").val(4);
            $("#valorMensaje").val($("#generic_error").val());
            showMessage();

        },
        beforeSend : function() {
            $("#ajaxSpinnerImage").show();
        },
        // hides the loader after completion of request, whether successfull or
        // failor.
        complete : function() {
            $("#ajaxSpinnerImage").hide();
        }
    });

}

function saveAllUnits() {

    $.ajax({
        url : "admin/units/saveAll",
        dataType : 'html',
        type : 'POST',
        success : function(model) {

            $("#tab2").html(model);

        },
        error : function error() {
            $("#tipoMensaje").val(4);
            $("#valorMensaje").val($("#generic_error").val());
            showMessage();

        },
        beforeSend : function() {
            $("#ajaxSpinnerImage").show();
        },
        // hides the loader after completion of request, whether successfull or
        // failor.
        complete : function() {
            $("#ajaxSpinnerImage").hide();
        }
    });

}

function saveUnit() {

    ocultarMensaje();

    unidadOrganica = $("#dir3").val();
    if (!unidadOrganica) {
        $("#tipoMensaje").val(4);
        $("#valorMensaje").val('DIR3 Obligatorio');
        showMessage();
    }

    $.ajax({
        url : "admin/units/manualSave?codigo=" + unidadOrganica,
        type : 'POST',
        async : true,
        success : function(data) {
            $("#tipoMensaje").val(1);
            $("#valorMensaje").val('DIR3 Insertado');
            showMessage();
            $('#tableUnidades').DataTable().ajax.reload();
        },
        error : function error(e) {
            console.log(e);
            $("#tipoMensaje").val(4);
            $("#valorMensaje").val($("#generic_error").val());
            showMessage();
        },
    });

}

function saveUnitsByDate() {

    ocultarMensaje();

    fecha = $("#dateBy").val();
    if (!fecha) {
        $("#tipoMensaje").val(4);
        $("#valorMensaje").val('"Desde el día" es obligatorio');
        showMessage();

    } else {

        $.ajax({
            url : "admin/units/saveUnitsByDate?fecha=" + fecha,
            type : 'POST',
            async : true,
            success : function(model) {

                $("#tab2").html(model);

            },
            error : function error() {
                $("#tipoMensaje").val(4);
                $("#valorMensaje").val($("#generic_error").val());
                showMessage();

            },
            beforeSend : function() {
                $("#ajaxSpinnerImage").show();
            },
            // hides the loader after completion of request, whether successfull or
            // failor.
            complete : function() {
                $("#ajaxSpinnerImage").hide();
            }
        });
    }
}

function deleteUnit(dir3) {

    ocultarMensaje();

    unidadOrganica = dir3;
    if (!unidadOrganica) {
        $("#tipoMensaje").val(4);
        $("#valorMensaje").val('DIR3 Obligatorio');
        showMessage();
    }

    $.ajax({
        url : "admin/units/manualDelete?codigo=" + unidadOrganica,
        type : 'POST',
        async : true,
        success : function(data) {
            $("#tipoMensaje").val(1);
            $("#valorMensaje").val('DIR3 Eliminado');
            showMessage();
            $('#tableUnidades').DataTable().ajax.reload();
        },
        error : function error(e) {
            console.log(e);
            $("#tipoMensaje").val(4);
            $("#valorMensaje").val($("#generic_error").val());
            showMessage();
        },
    });

}

function mostrarUnidades() {

    $.ajax({
        url : "admin/units/list",
        dataType : 'html',
        type : 'POST',
        success : function(model) {

            $("#tab2").html(model);

        },
        error : function error() {
            $("#tipoMensaje").val(4);
            $("#valorMensaje").val($("#generic_error").val());
            showMessage();

        },
        beforeSend : function() {
            $("#ajaxSpinnerImage").show();
        },
        // hides the loader after completion of request, whether successfull or
        // failor.
        complete : function() {
            $("#ajaxSpinnerImage").hide();
        }
    });

}

function consultardir3(unidadOrganica) {

    $.ajax({
        url : "admin/units/consultar",
        type : 'GET',
        contentType : "application/json; charset=utf-8",
        dataType : 'json',
        async : true,
        data : {
            'unidadOrganica' : unidadOrganica
        },
        success : function(data) {

            if (data.unidadOrganica == "null") {
                $("#unidadOrganica").val("");
            } else {
                $("#unidadOrganica").val(data.unidadOrganica);
            }

            if (data.nombre == "null") {
                $("#nombre").val("");
            } else {
                $("#nombre").val(data.nombre);
            }

            if (data.nivelAdministracion == "null") {
                $("#nivelAdministracion").val("");
            } else {
                $("#nivelAdministracion").val(data.nivelAdministracion);
            }

            if (data.nivelJerarquico == "null") {
                $("#nivelJerarquico").val("");
            } else {
                $("#nivelJerarquico").val(data.nivelJerarquico);
            }

            if (data.codigoUnidadSuperior == "null") {
                $("#codigoUnidadSuperior").val("");
            } else {
                $("#codigoUnidadSuperior").val(data.codigoUnidadSuperior);
            }

            if (data.nombreUnidadSuperior == "null") {
                $("#nombreUnidadSuperior").val("");
            } else {
                $("#nombreUnidadSuperior").val(data.nombreUnidadSuperior);
            }

            if (data.codigoUnidadRaiz == "null") {
                $("#codigoUnidadRaiz").val("");
            } else {
                $("#codigoUnidadRaiz").val(data.codigoUnidadRaiz);
            }

            if (data.nombreUnidadRaiz == "null") {
                $("#nombreUnidadRaiz").val("");
            } else {
                $("#nombreUnidadRaiz").val(data.nombreUnidadRaiz);
            }

            if (data.fechaCreacion == "null") {
                $("#fechaCreacion").val("");
            } else {
                var date = new Date(data.fechaCreacion);
                $("#fechaCreacion").val(date.toLocaleDateString());
            }

            $("#unidadOrganica").attr("readonly", "readonly");
            $("#nombre").attr("readonly", "readonly");
            $("#nivelAdministracion").attr("readonly", "readonly");
            $("#nivelJerarquico").attr("readonly", "readonly");
            $("#codigoUnidadSuperior").attr("readonly", "readonly");
            $("#nombreUnidadSuperior").attr("readonly", "readonly");
            $("#codigoUnidadRaiz").attr("readonly", "readonly");
            $("#nombreUnidadRaiz").attr("readonly", "readonly");
            $("#fechaCreacion").attr("readonly", "readonly");

        },
        error : function error() {
            $("#tipoMensaje").val(4);
            $("#valorMensaje").val($("#generic_error").val());
            showMessage();

        },
        beforeSend : function() {
            $("#ajaxSpinnerImage").show();
        },
        // hides the loader after completion of request, whether successfull or
        // failor.
        complete : function() {
            $("#ajaxSpinnerImage").hide();
        }
    });

}
