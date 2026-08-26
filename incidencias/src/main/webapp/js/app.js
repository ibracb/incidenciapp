import * as api from './api.js';
import * as ui from './ui.js';

let estadoActual = null;

async function cargar() {
  try {
    mostrarEsqueletos();
    // Si el filtro es "Todas" (estadoActual === null), porFiltro = false;
    // si hay un filtro específico (Pendientes/Asignadas/Resueltas), porFiltro = true.
    const porFiltro = estadoActual !== null;
    const data = await api.listarIncidencias(estadoActual);
    ui.renderLista(data, porFiltro);
  } catch (err) {
    ui.mostrarToast(err.message, 'error');
    const tbody = document.getElementById('tbody-incidencias');
    if (tbody) tbody.innerHTML = '<tr class="empty-state"><td colspan="5">Error al cargar incidencias</td></tr>';
  }
}

function mostrarEsqueletos() {
  const tbody = document.getElementById('tbody-incidencias');
  if (!tbody) return;
  tbody.innerHTML = Array(5).fill(0).map(() => `
    <tr class="skeleton-row">
      <td><div class="skeleton skeleton-id"></div></td>
      <td><div class="skeleton skeleton-desc"></div></td>
      <td><div class="skeleton skeleton-fecha"></div></td>
      <td><div class="skeleton skeleton-badge"></div></td>
      <td></td>
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
  const fila = e.target.closest('tr');
  if (!fila) return;
  const id = fila.dataset.id;
  const estadoFila = fila.dataset.estado; // viene del backend o '-'

  // Lógica por filtro activo:
  // - Si el filtro es "Todas" (estadoActual === null): no hacer acción, solo info
  // - Si el filtro es "Pendientes": acción "Asignar"
  // - Si el filtro es "Asignadas": acción "Resolver"
  // - Si el filtro es "Resueltas": no hacer nada
  if (estadoActual === null) {
    // Vista "Todas": sin acciones; puede hacer info adicional si se quiere
    ui.mostrarToast('Vista "Todas": no hay acción disponible', 'info');
    return;
  }

  if (estadoActual === 'PENDIENTE') {
    ui.abrirModalAsignar(id, async (nombreTecnico, telefonoTecnico) => {
      const btn = document.querySelector('#modal-asignar .btn-primary');
      ui.setLoading(btn, true);
      try {
        await api.asignarIncidencia(id, nombreTecnico, telefonoTecnico);
        ui.cerrarModal('modal-asignar');
        ui.mostrarToast('Incidencia asignada correctamente', 'success');
        cargar();
      } catch (err) {
        ui.mostrarToast(err.message, 'error');
      } finally {
        ui.setLoading(btn, false);
      }
    });
  } else if (estadoActual === 'ASIGNADA') {
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