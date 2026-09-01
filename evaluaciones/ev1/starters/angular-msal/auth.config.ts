import { InteractionType, PublicClientApplication } from '@azure/msal-browser';
import type {
  MsalGuardConfiguration,
  MsalInterceptorConfiguration,
} from '@azure/msal-angular';
import { environment } from './environment';

export function msalInstanceFactory() {
  return new PublicClientApplication({
    auth: {
      clientId: environment.auth.clientId,
      authority: `https://login.microsoftonline.com/${environment.auth.tenantId}`,
      redirectUri: window.location.origin,
      postLogoutRedirectUri: window.location.origin,
    },
    cache: {
      cacheLocation: 'sessionStorage',
    },
  });
}

export function msalGuardConfigFactory(): MsalGuardConfiguration {
  return {
    interactionType: InteractionType.Redirect,
    authRequest: {
      scopes: ['openid', 'profile', environment.auth.apiScope],
    },
  };
}

export function msalInterceptorConfigFactory(): MsalInterceptorConfiguration {
  const protectedResourceMap = new Map<string, Array<string | null>>();
  protectedResourceMap.set(
    `${environment.apiBaseUrl}/api/`,
    [environment.auth.apiScope],
  );

  return {
    interactionType: InteractionType.Redirect,
    protectedResourceMap,
  };
}
