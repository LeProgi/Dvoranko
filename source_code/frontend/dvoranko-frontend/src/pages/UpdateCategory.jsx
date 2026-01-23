import React, {useState, useEffect} from "react";
import Button from "../components/Button";
import { data, Link } from "react-router-dom";
import Footer from "../components/Footer";
import { url } from "../main";
import { all } from "axios";

const UpdateCategory = () => {
  ///////////////////
  const[kategorije, setKategorije] = useState([])
  const[loadingKategorije, setLoadingKategorije] = useState([])
  const[inputKategorija, setInputKategorija] = useState("")


  useEffect(() => {
    
    fetch(`${url}/api/auth/user`, {
            credentials: "include",
        })
            .then((res) => {
            if (res.status === 200) return res.json();
            throw new Error("Not logged in");
            })
            .then((data) => {
                if(data.role !== "ADMIN"){
                        navigate("/", {replace:true});
                        return;
                    }
                })

    const fetchKategorije = async () => {
      try {
        //zahtjevi za dvorane
        const res = await fetch(`${url}/api/public/kategorije`, {
          method: "GET",
          credentials: "include",
        });
        
        if (!res.ok) throw new Error("Error kume");
        const dataKategorije = await res.json();
        setKategorije(dataKategorije.data);

      } catch (err) {
        console.error("Kume error tijekom getall kategorije", err);
      }
    };

    fetchKategorije();
  }, []);


  // const handleCreateKategorija = async(ime_kategorije) => {
  //   try{
  //     const res = await fetch(`${url}/api/public/kategorije`)
  //   }
  // }

  const handleAddKategorija = async(imeKategorija) => {
    if(!imeKategorija.trim()) return;

    try{
        const res = await fetch(`${url}/api/public/kategorije`, {
            method: "POST",
            credentials: "include",
            headers:{
                "Content-Type":"application/json"
            },
            body: JSON.stringify({nazivKategorije: imeKategorija})
        });

        if(!res.ok) throw new Error("error kume");

        const result = await res.json();
        const dodanaKategorija = result.data

        setKategorije(prev => [...prev, dodanaKategorija]);
        setInputKategorija("");

    } catch(error){
        console.error(error)
    }
  }

  const handleDeleteKageorija = async(idKategorija) => {
    try{
        const res = await fetch(`${url}/api/public/kategorije/${idKategorija}`,{
            method: "DELETE",
            credentials: "include",
        });

        if(!res.ok) throw new Error("error kume");

        setKategorije((prevKategorije) =>
        prevKategorije.filter((k) => k.idKategorija !== idKategorija)
      );
        
    } catch(error){
        console.error(error)
    }
  }

  return (
    <div className="flex flex-col items-center min-h-screen bg-[#f5f5f5]">
      <div className="bg-[#3B5B80] w-full pb-10 rounded-b-[40%] flex flex-col items-center justify-center text-center mb-10">
        <div className="flex justify-evenly gap-12 mt-8 w-3/4">
          <Link to="/admin" className="w-[50vw] block">
            <Button variant="default" title="Admin" />
          </Link>
        </div>
        <h2 className="text-4xl text-white mt-10 mb-10 font-semibold tracking-wide">Ažuriranje kategorija</h2>
        </div>
        
        <div className="flex flex-row gap-3">
            <input
                type="text"
                value={inputKategorija}
                onChange={(e) => setInputKategorija(e.target.value)}
                placeholder="Unesite naziv kategorije"
                className="
                    w-3/4
                    px-4 py-2
                    bg-[#3B5B80]
                    text-white
                    rounded-xl
                    font-semibold
                    outline-none
                    focus:ring-2
                    focus:ring-[#3B5B80]
                    transition
                "
                />
                <button 
                className="bg-[#3B5B80] text-white font-bold px-4 py-1 rounded-xl hover:bg-gray-200 transition cursor-pointer "
                onClick={() => handleAddKategorija(inputKategorija)}>
                    +
                </button>
        </div>

            <div className="flex flex-col md:w-3/4 w-[95%] bg-[#d9d9d9] shadow-lg rounded-[10px] items-center py-6 mt-12 mb-12">
            <h2 className="text-xl font-semibold md:mb-6 mb-3 text-[#3B5B80]">
            Postojeće kategorije
            </h2>
            <div className="flex flex-col items-center gap-3 md:mt-10 mt-5 md:mb-12 mb-6 w-full">
                {kategorije.map((kategorija) => (
                    <div  className="flex md:w-3/4 w-[95%] gap-4 items-center">
                    <div className="flex-1 bg-white shadow-lg rounded-xl p-3 flex flex-col gap-3 hover:shadow-2xl transition-shadow">
                        <div className="flex justify-center items-center">
                        <h2 className="text-xl font-bold text-center">
                            {kategorija.nazivKategorije}
                        </h2>

                        </div>

                    </div>

                    <button
                    className ="px-4 py-2 bg-[#3B5B80] text-white rounded-lg bg-red-600 text-white rounded-lg hover:bg-red-700 transition-colors cursor-pointer"
                    onClick={() => handleDeleteKageorija(kategorija.idKategorija)}>
                        Obriši kategoriju
                    </button>
                    </div>
                ))}

        </div>  
        </div>
                
      <Footer />
    </div>
  );
};

export default UpdateCategory;