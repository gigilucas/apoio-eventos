document.getElementById('participanteForm').addEventListener('submit', async function(e) {
    e.preventDefault();
    
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
        const response = await fetch('/api/participantes', {
            method: 'POST',
            headers: getAuthHeaders(),
            body: JSON.stringify({ 
                nome, 
                email, 
                telefone, 
                numeroEstudante, 
                curso,
                password: '123456',
                role: 'USER'
            })
        });
        
        if (response.ok) {
            successMessage.style.display = 'block';
            successMessage.textContent = 'Participante criado com sucesso! Redirecionando...';
            setTimeout(() => {
                window.location.href = '/participantes';
            }, 2000);
        } else {
            const errorText = await response.text();
            errorMessage.style.display = 'block';
            errorMessage.textContent = errorText || 'Erro ao criar participante';
        }
    } catch (error) {
        errorMessage.style.display = 'block';
        errorMessage.textContent = 'Erro ao criar participante. Tente novamente.';
        console.error('Error:', error);
    }
});
