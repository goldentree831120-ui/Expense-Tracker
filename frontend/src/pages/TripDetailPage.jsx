import { useCallback, useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { tripApi } from "../api/tripApi";

function formatCurrency(amount) {
  return new Intl.NumberFormat("en-US", { style: "currency", currency: "USD" }).format(amount);
}

const emptyForm = {
  description: "",
  amount: "",
  paidBy: "",
  expenseDate: new Date().toISOString().slice(0, 10),
};

export default function TripDetailPage() {
  const { tripId } = useParams();
  const navigate = useNavigate();

  const [trip, setTrip] = useState(null);
  const [expenses, setExpenses] = useState([]);
  const [weeklySummary, setWeeklySummary] = useState([]);
  const [form, setForm] = useState(emptyForm);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  const loadAll = useCallback(async () => {
    try {
      const [tripData, expenseData, weeklyData] = await Promise.all([
        tripApi.getById(tripId),
        tripApi.getExpenses(tripId),
        tripApi.getWeeklySummary(tripId),
      ]);
      setTrip(tripData);
      setExpenses(expenseData);
      setWeeklySummary(weeklyData);
    } catch (err) {
      setError(err.message);
    }
  }, [tripId]);

  useEffect(() => {
    async function init() {
      setLoading(true);
      await loadAll();
      setLoading(false);
    }
    init();
  }, [loadAll]);

  async function handleAddExpense(e) {
    e.preventDefault();
    setError(null);
    try {
      await tripApi.addExpense(tripId, {
        ...form,
        amount: parseFloat(form.amount),
      });
      setForm(emptyForm);
      await loadAll();
    } catch (err) {
      setError(err.message);
    }
  }

  async function handleDeleteExpense(expenseId) {
    setError(null);
    try {
      await tripApi.removeExpense(tripId, expenseId);
      await loadAll();
    } catch (err) {
      setError(err.message);
    }
  }

  async function handleDeleteTrip() {
    setError(null);
    try {
      await tripApi.remove(tripId);
      navigate("/trips");
    } catch (err) {
      setError(err.message);
    }
  }

  if (loading) {
    return <p className="empty-state">Loading&hellip;</p>;
  }

  if (!trip) {
    return (
      <div>
        {error && (
          <div className="error-banner">
            {error}
            <button onClick={() => setError(null)}>&times;</button>
          </div>
        )}
        <p className="empty-state">
          {error ? "Couldn't load this trip - see the error above." : "Trip not found."}
        </p>
        <Link className="link-button" to="/trips">
          &larr; Back to all trips
        </Link>
      </div>
    );
  }

  return (
    <div>
      <header className="page-header">
        <Link className="link-button" to="/trips">
          &larr; All trips
        </Link>
        <h1>{trip.name}</h1>
        <p>
          {trip.startDate} {trip.endDate ? `– ${trip.endDate}` : "(ongoing)"}
          {trip.description ? ` · ${trip.description}` : ""}
        </p>
      </header>

      {error && (
        <div className="error-banner">
          {error}
          <button onClick={() => setError(null)}>&times;</button>
        </div>
      )}

      <div className="app-layout">
        <div className="app-main">
          <form className="expense-form" onSubmit={handleAddExpense}>
            <h2>Add Trip Expense</h2>
            <div className="form-row">
              <label htmlFor="description">Description</label>
              <input
                id="description"
                value={form.description}
                onChange={(e) => setForm((prev) => ({ ...prev, description: e.target.value }))}
                placeholder="e.g. Dinner at the beach shack"
                required
              />
            </div>
            <div className="form-row">
              <label htmlFor="amount">Amount</label>
              <input
                id="amount"
                type="number"
                step="0.01"
                min="0.01"
                value={form.amount}
                onChange={(e) => setForm((prev) => ({ ...prev, amount: e.target.value }))}
                required
              />
            </div>
            <div className="form-row">
              <label htmlFor="paidBy">Paid by</label>
              <input
                id="paidBy"
                value={form.paidBy}
                onChange={(e) => setForm((prev) => ({ ...prev, paidBy: e.target.value }))}
                placeholder="Who paid for this?"
                required
              />
            </div>
            <div className="form-row">
              <label htmlFor="expenseDate">Date</label>
              <input
                id="expenseDate"
                type="date"
                value={form.expenseDate}
                onChange={(e) => setForm((prev) => ({ ...prev, expenseDate: e.target.value }))}
                required
              />
            </div>
            <div className="form-actions">
              <button type="submit">Add Expense</button>
            </div>
          </form>

          <div className="expense-list">
            <h2>All Expenses</h2>
            {expenses.length === 0 ? (
              <p className="empty-state">No expenses logged yet. Add the first one above.</p>
            ) : (
              <table>
                <thead>
                  <tr>
                    <th>Date</th>
                    <th>Description</th>
                    <th>Paid by</th>
                    <th className="amount-col">Amount</th>
                    <th></th>
                  </tr>
                </thead>
                <tbody>
                  {expenses.map((e) => (
                    <tr key={e.id}>
                      <td>{e.expenseDate}</td>
                      <td>{e.description}</td>
                      <td>{e.paidBy}</td>
                      <td className="amount-col">{formatCurrency(e.amount)}</td>
                      <td className="actions-col">
                        <button className="link-button danger" onClick={() => handleDeleteExpense(e.id)}>
                          Delete
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            )}
          </div>
        </div>

        <aside className="app-sidebar">
          <div className="summary-card">
            <h2>Weekly Evaluation</h2>
            {weeklySummary.length === 0 ? (
              <p className="empty-state">No data yet.</p>
            ) : (
              <ul className="summary-list">
                {weeklySummary.map((w) => (
                  <li key={w.weekNumber}>
                    <span>
                      Week {w.weekNumber}
                      <br />
                      <span className="empty-state">
                        {w.weekStart} – {w.weekEnd}
                      </span>
                    </span>
                    <span className="summary-count">{w.count} item{w.count === 1 ? "" : "s"}</span>
                    <span className="summary-amount">{formatCurrency(w.total)}</span>
                  </li>
                ))}
              </ul>
            )}
            <div className="summary-total">Total: {formatCurrency(trip.totalSpent)}</div>
          </div>

          <button className="secondary" onClick={handleDeleteTrip}>
            Delete This Trip
          </button>
        </aside>
      </div>
    </div>
  );
}
