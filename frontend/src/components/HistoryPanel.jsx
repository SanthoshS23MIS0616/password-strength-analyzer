export default function HistoryPanel({ history, username }) {
  return (
    <div>
      <div className="section-title">
        <h2>Recent History</h2>
        <span>{username ? username : "No user"}</span>
      </div>

      {!username && <p className="muted">Enter a username to view saved password strength history.</p>}
      {username && history.length === 0 && <p className="muted">No saved results yet.</p>}

      {history.length > 0 && (
        <div className="history-list">
          {history.map((item) => (
            <div className="history-item" key={item.id}>
              <div>
                <strong>{item.label}</strong>
                <span>{new Date(item.createdAt).toLocaleString()}</span>
              </div>
              <b>{item.score}</b>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
