document.addEventListener('DOMContentLoaded', function() {
    loadParticipantes();
    loadEventos();
});

async function loadParticipantes() {
    try {
        const response = await fetch('/api/participantes');
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
        const response = await fetch('/api/eventos');
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
    
    const participanteId = document.getElementById('participanteId').value;
    const eventoId = document.getElementById('eventoId').value;
    
    const errorMessage = document.getElementById('errorMessage');
    const successMessage = document.getElementById('successMessage');
    
    errorMessage.style.display = 'none';
    successMessage.style.display = 'none';
    
    try {
        const response = await fetch('/api/inscricoes', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ 
                participanteId: parseInt(participanteId), 
                eventoId: parseInt(eventoId)
            })
        });
        
        if (response.ok) {
            successMessage.style.display = 'block';
            successMessage.textContent = 'Inscrição criada com sucesso! Redirecionando...';
            setTimeout(() => {
                window.location.href = '/inscricoes';
            }, 2000);
        } else {
            const errorText = await response.text();
            errorMessage.style.display = 'block';
            errorMessage.textContent = errorText || 'Erro ao criar inscrição';
        }
    } catch (error) {
        errorMessage.style.display = 'block';
        errorMessage.textContent = 'Erro ao criar inscrição. Tente novamente.';
        console.error('Error:', error);
    }
});
