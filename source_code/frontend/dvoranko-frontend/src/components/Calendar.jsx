import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import hrLocale from '@fullcalendar/core/locales/hr';
import {url} from "../main.jsx";
import "./Calendar.css";
import { useEffect, useState } from "react";

const Calendar = ({handleDateClick, venueId}) => {
    const [workingDays, setWorkingDays] = useState([]);
    const [days, setDays] = useState([]);

    const fetchDvoranu = async (venueId) => {
        try {
            const response = await fetch(`${url}/api/public/dvorane/${venueId}`, {
                method: "GET",
                credentials: "include"
            });

            const text = await response.text();
            const respData = JSON.parse(text);
            const daysTemp = respData.data.daysOpen.split(";").filter(x => x !== "");
            const workingDaysTemp = daysTemp.map(dan => dan.substring(0, 3));
            setDays(daysTemp);
            setWorkingDays(workingDaysTemp);
            return respData;
        } catch (error) {
            console.error("Error fetching data:", error);
            throw error;
        }
    }

    useEffect(() => {
        if (venueId) fetchDvoranu(venueId);
    }, [venueId]);

    return (
        <FullCalendar
            plugins={[dayGridPlugin, interactionPlugin]}
            initialView="dayGridMonth"
            height="100%"
            dateClick={(info) => {
                const dayNames = ["ned", "pon", "uto", "sri", "cet", "pet", "sub"];
                const day = dayNames[info.date.getDay()];
                if (!workingDays.includes(day)) return;
                const terminiDay = days.find(dan => dan.startsWith(day));

                handleDateClick(info, terminiDay);
            }}
            locale={hrLocale}
            dayCellContent={(arg) => arg.dayNumberText.replace(".", "")}
            dayCellClassNames={(arg) => {
                const dayNames = ["ned", "pon", "uto", "sri", "cet", "pet", "sub"];
                const day = dayNames[arg.date.getDay()];

                const today = new Date();
                today.setHours(0, 0, 0, 0);

                const cellDate = new Date(arg.date);
                cellDate.setHours(0, 0, 0, 0);

                if (cellDate < today) {
                    return "closed-day";
                }

                if (workingDays.includes(day)) {
                    return "open-day";
                } else {
                    return "closed-day";
                }
            }}
        />
    );
};

export default Calendar;