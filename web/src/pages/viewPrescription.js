import PharmacyClient from '../api/pharmacyClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view prescription page of the website.
 */
class ViewPrescription extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addPrescriptionToPage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addPrescriptionToPage);
        this.header = new Header(this.dataStore);
        console.log("viewPrescription constructor");
    }

    /**
     * Once the client is loaded, get the prescription metadata.
     */
    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const prescriptionId = urlParams.get('prescriptionId');
        const prescription = await this.client.viewPrescription(prescriptionId);
        this.dataStore.set('prescription', prescription);
    }

    /**
     * Add the header to the page and load the PharmacyClient.
     */
    mount() {
        this.header.addHeaderToPage();

        this.client = new PharmacyClient();
        this.clientLoaded();
    }

    /**
     * When the prescription is updated in the datastore, update the prescription metadata on the page.
     */
    addPrescriptionToPage() {
        const prescription = this.dataStore.get('prescription');

        if (prescription == null) {
            return;
        }

        document.getElementById('prescriptionId').innerText = prescription.prescriptionId;
        document.getElementById('dose').value = prescription.dose;
        document.getElementById('sigCode').value = prescription.sigCode;
        document.getElementById('notes').value = prescription.notes;
        document.getElementById('lastFillDate').value = prescription.lastFillDate.slice(0, -6);
        document.getElementById('expirationDate').value = prescription.expirationDate.slice(0, -6);
        document.getElementById('refills').value = prescription.refills;
        document.getElementById('status').value = prescription.status;
    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewPrescription = new ViewPrescription();
    viewPrescription.mount();
};

window.addEventListener('DOMContentLoaded', main);
