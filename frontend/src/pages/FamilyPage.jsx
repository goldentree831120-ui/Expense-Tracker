import { useCallback, useEffect, useState } from "react";
import { familyApi } from "../api/familyApi";
import { expenseApi } from "../api/expenseApi";

function formatCurrency(amount) {
  return new Intl.NumberFormat("en-US", { style: "currency", currency: "USD" }).format(amount);
}

const emptyForm = { name: "", monthlyBudget: "" };

export default function FamilyPage() {
  const [budgets, setBudgets] = useState([]);
  const [form, setForm] = useState(emptyForm);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);
  const [selectedMemberId, setSelectedMemberId] = useState(null);
  const [memberExpenses, setMemberExpenses] = useState([]);

  const loadBudgets = useCallback(async () => {
    try {
      const data = await familyApi.getBudgetSummary();
      setBudgets(data);
    } catch (err) {
      setError(err.message);
    }
  }, []);

  useEffect(() => {
    async function init() {
      setLoading(true);
      await loadBudgets();
      setLoading(false);
    }
    init();
  }, [loadBudgets]);

  async function handleAddMember(e) {
    e.preventDefault();
    setError(null);
    try {
      await familyApi.create({
        name: form.name,
        monthlyBudget: form.monthlyBudget ? parseFloat(form.monthlyBudget) : null,
      });
      setForm(emptyForm);
      await loadBudgets();
    } catch (err) {
      setError(err.message);
    }
  }

  async function handleDeleteMember(id) {
    setError(null);
    try {
      await familyApi.remove(id);
      if (selectedMemberId === id) {
        setSelectedMemberId(null);
        setMemberExpenses([]);
      }
      await loadBudgets();
    } catch (err) {
      setError(err.message);
    }
  }

  async function handleViewExpenses(id) {
    setError(null);
    try {
      setSelectedMemberId(id);
      const data = await expenseApi.getAll({ familyMemberId: id });
      setMemberExpenses(data);
    } catch (err) {
      setError(err.message);
    }
  }

  const selectedMember = budgets.find((b) => b.memberId === selectedMemberId);

  return (
    <div>
      <header className="page-header">
        <h1>Family Budgets</h1>
        <p>Give each family member their own monthly budget and see who's spending what.</p>
      </header>

      {error && (
        <div className="error-banner">
          {error}
          <button onClick={() => setError(null)}>&times;</button>
        </div>
      )}

      <form className="expense-form" onSubmit={handleAddMember}>
        <h2>Add Family Member</h2>
        <div className="form-row">
          <label htmlFor="name">Name</label>
          <input
            id="name"
            value={form.name}
            onChange={(e) => setForm((prev) => ({ ...prev, name: e.target.value }))}
            placeholder="e.g. Mom, Dad, Alex"
            required
          />
        </div>
        <div className="form-row">
          <label htmlFor="monthlyBudget">Monthly budget (optional)</label>
          <input
            id="monthlyBudget"
            type="number"
            step="0.01"
            min="0"
            value={form.monthlyBudget}
            onChange={(e) => setForm((prev) => ({ ...prev, monthlyBudget: e.target.value }))}
            placeholder="Leave blank for no limit"
          />
        </div>
        <div className="form-actions">
          <button type="submit">Add Member</button>
        </div>
      </form>

      {loading ? (
        <p className="empty-state">Loading&hellip;</p>
      ) : budgets.length === 0 ? (
        <p className="empty-state">No family members yet. Add one above to get started.</p>
      ) : (
        <div className="member-grid">
          {budgets.map((b) => {
            const hasBudget = b.monthlyBudget != null;
            const budgetAmount = Number(b.monthlyBudget);
            const pct = hasBudget && budgetAmount > 0
              ? Math.min(100, (Number(b.spentThisMonth) / budgetAmount) * 100)
              : hasBudget && Number(b.spentThisMonth) > 0
                ? 100 // $0 budget with any spending at all is fully "used up"
                : 0;
            const overBudget = hasBudget && Number(b.remaining) < 0;
            return (
              <div className="member-card" key={b.memberId}>
                <div className="member-card-header">
                  <h3>{b.memberName}</h3>
                  <button className="link-button danger" onClick={() => handleDeleteMember(b.memberId)}>
                    Remove
                  </button>
                </div>
                <div className="member-card-amount">
                  {formatCurrency(b.spentThisMonth)}
                  {hasBudget && <span className="empty-state"> / {formatCurrency(b.monthlyBudget)} this month</span>}
                </div>
                {hasBudget && (
                  <>
                    <div className="budget-bar">
                      <div
                        className={`budget-bar-fill ${overBudget ? "over-budget" : ""}`}
                        style={{ width: `${pct}%` }}
                      />
                    </div>
                    <div className={`empty-state ${overBudget ? "over-budget-text" : ""}`}>
                      {overBudget
                        ? `${formatCurrency(Math.abs(b.remaining))} over budget`
                        : `${formatCurrency(b.remaining)} remaining`}
                    </div>
                  </>
                )}
                <button className="link-button" onClick={() => handleViewExpenses(b.memberId)}>
                  View expenses
                </button>
              </div>
            );
          })}
        </div>
      )}

      {selectedMemberId && (
        <div className="expense-list">
          <h2>{selectedMember?.memberName}&rsquo;s Expenses</h2>
          {memberExpenses.length === 0 ? (
            <p className="empty-state">No expenses recorded for this member yet.</p>
          ) : (
            <table>
              <thead>
                <tr>
                  <th>Date</th>
                  <th>Description</th>
                  <th>Category</th>
                  <th className="amount-col">Amount</th>
                </tr>
              </thead>
              <tbody>
                {memberExpenses.map((e) => (
                  <tr key={e.id}>
                    <td>{e.expenseDate}</td>
                    <td>{e.description}</td>
                    <td>
                      <span className={`badge badge-${e.category.toLowerCase()}`}>{e.category}</span>
                    </td>
                    <td className="amount-col">{formatCurrency(e.amount)}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>
      )}
    </div>
  );
}
