import React, {useState, useEffect} from "react";
import Button from "../components/Button";
import { data, Link } from "react-router-dom";
import Footer from "../components/Footer";
import { url } from "../main";
import { all } from "axios";

const AdminPage = () => {

  const[showRequests, setShowRequests] = useState(true); //true == pokazuje zahtjeve, false == pokazuje postojece korisnike i dvorane
  const[zahtjevIznajmljivac, setZahtjevIznajmljivac] = useState([]);
  const[zahtjevDvorana, setZahtjevDvorana] = useState([]);
  const [allUsers, setAllUsers] = useState([]);
  const [allDvorane, setAllDvorane] = useState([])

  useEffect(() => {
    const fetchRequests = async () => {
      try {
        //zahtjevi za dvorane
        const resDvorana = await fetch(`${url}/api/public/admin/getall/zahtjevidvorana`, {
          method: "GET",
          credentials: "include",
        });
        console.log(resDvorana);
        if (!resDvorana.ok) throw new Error("Ne smijes biti tu kume, nisi admin1");
        const dataDvorana = await resDvorana.json();
        setZahtjevDvorana(dataDvorana.data); 
        console.log(dataDvorana);

        //iznajmljivaci
        const resIznajmljivac = await fetch(`${url}/api/public/admin/getall/zahtjeviznajmljivac`, {
          method: "GET",
          credentials: "include", 
        });
        console.log(resIznajmljivac);

        if (!resIznajmljivac.ok) throw new Error("Ne smijes biti tu kume, nisi admin2");
        const dataIznajmljivac = await resIznajmljivac.json();
        setZahtjevIznajmljivac(dataIznajmljivac.data); 

      } catch (err) {
        console.error("Kume error tijekom hvacanja zahtjeva za iznajmljivaca:", err);
      }
      
      //korisnici
      try{
          const resUsers = await fetch(`${url}/api/public/admin/getall/users`, {
            method: "GET",
            credentials: "include",
          });
          if (resUsers.ok) {
            const dataUsers = await resUsers.json();
            setAllUsers(dataUsers.data);
          }
        }
        catch(err) {
          console.error("Kume error tijekom hvaćanja svih korisnika")
        }

        //sve postojece dvorane
        const resAllDvorane = await fetch(`${url}/api/public/admin/getall/dvorane`, {
          method: "GET",
          credentials: "include",
        });
        if (resAllDvorane.ok) {
          const dataAllDvorane = await resAllDvorane.json();
          setAllDvorane(dataAllDvorane.data);
          console.log("Sve dvorane:", dataAllDvorane);
        }
    };

    fetchRequests();
  }, []);

  const handleAcceptIznajmljivac = (id) => { 
    try {
      const res = fetch(`${url}/api/public/admin/request/moderator/${id}/accept`, {
        method: "POST",
        credentials: "include",
      });
      // if (!res.ok) throw new Error("Ne smijes biti tu kume, nisi admin");

      // Ukloni prihvaćeni zahtjev iz stanja  
      setZahtjevIznajmljivac((prevRequests) =>
        prevRequests.filter((request) => request.id !== id)
      );
    } catch (err) {
      console.error("Kume error tijekom prihvaćanja zahtjeva za iznajmljivaca:", err);
    }
  };

  const handleRejectIznajmljivac = async (id) => {
    try {
      const res = await fetch(`${url}/api/public/admin/request/moderator/${id}/reject`, {
        method: "POST",
        credentials: "include",
      });
      // if (!res.ok) throw new Error("Ne smijes biti tu kume, nisi admin");

      // Ukloni odbijeni zahtjev iz stanja
      setZahtjevIznajmljivac((prevRequests) =>
        prevRequests.filter((request) => request.id !== id)
      );
    } catch (err) {
      console.error("Kume error tijekom odbijanja zahtjeva za iznajmljivaca:", err);
    }
  };



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


  const handleDeleteDvorana = async(id) => {
    try {
      const res = await fetch(`${url}/api/public/admin/delete/dvorana/${id}`, {
        method: "DELETE", 
        credentials: "include",
      })
      setAllDvorane((prevDvorane) =>
        prevDvorane.filter((d) => d.idDvorana !== id)
      );
    }
    catch(err){
      console.error("Kume error tijekom brisanja dvorane")
    }
  }

  const handleDeleteUser = async(id) => {
    try {
      const res = await fetch(`${url}/api/public/admin/delete/user/${id}`, {
        method: "DELETE",
        credentials: "include",
      })
      setAllUsers((prevUsers) =>
        prevUsers.filter((user) => user.id !== id)
      );
    }
    catch(err){
      console.error("kume error tijekom brisanja usera")
    }
  }


  return (
    <div className="flex flex-col items-center min-h-screen bg-[#f5f5f5]">
      <div className="bg-[#3B5B80] w-full pb-10 rounded-b-[40%] flex flex-col items-center justify-center text-center mb-10%">
        <div className="flex justify-evenly gap-12 mt-8 w-3/4">
          <Link to="/" className="w-[50vw] block">
            <Button variant="default" title="Početna stranica" />
          </Link>
        </div>
        <h2 className="text-4xl text-white mt-10 mb-10 font-semibold tracking-wide">Administrator</h2>
        <button
          onClick={() => setShowRequests(prev => !prev)}
          className="
            px-6 py-2
            bg-white text-[#3B5B80]
            rounded-xl font-semibold
            transition-colors duration-200
            hover:bg-gray-100
            cursor-pointer
          "
        >
          {showRequests ? "Prikaži korisnike i dvorane" : "Prikaži zahtjeve"}
        </button>

      </div>

      <div className="flex-grow w-[90vw] px-6 py-6 bg-[#8091A6] mt-[2%] mb-[2%] rounded-2xl">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6 h-full">

          <div className="bg-[#f5f5f5] rounded-2xl p-6 overflow-y-auto">
            <h3 className="text-2xl font-semibold mb-4 text-center">
              {showRequests ? "Zahtjevi za nove dvorane" : "Sve postojeće dvorane"}
            </h3>

            {showRequests 
              ? zahtjevDvorana.length === 0 
                ? <p className="text-center text-gray-500">Nema novih zahtjeva</p>
                : zahtjevDvorana.map((request) => (
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
                      <span>{request.street} {request.streetNumber}, {request.city}, {request.postalCode}</span>

                  <span className="font-semibold">Kategorije:</span>
                  <span>{request.kategorije?.map((kategorija) => kategorija.nazivKategorije).join(", ")}</span>

                  <span className="font-semibold">Opis:</span>
                  <span>{request.opis}</span>
                  

                      <span className="font-semibold">Slike:</span>
                      {request.slike && request.slike.length > 0 ? (
                        <a href={request.slike[0]?.urlSlike} target="_blank" rel="noopener noreferrer"> Slika</a>
                      ) : (
                        <span>Nema slika</span>
                      )}
                      {/* <span>{request.slike ? request.slike[0]?.urlSlike : "Nema slika"}</span> */}
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
            ))
            : allDvorane.length === 0
            ? <p className="text-center text-gray-500">Nema dvorana</p>
            : allDvorane.map((d) => (
                <div key={d.id} className="bg-white rounded-xl p-4 shadow-md mb-4">
                  <div className="grid grid-cols-[140px_1fr] gap-y-2">
                    <span className="font-semibold">Ime dvorane:</span>
                    <span>{d.nazivDvorana}</span>
                    <span className="font-semibold">Vlasnik:</span>
                    <span>{d.vlasnik?.name}</span>
                    <span className="font-semibold">Email:</span>
                    <span>{d.vlasnik?.email}</span>
                    <span className="font-semibold">Kapacitet:</span>
                    <span>{d.kapacitet}</span>
                    <span className="font-semibold">Adresa:</span>
                    <span>{d.adresa?.ulica} {d.adresa?.kucniBroj}, {d.adresa?.mjesto?.nazivMjesto}, {d.adresa?.mjesto?.postanskiBroj} </span>
                    <span className="font-semibold">Opis:</span>
                    <span>{d.opis}</span>
                    <span className="font-semibold">Slike:</span>
                    {d.slike && d.slike.length > 0 ? (
                      <span>
                        <a href={d.slike[0]?.urlSlika} target="_blank" rel="noopener noreferrer"> Slika </a>
                      </span>
                      ) : (
                        <span>Nema slika</span>
                      )}
                  </div>
                  <div className="flex justify-center mt-4">
                  <button className="px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700" onClick={() => handleDeleteDvorana(d.idDvorana)}>
                    IZBRIŠI DVORANU
                  </button>
                </div>
                </div>
          ))}
          </div>

          <div className="bg-[#f5f5f5] rounded-2xl p-6 overflow-y-auto">
            <h3 className="text-2xl font-semibold mb-4 text-center">
              {showRequests ? "Zahtjevi za nove iznajmljivače" : "Svi korisnici"}
            </h3>

            {showRequests  
              ? zahtjevIznajmljivac.length === 0
              ? <p className="text-center text-gray-500">Nema novih zahtjeva</p>
              :zahtjevIznajmljivac.map((request) => (
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
                    <button className="px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700" onClick={() => handleAcceptIznajmljivac(request.id)}>
                      PRIHVATI 
                    </button>
                    <button className="px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700" onClick={() => handleRejectIznajmljivac(request.id)}>
                      ODBIJ 
                    </button>
                  </div>
                </div>
            ))
            : allUsers.length === 0
            ? <p className="text-center text-gray-500">Nema korisnika</p>
            : allUsers.map((user) => (
              <div key={user.id} className="bg-white rounded-xl p-4 shadow-md mb-4">
                <div className="grid grid-cols-[140px_1fr] gap-y-2">
                  <span className="font-semibold">Ime:</span>
                  <span>{user.name}</span>
                  <span className="font-semibold">Email:</span>
                  <span>{user.email}</span>
                </div>
                <div className="flex justify-center mt-4">
                  <button className="px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700" onClick={() => handleDeleteUser(user.id)}>
                    IZBRIŠI KORISNIKA
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