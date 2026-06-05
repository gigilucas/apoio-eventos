document.addEventListener('DOMContentLoaded', function() {
    loadInscricoes();
});

async function loadInscricoes() {
    try {
        const response = await fetch('/api/inscricoes', {
            headers: getAuthHeaders()
        });
        if (response.ok) {
            const inscricoes = await response.json();
            renderInscricoes(inscricoes);
        } else {
            showError('Erro ao carregar inscrições');
        }
    } catch (error) {
        showError('Erro ao carregar inscrições');
        console.error('Error:', error);
    }
}

function renderInscricoes(inscricoes) {
    const table = document.getElementById('inscricoesTable');
    table.innerHTML = '';
    
    inscricoes.forEach(inscricao => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${inscricao.id}</td>
            <td>${inscricao.participante ? inscricao.participante.nome : '-'}</td>
            <td>${inscricao.evento ? inscricao.evento.nome : '-'}</td>
            <td>${new Date(inscricao.dataInscricao).toLocaleDateString()}</td>
            <td>${inscricao.estadoInscricao || '-'}</td>
            <td>
                <button onclick="editInscricao(${inscricao.id})" class="btn btn-sm btn-secondary">Editar</button>
                <button onclick="deleteInscricao(${inscricao.id})" class="btn btn-sm btn-danger">Eliminar</button>
            </td>
        `;
        table.appendChild(row);
    });
}

async function deleteInscricao(id) {
    if (!confirm('Tem certeza que deseja eliminar esta inscrição?')) {
        return;
    }
    
    try {
        const response = await fetch(`/api/inscricoes/${id}`, {
            method: 'DELETE',
            headers: getAuthHeaders()
        });
        
        if (response.ok) {
            loadInscricoes();
        } else {
            showError('Erro ao eliminar inscrição');
        }
    } catch (error) {
        showError('Erro ao eliminar inscrição');
        console.error('Error:', error);
    }
}

function editInscricao(id) {
    window.location.href = `/inscricoes/editar?id=${id}`;
}

function showError(message) {
    const errorDiv = document.getElementById('errorMessage');
    errorDiv.textContent = message;
    errorDiv.style.display = 'block';
}
