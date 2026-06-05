document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');
    
    if (id) {
        loadInscricao(id);
        loadParticipantes();
        loadEventos();
    }
});

async function loadInscricao(id) {
    try {
        const response = await fetch(`/api/inscricoes/${id}`, {
            headers: getAuthHeaders()
        });
        
        if (response.ok) {
            const inscricao = await response.json();
            document.getElementById('participanteId').value = inscricao.participanteId || '';
            document.getElementById('eventoId').value = inscricao.eventoId || '';
            document.getElementById('estadoInscricao').value = inscricao.estadoInscricao || 'Pendente';
        } else {
            showError('Erro ao carregar inscrição');
        }
    } catch (error) {
        showError('Erro ao carregar inscrição');
        console.error('Error:', error);
    }
}

async function loadParticipantes() {
    try {
        const response = await fetch('/api/participantes', {
            headers: getAuthHeaders()
        });
        if (response.ok) {
            const participantes = await response.json();
            const select = document.getElementById('participanteId');
            select.innerHTML = '<option value="">Selecione um participante</option>';
            participantes.forEach(p => {
                const option = document.createElement('option');
                option.value = p.id;
                option.textContent = p.nome;
                select.appendChild(option);
            });
        }
    } catch (error) {
        console.error('Error loading participantes:', error);
    }
}

async function loadEventos() {
    try {
        const response = await fetch('/api/eventos', {
            headers: getAuthHeaders()
        });
        if (response.ok) {
            const eventos = await response.json();
            const select = document.getElementById('eventoId');
            select.innerHTML = '<option value="">Selecione um evento</option>';
            eventos.forEach(e => {
                const option = document.createElement('option');
                option.value = e.id;
                option.textContent = e.nome;
                select.appendChild(option);
            });
        }
    } catch (error) {
        console.error('Error loading eventos:', error);
    }
}

document.getElementById('inscricaoForm').addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');
    
    const participanteId = document.getElementById('participanteId').value;
    const eventoId = document.getElementById('eventoId').value;
    const estadoInscricao = document.getElementById('estadoInscricao').value;
    
    const errorMessage = document.getElementById('errorMessage');
    const successMessage = document.getElementById('successMessage');
    
    errorMessage.style.display = 'none';
    successMessage.style.display = 'none';
    
    try {
        const response = await fetch(`/api/inscricoes/${id}`, {
            method: 'PUT',
            headers: getAuthHeaders(),
            body: JSON.stringify({ 
                participanteId: parseInt(participanteId), 
                eventoId: parseInt(eventoId),
                estadoInscricao
            })
        });
        
        if (response.ok) {
            successMessage.style.display = 'block';
            successMessage.textContent = 'Inscrição atualizada com sucesso! Redirecionando...';
            setTimeout(() => {
                window.location.href = '/inscricoes';
            }, 2000);
        } else {
            const errorText = await response.text();
            errorMessage.style.display = 'block';
            errorMessage.textContent = errorText || 'Erro ao atualizar inscrição';
        }
    } catch (error) {
        errorMessage.style.display = 'block';
        errorMessage.textContent = 'Erro ao atualizar inscrição. Tente novamente.';
        console.error('Error:', error);
    }
});

function showError(message) {
    const errorDiv = document.getElementById('errorMessage');
    errorDiv.textContent = message;
    errorDiv.style.display = 'block';
}
