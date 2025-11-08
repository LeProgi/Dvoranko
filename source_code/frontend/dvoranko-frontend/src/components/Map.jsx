import React from "react";
import { GoogleMap, Marker, useLoadScript } from "@react-google-maps/api";

const containerStyle = {
  width: "100%",
  height: "100%"
};

// Primjer koordinata dvorana
const locations = [
  { id: 1, name: "Dvorana 1", position: { lat: 45.815, lng: 15.978 } },
  { id: 2, name: "Dvorana 2", position: { lat: 45.820, lng: 15.980 } },
];

const customMapStyle = [
  { featureType: "poi", stylers: [{ visibility: "off" }] },
  { featureType: "transit", stylers: [{ visibility: "off" }] },
  { featureType: "road", elementType: "labels.icon", stylers: [{ visibility: "off" }] },
  { featureType: "road", elementType: "geometry", stylers: [{ color: "#ffffff" }] },
  { featureType: "water", elementType: "geometry.fill", stylers: [{ color: "#a0c4ff" }] },
  { featureType: "landscape", elementType: "geometry.fill", stylers: [{ color: "#e6ebef" }] },
];

const Map = () => {
  const { isLoaded } = useLoadScript({
    googleMapsApiKey: import.meta.env.VITE_GOOGLE_MAPS_API_KEY
  });

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
