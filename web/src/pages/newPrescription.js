import PharmacyClient from '../api/pharmacyClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

class NewPrescription extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'redirectToViewPrescription'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.redirectToViewPrescription);
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
        const drug = document.getElementById('drug').value;
        const dose = document.getElementById('dose').value;
        const sigCode = document.getElementById('sigCode').value;
        const notes = document.getElementById('notes').value;
        const lastFillDate = document.getElementById('lastFillDate').value + ':00.000Z';
        const expirationDate = document.getElementById('expirationDate').value + ':00.000Z';
        const refills = document.getElementById('refills').value;

        const prescription = await this.client.newPrescription(email, drug, dose, sigCode, notes, lastFillDate, expirationDate, refills, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('prescription', prescription);
    }

    redirectToViewPrescription() {
        const prescription = this.dataStore.get('prescription');
        console.log(prescription);
        if (prescription != null) {
            window.location.href = `/prescription.html?prescriptionId=${prescription.prescriptionId}`;
        }
    }
}

const main = async () => {
    const newPrescription = new NewPrescription();
    newPrescription.mount();
};

window.addEventListener('DOMContentLoaded', main);
