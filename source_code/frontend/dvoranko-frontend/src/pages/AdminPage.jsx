import React, {useState, useEffect} from "react";
import Button from "../components/Button";
import { Link } from "react-router-dom";
import Footer from "../components/Footer";
import { url } from "../main";

const AdminPage = () => {

  const[zahtjevIznajmljivac, setZahtjevIznajmljivac] = useState([]);
  const[zahtjevDvorana, setZahtjevDvorana] = useState([]);

  useEffect(() => {
    const fetchRequests = async () => {
      try {
        //dvorane
        const resDvorana = await fetch(`${url}/api/public/admin/getall/zahtjevidvorana`, {
          method: "GET",
          credentials: "include",
        });

        if (!resDvorana.ok) throw new Error("Ne smijes biti tu kume, nisi admin");
        const dataDvorana = await resDvorana.json();
        setZahtjevDvorana(dataDvorana.data); 
        console.log(dataDvorana);

        //iznajmljivaci
        const resIznajmljivac = await fetch(`${url}/api/public/admin/getall/zahtjeviznajmljivac`, {
          method: "GET",
          credentials: "include", 
        });

        if (!resIznajmljivac.ok) throw new Error("Ne smijes biti tu kume, nisi admin");
        const dataIznajmljivac = await resIznajmljivac.json();
        setZahtjevIznajmljivac(dataIznajmljivac.data); 
      } catch (err) {
        console.error("Kume error tijekom hvacanja zahtjeva za iznajmljivaca:", err);
      }
    };

    fetchRequests();
  }, []);



  const handleAcceptDvorana = async (id) => {
    try {
      const res = await fetch(`${url}/api/public/admin/requests/${id}/approve`, {
        method: "POST",
        credentials: "include",
      });

      // if (!res.ok) throw new Error("Ne smijes biti tu kume, nisi admin");

      // Ukloni prihvaćeni zahtjev iz stanja
      setZahtjevDvorana((prevRequests) =>
        prevRequests.filter((request) => request.id !== id)
      );
    } catch (err) {
      console.error("Kume error tijekom prihvaćanja zahtjeva za dvoranu:", err);
    }
  };


  const handleRejectDvorana = async (id) => {
    try {
      const res = await fetch(`${url}/api/public/admin/requests/${id}/reject`, {
        method: "POST",
        credentials: "include",
      });

      // if (!res.ok) throw new Error("Ne smijes biti tu kume, nisi admin");

      // Ukloni odbijeni zahtjev iz stanja
      setZahtjevDvorana((prevRequests) =>
        prevRequests.filter((request) => request.id !== id)
      );
    } catch (err) {
      console.error("Kume error tijekom odbijanja zahtjeva za dvoranu:", err);
    }
  };


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

            {zahtjevDvorana.length === 0 && (
              <p className="text-center text-gray-500">Nema novih zahtjeva</p>
            )}

            {zahtjevDvorana.map((request) => (
              <div key={request.id} className="bg-white rounded-xl p-4 shadow-md mb-4">
                <div className="grid grid-cols-[140px_1fr] gap-y-2">
                  <span className="font-semibold">Ime dvorane:</span>
                  <span>{request.naziv}</span>

                  <span className="font-semibold">Vlasnik:</span>
                  <span>{request.owner?.name}</span>
                  
                  <span className="font-semibold">Email:</span>
                  <span>{request.owner?.email}</span>

                  <span className="font-semibold">Kapacitet:</span>
                  <span>{request.kapacitet}</span>

                  <span className="font-semibold">Adresa:</span>
                  <span>{request.street} {request.streetNumber}, {request.postalCode}, {request.city}</span>

                  <span className="font-semibold">Opis:</span>
                  <span>{request.opis}</span>
                  

                  <span className="font-semibold">Slike:</span>
                  <span>{request.images ? request.images.join(", ") : "Nema slika"}</span>
                </div>

                <div className="flex justify-between mt-4">
                  <button className="px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700" onClick={() => handleAcceptDvorana(request.id)}>
                    PRIHVATI
                  </button>
                  <button className="px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700" onClick={() => handleRejectDvorana(request.id)}>
                    ODBIJ
                  </button>
                </div>
              </div>
            ))}
          </div>

          <div className="bg-[#f5f5f5] rounded-2xl p-6 overflow-y-auto">
            <h3 className="text-2xl font-semibold mb-4 text-center">
              Zahtjevi za nove iznajmljivače
            </h3>

            {zahtjevIznajmljivac.length === 0 && (
              <p className="text-center text-gray-500">Nema novih zahtjeva</p>
            )}

            {zahtjevIznajmljivac.map((request) => (
              <div key={request.id} className="bg-white rounded-xl p-4 shadow-md mb-4">
                <div className="grid grid-cols-[140px_1fr] gap-y-2">
                  <span className="font-semibold">ID:</span>
                  <span>{request.id}</span>

                  <span className="font-semibold">Ime i prezime:</span>
                  <span>{request.user?.name}</span>


                  <span className="font-semibold">Email:</span>
                  <span>{request.user?.email}</span>

                  <span className="font-semibold">Slika:</span>
                  <span>{request.image ? request.image : "Nema slike"}</span>
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
            ))}
          </div>

        </div>
      </div>

      <Footer />
    </div>
  );
};

export default AdminPage;
