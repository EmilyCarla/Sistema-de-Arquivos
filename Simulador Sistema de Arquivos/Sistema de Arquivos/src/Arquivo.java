class Arquivo {
    private final String nome;
    private final int tamanho;
    private Bloco inicio;

    public Arquivo(String nome, int tamanho, SistemaDeArquivos sistema) {
        this.nome = nome;
        this.tamanho = tamanho;
        this.inicio = null;
        alocarBlocos(sistema);
    }

    private void alocarBlocos(SistemaDeArquivos sistema) {
        Bloco anterior = null;
        for (int i = 0; i < tamanho; i++) {
            Bloco novoBloco = sistema.alocarBloco();
            if (novoBloco == null) {
                System.out.println("Erro: Não há espaço suficiente para alocar o arquivo!");
                return;
            }
            if (inicio == null) {
                inicio = novoBloco;
            } else {
                anterior.proximo = novoBloco;
            }
            anterior = novoBloco;
        }
    }

    public void liberarBlocos(SistemaDeArquivos sistema) {
        Bloco atual = inicio;
        while (atual != null) {
            Bloco proximo = atual.proximo;
            sistema.liberarBloco(atual);
            atual = proximo;
        }
    }

    public String getNome() {
        return nome;
    }

    public int getTamanho() {
        return tamanho;
    }
}