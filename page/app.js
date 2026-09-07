const button = document.querySelector('#copy-button');
const command = document.querySelector('#clone-command');

button?.addEventListener('click', async () => {
  try {
    await navigator.clipboard.writeText(command.textContent.trim());
    button.textContent = 'Copiado';
    setTimeout(() => { button.textContent = 'Copiar'; }, 1600);
  } catch {
    button.textContent = 'Selecciona y copia';
  }
});

const currentWeek = document.querySelector('.current-week');
if (currentWeek) {
  const week5 = document.createElement('section');
  week5.className = 'card';
  week5.innerHTML = `
    <p class="eyebrow">Semana 5 · cierre técnico EA1</p>
    <h2>Frontend + IDaaS + API Gateway + microservicio protegido</h2>
    <p>La prioridad de esta semana es demostrar el flujo seguro de extremo a extremo. No se prioriza ampliar CRUD ni reglas de negocio mientras autenticación, token, Gateway y backend no estén cerrados.</p>
    <p><strong>Ruta objetivo:</strong> usuario → SPA → IDaaS → access token para API propia → API Gateway/API Manager → microservicio → 401/403/2xx.</p>
    <p>
      <a class="text-link" href="https://github.com/cmartinezs/DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2/blob/master/semanas/semana-05/README.md">Abrir Semana 5 →</a>
      &nbsp;&nbsp;·&nbsp;&nbsp;
      <a class="text-link" href="https://github.com/cmartinezs/DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2/tree/master/labs/fullstack-seguro">Laboratorio Full Stack →</a>
      &nbsp;&nbsp;·&nbsp;&nbsp;
      <a class="text-link" href="https://github.com/cmartinezs/DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2/blob/master/proyecto-formativo/semana-05/README.md">RegistrApp Semana 5 →</a>
    </p>
  `;
  currentWeek.appendChild(week5);

  const params = new URLSearchParams(window.location.search);
  const selectedSection = params.get('seccion');

  const selector = document.createElement('section');
  selector.className = 'card';
  selector.innerHTML = `
    <p class="eyebrow">Continuidad por evidencia</p>
    <h2>002D y 003D parten desde su último gate verde</h2>
    <p>La planificación anterior no equivale a ejecución. El checkpoint de Semana 4 se conserva para identificar deuda real antes de avanzar al Gateway.</p>
    <p>
      <a class="text-link" href="?seccion=002D">Ver DSY1107-002D →</a>
      &nbsp;&nbsp;·&nbsp;&nbsp;
      <a class="text-link" href="?seccion=003D">Ver DSY1107-003D →</a>
      &nbsp;&nbsp;·&nbsp;&nbsp;
      <a class="text-link" href="https://github.com/cmartinezs/DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2/blob/master/semanas/semana-05/00-entrada-desde-semana-04.md">Checkpoint de entrada →</a>
    </p>
  `;

  const detail = document.createElement('div');

  if (selectedSection === '002D') {
    detail.innerHTML = `
      <hr />
      <p class="eyebrow">DSY1107-002D</p>
      <h2>Ruta mínima: autenticación → token → Gateway → API</h2>
      <p>Priorizar el cierre del flujo y la Evaluación Formativa 1. No forzar proveedores o capas adicionales si Email/Password o el access token siguen pendientes.</p>
      <a class="text-link" href="https://github.com/cmartinezs/DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2/blob/master/semanas/semana-05/DSY1107-002D.md">Abrir plan 002D →</a>
    `;
  }

  if (selectedSection === '003D') {
    detail.innerHTML = `
      <hr />
      <p class="eyebrow">DSY1107-003D</p>
      <h2>Ruta completa: Entra/MSAL → Gateway → Spring Security</h2>
      <p>Verificar primero el estado real de Firebase/Entra y del access token para API propia. Luego cerrar JWT Authorizer, backend protegido, pruebas 401/403/2xx y transferencia incremental a RegistrApp.</p>
      <a class="text-link" href="https://github.com/cmartinezs/DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2/blob/master/semanas/semana-05/DSY1107-003D.md">Abrir plan 003D →</a>
    `;
  }

  selector.appendChild(detail);
  currentWeek.insertAdjacentElement('afterend', selector);
}