document.addEventListener('DOMContentLoaded', function() {
    loadCertificados();
});

async function loadCertificados() {
    try {
        const response = await fetch('/api/certificados', {
            headers: getAuthHeaders()
        });
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
                <button onclick="imprimirCertificado(${certificado.id})" class="btn btn-sm btn-primary">Imprimir</button>
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
            method: 'DELETE',
            headers: getAuthHeaders()
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
    window.location.href = `/certificados/editar?id=${id}`;
}

async function imprimirCertificado(id) {
    try {
        const response = await fetch(`/api/certificados/${id}`, {
            headers: getAuthHeaders()
        });
        
        if (response.ok) {
            const certificado = await response.json();
            
            // Criar uma janela de impressão
            const printWindow = window.open('', '_blank');
            printWindow.document.write(`
                <!DOCTYPE html>
                <html>
                <head>
                    <title>Certificado - ${certificado.codigoCertificado}</title>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            padding: 40px;
                            text-align: center;
                        }
                        .certificado {
                            border: 5px solid #3498db;
                            padding: 40px;
                            max-width: 800px;
                            margin: 0 auto;
                        }
                        h1 {
                            color: #3498db;
                            margin-bottom: 30px;
                        }
                        .conteudo {
                            margin: 20px 0;
                            font-size: 18px;
                            line-height: 1.6;
                        }
                        .codigo {
                            margin-top: 40px;
                            font-size: 14px;
                            color: #666;
                        }
                        .data {
                            margin-top: 20px;
                            font-size: 14px;
                            color: #666;
                        }
                    </style>
                </head>
                <body>
                    <div class="certificado">
                        <h1>CERTIFICADO DE PARTICIPAÇÃO</h1>
                        <div class="conteudo">
                            <p>Certificamos que</p>
                            <p><strong>${certificado.inscricao && certificado.inscricao.participante ? certificado.inscricao.participante.nome : 'Participante'}</strong></p>
                            <p>participou com sucesso do evento</p>
                            <p><strong>${certificado.inscricao && certificado.inscricao.evento ? certificado.inscricao.evento.nome : 'Evento'}</strong></p>
                        </div>
                        <div class="codigo">
                            <p>Código do Certificado: ${certificado.codigoCertificado}</p>
                        </div>
                        <div class="data">
                            <p>Data de Emissão: ${new Date(certificado.dataEmissao).toLocaleDateString('pt-PT')}</p>
                        </div>
                    </div>
                    <script>
                        window.onload = function() {
                            window.print();
                            window.onafterprint = function() {
                                window.close();
                            };
                        };
                    </script>
                </body>
                </html>
            `);
            printWindow.document.close();
        } else {
            showError('Erro ao carregar certificado para impressão');
        }
    } catch (error) {
        showError('Erro ao imprimir certificado');
        console.error('Error:', error);
    }
}

function showError(message) {
    const errorDiv = document.getElementById('errorMessage');
    errorDiv.textContent = message;
    errorDiv.style.display = 'block';
}
