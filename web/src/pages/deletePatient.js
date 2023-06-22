import PharmacyClient from '../api/pharmacyClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';


class DeletePatient extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'deletePatient'], this);
        this.dataStore = new DataStore();
        console.log("DeletePatient constructor");
    }

    mount() {
        document.getElementById("delete-button").addEventListener('click',this.deletePatient);
        this.client = new PharmacyClient();
    }

    async deletePatient (){

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = '';
        errorMessageDisplay.classList.add('hidden');

        const email = document.getElementById('email').innerText;

        console.log(email);
        const patientResult = await this.client.deletePatient(email, (error) => {
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
            });
            alert("Patient Delete Successful");
            window.location.href = `/searchPatient.html`;
      }
    }

const main = async () => {
    const deletePatient = new DeletePatient();
    deletePatient.mount();
};

window.addEventListener('DOMContentLoaded', main);