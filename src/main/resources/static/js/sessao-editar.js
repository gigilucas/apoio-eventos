document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');
    
    if (id) {
        loadSessao(id);
        loadEventos();
    }
});

async function loadSessao(id) {
    try {
        const response = await fetch(`/api/sessoes/${id}`, {
            headers: getAuthHeaders()
        });
        
        if (response.ok) {
            const sessao = await response.json();
            document.getElementById('titulo').value = sessao.titulo || '';
            document.getElementById('nomeOrador').value = sessao.nomeOrador || '';
            document.getElementById('dataHoraInicio').value = sessao.dataHoraInicio ? sessao.dataHoraInicio.slice(0, 16) : '';
            document.getElementById('dataHoraFim').value = sessao.dataHoraFim ? sessao.dataHoraFim.slice(0, 16) : '';
            document.getElementById('eventoId').value = sessao.eventoId || '';
        } else {
            showError('Erro ao carregar sessão');
        }
    } catch (error) {
        showError('Erro ao carregar sessão');
        console.error('Error:', error);
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

document.getElementById('sessaoForm').addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');
    
    const titulo = document.getElementById('titulo').value;
    const nomeOrador = document.getElementById('nomeOrador').value;
    const dataHoraInicio = document.getElementById('dataHoraInicio').value;
    const dataHoraFim = document.getElementById('dataHoraFim').value;
    const eventoId = document.getElementById('eventoId').value;
    
    const errorMessage = document.getElementById('errorMessage');
    const successMessage = document.getElementById('successMessage');
    
    errorMessage.style.display = 'none';
    successMessage.style.display = 'none';
    
    try {
        const response = await fetch(`/api/sessoes/${id}`, {
            method: 'PUT',
            headers: getAuthHeaders(),
            body: JSON.stringify({ 
                titulo, 
                nomeOrador, 
                dataHoraInicio, 
                dataHoraFim, 
                eventoId: parseInt(eventoId)
            })
        });
        
        if (response.ok) {
            successMessage.style.display = 'block';
            successMessage.textContent = 'Sessão atualizada com sucesso! Redirecionando...';
            setTimeout(() => {
                window.location.href = '/sessoes';
            }, 2000);
        } else {
            const errorText = await response.text();
            errorMessage.style.display = 'block';
            errorMessage.textContent = errorText || 'Erro ao atualizar sessão';
        }
    } catch (error) {
        errorMessage.style.display = 'block';
        errorMessage.textContent = 'Erro ao atualizar sessão. Tente novamente.';
        console.error('Error:', error);
    }
});

function showError(message) {
    const errorDiv = document.getElementById('errorMessage');
    errorDiv.textContent = message;
    errorDiv.style.display = 'block';
}
