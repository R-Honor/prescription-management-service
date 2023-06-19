import PharmacyClient from '../api/pharmacyClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/*
The code below this comment is equivalent to...
const EMPTY_DATASTORE_STATE = {
    'search-criteria': '',
    'search-results': [],
};

...but uses the "KEY" constants instead of "magic strings".
The "KEY" constants will be reused a few times below.
*/

const SEARCH_CRITERIA_KEY = 'search-criteria';
const SEARCH_RESULTS_KEY = 'search-results';
const EMPTY_DATASTORE_STATE = {
    [SEARCH_CRITERIA_KEY]: '',
    [SEARCH_RESULTS_KEY]: [],
};


/**
 * Logic needed for the view playlist page of the website.
 */
class SearchPrescriptions extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'search', 'displaySearchResults', 'getHTMLForSearchResults', 'redirectToViewPrescription'], this);

        // Create a enw datastore with an initial "empty" state.
        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.header = new Header(this.dataStore);
        this.dataStore.addChangeListener(this.displaySearchResults);
//        this.dataStore.addChangeListener(this.redirectToViewPrescription);

        console.log("searchPrescriptions constructor");
    }

    /**
     * Add the header to the page and load the PharmacyClient.
     */
    mount() {
        // Wire up the form's 'submit' event and the button's 'click' event to the search method.
        document.getElementById('search-prescriptions-form').addEventListener('submit', this.search);
        document.getElementById('search-btn').addEventListener('click', this.search);
//        document.getElementById('search-prescriptions-form').addEventListener('submit', this.search);
        document.getElementById('search-single-btn').addEventListener('click', this.redirectToViewPrescription);

        this.header.addHeaderToPage();

        this.client = new PharmacyClient();
    }

    /**
     * Uses the client to perform the search,
     * then updates the datastore with the criteria and results.
     * @param evt The "event" object representing the user-initiated event that triggered this method.
     */
    async search(evt) {
        // Prevent submitting the from from reloading the page.
        evt.preventDefault();
        console.log('in search');
        let email = document.getElementById('email').value;
        let status = document.getElementById('status').value;
        console.log(email);
        console.log(status);


        if (email === '') {
            email = 'none';
        }
        if (status === '') {
            status = 'none';
        }

        const searchCriteria = email + " " + status
        const previousSearchCriteria = this.dataStore.get(SEARCH_CRITERIA_KEY);

//         If the user didn't change the search criteria, do nothing
        if (previousSearchCriteria === searchCriteria) {
            return;
        }

        if (searchCriteria) {
            const results = await this.client.searchPrescriptions(searchCriteria);

            this.dataStore.setState({
                [SEARCH_CRITERIA_KEY]: searchCriteria,
                [SEARCH_RESULTS_KEY]: results,
            });
        } else {
            this.dataStore.setState(EMPTY_DATASTORE_STATE);
        }
    }

    /**
     * Pulls search results from the datastore and displays them on the html page.
     */
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

    /**
     * Create appropriate HTML for displaying searchResults on the page.
     * @param searchResults An array of prescriptions objects to be displayed on the page.
     * @returns A string of HTML suitable for being dropped on the page.
     */
    getHTMLForSearchResults(searchResults) {
        if (searchResults.length === 0) {
            return '<h4>No results found</h4>';
        }

        let html = '<table><tr><th>Prescription Id</th><th>Status</th><th>Email</th></tr>';
        for (const res of searchResults) {
            html += `
            <tr>
                <td>
                    <a href="prescription.html?prescriptionId=${res.prescriptionId}">${res.prescriptionId}</a>
                </td>
                <td>${res.status}</td>
                <td>${res.email}</td>
            </tr>`;
        }
        html += '</table>';

        return html;
    }

    redirectToViewPrescription() {
        const prescriptionId = document.getElementById('prescriptionId').value;
        console.log(prescriptionId);
        if (prescriptionId != null) {
            window.location.href = `/prescription.html?prescriptionId=${prescriptionId}`;
        }
    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const searchPrescriptions = new SearchPrescriptions();
    searchPrescriptions.mount();
};

window.addEventListener('DOMContentLoaded', main);
