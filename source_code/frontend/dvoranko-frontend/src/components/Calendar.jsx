import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import hrLocale from '@fullcalendar/core/locales/hr';
import "./Calendar.css";
import { useEffect, useState } from "react";

const Calendar = ({handleDateClick}) => {

    const termini = "pon:05-22;uto:03-19;sri:07-12;pet:10-19;";
    const dani = termini.split(";").filter(x => x !== "");
    const daysOpen = dani.map(dan => dan.substring(0, 3));

    return (
        <FullCalendar
            plugins={[dayGridPlugin, interactionPlugin]}
            initialView="dayGridMonth"
            height="100%"
            dateClick={(info) => {
                const dayNames = ["ned", "pon", "uto", "sri", "čet", "pet", "sub"];
                const day = dayNames[info.date.getDay()];
                if (!daysOpen.includes(day)) return;
                const terminiDay = dani.find(dan => dan.startsWith(day));

                handleDateClick(info, terminiDay);
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
        />
    );
};

export default Calendar;