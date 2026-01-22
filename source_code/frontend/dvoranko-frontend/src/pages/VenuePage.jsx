
import { useEffect, useState } from "react";
import Button from "../components/Button";
import { Link, useNavigate, useLocation } from "react-router-dom";
import { useParams } from "react-router-dom";
import { url } from "../main.jsx";




const VenuePage = () => {
    const {id} = useParams();
    const navigate = useNavigate();
    const location = useLocation();
    const [venue, setVenue] = useState(null);
    const fromPath = location.state?.from || '/';
    useEffect(() =>{
        
        fetch(`${url}/api/auth/user`, {
            credentials: "include",
        })
        .then((res) => res.json())
        .then((data) => {
            if(data.role === "ADMIN"){
                    navigate("/admin", {replace:true});
                    return;
                }
        })

        fetch(`${url}/api/public/dvorane/${id}`,{
            credentials: "include",
        })
            .then(res => res.json())
            .then(data => {
                console.log(data);
                const dvorana = data.data;
                setVenue({
                    name: dvorana.nazivDvorana,
                    adresa: dvorana.adresa ? `${dvorana.adresa.ulica} ${dvorana.adresa.kucniBroj}, ${dvorana.adresa.mjesto?.nazivMjesto}`:
                    "",
                    kapacitet: dvorana.kapacitet,
                    cijenaPoSatu: dvorana.cijenaPoSatu,
                    opis: dvorana.opis,
                    slika: dvorana.slike && dvorana.slike.length > 0 ? dvorana.slike[0].urlSlika : ""
                });                
            })
            .catch(err => console.error("neuspjelo dohvacanje dvorane", err));
    }, [id]);

    if(!venue) return <p className="text-center mt-10 text-white">Učitavanje...</p>;
    
   
    return (
        <div className="flex justify-center items-center min-h-screen bg-[#5B7692]">
            <div className="flex flex-col bg-[#F5F5F5] w-[95%] md:w-3/4 max-w-6xl min-h-[80vh] max-h-[100vh] rounded-[20px] shadow-lg overflow-hidden">
                

                
                <div className="flex flex-col justify-between w-full p-3 md:p-6 relative">
                    <div>
                        <button 
                            onClick={() => navigate(fromPath)}
                            className="absolute top-6 right-6 bg-[#3B5B80] text-[#f5f5f5] rounded-[15%] w-[25px] h-[25px] flex items-center justify-center cursor-pointer hover:bg-[#2F4B6A]"
                        >
                            X
                        </button>
                        <h2 className="text-2xl font-semibold text-[#1C2D3A] mb-6" id="ime-dvorana">
                            {venue.name}
                        </h2>

                        <p className="text-[#1C2D3A] mb-2" id="lokacija">
                            <span className="font-semibold">Lokacija: </span> {venue.adresa}
                        </p>
                        <p className="text-[#1C2D3A] mb-2" id="kapacitet">
                            <span className="font-semibold">Kapacitet: </span> {venue.kapacitet}
                        </p>
                        {venue.cijenaPoSatu && (
                            <p className="text-[#1C2D3A] mb-2" id="cijena">
                                <span className="font-semibold">Cijena po satu: </span> {venue.cijenaPoSatu} €/h
                            </p>
                        )}
                        <p className="text-[#1C2D3A] mb-2" id="opis">
                            <span className="font-semibold">Opis prostora: </span> 
                            {venue.opis}
                        </p>
                    </div>

                    
                    <div className="self-end mt-2">
                        <Link to="/reservation" state={{ venueId: id }}>
                            <Button 
                                variant="default"
                                title="Rezerviraj dvoranu"
                                className="text-white px-6 py-2 rounded-lg"
                                id="rezerviraj-dvoranu-btn"
                            />
                        </Link>
                    </div>
                </div>

                 <div className="bg-[#3B5B80] w-full h-[60vh]  flex items-center justify-center rounded-l-[20px]">
                    {/* <p className="text-white text-lg">Slika prostora</p> */}
                    <img src={venue.slika} alt="Slika dvorane" className="h-full w-full object-cover" />
                </div>

            </div>
        </div>
    );
};

export default VenuePage;
