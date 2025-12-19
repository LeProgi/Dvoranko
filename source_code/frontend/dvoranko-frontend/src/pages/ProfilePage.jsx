import Form from "../components/Form.jsx";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { url } from "../main.jsx";
import Button from "../components/Button.jsx";
import Footer from "../components/Footer";
import { useLocation, useNavigate } from "react-router-dom";


const zahtjevIznajmljivac = () => { 
    try {
      const res = fetch(`${url}/api/user/request/getModerator`, {
        method: "POST",
        credentials: "include",
      });

    } catch (err) {
      console.error("Error pri pokušaju slanja zahtjva:", err);
    }
  };
    


const ProfilePage = () => {
    const location = useLocation();
    
    const [seeForm, setSeeForm] = useState(false);
    const [seeCheck, setSeeCheck] = useState(false);
     
    const [user, setUser] = useState(location.state?.user ?? null);
    const navigate = useNavigate();

    useEffect(() => {
        if (user) return;

        fetch(`${url}/api/auth/user`, {
            credentials: "include",
        })
            .then((res) => {
            if (!res.ok) throw new Error("Not logged in");
            return res.json();
            })
            .then((data) => setUser(data))
            .catch(() => {
                setUser(null);
                navigate("/", { replace: true });
            });
    }, [user, navigate]);

    if (!user) {
        return (
            <div className="flex min-h-screen items-center justify-center">
            <p>Učitavanje profila...</p>
            </div>
        );
    }

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
                                zahtjevIznajmljivac();
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

        {user.role === "MODERATOR"  && (
            <div className="flex justify-between items-center w-3/4 bg-[#3B5B80] text-white p-4 rounded-lg mb-6 mt-6">
                <h2 className="text-lg font-semibold">Objavi dvoranu</h2>
                <Link to="/form" >
                <button className="bg-white text-[#3B5B80] font-bold px-4 py-1 rounded hover:bg-gray-200 transition">
                    +
                </button>
                </Link>
                
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

