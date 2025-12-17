import Form from "../components/Form.jsx";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { url } from "../main.jsx";
import Button from "../components/Button.jsx";
import Footer from "../components/Footer";
import { useLocation } from "react-router-dom";


const ProfilePage = () => {
    //const location = useLocation();
    //const { user } = location.state;
        const user = {
        id: 3,
        name: "Borna Navratil",
        email: "borna.navratil@gmail.com",
        pictureUrl: "https://lh3.googleusercontent.com/a/ACg8ocKt_F4BE5H3rvtT9UTEjIqdJfAJ9eZueKPmt2QQZm-UqOeC3pCj=s96-c",
        role: "USER" 
    };
    const [seeForm, setSeeForm] = useState(false);
    const [seeCheck, setSeeCheck] = useState(false);
     


    


  return (
    <div className="flex flex-col min-h-screen items-center bg-gray-100">
      
        <div className="relative bg-[#3B5B80] w-full pb-5 px-[5vw] flex flex-row justify-between items-center text-white">
       
            <div className="flex flex-row items-center gap-[5vw] mt-16 bg-[#3B5B80] px-6 py-4 rounded-2xl ">
            <img
            src={user.pictureUrl}
            alt={user.name}
            className="w-24 h-24 rounded-full border-4 border-white object-cover"
            />
                <div className="text-left">
                    <h2 className="text-2xl font-semibold">
                    {user.name} 
                    </h2>
                    <p className="text-lg opacity-90">{user.email}</p>
                </div>
            </div>
            <div className="flex flex-col justify-center gap-[1vw]">
            
                <Link to="/" className="w-[20vw] block">
                    <Button variant="default" title="Početna stranica" />
                </Link>
                {user.role === "USER" && (
                    <button
                    onClick={() => setSeeCheck(true)}
                    className="w-[20vw]"
                    >
                        <Button variant="default" title="Postani iznajmljivač" />
                    </button>
                )}
            </div>
        </div>

        {seeCheck && (
            <div className="fixed inset-0 z-50 flex items-center justify-center">

        
                <div
                    className="absolute inset-0 backdrop-blur-sm"
                    onClick={() => setSeeCheck(false)}
                />

        
                <div className="relative bg-white rounded-lg shadow-2xl px-16 py-14 w-[450px] z-20">
                    <h2 className="text-lg font-semibold text-gray-800 mb-4 text-center">
                        Poslati zahtjev za postati iznajmljivač?
                    </h2>

                    <div className="flex gap-3">
                        <button
                            className="flex-1 bg-green-600 text-white py-2 rounded-md hover:bg-green-700 transition"
                            onClick={() => {
                                setSeeCheck(false);
                            }}
                        >
                            DA
                        </button>

                        <button
                            className="flex-1 bg-red-500 text-white py-2 rounded-md hover:bg-red-600 transition"
                            onClick={() => setSeeCheck(false)}
                        >
                            NE
                        </button>
                    </div>
                </div>
            </div>
)}

        {user.role === "MODERATOR" && (
            <div className="flex justify-between items-center w-3/4 bg-[#3B5B80] text-white p-4 rounded-lg mb-6 mt-6">
                <h2 className="text-lg font-semibold">Objavi dvoranu</h2>
                <button className="bg-white text-[#3B5B80] font-bold px-4 py-1 rounded hover:bg-gray-200 transition"
                onClick={() => setSeeForm(true)}>
                    +
                </button>
            </div>
        )}
        {seeForm && (
            <div  className="w-3/4 bg-white rounded-lg shadow-md p-6 m-6">
                <Form/>
                <button 
                    onClick={() => setSeeForm(false)}
                    className="mt-6 bg-red-500 text-white py-2 px-4 rounded  hover:bg-red-600 transition"
                >
                    Zatvori
                </button>
            </div>
        )}



      
        <div className="flex flex-col w-3/4 bg-[#d9d9d9] shadow-lg rounded-[10px] items-center py-6 mt-12 mb-12">
            <h2 className="text-xl font-semibold mb-6 text-[#3B5B80]">
            Moje rezervacije
            </h2>

            <p className="text-gray-600">Trenutno nemate rezervacija.</p>
        </div>

        <Footer />
    </div>
  );
};

export default ProfilePage;

