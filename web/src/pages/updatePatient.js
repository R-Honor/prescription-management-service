import PharmacyClient from '../api/pharmacyClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';


const SEARCH_CRITERIA_KEY = 'search-criteria';
const SEARCH_RESULTS_KEY = 'search-results';
const EMPTY_DATASTORE_STATE = {
    [SEARCH_RESULTS_KEY]: [],
};

class UpdatePatient extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'viewPatientFirst', 'submit', 'clientLoaded'], this);
        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.header = new Header(this.dataStore);
    }

    mount() {
        document.getElementById('update-button').addEventListener('click', this.submit);
        this.client = new PharmacyClient();
        this.clientLoaded();
    }

    async clientLoaded() {
         const urlParams = new URLSearchParams(window.location.search);
         const email = urlParams.get('email');

         if (email) {
         const patient = await this.client.viewPatient(email, (error) => {
         errorMessageDisplay.innerText = `Error: ${error.message}`;
         errorMessageDisplay.classList.remove('hidden');
         });

         this.dataStore.setState({
             [SEARCH_RESULTS_KEY]: patient,
         });
         }}

    async viewPatientFirst() {

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const email = document.getElementById('email').value;

        const patient = await this.client.getPatient(email, (error) => {

        errorMessageDisplay.innerText = `Error: ${error.message}`;
        errorMessageDisplay.classList.remove('hidden');
        });

        this.dataStore.setState({
            [SEARCH_RESULTS_KEY]: patient,
        });
        document.getElementById('patient-display-header').innerHTML = "Current Patient";

    }

    async submit(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const urlParams = new URLSearchParams(window.location.search);
        let email = urlParams.get('email');
        if (!email) {
            email = document.getElementById('email').value;
        }

        const firstName = document.getElementById('firstName').value;
        const lastName = document.getElementById('lastName').value;
        const insurance = document.getElementById('insurance').value;
        const phone = document.getElementById('phone').value;
        const address = document.getElementById('address').value;

        const patient = await this.client.updatePatient(email, firstName, lastName, insurance, phone, address, (error) => {

        errorMessageDisplay.innerText = `Error: ${error.message}`;
        errorMessageDisplay.classList.remove('hidden');
        });

        alert("Patient Update Successful");

        this.dataStore.setState({
            [SEARCH_RESULTS_KEY]: patient,
        });
    }
}

const main = async () => {
    const updatePatient = new UpdatePatient();
    updatePatient.mount();
};

window.addEventListener('DOMContentLoaded', main);