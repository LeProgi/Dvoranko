import React, { useState, useEffect, useRef } from "react";
import { GoogleMap, Marker, useLoadScript } from "@react-google-maps/api";
import { url } from "../main.jsx";
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

const Map = ({ hoveredVenue }) => {
  const[locations, setLocations] = useState([]);
  const [mapCenter, setMapCenter] = useState({ lat: 45.817, lng: 15.979 });
  const mapRef = useRef(null);

  const { isLoaded } = useLoadScript({
    googleMapsApiKey: import.meta.env.VITE_GOOGLE_MAPS_API_KEY
  });

  useEffect(() => {

    fetch(`${url}/api/public/dvorane`)
      .then((res) => res.json())
      .then((data) => {
        const formatted = data.data.map(dvorana => ({
          id: dvorana.idDvorana,
          name: dvorana.nazivDvorana,
          position: { lat: dvorana.adresa.latitude, lng: dvorana.adresa.longitude }
        }));
        setLocations(formatted);
      })
      .catch((err) => console.error("Nešto ne valja kume, nisam uspio dohvatit lokacije ", err));
  }, []);

  useEffect(() => {
    if (mapRef.current) {
      if (hoveredVenue?.latitude && hoveredVenue?.longitude) {
        mapRef.current.panTo({ lat: hoveredVenue.latitude, lng: hoveredVenue.longitude });
      } else {
        mapRef.current.panTo({ lat: 45.817, lng: 15.979 });
      }
    }
  }, [hoveredVenue]);

  const onMapLoad = (map) => {
    mapRef.current = map;
  };

  if (!isLoaded) return <div>Učitavanje karte...</div>;

  return (
    <GoogleMap
      mapContainerStyle={containerStyle}
      center={mapCenter}
      zoom={14}
      onLoad={onMapLoad}
      options={{
        styles: customMapStyle,    
        streetViewControl: false,
      }}
    >
      {locations.map((loc) => {
        const isHovered = hoveredVenue?.id === loc.id;
        return (
          <Marker 
            key={loc.id} 
            position={loc.position} 
            title={loc.name}
            icon={{
              url: "http://maps.google.com/mapfiles/ms/icons/red-dot.png",
              scaledSize: new window.google.maps.Size(isHovered ? 55 : 33, isHovered ? 50 : 37)
            }}
          />
        );
      })}
    </GoogleMap>
  );
};

export default Map;
