import PharmacyClient from '../api/pharmacyClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

class ViewPatient extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addPatientToPage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addPatientToPage);
        this.header = new Header(this.dataStore);
        console.log("viewPatient constructor");
    }

    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const email = urlParams.get('email');
        const patient = await this.client.viewPatient(email);

        this.dataStore.set('patient', patient);
    }

    mount() {
        this.header.addHeaderToPage();

        this.client = new PharmacyClient();
        this.clientLoaded();
    }

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

const main = async () => {
    const viewPatient = new ViewPatient();
    viewPatient.mount();
};

window.addEventListener('DOMContentLoaded', main);
