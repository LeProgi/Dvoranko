import { useEffect, useState } from 'react'
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import './App.css'

import Home from './pages/Home';
import EventBoard from './pages/EventBoard';
import VenuePage from './pages/VenuePage';
import MapPage from './pages/MapPage';
import ProfilePage from './pages/ProfilePage';
import AdminPage from './pages/AdminPage';
import FormPage from './pages/FormPage';
import EditFormPage from './pages/EditFormPage'
import Reservation from './pages/ReservationPage';
import VenueReservations from './pages/VenueReservations';

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
        <Route path='/form' element={<FormPage/>}/>
        <Route path='/editform/:id' element= {<EditFormPage/>}/>
        <Route path='/reservation' element={<Reservation/>}/>
        <Route path='reservations/:idDvorana' element={<VenueReservations/>}/>
      </Routes>
    </Router>
  )
}

export default App
