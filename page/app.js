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
  lab.innerHTML = '<strong>Laboratorio puente:</strong> API Gateway local con Spring Cloud Gateway · routing, HTTP nivel 2, versionado, CORS y colaboración GitHub. <a class="text-link" href="https://github.com/cmartinezs/DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2/tree/master/labs/api-gateway-local">Abrir guía y starter →</a>';
  currentWeek.appendChild(lab);

  const params = new URLSearchParams(window.location.search);
  const selectedSection = params.get('seccion');

  const selector = document.createElement('section');
  selector.className = 'card';
  selector.innerHTML = `
    <p class="eyebrow">Avance real por sección</p>
    <h2>Selecciona tu sección</h2>
    <p>Los resúmenes reflejan lo que efectivamente se alcanzó en cada curso. El material común sigue siendo el mismo para ambas secciones.</p>
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
      <h2>Resumen de la sección</h2>
      <p>Consulta únicamente los registros de avance de la sección 002D.</p>
      <a class="text-link" href="https://github.com/cmartinezs/DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2/blob/master/semanas/semana-02/DSY1107-002D.md">Abrir planificación/avance de Semana 2 →</a>
    `;
  }

  if (selectedSection === '003D') {
    detail.innerHTML = `
      <hr />
      <p class="eyebrow">DSY1107-003D</p>
      <h2>API Gateway + OAuth2/OIDC</h2>
      <p>La sección mantiene su propio checkpoint real. Consulta el registro de Semana 2 para distinguir lo ya trabajado de lo planificado.</p>
      <p>
        <a class="text-link" href="https://github.com/cmartinezs/DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2/blob/master/semanas/semana-02/DSY1107-003D.md">Abrir avance de Semana 2 →</a><br />
        <a class="text-link" href="https://github.com/cmartinezs/DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2/tree/master/labs/api-gateway-local">Continuar laboratorio API Gateway →</a><br />
        <a class="text-link" href="https://github.com/cmartinezs/DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2/tree/master/labs/identidad-local">Abrir laboratorio de identidad →</a>
      </p>
    `;
  }

  selector.appendChild(detail);
  currentWeek.insertAdjacentElement('afterend', selector);
}
