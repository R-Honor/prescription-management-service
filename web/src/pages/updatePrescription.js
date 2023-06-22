import PharmacyClient from '../api/pharmacyClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';


const SEARCH_CRITERIA_KEY = 'search-criteria';
const SEARCH_RESULTS_KEY = 'search-results';
const EMPTY_DATASTORE_STATE = {
    [SEARCH_RESULTS_KEY]: [],
};

class UpdatePrescription extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'clientLoaded'], this);
        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.header = new Header(this.dataStore);
    }

    mount() {
        document.getElementById('update-button').addEventListener('click', this.submit);
        document.getElementById('update-button').addEventListener('click', this.viewPrescriptionFirst);

        this.client = new PharmacyClient();
        this.clientLoaded();
    }

    async clientLoaded() {
         const urlParams = new URLSearchParams(window.location.search);
         const prescriptionId = urlParams.get('prescriptionId');

         if (prescriptionId) {
         const prescription = await this.client.viewPrescription(prescriptionId, (error) => {
         errorMessageDisplay.innerText = `Error: ${error.message}`;
         errorMessageDisplay.classList.remove('hidden');
         });

         this.dataStore.setState({
             [SEARCH_RESULTS_KEY]: prescription,
         });
         }}

    async submit(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const urlParams = new URLSearchParams(window.location.search);
        let prescriptionId = urlParams.get('prescriptionId');

        if (!prescriptionId) {
            prescriptionId = document.getElementById('prescriptionId').value;
        }

        console.log(prescriptionId);

        const dose = document.getElementById('dose').value;
        const sigCode = document.getElementById('sigCode').value;
        const notes = document.getElementById('notes').value;
        const lastFillDate = document.getElementById('lastFillDate').value + ':00.000Z';
        const expirationDate = document.getElementById('expirationDate').value + ':00.000Z';
        const refills = document.getElementById('refills').value;
        const status = document.getElementById('status').value;

        const prescription = await this.client.updatePrescription(prescriptionId, dose, sigCode, notes, lastFillDate, expirationDate, refills, status, (error) => {
        errorMessageDisplay.innerText = `Error: ${error.message}`;
        errorMessageDisplay.classList.remove('hidden');
        });

        alert("Prescription Update Successful");

        this.dataStore.setState({
            [SEARCH_RESULTS_KEY]: prescription,
        });
    }
}

const main = async () => {
    const updatePrescription = new UpdatePrescription();
    updatePrescription.mount();
};

window.addEventListener('DOMContentLoaded', main);