
import React from "react";
import { useEffect, useState } from "react";
import { url } from "../main.jsx";
import Map from "../components/Map";
import Button from "../components/Button";
import { Link } from "react-router-dom";
import Footer from "../components/Footer";

const MapPage = () => {
    const [user, setUser] = useState(null);

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

const handleGoogleLogin = () => {
  window.location.href = `${url}/oauth2/authorization/google`;
};

  return (
    <div className="flex flex-col items-center min-h-screen bg-[#f5f5f5]">
        <div className="bg-[#3B5B80] w-full pb-10 rounded-b-[40%] flex flex-col items-center justify-center text-center mb-10%">
        
            <div className="flex justify-evenly gap-12 mt-8 w-3/4">
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
        <div className="flex-grow w-[90vw] items-center h-[80vh] px-6 py-4 bg-[#8091A6] mt-[2%] mb-[2%] rounded-2xl">
            <div className="w-full h-full rounded-2xl shadow-lg overflow-hidden">
                <Map />
            </div>
        </div>
        <Footer/>
    </div>
  );
};

export default MapPage;
