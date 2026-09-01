import React, { useState } from 'react';
import { createRoot } from 'react-dom/client';
import { MsalProvider } from '@azure/msal-react';
import { apiBaseUrl, apiRequest, currentAccount, initializeAuth, login, logout, msalInstance } from './auth';

function App() {
  const [output, setOutput] = useState('Listo para probar.');
  const account = currentAccount();

  async function run(label: string, action: () => Promise<Response>) {
    try {
      const response = await action();
      setOutput(`${label}: HTTP ${response.status}\n${await response.text()}`);
    } catch (error) {
      setOutput(`${label}: ${error instanceof Error ? error.message : String(error)}`);
    }
  }

  return <main>
    <h1>EV1 · Referencia React</h1>
    <p>Usuario: {account?.username ?? 'no autenticado'}</p>
    <button onClick={() => login()}>Login</button>
    <button onClick={() => logout()}>Logout</button>
    <button onClick={() => run('Público', () => fetch(`${apiBaseUrl}/public/health`))}>GET público</button>
    <button onClick={() => run('Perfil', () => apiRequest('/api/profile'))}>GET autenticado</button>
    <button onClick={() => run('Write', () => apiRequest('/api/write/example', { method: 'POST' }))}>POST scope write</button>
    <button onClick={() => run('Admin', () => apiRequest('/api/admin/example'))}>GET rol admin</button>
    <pre>{output}</pre>
  </main>;
}

initializeAuth().then(() => {
  createRoot(document.getElementById('root')!).render(
    <React.StrictMode><MsalProvider instance={msalInstance}><App /></MsalProvider></React.StrictMode>,
  );
});
