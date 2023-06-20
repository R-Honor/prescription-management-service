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

    /**
     * Add the header to the page and load the RentalCarServiceClient
     */
    mount() {
        document.getElementById("delete-button").addEventListener('click',this.deletePrescription);
        this.client = new PharmacyClient();
    }

    async deletePrescription (){

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = '';
        errorMessageDisplay.classList.add('hidden');

//        const deleteResultsContainer = document.getElementById('remove-car-container');
//        const deleteCriteriaDisplay = document.getElementById('delete-criteria');
//        const deleteResultsDisplay = document.getElementById('remove-car-display');

        const prescriptionId = document.getElementById('prescriptionId').innerText;

        console.log(prescriptionId);
        const prescriptionResult = await this.client.deletePrescription(prescriptionId, (error) => {
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');

//            deleteResultsContainer.classList.add('hidden');
//            deleteCriteriaDisplay.innerHTML = '';
//            deleteResultsDisplay.innerHTML = '';
            });
            window.location.reload();
//            let input = document.getElementById('update-prescription-form');
//            input.reset();
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