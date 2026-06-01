import { useState } from "react";

export default function PasswordForm({
  username,
  password,
  onUsernameChange,
  onPasswordChange,
  onSave,
  canSave,
  loading
}) {
  const [visible, setVisible] = useState(false);

  return (
    <div className="form-stack">
      <div className="section-title">
        <h2>Analyze Password</h2>
        <span>{loading ? "Checking..." : "Live check"}</span>
      </div>

      <label>
        Username
        <input
          value={username}
          onChange={(event) => onUsernameChange(event.target.value)}
          placeholder="Example: santhosh"
          maxLength={60}
        />
      </label>

      <label>
        Password
        <div className="password-row">
          <input
            value={password}
            onChange={(event) => onPasswordChange(event.target.value)}
            type={visible ? "text" : "password"}
            placeholder="Type a password"
            maxLength={128}
          />
          <button type="button" className="ghost-button" onClick={() => setVisible(!visible)}>
            {visible ? "Hide" : "Show"}
          </button>
        </div>
      </label>

      <button type="button" className="primary-button" onClick={onSave} disabled={!canSave}>
        Save to History
      </button>
    </div>
  );
}
