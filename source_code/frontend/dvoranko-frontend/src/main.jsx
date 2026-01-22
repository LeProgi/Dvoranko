import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import { urlPublic as url } from './URL.js'

export { url };

createRoot(document.getElementById("root")).render(<App />);
