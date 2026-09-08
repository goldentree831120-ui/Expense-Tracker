import { useCallback, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { tripApi } from "../api/tripApi";

function formatCurrency(amount) {
  return new Intl.NumberFormat("en-US", { style: "currency", currency: "USD" }).format(amount);
}

const emptyForm = { name: "", startDate: new Date().toISOString().slice(0, 10), endDate: "", description: "" };

export default function TripsPage() {
  const [trips, setTrips] = useState([]);
  const [form, setForm] = useState(emptyForm);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  const loadTrips = useCallback(async () => {
    try {
      const data = await tripApi.getAll();
      // Most recent trip first.
      setTrips(data.slice().sort((a, b) => (a.startDate < b.startDate ? 1 : -1)));
    } catch (err) {
      setError(err.message);
    }
  }, []);

  useEffect(() => {
    async function init() {
      setLoading(true);
      await loadTrips();
      setLoading(false);
    }
    init();
  }, [loadTrips]);

  async function handleCreate(e) {
    e.preventDefault();
    setError(null);
    try {
      await tripApi.create({
        ...form,
        endDate: form.endDate || null,
        description: form.description || null,
      });
      setForm(emptyForm);
      await loadTrips();
    } catch (err) {
      setError(err.message);
    }
  }

  async function handleDelete(id) {
    setError(null);
    try {
      await tripApi.remove(id);
      await loadTrips();
    } catch (err) {
      setError(err.message);
    }
  }

  return (
    <div>
      <header className="page-header">
        <h1>Group Trips</h1>
        <p>Log shared expenses for a trip, then see totals broken down week by week.</p>
      </header>

      {error && (
        <div className="error-banner">
          {error}
          <button onClick={() => setError(null)}>&times;</button>
        </div>
      )}

      <form className="expense-form" onSubmit={handleCreate}>
        <h2>New Trip</h2>
        <div className="form-row">
          <label htmlFor="name">Trip name</label>
          <input
            id="name"
            value={form.name}
            onChange={(e) => setForm((prev) => ({ ...prev, name: e.target.value }))}
            placeholder="e.g. Goa Trip"
            required
          />
        </div>
        <div className="form-row">
          <label htmlFor="startDate">Start date</label>
          <input
            id="startDate"
            type="date"
            value={form.startDate}
            onChange={(e) => setForm((prev) => ({ ...prev, startDate: e.target.value }))}
            required
          />
        </div>
        <div className="form-row">
          <label htmlFor="endDate">End date (optional, leave blank if ongoing)</label>
          <input
            id="endDate"
            type="date"
            value={form.endDate}
            onChange={(e) => setForm((prev) => ({ ...prev, endDate: e.target.value }))}
          />
        </div>
        <div className="form-row">
          <label htmlFor="description">Description (optional)</label>
          <input
            id="description"
            value={form.description}
            onChange={(e) => setForm((prev) => ({ ...prev, description: e.target.value }))}
            placeholder="e.g. Friends trip with the college group"
          />
        </div>
        <div className="form-actions">
          <button type="submit">Create Trip</button>
        </div>
      </form>

      {loading ? (
        <p className="empty-state">Loading&hellip;</p>
      ) : trips.length === 0 ? (
        <p className="empty-state">No trips yet. Create one above to start logging expenses.</p>
      ) : (
        <div className="trip-grid">
          {trips.map((trip) => (
            <div className="trip-card" key={trip.id}>
              <div className="member-card-header">
                <h3>
                  <Link to={`/trips/${trip.id}`}>{trip.name}</Link>
                </h3>
                <button className="link-button danger" onClick={() => handleDelete(trip.id)}>
                  Delete
                </button>
              </div>
              <div className="empty-state">
                {trip.startDate} {trip.endDate ? `– ${trip.endDate}` : "(ongoing)"}
              </div>
              {trip.description && <p>{trip.description}</p>}
              <div className="member-card-amount">
                {formatCurrency(trip.totalSpent)}
                <span className="empty-state"> · {trip.expenseCount} expense{trip.expenseCount === 1 ? "" : "s"}</span>
              </div>
              <Link className="link-button" to={`/trips/${trip.id}`}>
                Open trip &rarr;
              </Link>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
