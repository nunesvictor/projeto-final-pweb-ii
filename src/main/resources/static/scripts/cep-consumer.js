$('#cep').on('keyup', (e) => {
    if (e.target.value.length === 8) {
        const cep = e.target.value;
        fetch(`https://viacep.com.br/ws/${cep}/json/`).then((response) => {
            if (response.ok) {
                response.json().then((data) => {
                    if (data.erro !== undefined) {
                        $('#cep').addClass("is-invalid");
                        return;
                    }

                    $('#cep').removeClass("is-invalid");
                    $('#logradouro').val(data.logradouro);
                    $('#complemento').val(data.complemento);
                    $('#bairro').val(data.bairro);
                    $('#localidade').val(data.localidade);
                    $('#uf').val(data.uf);
                });
            }
        });
    }
});