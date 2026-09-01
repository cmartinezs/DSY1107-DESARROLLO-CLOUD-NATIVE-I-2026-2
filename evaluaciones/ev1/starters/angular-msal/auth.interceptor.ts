import { inject } from '@angular/core';
import type { HttpInterceptorFn } from '@angular/common/http';
import { from, switchMap } from 'rxjs';
import { AuthService } from './auth.service';
import { environment } from './environment';

export const authInterceptor: HttpInterceptorFn = (request, next) => {
  const auth = inject(AuthService);

  if (!request.url.startsWith(`${environment.apiBaseUrl}/api/`)) {
    return next(request);
  }

  return from(auth.getAccessToken()).pipe(
    switchMap((accessToken) =>
      next(request.clone({
        setHeaders: {
          Authorization: `Bearer ${accessToken}`,
        },
      })),
    ),
  );
};
