import BaseApi from "../commons/BaseApi";

export default class Api extends BaseApi {
    constructor(config) {
        super(config);
        this.baseEndpoint = `${config.context}/rest/personas`;
    }

    #getBaseEndPoint() {
        return this.baseEndpoint;
    }

    listarPersonas() {
        const url = this.#getBaseEndPoint();
        return Api.get(url);
    }

    registrarPersona(data) {
        const url = this.#getBaseEndPoint();
        return Api.post({
            url: url,
            data: data
        });
    }

    eliminarPersona(id) {
        const url = this.#getBaseEndPoint() + "/" + id;
        return Api.delete(url);
    }

    editarPersona(data) {
        const url = this.#getBaseEndPoint() + "/" + data.id;
        return Api.put({
            url: url,
            data: data
        });
    }
}