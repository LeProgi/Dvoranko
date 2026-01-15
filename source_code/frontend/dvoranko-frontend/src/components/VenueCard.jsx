const VenueCard = ({name, adresa, imgUrl, cijenaPoSatu}) => {
    return (
        <button className="w-11/12">
            <div className="flex items-center bg-[#e5e5e5] h-20 w-full rounded-lg shadow-sm hover:bg-[#d0d0d0] cursor-pointer transition-colors">
                <div className="w-14 h-14 bg-white rounded-md ml-6">
                    {imgUrl ? (
                        <img src={imgUrl} alt="Slika dvorane" />
                    ) : (
                        <div className="w-full h-full bg-gray-300 rounded-md"></div>
                    )}
                </div>

                {/* Tekst ili info desno */}
                <div className="ml-6 text-left flex-1">
                    <h3 className="text-lg font-semibold text-gray-700">{name}</h3>
                    <p className="text-sm text-gray-500">{adresa}</p>
                </div>
                
                {cijenaPoSatu && (
                    <div className="mr-6 text-right">
                        <p className="text-lg font-bold text-[#3B5B80]">{cijenaPoSatu} €/h</p>
                    </div>
                )}
            </div>
        </button>
    );
};

export default VenueCard;
