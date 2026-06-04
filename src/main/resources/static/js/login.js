document.getElementById('loginForm').addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const errorMessage = document.getElementById('errorMessage');
    
    try {
        const response = await fetch('/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ email, password })
        });
        
        if (response.ok) {
            const data = await response.json();
            localStorage.setItem('token', data.accessToken);
            localStorage.setItem('userId', data.userId);
            localStorage.setItem('email', data.email);
            window.location.href = '/';
        } else {
            errorMessage.style.display = 'block';
            errorMessage.textContent = 'Credenciais inválidas';
        }
    } catch (error) {
        errorMessage.style.display = 'block';
        errorMessage.textContent = 'Erro ao fazer login. Tente novamente.';
        console.error('Login error:', error);
    }
});

// Verificar se já está logado
window.addEventListener('load', function() {
    const token = localStorage.getItem('token');
    if (token) {
        window.location.href = '/';
    }
});
