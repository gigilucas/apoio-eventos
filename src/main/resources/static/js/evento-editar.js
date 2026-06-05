document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');
    
    if (id) {
        loadEvento(id);
    }
});

async function loadEvento(id) {
    try {
        const response = await fetch(`/api/eventos/${id}`, {
            headers: getAuthHeaders()
        });
        
        if (response.ok) {
            const evento = await response.json();
            document.getElementById('nome').value = evento.nome || '';
            document.getElementById('descricao').value = evento.descricao || '';
            document.getElementById('dataInicio').value = evento.dataInicio ? evento.dataInicio.slice(0, 16) : '';
            document.getElementById('dataFim').value = evento.dataFim ? evento.dataFim.slice(0, 16) : '';
            document.getElementById('local').value = evento.local || '';
            document.getElementById('capacidadeMaxima').value = evento.capacidadeMaxima || '';
            document.getElementById('estaAtivo').value = evento.estaAtivo ? 'true' : 'false';
        } else {
            showError('Erro ao carregar evento');
        }
    } catch (error) {
        showError('Erro ao carregar evento');
        console.error('Error:', error);
    }
}

document.getElementById('eventoForm').addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');
    
    const nome = document.getElementById('nome').value;
    const descricao = document.getElementById('descricao').value;
    const dataInicio = document.getElementById('dataInicio').value;
    const dataFim = document.getElementById('dataFim').value;
    const local = document.getElementById('local').value;
    const capacidadeMaxima = document.getElementById('capacidadeMaxima').value;
    const estaAtivo = document.getElementById('estaAtivo').value === 'true';
    
    const errorMessage = document.getElementById('errorMessage');
    const successMessage = document.getElementById('successMessage');
    
    errorMessage.style.display = 'none';
    successMessage.style.display = 'none';
    
    try {
        const response = await fetch(`/api/eventos/${id}`, {
            method: 'PUT',
            headers: getAuthHeaders(),
            body: JSON.stringify({ 
                nome, 
                descricao, 
                dataInicio, 
                dataFim, 
                local, 
                capacidadeMaxima,
                estaAtivo
            })
        });
        
        if (response.ok) {
            successMessage.style.display = 'block';
            successMessage.textContent = 'Evento atualizado com sucesso! Redirecionando...';
            setTimeout(() => {
                window.location.href = '/eventos';
            }, 2000);
        } else {
            const errorText = await response.text();
            errorMessage.style.display = 'block';
            errorMessage.textContent = errorText || 'Erro ao atualizar evento';
        }
    } catch (error) {
        errorMessage.style.display = 'block';
        errorMessage.textContent = 'Erro ao atualizar evento. Tente novamente.';
        console.error('Error:', error);
    }
});

function showError(message) {
    const errorDiv = document.getElementById('errorMessage');
    errorDiv.textContent = message;
    errorDiv.style.display = 'block';
}
