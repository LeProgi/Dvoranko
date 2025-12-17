import { useState, useEffect, useRef } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import AddressAutocomplete from "../components/AddressAutocomplete.jsx";

function Form() {
    const [name, setName] = useState("");
    const [capacity, setCapacity] = useState("");
    const [category, setCategory] = useState("");
    const [description, setDescription] = useState("");
    const [address, setAddress] = useState(null);
    const [addressError, setAddressError] = useState(false);
    const [days, setDays] = useState({
        pon: { enabled: false, start: "", end: "" },
        uto: { enabled: false, start: "", end: "" },
        sri: { enabled: false, start: "", end: "" },
        cet: { enabled: false, start: "", end: "" },
        pet: { enabled: false, start: "", end: "" },
        sub: { enabled: false, start: "", end: "" },
        ned: { enabled: false, start: "", end: "" },
    });
    const DAY_LABELS = [
        { key: "pon", label: "Ponedjeljak" },
        { key: "uto", label: "Utorak" },
        { key: "sri", label: "Srijeda" },
        { key: "cet", label: "Četvrtak" },
        { key: "pet", label: "Petak" },
        { key: "sub", label: "Subota" },
        { key: "ned", label: "Nedjelja" },
    ];
    const [user, setUser] = useState(null);
    const [image, setImage] = useState(null);
    const [imagePreview, setImagePreview] = useState(null);
    const fileInputRef = useRef(null);

    useEffect(() => {
        if (!image) {
            setImagePreview(null);
            return;
        }

        const objectUrl = URL.createObjectURL(image);
        setImagePreview(objectUrl);

        return () =>  URL.revokeObjectURL(objectUrl);
    }, [image]);

    const handleRemove = (e) => {
        e.stopPropagation(); // prevent label click
        setImage(null);
        if (fileInputRef.current) fileInputRef.current.value = ""; // 2. reset input
    };

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
        <div style={{ backgroundColor: "#5B7692", minHeight: "100vh", display: "flex", justifyContent: "center", alignItems: "center" }}>
            <form
                onSubmit={handleSubmit}
                onKeyDown={(e) => {
                    if (e.key === "Enter" && e.target.tagName === "INPUT") e.preventDefault();
                }}
            >
                <div style={{ display: "flex", flexDirection: "row", alignItems: "center", gap: "8vw", flexWrap: "wrap" }}>
                    <div style={{ width: "45vw", backgroundColor: "#F5F5F5", borderRadius: "20px", display: "flex", flexDirection: "column", gap: "20px", alignItems: "center", position: "relative" }}>
                        <div style={{backgroundColor: "#3B5B80", borderTopLeftRadius: "19px", borderTopRightRadius: "19px", padding: "16px", color: "white", textAlign: "center", fontSize: "24px", fontWeight: "bold", width: "100%"}}>
                            <label>Nova lokacija</label>
                        </div>

                        <div style={{ display: "flex", flexDirection: "row", width: "100%" }}>
                            <div style={{ display: "flex", flexDirection: "column", gap: "16px", borderRight: "1px solid black", width:"50%" }}>
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
                                        value={capacity}
                                        onChange={(e) => setCapacity(e.target.value)}
                                        required
                                        placeholder="Kapacitet dvorane"
                                        style={{ width: "90%", height: "40px", border: "2px solid black", borderRadius: "4px", backgroundColor: "white" }}
                                    />
                                </div>

                                <div style={{ marginBottom: "16px" }}>
                                    <label>Kategorija dvorane</label>
                                    <input
                                        type="text"
                                        value={category}
                                        onChange={(e) => setCategory(e.target.value)}
                                        required
                                        placeholder="Kategorija dvorane"
                                        style={{ width: "90%", height: "40px", border: "2px solid black", borderRadius: "4px", backgroundColor: "white" }}
                                    />
                                </div>

                                <div style={{ marginBottom: "16px", display: "flex", flexDirection: "column", alignItems: "center" }}>
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

                            <div style={{ display: "flex", flexDirection: "column", gap: "16px", width:"50%", borderLeft: "1px solid black" }}>
                                <div style={{ marginBottom: "16px" }}>
                                    <label>Adresa</label>
                                    <AddressAutocomplete
                                        onSelect={handleAddressSelect}
                                        onInvalid={() => {
                                            setAddress(null);
                                            setAddressError(true);}}
                                    />
                                    {addressError && (
                                        <p style={{ color: "red", marginTop: "4px", width: "100%" }}>
                                            Molimo odaberite adresu iz Google Places liste i ne mijenjajte je ručno.
                                        </p>
                                    )}
                                </div>

                                <div style={{ display: "flex", flexDirection: "column" }}>
                                    <label>Radni dani</label>

                                    {DAY_LABELS.map(({ key, label }) => {
                                        const day = days[key];

                                        return (
                                        <div key={key} style={{ display: "flex", alignItems: "center"}}>
                                            <div style={{ display: "flex", justifyContent: "center", alignItems: "center", flexDirection: "row", width: "50%", gap: "5px" }}>
                                                <label>{label}</label>

                                                <input
                                                    type="checkbox"
                                                    checked={day.enabled}
                                                    className="hover:cursor-pointer"
                                                    onChange={(e) =>
                                                        setDays({
                                                        ...days,
                                                        [key]: {
                                                            ...day,
                                                            enabled: e.target.checked,
                                                            start: e.target.checked ? day.start : "",
                                                            end: e.target.checked ? day.end : "",
                                                        },
                                                        })
                                                    }
                                                />
                                            </div>

                                            <div style={{ display: "flex", justifyContent: "center", alignItems: "center", flexDirection: "row", width: "50%" }}>
                                                <label style={{ marginRight: "5px" }}>Od:</label>

                                                <input
                                                    type="text"
                                                    placeholder="0"
                                                    value={day.start}
                                                    disabled={!day.enabled}
                                                    onChange={(e) =>
                                                        setDays({
                                                        ...days,
                                                        [key]: { ...day, start: e.target.value },
                                                        })
                                                    }
                                                    style={{ width: "30px", height: "30px", border: "2px solid black", borderRadius: "4px", backgroundColor: day.enabled ? "white" : "#ccc" }}
                                                />

                                                <label style={{ margin: "5px" }}>Do:</label>

                                                <input
                                                    type="text"
                                                    placeholder="0"
                                                    value={day.end}
                                                    disabled={!day.enabled}
                                                    onChange={(e) =>
                                                        setDays({
                                                        ...days,
                                                        [key]: { ...day, end: e.target.value },
                                                        })
                                                    }
                                                    style={{ width: "30px", height: "30px", border: "2px solid black", borderRadius: "4px", backgroundColor: day.enabled ? "white" : "#ccc" }}
                                                />
                                            </div>
                                        </div>
                                        );
                                    })}
                                </div>
                            </div>
                        </div>
                        
                        <button type="submit" className="bg-[#3B5B80] hover:bg-[#2F4B6A] transition-colors" style={{ height: "40px", width: "45%", color: "white", border: "none", borderRadius: "10px", cursor: "pointer", marginBottom: "10px" }}>
                            Zatraži zahtjev za lokaciju
                        </button>

                        <div style={{ position: "absolute", bottom: "10px", right: "10px" }}>
                            <Link to="/my-profile">
                                <button className="bg-[#3B5B80] hover:bg-[#2F4B6A] transition-colors" style={{ width: "fit-content", height: "40px", border: "none", borderRadius: "10px", color: "white", cursor: "pointer", padding: "0 10px" }}>
                                    Odustani
                                </button>
                            </Link>
                        </div>
                    </div>

                    <div style={{ padding: "10px", backgroundColor: "#F5F5F5", borderRadius: "20px", display: "flex", flexDirection: "column", gap: "10px", width: "30vw", height: "fit-content", minHeight: "20vh" }}>
                        <label>Slika dvorane</label>

                        <input
                            type="file"
                            id="photo"
                            accept="image/*"
                            className="hidden"
                            ref={fileInputRef}
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
                                position: "relative",
                            }}
                            >
                            {imagePreview ? (
                                <>
                                    <img
                                        src={imagePreview}
                                        alt="Preview"
                                        style={{ width: "100%", height: "100%", objectFit: "cover" }}
                                    />

                                    <button
                                        type="button"
                                        onClick={handleRemove}
                                        style={{
                                            position: "absolute",
                                            top: "5px",
                                            right: "5px",
                                            backgroundColor: "rgba(0,0,0,0.5)",
                                            color: "white",
                                            border: "none",
                                            borderRadius: "50%",
                                            width: "25px",
                                            height: "25px",
                                            cursor: "pointer",
                                        }}
                                    >
                                        X
                                    </button>
                                </>
                            ) : (
                                <div style={{ textAlign: "center", color: "#3B5B80" }}>
                                    <strong>Klikni za dodati sliku</strong>
                                </div>
                            )}
                        </label>
                    </div>
                </div>
            </form>
        </div>
    );
}

export default Form;