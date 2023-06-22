import PharmacyClient from '../api/pharmacyClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

const SEARCH_CRITERIA_KEY = 'search-criteria';
const SEARCH_RESULTS_KEY = 'search-results';
const EMPTY_DATASTORE_STATE = {
    [SEARCH_CRITERIA_KEY]: '',
    [SEARCH_RESULTS_KEY]: [],
};

class SearchPatients extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'search', 'displaySearchResults', 'getHTMLForSearchResults', 'redirectToViewPatient'], this);

        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.header = new Header(this.dataStore);
        this.dataStore.addChangeListener(this.displaySearchResults);

        console.log("searchPatients constructor");
    }

    mount() {
        document.getElementById('search-patients-form').addEventListener('submit', this.search);
        document.getElementById('search-btn').addEventListener('click', this.search);
        document.getElementById('search-single-btn').addEventListener('click', this.redirectToViewPatient);

        this.header.addHeaderToPage();
        this.client = new PharmacyClient();
    }

    async search(evt) {
        evt.preventDefault();

        console.log('in search');

        let lastName = document.getElementById('lastName').value;
        let firstName = document.getElementById('firstName').value;
        console.log(lastName);
        console.log(firstName);

        if (lastName === '') {
            lastName = 'none';
        }
        if (firstName === '') {
            firstName = 'none';
        }

        const searchCriteria = lastName + " " + firstName
        const previousSearchCriteria = this.dataStore.get(SEARCH_CRITERIA_KEY);

        if (previousSearchCriteria === searchCriteria) {
            return;
        }

        if (searchCriteria) {
            const results = await this.client.searchPatients(searchCriteria);

            this.dataStore.setState({
                [SEARCH_CRITERIA_KEY]: searchCriteria,
                [SEARCH_RESULTS_KEY]: results,
            });
        }
        else {
            this.dataStore.setState(EMPTY_DATASTORE_STATE);
        }
    }

    displaySearchResults() {
        const searchCriteria = this.dataStore.get(SEARCH_CRITERIA_KEY);
        const searchResults = this.dataStore.get(SEARCH_RESULTS_KEY);

        const searchResultsContainer = document.getElementById('search-results-container');
        const searchCriteriaDisplay = document.getElementById('search-criteria-display');
        const searchResultsDisplay = document.getElementById('search-results-display');

        if (searchCriteria === '') {
            searchResultsContainer.classList.add('hidden');
            searchCriteriaDisplay.innerHTML = '';
            searchResultsDisplay.innerHTML = '';
        } else {
            searchResultsContainer.classList.remove('hidden');
            searchCriteriaDisplay.innerHTML = `"${searchCriteria}"`;
            searchResultsDisplay.innerHTML = this.getHTMLForSearchResults(searchResults);
        }
    }

    getHTMLForSearchResults(searchResults) {
        if (searchResults.length === 0) {
            return '<h4>No results found</h4>';
        }

        let html = '<table><tr><th>Email</th><th>First</th><th>Last</th></tr>';
        for (const res of searchResults) {
            html += `
            <tr>
                <td>
                    <a href="patient.html?email=${res.email}">${res.email}</a>
                </td>
                <td>${res.firstName}</td>
                <td>${res.lastName}</td>
            </tr>`;
        }
        html += '</table>';

        return html;
    }

    redirectToViewPatient() {
        const email = document.getElementById('email').value;
        console.log(email);
        if (email != null) {
            window.location.href = `/patient.html?email=${email}`;
        }
    }

}

const main = async () => {
    const searchPatients = new SearchPatients();
    searchPatients.mount();
};

window.addEventListener('DOMContentLoaded', main);
