import { NavLink } from "react-router-dom";

export default function NavBar() {
  return (
    <nav className="navbar">
      <div className="navbar-brand">Expense Tracker</div>
      <div className="navbar-links">
        <NavLink to="/" end className={({ isActive }) => (isActive ? "nav-link active" : "nav-link")}>
          Personal
        </NavLink>
        <NavLink to="/family" className={({ isActive }) => (isActive ? "nav-link active" : "nav-link")}>
          Family Budgets
        </NavLink>
        <NavLink to="/trips" className={({ isActive }) => (isActive ? "nav-link active" : "nav-link")}>
          Group Trips
        </NavLink>
      </div>
    </nav>
  );
}
