import { useCallback, useEffect, useState } from "react";
import ExpenseForm from "../components/ExpenseForm";
import ExpenseList from "../components/ExpenseList";
import SummaryPanel from "../components/SummaryPanel";
import { expenseApi } from "../api/expenseApi";
import { familyApi } from "../api/familyApi";

export default function PersonalPage() {
  const [expenses, setExpenses] = useState([]);
  const [categorySummary, setCategorySummary] = useState([]);
  const [monthlySummary, setMonthlySummary] = useState([]);
  const [members, setMembers] = useState([]);
  const [categoryFilter, setCategoryFilter] = useState("ALL");
  const [editingExpense, setEditingExpense] = useState(null);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  const loadExpenses = useCallback(async () => {
    try {
      const category = categoryFilter === "ALL" ? undefined : categoryFilter;
      const data = await expenseApi.getAll({ category });
      setExpenses(data);
    } catch (err) {
      setError(err.message);
    }
  }, [categoryFilter]);

  const loadSummaries = useCallback(async () => {
    try {
      const [byCategory, byMonth] = await Promise.all([
        expenseApi.getSummaryByCategory(),
        expenseApi.getSummaryByMonth(),
      ]);
      setCategorySummary(byCategory);
      setMonthlySummary(byMonth);
    } catch (err) {
      setError(err.message);
    }
  }, []);

  const loadMembers = useCallback(async () => {
    try {
      const data = await familyApi.getAll();
      setMembers(data);
    } catch {
      // Family members are optional context here - if this call fails we
      // just fall back to showing the form without a member picker rather
      // than blocking the whole page with an error banner.
    }
  }, []);

  useEffect(() => {
    async function init() {
      setLoading(true);
      await Promise.all([loadExpenses(), loadSummaries(), loadMembers()]);
      setLoading(false);
    }
    init();
  }, [loadExpenses, loadSummaries, loadMembers]);

  async function handleSubmit(formValues) {
    setError(null);
    try {
      if (editingExpense) {
        await expenseApi.update(editingExpense.id, formValues);
        setEditingExpense(null);
      } else {
        await expenseApi.create(formValues);
      }
      await Promise.all([loadExpenses(), loadSummaries()]);
    } catch (err) {
      setError(err.message);
    }
  }

  async function handleDelete(id) {
    setError(null);
    try {
      await expenseApi.remove(id);
      if (editingExpense?.id === id) {
        setEditingExpense(null);
      }
      await Promise.all([loadExpenses(), loadSummaries()]);
    } catch (err) {
      setError(err.message);
    }
  }

  return (
    <div>
      <header className="page-header">
        <h1>Personal Expenses</h1>
        <p>Track spending, categorize it, and see where your money goes.</p>
      </header>

      {error && (
        <div className="error-banner">
          {error}
          <button onClick={() => setError(null)}>&times;</button>
        </div>
      )}

      {loading ? (
        <p className="empty-state">Loading&hellip;</p>
      ) : (
        <div className="app-layout">
          <div className="app-main">
            <ExpenseForm
              editingExpense={editingExpense}
              members={members}
              onSubmit={handleSubmit}
              onCancelEdit={() => setEditingExpense(null)}
            />
            <ExpenseList
              expenses={expenses}
              categoryFilter={categoryFilter}
              onCategoryFilterChange={setCategoryFilter}
              onEdit={setEditingExpense}
              onDelete={handleDelete}
            />
          </div>
          <aside className="app-sidebar">
            <SummaryPanel categorySummary={categorySummary} monthlySummary={monthlySummary} />
          </aside>
        </div>
      )}
    </div>
  );
}
