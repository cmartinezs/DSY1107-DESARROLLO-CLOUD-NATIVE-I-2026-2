import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { MsalService } from '@azure/msal-angular';
import { InteractionRequiredAuthError } from '@azure/msal-browser';
import { environment } from '../environments/environment';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule],
  template: `
    <main>
      <h1>EV1 · Referencia Angular</h1>
      <p>Usuario: {{ accountName() }}</p>
      <button (click)="login()">Login</button>
      <button (click)="logout()">Logout</button>
      <button (click)="publicCall()">GET público</button>
      <button (click)="protectedCall('/api/profile', 'GET')">GET autenticado</button>
      <button (click)="protectedCall('/api/write/example', 'POST')">POST scope write</button>
      <button (click)="protectedCall('/api/admin/example', 'GET')">GET rol admin</button>
      <pre>{{ output() }}</pre>
    </main>
  `,
})
export class AppComponent implements OnInit {
  readonly output = signal('Listo para probar.');
  readonly accountName = signal('no autenticado');

  constructor(private readonly msal: MsalService, private readonly http: HttpClient) {}

  async ngOnInit(): Promise<void> {
    await this.msal.instance.initialize();
    const result = await this.msal.instance.handleRedirectPromise();
    if (result?.account) this.msal.instance.setActiveAccount(result.account);
    if (!this.msal.instance.getActiveAccount()) {
      this.msal.instance.setActiveAccount(this.msal.instance.getAllAccounts()[0] ?? null);
    }
    this.accountName.set(this.account()?.username ?? 'no autenticado');
  }

  account() {
    return this.msal.instance.getActiveAccount() ?? this.msal.instance.getAllAccounts()[0] ?? null;
  }

  login(): void {
    this.msal.loginRedirect({ scopes: ['openid', 'profile', environment.auth.apiScope] }).subscribe();
  }

  logout(): void {
    this.msal.logoutRedirect({ account: this.account() ?? undefined }).subscribe();
  }

  async token(): Promise<string> {
    const account = this.account();
    if (!account) throw new Error('No hay usuario autenticado.');
    try {
      return (await firstValueFrom(this.msal.acquireTokenSilent({ account, scopes: [environment.auth.apiScope] }))).accessToken;
    } catch (error) {
      if (error instanceof InteractionRequiredAuthError) {
        this.msal.acquireTokenRedirect({ account, scopes: [environment.auth.apiScope] }).subscribe();
      }
      throw error;
    }
  }

  async publicCall(): Promise<void> {
    try {
      const response = await firstValueFrom(this.http.get(`${environment.apiBaseUrl}/public/health`, { observe: 'response', responseType: 'text' }));
      this.output.set(`Público: HTTP ${response.status}\n${response.body}`);
    } catch (error) {
      this.output.set(`Público: ${String(error)}`);
    }
  }

  async protectedCall(path: string, method: 'GET' | 'POST'): Promise<void> {
    try {
      const token = await this.token();
      const options = { headers: { Authorization: `Bearer ${token}` }, observe: 'response' as const, responseType: 'text' as const };
      const response = method === 'POST'
        ? await firstValueFrom(this.http.post(`${environment.apiBaseUrl}${path}`, {}, options))
        : await firstValueFrom(this.http.get(`${environment.apiBaseUrl}${path}`, options));
      this.output.set(`${method} ${path}: HTTP ${response.status}\n${response.body}`);
    } catch (error) {
      this.output.set(`${method} ${path}: ${String(error)}`);
    }
  }
}
