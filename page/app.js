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
  lab.innerHTML = '<strong>Laboratorio puente:</strong> API Gateway local con Spring Cloud Gateway · routing, HTTP nivel 2, versionado, CORS y colaboración GitHub. <a class="text-link" href="https://github.com/cmartinezs/DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2/tree/master/semanas/semana-01/laboratorio-api-gateway">Abrir guía y starter →</a>';
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
      <a class="text-link" href="https://github.com/cmartinezs/DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2/blob/master/semanas/semana-01/secciones/002D/resumen-2026-08-11.md">Abrir resumen del 11 de agosto →</a>
    `;
  }

  if (selectedSection === '003D') {
    detail.innerHTML = `
      <hr />
      <p class="eyebrow">DSY1107-003D · 14 de agosto</p>
      <h2>API REST, HTTP, Gateway y versionamiento</h2>
      <p>Se revisaron recursos y paths, métodos GET/POST/PUT/PATCH/DELETE, query parameters, diferencia PUT/PATCH, API vs API Gateway vs API Management, funciones transversales del gateway y versionamiento SemVer frente a versiones de contrato como /v1 y /v2.</p>
      <p><strong>Estado actual:</strong> laboratorio de API Gateway con Spring Cloud Gateway en curso. El diagnóstico técnico continúa pendiente.</p>
      <p>
        <a class="text-link" href="https://github.com/cmartinezs/DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2/blob/master/semanas/semana-01/secciones/003D/resumen-2026-08-14.md">Abrir resumen de la clase →</a><br />
        <a class="text-link" href="https://github.com/cmartinezs/DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2/tree/master/semanas/semana-01/laboratorio-api-gateway">Continuar laboratorio API Gateway →</a>
      </p>
    `;
  }

  selector.appendChild(detail);
  currentWeek.insertAdjacentElement('afterend', selector);
}
