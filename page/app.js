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
