import { useState } from "react";
import axios from "axios";
import AddressAutocomplete from "../components/AddressAutocomplete.jsx";

function Form() {
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [address, setAddress] = useState(null);
    const [addressError, setAddressError] = useState(false);
    const [image, setImage] = useState(null);

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

        //dodat slanje slike

        const payload = { name, description, address };
        console.log("SUBMITTED DATA:", payload);

        // postDvorana(payload);
    };

    return (
        <div style={{ backgroundColor: "#5B7692", minHeight: "100vh", display: "flex", justifyContent: "center", alignItems: "center", gap: "10vw" }}>
            <div style={{ width: "40vw", backgroundColor: "#F5F5F5", borderRadius: "20px", display: "flex", flexDirection: "column", gap: "20px" }}>
                <div style={{backgroundColor: "#3B5B80", borderTopLeftRadius: "20px", borderTopRightRadius: "20px", padding: "16px", color: "white", textAlign: "center", fontSize: "24px", fontWeight: "bold" }}>
                    <h2>Nova lokacija</h2>
                </div>

                <form
                    onSubmit={handleSubmit}
                    onKeyDown={(e) => {
                        if (e.key === "Enter" && e.target.tagName === "INPUT") e.preventDefault();
                    }}
                >
                    <div style={{ display: "flex", flexDirection: "row", gap: "16px", borderLeft: "" }}>
                        <div style={{ display: "flex", flexDirection: "column", gap: "16px" }}>
                            <div style={{ marginBottom: "16px" }}>
                                <label>Naziv dvorane</label>
                                <input
                                    type="text"
                                    value={name}
                                    onChange={(e) => setName(e.target.value)}
                                    required
                                    placeholder="Naziv dvorane"
                                    style={{ width: "90%", height: "40px", border: "2px solid black", borderRadius: "4px", backgroundColor: "white" }}
                                />
                            </div>

                            <div style={{ marginBottom: "16px" }}>
                                <label>Kapacitet dvorane</label>
                                <input
                                    type="text"
                                    value={name}
                                    onChange={(e) => setName(e.target.value)}
                                    required
                                    placeholder="Kapacitet dvorane"
                                    style={{ width: "90%", height: "40px", border: "2px solid black", borderRadius: "4px", backgroundColor: "white" }}
                                />
                            </div>

                            <div style={{ marginBottom: "16px" }}>
                                <label>Kategorija dvorane</label>
                                <input
                                    type="text"
                                    value={name}
                                    onChange={(e) => setName(e.target.value)}
                                    required
                                    placeholder="Kategorija dvorane"
                                    style={{ width: "90%", height: "40px", border: "2px solid black", borderRadius: "4px", backgroundColor: "white" }}
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
                                    style={{ width: "90%", border: "2px solid black", borderRadius: "4px", backgroundColor: "white" }}
                                />
                            </div>
                        </div>

                        <div>
                            <div style={{ marginBottom: "16px" }}>
                                <label>Adresa</label>
                                <AddressAutocomplete
                                    onSelect={handleAddressSelect}
                                    onInvalid={() => setAddress(null) & setAddressError(true)}
                                />
                                {addressError && (
                                    <p style={{ color: "red", marginTop: "4px", width: "250px" }}>
                                        Molimo odaberite adresu iz Google Places liste i ne mijenjajte je ručno.
                                    </p>
                                )}
                            </div>

                            <div style={{ marginBottom: "16px", display: "flex", flexDirection: "row", alignItems: "center" }}>
                                <label>Ponedjeljak</label>
                                <input
                                    type="checkbox"
                                    value={name}
                                    onChange={(e) => setName(e.target.value)}
                                    required
                                    placeholder="Naziv dvorane"
                                    style={{ width: "10%", height: "12px" }}
                                />
                            </div>

                            <button type="submit" style={{ height: "40px", width: "100%" }}>
                                Zatraži zahtjev za lokaciju
                            </button>
                        </div>
                    </div>
                </form>
            </div>

            <div style={{ padding: "10px", backgroundColor: "#F5F5F5", borderRadius: "20px", display: "flex", flexDirection: "column", gap: "10px", width: "30vw", height: "fit-content", minHeight: "20vh" }}>
                <label>Slika dvorane</label>
                <input
                    type="file"
                    id="photo"
                    accept="image/*"
                    className="hidden"
                    onChange={(e) => setImage(e.target.files[0])}
                />

                <label
                    htmlFor="photo"
                    style={{
                        width: "100%",
                        height: "100%",
                        minHeight: "20vh",
                        border: "2px dashed #3B5B80",
                        borderRadius: "12px",
                        cursor: "pointer",
                        backgroundColor: "white",
                        display: "flex",
                        alignItems: "center",
                        justifyContent: "center",
                        overflow: "hidden",
                    }}
                    >
                    {image ? (
                        <img
                        src={URL.createObjectURL(image)}
                        alt="Preview"
                        style={{ width: "100%", height: "100%", objectFit: "cover" }}
                        />
                    ) : (
                        <div style={{ textAlign: "center", color: "#3B5B80" }}>
                        <strong>Klikni za dodati sliku</strong>
                        <div style={{ fontSize: "12px", color: "#777" }}>
                            JPG ili PNG
                        </div>
                        </div>
                    )}
                </label>

            </div>
        </div>
    );
}

export default Form;