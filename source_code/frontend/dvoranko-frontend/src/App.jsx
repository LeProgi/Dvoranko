import { useEffect, useState } from 'react'
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import './App.css'

import Home from './pages/Home';
import EventBoard from './pages/EventBoard';
import VenuePage from './pages/VenuePage';

const App = () => {
  
  return(
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/event-board" element={<EventBoard />} />
        <Route path="/venue1" element={<VenuePage/>}/>
      </Routes>
    </Router>
  )
}

export default App
