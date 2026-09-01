import { PropsWithChildren } from 'react';
import { useAuth } from './useAuth';

export function ProtectedRoute({ children }: PropsWithChildren) {
  const { isAuthenticated, login } = useAuth();

  if (!isAuthenticated) {
    return (
      <section>
        <p>Debes iniciar sesión para acceder a este contenido.</p>
        <button type="button" onClick={() => void login()}>
          Iniciar sesión
        </button>
      </section>
    );
  }

  return <>{children}</>;
}
