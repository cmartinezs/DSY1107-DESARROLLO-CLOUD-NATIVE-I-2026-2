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
}
