import React from "react";

const ModeratorReservationsCard = ({
  imeVlasnika,
  imeDogadanja,
  opisDogadanja,
  datumVrijemeStart,
  datumVrijemeEnd,
  jeJavniEvent,
  cijenaPoSatu,
}) => {

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


    return (
    <div className="flex-1 bg-white shadow-lg rounded-xl p-5 flex flex-col gap-3 hover:shadow-2xl transition-shadow">
        {/* GORNJI RED */}
        <div className="flex justify-between items-start">
            <div>
                <h2 className="text-xl font-bold text-gray-800">
                    {imeVlasnika}
                </h2>

                {jeJavniEvent ? (
                    <p className="text-sm">
                        <span className="text-gray-500">Naziv događanja: </span>{" "}
                        <span className="text-[#3B5B80] font-semibold">{imeDogadanja}</span>
                    </p>
                ) : (
                    <p className="text-sm">
                        <span className="text-[#3B5B80] font-semibold">Privatno događanje</span>
                    </p>
                )}

            </div>

            <span
            className={`text-sm font-semibold px-3 py-1 rounded-full
                ${jeJavniEvent
                ? "bg-green-100 text-green-700"
                : "bg-gray-200 text-gray-700"
                }`}
            >
            {jeJavniEvent ? "Javni" : "Privatni"}
            </span>
        </div>

        {/* OPIS */}
        <div className="flex flex-col gap-1 items-start w-full">
            <p className="text-sm left-3 text-gray-500">
                Opis:
            </p>
            {opisDogadanja && (
                <p className="text-gray-700 text-sm bg-gray-100 rounded-lg p-3 w-full">
                {opisDogadanja}
                </p>
            )}
        </div>

        {/* DATUM I VRIJEME */}
        <div className="flex flex-wrap justify-between text-sm text-gray-700 gap-2">
            <p>
            <span className="font-semibold">Datum:</span>{" "}
            {getDateFromTimestamp(datumVrijemeStart)}
            </p>

            <div className="flex gap-4">
            <p>
                <span className="font-semibold">Od:</span>{" "}
                {getTimeFromTimestamp(datumVrijemeStart)}
            </p>
            <p>
                <span className="font-semibold">Do:</span>{" "}
                {getTimeFromTimestamp(datumVrijemeEnd)}
            </p>
            </div>
        </div>

        {/* CIJENA */}
        <p className="text-right font-semibold text-gray-800">
            Cijena:{" "}
            {getDurationInHours(
            getTimeFromTimestamp(datumVrijemeStart),
            getTimeFromTimestamp(datumVrijemeEnd)
            ) * cijenaPoSatu}{" "}
            €
        </p>
    </div>
    );
}

export default ModeratorReservationsCard;