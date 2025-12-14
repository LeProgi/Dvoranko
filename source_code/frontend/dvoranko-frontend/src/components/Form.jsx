import { useState } from "react";
import axios from "axios";
import AddressAutocomplete from "./AddressComplete.jsx";

function Form() {
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [address, setAddress] = useState(null);
    const [addressError, setAddressError] = useState(false);

    const handleAddressSelect = (place) => {
        const get = (type) =>
            place.address_components?.find((c) => c.types.includes(type))?.long_name || "";

        setAddress({
            street: get("route"),
            streetNumber: get("street_number"),
            city: get("locality"),
            postalCode: get("postal_code"),
            country: get("country"),
            lat: place.geometry.location.lat(),
            lng: place.geometry.location.lng(),
        });

        setAddressError(false); // ukloni error kad se odabere validna adresa
        console.log("SELECTED ADDRESS:", place);
    };

    const postDvorana = async (data) => {
        const url = "/api/moderator/request/requestAdd";
        try {
            const response = await axios.post(url, data);
            console.log("Response from server:", response.data);
        } catch (error) {
            console.error("Error submitting data:", error);
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        // Validacija polja
        if (!name.trim()) {
            alert("Molimo unesite naziv dvorane.");
            return;
        }
        if (!description.trim()) {
            alert("Molimo unesite opis dvorane.");
            return;
        }
        if (!address) {
            setAddressError(true);
            return;
        }

        const payload = { name, description, address };
        console.log("SUBMITTED DATA:", payload);

        // postDvorana(payload);
    };

    return (
        <div style={{ padding: "40px", maxWidth: "600px" }}>
        <h2>Nova lokacija</h2>

        <form
            onSubmit={handleSubmit}
            onKeyDown={(e) => {
                if (e.key === "Enter" && e.target.tagName === "INPUT") e.preventDefault();
            }}
        >
            <div style={{ marginBottom: "16px" }}>
                <label>Naziv dvorane</label>
                <input
                    type="text"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                    placeholder="Naziv dvorane"
                    style={{ width: "100%", height: "40px" }}
                />
            </div>

            <div style={{ marginBottom: "16px" }}>
                <label>Opis</label>
                <textarea
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                    rows={4}
                    required
                    placeholder="Kratki opis dvorane"
                    style={{ width: "100%" }}
                />
            </div>

            <div style={{ marginBottom: "16px" }}>
                <label>Adresa</label>
                <AddressAutocomplete
                    onSelect={handleAddressSelect}
                    onInvalid={() => setAddress(null) & setAddressError(true)}
                />
                {addressError && (
                    <p style={{ color: "red", marginTop: "4px" }}>
                        Molimo odaberite adresu iz Google Places liste i nemijenjajte je ručno.
                    </p>
                )}
            </div>

            <button type="submit" style={{ height: "40px", width: "100%" }}>
                Zatraži zahtjev za lokaciju
            </button>
        </form>
        </div>
    );
}

export default Form;