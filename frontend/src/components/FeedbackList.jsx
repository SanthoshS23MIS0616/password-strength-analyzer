export default function FeedbackList({ feedback, reused, common }) {
  return (
    <div className="feedback-block">
      <div className="section-title">
        <h2>Feedback</h2>
        <span>{feedback?.length || 0} checks</span>
      </div>

      {(reused || common) && (
        <div className="warning-box">
          {reused && <p>This password was used before by this username.</p>}
          {common && <p>This password appears in the common password list.</p>}
        </div>
      )}

      <ul className="feedback-list">
        {(feedback || []).map((item, index) => (
          <li key={`${item}-${index}`}>{item}</li>
        ))}
      </ul>
    </div>
  );
}
