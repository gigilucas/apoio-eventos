document.addEventListener('DOMContentLoaded', function() {
    loadCertificados();
});

async function loadCertificados() {
    try {
        const response = await fetch('/api/certificados');
        if (response.ok) {
            const certificados = await response.json();
            renderCertificados(certificados);
        } else {
            showError('Erro ao carregar certificados');
        }
    } catch (error) {
        showError('Erro ao carregar certificados');
        console.error('Error:', error);
    }
}

function renderCertificados(certificados) {
    const table = document.getElementById('certificadosTable');
    table.innerHTML = '';
    
    certificados.forEach(certificado => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${certificado.id}</td>
            <td>${certificado.codigoCertificado}</td>
            <td>${new Date(certificado.dataEmissao).toLocaleDateString()}</td>
            <td>${certificado.inscricao ? certificado.inscricao.id : '-'}</td>
            <td>${certificado.inscricao && certificado.inscricao.participante ? certificado.inscricao.participante.nome : '-'}</td>
            <td>
                <button onclick="editCertificado(${certificado.id})" class="btn btn-sm btn-secondary">Editar</button>
                <button onclick="deleteCertificado(${certificado.id})" class="btn btn-sm btn-danger">Eliminar</button>
            </td>
        `;
        table.appendChild(row);
    });
}

async function deleteCertificado(id) {
    if (!confirm('Tem certeza que deseja eliminar este certificado?')) {
        return;
    }
    
    try {
        const response = await fetch(`/api/certificados/${id}`, {
            method: 'DELETE'
        });
        
        if (response.ok) {
            loadCertificados();
        } else {
            showError('Erro ao eliminar certificado');
        }
    } catch (error) {
        showError('Erro ao eliminar certificado');
        console.error('Error:', error);
    }
}

function editCertificado(id) {
    window.location.href = `/certificados/editar/${id}`;
}

function showError(message) {
    const errorDiv = document.getElementById('errorMessage');
    errorDiv.textContent = message;
    errorDiv.style.display = 'block';
}
