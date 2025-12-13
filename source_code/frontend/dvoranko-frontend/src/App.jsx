import { useEffect, useState } from 'react'
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import './App.css'

import Home from './pages/Home';
import EventBoard from './pages/EventBoard';
import VenuePage from './pages/VenuePage';
import MapPage from './pages/MapPage';
import ProfilePage from './pages/ProfilePage';
import AdminPage from './pages/AdminPage';

const App = () => {
  
  return(
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/event-board" element={<EventBoard />} />
        <Route path="/venue/:id" element={<VenuePage/>}/>
        <Route path="/maps" element={<MapPage/>}/>
        <Route path='/my-profile' element={<ProfilePage/>}/>
        <Route path='/admin' element ={<AdminPage/>}/>
      </Routes>
    </Router>
  )
}

export default App
