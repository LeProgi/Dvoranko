
import React from "react";
import Button from "../components/Button";
import { Link } from "react-router-dom";
import Footer from "../components/Footer";

const AdminPage = () => {
  return (
    <div className="flex flex-col items-center min-h-screen bg-[#f5f5f5]">
        <div className="bg-[#3B5B80] w-full pb-10 rounded-b-[40%] flex flex-col items-center justify-center text-center mb-10%">
        
            <div className="flex justify-evenly gap-12 mt-8 w-3/4">
                <Link to="/" className="w-[50vw] block">
                 <Button variant="default" title="Početna stranica" />
                </Link>

                
            </div>
            <h2 className="text-4xl text-white mt-10 mb-10 font-semibold tracking-wide">Administrator</h2>
        </div>
        <div className="flex-grow w-[90vw] px-6 py-6 bg-[#8091A6] mt-[2%] mb-[2%] rounded-2xl">
  <div className="grid grid-cols-1 md:grid-cols-2 gap-6 h-full">

    <div className="bg-[#f5f5f5] rounded-2xl p-6 overflow-y-auto">
      <h3 className="text-2xl font-semibold mb-4 text-center">
        Zahtjevi za nove dvorane
      </h3>

      <div className="bg-white rounded-xl p-4 shadow-md mb-4">
  <div className="grid grid-cols-[140px_1fr] gap-y-2">
    <span className="font-semibold">Ime dvorane:</span>
    <span>OŠ Michaela Jordana</span>

    <span className="font-semibold">Vlasnik:</span>
    <span>Nikola Jokić</span>

    <span className="font-semibold">Kapacitet:</span>
    <span>67</span>

    <span className="font-semibold">Adresa:</span>
    <span>Ulica Lebrona Jamesa 23</span>

    <span className="font-semibold">Slike:</span>
    <span>neki link pa da pop upuju slike ili kako?</span>
  </div>

  <div className="flex justify-between mt-4">
    <button className="px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700">
      PRIHVATI
    </button>
    <button className="px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700">
      ODBIJ
    </button>
  </div>
</div>
<div className="bg-white rounded-xl p-4 shadow-md mb-4">
  <div className="grid grid-cols-[140px_1fr] gap-y-2">
    <span className="font-semibold">Ime Dvorane:</span>
    <span>Martinovka</span>

    <span className="font-semibold">Vlasnik:</span>
    <span>Kruno Simon</span>

    <span className="font-semibold">Kapacitet:</span>
    <span>9999</span>

    <span className="font-semibold">Adresa:</span>
    <span>Unska 3</span>
  </div>

  <div className="flex justify-between mt-4">
    <button className="px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700">
      PRIHVATI
    </button>
    <button className="px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700">
      ODBIJ
    </button>
  </div>
</div>


    </div>

    <div className="bg-[#f5f5f5] rounded-2xl p-6 overflow-y-auto">
      <h3 className="text-2xl font-semibold mb-4 text-center">
        Zahtjevi za nove iznajmljivačače
      </h3>

      <div className="bg-white rounded-xl p-4 shadow-md mb-4">
  <div className="grid grid-cols-[140px_1fr] gap-y-2">
    <span className="font-semibold">Ime i prezime:</span>
    <span>Michael Jordan</span>

    <span className="font-semibold">ID:</span>
    <span>23</span>

    <span className="font-semibold">Email:</span>
    <span>michael.jordan@fer.hr</span>

    <span className="font-semibold">slika:</span>
    <span> neka slika? </span>
  </div>

  <div className="flex justify-between mt-4">
    <button className="px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700">
      PRIHVATI
    </button>
    <button className="px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700">
      ODBIJ
    </button>
  </div>
  
</div>


    </div>

  </div>
</div>

        <Footer/>
    </div>
  );
};

export default AdminPage;
