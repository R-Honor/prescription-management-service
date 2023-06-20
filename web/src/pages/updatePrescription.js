import PharmacyClient from '../api/pharmacyClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';


const SEARCH_CRITERIA_KEY = 'search-criteria';
const SEARCH_RESULTS_KEY = 'search-results';
const EMPTY_DATASTORE_STATE = {
    [SEARCH_RESULTS_KEY]: [],
};

/**
 * Logic needed for the create playlist page of the website.
 */
class UpdatePrescription extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'viewPrescriptionFirst', 'submit', 'clientLoaded', 'displaySearchResults', 'getHTMLForSearchResults'], this);
        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.dataStore.addChangeListener(this.displaySearchResults);
        this.header = new Header(this.dataStore);
    }

    /**
     * Add the header to the page and load the PharmacyClient
     */
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

    /**
     * Method to run when the create playlist submit button is pressed. Call the PharmacyClient to update the
     * prescription.
     */
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

        this.dataStore.setState({
            [SEARCH_RESULTS_KEY]: prescription,
        });
    }

    /**
     * When the playlist is updated in the datastore, redirect to the view playlist page.
     */
    displaySearchResults() {
        const searchResults = this.dataStore.get(SEARCH_RESULTS_KEY);

        console.log(searchResults);

        const searchResultsContainer = document.getElementById('search-results-container');
        const searchResultsDisplay = document.getElementById('search-results-display');

        searchResultsContainer.classList.remove('hidden');
        searchResultsDisplay.innerHTML = this.getHTMLForSearchResults(searchResults);
    }

    getHTMLForSearchResults(prescription) {
        if (prescription === undefined) {
            return '<h4>No results found</h4>';
        }

        let html = '<table><tr><th>Email</th><th>Drug</th><th>Dose</th><th>Sig Code</th><th>Notes</th><th>Last Filled</th><th>Expiration Date</th><th>Refills</th><th>Status</th></tr>' +
            '<tr><td>'+ prescription.email + '</td>' +
            '<td>'+ prescription.drug + '</td>'  +
            '<td>'+ prescription.dose + '</td>'  +
            '<td>'+ prescription.sigCode + '</td>'  +
            '<td>'+ prescription.notes + '</td>'  +
            '<td>'+ prescription.lastFillDate + '</td>' +
            '<td>'+ prescription.expirationDate + '</td>' +
            '<td>'+ prescription.refills + '</td>' +
            '<td>'+ prescription.status + '</td></tr>'
            '</table>';

        return html;
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const updatePrescription = new UpdatePrescription();
    updatePrescription.mount();
};

window.addEventListener('DOMContentLoaded', main);