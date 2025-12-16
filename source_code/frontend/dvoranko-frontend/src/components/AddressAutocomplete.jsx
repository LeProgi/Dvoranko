import { useState } from "react"; 
import Autocomplete from "react-google-autocomplete";

function AddressAutocomplete({onSelect, onInvalid}) {
    const [selectedAddress, setSelectedAddress] = useState("");

    return (
        <Autocomplete
            apiKey={import.meta.env.VITE_GOOGLE_MAPS_API_KEY}
            options = {{
                types: ['address'],
                componentRestrictions: { country: "hr" },
            }}

            onPlaceSelected={(place) => {
                setSelectedAddress(place.formatted_address);
                onSelect(place);
            }}

            onChange={(e) => {
                const value = e.target.value;

                if (value != selectedAddress) {
                    onInvalid();
                }
            }}

            placeholder="Unesite adresu..."
            style = {{
                width: '90%',
                height: '40px',
                padding: '6px',
                borderRadius: '4px',
                fontSize: '16px',
                border: '2px solid black',
                backgroundColor: 'white'
            }}
        />
    );
}

export default AddressAutocomplete;