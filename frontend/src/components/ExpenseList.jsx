const CATEGORIES = [
  "ALL",
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

function formatCurrency(amount) {
  return new Intl.NumberFormat("en-US", { style: "currency", currency: "USD" }).format(amount);
}

export default function ExpenseList({ expenses, categoryFilter, onCategoryFilterChange, onEdit, onDelete }) {
  return (
    <div className="expense-list">
      <div className="list-header">
        <h2>Expenses</h2>
        <div className="filter-row">
          <label htmlFor="categoryFilter">Filter by category</label>
          <select
            id="categoryFilter"
            value={categoryFilter}
            onChange={(e) => onCategoryFilterChange(e.target.value)}
          >
            {CATEGORIES.map((c) => (
              <option key={c} value={c}>
                {c}
              </option>
            ))}
          </select>
        </div>
      </div>

      {expenses.length === 0 ? (
        <p className="empty-state">No expenses yet. Add one above to get started.</p>
      ) : (
        <table>
          <thead>
            <tr>
              <th>Date</th>
              <th>Description</th>
              <th>Category</th>
              <th>Member</th>
              <th className="amount-col">Amount</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {expenses.map((expense) => (
              <tr key={expense.id}>
                <td>{expense.expenseDate}</td>
                <td>{expense.description}</td>
                <td>
                  <span className={`badge badge-${expense.category.toLowerCase()}`}>{expense.category}</span>
                </td>
                <td>{expense.familyMemberName || <span className="empty-state">—</span>}</td>
                <td className="amount-col">{formatCurrency(expense.amount)}</td>
                <td className="actions-col">
                  <button className="link-button" onClick={() => onEdit(expense)}>
                    Edit
                  </button>
                  <button className="link-button danger" onClick={() => onDelete(expense.id)}>
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}
