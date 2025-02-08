package desafio.votacao.service.excecao;

import java.io.Serial;

public class FalhaRequisicaoException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public FalhaRequisicaoException(String mensagem) {
        super(mensagem);
    }
}
