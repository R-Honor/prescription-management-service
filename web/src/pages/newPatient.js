import PharmacyClient from '../api/pharmacyClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

class NewPatient extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'redirectToViewPatient'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.redirectToViewPatient);
        this.header = new Header(this.dataStore);
    }

    mount() {
        document.getElementById('create').addEventListener('click', this.submit);
        this.header.addHeaderToPage();
        this.client = new PharmacyClient();
    }

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

    redirectToViewPatient() {
        const patient = this.dataStore.get('patient');
        console.log(patient);
        if (patient != null) {
            window.location.href = `/patient.html?email=${patient.email}`;
        }
    }
}

const main = async () => {
    const newPatient = new NewPatient();
    newPatient.mount();
};

window.addEventListener('DOMContentLoaded', main);
