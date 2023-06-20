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
class UpdatePatient extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'viewPatientFirst', 'submit', 'clientLoaded', 'displaySearchResults', 'getHTMLForSearchResults'], this);
        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.dataStore.addChangeListener(this.displaySearchResults);
        this.header = new Header(this.dataStore);
    }

    /**
     * Add the header to the page and load the PharmacyClient
     */
    mount() {
//        document.getElementById('view-button').addEventListener('click', this.viewPatientFirst);
        document.getElementById('update-button').addEventListener('click', this.submit);
//        this.header.addHeaderToPage();
        this.client = new PharmacyClient();
        this.clientLoaded();
    }

    async clientLoaded() {
         const urlParams = new URLSearchParams(window.location.search);
         const email = urlParams.get('email');
         if (email) {
//         document.getElementById('patient').classList.add('hidden');
//         const emailDisplayBox = document.createElement("div");
//         const emailDisplay = document.createElement("p");
//         document.getElementById('update-if-linked').innerText = "Update Patient with Email " + email;
//         document.getElementById('patient').appendChild(emailDisplayBox);
//         document.getElementById('patient').appendChild(emailDisplay);

         const patient = await this.client.viewPatient(email, (error) => {
         errorMessageDisplay.innerText = `Error: ${error.message}`;
         errorMessageDisplay.classList.remove('hidden');



         });

         this.dataStore.setState({
             [SEARCH_RESULTS_KEY]: patient,
         });
//         document.getElementById('patient-display-header').innerHTML = "Current Patient";
         }}

    /**
     * Display the patient to update before user selects update information
     */
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

    /**
     * Method to run when the create playlist submit button is pressed. Call the PharmacyClient to update the
     * patient.
     */
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

        this.dataStore.setState({
            [SEARCH_RESULTS_KEY]: patient,
        });
//        document.getElementById('patient-display-header').innerHTML = "Updated Patient";

    }

    /**
     * When the playlist is updated in the datastore, redirect to the view playlist page.
     */
    displaySearchResults() {
        const searchResults = this.dataStore.get(SEARCH_RESULTS_KEY);

        console.log(searchResults);

        const searchResultsContainer = document.getElementById('search-results-container');
        const searchResultsDisplay = document.getElementById('search-results-display');

//        searchResultsContainer.classList.add('hidden');
//        searchResultsContainer.classList.remove('hidden');
//        searchResultsDisplay.innerHTML = this.getHTMLForSearchResults(searchResults);
    }

    getHTMLForSearchResults(patient) {
        if (patient.length === 0) {
            return '<h4>No results found</h4>';
        }

        let html = '<table><tr><th>Make</th><th>Model</th><th>Year</th><th>Class</th><th>Capacity</th><th>Availability</th><th>CostPerDay</th></tr>' +
            '<tr><td>'+ patient.make + '</td>' +
            '<td>'+ patient.model + '</td>'  +
            '<td>'+ patient.year + '</td>'  +
            '<td>'+ patient.classOfVehicle + '</td>'  +
            '<td>'+ patient.capacity + '</td>'  +
            '<td>'+ patient.availability + '</td>' +
            '<td>'+ patient.costPerDay + '</td></tr>'
            '</table>';

        return html;
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const updatePatient = new UpdatePatient();
    updatePatient.mount();
};

window.addEventListener('DOMContentLoaded', main);