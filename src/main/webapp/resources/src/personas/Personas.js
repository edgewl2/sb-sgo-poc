import DataTable from 'datatables.net';
import 'datatables.net-dt/css/jquery.dataTables.min.css';
// import $ from "jquery";

export default class Personas {
    constructor(config) {
        this.api = config.api;
        this.idTablaPersonas = config.idTablaPersonas;
        this.mostrarTooltips = config.mostrarTooltips;
        this.eliminarPersona = config.eliminarPersona;
        this.editarPersona = config.editarPersona;
    }

    render(personas) {
        this.tablaPersonas = new DataTable(`#${this.idTablaPersonas}`, {
            data: personas,
            autoWidth: true,
            responsive: true,
            language: {
                url: `${this.api.getContext()}/DataTables/i18n/es-CL.json`,
            },
            columns: [
                {
                    data: "id",
                    render: (data, type, row) => data
                },
                {
                    data: "nombre",
                    render: (data, type, row) => data
                },
                {
                    data: "apellido",
                    render: (data, type, row) => data
                },
                {
                    data: "pais",
                    render: (data, type, row) => data.nombre
                },
                {
                    data: null,
                    orderable: false,
                    searchable: false,
                    render: (data, type, row) => {
                        return `
                        <button class="btn btn-primary btn-sm" id="editarPersona" data-id="${row.id}" data-pais-id="${row.pais.id}" data-bs-toggle="tooltip" data-bs-target="#personaModal" data-bs-placement="bottom" title="Editar Persona"><i class="fas fa-edit"></i></button>
                        <button class="btn btn-danger btn-sm" id="eliminarPersona" data-id="${row.id}" data-pais-id="${row.pais.id}" data-bs-toggle="tooltip" data-bs-placement="bottom" title="Eliminar Persona"><i class="fa-solid fa-trash"></i></i></button>
                    `;
                    }
                }
            ],
            dom: '<"datatable-header"fl><"datatable-scroll-wrap"t><"datatable-footer"ip>'
        });

        this.tablaPersonas.on('draw.dt', () => {
            this.mostrarTooltips();
        });

        this.tablaPersonas.on('click', '#eliminarPersona', (event) => {
            const personaId = event.currentTarget.dataset.id;
            this.eliminarPersona(personaId);
        });

        this.tablaPersonas.on('click', '#editarPersona', (event) => {
            const personaId = event.currentTarget.dataset.id;
            const fila = $(event.currentTarget).closest('tr');
            const datosFila = this.tablaPersonas.row(fila).data();
            this.editarPersona(personaId, datosFila);
        });
    }
}