const API_BASE = "http://localhost:8081/api/password";

async function request(path, options = {}) {
  const response = await fetch(`${API_BASE}${path}`, {
    headers: {
      "Content-Type": "application/json",
      ...(options.headers || {})
    },
    ...options
  });

  const data = await response.json().catch(() => null);
  if (!response.ok) {
    throw new Error(data?.message || "Request failed");
  }
  return data;
}

export function analyzePassword(payload) {
  return request("/analyze", {
    method: "POST",
    body: JSON.stringify(payload)
  });
}

export function savePassword(payload) {
  return request("/save", {
    method: "POST",
    body: JSON.stringify(payload)
  });
}

export function getHistory(username) {
  return request(`/history/${encodeURIComponent(username)}`);
}
