import type { Configuration, RedirectRequest } from '@azure/msal-browser';

const clientId = import.meta.env.VITE_ENTRA_CLIENT_ID as string;
const tenantId = import.meta.env.VITE_ENTRA_TENANT_ID as string;
const apiScope = import.meta.env.VITE_API_SCOPE as string;

export const msalConfig: Configuration = {
  auth: {
    clientId,
    authority: `https://login.microsoftonline.com/${tenantId}`,
    redirectUri: window.location.origin,
    postLogoutRedirectUri: window.location.origin,
  },
  cache: {
    cacheLocation: 'sessionStorage',
  },
};

export const loginRequest: RedirectRequest = {
  scopes: ['openid', 'profile', apiScope],
};

export const apiScopes = [apiScope];
