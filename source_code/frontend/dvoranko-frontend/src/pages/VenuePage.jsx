
import Button from "../components/Button";
import { Link } from "react-router-dom";

const VenuePage = () => {
   
    return (
        <div className="flex justify-center items-center min-h-screen bg-[#5B7692]">
            <div className="flex bg-[#F5F5F5] w-3/4 max-w-5xl h-[80vh] rounded-[20px] shadow-lg overflow-hidden">
                
                
                <div className="bg-[#3B5B80] w-1/3 flex items-center justify-center rounded-l-[20px]">
                    <p className="text-white text-lg">Slika prostora</p>
                </div>

                
                <div className="flex flex-col justify-between w-2/3 p-10 relative">
                    <div>
                        <Link to="/">
                            <button className="absolute top-6 right-6 bg-[#3B5B80] text-[#f5f5f5] rounded-[15%] w-[25px] h-[25px] flex items-center justify-center">
                                X
                            </button>
                        </Link>
                        <h2 className="text-2xl font-semibold text-[#1C2D3A] mb-6">
                            Ime Prostorа
                        </h2>

                        <p className="text-[#1C2D3A] mb-2">
                            <span className="font-semibold">Lokacija:</span> Zagrebačka 10
                        </p>
                        <p className="text-[#1C2D3A] mb-2">
                            <span className="font-semibold">Kapacitet:</span> 120 osoba
                        </p>
                        <p className="text-[#1C2D3A] mb-2">
                            <span className="font-semibold">Opis prostora:</span> 
                            Prostrana konferencijska dvorana s modernom opremom i odličnom akustikom.
                        </p>
                    </div>

                    
                    <div className="self-end mt-8">
                        <Button 
                            variant="default"
                            title="Rezerviraj dvoranu"
                            className="bg-[#3B5B80] text-white px-6 py-2 rounded-lg hover:bg-[#2F4B6A]"
                        />
                    </div>
                </div>
            </div>
        </div>
    );
};

export default VenuePage;
