import PharmacyClient from '../api/pharmacyClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/**
 * Logic needed for the view patient page of the website.
 */
class ViewPatient extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addPatientToPage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addPatientToPage);
        this.header = new Header(this.dataStore);
        console.log("viewpatient constructor");
    }

    /**
     * Once the client is loaded, get the patient metadata.
     */
    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const email = urlParams.get('email');
//        document.getElementById('patient').innerText = "Loading Patient ...";
        const patient = await this.client.viewPatient(email);
        this.dataStore.set('patient', patient);
    }

    /**
     * Add the header to the page and load the PharmacyClient.
     */
    mount() {
//        document.getElementById('add-song').addEventListener('click', this.addSong);

        this.header.addHeaderToPage();

        this.client = new PharmacyClient();
        this.clientLoaded();
    }

    /**
     * When the patient is updated in the datastore, update the patient metadata on the page.
     */
    addPatientToPage() {
        const patient = this.dataStore.get('patient');
        if (patient == null) {
            return;
        }

        document.getElementById('email').innerText = patient.email;
        document.getElementById('first-name').value = patient.firstName;
        document.getElementById('last-name').value = patient.lastName;
        document.getElementById('insurance').value = patient.insurance;
        document.getElementById('phone').value = patient.phone;
        document.getElementById('address').value = patient.address;
    }

    /**
     * When the songs are updated in the datastore, update the list of songs on the page.
     */
//    addSongsToPage() {
//        const songs = this.dataStore.get('songs')
//
//        if (songs == null) {
//            return;
//        }
//
//        let songHtml = '';
//        let song;
//        for (song of songs) {
//            songHtml += `
//                <li class="song">
//                    <span class="title">${song.title}</span>
//                    <span class="album">${song.album}</span>
//                </li>
//            `;
//        }
//        document.getElementById('songs').innerHTML = songHtml;
//    }

    /**
     * Method to run when the add song patient submit button is pressed. Call the MusicPlaylistService to add a song to the
     * patient.
     */
//    async addSong() {
//
//        const errorMessageDisplay = document.getElementById('error-message');
//        errorMessageDisplay.innerText = ``;
//        errorMessageDisplay.classList.add('hidden');
//
//        const patient = this.dataStore.get('patient');
//        if (patient == null) {
//            return;
//        }
//
//        document.getElementById('add-song').innerText = 'Adding...';
//        const asin = document.getElementById('album-asin').value;
//        const trackNumber = document.getElementById('track-number').value;
//        const email = patient.id;
//
//        const songList = await this.client.addSongToPlaylist(email, asin, trackNumber, (error) => {
//            errorMessageDisplay.innerText = `Error: ${error.message}`;
//            errorMessageDisplay.classList.remove('hidden');
//        });
//
//        this.dataStore.set('songs', songList);
//
//        document.getElementById('add-song').innerText = 'Add Song';
//        document.getElementById("add-song-form").reset();
//    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const viewPatient = new ViewPatient();
    viewPatient.mount();
};

window.addEventListener('DOMContentLoaded', main);
