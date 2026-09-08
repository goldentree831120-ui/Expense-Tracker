import { useEffect, useState } from "react";

const CATEGORIES = [
  "FOOD",
  "RENT",
  "TRAVEL",
  "UTILITIES",
  "ENTERTAINMENT",
  "HEALTH",
  "SHOPPING",
  "EDUCATION",
  "OTHER",
];

const emptyForm = {
  description: "",
  amount: "",
  category: "FOOD",
  expenseDate: new Date().toISOString().slice(0, 10),
  familyMemberId: "",
};

/**
 * Shared form for creating a new expense and editing an existing one.
 * `editingExpense` (nullable) tells us which mode we're in. `members` is
 * optional - pass a list of family members to show a "who is this for?"
 * picker; omit it (or pass an empty array) to hide that field entirely.
 */
export default function ExpenseForm({ editingExpense, members = [], onSubmit, onCancelEdit }) {
  const [form, setForm] = useState(emptyForm);

  useEffect(() => {
    if (editingExpense) {
      setForm({
        description: editingExpense.description,
        amount: editingExpense.amount,
        category: editingExpense.category,
        expenseDate: editingExpense.expenseDate,
        familyMemberId: editingExpense.familyMemberId ?? "",
      });
    } else {
      setForm(emptyForm);
    }
  }, [editingExpense]);

  function handleChange(e) {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  }

  function handleSubmit(e) {
    e.preventDefault();
    onSubmit({
      ...form,
      amount: parseFloat(form.amount),
      familyMemberId: form.familyMemberId ? Number(form.familyMemberId) : null,
    });
    if (!editingExpense) {
      setForm(emptyForm);
    }
  }

  return (
    <form className="expense-form" onSubmit={handleSubmit}>
      <h2>{editingExpense ? "Edit Expense" : "Add Expense"}</h2>

      <div className="form-row">
        <label htmlFor="description">Description</label>
        <input
          id="description"
          name="description"
          type="text"
          value={form.description}
          onChange={handleChange}
          placeholder="e.g. Groceries at Whole Foods"
          required
        />
      </div>

      <div className="form-row">
        <label htmlFor="amount">Amount</label>
        <input
          id="amount"
          name="amount"
          type="number"
          step="0.01"
          min="0.01"
          value={form.amount}
          onChange={handleChange}
          required
        />
      </div>

      <div className="form-row">
        <label htmlFor="category">Category</label>
        <select id="category" name="category" value={form.category} onChange={handleChange}>
          {CATEGORIES.map((c) => (
            <option key={c} value={c}>
              {c}
            </option>
          ))}
        </select>
      </div>

      <div className="form-row">
        <label htmlFor="expenseDate">Date</label>
        <input
          id="expenseDate"
          name="expenseDate"
          type="date"
          value={form.expenseDate}
          onChange={handleChange}
          required
        />
      </div>

      {members.length > 0 && (
        <div className="form-row">
          <label htmlFor="familyMemberId">Family member (optional)</label>
          <select
            id="familyMemberId"
            name="familyMemberId"
            value={form.familyMemberId}
            onChange={handleChange}
          >
            <option value="">— Unassigned —</option>
            {members.map((m) => (
              <option key={m.id} value={m.id}>
                {m.name}
              </option>
            ))}
          </select>
        </div>
      )}

      <div className="form-actions">
        <button type="submit">{editingExpense ? "Save Changes" : "Add Expense"}</button>
        {editingExpense && (
          <button type="button" className="secondary" onClick={onCancelEdit}>
            Cancel
          </button>
        )}
      </div>
    </form>
  );
}
