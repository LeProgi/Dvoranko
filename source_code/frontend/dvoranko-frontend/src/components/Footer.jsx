import React from "react";

const Footer = () => {
  return (
    <footer className="bg-[#536F8F] text-white w-full py-6 mt-auto">
      <div className="max-w-5xl mx-auto flex flex-row flex-nowrap justify-evenly items-center px-8">
        
        <div className="text-center md:text-left mb-4 md:mb-0">
          <h3 className="text-xl font-semibold">Dvoranko</h3>
          <p className="text-sm text-gray-200 mt-1">
            Aplikacija za rezervaciju i pregled dvorana.
          </p>
        </div>

        
        <div className="text-center text-sm text-gray-200">
          <p>Projekt na predmetu Programsko inžinjerstvo.</p>
        </div>

        <div className="text-center md:text-right text-sm text-gray-200">
          <p>
            Kontakt: leprogi.dvornako@gmail.com
            
          </p>
          <p>Verzija: 1.0.0</p>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
