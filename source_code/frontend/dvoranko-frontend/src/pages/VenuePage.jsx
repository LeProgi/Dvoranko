
import { useEffect, useState } from "react";
import Button from "../components/Button";
import { Link } from "react-router-dom";
import { useParams } from "react-router-dom";
import { url } from "../main.jsx";




const VenuePage = () => {
    const {id} = useParams();
    const [venue, setVenue] = useState(null);
    useEffect(() =>{
        fetch(`${url}/api/public/dvorane/${id}`,{
            credentials: "include",
        })
            .then(res => res.json())
            .then(data => {
                const dvorana = data.data;
                setVenue({
                    name: dvorana.nazivDvorana,
                    adresa: dvorana.adresa ? `${dvorana.adresa.ulica} ${dvorana.adresa.kucniBroj}, ${dvorana.adresa.mjesto?.nazivMjesto}`:
                    "",
                    kapacitet: dvorana.kapacitet,
                    opis: dvorana.opis
                });                
            })
            .catch(err => console.error("neuspjelo dohvacanje dvorane", err));
    }, [id]);

    if(!venue) return <p className="text-center mt-10 text-white">Učitavanje...</p>;
    
   
    return (
        <div className="flex justify-center items-center min-h-screen bg-[#5B7692]">
            <div className="flex bg-[#F5F5F5] w-3/4 max-w-5xl h-[80vh] rounded-[20px] shadow-lg overflow-hidden">
                
                
                <div className="bg-[#3B5B80] w-1/3 flex items-center justify-center rounded-l-[20px]">
                    <p className="text-white text-lg">Slika prostora</p>
                </div>

                
                <div className="flex flex-col justify-between w-2/3 p-10 relative">
                    <div>
                        <Link to="/">
                            <button className="absolute top-6 right-6 bg-[#3B5B80] text-[#f5f5f5] rounded-[15%] w-[25px] h-[25px] flex items-center justify-center cursor-pointer hover:bg-[#2F4B6A] transition-colors">
                                X
                            </button>
                        </Link>
                        <h2 className="text-2xl font-semibold text-[#1C2D3A] mb-6">
                            {venue.name}
                        </h2>

                        <p className="text-[#1C2D3A] mb-2">
                            <span className="font-semibold">Lokacija: </span> {venue.adresa}
                        </p>
                        <p className="text-[#1C2D3A] mb-2">
                            <span className="font-semibold">Kapacitet: </span> {venue.kapacitet}
                        </p>
                        <p className="text-[#1C2D3A] mb-2">
                            <span className="font-semibold">Opis prostora:</span> 
                            {venue.opis}
                        </p>
                    </div>

                    
                    <div className="self-end mt-8">
                        <Link to="/reservation" state={{ venueId: id }}>
                            <Button 
                                variant="default"
                                title="Rezerviraj dvoranu"
                                className="bg-[#3B5B80] text-white px-6 py-2 rounded-lg hover:bg-[#2F4B6A]"
                            />
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default VenuePage;
