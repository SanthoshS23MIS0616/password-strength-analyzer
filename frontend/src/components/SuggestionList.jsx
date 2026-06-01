export default function SuggestionList({ suggestions, onUse }) {
  return (
    <div className="suggestion-block">
      <div className="section-title">
        <h2>Stronger Suggestions</h2>
        <span>{suggestions?.length || 0} options</span>
      </div>

      {!suggestions?.length && <p className="muted">Suggestions appear after analysis.</p>}

      <div className="suggestions">
        {(suggestions || []).map((suggestion) => (
          <button key={suggestion} type="button" onClick={() => onUse(suggestion)}>
            {suggestion}
          </button>
        ))}
      </div>
    </div>
  );
}
