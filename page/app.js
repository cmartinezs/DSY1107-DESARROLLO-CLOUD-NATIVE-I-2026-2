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
  const week8 = document.createElement('section');
  week8.className = 'card';
  week8.innerHTML = `
    <p class="eyebrow">Semana 8 · Experiencia de aprendizaje 2</p>
    <h2>Mensajería asíncrona y RabbitMQ</h2>
    <p>Partimos desde el problema de acoplamiento: cuándo una capacidad puede ejecutarse sin bloquear al componente que origina el trabajo. Luego construimos el flujo Producer → Exchange → Binding → Queue → Consumer.</p>
    <p><strong>Ruta de la semana:</strong> síncrono vs asíncrono → RabbitMQ con Docker → Hello World con Spring AMQP → DirectExchange + routing keys → ejercicio → laboratorio → transferencia formativa.</p>
    <p>
      <a class="text-link" href="https://github.com/cmartinezs/DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2/blob/master/semanas/semana-08/README.md">Abrir Semana 8 →</a>
      &nbsp;&nbsp;·&nbsp;&nbsp;
      <a class="text-link" href="https://github.com/cmartinezs/DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2/tree/master/examples/semana-08">Ejemplo →</a>
      &nbsp;&nbsp;·&nbsp;&nbsp;
      <a class="text-link" href="https://github.com/cmartinezs/DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2/tree/master/labs/rabbitmq-spring-amqp">Laboratorio →</a>
      &nbsp;&nbsp;·&nbsp;&nbsp;
      <a class="text-link" href="https://github.com/cmartinezs/DSY1107-DESARROLLO-CLOUD-NATIVE-I-2026-2/blob/master/proyecto-formativo/semana-08/README.md">RegistrApp Semana 8 →</a>
    </p>
  `;
  currentWeek.appendChild(week8);

  const note = document.createElement('section');
  note.className = 'card';
  note.innerHTML = `
    <p class="eyebrow">Regla pedagógica</p>
    <h2>La capacidad manda; REST y RabbitMQ son adaptadores</h2>
    <p>Una misma capacidad puede activarse desde un endpoint REST o desde un Rabbit Listener. La lógica de negocio no se duplica ni queda mezclada con la configuración del broker.</p>
    <p>Los resúmenes particulares de 002D y 003D se publican después de cada sesión según el avance real de la sección.</p>
  `;
  currentWeek.insertAdjacentElement('afterend', note);
}
