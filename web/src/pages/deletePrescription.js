import PharmacyClient from '../api/pharmacyClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';


class DeletePrescription extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'deletePrescription'], this);

        this.dataStore = new DataStore();
        console.log("DeletePrescription constructor");
    }

    mount() {
        document.getElementById("delete-button").addEventListener('click',this.deletePrescription);
        this.client = new PharmacyClient();
    }

    async deletePrescription (){

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = '';
        errorMessageDisplay.classList.add('hidden');

        const prescriptionId = document.getElementById('prescriptionId').innerText;

        console.log(prescriptionId);
        const prescriptionResult = await this.client.deletePrescription(prescriptionId, (error) => {
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
            });

            alert("Prescription Delete Successful");

            window.location.reload();
      }
    }

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const deletePrescription = new DeletePrescription();
    deletePrescription.mount();
};

window.addEventListener('DOMContentLoaded', main);