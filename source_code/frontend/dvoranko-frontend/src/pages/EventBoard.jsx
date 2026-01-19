import { use, useEffect, useState } from "react";
import { url } from "../main.jsx";
import Button from "../components/Button";
import { Link } from "react-router-dom";
import Footer from "../components/Footer";
import ReservationCard from "../components/ReservationCard.jsx";



const EventBoard = () => {
    const [user, setUser] = useState(null);
    const [publicEvents, setPublicEvents] = useState([]);

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

    useEffect(() => {
        fetch(`${url}/api/public/termini/public`)
        .then((res) => {
            if (res.status === 200) return res.json();
            throw new Error("Failed to fetch public events");
        })
        .then((data) => {
            setPublicEvents(data);
            // console.log(data);
        })
        .catch((err) => {
            console.log(err);
        });

    }, []);

    const handleGoogleLogin = () => {
        window.location.href = `${url}/oauth2/authorization/google`;
    };

    return (
        <div className="flex flex-col min-h-screen w-full bg-gray-100 items-center">
        
              <div className="bg-[#3B5B80] w-full pb-10 rounded-b-[40%] flex flex-col items-center text-center">
                
                <div className="flex justify-evenly gap-12 mt-4 w-3/4">
                    <Link to="/" className="w-[50vw] block">
                        <Button variant="default" title="Početna stranica" />
                    </Link>
                    <Link to="/maps" className="w-[50vw] block">
                        <Button variant="default" title="Karta" />
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
        
                <h2 className="text-4xl text-white mt-10 mb-10 font-semibold tracking-wide">Oglasna ploča</h2>
            </div>
            <div className="flex flex-col w-3/4 bg-[#d9d9d9] rounded-[10px] items-center py-6 mt-12 mb-12">
                <h2 className="text-xl font-semibold mb-6">Popis javnih događanja</h2>
                <div className="flex flex-col items-center gap-4 w-full">
                    {publicEvents.length === 0 ? (
                        <p className="text-gray-600">Trenutno nema javnih događanja.</p>
                    ) : (
                        publicEvents.map((event) => (
                            <ReservationCard
                                key={event.id}
                                nameDogadanje={event.imeDogadanja}
                                opisDogadanje={event.opisDogadanja}
                                nameDvorana={event.dvorana.nazivDvorana}
                                adresa={event.dvorana.adresa.ulica + " " + event.dvorana.adresa.kucniBroj + ", " + event.dvorana.adresa.mjesto.nazivMjesto}
                                imgUrl={event.dvorana.slike[0].urlSlika}
                                vrijemeOd={event.datumVrijemeStart}
                                vrijemeDo={event.datumVrijemeEnd}
                            />
                        ))
                    )}
                </div>
            </div>
            <Footer/>
        </div>
    );
};

export default EventBoard;