import java.util.*;

public class SistemaDeArquivos {
    private final int TAMANHO_TOTAL = 100 * 1024; // 100MB em KB
    private int espacoUsado = 0;
    private final Map<String, Diretorio> repositorios = new HashMap<>();
    private final List<Bloco> blocosLivres = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public SistemaDeArquivos() {
        for (int i = 0; i < TAMANHO_TOTAL; i++) {
            blocosLivres.add(new Bloco(i));
        }
    }

    public void executar() {
        while (true) {
            try {
                exibirMenu();
                int opcao = obterOpcao();
                processarOpcao(opcao);
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Por favor, insira um número válido.");
                scanner.nextLine(); 
            }
        }
    }

    private void exibirMenu() {
        System.out.println("\n====================");
        System.out.println("\n1. Criar Repositório");
        System.out.println("2. Acessar Repositório");
        System.out.println("3. Exibir Memória");
        System.out.println("4. Sair");
        System.out.println("\n====================");
        System.out.print("Escolha uma opção: ");
    }

    private int obterOpcao() {
        return scanner.nextInt();
    }

    private void processarOpcao(int opcao) {
        scanner.nextLine();
        switch (opcao) {
            case 1 -> criarRepositorio();
            case 2 -> acessarRepositorio();
            case 3 -> exibirMemoria();
            case 4 -> {
                System.out.println("Saindo do programa...");
                System.exit(0);
            }
            default -> System.out.println("Opção inválida! Tente novamente.");
        }
    }

    private void criarRepositorio() {
        System.out.print("Letra do repositório (ex: E:): ");
        String letra = scanner.nextLine().toUpperCase();
        if (repositorios.containsKey(letra)) {
            System.out.println("Esse repositório já existe!");
        } else {
            repositorios.put(letra, new Diretorio(letra, this));
            System.out.println("Repositório criado com sucesso!");
        }
    }

    private void acessarRepositorio() {
        if (repositorios.isEmpty()) {
            System.out.println("Nenhum repositório encontrado! Crie um primeiro.");
            return;
        }

        System.out.print("Escolha um repositório: ");
        String letra = scanner.nextLine().toUpperCase();
        if (!repositorios.containsKey(letra)) {
            System.out.println("Repositório não encontrado!");
            return;
        }
        repositorios.get(letra).gerenciar();
    }

    private void exibirMemoria() {
        System.out.println("\n USO DE MEMÓRIA:");
        System.out.println("Espaço usado: " + espacoUsado + " KB");
        System.out.println("Espaço livre: " + (TAMANHO_TOTAL - espacoUsado) + " KB");
    }

    public boolean alocarEspaco(int tamanho) {
        if (espacoUsado + tamanho > TAMANHO_TOTAL) {
            System.out.println("Erro: Espaço insuficiente!");
            return false;
        }
        espacoUsado += tamanho;
        return true;
    }

    public void liberarEspaco(int tamanho) {
        espacoUsado -= tamanho;
    }

    public Bloco alocarBloco() {
        if (blocosLivres.isEmpty()) {
            System.out.println("Erro: Espaço insuficiente!");
            return null;
        }
        return blocosLivres.remove(0);
    }

    public void liberarBloco(Bloco bloco) {
        blocosLivres.add(bloco);
    }
}
