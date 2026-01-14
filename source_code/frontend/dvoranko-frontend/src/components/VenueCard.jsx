const VenueCard = ({name, adresa, imgUrl}) => {
    return (
        <button className="w-11/12">
            <div className="flex items-center bg-[#e5e5e5] h-20 w-full rounded-lg shadow-sm hover:bg-[#d0d0d0] cursor-pointer transition-colors">
                <div className="w-14 h-14 bg-white rounded-md ml-6">
                    <img src={imgUrl} alt="Slika dvorane" />
                </div>

                {/* Tekst ili info desno */}
                <div className="ml-6 text-left">
                    <h3 className="text-lg font-semibold text-gray-700">{name}</h3>
                    <p className="text-sm text-gray-500">{adresa}</p>
                </div>
            </div>
        </button>
    );
};

export default VenueCard;
