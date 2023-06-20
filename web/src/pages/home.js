import PharmacyClient from '../api/pharmacyClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

class Home extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount'], this);
        this.header = new Header(this.dataStore);
    }

    /**
     * Add the header to the page and load the PharmacyClient.
     */
    mount() {
        this.header.addHeaderToPage();
        this.client = new PharmacyClient();
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const home = new Home();
    home.mount();
};

window.addEventListener('DOMContentLoaded', main);
