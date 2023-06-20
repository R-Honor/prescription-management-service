import PharmacyClient from '../api/pharmacyClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view patient page of the website.
 */
class ViewPatient extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addPatientToPage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addPatientToPage);
        this.header = new Header(this.dataStore);
        console.log("viewPatient constructor");
    }

    /**
     * Once the client is loaded, get the patient metadata.
     */
    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const email = urlParams.get('email');
        const patient = await this.client.viewPatient(email);

        this.dataStore.set('patient', patient);
    }

    /**
     * Add the header to the page and load the PharmacyClient.
     */
    mount() {
        this.header.addHeaderToPage();

        this.client = new PharmacyClient();
        this.clientLoaded();
    }

    /**
     * When the patient is updated in the datastore, update the patient metadata on the page.
     */
    addPatientToPage() {
        const patient = this.dataStore.get('patient');

        if (patient == null) {
            return;
        }

        document.getElementById('email').innerText = patient.email;
        document.getElementById('firstName').value = patient.firstName;
        document.getElementById('lastName').value = patient.lastName;
        document.getElementById('insurance').value = patient.insurance;
        document.getElementById('phone').value = patient.phone;
        document.getElementById('address').value = patient.address;
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewPatient = new ViewPatient();
    viewPatient.mount();
};

window.addEventListener('DOMContentLoaded', main);
