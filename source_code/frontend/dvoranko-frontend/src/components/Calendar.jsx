import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import hrLocale from '@fullcalendar/core/locales/hr';
import "./Calendar.css";
import { useEffect, useState } from "react";

const Calendar = ({handleDateClick}) => {

    const termini = "pon:05-22;uto:07-12;pet:10-19;";
    const dani = termini.split(";").filter(x => x !== "");
    const daysOpen = dani.map(dan => dan.substring(0, 3));
    //const [events, setEvents] = useState([]);

    /*useEffect(() => {
        fetch("http://localhost:8080/api/calendar/events", {
        credentials: "include"
        })
        .then(r => {
            if (r.status === 401) {
            window.location.href = "http://localhost:8080/oauth2/authorization/google";
            }
            return r.json();
        })
        .then(data => {
            setEvents(data.map(e => ({
            id: e.id,
            title: e.summary,
            start: e.start.dateTime || e.start.date,
            end: e.end.dateTime || e.end.date
            })));
        });
    }, []);*/

    return (
        <FullCalendar
        plugins={[dayGridPlugin, interactionPlugin]}
        initialView="dayGridMonth"
        height="100%"
        dateClick={(info) => {
            const dayNames = ["ned", "pon", "uto", "sri", "čet", "pet", "sub"];
            const day = dayNames[info.date.getDay()];
            if (!daysOpen.includes(day)) return;

            handleDateClick(info);
        }}
        locale={hrLocale}
        dayCellContent={(arg) => arg.dayNumberText.replace(".", "")}
        dayCellClassNames={(arg) => {
            const dayNames = ["ned", "pon", "uto", "sri", "čet", "pet", "sub"];
            const day = dayNames[arg.date.getDay()];

            if (daysOpen.includes(day)) {
            return "open-day";
            } else {
            return "closed-day";
            }
        }}
        //events={events}
        />
    );
};

export default Calendar;