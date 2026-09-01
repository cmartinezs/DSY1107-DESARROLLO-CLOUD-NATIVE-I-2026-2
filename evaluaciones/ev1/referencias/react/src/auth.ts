import {
  InteractionRequiredAuthError,
  PublicClientApplication,
  type AccountInfo,
} from '@azure/msal-browser';

const clientId = import.meta.env.VITE_ENTRA_CLIENT_ID as string;
const tenantId = import.meta.env.VITE_ENTRA_TENANT_ID as string;
const apiScope = import.meta.env.VITE_API_SCOPE as string;

export const apiBaseUrl = (import.meta.env.VITE_API_BASE_URL as string) || 'http://localhost:8080';

export const msalInstance = new PublicClientApplication({
  auth: {
    clientId,
    authority: `https://login.microsoftonline.com/${tenantId}`,
    redirectUri: window.location.origin,
    postLogoutRedirectUri: window.location.origin,
  },
  cache: { cacheLocation: 'sessionStorage' },
});

export async function initializeAuth(): Promise<void> {
  await msalInstance.initialize();
  const result = await msalInstance.handleRedirectPromise();
  if (result?.account) msalInstance.setActiveAccount(result.account);
  if (!msalInstance.getActiveAccount()) {
    msalInstance.setActiveAccount(msalInstance.getAllAccounts()[0] ?? null);
  }
}

export function currentAccount(): AccountInfo | null {
  return msalInstance.getActiveAccount() ?? msalInstance.getAllAccounts()[0] ?? null;
}

export async function login(): Promise<void> {
  await msalInstance.loginRedirect({ scopes: ['openid', 'profile', apiScope] });
}

export async function logout(): Promise<void> {
  await msalInstance.logoutRedirect({ account: currentAccount() ?? undefined });
}

export async function accessToken(): Promise<string> {
  const account = currentAccount();
  if (!account) throw new Error('No hay usuario autenticado.');
  try {
    const result = await msalInstance.acquireTokenSilent({ account, scopes: [apiScope] });
    return result.accessToken;
  } catch (error) {
    if (error instanceof InteractionRequiredAuthError) {
      await msalInstance.acquireTokenRedirect({ account, scopes: [apiScope] });
    }
    throw error;
  }
}

export async function apiRequest(path: string, init: RequestInit = {}): Promise<Response> {
  const token = await accessToken();
  const headers = new Headers(init.headers);
  headers.set('Authorization', `Bearer ${token}`);
  if (init.body && !headers.has('Content-Type')) headers.set('Content-Type', 'application/json');
  return fetch(`${apiBaseUrl}${path}`, { ...init, headers });
}
