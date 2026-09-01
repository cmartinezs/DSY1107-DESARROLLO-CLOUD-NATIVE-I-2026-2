import type { ApplicationConfig } from '@angular/core';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import {
  MSAL_INSTANCE,
  MsalBroadcastService,
  MsalService,
} from '@azure/msal-angular';
import { msalInstanceFactory } from './auth.config';
import { authInterceptor } from './auth.interceptor';

export const authAppConfig: ApplicationConfig = {
  providers: [
    {
      provide: MSAL_INSTANCE,
      useFactory: msalInstanceFactory,
    },
    MsalService,
    MsalBroadcastService,
    provideHttpClient(withInterceptors([authInterceptor])),
  ],
};
