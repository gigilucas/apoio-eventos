document.getElementById('registerForm').addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const nome = document.getElementById('nome').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const telefone = document.getElementById('telefone').value;
    const numeroEstudante = document.getElementById('numeroEstudante').value;
    const curso = document.getElementById('curso').value;
    
    const errorMessage = document.getElementById('errorMessage');
    const successMessage = document.getElementById('successMessage');
    
    errorMessage.style.display = 'none';
    successMessage.style.display = 'none';
    
    if (password !== confirmPassword) {
        errorMessage.style.display = 'block';
        errorMessage.textContent = 'As senhas não coincidem';
        return;
    }
    
    try {
        const response = await fetch('/api/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ 
                nome, 
                email, 
                password, 
                confirmPassword,
                telefone,
                numeroEstudante,
                curso
            })
        });
        
        if (response.ok) {
            successMessage.style.display = 'block';
            successMessage.textContent = 'Usuário registrado com sucesso! Redirecionando para login...';
            setTimeout(() => {
                window.location.href = '/login';
            }, 2000);
        } else {
            const errorText = await response.text();
            errorMessage.style.display = 'block';
            errorMessage.textContent = errorText || 'Erro ao registrar usuário';
        }
    } catch (error) {
        errorMessage.style.display = 'block';
        errorMessage.textContent = 'Erro ao registrar usuário. Tente novamente.';
        console.error('Registration error:', error);
    }
});
