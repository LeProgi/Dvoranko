import Form from "../components/Form.jsx";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { url } from "../main.jsx";
import Button from "../components/Button.jsx";
import Footer from "../components/Footer";
import { useLocation, useNavigate } from "react-router-dom";
import VenueCard from "../components/VenueCard.jsx";


const zahtjevIznajmljivac = () => { 
    try {
      const res = fetch(`${url}/api/user/request/getModerator`, {
        method: "POST",
        credentials: "include",
      });

    } catch (err) {
      console.error("Error pri pokušaju slanja zahtjva:", err);
    }
  };
    


const ProfilePage = () => {
    const location = useLocation();
    
    
    const [seeCheck, setSeeCheck] = useState(false);
     
    const [user, setUser] = useState(location.state?.user ?? null);
    const navigate = useNavigate();

    const [myDvorane, setMyDvorane] = useState([]);
    const [loadingDvorane, setLoadingDvorane] = useState(false);

    useEffect(() => {

        fetch(`${url}/api/auth/user`, {
            credentials: "include",
        })
            .then((res) => {
            if (!res.ok) throw new Error("Not logged in");
            return res.json();
            })
            .then((data) => setUser(data))
            .catch(() => {
                setUser(null);
                navigate("/", { replace: true });
            });
    }, [navigate]);


    useEffect(() => {
        if (!user) return;
        if (user.role !== "MODERATOR") return;

        setLoadingDvorane(true);

        fetch(`${url}/api/moderator/getMyDvorane`, {
            method: "GET",
            credentials: "include",
        })
            .then(res => {
                if (!res.ok) {
                    return res.json().then(errorData => {
                        throw {
                            status: res.status,
                            statusText: res.statusText,
                            message: errorData?.message || "Greška pri dohvaćanju dvorana",
                            details: errorData
                        };
                    }).catch(() => {
                        throw {
                            status: res.status,
                            statusText: res.statusText,
                            message: `HTTP ${res.status}: ${res.statusText}`,
                            details: null
                        };
                    });
                }
                return res.json();
            })
            .then(data => {
                setMyDvorane(data);
                console.log("My dvorane fetched:", data);
            })
            .catch(err => {
                console.error(err);
                setMyDvorane([]);
            })
            .finally(() => {
                setLoadingDvorane(false);
            });
    }, [location.key, user]);



    if (!user) {
        return (
            <div className="flex min-h-screen items-center justify-center">
            <p>Učitavanje profila...</p>
            </div>
        );
    }

    return (
    <div className="flex flex-col min-h-screen items-center bg-gray-100">
      
        <div className="relative bg-[#3B5B80] w-full pb-5 px-[5vw] flex flex-row justify-between items-center text-white">
       
            <div className="flex flex-row items-center gap-[5vw] mt-16 bg-[#3B5B80] px-6 py-4 rounded-2xl ">
            <img
            src={user.pictureUrl}
            alt={user.name}
            className="w-24 h-24 rounded-full border-4 border-white object-cover"
            />
                <div className="text-left">
                    <h2 className="text-2xl font-semibold">
                    {user.name} 
                    </h2>
                    <p className="text-lg opacity-90">{user.email}</p>
                </div>
            </div>
            <div className="flex flex-col justify-center gap-[1vw]">
            
                <Link to="/" className="w-[20vw] block">
                    <Button variant="default" title="Početna stranica" />
                </Link>
                {user.role === "USER" && (
                    <button
                    onClick={() => setSeeCheck(true)}
                    className="w-[20vw]"
                    >
                        <Button variant="default" title="Postani iznajmljivač" />
                    </button>
                )}
            </div>
        </div>

        {seeCheck && (
            <div className="fixed inset-0 z-50 flex items-center justify-center">

                <div
                    className="absolute inset-0 backdrop-blur-sm"
                    onClick={() => setSeeCheck(false)}
                />

        
                <div className="relative bg-white rounded-lg shadow-2xl px-16 py-14 w-[450px] z-20">
                    <h2 className="text-lg font-semibold text-gray-800 mb-4 text-center">
                        Poslati zahtjev za postati iznajmljivač?
                    </h2>

                    <div className="flex gap-3">
                        <button
                            className="flex-1 bg-green-600 text-white py-2 rounded-md hover:bg-green-700 transition"
                            onClick={() => {
                                zahtjevIznajmljivac();
                                setSeeCheck(false);
                            }}
                        >
                            DA
                        </button>

                        <button
                            className="flex-1 bg-red-500 text-white py-2 rounded-md hover:bg-red-600 transition"
                            onClick={() => setSeeCheck(false)}
                        >
                            NE
                        </button>
                    </div>
                </div>
            </div>
)}

        {user.role === "MODERATOR"  && (
            <div className="flex justify-between items-center w-3/4 bg-[#3B5B80] text-white p-4 rounded-lg mb-6 mt-6">
                <h2 className="text-lg font-semibold">Objavi dvoranu</h2>
                <Link to="/form" >
                <button className="bg-white text-[#3B5B80] font-bold px-4 py-1 rounded hover:bg-gray-200 transition">
                    +
                </button>
                </Link>
                
            </div>
        )}

        {user.role === "MODERATOR" && (
            <div className="flex flex-col w-3/4 bg-[#d9d9d9] rounded-[10px] items-center py-6 mt-12 mb-12">
                <h2 className="text-xl font-semibold text-[#3B5B80] mb-6">
                    Moje dvorane
                </h2>

                
                {myDvorane === null && (
                    <p>Ucitavanje...</p>
                )}
                {myDvorane?.data?.length === 0 && (
                    <p>Nemate oglašenih dvorana</p>
                )}
                {myDvorane?.data?.length > 0 &&(
                    <div className="flex flex-col items-center gap-3 w-full">
                        {myDvorane.data?.map((dvorana) => (
                            <div className ="flex items-center gap-3 w-11/12">
                                <Link key = {dvorana.idDvorana} to = {`/venue/${dvorana.idDvorana}`} className="w-11/12 block">
                                    <VenueCard 
                                    name = {dvorana.nazivDvorana}
                                    adresa = {dvorana.adresa
                                                ? `${dvorana.adresa.ulica} ${dvorana.adresa.kucniBroj}, ${dvorana.adresa.mjesto?.nazivMjesto}`
                                                : "Adresa nije dostupna"
                                            }
                                
                                    />
                                </Link>   
                                    <Link key = {dvorana.idDvorana} to = {`/editform/${dvorana.idDvorana}`}
                                    className="px-4 py-2 bg-[#3B5B80] hover:bg-[#2F4B6A] transition-colors text-white rounded cursor-pointer">
                                    Uredi dvoranu
                                    </Link>
                            </div>
                        ))}
                    </div>
                )}
                </div>
         )}

        <div className="flex flex-col w-3/4 bg-[#d9d9d9] shadow-lg rounded-[10px] items-center py-6 mt-12 mb-12">
            <h2 className="text-xl font-semibold mb-6 text-[#3B5B80]">
            Moje rezervacije
            </h2>

            <p className="text-gray-600">Trenutno nemate rezervacija.</p>
        </div>



        <Footer />
    </div>
  );
};

export default ProfilePage;

