import { BrowserRouter, Route, Routes } from "react-router-dom";
import NavBar from "./components/NavBar";
import PersonalPage from "./pages/PersonalPage";
import FamilyPage from "./pages/FamilyPage";
import TripsPage from "./pages/TripsPage";
import TripDetailPage from "./pages/TripDetailPage";
import "./App.css";

export default function App() {
  return (
    <BrowserRouter>
      <NavBar />
      <div className="app">
        <Routes>
          <Route path="/" element={<PersonalPage />} />
          <Route path="/family" element={<FamilyPage />} />
          <Route path="/trips" element={<TripsPage />} />
          <Route path="/trips/:tripId" element={<TripDetailPage />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}
