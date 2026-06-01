function colorClass(label) {
  return label.toLowerCase().replace(/\s+/g, "-");
}

export default function StrengthMeter({ result, loading }) {
  const percent = Math.max(0, Math.min(100, result.score || 0));

  return (
    <div className="strength-card">
      <div className="section-title">
        <h2>Strength Result</h2>
        <span>{loading ? "Updating" : result.label}</span>
      </div>

      <div className="score-line">
        <strong>{percent}</strong>
        <span>/ 100</span>
      </div>

      <div className="meter-track">
        <div className={`meter-fill ${colorClass(result.label)}`} style={{ width: `${percent}%` }} />
      </div>

      <div className="metrics-grid">
        <div>
          <span>Label</span>
          <strong>{result.label}</strong>
        </div>
        <div>
          <span>Entropy</span>
          <strong>{result.entropyBits} bits</strong>
        </div>
        <div>
          <span>Crack time</span>
          <strong>{result.crackTime}</strong>
        </div>
      </div>
    </div>
  );
}
