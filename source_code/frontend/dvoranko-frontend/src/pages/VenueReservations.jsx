import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { url } from "../main.jsx";
import Footer from "../components/Footer";
import { data, Link } from "react-router-dom";
import Button from "../components/Button.jsx";
import ModeratorReservationsCard from "../components/ModeratorReservationsCard.jsx";

const VenueReservations = () => {
    const { idDvorana } = useParams();
    const navigate = useNavigate();

    const [reservations, setReservations] = useState([]);
    const [reservationRequests, setReservationRequests] = useState([])
    const [loading, setLoading] = useState(true);
    const [dvorana, setDvorana] = useState(null)
    const [userMap, setUsersMap] = useState({})


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

    useEffect(() => {
        if (!idDvorana){
            return;
    } 

    const fetchData = async () => {
        try {
        const res = await fetch(
            `${url}/api/moderator/getZahtjeviForDvorana/${idDvorana}`,
            {
            credentials: "include",
            }
        );

        if (!res.ok) throw new Error("Fetch failed");

        const data = await res.json();
        setReservationRequests(data.data || []);
        console.log("Reservation requests:", data.data);

        const resDvorana = await fetch(`${url}/api/public/dvorane/${idDvorana}`);
        if (!resDvorana.ok) throw new Error("Dvorana fetch failed");
        const dvoranaJson = await resDvorana.json();
        setDvorana(dvoranaJson.data);

        const resPotvrdeniTermini = await fetch(
            `${url}/api/moderator/getPotvrdeniTerminiForDvorana/${idDvorana}`,
             {
            credentials: "include",
            });
        if(!resPotvrdeniTermini.ok) throw new Error("fetch failed");
        const potvrdeniTerminiJson = await(resPotvrdeniTermini.json());
        setReservations(potvrdeniTerminiJson.data)
        console.log("Potvrdeni termini:", potvrdeniTerminiJson.data);
        } catch (err) {
            console.error(err);
            navigate("/my-profile");
        } finally {
            setLoading(false);
        }
    };

    fetchData();
    }, []);


    const updateReservationStatus = async (id, status) => {
        try {
        const res = await fetch(`${url}/api/moderator/rezervacije/${id}`, {
            method: "PATCH",
            credentials: "include",
            headers: {
            "Content-Type": "application/json",
            },
            body: JSON.stringify({ status }),
        });

        if (!res.ok) throw new Error("Neuspješno ažuriranje statusa");

        setReservations(prev =>
            prev.map(r =>
            r.id === id ? { ...r, status } : r
            )
        );
        } catch (err) {
        console.error(err);
        }
    };

    const getUserFromId = async (id) => {
        console.log(reservations)
        console.log(id)
        try{
            const res = await fetch(`${url}/api/user/getUserById/${id}`, {
                method: "GET",
                credentials: "include",
            });

            if(!res.ok) throw new Error("Neuspjesno hvacanje usera");
            const json = await res.json();
            console.log(json.data)
            console.log(json.data.name)
            return json.data || json;
        }
        catch(err){
            console.error(err);
        }
    }

    const getAndSetDvorana = async (id) => {
        if (dvorana) return; 
        try {
            const res = await fetch(`${url}/api/public/dvorane/${id}`);
            const json = await res.json();
            setDvorana(json.data)
        } catch (err) {
            console.error("Failed to fetch dvorana", err);
            }
    };

    function getDurationInHours(start, end) {
        if (!start || !end) return 0;

        const [startH, startM] = start.split(":").map(Number);
        const [endH, endM] = end.split(":").map(Number);


        if (
            isNaN(startH) || isNaN(startM) ||
            isNaN(endH) || isNaN(endM)
        ) return 0;

        const startTotalMinutes = startH * 60 + startM;
        const endTotalMinutes = endH * 60 + endM;

        let durationMinutes = endTotalMinutes - startTotalMinutes;
        return durationMinutes / 60;
        }

    const getDate = (ts) => ts?.slice(0, 10).split("-").reverse().join(".");
    const getTime = (ts) => ts?.split("T")[1]?.slice(0, 5);

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

    const getTimeFromTimestamp = (timestamp) => {
        try{
            if (!timestamp) return "";

            const timePart = timestamp.includes("T")
                ? timestamp.split("T")[1]
                : timestamp.split(" ")[1]
                
            if (!timePart) return "";

            const [hours, minutes] = timePart.split(":");
            return `${hours}:${minutes}`;
        }
        catch(err){
            console.log(err)
        }
    };

    const acceptReservationRequest = async(id) => {
        try{
            const res = await fetch(`${url}/api/moderator/approveTeminRequest/${id}`, {
            method: "POST",
            credentials: "include",
        });
        if(!res.ok) throw new Error("nešto se strgalo");

        setReservationRequests((prevRequests) =>
            prevRequests.filter((request) => request.id !== id)
        );

        setReservations((prevReservations) => [
            ...prevReservations,
            reservationRequests.find((request) => request.id === id)
        ]);
        
        } catch(err) {
            console.error("error: ", err)
        }
    }

    const rejectReservationRequest = async (id) => {
        try{
            const res = await fetch(`${url}/api/moderator/rejectTeminRequest/${id}`, {
            method: "POST",
            credentials: "include",
        });
        if(!res.ok) throw new Error("nešto se strgalo");

        setReservationRequests((prevRequests) =>
            prevRequests.filter((request) => request.id !== id)
        );
        } catch(err) {
            console.error("error: ", err)
        }
    }

  if (loading) {
    return (
      <div className="flex min-h-screen items-center justify-center">
        <p>Učitavanje rezervacija...</p>
      </div>
    );
  }

  return (
    <div className="flex flex-col items-center min-h-screen bg-[#f5f5f5]">
      <div className="bg-[#3B5B80] w-full pb-10 rounded-b-[40%] flex flex-col items-center justify-center text-center mb-10%">
        {/* <div className="flex justify-evenly gap-12 mt-8 w-3/4">
          <Link to="/" className="w-[50vw] block">
            <Button variant="default" title="Početna stranica" />
          </Link>
        </div> */}

        <div className="flex justify-evenly md:gap-12 gap-6 mt-4 w-3/4">
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
                        src={user.pictureUrl ? user.pictureUrl : "../../public/user.svg"}
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
        
        <h2 className="text-4xl text-white mt-10 mb-10 font-semibold tracking-wide">{dvorana.nazivDvorana}</h2>
        <h3 className="text-4x1 text-white mt-10 mb-10 font-semibold tracking-wide">{dvorana?.adresa?.ulica} {dvorana?.adresa?.kucniBroj}, {dvorana?.adresa?.mjesto?.nazivMjesto}</h3>

    </div>
    {/* ZAHTJEVI */}
    <div className="flex flex-col md:w-3/4 w-[95%] bg-[#d9d9d9] rounded-[10px] items-center py-6 mt-12">
        <h2 className="text-xl font-semibold text-[#3B5B80] mb-6">
          Zahtjevi za rezervacije
        </h2>


        <div className="flex flex-col items-center gap-6 md:w-3/4 w-[95%]">

            {reservationRequests.length === 0 ? (
                <div className="text-gray-600 text-lg py-10">
                Trenutno nema zahtjeva za termine.
                </div>
            ) : (
                reservationRequests.map((reservation, index) => (
                <div className="flex md:flex-row flex-col w-full gap-6 items-stretch">

                    <ModeratorReservationsCard 
                        key={reservation.id}
                        imeVlasnika={reservation.imeVlasnika}
                        imeDogadanja={reservation.imeDogadanja}
                        opisDogadanja={reservation.opisDogadanja}
                        datumVrijemeStart={reservation.datumVrijemeStart}
                        datumVrijemeEnd={reservation.datumVrijemeEnd}
                        jeJavniEvent={reservation.jeJavniEvent}
                        cijenaPoSatu={dvorana.cijenaPoSatu} // ako izračunavaš
                        potvrdeno={true}
                        />


                    {/* GUMBI */}
                    <div className="flex flex-col gap-3 justify-center items-center">
                    <button
                        className="px-6 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 transition-colors font-semibold w-[200px] hover:cursor-pointer"
                        onClick={() => acceptReservationRequest(reservation.id)}
                    >
                        PRIHVATI
                    </button>

                    <button
                        className="px-6 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700 transition-colors font-semibold w-[200px] hover:cursor-pointer"
                        onClick={() => rejectReservationRequest(reservation.id)}
                    >
                        ODBIJ
                    </button>
                    </div>
                </div>
                ))
            )}
        </div>
    </div>

      {/* POTVRĐENE */}
      <div className="flex flex-col md:w-3/4 w-[95%] bg-[#d9d9d9] rounded-[10px] items-center py-6 mt-12 mb-12">
        <h2 className="text-xl font-semibold text-[#3B5B80] mb-6">
          Potvrđene rezervacije
        </h2>

        {reservations.length === 0 && <p>Nema potvrđenih rezervacija</p>}

        <div className="flex flex-col justify-center items-center gap-4 md:w-3/4 w-[95%]">
          {reservations.map((reservation, index) => (
            <div className="flex md:w-3/4 w-[95%] gap-4 items-center">

                <ModeratorReservationsCard 
                    key={reservation.id}
                    imeVlasnika={reservation.imeVlasnika}
                    imeDogadanja={reservation.imeDogadanja}
                    opisDogadanja={reservation.opisDogadanja}
                    datumVrijemeStart={reservation.datumVrijemeStart}
                    datumVrijemeEnd={reservation.datumVrijemeEnd}
                    jeJavniEvent={reservation.jeJavniEvent}
                    cijenaPoSatu={dvorana.cijenaPoSatu}
                    potvrdeno={true}
                />

            </div>
          ))}
        </div>
      </div>

      <Footer />
    </div>
  );
};

export default VenueReservations;
