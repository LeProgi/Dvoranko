import React, { useState, useEffect } from "react";
import { GoogleMap, Marker, useLoadScript } from "@react-google-maps/api";

const containerStyle = {
  width: "100%",
  height: "100%"
};


const customMapStyle = [
  { featureType: "poi", stylers: [{ visibility: "off" }] },
  { featureType: "transit", stylers: [{ visibility: "off" }] },
  { featureType: "road", elementType: "labels.icon", stylers: [{ visibility: "off" }] },
  { featureType: "road", elementType: "geometry", stylers: [{ color: "#ffffff" }] },
  { featureType: "water", elementType: "geometry.fill", stylers: [{ color: "#a0c4ff" }] },
  { featureType: "landscape", elementType: "geometry.fill", stylers: [{ color: "#e6ebef" }] },
];

const Map = () => {
  const[locations, setLocations] = useState([]);

  const { isLoaded } = useLoadScript({
    googleMapsApiKey: import.meta.env.VITE_GOOGLE_MAPS_API_KEY
  });

  useEffect(() => {
    // fetch("https://dvoranko.onrender.com/api/public/dvorane") 
    console.log("Fetching locations from backend: " + `${import.meta.env.VITE_BACKEND_URL}/api/public/dvorane`);
    fetch(`${import.meta.env.VITE_BACKEND_URL}/api/public/dvorane`)
      .then((res) => res.json())
      .then((data) => {
        console.log("Fetched locations: ", data);
        const formatted = data.data.map(dvorana => ({
          id: dvorana.idDvorana,
          name: dvorana.nazivDvorana,
          position: { lat: dvorana.adresa.latitude, lng: dvorana.adresa.longitude }
        }));
        setLocations(formatted);
      })
      .catch((err) => console.error("Nešto ne valja kume, nisam uspio dohvatit lokacije ", err));
  }, []);

  if (!isLoaded) return <div>Učitavanje karte...</div>;

  return (
    <GoogleMap
      mapContainerStyle={containerStyle}
      center={{ lat: 45.817, lng: 15.979 }} // centar karte
      zoom={14}
      options={{
        styles: customMapStyle,    
        streetViewControl: false,
      }}
    >
      {locations.map((loc) => (
        <Marker key={loc.id} position={loc.position} title={loc.name} />
      ))}
    </GoogleMap>
  );
};

export default Map;
