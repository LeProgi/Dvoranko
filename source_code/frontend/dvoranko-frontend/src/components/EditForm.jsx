import { useState, useEffect, useRef } from "react";
import { Link, useNavigate } from "react-router-dom";
import AddressAutocomplete from "../components/AddressAutocomplete.jsx";
import CategorySelector from "./CategorySelector.jsx";
import TimeDropdown from "./TimeDropdown.jsx";
import { useParams } from "react-router-dom";
import { url } from "../main.jsx";

function Form() {
    const navigate = useNavigate();
    // const categories = ["Sportska", "Koncertna", "Kazališna", "Društvena", "Drugo(u opisu)"];
    const [categories, setCategories] = useState([]);
    const [selectedCategories, setselectedCategories] = useState([]);
    const timesOd = ["00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"];
    const timesDo = ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"];
    const [naziv, setName] = useState("");
    const [kapacitet, setCapacity] = useState("");
    const [opis, setDescription] = useState("");
    const [address, setAddress] = useState(null);
    const [addressId, setAddressId] = useState(null)
    const [addressError, setAddressError] = useState(false);
    const [addressText, setAddressText] = useState("");
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
    const [image, setImage] = useState(null);
    const [imagePreview, setImagePreview] = useState(null);
    const fileInputRef = useRef(null);
    const [formError, setFormError] = useState("");

    const {id} = useParams();
    const idNumber= +id;


    useEffect(() => {
        const fetchCategories = async () => {
            try {
                const res = await fetch(`${url}/api/public/kategorije`);
                if (!res.ok) throw new Error("Greška pri dohvaćanju kategorija");
                const data = await res.json();
                // console.log("Fetched categories:", data);
                setCategories(data.data);
            } catch (err) {
                console.error(err);
                setCategoriesError("Ne mogu dohvatiti kategorije.");
            }
        };

        const fetchDvorana = async () => {
            try {
                const res = await fetch(`${url}/api/public/dvorane/${id}`) //treba update taj api poziv da vraca i kategorije
                .then(res => res.json())
                .then(data => {
                    const dvorana = data.data;
                    //setselectedCategories(dvorana.categories) pogledaj komentar 4 linije iznad
                    setName(dvorana.nazivDvorana)
                    setCapacity((dvorana.kapacitet).toString())
                    setDescription(dvorana.opis)
                    setAddress(dvorana.adresa ? `${dvorana.adresa.ulica} ${dvorana.adresa.kucniBroj}, ${dvorana.adresa.mjesto?.nazivMjesto}`: ''); //a valjda nemre dvorana promjenit adresu 
                    setAddressId(dvorana.adresa.idAdresa) 
                    console.log("uspjesno dohvacanje dvorane kumeeee laooo")
                })
            } catch (err){
                console.error("error tijekom dohvaćanja dvorana kume", err)
            }
        }

        fetchCategories();
        fetchDvorana();
    }, []);


    useEffect (() => {
        if (addressText.trim() === "") {
            setAddressError(false);
            setFormError("");
        }

        if (addressError) {
            setFormError("Molimo odaberite adresu iz Google Places liste i ne mijenjajte je ručno.");
        } else {
            setFormError("");
        }
    }, [addressError, addressText]);

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
        e.stopPropagation();
        setImage(null);
        if (fileInputRef.current) fileInputRef.current.value = "";
    };

    const updateDvorana = async (data) => {
        try {
            const response = await fetch(`${url}/api/moderator/dvorana/${id}`, { //promjenit api poziv?
                method: "PUT",
                credentials: "include",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data)
            });

            const text = await response.text();
            let respData;
            try {
                respData = text ? JSON.parse(text) : null;
            } catch {
                respData = text;
            }

            if (!response.ok) {
                console.error("Server returned error:", response.status, respData);
                throw new Error(respData?.message || `Server error ${response.status}`);
            }

            console.log("Response from server:", respData);
            return respData;
        } catch (error) {
            console.error("Error submitting data:", error);
            throw error;
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!naziv.trim()) {
            setFormError("Molimo unesite naziv dvorane.");
            return;
        }

        if (!kapacitet.trim()) {
            setFormError("Molimo unesite kapacitet dvorane.");
            return;
        } else if (!/^([1-9]\d*)$/.test(kapacitet.trim())) {
            setFormError("Molimo da kapacitet bude pozitivan cijeli broj.");
            return;
        }

        if (selectedCategories.length === 0) {
            setFormError("Molimo odaberite barem jednu kategoriju.");
            return;
        }

        if (!opis.trim()) {
            setFormError("Molimo unesite opis dvorane.");
            return;
        }

        if (!days.pon.enabled && !days.uto.enabled && !days.sri.enabled && !days.cet.enabled && !days.pet.enabled && !days.sub.enabled && !days.ned.enabled) {
            setFormError("Molimo odaberite barem jedan radni dan.");
            return;
        } else {
            const invalidDays = Object.entries(days)
                .filter(([key, day]) => day.enabled && (!day.start || !day.end))
                .map(([key]) => key);

            if (invalidDays.length > 0) {
                const dayNames = invalidDays.map(d => {
                    switch(d) {
                        case "pon": return "ponedjeljak";
                        case "uto": return "utorak";
                        case "sri": return "srijedu";
                        case "cet": return "četvrtak";
                        case "pet": return "petak";
                        case "sub": return "subotu";
                        case "ned": return "nedjelju";
                    }
                }).join(", ");

                setFormError(`Molimo unesite vremena "Od" i "Do" za ${dayNames}.`);
                return;
            }
        }

        if (!image) {
            setFormError("Molimo dodajte sliku dvorane.");
            return;
        }

        setFormError("");

        let daysOpen = "";
        Object.entries(days).forEach(([key, day]) => {
            if (day.enabled) {
                daysOpen += `${key}:${day.start}-${day.end};`;
            }
        });

        try {
            const userRes = await fetch(`${url}/api/auth/user`, { credentials: "include" });
            if (userRes.status !== 200) throw new Error("Nije ulogiran");
            const userData = await userRes.json();
            const ownerIdLocal = userData.id;
            console.log(naziv)
            console.log(kapacitet)
            console.log(opis)
            console.log(addressId)
            console.log(selectedCategories)
            console.log(userData.id)

        const payload = { nazivDvorana: naziv, kapacitet, opis, idAdresa: addressId, idKategorije: selectedCategories, idVlasnik: userData.id};
            console.log("Payload to be sent:", payload);
            await updateDvorana(payload);
            setFormError("Zahtjev uspješno poslan! Preusmjeravanje na profil...");
            navigate("/my-profile");
            // setTimeout(() => {
            // }, 0);

        } catch (err) {
            console.error(err);
            setFormError("Greška pri slanju zahtjeva. Pokušajte ponovno.");
        }
    };

    return (
        <div className="bg-[#5B7692] min-h-screen flex justify-center items-center relative">
            <form
                onSubmit={handleSubmit}
                noValidate
                onKeyDown={(e) => {
                    if (e.key === "Enter" && e.target.tagName === "INPUT") e.preventDefault();
                }}
            >
                <div className="flex flex-row items-center gap-[8vw] flex-wrap">
                    <div className="w-[45vw] bg-[#F5F5F5] rounded-[20px] flex flex-col gap-[20px] items-center relative">
                        <div className="bg-[#3B5B80] rounded-tl-[19px] rounded-tr-[19px] p-4 text-white text-center text-[24px] font-bold w-full">
                            <label>Uređivanje podataka o dvorani</label>
                        </div>

                        <div className="flex flex-row w-full">
                            <div className="flex flex-col gap-[5px] border-r border-black w-[50%]">
                                <div className="mb-[5px]">
                                    <label>Naziv dvorane</label>
                                    <input
                                        type="text"
                                        value={naziv}
                                        onChange={(e) => setName(e.target.value)}
                                        required
                                        placeholder="Naziv dvorane"
                                        className="w-[90%] h-[40px] border-2 border-black rounded-[4px] bg-white"
                                    />
                                </div>

                                <div className="mb-[5px]">
                                    <label>Kapacitet dvorane</label>
                                    <input
                                        type="text"
                                        value={kapacitet}
                                        onChange={(e) => setCapacity(e.target.value)}
                                        required
                                        placeholder="Kapacitet dvorane"
                                        className="w-[90%] h-[40px] border-2 border-black rounded-[4px] bg-white"
                                    />
                                </div>

                                <div className="mb-[5px] flex flex-col items-center">
                                    <label>Kategorija dvorane</label>
                                    <CategorySelector
                                        categories={categories}
                                        selectedCategories={selectedCategories}
                                        setSelectedCategories={setselectedCategories}
                                    />
                                </div>

                                <div className="mb-[5px] flex flex-col items-center">
                                    <label>Opis</label>
                                    <textarea
                                        value={opis}
                                        onChange={(e) => setDescription(e.target.value)}
                                        rows={4}
                                        required
                                        placeholder="Kratki opis dvorane"
                                        className="w-[90%] border-2 border-black rounded-[4px] bg-white"
                                    />
                                </div>
                            </div>

                            <div className="flex flex-col gap-[5px] w-[50%] border-l border-black">
                                <div className="mb-[5px]">
                                    <label>Adresa</label>
                                    <p className="text-x1 font-bold">{address}</p>
                                </div>

                                <div className="flex flex-col">
                                    <label>Radni dani</label>

                                    {DAY_LABELS.map(({ key, label }) => {
                                        const day = days[key];

                                        return (
                                        <div key={key} className="flex items-center">
                                            <div className="flex justify-center items-center flex-row w-[40%] gap-[5px]">
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

                                            <div className="flex justify-center items-center flex-row w-[60%]">
                                                <label className="mr-[5px]">Od:</label>

                                                <TimeDropdown
                                                    value={day.start}
                                                    disabled={!day.enabled}
                                                    onChange={(val) =>
                                                        setDays({ ...days, [key]: { ...day, start: val } })
                                                    }
                                                    options={timesOd.filter(t => !day.end || t < day.end)}
                                                />

                                                <label className="m-[5px]">Do:</label>

                                                <TimeDropdown
                                                    value={day.end}
                                                    disabled={!day.enabled}
                                                    onChange={(val) =>
                                                        setDays({ ...days, [key]: { ...day, end: val } })
                                                    }
                                                    options={timesDo.filter(t => t > day.start)}
                                                />
                                            </div>
                                        </div>
                                        );
                                    })}
                                </div>
                            </div>
                        </div>
                        
                        <button type="submit" className="h-[40px] w-[45%] text-white font-bold border-none rounded-[10px] cursor-pointer mb-[10px] bg-[#3B5B80] hover:bg-[#2F4B6A] transition-colors">
                            Promjeni podatke o dvorani
                        </button>

                        <div className="absolute bottom-[10px] right-[10px]">
                            <Link to="/my-profile">
                                <button className="w-fit h-[40px] font-bold border-none rounded-[10px] text-white cursor-pointer px-[10px] bg-[#3B5B80] hover:bg-[#2F4B6A] transition-colors">
                                    Odustani
                                </button>
                            </Link>
                        </div>
                    </div>

                    <div className="p-[10px] bg-[#F5F5F5] rounded-[20px] flex flex-col gap-[10px] w-[30vw] h-fit min-h-[20vh]">
                        <label>Slika dvorane</label>

                        <input
                            type="file"
                            id="photo"
                            accept="image/*"
                            className="hidden"
                            ref={fileInputRef}
                            required
                            onChange={(e) => setImage(e.target.files[0])}
                        />

                        <label
                            htmlFor="photo"
                            className="w-full h-full min-h-[20vh] border-2 border-dashed border-[#3B5B80] rounded-[12px] cursor-pointer bg-white flex items-center justify-center overflow-hidden relative"
                            >
                            {imagePreview ? (
                                <>
                                    <img
                                        src={imagePreview}
                                        alt="Preview"
                                        className="w-full h-full object-cover"
                                    />

                                    <button
                                        type="button"
                                        onClick={handleRemove}
                                        className="absolute top-[5px] right-[5px] bg-[rgba(0,0,0,0.5)] text-white border-none rounded-full w-[25px] h-[25px] cursor-pointer"
                                    >
                                        X
                                    </button>
                                </>
                            ) : (
                                <div className="text-center text-[#3B5B80]">
                                    <strong>Klikni za dodati sliku</strong>
                                </div>
                            )}
                        </label>
                    </div>
                </div>
            </form>

            {formError && (
                <div className="text-white bg-[#b91c1c] p-[10px_10px] rounded-[40px] w-fit text-center font-medium absolute top-[20px] right-[20px]">
                    {formError}
                </div>
            )}
        </div>
    );
}

export default Form;