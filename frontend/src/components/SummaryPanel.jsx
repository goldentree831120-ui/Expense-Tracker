function formatCurrency(amount) {
  return new Intl.NumberFormat("en-US", { style: "currency", currency: "USD" }).format(amount);
}

const MONTH_NAMES = [
  "January", "February", "March", "April", "May", "June",
  "July", "August", "September", "October", "November", "December",
];

export default function SummaryPanel({ categorySummary, monthlySummary }) {
  const categoryTotal = categorySummary.reduce((sum, row) => sum + row.total, 0);

  return (
    <div className="summary-panel">
      <div className="summary-card">
        <h2>By Category</h2>
        {categorySummary.length === 0 ? (
          <p className="empty-state">No data yet.</p>
        ) : (
          <ul className="summary-list">
            {categorySummary
              .slice()
              .sort((a, b) => b.total - a.total)
              .map((row) => (
                <li key={row.category}>
                  <span className={`badge badge-${row.category.toLowerCase()}`}>{row.category}</span>
                  <span className="summary-count">{row.count} item{row.count === 1 ? "" : "s"}</span>
                  <span className="summary-amount">{formatCurrency(row.total)}</span>
                </li>
              ))}
          </ul>
        )}
        {categorySummary.length > 0 && (
          <div className="summary-total">Total: {formatCurrency(categoryTotal)}</div>
        )}
      </div>

      <div className="summary-card">
        <h2>By Month</h2>
        {monthlySummary.length === 0 ? (
          <p className="empty-state">No data yet.</p>
        ) : (
          <ul className="summary-list">
            {monthlySummary.map((row) => (
              <li key={`${row.year}-${row.month}`}>
                <span>{MONTH_NAMES[row.month - 1]} {row.year}</span>
                <span className="summary-count">{row.count} item{row.count === 1 ? "" : "s"}</span>
                <span className="summary-amount">{formatCurrency(row.total)}</span>
              </li>
            ))}
          </ul>
        )}
      </div>
    </div>
  );
}
