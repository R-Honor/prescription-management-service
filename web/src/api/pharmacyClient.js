import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

/**
 * Client to call the MusicPlaylistService.
 *
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
  */
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

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     */
    clientLoaded() {
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady(this);
        }
    }

    /**
     * Get the identity of the current user
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The user information for the current user.
     */
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

    /**
     * Gets the patient for the given email.
     * @param id Unique identifier for a patient
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The patient's metadata.
     */
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

    /**
     * Get the songs on a given playlist by the playlist's identifier.
     * @param id Unique identifier for a playlist
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The list of songs on a playlist.
     */
//    async getPlaylistSongs(id, errorCallback) {
//        try {
//            const response = await this.axiosClient.get(`playlists/${id}/songs`);
//            return response.data.songList;
//        } catch (error) {
//            this.handleError(error, errorCallback)
//        }
//    }

    /**
     * Create a new playlist owned by the current user.
     * @param name The name of the playlist to create.
     * @param tags Metadata tags to associate with a playlist.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The playlist that has been created.
     */
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
//                prescriptionId: prescriptionId,
                email: email,
                drug: drug,
                dose: dose,
                sigCode: sigCode,
                notes: notes,
                lastFillDate: lastFillDate,
                expirationDate: expirationDate,
                refills: refills,
//                status: status
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

    /**
     * Add a song to a playlist.
     * @param id The id of the playlist to add a song to.
     * @param asin The asin that uniquely identifies the album.
     * @param trackNumber The track number of the song on the album.
     * @returns The list of songs on a playlist.
     */
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

    /**
     * Search for a soong.
     * @param criteria A string containing search criteria to pass to the API.
     * @returns The playlists that match the search criteria.
     */
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

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
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
