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

    mount() {
        this.header.addHeaderToPage();
        this.client = new PharmacyClient();
    }
}

const main = async () => {
    const home = new Home();
    home.mount();
};

window.addEventListener('DOMContentLoaded', main);
