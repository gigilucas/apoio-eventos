document.addEventListener('DOMContentLoaded', function() {
    loadParticipantes();
});

async function loadParticipantes() {
    try {
        const response = await fetch('/api/participantes');
        if (response.ok) {
            const participantes = await response.json();
            renderParticipantes(participantes);
        } else {
            showError('Erro ao carregar participantes');
        }
    } catch (error) {
        showError('Erro ao carregar participantes');
        console.error('Error:', error);
    }
}

function renderParticipantes(participantes) {
    const table = document.getElementById('participantesTable');
    table.innerHTML = '';
    
    participantes.forEach(participante => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${participante.id}</td>
            <td>${participante.nome}</td>
            <td>${participante.email}</td>
            <td>${participante.telefone || '-'}</td>
            <td>${participante.curso || '-'}</td>
            <td>${participante.role || 'USER'}</td>
            <td>
                <button onclick="editParticipante(${participante.id})" class="btn btn-sm btn-secondary">Editar</button>
                <button onclick="deleteParticipante(${participante.id})" class="btn btn-sm btn-danger">Eliminar</button>
            </td>
        `;
        table.appendChild(row);
    });
}

async function deleteParticipante(id) {
    if (!confirm('Tem certeza que deseja eliminar este participante?')) {
        return;
    }
    
    try {
        const response = await fetch(`/api/participantes/${id}`, {
            method: 'DELETE'
        });
        
        if (response.ok) {
            loadParticipantes();
        } else {
            showError('Erro ao eliminar participante');
        }
    } catch (error) {
        showError('Erro ao eliminar participante');
        console.error('Error:', error);
    }
}

function editParticipante(id) {
    window.location.href = `/participantes/editar/${id}`;
}

function showError(message) {
    const errorDiv = document.getElementById('errorMessage');
    errorDiv.textContent = message;
    errorDiv.style.display = 'block';
}
