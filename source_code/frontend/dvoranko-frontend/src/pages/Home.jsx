import Button from "../components/Button";
import { data, Link } from "react-router-dom";
import VenueCard from "../components/VenueCard";
import { useEffect, useState } from "react";
import Footer from "../components/Footer";
import Form from "../components/Form";
import Filter from "../components/Filter.jsx"
import { url } from "../main.jsx";
const Home = () => {
    
    const [hasLoggedIn, setHasLoggedIn] = useState(false);
    const [user, setUser] = useState(null);
    const [venues, setVenues] = useState([]);
    const [filteredVenues, setFilteredVenues] = useState([]);
    
    useEffect(() =>{
        console.log(`${url}/api/public/dvorane`);
        fetch(`${url}/api/public/dvorane`,{
            credentials: "include",
        })
            .then(res => res.json())
            .then(data => {
                if(data){
                console.log("datadohvacen");
                }
                const formatted = data.data.map(dvorana=>({
                    id: dvorana.idDvorana,
                    name: dvorana.nazivDvorana,
                    kapacitet: dvorana.kapacitet,
                    postanskiBroj: dvorana.adresa?.mjesto?.postanskiBroj,
                    adresa:dvorana.adresa ?  `${dvorana.adresa.ulica} ${dvorana.adresa.kucniBroj}, ${dvorana.adresa.mjesto?.nazivMjesto}` : ""


                }));
                setVenues(formatted);
                setFilteredVenues(formatted);
            })
            .catch(err => console.error("neuspjelo dohvacanje dvorane", err));
    }, []);
    
    useEffect(() => {
        console.log(`${url}/api/auth/user`);
        fetch(`${url}/api/auth/user`, {
            credentials: "include",
        })
        .then((res) =>  {
            console.log(res);
            if(res.status === 200) return res.json();
            throw new Error("Nije ulogiran");
            
        })
        .then((data) => {
            setHasLoggedIn(true);
            setUser(data);
            console.log(data)
        })
        .catch(() => {
            setHasLoggedIn(false);
            
        });
    }, []);

    const handleGoogleLogin = () => {
    // window.location.href = "https://dvoranko.onrender.com/oauth2/authorization/google";
    console.log(`${url}/oauth2/authorization/google`);
        window.location.href = `${url}/oauth2/authorization/google`;
    };

    const applyFilters = (filters) => {
        let result = [...venues];

        // Kapacitet
        if (filters.capacity) {
            result = result.filter(v => {
                const cap = v.kapacitet;
                if (filters.capacity === "0-20") return cap <= 20;
                if (filters.capacity === "20-50") return cap > 20 && cap <= 50;
                if (filters.capacity === "50-70") return cap > 50 && cap <= 70;
                if (filters.capacity === "70+") return cap > 70;
                return true;
            });
        }

        // Poštanski broj
        if (filters.zip) {
            result = result.filter(v =>
                String(v.postanskiBroj).startsWith(filters.zip)
            );
        }

        setFilteredVenues(result);
    };

    return (
    <div className="flex flex-col min-h-screen w-full bg-gray-100 items-center">

      <div className="bg-[#3B5B80] w-full pb-10 rounded-b-[40%] flex flex-col items-center text-center">
        
        <div className="flex justify-evenly gap-12 mt-8 w-3/4">
            <Link to="/event-board" className="w-[50vw] block">
                <Button variant="default" title="Oglasna ploča" />
            </Link>
            <Link to="/maps" className="w-[50vw] block">
                <Button variant="default" title="Karta" />
            </Link>
            
            {user ? (
                <Link to="/my-profile" state ={{ user }}>
               <div className="w-12 h-12 rounded-full border-2 border-white overflow-hidden shadow-md hover:scale-105 transition-transform duration-200">
                <img
                    src={user.pictureUrl}
                    alt={user.name}
                    title={user.name}
                    className="w-full h-full object-cover"
                />
            </div>
            </Link>
            ):<Button variant="default" title="Prijavi se"  onClick={handleGoogleLogin}/>
}
            
        </div>

        <h1 className="text-4xl text-white mt-10 mb-10 font-semibold tracking-wide">Dvoranko</h1>
      </div>
        <Filter onApply={applyFilters} />
        <div className="flex flex-col w-3/4 bg-[#d9d9d9] rounded-[10px] items-center py-6 mt-4 mb-12">
                <h2 className="text-xl font-semibold mb-6">Popis dvorana</h2>
                <div className="flex flex-col items-center gap-4 w-full">
                    {filteredVenues.map((venue) => (
                        <Link key={venue.id} to={`/venue/${venue.id}`} className="w-11/12 block">
                            <VenueCard name={venue.name} adresa={venue.adresa} />
                        </Link>
                    ))}
                </div>
        </div>
        
        <Footer/>
    </div>
  );
}

export default Home;