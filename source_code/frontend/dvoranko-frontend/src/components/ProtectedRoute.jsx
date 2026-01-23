import { useEffect, useState } from 'react';
import { url } from '../main.jsx';

const ProtectedRoute = ({ children }) => {
    const [isAuthenticated, setIsAuthenticated] = useState(null);

    useEffect(() => {
        const checkAuth = async () => {
            try {
                const res = await fetch(`${url}/api/auth/user`, {
                    credentials: "include",
                });
                
                if (res.ok) {
                    setIsAuthenticated(true);
                } else {
                    setIsAuthenticated(false);
                    window.location.href = `${url}/oauth2/authorization/google`;
                }
            } catch (err) {
                setIsAuthenticated(false);
                window.location.href = `${url}/oauth2/authorization/google`;
            }
        };

        checkAuth();
    }, []);

    if (isAuthenticated === null) {
        return <div>Učitavanje...</div>;
    }

    return isAuthenticated ? children : null;
};

export default ProtectedRoute;
