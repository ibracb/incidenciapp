const ICONOS = {
  exito: `<svg class="toast-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" aria-hidden="true"><polyline points="20 6 9 17 4 12"></polyline></svg>`,
  error: `<svg class="toast-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" aria-hidden="true"><circle cx="12" cy="12" r="10"></circle><line x1="15" y1="9" x2="9" y2="15"></line><line x1="9" y1="9" x2="15" y2="15"></line></svg>`,
  info: `<svg class="toast-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" aria-hidden="true"><circle cx="12" cy="12" r="10"></circle><line x1="12" y1="16" x2="12" y2="12"></line><line x1="12" y1="8" x2="12.01" y2="8"></line></svg>`
};

function escaparHtml(texto) {
  const div = document.createElement('div');
  div.textContent = texto;
  return div.innerHTML;
}

export function renderLista(incidencias, porFiltro = false) {
  const tbody = document.getElementById('tbody-incidencias');
  if (!tbody) return;

  if (!incidencias || incidencias.length === 0) {
    tbody.innerHTML = '<tr class="empty-state"><td colspan="5">No hay incidencias</td></tr>';
    return;
  }

  tbody.innerHTML = incidencias.map(item => {
    const resumen = item.resumen;
    const id = resumen.id;
    const idCorto = truncarId(id);
    const fecha = formatearFecha(resumen.fecha);

    // Si porFiltro es true, no insertamos botones en la celda ahora;
    // los añadiremos después desde app.js mediante delegación de eventos.
    // Si porFiltro es false (vista "Todas"), dejamos la celda vacía.
    const celdasAcciones = porFiltro
      ? '<td class="acciones-cell"><!-- accion por filtrar --></td>'
      : '<td class="acciones-cell"></td>';

    // Construimos el badge opcionalmente si el backend envía estado;
    // si no, mostramos "—"
    const estadoBadge = resumen.estado
      ? `badge-${resumen.estado.toLowerCase()}`
      : '';
    const estadoTexto = resumen.estado
      ? resumen.estado.charAt(0) + resumen.estado.slice(1).toLowerCase()
      : '—';

    return `
      <tr data-id="${escaparHtml(id)}" data-estado="${escaparHtml(resumen.estado || '')}">
        <td class="id-cell" title="${escaparHtml(id)}">${idCorto}</td>
        <td class="desc-cell" title="${escaparHtml(resumen.descripcion)}">${escaparHtml(resumen.descripcion)}</td>
        <td class="fecha-cell">${fecha}</td>
        <td><span class="badge ${estadoBadge}">${estadoTexto}</span></td>
        ${celdasAcciones}
      </tr>
    `;
  }).join('');
}

export function formatearFecha(iso) {
  if (!iso) return '—';
  try {
    const fecha = new Date(iso);
    if (isNaN(fecha.getTime())) return 'Fecha inválida';
    const dia = String(fecha.getDate()).padStart(2, '0');
    const mes = String(fecha.getMonth() + 1).padStart(2, '0');
    const anio = fecha.getFullYear();
    const horas = String(fecha.getHours()).padStart(2, '0');
    const minutos = String(fecha.getMinutes()).padStart(2, '0');
    return `${dia}/${mes}/${anio} ${horas}:${minutos}`;
  } catch {
    return 'Fecha inválida';
  }
}

export function truncarId(id, len = 8) {
  if (!id || id.length <= len) return id;
  return id.slice(0, len) + '…';
}

export function abrirModal(id) {
  const modal = document.getElementById(id);
  if (!modal) return;

  modal.hidden = false;
  document.body.style.overflow = 'hidden';

  const focusable = modal.querySelector('button, [href], input, select, textarea, [tabindex]:not([tabindex="-1"])');
  if (focusable) focusable.focus();

  modal._ultimoFoco = document.activeElement;

  modal.addEventListener('keydown', atraparFoco);
  modal.addEventListener('click', cerrarPorOverlay);
}

function atraparFoco(e) {
  if (e.key !== 'Tab') return;
  const modal = e.currentTarget;
  const focusables = modal.querySelectorAll('button, [href], input, select, textarea, [tabindex]:not([tabindex="-1"])');
  const primer = focusables[0];
  const ultimo = focusables[focusables.length - 1];

  if (e.shiftKey && document.activeElement === primer) {
    e.preventDefault();
    ultimo.focus();
  } else if (!e.shiftKey && document.activeElement === ultimo) {
    e.preventDefault();
    primer.focus();
  }
}

function cerrarPorOverlay(e) {
  if (e.target === e.currentTarget) {
    const id = e.currentTarget.id;
    cerrarModal(id);
  }
}

export function cerrarModal(id) {
  const modal = document.getElementById(id);
  if (!modal) return;

  modal.hidden = true;
  document.body.style.overflow = '';

  modal.removeEventListener('keydown', atraparFoco);
  modal.removeEventListener('click', cerrarPorOverlay);

  if (modal._ultimoFoco) modal._ultimoFoco.focus();
}

export function cerrarTodosModales() {
  document.querySelectorAll('.modal-overlay:not([hidden])').forEach(m => cerrarModal(m.id));
}

export function abrirModalCrear(onSubmit) {
  const form = document.getElementById('form-crear');
  if (!form) return;
  form.reset();
  limpiarErroresForm(form);

  const handler = (e) => {
    e.preventDefault();
    if (!form.checkValidity()) {
      form.reportValidity();
      return;
    }
    const descripcion = form.descripcion.value.trim();
    const ubicacion = form.ubicacion.value.trim();
    form.removeEventListener('submit', handler);
    onSubmit(descripcion, ubicacion);
  };
  form.addEventListener('submit', handler, { once: true });

  abrirModal('modal-crear');
}

export function abrirModalAsignar(id, onSubmit) {
  const form = document.getElementById('form-asignar');
  if (!form) return;
  form.reset();
  limpiarErroresForm(form);

  document.getElementById('asignar-id').value = id;

  const handler = (e) => {
    e.preventDefault();
    if (!form.checkValidity()) {
      form.reportValidity();
      return;
    }
    const nombreTecnico = form.nombreTecnico.value.trim();
    const telefonoTecnico = form.telefonoTecnico.value.trim();

    if (!/^\d{9}$/.test(telefonoTecnico)) {
      mostrarErrorForm(form, 'telefonoTecnico', 'El teléfono debe tener exactamente 9 dígitos numéricos');
      return;
    }

    form.removeEventListener('submit', handler);
    onSubmit(nombreTecnico, telefonoTecnico);
  };
  form.addEventListener('submit', handler, { once: true });

  abrirModal('modal-asignar');
}

export function abrirModalConfirmar(mensaje, onConfirm) {
  const modal = document.getElementById('modal-confirmar');
  if (!modal) return;

  document.getElementById('modal-confirmar-mensaje').textContent = mensaje;

  const btnConfirmar = document.getElementById('btn-confirmar-accion');
  const handlerConfirmar = () => {
    btnConfirmar.removeEventListener('click', handlerConfirmar);
    cerrarModal('modal-confirmar');
    onConfirm();
  };
  btnConfirmar.addEventListener('click', handlerConfirmar, { once: true });

  abrirModal('modal-confirmar');
}

function limpiarErroresForm(form) {
  form.querySelectorAll('.error-form').forEach(el => el.remove());
  form.querySelectorAll('input, textarea').forEach(el => el.style.borderColor = '');
}

function mostrarErrorForm(form, campoNombre, mensaje) {
  const campo = form.querySelector(`[name="${campoNombre}"]`);
  if (!campo) return;

  limpiarErroresForm(form);

  campo.style.borderColor = 'var(--color-error)';
  const error = document.createElement('p');
  error.className = 'error-form';
  error.style.cssText = 'color: var(--color-error); font-size: 0.75rem; margin: 4px 0 0;';
  error.textContent = mensaje;
  campo.parentNode.appendChild(error);
  campo.focus();
}

export function mostrarToast(mensaje, tipo = 'info') {
  const contenedor = document.getElementById('toast-container');
  if (!contenedor) return;

  const toast = document.createElement('div');
  toast.className = `toast toast-${tipo}`;
  toast.innerHTML = `
    ${ICONOS[tipo] || ICONOS.info}
    <span class="toast-mensaje">${escaparHtml(mensaje)}</span>
    <button type="button" class="toast-cerrar" aria-label="Cerrar">
      <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" aria-hidden="true"><line x1="18" y1="6" x2="6" y2="18"></line><line x1="6" y1="6" x2="18" y2="18"></line></svg>
    </button>
  `;

  toast.querySelector('.toast-cerrar').addEventListener('click', () => cerrarToast(toast));

  contenedor.appendChild(toast);

  setTimeout(() => cerrarToast(toast), 5000);
}

function cerrarToast(toast) {
  toast.classList.add('salida');
  toast.addEventListener('animationend', () => toast.remove());
}

export function setLoading(btn, loading) {
  if (!btn) return;
  if (loading) {
    btn.disabled = true;
    btn.dataset.textoOriginal = btn.innerHTML;
    btn.innerHTML = '<span class="spinner" aria-hidden="true"></span> Cargando...';
  } else {
    btn.disabled = false;
    btn.innerHTML = btn.dataset.textoOriginal || btn.innerHTML;
    delete btn.dataset.textoOriginal;
  }
}