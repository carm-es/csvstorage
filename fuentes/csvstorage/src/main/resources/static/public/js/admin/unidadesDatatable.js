$(document).ready(
        function() {

            $(function() {
                // this initializes the dialog
                $("#dialog").dialog({
                    autoOpen : false,
                    modal : true,
                    width : "auto",
                    height : "auto",
                    create : function() {
                        $(".ui-dialog").addClass("ui-widget-contentunits")
                    }

                })

            });

            loadUnidadesDatatable();

            $("#tableUnidades").on(
                    'draw.dt',
                    function() {
                        $(".dt-view").each(
                                function() {
                                    $(this).addClass('btn-table-action btn__no-text').append(
                                            "<span class='mf-icon mf-icon-eye' aria-hidden='true'></span>");
                                    $(this).on('click', function() {
                                        var table = $('#tableUnidades').DataTable();
                                        var data = table.row($(this).parents('tr')).data();
                                        consultardir3(data.unidadOrganica);
                                        $("#dialog").dialog("open");
                                    });
                                });
                        $(".dt-delete").each(
                                function() {
                                    $(this).addClass('btn-table-action btn__no-text').append(
                                            "<span class='mf-icon mf-icon-delete' aria-hidden='true'></span>");
                                    $(this).on('click', function() {
                                        var table = $('#tableUnidades').DataTable();
                                        var data = table.row($(this).parents('tr')).data();
                                        deleteUnit(data.unidadOrganica);
                                    });
                                });
                    });

        });

function loadUnidadesDatatable() {
    tableCertificate = $('#tableUnidades').dataTable({
        "ajax" : {
            "url" : "admin/units/list/datatable.jquery",
            "data" : function(d) {
                var table = $('#tableUnidades').DataTable()
                d.page = (table != undefined) ? table.page.info().page : 0;
                d.size = (table != undefined) ? table.page.info().length : 15;
                d.sort = d.columns[d.order[0].column].data + ',' + d.order[0].dir;
                d.search = table.search;
            },

        },
        "searching" : true,
        "processing" : true,
        "serverSide" : true,
        "lengthMenu" : [ [ 15, 30, 50, 75, 100 ], [ 15, 30, 50, 75, 100 ] ],
        "columns" : [ {
            "data" : "unidadOrganica"
        }, {
            "data" : "nombre"
        }, {
            "" : ""
        }, {
            "" : ""
        } ],
        "columnDefs" : [ {
            "data" : null,
            "targets" : -2,
            "defaultContent" : "<h4><a class='dt-view'></a></h4>"
        }, {
            "data" : null,
            "targets" : -1,
            "defaultContent" : "<h4><a class='dt-delete'></a></h4>"
        } ],
        "pagingType" : "full_numbers",
        "language" : {
            "sProcessing" : "Procesando...",
            "sLengthMenu" : "Mostrar _MENU_",
            "sZeroRecords" : "No se encontraron resultados",
            "sEmptyTable" : "Ningún dato disponible",
            "sInfo" : "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
            "sInfoEmpty" : "Mostrando registros del 0 al 0 de un total de 0 registros",
            "sInfoFiltered" : "(filtrado de un total de _MAX_ registros)",
            "sInfoPostFix" : "",
            "sSearch" : "Buscar",
            "sUrl" : "",
            "sInfoThousands" : ",",
            "sLoadingRecords" : "Cargando...",
            "oPaginate" : {
                "sFirst" : "Primero",
                "sLast" : "Último",
                "sNext" : "Siguiente",
                "sPrevious" : "Anterior"
            },
            "oAria" : {
                "sSortAscending" : ": Activar para ordenar la columna de manera ascendente",
                "sSortDescending" : ": Activar para ordenar la columna de manera descendente"
            }
        }
    });

}
