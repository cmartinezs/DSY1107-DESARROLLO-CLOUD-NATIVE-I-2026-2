# 03A · Starter reproducible Angular + MSAL Angular

## Objetivo

Usar el wrapper oficial de Angular para MSAL y reducir al mínimo el código incidental. El estudiante configura valores reales y observa el flujo; **no implementa OAuth2, OIDC ni PKCE manualmente**.

MSAL Angular utiliza Authorization Code Flow con PKCE y ofrece `MsalInterceptor` para adquirir Access Tokens y adjuntarlos a requests `HttpClient` dirigidas a recursos protegidos conocidos.

## 0. Comprobar compatibilidad

```bash
ng version
```

Referencia vigente:

```text
Angular 22      → MSAL Angular 6
Angular 19–21   → MSAL Angular 5
```

Angular 22:

```bash
npm install @azure/msal-angular@^6 @azure/msal-browser@^5
```

Angular 19–21:

```bash
npm install @azure/msal-angular@^5 @azure/msal-browser@^5
```

No ignorar un conflicto de peer dependencies instalando versiones al azar.

**CHECKPOINT 03A-0**

```bash
npm start
```

Angular debe seguir compilando antes de configurar identidad.

---

# 1. Archivos mínimos

```text
src/app/
├── auth-config.ts
├── api.service.ts
├── app.config.ts
├── app.component.ts
└── app.component.html
```

No se crea un `AuthService` propio: `MsalService` y `MsalInterceptor` ya resuelven esa infraestructura.

---

# 2. `auth-config.ts`

```ts
export const authConfig = {
  clientId: '<SPA_CLIENT_ID>',
  authority: '<MSAL_AUTHORITY>',
  redirectUri: window.location.origin,
  postLogoutRedirectUri: window.location.origin
};

export const apiConfig = {
  baseUrl: 'http://localhost:8080',
  readScope: '<SCOPE_READ>',
  writeScope: '<SCOPE_WRITE>'
};
```

`SCOPE_READ` y `SCOPE_WRITE` son los scopes **completos** obtenidos desde Entra, por ejemplo:

```text
api://<API_CLIENT_ID>/tasks.read
api://<API_CLIENT_ID>/tasks.write
```

No existe `client_secret` en una SPA.

---

# 3. `app.config.ts`

```ts
import { ApplicationConfig } from '@angular/core';
import {
  HTTP_INTERCEPTORS,
  provideHttpClient,
  withInterceptorsFromDi
} from '@angular/common/http';
import {
  BrowserCacheLocation,
  InteractionType,
  IPublicClientApplication,
  PublicClientApplication
} from '@azure/msal-browser';
import {
  MsalBroadcastService,
  MsalInterceptor,
  MsalInterceptorConfiguration,
  MsalService,
  MSAL_INSTANCE,
  MSAL_INTERCEPTOR_CONFIG,
  ProtectedResourceScopes
} from '@azure/msal-angular';
import { authConfig, apiConfig } from './auth-config';

export function msalInstanceFactory(): IPublicClientApplication {
  return new PublicClientApplication({
    auth: {
      clientId: authConfig.clientId,
      authority: authConfig.authority,
      redirectUri: authConfig.redirectUri,
      postLogoutRedirectUri: authConfig.postLogoutRedirectUri
    },
    cache: {
      cacheLocation: BrowserCacheLocation.SessionStorage
    },
    system: {
      allowPlatformBroker: false
    }
  });
}

export function msalInterceptorConfigFactory(): MsalInterceptorConfiguration {
  const read: ProtectedResourceScopes = {
    httpMethod: 'GET',
    scopes: [apiConfig.readScope]
  };

  const writePost: ProtectedResourceScopes = {
    httpMethod: 'POST',
    scopes: [apiConfig.writeScope]
  };

  const writeDelete: ProtectedResourceScopes = {
    httpMethod: 'DELETE',
    scopes: [apiConfig.writeScope]
  };

  return {
    interactionType: InteractionType.Redirect,
    protectedResourceMap: new Map([
      [`${apiConfig.baseUrl}/api/me`, [apiConfig.readScope]],
      [`${apiConfig.baseUrl}/api/tasks`, [read, writePost]],
      [`${apiConfig.baseUrl}/api/tasks/*`, [writeDelete]]
    ])
  };
}

export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(withInterceptorsFromDi()),
    {
      provide: HTTP_INTERCEPTORS,
      useClass: MsalInterceptor,
      multi: true
    },
    {
      provide: MSAL_INSTANCE,
      useFactory: msalInstanceFactory
    },
    {
      provide: MSAL_INTERCEPTOR_CONFIG,
      useFactory: msalInterceptorConfigFactory
    },
    MsalService,
    MsalBroadcastService
  ]
};
```

Si el archivo generado ya contiene `provideRouter(routes)` u otros providers válidos, **conservarlos** y agregar estos providers.

### Qué hace el interceptor

```text
HttpClient llama recurso conocido
→ MsalInterceptor identifica URL + método
→ obtiene/reutiliza Access Token con scope correspondiente
→ agrega Authorization: Bearer ...
→ request sale hacia API
```

`/api/public/health` no aparece en `protectedResourceMap`, por lo que continúa público.

---

# 4. `api.service.ts`

```ts
import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { apiConfig } from './auth-config';

export interface MeResponse {
  subject: string;
  issuer: string;
  audience: string[];
  scopes: string[];
  roles: string[];
}

export interface Task {
  id: number;
  title: string;
  ownerId: string;
}

@Injectable({ providedIn: 'root' })
export class ApiService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = apiConfig.baseUrl;

  health() {
    return this.http.get(`${this.baseUrl}/api/public/health`);
  }

  me(): Observable<MeResponse> {
    return this.http.get<MeResponse>(`${this.baseUrl}/api/me`);
  }

  tasks(): Observable<Task[]> {
    return this.http.get<Task[]>(`${this.baseUrl}/api/tasks`);
  }

  createTask(title: string): Observable<Task> {
    return this.http.post<Task>(`${this.baseUrl}/api/tasks`, { title });
  }

  deleteTask(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/api/tasks/${id}`);
  }
}
```

El servicio **no construye manualmente el header Bearer**: esa es la responsabilidad del interceptor.

---

# 5. `app.component.ts`

```ts
import { JsonPipe } from '@angular/common';
import { Component, DestroyRef, inject, OnInit, signal } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { MsalBroadcastService, MsalService } from '@azure/msal-angular';
import { InteractionStatus } from '@azure/msal-browser';
import { filter } from 'rxjs/operators';
import { ApiService, MeResponse, Task } from './api.service';
import { apiConfig } from './auth-config';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [JsonPipe],
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {
  private readonly destroyRef = inject(DestroyRef);
  private readonly msal = inject(MsalService);
  private readonly broadcast = inject(MsalBroadcastService);
  private readonly api = inject(ApiService);

  readonly loggedIn = signal(false);
  readonly status = signal('listo');
  readonly me = signal<MeResponse | null>(null);
  readonly tasks = signal<Task[]>([]);

  ngOnInit(): void {
    this.msal.handleRedirectObservable().subscribe();

    this.broadcast.inProgress$
      .pipe(
        filter(status => status === InteractionStatus.None),
        takeUntilDestroyed(this.destroyRef)
      )
      .subscribe(() => {
        const current = this.msal.instance.getActiveAccount();
        const accounts = this.msal.instance.getAllAccounts();

        if (!current && accounts.length > 0) {
          this.msal.instance.setActiveAccount(accounts[0]);
        }

        this.loggedIn.set(this.msal.instance.getActiveAccount() !== null);
      });
  }

  login(): void {
    this.msal.loginRedirect({
      scopes: [apiConfig.readScope, apiConfig.writeScope]
    });
  }

  logout(): void {
    this.msal.logoutRedirect({
      postLogoutRedirectUri: window.location.origin
    });
  }

  cargarIdentidad(): void {
    this.api.me().subscribe({
      next: value => this.me.set(value),
      error: error => this.mostrarError('identidad', error)
    });
  }

  cargarTareas(): void {
    this.api.tasks().subscribe({
      next: value => this.tasks.set(value),
      error: error => this.mostrarError('tareas', error)
    });
  }

  crearTarea(title: string): void {
    const value = title.trim();
    if (!value) return;

    this.api.createTask(value).subscribe({
      next: () => {
        this.status.set('tarea creada');
        this.cargarTareas();
      },
      error: error => this.mostrarError('crear tarea', error)
    });
  }

  eliminarTarea(id: number): void {
    this.api.deleteTask(id).subscribe({
      next: () => {
        this.status.set('tarea eliminada');
        this.cargarTareas();
      },
      error: error => this.mostrarError('eliminar tarea', error)
    });
  }

  private mostrarError(contexto: string, error: unknown): void {
    console.error(`Error en ${contexto}`, error);
    this.status.set(`ERROR: ${contexto}`);
  }
}
```

---

# 6. `app.component.html`

```html
<main>
  <h1>CloudTasks</h1>

  @if (!loggedIn()) {
    <button type="button" (click)="login()">Iniciar sesión</button>
  } @else {
    <button type="button" (click)="logout()">Cerrar sesión</button>

    <hr>

    <button type="button" (click)="cargarIdentidad()">Mi identidad</button>
    <button type="button" (click)="cargarTareas()">Recargar tareas</button>

    <div>
      <input #title type="text" placeholder="Título de tarea">
      <button type="button" (click)="crearTarea(title.value); title.value = ''">Crear</button>
    </div>

    @if (me(); as identity) {
      <pre>{{ identity | json }}</pre>
    }

    <ul>
      @for (task of tasks(); track task.id) {
        <li>
          {{ task.title }}
          <button type="button" (click)="eliminarTarea(task.id)">Eliminar</button>
        </li>
      }
    </ul>
  }

  <p>Estado: {{ status() }}</p>
</main>
```

---

# 7. Validar por capas

## CHECKPOINT 03A-1 · compilación

```bash
npm start
```

- [ ] TypeScript compila.
- [ ] no hay `NullInjectorError`.
- [ ] no hay error de providers MSAL.
- [ ] frontend abre en `http://localhost:4200`.

No intentar login mientras este checkpoint esté rojo.

## CHECKPOINT 03A-2 · autenticación

```text
Iniciar sesión
→ <TENANT_SUBDOMAIN>.ciamlogin.com
→ user flow
→ Authorization Code + PKCE gestionado por MSAL
→ retorno a http://localhost:4200
→ active account
```

- [ ] authority correcta.
- [ ] redirect URI exacta.
- [ ] no existe client secret.
- [ ] logout funciona.

## CHECKPOINT 03A-3 · Access Token

Presionar **Mi identidad** o **Recargar tareas** y observar DevTools → Network.

Debe existir:

```http
Authorization: Bearer <ACCESS_TOKEN>
```

Claims esperados, revisados de forma temporal/sanitizada:

```text
aud → API_AUDIENCE
iss → OIDC_ISSUER
scp → permiso solicitado
```

No guardar el token completo.

## Método → scope

| Request | Scope |
|---|---|
| `GET /api/me` | `SCOPE_READ` |
| `GET /api/tasks` | `SCOPE_READ` |
| `POST /api/tasks` | `SCOPE_WRITE` |
| `DELETE /api/tasks/{id}` | `SCOPE_WRITE` |
| `GET /api/public/health` | ninguno |

---

# 8. ID Token vs Access Token

```text
ID Token     → sesión/identidad de la SPA
Access Token → autorización frente a CloudTasks API
```

El interceptor usa el **Access Token** para el API.

---

# 9. SI FALLA

| Síntoma | Revisar primero |
|---|---|
| `ERESOLVE` npm | Angular vs MSAL Angular |
| redirect incorrecto | authority + redirect registrada |
| login correcto, API sin token | `protectedResourceMap` + URL exacta/wildcard |
| GET funciona, POST no | write scope + consent + método |
| `aud` incorrecto | scope completo solicitado |
| 401 | token/iss/aud/exp; no CORS |
| 403 | scope/ownership |
| CORS | origin/preflight; no cambiar MSAL |
| `interaction_in_progress` | evitar dos redirects simultáneos |

## Puerta de validación 03A

```text
compilación PASS
login PASS
active account PASS
GET /api/me con Bearer PASS
GET /api/tasks con read scope PASS
POST /api/tasks con write scope PASS
ID Token no usado como Bearer PASS
sin client secret PASS
```

## Qué debe explicar

```text
clientId
authority
redirect URI
Authorization Code
PKCE
scope
Access Token
protectedResourceMap
ID Token vs Access Token
```

No necesita explicar el código interno de MSAL.
