
import React from "react";
import Map from "../components/Map";
import Button from "../components/Button";
import { Link } from "react-router-dom";
import Footer from "../components/Footer";

const MapPage = () => {
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
