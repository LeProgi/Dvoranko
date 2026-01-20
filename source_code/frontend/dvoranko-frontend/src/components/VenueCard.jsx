const VenueCard = ({name, adresa, imgUrl, cijenaPoSatu}) => {
    return (
        <button className="w-full">
            <div className="flex items-center bg-[#e5e5e5] md:h-20 h-auto min-h-20 w-full rounded-lg shadow-sm hover:bg-[#d0d0d0] cursor-pointer transition-colors">
                <div className="w-14 h-14 bg-white rounded-md md:ml-6 ml-3 overflow-hidden">
                    {imgUrl ? (
                        <img src={imgUrl} alt="Slika dvorane" className="w-full h-full object-cover object-center"/>
                    ) : (
                        <div className="w-full h-full bg-gray-300 rounded-md"></div>
                    )}
                </div>

                {/* Tekst ili info desno */}
                <div className="md:ml-6 ml-3 text-left flex-1">
                    <h3 className="text-lg font-semibold text-gray-700">{name}</h3>
                    <p className="text-sm text-gray-500">{adresa}</p>
                </div>
                
                {cijenaPoSatu && (
                    <div className="md:mr-6 mr-1 text-right">
                        <p className="text-lg font-bold text-[#3B5B80]">{cijenaPoSatu} €/h</p>
                    </div>
                )}
            </div>
        </button>
    );
};

export default VenueCard;
