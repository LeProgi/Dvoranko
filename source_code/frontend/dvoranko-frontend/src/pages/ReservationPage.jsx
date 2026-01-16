import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import { Link } from "react-router-dom";
import { url } from "../main.jsx";
import Calendar from "../components/Calendar";

const ReservationPage = () => {
   const { state } = useLocation();
   const venueId = state?.venueId;
   const [selectedDate, setSelectedDate] = useState(null);
   const [allTimes, setallTimes] = useState(["00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00"]);
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
   const [privatno, setPrivatno] = useState(null);
   const [brojLjudi, setBrojLjudi] = useState("");
   const [formError, setFormError] = useState("");

   const handleDateClick = (info, terminiDay) => {
      const dateString = String(info.date.getDate()).padStart(2, "0") + "." + String(info.date.getMonth() + 1).padStart(2, "0") + "." + info.date.getFullYear() + ".";
      setSelectedDate(dateString);
      const start = terminiDay.substring(terminiDay.indexOf(":") + 1, terminiDay.indexOf("-"));
      const end = terminiDay.substring(terminiDay.indexOf("-") + 1, terminiDay.length);
      const startIndex = allTimes.findIndex(time => time.startsWith(start));
      const endIndex = allTimes.findIndex(time => time.startsWith(end));
      setAvailableTimes(allTimes.slice(startIndex, endIndex + 1));
      fetchTakenTimes(dateString);
   };

   const fetchTakenTimes = (date) => {
      //fetch zauzete termine i uredi availableTimes i taken
      setStartTime(null);
      setEndTime(null);
      setTimesBetween([]);
      setEdgeTimes([]);
      const notStart = [];
      const disabled = [];
      for (let i = 0; i < availableTimes.length - 1; ++i) {
         if (parseInt(availableTimes[i + 1].substring(0, 2)) !== parseInt(availableTimes[i].substring(0, 2)) + 1) {
            notStart.push(availableTimes[i]);
            disabled.push(availableTimes[i]);
         }
      }
      notStart.push(availableTimes[availableTimes.length - 1]);
      disabled.push(availableTimes[availableTimes.length - 1]);
      setNotStartTimes(notStart);
      setDisabledTimes(disabled);
      setGrayTimes(disabled);
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
   }

   const handleClickPrivatno = () => {
      setJavno(false);
      setPrivatno(true);
   }

   const handleSubmit = (e) => {
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

      if (!brojLjudi.trim()) {
         setFormError("Molimo unesite broj ljudi za rezervaciju.");
         return;
      } else if (!/^([1-9]\d*)$/.test(brojLjudi.trim())) {
         setFormError("Molimo da broj ljudi bude pozitivan cijeli broj.");
         return;
      }
      //dodat provjeru kapaciteta

      setFormError("");
   }

   return (
      <div className="bg-[#5B7692] min-h-screen flex justify-center md:items-center h-auto">
         <div className="flex flex-col md:flex-row justify-between bg-white rounded-[20px] w-[90vw] md:h-[80vh] p-5 h-auto mt-5 md:mt-0 mb-5 md:mb-0">
            <div className="w-[100%] md:w-[50%] h-[550px] md:h-[100%]" >
               <Calendar handleDateClick={handleDateClick}/>
            </div>

            <div className="w-[100%] md:w-[49%] h-auto md:h-[100%] flex items-center justify-center">
               {!selectedDate ? (
                  <div className="w-[100%]">
                     <p className="text-xl m-4">
                        Izaberite datum
                     </p>

                     <div>
                        <Link key={venueId} to={`/venue/${venueId}`}>
                        <button
                           type="button"
                           className="h-[40px] w-[25%] text-white font-bold rounded-[10px] cursor-pointer bg-[#3B5B80] hover:bg-[#2F4B6A]"
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
                                 className="h-[40px] w-[25%] text-white font-bold rounded-[10px] cursor-pointer bg-[#3B5B80] hover:bg-[#2F4B6A]"
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
                              
                              <div className="mt-2 flex flex-col justify-between h-[250px]">
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

                                    <div className="flex flex-row items-center justify-center">
                                       <label className="mr-2">Broj ljudi:</label>
                                       <input
                                          type="text"
                                          value={brojLjudi}
                                          onChange={(e) => setBrojLjudi(e.target.value)}
                                          required
                                          placeholder="Broj ljudi"
                                          className="w-[40%] h-[30px] border-2 border-black rounded-[4px] bg-white"
                                       />
                                    </div>
                                 </div>

                                 <div className="flex flex-row items-center justify-end h-[70px] gap-[5%]">
                                    {formError && (
                                       <div className="text-white bg-[#b91c1c] p-[10px_10px] rounded-[40px] w-[70%] text-center font-medium top-[20px] right-[20px]">
                                          {formError}
                                       </div>
                                    )}
                                    <button type="submit" className="h-[40px] w-[25%] text-white font-bold rounded-[10px] cursor-pointer bg-[#3B5B80] hover:bg-[#2F4B6A]">
                                       Submit
                                    </button>
                                 </div>

                                 <div>
                                    <Link key={venueId} to={`/venue/${venueId}`}>
                                    <button
                                       type="button"
                                       className="h-[40px] w-[25%] text-white font-bold rounded-[10px] cursor-pointer bg-[#3B5B80] hover:bg-[#2F4B6A]"
                                       >Odustani</button>
                                    </Link>
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
   );
};

export default ReservationPage;