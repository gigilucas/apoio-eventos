document.addEventListener('DOMContentLoaded', function() {
    loadSessoes();
});

async function loadSessoes() {
    try {
        const response = await fetch('/api/sessoes');
        if (response.ok) {
            const sessoes = await response.json();
            renderSessoes(sessoes);
        } else {
            showError('Erro ao carregar sessões');
        }
    } catch (error) {
        showError('Erro ao carregar sessões');
        console.error('Error:', error);
    }
}

function renderSessoes(sessoes) {
    const table = document.getElementById('sessoesTable');
    table.innerHTML = '';
    
    sessoes.forEach(sessao => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${sessao.id}</td>
            <td>${sessao.titulo}</td>
            <td>${sessao.nomeOrador || '-'}</td>
            <td>${new Date(sessao.dataHoraInicio).toLocaleString()}</td>
            <td>${new Date(sessao.dataHoraFim).toLocaleString()}</td>
            <td>${sessao.evento ? sessao.evento.nome : '-'}</td>
            <td>
                <button onclick="editSessao(${sessao.id})" class="btn btn-sm btn-secondary">Editar</button>
                <button onclick="deleteSessao(${sessao.id})" class="btn btn-sm btn-danger">Eliminar</button>
            </td>
        `;
        table.appendChild(row);
    });
}

async function deleteSessao(id) {
    if (!confirm('Tem certeza que deseja eliminar esta sessão?')) {
        return;
    }
    
    try {
        const response = await fetch(`/api/sessoes/${id}`, {
            method: 'DELETE'
        });
        
        if (response.ok) {
            loadSessoes();
        } else {
            showError('Erro ao eliminar sessão');
        }
    } catch (error) {
        showError('Erro ao eliminar sessão');
        console.error('Error:', error);
    }
}

function editSessao(id) {
    window.location.href = `/sessoes/editar/${id}`;
}

function showError(message) {
    const errorDiv = document.getElementById('errorMessage');
    errorDiv.textContent = message;
    errorDiv.style.display = 'block';
}
