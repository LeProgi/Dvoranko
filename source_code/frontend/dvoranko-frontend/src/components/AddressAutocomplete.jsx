import Autocomplete from "react-google-autocomplete";

function AddressAutocomplete({value, onSelect, onChange}) {

    return (
        <Autocomplete
            apiKey={import.meta.env.VITE_GOOGLE_MAPS_API_KEY}
            options = {{
                types: ['address'],
                componentRestrictions: { country: "hr" },
            }}

            onPlaceSelected={(place) => {
                onSelect(place);
            }}

            value={value}

            onChange={(e) => onChange(e.target.value)}

            placeholder="Unesite adresu..."
            className="w-[90%] h-[40px] p-1.5 rounded-[4px] text-[16px] border-2 border-black bg-white"
        />
    );
}

export default AddressAutocomplete;