package desafio.votacao.service.enums;


public enum SimNao {
    SIM("SIM"),
    NAO("NAO");

    private final String valor;

    SimNao(String valor) {
        this.valor = valor;
    }

    public boolean isSim() {
        return this == SIM;
    }
}
