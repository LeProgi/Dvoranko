import Button from "../components/Button";
import VenueCard from "../components/VenueCard";
import { Link } from "react-router-dom";
import Footer from "../components/Footer";
const EventBoard = () => {
    return (
        <div className="flex flex-col min-h-screen w-full bg-gray-100 items-center">
        
              <div className="bg-[#3B5B80] w-full pb-10 rounded-b-[40%] flex flex-col items-center text-center">
                
                <div className="flex justify-evenly gap-12 mt-4 w-3/4">
                    <Link to="/" className="w-[50vw] block">
                        <Button variant="default" title="Početna stranica" />
                    </Link>
                    <Link to="/maps" className="w-[50vw] block">
                        <Button variant="default" title="Karta" />
                    </Link>
                    <Button variant="default" title="O nama" />
                    <Button variant="profile" title="profile"/>
                </div>
        
                <h2 className="text-4xl text-white mt-10 mb-10 font-semibold tracking-wide">Oglasna ploča</h2>
            </div>
            <div className="flex flex-col w-3/4 bg-[#d9d9d9] rounded-[10px] items-center py-6 mt-12 mb-12">
                <h2 className="text-xl font-semibold mb-6">Popis javnih događanja</h2>
                <div className="flex flex-col items-center gap-4 w-full">
                    <Link to="/venue1" className="w-11/12 block">
                        <VenueCard></VenueCard>
                    </Link>
                    <Link to="/venue1" className="w-11/12 block">
                        <VenueCard></VenueCard>
                    </Link>
                    <Link to="/venue1" className="w-11/12 block">
                        <VenueCard></VenueCard>
                    </Link>
                    
                </div>
            </div>
            <Footer/>
        </div>
    );
};

export default EventBoard;