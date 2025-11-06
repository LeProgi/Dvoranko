import Button from "../components/Button";
import { data, Link } from "react-router-dom";
import VenueCard from "../components/VenueCard";
import { useEffect, useState } from "react";
const Home = () => {
    
    const [hasLoggedIn, setHasLoggedIn] = useState(false);
    const [username, setUsername] = useState("");
    useEffect(() => {
        fetch("http://localhost:8080/api/auth/user", {
            credentials: "include",
        })
        .then((res) =>  {
            if(res.status === 200) return res.json();
            throw new Error("Nije ulogiran");
        })
        .then((data) => {
            setHasLoggedIn(true);
            setUsername(data.email);
        })
        .catch(() => {
            setHasLoggedIn(false);
            
        });
    }, []);

    const handleGoogleLogin = () => {
    window.location.href = "http://localhost:8080/oauth2/authorization/google";
    };

    return (
    <div className="flex flex-col min-h-screen w-full bg-gray-100 items-center">

      <div className="bg-[#3B5B80] w-full pb-10 rounded-b-[40%] flex flex-col items-center text-center">
        
        <div className="flex justify-evenly gap-12 mt-8 w-3/4">
            <Link to="/event-board" className="w-[50vw] block">
                <Button variant="default" title="Oglasna ploča" />
            </Link>
            <Button variant="default" title="Karta" />
            <Button variant="default" title="O nama" />
            <Button variant="default" title="Prijavi se" onClick={handleGoogleLogin}/>
            <Button variant="profile" title="profile" />
        </div>

        <h1 className="text-4xl text-white mt-10 mb-10 font-semibold tracking-wide">Dvoranko</h1>
      </div>
      
        <div className="flex flex-col w-3/4 bg-[#d9d9d9] rounded-[10px] items-center py-6 mt-12">
                <h2 className="text-xl font-semibold mb-6">Popis dvorana</h2>
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
    </div>
  );
}

export default Home;