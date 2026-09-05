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
  const lab = document.createElement('p');
  lab.innerHTML = '<strong>Laboratorio Full Stack seguro:</strong> recorrido provider-backed por etapas con dos App Registrations, MSAL + PKCE, access token para API propia, JWT Authorizer, Spring Security, audience explícita y matriz 401/403/2xx. <a class="text-link" href="https://github.com/cmartinezs/DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2/tree/master/labs/fullstack-seguro">Abrir laboratorio →</a>';
  currentWeek.appendChild(lab);

  const params = new URLSearchParams(window.location.search);
  const selectedSection = params.get('seccion');

  const selector = document.createElement('section');
  selector.className = 'card';
  selector.innerHTML = `
    <p class="eyebrow">Avance real por sección</p>
    <h2>Selecciona tu sección</h2>
    <p>El horizonte curricular de Semana 4 es común, pero 002D y 003D deben continuar desde su último checkpoint demostrable. Que un contenido esté publicado no significa que ya se haya ejecutado en aula.</p>
    <p>
      <a class="text-link" href="?seccion=002D">Ver DSY1107-002D →</a>
      &nbsp;&nbsp;·&nbsp;&nbsp;
      <a class="text-link" href="?seccion=003D">Ver DSY1107-003D →</a>
    </p>
  `;

  const detail = document.createElement('div');

  if (selectedSection === '002D') {
    detail.innerHTML = `
      <hr />
      <p class="eyebrow">DSY1107-002D</p>
      <h2>Semana 4 · planificación</h2>
      <p>Consulta el plan de la sección y actualízalo únicamente con evidencia real de clase.</p>
      <a class="text-link" href="https://github.com/cmartinezs/DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2/blob/master/semanas/semana-04/DSY1107-002D.md">Abrir planificación Semana 4 →</a>
    `;
  }

  if (selectedSection === '003D') {
    detail.innerHTML = `
      <hr />
      <p class="eyebrow">DSY1107-003D</p>
      <h2>Semana 4 · planificación</h2>
      <p>Consulta el plan de la sección y actualízalo únicamente con evidencia real de clase.</p>
      <a class="text-link" href="https://github.com/cmartinezs/DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2/blob/master/semanas/semana-04/DSY1107-003D.md">Abrir planificación Semana 4 →</a>
    `;
  }

  selector.appendChild(detail);
  currentWeek.insertAdjacentElement('afterend', selector);
}
