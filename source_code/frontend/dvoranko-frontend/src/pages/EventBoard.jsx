import { useEffect, useState } from "react";
import { url } from "../main.jsx";
import Button from "../components/Button";
import VenueCard from "../components/VenueCard";
import { Link } from "react-router-dom";
import Footer from "../components/Footer";
import ReservationCard from "../components/ReservationCard.jsx";
const EventBoard = () => {
    const [user, setUser] = useState(null);

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

const handleGoogleLogin = () => {
  window.location.href = `${url}/oauth2/authorization/google`;
};

const getDateFromTimestamp = (timestamp) => {
        try{
            const date = timestamp.slice(0, 10);
            const[year, month, day] = date.split("-")
            return `${day}.${month}.${year}`
        }
        catch(err){
            console.log(err)
        }
    }

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
                    <ReservationCard 
                        nameDogadanje={"Interliber"}
                        opisDogadanje={"Najveca izlozba knjiga u regionu"}
                        nameDvorana={"Velesajam Zagreb"}
                        adresa={"Avenija Dubrovnik 15, Zagreb"}
                        imgUrl={"https://res.cloudinary.com/dnrpd10np/image/upload/v1768420464/dvorane/10/img_1.png"}
                        vrijemeOd={"2026-01-07 10:00:00"}
                        vrijemeDo={"2026-01-07 20:00:00"}
                        ></ReservationCard>
                    
                </div>
            </div>
            <Footer/>
        </div>
    );
};

export default EventBoard;