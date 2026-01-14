import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import Calendar from "../components/Calendar";

const ReservationPage = () => {
   const { state } = useLocation();
   const venueId = state?.venueId;
   const [workingHours, setWorkingHours] = useState([]);
   const [selectedDate, setSelectedDate] = useState(null);
   const [availableTimes, setAvailableTimes] = useState(["00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"]);
   const [taken, setTaken] = useState(false);
   const [timesBetween, setTimesBetween] = useState([]);
   const [edgeTimes, setEdgeTimes] = useState([]);
   const [disabledTimes, setDisabledTimes] = useState([]);
   const [grayTimes, setGrayTimes] = useState([]);
   const [startTime, setStartTime] = useState(null);
   const [endTime, setEndTime] = useState(null);
   const [notStartTimes, setNotStartTimes] = useState([]);

   useEffect(() => {
      //fetch termini pomocu venueId string i napunit i wokringHours
   }, []);

   const handleDateClick = (info) => {
      const dateString = String(info.date.getDate()).padStart(2, "0") + "." + String(info.date.getMonth() + 1).padStart(2, "0") + "." + info.date.getFullYear() + ".";
      setSelectedDate(dateString);
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

   return (
      <div className="bg-[#5B7692] min-h-screen flex justify-center items-center">
         <div className="flex justify-between bg-white rounded-[20px] w-[80vw] h-[80vh] p-5">
            <div className="w-[50%] h-[100%]" >
               <Calendar handleDateClick={handleDateClick}/>
            </div>

            <div className="w-[49%] h-[100%] flex items-center justify-center">
               {!selectedDate ? (
                  <p className="text-xl">Izaberite datum</p>
                  ) : (
                  <div>
                     {taken ? (
                        <div>
                           <p className="text-xl">
                           Nema slobodnih termina za {selectedDate}
                           </p>
                        </div>
                     ) : (
                        <div>
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
                     )}
                  </div>
               )}
            </div>
         </div>
      </div>
   );
};

export default ReservationPage;