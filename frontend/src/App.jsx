import { useEffect, useMemo, useState } from "react";
import { analyzePassword, getHistory, savePassword } from "./api/passwordApi";
import PasswordForm from "./components/PasswordForm.jsx";
import StrengthMeter from "./components/StrengthMeter.jsx";
import FeedbackList from "./components/FeedbackList.jsx";
import SuggestionList from "./components/SuggestionList.jsx";
import HistoryPanel from "./components/HistoryPanel.jsx";

const emptyResult = {
  score: 0,
  label: "Not checked",
  entropyBits: 0,
  crackTime: "-",
  reused: false,
  commonPassword: false,
  feedback: ["Type a password to start the analysis."],
  suggestions: []
};

export default function App() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [result, setResult] = useState(emptyResult);
  const [history, setHistory] = useState([]);
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");

  const canSave = useMemo(() => {
    return username.trim() && password.trim() && result.score > 0 && !result.reused;
  }, [username, password, result]);

  useEffect(() => {
    if (!password.trim()) {
      setResult(emptyResult);
      return;
    }

    const timer = setTimeout(async () => {
      setLoading(true);
      setMessage("");
      try {
        const data = await analyzePassword({
          username: username.trim(),
          password
        });
        setResult(data);
      } catch (error) {
        setMessage(error.message);
      } finally {
        setLoading(false);
      }
    }, 350);

    return () => clearTimeout(timer);
  }, [username, password]);

  useEffect(() => {
    if (!username.trim()) {
      setHistory([]);
      return;
    }

    const timer = setTimeout(() => {
      refreshHistory(username);
    }, 400);

    return () => clearTimeout(timer);
  }, [username]);

  async function refreshHistory(name = username) {
    if (!name.trim()) {
      return;
    }
    try {
      const data = await getHistory(name.trim());
      setHistory(data);
    } catch {
      setHistory([]);
    }
  }

  async function handleSave() {
    setMessage("");
    try {
      await savePassword({
        username: username.trim(),
        password,
        score: result.score,
        label: result.label
      });
      setMessage("Password strength result saved safely. Only the BCrypt hash was stored.");
      await refreshHistory(username);
      const updated = await analyzePassword({ username: username.trim(), password });
      setResult(updated);
    } catch (error) {
      setMessage(error.message);
    }
  }

  function useSuggestion(value) {
    setPassword(value);
    setMessage("Suggestion copied into the password field.");
  }

  return (
    <main className="app-shell">
      <section className="topbar">
        <div>
          <p className="eyebrow">Internship Security Project</p>
          <h1>Password Strength Analyzer</h1>
        </div>
        <div className="status-pill name-pill">SANTHOSH S</div>
      </section>

      <section className="workspace">
        <div className="panel input-panel">
          <PasswordForm
            username={username}
            password={password}
            onUsernameChange={setUsername}
            onPasswordChange={setPassword}
            onSave={handleSave}
            canSave={canSave}
            loading={loading}
          />
          {message && <div className="message">{message}</div>}
        </div>

        <div className="panel result-panel">
          <StrengthMeter result={result} loading={loading} />
          <FeedbackList feedback={result.feedback} reused={result.reused} common={result.commonPassword} />
          <SuggestionList suggestions={result.suggestions} onUse={useSuggestion} />
        </div>

        <div className="panel history-panel">
          <HistoryPanel history={history} username={username} />
        </div>
      </section>
    </main>
  );
}
