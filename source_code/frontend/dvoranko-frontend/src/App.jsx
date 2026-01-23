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
import UpdateCategory from './pages/UpdateCategory';
import ProtectedRoute from './components/ProtectedRoute';

const App = () => {
  
  return(
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/event-board" element={<EventBoard />} />
        <Route path="/maps" element={<MapPage/>}/>
        <Route path="/venue/:id" element={<ProtectedRoute><VenuePage/></ProtectedRoute>}/>
        <Route path='/my-profile' element={<ProtectedRoute><ProfilePage/></ProtectedRoute>}/>
        <Route path='/admin' element={<ProtectedRoute><AdminPage/></ProtectedRoute>}/>
        <Route path='/form' element={<ProtectedRoute><FormPage/></ProtectedRoute>}/>
        <Route path='/editform/:id' element={<ProtectedRoute><EditFormPage/></ProtectedRoute>}/>
        <Route path='/reservation' element={<ProtectedRoute><Reservation/></ProtectedRoute>}/>
        <Route path='/reservations/:idDvorana' element={<ProtectedRoute><VenueReservations/></ProtectedRoute>}/>
        <Route path='/admin/updatecategory' element={<ProtectedRoute><UpdateCategory/></ProtectedRoute>}/>
      </Routes>
    </Router>
  )
}

export default App
