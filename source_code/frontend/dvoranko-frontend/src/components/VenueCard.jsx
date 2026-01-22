import React from "react";

const VenueCard = ({
  name,
  adresa,
  imgUrl,
  cijenaPoSatu,
  potvrdeno,
}) => {
  return (
    <div
      className={`
        w-full relative
        ${!potvrdeno ? "pointer-events-none" : ""}
      `}
    >
      {/* BADGE ZA NEPOTVRĐENO */}
      {!potvrdeno && (
        <span className="absolute top-2 right-4 z-10 text-xs font-semibold bg-gray-300 text-gray-700 px-3 py-1 rounded-full">
          Čeka potvrdu
        </span>
      )}

      <div
        className={`
          flex items-center h-20 w-full rounded-lg shadow-sm transition-all
          ${potvrdeno
            ? "bg-[#e5e5e5] hover:bg-[#d0d0d0] cursor-pointer"
            : "bg-gray-200 opacity-60 grayscale"}
        `}
      >
        {/* SLIKA */}
        <div className="w-14 h-14 bg-white rounded-md ml-6 overflow-hidden">
          {imgUrl ? (
            <img
              src={imgUrl}
              alt="Slika dvorane"
              className={`w-full h-full object-cover object-center ${
                !potvrdeno ? "grayscale" : ""
              }`}
            />
          ) : (
            <div className="w-full h-full bg-gray-300 rounded-md"></div>
          )}
        </div>

        {/* TEKST */}
        <div className="ml-6 text-left flex-1">
          <h3 className="text-lg font-semibold text-gray-700">
            {name}
          </h3>
          <p className="text-sm text-gray-500">{adresa}</p>
        </div>

        {/* CIJENA */}
        {cijenaPoSatu && (
          <div className="mr-6 text-right">
            <p
              className={`text-lg font-bold ${
                potvrdeno ? "text-[#3B5B80]" : "text-gray-500"
              }`}
            >
              {cijenaPoSatu} €/h
            </p>
          </div>
        )}
      </div>
    </div>
  );
};

export default VenueCard;