window.addEventListener('load', async function() {
    const token = localStorage.getItem('token');
    if (!token) {
        window.location.href = '/login';
        return;
    }
    
    await loadEventos();
});

async function loadEventos() {
    try {
        const response = await fetch('/api/eventos', {
            headers: getAuthHeaders()
        });
        
        if (response.ok) {
            const eventos = await response.json();
            renderEventos(eventos);
        } else {
            console.error('Error loading eventos');
        }
    } catch (error) {
        console.error('Error loading eventos:', error);
    }
}

function renderEventos(eventos) {
    const tbody = document.getElementById('eventosTableBody');
    tbody.innerHTML = '';
    
    eventos.forEach(evento => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${evento.id}</td>
            <td>${evento.nome}</td>
            <td>${evento.descricao || '-'}</td>
            <td>${formatDate(evento.dataInicio)}</td>
            <td>${formatDate(evento.dataFim)}</td>
            <td>${evento.local}</td>
            <td>${evento.capacidadeMaxima}</td>
            <td>${evento.estaAtivo ? 'Ativo' : 'Inativo'}</td>
            <td>
                <div class="action-buttons">
                    <button class="btn btn-secondary" onclick="editEvento(${evento.id})">Editar</button>
                    <button class="btn btn-danger" onclick="deleteEvento(${evento.id})">Excluir</button>
                </div>
            </td>
        `;
        tbody.appendChild(row);
    });
}

function formatDate(dateString) {
    if (!dateString) return '-';
    const date = new Date(dateString);
    return date.toLocaleDateString('pt-PT');
}

async function deleteEvento(id) {
    if (!confirm('Tem certeza que deseja excluir este evento?')) {
        return;
    }
    
    try {
        const response = await fetch(`/api/eventos/${id}`, {
            method: 'DELETE',
            headers: getAuthHeaders()
        });
        
        if (response.ok) {
            alert('Evento excluído com sucesso');
            loadEventos();
        } else {
            alert('Erro ao excluir evento');
        }
    } catch (error) {
        console.error('Error deleting evento:', error);
        alert('Erro ao excluir evento');
    }
}

function editEvento(id) {
    window.location.href = `/eventos/editar?id=${id}`;
}
