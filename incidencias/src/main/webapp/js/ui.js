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

let handlerCrear = null, handlerAsignar = null, handlerConfirmar = null;

export function renderLista(incidencias, { mostrarEstado = true, estadoFiltro = null } = {}) {
  const tbody = document.getElementById('tbody-incidencias');
  const thEstado = document.getElementById('th-estado');
  const thTecnico = document.getElementById('th-tecnico');
  const thAcciones = document.getElementById('th-acciones');
  const mostrarAcciones = mostrarEstado || estadoFiltro !== 'RESUELTA';
  const mostrarTecnico = mostrarEstado || estadoFiltro !== 'PENDIENTE';
  if (thEstado) {
    thEstado.style.display = mostrarEstado ? '' : 'none';
  }
  if (thTecnico) {
    thTecnico.style.display = mostrarTecnico ? '' : 'none';
  }
  if (thAcciones) {
    thAcciones.style.display = mostrarAcciones ? '' : 'none';
  }
  if (!tbody) return;

  if (!incidencias || incidencias.length === 0) {
    const colspan = 4 + (mostrarEstado ? 1 : 0) + (mostrarTecnico ? 1 : 0) + (mostrarAcciones ? 1 : 0);
    tbody.innerHTML = `<tr class="empty-state"><td colspan="${colspan}">No hay incidencias</td></tr>`;
    return;
  }

  tbody.innerHTML = incidencias.map(item => {
    const resumen = item.dto;
    const id = resumen.id;
    const idCorto = truncarId(id);
    const fecha = formatearFecha(resumen.fecha);

    // Determinamos el botón de acción:
    // - Si mostrarEstado (vista "Todas"): por datos del backend (resumen.estado)
    // - Si no (otras vistas): por el filtro activo (estadoFiltro)
    let celdasAcciones = '';
    if (mostrarAcciones) {
      let accion = null;
      if (mostrarEstado) {
        const e = (resumen.estado || '').trim().toUpperCase();
        if (e === 'PENDIENTE') accion = 'asignar';
        else if (e === 'ASIGNADA') accion = 'resolver';
      } else if (estadoFiltro === 'PENDIENTE') {
        accion = 'asignar';
      } else if (estadoFiltro === 'ASIGNADA') {
        accion = 'resolver';
      }

      if (accion === 'asignar') {
        celdasAcciones = `<td class="acciones-cell"><button type="button" class="btn-accion btn-asignar" data-accion="asignar">Asignar</button></td>`;
      } else if (accion === 'resolver') {
        celdasAcciones = `<td class="acciones-cell"><button type="button" class="btn-accion btn-resolver" data-accion="resolver">Resolver</button></td>`;
      } else {
        celdasAcciones = '<td class="acciones-cell"><span class="ayuda" style="display:inline;">—</span></td>';
      }
    }

    // Celda de estado solo si mostrarEstado
    const celdaEstado = mostrarEstado
      ? `<td><span class="badge ${resumen.estado ? `badge-${resumen.estado.toLowerCase()}` : ''}">${resumen.estado ? resumen.estado.charAt(0) + resumen.estado.slice(1).toLowerCase() : '—'}</span></td>`
      : '';

    // Celda de ubicación (siempre visible)
    const celdaUbicacion = `<td class="ubicacion-cell" title="${escaparHtml(resumen.ubicacion || '')}">${escaparHtml(resumen.ubicacion || '—')}</td>`;

    // Celda de técnico solo si mostrarTecnico
    let celdaTecnico = '';
    if (mostrarTecnico) {
      const t = resumen.tecnico;
      const texto = (t && t.nombre)
        ? `${escaparHtml(t.nombre)}${t.telefono ? ` · ${escaparHtml(t.telefono)}` : ''}`
        : '—';
      celdaTecnico = `<td class="tecnico-cell">${texto}</td>`;
    }

    return `
      <tr data-id="${escaparHtml(id)}" data-estado="${escaparHtml(resumen.estado || '')}">
        <td class="id-cell" title="${escaparHtml(id)}">${idCorto}</td>
        <td class="desc-cell" title="${escaparHtml(resumen.descripcion)}">${escaparHtml(resumen.descripcion)}</td>
        ${celdaUbicacion}
        <td class="fecha-cell">${fecha}</td>
        ${celdaEstado}
        ${celdaTecnico}
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

  if (handlerCrear) form.removeEventListener('submit', handlerCrear);
  handlerCrear = (e) => {
    e.preventDefault();
    if (!form.checkValidity()) {
      form.reportValidity();
      return;
    }
    const descripcion = form.descripcion.value.trim();
    const ubicacion = form.ubicacion.value.trim();
    form.removeEventListener('submit', handlerCrear);
    onSubmit(descripcion, ubicacion);
  };
  form.addEventListener('submit', handlerCrear, { once: true });

  abrirModal('modal-crear');
}

export function abrirModalAsignar(id, onSubmit) {
  const form = document.getElementById('form-asignar');
  if (!form) return;
  form.reset();
  limpiarErroresForm(form);

  document.getElementById('asignar-id').value = id;

  if (handlerAsignar) form.removeEventListener('submit', handlerAsignar);
  handlerAsignar = (e) => {
    e.preventDefault();
    if (!form.checkValidity()) {
      form.reportValidity();
      return;
    }
    const nombre = form.nombre.value.trim();
    const telefono = form.telefono.value.trim();

    if (!/^\d{9}$/.test(telefono)) {
      mostrarErrorForm(form, 'telefono', 'El teléfono debe tener exactamente 9 dígitos numéricos');
      return;
    }

    form.removeEventListener('submit', handlerAsignar);
    onSubmit(nombre, telefono);
  };
  form.addEventListener('submit', handlerAsignar, { once: true });

  abrirModal('modal-asignar');
}

export function abrirModalConfirmar(mensaje, onConfirm) {
  const modal = document.getElementById('modal-confirmar');
  if (!modal) return;

  document.getElementById('modal-confirmar-mensaje').textContent = mensaje;

  const btnConfirmar = document.getElementById('btn-confirmar-accion');
  if (handlerConfirmar) btnConfirmar.removeEventListener('click', handlerConfirmar);
  handlerConfirmar = () => {
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