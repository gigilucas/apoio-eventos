// Função para obter o token JWT
function getAuthHeaders() {
    const token = localStorage.getItem('token');
    console.log('Token no getAuthHeaders:', token ? 'Presente' : 'Ausente');
    const headers = {
        'Content-Type': 'application/json'
    };
    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }
    console.log('Headers:', headers);
    return headers;
}

// Função para fazer logout
function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    localStorage.removeItem('email');
    window.location.href = '/login?logout=true';
}

// Adicionar evento de logout a todos os links com classe logout
document.addEventListener('DOMContentLoaded', function() {
    const logoutLinks = document.querySelectorAll('.logout');
    logoutLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            logout();
        });
    });
});
