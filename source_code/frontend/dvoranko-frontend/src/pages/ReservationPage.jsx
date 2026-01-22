import { useEffect, useState } from "react";
import { useLocation, Link, useNavigate, Navigate } from "react-router-dom";
import { url } from "../main.jsx";
import Calendar from "../components/Calendar";

const ReservationPage = () => {
   const navigate = useNavigate();
   const { state } = useLocation();
   const venueId = state?.venueId;
   const [termini, setTermini] = useState([]);
   const [selectedDate, setSelectedDate] = useState(null);
   const [dateStr, setDateStr] = useState(null);
   const allTimes = ["00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00"];
   const [availableTimes, setAvailableTimes] = useState([]);
   const [taken, setTaken] = useState(false);
   const [timesBetween, setTimesBetween] = useState([]);
   const [edgeTimes, setEdgeTimes] = useState([]);
   const [disabledTimes, setDisabledTimes] = useState([]);
   const [grayTimes, setGrayTimes] = useState([]);
   const [startTime, setStartTime] = useState(null);
   const [endTime, setEndTime] = useState(null);
   const [notStartTimes, setNotStartTimes] = useState([]);
   const [javno, setJavno] = useState(null);
   const [privatno, setPrivatno] = useState(true);
   const [kapacitet, setKapacitet] = useState(null);
   const [brojLjudi, setBrojLjudi] = useState("");
   const [formError, setFormError] = useState("");
   const [imeDogadanja, setImeDogadanja] = useState("Privatno događanje");
   const [opisDogadanja, setOpisDogadanja] = useState("");
   const [ownerIdLocal, setOwnerIdLocal] = useState("");

   const [isSubmitting, setIsSubmitting] = useState(false);

   useEffect(() => {
      fetch(`${url}/api/auth/user`, {
         credentials: "include",
      })
         .then((res) => {
            if (!res.ok) throw new Error("Not logged in");
            return res.json();
         })
         .then((data) => {
            setOwnerIdLocal(data.id)
            console.log("User data fetched:", data);
            if (!venueId) throw new Error("Nema podatka o izabranoj dvorani");
            fetchDvoranu(venueId);
            fetchTermineDvorane(venueId);
            fetchZahtjeveZaTermine();
         })
         .catch((error) => {
            setOwnerIdLocal(null);
            console.error(error);
            if (error.message === "Not logged in") {
               window.location.href = `${url}/oauth2/authorization/google`;
            } else {
               navigate("/", { replace: true });
            }
         });
   }, [navigate]);

   const fetchDvoranu = async (venueId) => {
      try {
         const response = await fetch(`${url}/api/public/dvorane/${venueId}`, {
            method: "GET",
            credentials: "include"
         });

         const text = await response.text();
         const respData = JSON.parse(text);
         setKapacitet(respData.data.kapacitet);

         console.log("Dohvaćena dvorana:", respData.data);
         return respData;
      } catch (error) {
         console.error("Error fetching data:", error);
         throw error;
      }
   };

   const fetchTermineDvorane = async (venueId) => {
      try {
         const response = await fetch(`${url}/api/moderator/getPotvrdeniTerminiForDvorana/${venueId}`, {
            method: "GET",
            credentials: "include"
         });

         const text = await response.text();
         const respData = JSON.parse(text);

         if (respData.data) {
            setTermini(prev => [...prev, ...respData.data.map(termin => ({start: termin.datumVrijemeStart, end: termin.datumVrijemeEnd}))]);
         }

         console.log("Dohvaćeni termini:", respData.data);
         return respData;
      } catch (error) {
         console.error("Error fetching data:", error);
         throw error;
      }
   }

   const fetchZahtjeveZaTermine = async () => {
      try {
         const response = await fetch(`${url}/api/public/termini/zahtjevi/${venueId}`, {
            method: "GET",
            credentials: "include"
         });
         const text = await response.text();
         const respData = JSON.parse(text);
         
         if (respData) {
            setTermini(prev => [...prev, ...respData.map(termin => ({start: termin.datumVrijemeStart, end: termin.datumVrijemeEnd}))]);
         }

         console.log("Dohvaćeni zahtjevi:", respData);
      } catch (error) {
         console.error("Error fetching data:", error);
         throw error;
      }
   }

   const handleDateClick = (info, terminiDay) => {
      setStartTime(null);
      setEndTime(null);
      setTimesBetween([]);
      setEdgeTimes([]);
      setFormError("");
      setTaken(false);
      const izabranDate = String(info.date.getDate()).padStart(2, "0") + "." + String(info.date.getMonth() + 1).padStart(2, "0") + "." + info.date.getFullYear() + ".";
      setSelectedDate(izabranDate);
      setDateStr(info.dateStr);
      namjestiAvailableTimes(info.dateStr, terminiDay);
   };

   const namjestiAvailableTimes = (dateString, terminiDay) => {
      const filtriraniTermini = termini
         .filter(termin => termin.start.startsWith(dateString))
         .map(termin => {
            const endFixed = termin.end.replace("T00:00:00", "T24:00:00");

            return {
               ...termin,
               end: endFixed
            };
         });
      const start = terminiDay.substring(terminiDay.indexOf(":") + 1, terminiDay.indexOf("-"));
      const end = terminiDay.substring(terminiDay.indexOf("-") + 1, terminiDay.length);
      const startIndex = allTimes.findIndex(time => time.startsWith(start));
      const endIndex = allTimes.findIndex(time => time.startsWith(end));
      const availableTimesPomocna = allTimes.slice(startIndex, endIndex + 1);
      const notStart = [];
      const disabled = [];
      console.log(filtriraniTermini);
      filtriraniTermini.forEach(termin => {
         const start = termin.start.substring(11, 16);
         const end = termin.end.substring(11, 16);

         const startIndex = availableTimesPomocna.findIndex(time => time.startsWith(start));
         const endIndex = availableTimesPomocna.findIndex(time => time.startsWith(end));

         if (startIndex !== -1 && endIndex !== -1) {
            if (startIndex + 1 === endIndex) {
               if (startIndex === 0) {
                  availableTimesPomocna.splice(0, 1);
               } else {
                  notStart.push(availableTimesPomocna[startIndex]);
                  disabled.push(availableTimesPomocna[startIndex]);
               }
            } else {
               availableTimesPomocna.splice(startIndex + 1, endIndex - startIndex - 1);
            }
         }
      });
      let elementi = availableTimesPomocna.length;
      for (let i = 0; i < elementi; ++i) {
         if (elementi > 1) {
            if (i === 0) {
               if (parseInt(availableTimesPomocna[i + 1].substring(0, 2)) !== parseInt(availableTimesPomocna[i].substring(0, 2)) + 1) {
                  availableTimesPomocna.splice(0, 1);
                  --elementi;
                  --i;
               }
            } else if (i == elementi - 1) {
               if (parseInt(availableTimesPomocna[i].substring(0, 2)) !== parseInt(availableTimesPomocna[i - 1].substring(0, 2)) + 1) {
                  availableTimesPomocna.splice(i, 1);
               }
            } else if (parseInt(availableTimesPomocna[i + 1].substring(0, 2)) !== parseInt(availableTimesPomocna[i].substring(0, 2)) + 1 && parseInt(availableTimesPomocna[i].substring(0, 2)) !== parseInt(availableTimesPomocna[i - 1].substring(0, 2)) + 1) {
               availableTimesPomocna.splice(i, 1);
               --elementi;
               --i;
            }
         }
      }
      if (elementi < 2) {
         setTaken(true);
      } else {
         setAvailableTimes(availableTimesPomocna);
         for (let i = 0; i < availableTimesPomocna.length - 1; ++i) {
            if (parseInt(availableTimesPomocna[i + 1].substring(0, 2)) !== parseInt(availableTimesPomocna[i].substring(0, 2)) + 1) {
               notStart.push(availableTimesPomocna[i]);
               disabled.push(availableTimesPomocna[i]);
            }
         }
         notStart.push(availableTimesPomocna[availableTimesPomocna.length - 1]);
         disabled.push(availableTimesPomocna[availableTimesPomocna.length - 1]);
         setNotStartTimes(notStart);
         setDisabledTimes(disabled);
         setGrayTimes(disabled);
      }
   };

   const startTimes = (clickedTime) => {
      setStartTime(clickedTime);
      setEdgeTimes([clickedTime]);
      const disabled = availableTimes.slice(0, availableTimes.indexOf(clickedTime) + 1);
      for (let i = availableTimes.indexOf(clickedTime) + 1; i < availableTimes.length; ++i) {
         if (notStartTimes.includes(availableTimes[i])) {
            disabled.push(...availableTimes.slice(i + 1, availableTimes.length));
            break;
         }
      }
      const notStartTimesFilter = notStartTimes.filter(t => availableTimes.indexOf(t) < availableTimes.indexOf(clickedTime) || disabled.includes(t));
      setDisabledTimes(notStartTimesFilter);
      setGrayTimes(disabled);
   };

   const handleClick = (clickedTime) => {
      if (startTime === null) {
         startTimes(clickedTime);
      } else if (endTime === null) {
         if (availableTimes.indexOf(clickedTime) <= availableTimes.indexOf(startTime) || grayTimes.includes(clickedTime)) {
            startTimes(clickedTime);
         } else {
            setEndTime(clickedTime);
            setEdgeTimes(prev => [...prev, clickedTime]);
            const disabled = availableTimes.slice(availableTimes.indexOf(clickedTime) + 1);
            setDisabledTimes(notStartTimes);
            setGrayTimes(prev => [...prev, ...disabled]);
            const between = availableTimes.slice(availableTimes.indexOf(startTime) + 1, availableTimes.indexOf(clickedTime));
            setTimesBetween(between);
         }
      } else {
         setEndTime(null);
         setTimesBetween([]);
         startTimes(clickedTime);
      }
   };

   const handleClickJavno = () => {
      setJavno(true);
      setPrivatno(false);
      setImeDogadanja("");
   }

   const handleClickPrivatno = () => {
      setJavno(false);
      setPrivatno(true);
      setImeDogadanja("Privatno događanje");
   }

   const handleSubmit = async (e) => {
      e.preventDefault();

      if (!startTime) {
         setFormError("Molimo izaberite vrijeme početka termina.");
         return;
      }

      if (!endTime) {
         setFormError("Molimo izaberite vrijeme kraja termina.");
         return;
      }

      if (!javno && !privatno) {
         setFormError("Molimo izaberite je li događanje javno ili privatno.");
         return;
      }

      if (!imeDogadanja.trim() && !privatno) {
         setFormError("Molimo unesite ime događanja.");
         return;
      }

      if (!opisDogadanja.trim()) {
         setFormError("Molimo unesite opis događanja.");
         return;
      }

      if (!brojLjudi.trim()) {
         setFormError("Molimo unesite broj ljudi za rezervaciju.");
         return;
      } else if (!/^([1-9]\d*)$/.test(brojLjudi.trim())) {
         setFormError("Molimo da broj ljudi bude pozitivan cijeli broj.");
         return;
      } else if (parseInt(brojLjudi.trim()) > kapacitet) {
         setFormError(`Broj ljudi ne smije prelaziti kapacitet dvorane (${kapacitet}).`);
         return;
      }

      setFormError("");

      setIsSubmitting(true);

      try {
         let datumVrijemeEnd = "";
         if (endTime === "24:00") {
            let date = new Date(dateStr);
            date.setDate(date.getDate() + 1);
            console.log(date);
            let dateDatum = String(date.getFullYear()) + "-" + String(date.getMonth() + 1).padStart(2, "0") + "-" + String(date.getDate());
            datumVrijemeEnd = dateDatum + "T" + "00:00" + ":00";
         } else {
            datumVrijemeEnd = dateStr + "T" + endTime + ":00";
         }
         const payload = {
            datumVrijemeStart: dateStr + "T" + startTime + ":00",
            datumVrijemeEnd: datumVrijemeEnd,
            idDvorana: parseInt(venueId),
            jeJavniEvent: javno ? 1 : 0,
            idKorisnik: ownerIdLocal,
            imeDogadanja: privatno ? "Privatno događanje" : imeDogadanja,
            opisDogadanja: opisDogadanja
         }
         console.log("Payload to be sent:", payload);

         await postZahtjevTermin(payload);
         setFormError("Zahtjev uspješno poslan");
         navigate("/my-profile");
      } catch (err) {
         console.error(err);
         setFormError("Greška pri slanju zahtjeva. Pokušajte ponovno.");
      } finally {
         setIsSubmitting(false);
      }
   }

   const postZahtjevTermin = async (data) => {
      try {
            const response = await fetch(`${url}/api/user/request/createZahtjevTermin`, {
               method: "POST",
               credentials: "include",
               headers: { "Content-Type": "application/json" },
               body: JSON.stringify(data),
            });

            const text = await response.text();
            let respData;
            try {
               respData = text ? JSON.parse(text) : null;
            } catch {
               respData = text;
            }

            if (!response.ok) {
               console.error("Server returned error:", response.status, respData);
               throw new Error(respData?.message || `Server error ${response.status}`);
            }

            console.log("Response from server:", respData);
            return respData;
        } catch (error) {
            console.error("Error submitting data:", error);
            throw error;
        }
   }

   return (
      <div className="relative">
      <div className={`w-full max-w-full relative transition-all duration-200 ${isSubmitting ? 'blur-sm pointer-events-none select-none opacity-50' : ''}`}>
      <div className="bg-[#5B7692] min-h-screen flex justify-center lg:items-center h-auto">
         <div className="flex flex-col lg:flex-row justify-between bg-white rounded-[20px] w-[90vw] lg:h-[90vh] p-5 h-auto mt-5 lg:mt-0 mb-5 lg:mb-0">
            <div className="w-[100%] lg:w-[50%] h-[550px] lg:h-[100%]" >
               <Calendar handleDateClick={handleDateClick} venueId={venueId}/>
            </div>

            <div className={`w-[100%] lg:w-[49%] h-auto lg:h-[100%] flex justify-center ${selectedDate ? "items-start" : "items-center"}`}>
               {!selectedDate ? (
                  <div className="w-[100%]">
                     <p className="text-xl m-4">
                        Izaberite datum
                     </p>

                     <div>
                        <Link key={venueId} to={`/venue/${venueId}`}>
                        <button
                           type="button"
                           className="h-[40px] md:w-[25%] w-[40%] text-white font-bold rounded-[10px] cursor-pointer bg-[#3B5B80] hover:bg-[#2F4B6A]"
                           >Odustani</button>
                        </Link>
                     </div>
                  </div>
                  ) : (
                  <div>
                     {taken ? (
                        <div>
                           <p className="text-xl">
                           Nema slobodnih termina za {selectedDate}
                           </p>

                           <div>
                              <Link key={venueId} to={`/venue/${venueId}`}>
                              <button
                                 type="button"
                                 className="h-[40px] md:w-[25%] w-[40%] text-white font-bold rounded-[10px] cursor-pointer bg-[#3B5B80] hover:bg-[#2F4B6A]"
                                 >Odustani</button>
                              </Link>
                           </div>
                        </div>
                     ) : (
                        <div>
                           <form
                              onSubmit={handleSubmit}
                              noValidate
                              onKeyDown={(e) => {
                                 if (e.key === "Enter" && e.target.tagName === "INPUT") e.preventDefault();
                              }}
                           >
                              <div className="mb-2">
                                 <p className="text-xl font-semibold mb-2">
                                    Termini za {selectedDate}
                                 </p>
                                 <hr></hr>
                                 <p className="opacity-40">
                                    Molimo izaberite vrijeme početka pa vrijeme kraja termina
                                 </p>
                                 {availableTimes.map((time, idx) => {
                                    const isEdge = edgeTimes.includes(time);
                                    const isBetween = timesBetween.includes(time);
                                    const isDisabled = disabledTimes.includes(time);
                                    const isGray = grayTimes.includes(time);
                                    return (
                                    <button
                                       id={`time-select-${time}`}
                                       type="button"
                                       key={idx}
                                       onClick={() => !isDisabled && handleClick(time)}
                                       className={`px-4 py-2 transition
                                          ${isEdge && isDisabled
                                          ? "bg-blue-500 text-white"
                                          : isBetween
                                          ? "bg-blue-300 text-white cursor-pointer hover:bg-blue-400"
                                          : isEdge
                                          ? "bg-blue-500 text-white cursor-pointer hover:bg-blue-600"
                                          : isDisabled
                                          ? "bg-[#e9e9e9]"
                                          : isGray
                                          ? "bg-[#e9e9e9] cursor-pointer hover:bg-gray-300"
                                          : "bg-white cursor-pointer hover:bg-gray-100"}`}
                                       >
                                       {time}
                                    </button>
                                    );
                                 })}
                              </div>

                              <hr></hr>
                              
                              <div className="mt-2 flex flex-col justify-between h-auto lg:h-[300px]">
                                 <div className="flex flex-col items-center mb-2 gap-[8px]">
                                    <div className="flex flex-row items-center justify-center">
                                       <p className="mr-2 ml-2">
                                          Događanje je:
                                       </p>
                                       <div className="relative">
                                          <div className={`absolute top-0 left-0 z-0 javno-btn ${javno ? "bg-[#3B5B80] scale-[1.05]" : "bg-black scale-[0.95]"} w-[80px] h-[40px] rounded-l-xl -mr-1.5`}></div>
                                          <button
                                             type="button"
                                             className={`javno-btn ${javno ? "bg-[#3B5B80] text-white" : "bg-[#FFFFFF] scale-[0.9] hover:bg-[#f0f0f0]"} pr-1 w-[80px] h-[40px] text-center font-semibold cursor-pointer rounded-l-xl -mr-1.5`}
                                             onClick={() => handleClickJavno()}
                                          >Javno</button>
                                       </div>
                                       <div className="relative">
                                          <div className={`absolute top-0 left-0 z-0 privatno-btn ${privatno ? "bg-[#3B5B80] scale-[1.05]" : "bg-black scale-[0.95]"} w-[80px] h-[40px] rounded-r-xl -ml-1.5`}></div>
                                          <button
                                             type="button"
                                             className={`privatno-btn ${privatno ? "bg-[#3B5B80] text-white" : "bg-[#FFFFFF] scale-[0.9] hover:bg-[#f0f0f0]"} pl-2 w-[80px] h-[40px] text-center font-semibold cursor-pointer rounded-r-xl -ml-1.5`}
                                             onClick={() => handleClickPrivatno()}
                                          >Privatno</button>
                                       </div>
                                    </div>

                                    <div className="w-full flex flex-col items-center gap-1">
                                       <label>Ime događanja:</label>
                                       <input
                                          type="text"
                                          value={imeDogadanja}
                                          onChange={(e) => setImeDogadanja(e.target.value)}
                                          disabled={privatno}
                                          placeholder={privatno ? "Privatno događanje" : "Ime događanja"}
                                          className={`w-[60%] h-[30px] border-2 rounded-[4px] 
                                             ${privatno ? "bg-gray-200 text-gray-500 border-gray-400" : "bg-white border-black"}`}
                                       />
                                    </div>

                                    <div className="w-full flex flex-col items-center gap-1">
                                       <label>Opis događanja:</label>
                                       <textarea
                                          id="input-opis-dogadanja"
                                          value={opisDogadanja}
                                          onChange={(e) => setOpisDogadanja(e.target.value)}
                                          placeholder="Opis događanja"
                                          className="w-[100%] h-[100px] border-2 border-black rounded-[4px] bg-white resize-none"
                                       />
                                    </div>

                                    <div className="flex flex-row items-center justify-center">
                                       <label className="mr-2">Broj ljudi:</label>
                                       <input
                                          id="input-broj-ljudi"
                                          type="text"
                                          value={brojLjudi}
                                          onChange={(e) => setBrojLjudi(e.target.value)}
                                          required
                                          placeholder="Broj ljudi"
                                          className="w-[40%] h-[30px] border-2 border-black rounded-[4px] bg-white"
                                       />
                                       <label className="ml-2">/ {kapacitet}</label>
                                    </div>
                                 </div>

                                 <div className="flex flex-row items-center justify-center h-[70px] gap-[5%]">

                                    <Link key={venueId} to={`/venue/${venueId}`}>
                                       <button
                                          type="button"
                                          className="h-[40px] w-[120px] text-white font-bold rounded-[10px] cursor-pointer bg-gray-500 hover:bg-gray-600"
                                       >
                                          Odustani
                                       </button>
                                    </Link>

                                    <button id="submit-zahtjev-termin-btn" type="submit" className="h-[40px] w-[120px] text-white font-bold rounded-[10px] cursor-pointer bg-[#3B5B80] hover:bg-[#2F4B6A]">
                                       Submit
                                    </button>
                                 </div>
                                 <div className="flex justify-center mt-3">
                                    {formError && (
                                       <div className="text-white bg-[#b91c1c] p-[10px_10px] rounded-[40px] w-auto max-w-[90%] text-center font-medium top-[20px] right-[20px]">
                                          {formError}
                                       </div>
                                    )}
                                 </div>
                              </div>
                           </form>
                        </div>
                     )}
                  </div>
               )}
            </div>
         </div>
      </div>

      </div>
      {isSubmitting && (
                <div className="absolute inset-0 bg-black/40 flex items-center justify-center z-50">
                    <div className="flex flex-col items-center gap-4">
                    <div className="w-16 h-16 border-4 border-[#536F8F] border-t-transparent rounded-full animate-spin"></div>
                    <p className="text-white text-lg font-semibold animate-pulse">
                        Slanje zahtjeva...
                    </p>
                    </div>
                </div>
                )
            }
      </div>
   );
};

export default ReservationPage;