import Button from "../components/Button";
import { data, Link, useNavigate } from "react-router-dom";
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
    const [imagesLoaded, setImagesLoaded] = useState(false);
    const [categories, setCategories] = useState([]);
    const navigate = useNavigate();


    
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

        console.log(`${url}/api/public/dvorane`);
        fetch(`${url}/api/public/dvorane`,{
            credentials: "include",
        })
            .then(res => res.json())
            .then(data => {
                if(data){
                console.log(data);
                }
                const formatted = data.data.map(dvorana=>({
                    id: dvorana.idDvorana,
                    name: dvorana.nazivDvorana,
                    kapacitet: dvorana.kapacitet,
                    postanskiBroj: dvorana.adresa?.mjesto?.postanskiBroj,
                    
                    adresa:dvorana.adresa ?  `${dvorana.adresa.ulica} ${dvorana.adresa.kucniBroj}, ${dvorana.adresa.mjesto?.nazivMjesto}` : "",
                    imgUrl: dvorana.slike && dvorana.slike.length > 0 ? dvorana.slike[0].urlSlika : "",
                    cijenaPoSatu: dvorana.cijenaPoSatu,
                    categories: dvorana.kategorije.map(k => k.nazivKategorije) 

                }));
                setVenues(formatted);
                setFilteredVenues(formatted);


                const allCategories = Array.from(
                    new Set(data.data.flatMap(dvorana => dvorana.kategorije.map(k => k.nazivKategorije)))
                );
                setCategories(allCategories);
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


    useEffect(() => {
        if (venues.length === 0) return;

        const preloadImages = venues
            .filter(v => v.imgUrl)
            .map(v => {
                return new Promise(resolve => {
                    const img = new Image();
                    img.src = v.imgUrl;
                    img.onload = () => resolve(v.imgUrl);
                    img.onerror = () => resolve(v.imgUrl); // greške ignoriramo
                });
            });

        Promise.all(preloadImages).then(() => setImagesLoaded(true));
    }, [venues]);


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


        if (filters.price) {
            result = result.filter(v => {
                const pr = v.cijenaPoSatu;
                if (filters.price === "0-20") return pr <= 20;
                if (filters.price === "20-40") return pr > 20 && pr <= 40;
                if (filters.price === "40-60") return pr > 40 && pr <= 60;
                if (filters.price === "60+") return pr > 60;
                return true;
            });
        }

        // Kategorije
        if (filters.category) {
            result = result.filter(v => v.categories.includes(filters.category));
        }

        setFilteredVenues(result);
    };

    return (
    <div className="flex flex-col min-h-screen w-full bg-gray-100 items-center">

      <div className="bg-[#3B5B80] w-full pb-10 rounded-b-[40%] flex flex-col items-center text-center">
        
        <div className="flex justify-evenly md:gap-12 gap-6 mt-8 w-3/4">
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
                    src={user.pictureUrl ? user.pictureUrl : "../user.svg"}
                    alt={user.name}
                    title={user.name}
                    className="w-full h-full object-cover"
                />
            </div>
            </Link>
            ):<Button id="login-button" variant="default" title="Prijavi se"  onClick={handleGoogleLogin}/>
}
            
        </div>

        <h1 className="text-4xl text-white mt-10 mb-10 font-semibold tracking-wide">Dvoranko</h1>
      </div>
        <Filter onApply={applyFilters} categories={categories}/>
        <div className="flex flex-col md:w-3/4 w-[95%] bg-[#d9d9d9] rounded-[10px] items-center py-6 mt-4 mb-12">
                <h2 className="text-xl font-semibold mb-6">Popis dvorana</h2>
                {venues.length == 0 && <p className="text-gray-500">Nema objavljenih dvorana...</p>}
                {!imagesLoaded && venues.length > 0 ? (
                    
                    <div className="flex flex-col justify-center items-center w-full py-10 gap-4">
                        <p className="text-gray-500">Učitavanje dvorana...</p>
                        <div className="border-4 border-gray-300 border-t-4 border-t-blue-500 rounded-full w-12 h-12 animate-spin"></div>
                    </div>
                ) : filteredVenues.length === 0 && venues.length > 0 ? (
                    <div className="flex flex-col justify-center items-center w-full py-10">
                        <p className="text-gray-600 text-lg font-medium">
                            Nema dvorana po vašim željama.
                        </p>
                        <p className="text-gray-500 text-sm mt-2">
                            Pokušajte promijeniti filtere.
                        </p>
                    </div>
                ) : (
                    <div className="flex flex-col items-center gap-4 w-full">
                    {filteredVenues.map((venue) => (
                        <Link id={`dvorana-link-${venue.id}`} key={venue.id} to={`/venue/${venue.id}`} state={{ from: '/' }} className="w-11/12 block">
                            <VenueCard name={venue.name} adresa={venue.adresa} imgUrl = {venue.imgUrl}  cijenaPoSatu={venue.cijenaPoSatu} potvrdeno={true}/>
                        </Link>
                    ))}
                </div>
                )}
        </div>
        
        <Footer/>
    </div>
  );
}

export default Home;