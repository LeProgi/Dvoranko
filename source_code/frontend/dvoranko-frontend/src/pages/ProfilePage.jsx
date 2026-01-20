import Form from "../components/Form.jsx";
import { use, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { url } from "../main.jsx";
import Button from "../components/Button.jsx";
import Footer from "../components/Footer";
import { useLocation, useNavigate } from "react-router-dom";
import VenueCard from "../components/VenueCard.jsx";
import ModeratorReservationsCard from "../components/ModeratorReservationsCard.jsx";


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
    const [myReservations, setMyReservations] = useState(false);
    const [loadingReservations, setLoadingReservations] = useState(false);
    const [dvoranaCache, setDvoranaCache] = useState({})

    const [zahtjeviCount, setZahtjeviCount] = useState({});
    const [myRequests, setMyRequests] = useState([]);

    useEffect(() => {

        fetch(`${url}/api/auth/user`, {
            credentials: "include",
        })
            .then((res) => {
            if (!res.ok) throw new Error("Not logged in");
                return res.json();
            })
            .then((data) => {
                setUser(data)
                // console.log("User data fetched:", data);
            })
            .catch(() => {
                setUser(null);
                navigate("/", { replace: true });
            });
    }, [navigate]);

    const getDvorana = async (id) => {
        if (dvoranaCache[id]) return; 

        try {
            const res = await fetch(`${url}/api/public/dvorane/${id}`);
            const json = await res.json();

            setDvoranaCache(prev => ({
            ...prev,
            [id]: json.data
            }));
        } catch (err) {
            console.error("Failed to fetch dvorana", err);
        }
        };


    const deleteReservation = async (id) => {
        try {
              const res = await fetch(`${url}/api/public/termini/${id}`, {
                method: "DELETE",
                credentials: "include",
              })
        
              if (!res.ok) throw new Error("Kume nesto ti se strgalo, server kaze da nije ok");
              setMyReservations((prevReservations) => ({
                ...prevReservations,
                data: prevReservations.data.filter((reservation) => reservation.id !== id)
            }));
            }
        catch(err){
            console.error(err)
        }
    }


    const deleteRequest = async (id) => {
        try {
              const res = await fetch(`${url}/api/public/termini/zahtjevi/${id}`, {
                method: "DELETE",
                credentials: "include",
              })
        
              if (!res.ok) throw new Error("Kume nesto ti se strgalo, server kaze da nije ok");
              setMyRequests((prevRequests) => (
                prevRequests.filter((request) => request.id !== id)
            ));
            }
        catch(err){
            console.error(err)
        }
    }

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
                // console.log("My dvorane fetched:", data);
            })
            .catch(err => {
                console.error(err);
                setMyDvorane([]);
            })
            .finally(() => {
                setLoadingDvorane(false);
            });
    }, [location.key, user]);

    useEffect(() => {
        if (!user) return;

        setLoadingReservations(true);

        fetch(`${url}/api/user/getMyReservations`, {
            method: "GET",
            credentials: "include",
        })
        .then(res => {
            if(!res.ok) {
                return res.json().then(errorData => {
                    throw {
                        status: res.status,
                        statusText: res.statusText,
                        message: errorData?.message || "Greška pri dohvaćanju termina",
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
            setMyReservations(data)
            console.log("My reservations fetched:", data)
            data?.data?.forEach(reservation => {
                // console.log(reservation)
                // console.log(reservation.idDvorana)

                if(reservation.idDvorana) {
                    getDvorana(reservation.idDvorana)
                }
            })
        })
        .catch(err => {
            console.error(err);
            setMyReservations([]);
        })
        .finally(() =>{
            setLoadingReservations(false);
        });

        fetch(`${url}/api/public/termini/my/requests`, {
            method: "GET",
            credentials: "include",
        })
        .then(res => {
            if (!res.ok) throw new Error("Greška pri dohvaćanju mojih zahtjeva");
            return res.json();
        })
        .then(data => {
            setMyRequests(data);
            console.log("My requests fetched:", data);
        })
        .catch(err => {
            console.error(err);
            setMyRequests([]);
        });

    }, [])


    const logout = async () => {
        try {
            const res = await fetch(`${url}/api/auth/logout`, {
                method: "POST",
                credentials: "include",
            });
            
            // console.log("Logout response:", res);
            if (!res.ok) throw new Error("Logout failed");

            setUser(null);
            navigate("/", { replace: true });
        } catch (err) {
            console.error("Logout error:", err);
        }
    };


    

    useEffect(() => {
        if (!myDvorane?.data) return;

        myDvorane.data.forEach(async (dvorana) => {
            try {
                const res = await fetch(
                    `${url}/api/moderator/getZahtjeviForDvorana/${dvorana.idDvorana}`,
                    { credentials: "include" }
                );

                if (!res.ok) return;

                const json = await res.json();
                const count = json.data?.length ?? 0;

                setZahtjeviCount(prev => ({
                    ...prev,
                    [dvorana.idDvorana]: count
                }));
            } catch (err) {
                console.error(err);
            }
        });
    }, [myDvorane]);


    return (
        <div className="flex flex-col min-h-screen items-center bg-gray-100 relative">
            
            {!user && (
                <div className="fixed inset-0 z-50 flex flex-col items-center justify-center bg-black bg-opacity-30 backdrop-blur-sm">
                    <div className="loader ease-linear rounded-full border-8 border-t-8 border-gray-200 h-24 w-24 mb-4"></div>
                    <p className="text-white text-xl font-semibold">Učitavanje profila...</p>
                </div>
            )}

            {user && (
                <>
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
                    <div className="flex flex-col justify-center gap-[0.5vw]">


                        <Link to="/" className="w-[20vw] block">
                            <Button variant="default" title="Početna stranica" />
                        </Link>

                        <Button title={"Odjavi se"} variant={"default"} onClick={logout}/>

                        {user.role === "USER" && (
                            <Button variant="default" title="Postani iznajmljivač" onClick={() => setSeeCheck(true)} />
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
                                Želiš li postati iznajmljivač?
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
                                    <div key={dvorana.idDvorana} className ="flex items-center gap-3 w-11/12">
                                        <Link to = {`/venue/${dvorana.idDvorana}`} state={{ from: '/my-profile' }} className="w-11/12 block">
                                            <VenueCard 
                                            name = {dvorana.nazivDvorana}
                                            adresa = {dvorana.adresa
                                                        ? `${dvorana.adresa.ulica} ${dvorana.adresa.kucniBroj}, ${dvorana.adresa.mjesto?.nazivMjesto}`
                                                        : "Adresa nije dostupna"
                                                    }
                                            imgUrl={ dvorana.slike && dvorana.slike.length > 0 ? dvorana.slike[0].urlSlika : "" }
                                            cijenaPoSatu={dvorana.cijenaPoSatu}
                                            />
                                        </Link>   
                                        <Link to = {`/editform/${dvorana.idDvorana}`}
                                        className="px-4 py-2 bg-[#3B5B80] hover:bg-[#2F4B6A] transition-colors text-white rounded cursor-pointer">
                                        Uredi dvoranu
                                        </Link>

                                        <Link
                                            to={`/reservations/${dvorana.idDvorana}`}
                                            className="relative px-4 py-2 bg-[#3B5B80] hover:bg-[#2F4B6A] transition-colors text-white rounded cursor-pointer">
                                            Pogledaj termine

                                            {zahtjeviCount[dvorana.idDvorana] > 0 && (
                                                <span className="absolute -top-2 -right-2 bg-red-600 text-white text-xs font-bold rounded-full w-6 h-6 flex items-center justify-center">
                                                    {zahtjeviCount[dvorana.idDvorana]}
                                                </span>
                                            )}
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
                    <div className="flex flex-col items-center gap-3 mt-10 mb-12 w-full">
                        {myReservations?.data?.map((reservation, index) => (
                            <div key={index} className="flex w-3/4 gap-4 items-center">
                                <ModeratorReservationsCard
                                    key={reservation.id}
                                    imeVlasnika={dvoranaCache[reservation.idDvorana]?.vlasnik?.name || "Nepoznato"}
                                    imeDogadanja={reservation.imeDogadanja}
                                    opisDogadanja={reservation.opisDogadanja}
                                    datumVrijemeStart={reservation.datumVrijemeStart}
                                    datumVrijemeEnd={reservation.datumVrijemeEnd}
                                    jeJavniEvent={reservation.je_javni_event}
                                    idRequest={reservation.id}
                                    cijenaPoSatu={dvoranaCache[reservation.idDvorana]?.cijenaPoSatu || 0}
                                    potvrdeno={true}
                                />

                                <button
                                className ="px-4 py-2 bg-[#3B5B80] text-white rounded-lg bg-red-600 text-white rounded-lg hover:bg-red-700 transition-colors cursor-pointer"
                                onClick={() => deleteReservation(reservation.id)}>
                                    Otkaži termin
                                </button>
                            </div>
                        ))}

                        {myRequests?.map((request) => (
                            <div key={request.id} className="flex w-3/4 gap-4 items-center">
                                <ModeratorReservationsCard
                                    key={request.id}
                                    imeVlasnika={""}
                                    imeDogadanja={request.imeDogadanja}
                                    opisDogadanja={request.opisDogadanja}
                                    datumVrijemeStart={request.datumVrijemeStart}
                                    datumVrijemeEnd={request.datumVrijemeEnd}
                                    jeJavniEvent={request.jeJavniEvent}
                                    idRequest={request.id}
                                    cijenaPoSatu={request.dvorana.cijenaPoSatu}
                                    potvrdeno={false}
                                />

                                <button
                                className ="px-4 py-2 bg-[#3B5B80] text-white rounded-lg bg-red-600 text-white rounded-lg hover:bg-red-700 transition-colors cursor-pointer"
                                onClick={() => deleteRequest(request.id)}>
                                    Otkaži zahtjev
                                </button>
                            </div>
                        ))}
                    </div>

                </div>
                </>
            )};
            <Footer />
        </div>
    );
};

export default ProfilePage;

