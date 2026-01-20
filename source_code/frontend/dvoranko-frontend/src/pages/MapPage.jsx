
import React from "react";
import { useEffect, useState } from "react";
import { url } from "../main.jsx";
import Map from "../components/Map";
import Button from "../components/Button";
import { Link } from "react-router-dom";
import Footer from "../components/Footer";

const MapPage = () => {
    const [user, setUser] = useState(null);
    const [venues, setVenues] = useState([]);
    const [hoveredVenue, setHoveredVenue] = useState(null);

useEffect(() => {
  fetch(`${url}/api/auth/user`, {
    credentials: "include",
  })
    .then((res) => {
      if (res.status === 200) return res.json();
      throw new Error("Not logged in");
    })
    .then((data) => setUser(data))
    .catch(() => setUser(null));
}, []);

useEffect(() => {
  fetch(`${url}/api/public/dvorane`, {
    credentials: "include",
  })
    .then((res) => res.json())
    .then((data) => {
      if (data?.data) {
        const formatted = data.data.map((dvorana) => ({
          id: dvorana.idDvorana,
          name: dvorana.nazivDvorana,
          adresa: dvorana.adresa
            ? `${dvorana.adresa.ulica} ${dvorana.adresa.kucniBroj}, ${dvorana.adresa.mjesto?.nazivMjesto}`
            : "nemrem nac adresu kume",
          kapacitet: dvorana.kapacitet,
          cijenaPoSatu: dvorana.cijenaPoSatu,
          latitude: dvorana.adresa?.latitude,
          longitude: dvorana.adresa?.longitude,
        }));
        setVenues(formatted);
      }
    })
    .catch((err) => console.error("Error fetching venues:", err));
}, []);

const handleGoogleLogin = () => {
  window.location.href = `${url}/oauth2/authorization/google`;
};

  return (
    <div className="flex flex-col items-center min-h-screen bg-[#f5f5f5]">
        <div className="bg-[#3B5B80] w-full pb-10 rounded-b-[40%] flex flex-col items-center justify-center text-center mb-10%">
        
            <div className="flex justify-evenly md:gap-12 gap-6 mt-8 w-3/4">
                <Link to="/" className="w-[50vw] block">
                 <Button variant="default" title="Početna stranica" />
                </Link>
                <Link to="/event-board" className="w-[50vw] block">
                    <Button variant="default" title="Oglasna ploča" />
                </Link>
                {user ? (
                <Link to="/my-profile" state={{ user }}>
                    <div className="w-12 h-12 rounded-full border-2 border-white overflow-hidden shadow-md hover:scale-105 transition-transform duration-200">
                    <img
                        src={user.pictureUrl}
                        alt={user.name}
                        title={user.name}
                        className="w-full h-full object-cover"
                    />
                    </div>
                </Link>
                ) : (
                <Button
                    variant="default"
                    title="Prijavi se"
                    onClick={handleGoogleLogin}
                />
                )}

                
            </div>
            <h2 className="text-4xl text-white mt-10 mb-10 font-semibold tracking-wide">Karta</h2>
        </div>
        <div className="md:flex-grow md:flex-row flex-col  w-[90vw] h-[80vh] px-6 py-4 bg-[#8091A6] mt-[2%] mb-[2%] rounded-2xl flex gap-4">
            <div className="md:flex-1 md:h-full h-[1000px] rounded-2xl shadow-lg overflow-hidden">
                <Map hoveredVenue={hoveredVenue} />
            </div>
            <div className="md:w-1/5 w-full min-w-[200px] h-full bg-white rounded-2xl p-3 overflow-y-auto">
                <h3 className="text-gray-700 font-semibold text-lg mb-3">Dvorane</h3>
                <div className="space-y-0">
                    {venues.map((venue, index) => (
                        <Link 
                            key={venue.id} 
                            to={`/venue/${venue.id}`}
                            state={{ from: '/maps' }}
                            onMouseEnter={() => setHoveredVenue(venue)}
                            onMouseLeave={() => setHoveredVenue(null)}
                        >
                            <div className={`p-3 hover:bg-gray-50 transition cursor-pointer ${index !== venues.length - 1 ? 'border-b border-gray-200' : ''}`}>
                                <h4 className="font-semibold text-sm text-gray-800">{venue.name}</h4>
                                <p className="text-xs text-gray-600 mt-1">{venue.adresa}</p>
                                <div className="flex md:justify-between justify-center mt-2">
                                    <span className="text-xs text-gray-700">Kapacitet: {venue.kapacitet}</span>
                                    <span className="text-xs font-semibold text-[#3B5B80]">{venue.cijenaPoSatu} €/h</span>
                                </div>
                            </div>
                        </Link>
                    ))}
                </div>
            </div>
        </div>
        <Footer/>
    </div>
  );
};

export default MapPage;
