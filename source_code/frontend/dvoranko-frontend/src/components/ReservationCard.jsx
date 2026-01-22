const ReservationCard = ({
    nameDogadanje,
    opisDogadanje,
    nameDvorana,
    adresa,
    imgUrl,
    vrijemeOd,
    vrijemeDo
}) => {
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
        <button className="md:w-11/12 w-[95%]">
            <div className="flex items-center bg-[#e5e5e5] md:h-24 min-h-24 h-auto w-full rounded-lg shadow-sm hover:bg-[#d0d0d0] cursor-pointer transition-colors">

                {/* Slika */}
                <div className="w-16 h-16 bg-white rounded-md md:ml-6 ml-3 overflow-hidden">
                    {imgUrl ? (
                        <img
                            src={imgUrl}
                            alt="Slika dvorane"
                            className="w-full h-full object-cover object-center"
                        />
                    ) : (
                        <div className="w-full h-full bg-gray-300 rounded-md" />
                    )}
                </div>

                {/* Tekst */}
                <div className="md:ml-6 ml-3 text-left flex-1 overflow-hidden">
                    <h3 className="text-lg font-semibold text-gray-700 truncate">
                        {nameDogadanje}
                    </h3>

                    {opisDogadanje && (
                        <p className="text-sm text-gray-500 truncate">
                            {opisDogadanje}
                        </p>
                    )}

                    <p className="text-sm text-gray-600">
                        {nameDvorana} • {adresa}
                    </p>
                </div>

                {/* Datum + vrijeme */}
                <div className="md:mr-6 mr-1 text-right whitespace-nowrap">
                    <p className="text-sm text-gray-500">
                        {getDateFromTimestamp(vrijemeOd)}
                    </p>
                    <p className="text-md font-bold text-[#3B5B80]">
                        {getTimeFromTimestamp(vrijemeOd)} – {getTimeFromTimestamp(vrijemeDo)}
                        {/* {vrijemeOd} – {vrijemeDo} */}
                    </p>
                </div>

            </div>
        </button>
    );
};

export default ReservationCard;