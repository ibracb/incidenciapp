import * as api from './api.js';
import * as ui from './ui.js';

let estadoActual = null;

async function cargar() {
  try {
    const mostrarEstado = estadoActual === null;
    const mostrarAcciones = mostrarEstado || estadoActual !== 'RESUELTA';
    const mostrarTecnico = mostrarEstado || estadoActual !== 'PENDIENTE';
    mostrarEsqueletos(mostrarEstado, mostrarAcciones, mostrarTecnico);
    const data = await api.listarIncidencias(estadoActual);
    ui.renderLista(data, { mostrarEstado, estadoFiltro: estadoActual });
  } catch (err) {
    ui.mostrarToast(err.message, 'error');
    const mostrarEstado = estadoActual === null;
    const mostrarAcciones = mostrarEstado || estadoActual !== 'RESUELTA';
    const mostrarTecnico = mostrarEstado || estadoActual !== 'PENDIENTE';
    const thEstado = document.getElementById('th-estado');
    if (thEstado) thEstado.style.display = mostrarEstado ? '' : 'none';
    const thTecnico = document.getElementById('th-tecnico');
    if (thTecnico) thTecnico.style.display = mostrarTecnico ? '' : 'none';
    const thAcciones = document.getElementById('th-acciones');
    if (thAcciones) thAcciones.style.display = mostrarAcciones ? '' : 'none';
    const tbody = document.getElementById('tbody-incidencias');
    if (tbody) {
      const colspan = 4 + (mostrarEstado ? 1 : 0) + (mostrarTecnico ? 1 : 0) + (mostrarAcciones ? 1 : 0);
      tbody.innerHTML = `<tr class="empty-state"><td colspan="${colspan}">Error al cargar incidencias</td></tr>`;
    }
  }
}

function mostrarEsqueletos(mostrarEstado = true, mostrarAcciones = true, mostrarTecnico = true) {
  const thEstado = document.getElementById('th-estado');
  if (thEstado) thEstado.style.display = mostrarEstado ? '' : 'none';
  const thTecnico = document.getElementById('th-tecnico');
  if (thTecnico) thTecnico.style.display = mostrarTecnico ? '' : 'none';
  const thAcciones = document.getElementById('th-acciones');
  if (thAcciones) thAcciones.style.display = mostrarAcciones ? '' : 'none';
  const tbody = document.getElementById('tbody-incidencias');
  if (!tbody) return;
  const celdaEstado = mostrarEstado ? '<td><div class="skeleton skeleton-badge"></div></td>' : '';
  const celdaUbicacion = '<td><div class="skeleton skeleton-desc"></div></td>';
  const celdaTecnico = mostrarTecnico ? '<td><div class="skeleton skeleton-desc"></div></td>' : '';
  const celdaAcciones = mostrarAcciones ? '<td></td>' : '';
  tbody.innerHTML = Array(5).fill(0).map(() => `
    <tr class="skeleton-row">
      <td><div class="skeleton skeleton-id"></div></td>
      <td><div class="skeleton skeleton-desc"></div></td>
      ${celdaUbicacion}
      <td><div class="skeleton skeleton-fecha"></div></td>
      ${celdaEstado}
      ${celdaTecnico}
      ${celdaAcciones}
    </tr>
  `).join('');
}

document.querySelectorAll('.tab-btn').forEach(btn => {
  btn.addEventListener('click', () => {
    document.querySelectorAll('.tab-btn').forEach(b => {
      b.classList.remove('activo');
      b.setAttribute('aria-selected', 'false');
    });
    btn.classList.add('activo');
    btn.setAttribute('aria-selected', 'true');
    estadoActual = btn.dataset.estado || null;
    cargar();
  });
});

document.getElementById('btn-nueva').addEventListener('click', () => {
  ui.abrirModalCrear(async (descripcion, ubicacion) => {
    const btn = document.querySelector('#modal-crear .btn-primary');
    ui.setLoading(btn, true);
    try {
      await api.crearIncidencia(descripcion, ubicacion);
      ui.cerrarModal('modal-crear');
      ui.mostrarToast('Incidencia creada correctamente', 'success');
      cargar();
    } catch (err) {
      ui.mostrarToast(err.message, 'error');
    } finally {
      ui.setLoading(btn, false);
    }
  });
});

document.getElementById('tbody-incidencias').addEventListener('click', async (e) => {
  const btnAccion = e.target.closest('.btn-accion');
  if (!btnAccion) return; // el clic no fue sobre un botón de acción

  const fila = btnAccion.closest('tr');
  if (!fila) return;
  const id = fila.dataset.id;
  const accion = btnAccion.dataset.accion; // 'asignar' o 'resolver'

  if (accion === 'asignar') {
    ui.abrirModalAsignar(id, async (nombre, telefono) => {
      const btn = document.querySelector('#modal-asignar .btn-primary');
      ui.setLoading(btn, true);
      try {
        await api.asignarTecnicoIncidencia(id, nombre, telefono);
        ui.cerrarModal('modal-asignar');
        ui.mostrarToast('Incidencia asignada correctamente', 'success');
        cargar();
      } catch (err) {
        ui.mostrarToast(err.message, 'error');
      } finally {
        ui.setLoading(btn, false);
      }
    });
  } else if (accion === 'resolver') {
    ui.abrirModalConfirmar(
      '¿Confirmas que quieres marcar esta incidencia como resuelta? Esta acción no se puede deshacer.',
      async () => {
        try {
          await api.resolverIncidencia(id);
          ui.mostrarToast('Incidencia resuelta correctamente', 'success');
          cargar();
        } catch (err) {
          ui.mostrarToast(err.message, 'error');
        }
      }
    );
  }
});

document.querySelectorAll('[data-cerrar-modal]').forEach(btn => {
  btn.addEventListener('click', () => ui.cerrarModal(btn.dataset.cerrarModal));
});

document.addEventListener('keydown', (e) => {
  if (e.key === 'Escape') ui.cerrarTodosModales();
});

document.addEventListener('DOMContentLoaded', cargar);