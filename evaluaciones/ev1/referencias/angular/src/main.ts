import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient } from '@angular/common/http';
import { PublicClientApplication } from '@azure/msal-browser';
import { MSAL_INSTANCE, MsalService } from '@azure/msal-angular';
import { AppComponent } from './app/app.component';
import { environment } from './environments/environment';

function msalInstanceFactory() {
  return new PublicClientApplication({
    auth: {
      clientId: environment.auth.clientId,
      authority: `https://login.microsoftonline.com/${environment.auth.tenantId}`,
      redirectUri: window.location.origin,
      postLogoutRedirectUri: window.location.origin,
    },
    cache: { cacheLocation: 'sessionStorage' },
  });
}

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(),
    { provide: MSAL_INSTANCE, useFactory: msalInstanceFactory },
    MsalService,
  ],
}).catch(console.error);
