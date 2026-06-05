document.addEventListener('DOMContentLoaded', function() {
    loadEventos();
});

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
        const response = await fetch('/api/sessoes', {
            method: 'POST',
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
            successMessage.textContent = 'Sessão criada com sucesso! Redirecionando...';
            setTimeout(() => {
                window.location.href = '/sessoes';
            }, 2000);
        } else {
            const errorText = await response.text();
            errorMessage.style.display = 'block';
            errorMessage.textContent = errorText || 'Erro ao criar sessão';
        }
    } catch (error) {
        errorMessage.style.display = 'block';
        errorMessage.textContent = 'Erro ao criar sessão. Tente novamente.';
        console.error('Error:', error);
    }
});
