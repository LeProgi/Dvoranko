import { useEffect, useState } from 'react'
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import './App.css'

import Home from './pages/Home';
import EventBoard from './pages/EventBoard';
import VenuePage from './pages/VenuePage';
import MapPage from './pages/MapPage';

const App = () => {
  
  return(
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/event-board" element={<EventBoard />} />
        <Route path="/venue1" element={<VenuePage/>}/>
        <Route path="/maps" element={<MapPage/>}/>
      </Routes>
    </Router>
  )
}

export default App
