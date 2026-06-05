document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');
    
    if (id) {
        loadCertificado(id);
        loadInscricoes();
    }
});

async function loadCertificado(id) {
    try {
        const response = await fetch(`/api/certificados/${id}`, {
            headers: getAuthHeaders()
        });
        
        if (response.ok) {
            const certificado = await response.json();
            document.getElementById('codigoCertificado').value = certificado.codigoCertificado || '';
            document.getElementById('inscricaoId').value = certificado.inscricaoId || '';
        } else {
            showError('Erro ao carregar certificado');
        }
    } catch (error) {
        showError('Erro ao carregar certificado');
        console.error('Error:', error);
    }
}

async function loadInscricoes() {
    try {
        const response = await fetch('/api/inscricoes', {
            headers: getAuthHeaders()
        });
        if (response.ok) {
            const inscricoes = await response.json();
            const select = document.getElementById('inscricaoId');
            select.innerHTML = '<option value="">Selecione uma inscrição</option>';
            inscricoes.forEach(i => {
                const option = document.createElement('option');
                option.value = i.id;
                option.textContent = `${i.participante ? i.participante.nome : 'Participante'} - ${i.evento ? i.evento.nome : 'Evento'}`;
                select.appendChild(option);
            });
        }
    } catch (error) {
        console.error('Error loading inscricoes:', error);
    }
}

document.getElementById('certificadoForm').addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');
    
    const codigoCertificado = document.getElementById('codigoCertificado').value;
    const inscricaoId = document.getElementById('inscricaoId').value;
    
    const errorMessage = document.getElementById('errorMessage');
    const successMessage = document.getElementById('successMessage');
    
    errorMessage.style.display = 'none';
    successMessage.style.display = 'none';
    
    try {
        const response = await fetch(`/api/certificados/${id}`, {
            method: 'PUT',
            headers: getAuthHeaders(),
            body: JSON.stringify({ 
                codigoCertificado, 
                inscricaoId: parseInt(inscricaoId)
            })
        });
        
        if (response.ok) {
            successMessage.style.display = 'block';
            successMessage.textContent = 'Certificado atualizado com sucesso! Redirecionando...';
            setTimeout(() => {
                window.location.href = '/certificados';
            }, 2000);
        } else {
            const errorText = await response.text();
            errorMessage.style.display = 'block';
            errorMessage.textContent = errorText || 'Erro ao atualizar certificado';
        }
    } catch (error) {
        errorMessage.style.display = 'block';
        errorMessage.textContent = 'Erro ao atualizar certificado. Tente novamente.';
        console.error('Error:', error);
    }
});

function showError(message) {
    const errorDiv = document.getElementById('errorMessage');
    errorDiv.textContent = message;
    errorDiv.style.display = 'block';
}
