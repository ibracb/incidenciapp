const API_BASE = '/api/incidencias';

async function request(url, options = {}) {
  const res = await fetch(url, {
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
      ...options.headers
    },
    ...options
  });

  const text = await res.text();

  if (!res.ok) {
    let mensaje = text || `Error ${res.status}`;
    try {
      const json = JSON.parse(text);
      if (json.message) mensaje = json.message;
    } catch {}
    throw new Error(mensaje);
  }

  return text ? JSON.parse(text) : null;
}

export async function listarIncidencias(estado = null) {
  const url = estado ? `${API_BASE}?estado=${encodeURIComponent(estado)}` : API_BASE;
  return request(url);
}

export async function crearIncidencia(descripcion, ubicacion) {
  return request(API_BASE, {
    method: 'POST',
    body: JSON.stringify({ descripcion, ubicacion })
  });
}

export async function asignarIncidencia(id, nombreTecnico, telefonoTecnico) {
  return request(`${API_BASE}/${encodeURIComponent(id)}/asignar`, {
    method: 'PATCH',
    body: JSON.stringify({ nombreTecnico, telefonoTecnico })
  });
}

export async function resolverIncidencia(id) {
  return request(`${API_BASE}/${encodeURIComponent(id)}/resolver`, {
    method: 'PATCH'
  });
}