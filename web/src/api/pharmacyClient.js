import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

export default class PharmacyClient extends BindingClass {

    constructor(props = {}) {
        super();

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'newPatient', 'newPrescription', 'viewPatient', 'viewPrescription', 'searchPatients', 'searchPrescriptions', 'updatePatient'];
        this.bindClassMethods(methodsToBind, this);

        this.authenticator = new Authenticator();;
        this.props = props;

        axios.defaults.baseURL = process.env.API_BASE_URL;
        this.axiosClient = axios;
        this.clientLoaded();
    }

    clientLoaded() {
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady(this);
        }
    }

    async getIdentity(errorCallback) {
        try {
            const isLoggedIn = await this.authenticator.isUserLoggedIn();

            if (!isLoggedIn) {
                return undefined;
            }

            return await this.authenticator.getCurrentUserInfo();
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async login() {
        this.authenticator.login();
    }

    async logout() {
        this.authenticator.logout();
    }

    async getTokenOrThrow(unauthenticatedErrorMessage) {
        const isLoggedIn = await this.authenticator.isUserLoggedIn();
        if (!isLoggedIn) {
            throw new Error(unauthenticatedErrorMessage);
        }

        return await this.authenticator.getUserToken();
    }

    async viewPatient(email, errorCallback) {
        try {
            const response = await this.axiosClient.get(`patient/${email}`);
            return response.data.patient;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async viewPrescription(prescriptionId, errorCallback) {
        try {
            const response = await this.axiosClient.get(`prescription/${prescriptionId}`);
            return response.data.prescription;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async newPatient(email, firstName, lastName, insurance, phone, address, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create patients.");
            const response = await this.axiosClient.post(`patient`, {
                email: email,
                firstName: firstName,
                lastName: lastName,
                insurance: insurance,
                phone: phone,
                address: address,
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            console.log(response.data);
            return response.data.patient;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async newPrescription(email, drug, dose, sigCode, notes, lastFillDate, expirationDate, refills, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create prescriptions.");
            const response = await this.axiosClient.post(`prescription`, {
                email: email,
                drug: drug,
                dose: dose,
                sigCode: sigCode,
                notes: notes,
                lastFillDate: lastFillDate,
                expirationDate: expirationDate,
                refills: refills,
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            console.log(response.data);
            return response.data.prescription;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async updatePatient(email, firstName, lastName, insurance, phone, address, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can update patients.");
            const response = await this.axiosClient.put(`patient/${email}`, {
                email: email,
                firstName: firstName,
                lastName: lastName,
                insurance: insurance,
                phone: phone,
                address: address
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.patient;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async updatePrescription(prescriptionId, dose, sigCode, notes, lastFillDate, expirationDate, refills, status, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can update prescriptions.");
            const response = await this.axiosClient.put(`prescription/${prescriptionId}`, {
                prescriptionId: prescriptionId,
                dose: dose,
                sigCode: sigCode,
                notes: notes,
                lastFillDate: lastFillDate,
                expirationDate: expirationDate,
                refills: refills,
                status: status
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.prescription;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async searchPatients(criteria, errorCallback) {
        try {
            const queryParams = new URLSearchParams({ q: criteria })
            const queryString = queryParams.toString();

            console.log(queryString);
            const response = await this.axiosClient.get(`patient/?${queryString}`);
            console.log('out of response');

            return response.data.patients;
        } catch (error) {
            this.handleError(error, errorCallback)
        }

    }

    async searchPrescriptions(criteria, errorCallback) {
        try {
            const queryParams = new URLSearchParams({ q: criteria })
            const queryString = queryParams.toString();

            console.log(queryString);
            const response = await this.axiosClient.get(`prescription/?${queryString}`);
            console.log('out of response');

            return response.data.prescriptions;
        } catch (error) {
            this.handleError(error, errorCallback)
        }

    }

    async deletePrescription(prescriptionId, errorCallback) {
        try {

            const token = await this.getTokenOrThrow("Only authenticated users can delete a prescription.");

            const response = await this.axiosClient.delete(`prescription/${prescriptionId}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.prescription;
        }
        catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async deletePatient(email, errorCallback) {
        try {

            const token = await this.getTokenOrThrow("Only authenticated users can delete a patient.");

            const response = await this.axiosClient.delete(`patient/${email}`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.patient;
        }
        catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    handleError(error, errorCallback) {
        console.error(error);

        const errorFromApi = error?.response?.data?.error_message;
        if (errorFromApi) {
            console.error(errorFromApi)
            error.message = errorFromApi;
        }

        if (errorCallback) {
            errorCallback(error);
        }
    }
}
