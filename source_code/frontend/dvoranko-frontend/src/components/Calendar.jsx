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
            //promijenit privremeno
            const privremeno = "pon:07-12;sri:05-23;cet:13-20;sub:14-18;";
            const daysTemp = privremeno.split(";").filter(x => x !== "");
            const workingDaysTemp = daysTemp.map(dan => dan.substring(0, 3));
            setDays(daysTemp);
            setWorkingDays(workingDaysTemp);

            console.log("Response from server:", respData.data);
            return respData;
        } catch (error) {
            console.error("Error fetching data:", error);
            throw error;
        }
    }

    useEffect(() => {
        fetchDvoranu(venueId);
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