document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');
    
    if (id) {
        loadParticipante(id);
    }
});

async function loadParticipante(id) {
    try {
        const response = await fetch(`/api/participantes/${id}`, {
            headers: getAuthHeaders()
        });
        
        if (response.ok) {
            const participante = await response.json();
            document.getElementById('nome').value = participante.nome || '';
            document.getElementById('email').value = participante.email || '';
            document.getElementById('telefone').value = participante.telefone || '';
            document.getElementById('numeroEstudante').value = participante.numeroEstudante || '';
            document.getElementById('curso').value = participante.curso || '';
        } else {
            showError('Erro ao carregar participante');
        }
    } catch (error) {
        showError('Erro ao carregar participante');
        console.error('Error:', error);
    }
}

document.getElementById('participanteForm').addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');
    
    const nome = document.getElementById('nome').value;
    const email = document.getElementById('email').value;
    const telefone = document.getElementById('telefone').value;
    const numeroEstudante = document.getElementById('numeroEstudante').value;
    const curso = document.getElementById('curso').value;
    
    const errorMessage = document.getElementById('errorMessage');
    const successMessage = document.getElementById('successMessage');
    
    errorMessage.style.display = 'none';
    successMessage.style.display = 'none';
    
    try {
        const response = await fetch(`/api/participantes/${id}`, {
            method: 'PUT',
            headers: getAuthHeaders(),
            body: JSON.stringify({ 
                nome, 
                email, 
                telefone, 
                numeroEstudante, 
                curso
            })
        });
        
        if (response.ok) {
            successMessage.style.display = 'block';
            successMessage.textContent = 'Participante atualizado com sucesso! Redirecionando...';
            setTimeout(() => {
                window.location.href = '/participantes';
            }, 2000);
        } else {
            const errorText = await response.text();
            errorMessage.style.display = 'block';
            errorMessage.textContent = errorText || 'Erro ao atualizar participante';
        }
    } catch (error) {
        errorMessage.style.display = 'block';
        errorMessage.textContent = 'Erro ao atualizar participante. Tente novamente.';
        console.error('Error:', error);
    }
});

function showError(message) {
    const errorDiv = document.getElementById('errorMessage');
    errorDiv.textContent = message;
    errorDiv.style.display = 'block';
}
