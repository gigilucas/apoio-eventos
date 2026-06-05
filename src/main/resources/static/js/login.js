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
    const urlParams = new URLSearchParams(window.location.search);
    const justLoggedOut = urlParams.get('logout');
    const goingToRegister = sessionStorage.getItem('goingToRegister');
    
    // Se acabou de fazer logout, não redirecionar
    if (justLoggedOut === 'true') {
        // Limpar o parâmetro da URL
        window.history.replaceState({}, document.title, window.location.pathname);
        return; // Não redirecionar
    }
    
    // Se está a ir para registro, não redirecionar
    if (goingToRegister === 'true') {
        sessionStorage.removeItem('goingToRegister');
        return; // Não redirecionar
    }
    
    // Se tem token e não acabou de fazer logout nem está a ir para registro, redirecionar para dashboard
    if (token) {
        window.location.href = '/';
    }
});
