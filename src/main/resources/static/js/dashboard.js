window.addEventListener('load', async function() {
    const token = localStorage.getItem('token');
    if (!token) {
        window.location.href = '/login';
        return;
    }
    
    await loadStats();
});

async function loadStats() {
    try {
        const token = localStorage.getItem('token');
        
        const [eventosRes, participantesRes, inscricoesRes, certificadosRes] = await Promise.all([
            fetch('/api/eventos', {
                headers: { 'Authorization': `Bearer ${token}` }
            }),
            fetch('/api/participantes', {
                headers: { 'Authorization': `Bearer ${token}` }
            }),
            fetch('/api/inscricoes', {
                headers: { 'Authorization': `Bearer ${token}` }
            }),
            fetch('/api/certificados', {
                headers: { 'Authorization': `Bearer ${token}` }
            })
        ]);
        
        if (eventosRes.ok) {
            const eventos = await eventosRes.json();
            document.getElementById('totalEventos').textContent = eventos.length;
        }
        
        if (participantesRes.ok) {
            const participantes = await participantesRes.json();
            document.getElementById('totalParticipantes').textContent = participantes.length;
        }
        
        if (inscricoesRes.ok) {
            const inscricoes = await inscricoesRes.json();
            document.getElementById('totalInscricoes').textContent = inscricoes.length;
        }
        
        if (certificadosRes.ok) {
            const certificados = await certificadosRes.json();
            document.getElementById('totalCertificados').textContent = certificados.length;
        }
    } catch (error) {
        console.error('Error loading stats:', error);
    }
}
