import '../scss/styles.scss';
import {Tooltip, Modal} from 'bootstrap';

import Api from "./Api";
import ApiPaises from "../paises/Api"
import Personas from "./Personas";  //Datatable
import {showError} from "../commons/Utils";

export default class App {
    constructor(config) {
        this.api = new Api(config);
        this.apiPaises = new ApiPaises(config);
        this.personas = new Personas({
            idTablaPersonas: 'personas',
            api: this.api,
            mostrarTooltips: this.mostrarTooltips,
            eliminarPersona: this.eliminarPersona,
            editarPersona: this.editarPersona,
            ...config
        });

        console.log(this);
        console.log(config)

        this.renderPersonas();
        this.mostrarTooltips();
        this.renderModalRegistrar();
    }

    mostrarTooltips = () => {
        const listaTooltips = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
        listaTooltips.forEach(tooltip => {
            new Tooltip(tooltip, {
                placement: tooltip.getAttribute('data-bs-placement') || 'auto'
            });
        });
    }

    renderPersonas = () => {
        const personas = this.api.listarPersonas();

        personas.then(data => {
            if (this.personas.tablaPersonas) {
                this.personas.tablaPersonas.ajax.reload(null, false);
            } else {
                this.personas.render(data);
            }
        }).catch(error => showError(error));
    }

    renderModalRegistrar = () => {
        const registrarBoton = document.querySelector('.btn[data-bs-target="#registrarModal"]');

        registrarBoton.addEventListener('click', (event) => {
            this.limpiarModalRegistrarForm();
            this.registrarPersona();
        });
    }

    limpiarModalRegistrarForm = () => {
        const paisId = document.querySelector('#personaPais');
        document.querySelector('#personaNombre').value = '';
        document.querySelector('#personaApellido').value = '';
        paisId.value = '';

        this.cargarModalRegistrarFormSelectPaises(paisId);
    }

    cargarModalRegistrarFormSelectPaises = (paisId) => {
        return new Promise((resolve, reject) => {
            this.apiPaises.obtenerPaises().then(paises => {
                paises.forEach((data) => {
                    const optionElement = document.createElement('option');
                    optionElement.value = data.id;
                    optionElement.textContent = data.nombre;
                    paisId.appendChild(optionElement);
                    resolve();
                });
            }).catch(error => reject(error));
        });
    }

    activarModalRegistrarBoton = (formulario, boton) => {

        const listaEntradas = formulario.querySelectorAll('input, select');

        listaEntradas.forEach(entrada => {
            entrada.addEventListener('change', () => {
                this.validarModalRegistrarForm(formulario, boton);
            });
        });
    }

    validarModalRegistrarForm = (formulario, boton) => {
        if (formulario.checkValidity()) {
            boton.classList.remove('disabled');
            boton.parentNode.removeEventListener('click', this.referModalRegistrarAlertaClick);
        } else {
            boton.classList.add('disabled');
        }
    }

    renderModalRegistrarAlerta = () => {
        const modalRegistrarBoton = document.querySelector('#registrarPersona');
        const modalRegistrarBotonPadre = modalRegistrarBoton.parentNode

        this.referModalRegistrarAlertaClick = (event) => {
            this.renderModalRegistrarAlertaContenedor(event, modalRegistrarBoton);
        };

        modalRegistrarBotonPadre.addEventListener('click', this.referModalRegistrarAlertaClick)
    }

    renderModalRegistrarAlertaContenedor = (event, boton) => {
        const modalRegistrarAlerta = document.querySelector('#registrarAlertaForm');

        if (boton.classList.contains('disabled')) {
            event.preventDefault();

            modalRegistrarAlerta.classList.remove('d-none')
            modalRegistrarAlerta.classList.remove('fade');
            modalRegistrarAlerta.classList.add('show');

            setTimeout(() => {
                modalRegistrarAlerta.classList.remove('show');
                modalRegistrarAlerta.classList.add('fade');
            }, 4000)

            setTimeout(() => {
                modalRegistrarAlerta.classList.add('d-none')
            }, 4500)
        }
    }

    /**
     * Funcion para registrar personas
     */
    registrarPersona = () => {
        const modalRegistrarForm = document.querySelector('#registrarForm');
        const modalRegistrarBoton = document.querySelector('#registrarPersona');
        const modalRegistrar = new Modal(document.querySelector('#registrarModal'));

        modalRegistrar.show();
        this.renderModalRegistrarAlerta();
        this.activarModalRegistrarBoton(modalRegistrarForm, modalRegistrarBoton);

        modalRegistrarBoton.addEventListener('click', () => {
            this.guardarPersona(null, modalRegistrar)
        }, {once: true});
    }

    eliminarPersona = (personaId) => {
        const modalEliminarBoton = document.querySelector('#eliminarPersona');
        const modalEliminar = new Modal(document.querySelector('#eliminarModal'));

        modalEliminar.show();

        modalEliminarBoton.addEventListener('click', () => {
            this.api.eliminarPersona(personaId)
                .then((data) => {
                    modalEliminar.hide();
                    const table = this.personas.tablaPersonas;
                    const rowsCount = table.rows().count();

                    for (let i = 0; i < rowsCount; i++) {
                        if (table.cell(i, 0).data().toString() === personaId.toString()) {
                            table.row(i).remove().draw();
                            break;
                        }
                    }
                })
                .catch(error => {
                    showError(error);
                    console.error(error);
                });
        });
    }

    editarPersona = (personaId, datos) => {
        const modalEditarBoton = document.querySelector('#registrarPersona');
        const modalEditar = new Modal(document.querySelector('#registrarModal'));
        const modalEditarTitulo = document.querySelector('#registrarModalTitle');
        const modalEditarForm = this.obtenerRegistrarModalForm();

        this.cargarModalRegistrarFormSelectPaises(modalEditarForm.pais).then(() => {
            for (let i = 0; i < modalEditarForm.pais.options.length; i++) {
                if (modalEditarForm.pais.options[i].value === datos.pais.id) {
                    modalEditarForm.pais.options[i].selected = true;
                    break;
                }
            }
        });
        modalEditarForm.nombre.value = datos.nombre;
        modalEditarForm.apellido.value = datos.apellido;
        modalEditarTitulo.textContent = 'Editar Persona';
        modalEditarBoton.classList.remove('disabled');

        modalEditar.show();

        modalEditarBoton.addEventListener('click', () => {
            this.guardarPersona(personaId, modalEditar);
        }, {once: true});
    }

    obtenerRegistrarModalForm = () => {
        let pais = document.querySelector('#personaPais');
        let paisOpcion = pais.options[pais.selectedIndex];
        let nombre = document.querySelector('#personaNombre');
        let apellido = document.querySelector('#personaApellido');

        return {
            nombre: nombre,
            apellido: apellido,
            pais: pais
        };
    }

    guardarPersona(personaId, modal) {
        const modalRegistrarFormInputs = this.obtenerRegistrarModalForm();
        const paisOpcion = modalRegistrarFormInputs.pais.options[modalRegistrarFormInputs.pais.selectedIndex];
        const datos = {
            nombre: modalRegistrarFormInputs.nombre.value,
            apellido: modalRegistrarFormInputs.apellido.value,
            pais: {
                id: paisOpcion.value,
                nombre: paisOpcion.text
            }
        };

        event.preventDefault();

        if (personaId) {
            datos.id = personaId;
            this.api.editarPersona(datos)
                .then((data) => {
                    modal.hide();
                    const table = this.personas.tablaPersonas;
                    const rowsCount = table.rows().count();

                    for (let i = 0; i < rowsCount; i++) {
                        if (table.cell(i, 0).data().toString() === personaId.toString()) {
                            table.row(i).data(datos).draw();
                            break;
                        }
                    }
                })
                .catch(error => {
                    showError(error);
                    console.error(error);
                });
        } else {

            this.api.registrarPersona(datos)
                .then((data) => {
                    modal.hide();
                    this.personas.tablaPersonas.row.add(data).draw();
                }).catch((error) => {
                    showError(error);
                    console.error(error);
                });
        }
    }

}

globalThis.app = new App(config);
