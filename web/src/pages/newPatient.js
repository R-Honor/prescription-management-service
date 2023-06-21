import PharmacyClient from '../api/pharmacyClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the create patient page of the website.
 */
class NewPatient extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'redirectToViewPatient'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.redirectToViewPatient);
        this.header = new Header(this.dataStore);
    }

    /**
     * Add the header to the page and load the PharmacyClient.
     */
    mount() {
        document.getElementById('create').addEventListener('click', this.submit);
        this.header.addHeaderToPage();
        this.client = new PharmacyClient();
    }

    /**
     * Method to run when the create patient submit button is pressed. Call the PharmacyClient to create the
     * patient.
     */
    async submit(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const createButton = document.getElementById('create');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Loading...';

        const email = document.getElementById('email').value;
        const firstName = document.getElementById('first-name').value;
        const lastName = document.getElementById('last-name').value;
        const insurance = document.getElementById('insurance').value;
        const phone = document.getElementById('phone').value;
        const address = document.getElementById('address').value;

        const patient = await this.client.newPatient(email, firstName, lastName, insurance, phone, address, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('patient', patient);
    }

    /**
     * When the patient is updated in the datastore, redirect to the view patient page.
     */
    redirectToViewPatient() {
        const patient = this.dataStore.get('patient');
        console.log(patient);
        if (patient != null) {
            window.location.href = `/patient.html?email=${patient.email}`;
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const newPatient = new NewPatient();
    newPatient.mount();
};

window.addEventListener('DOMContentLoaded', main);
