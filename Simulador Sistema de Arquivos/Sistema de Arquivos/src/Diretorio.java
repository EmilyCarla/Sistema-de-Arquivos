import java.util.*;

class Diretorio {
    private final String nome;
    private final Map<String, Diretorio> subdiretorios = new HashMap<>();
    private final List<Arquivo> arquivos = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private final SistemaDeArquivos sistema;

    public Diretorio(String nome, SistemaDeArquivos sistema) {
        this.nome = nome;
        this.sistema = sistema;
    }

    public void gerenciar() {
        while (true) {
            System.out.println("\n====================");
            System.out.println("\nGerenciando: " + nome);
            System.out.println("1. Criar Diretório");
            System.out.println("2. Criar Arquivo");
            System.out.println("3. Excluir Arquivo");
            System.out.println("4. Excluir Diretório");
            System.out.println("5. Listar Conteúdo");
            System.out.println("6. Voltar");
            System.out.println("\n====================");
            System.out.print("Escolha uma opção: ");
            
            int opcao = scanner.nextInt();
            scanner.nextLine();
            switch (opcao) {
                case 1 -> criarDiretorio();
                case 2 -> criarArquivo();
                case 3 -> excluirArquivo();
                case 4 -> excluirDiretorio();
                case 5 -> listarConteudo();
                case 6 -> {
                    return;
                }
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }

    private void criarDiretorio() {
        System.out.print("Nome do diretório: ");
        String nome = scanner.nextLine();
        if (subdiretorios.containsKey(nome)) {
            System.out.println("O diretório já existe!");
        } else {
            subdiretorios.put(nome, new Diretorio(nome, sistema));
            System.out.println("Diretório criado com sucesso!");
        }
    }

    private void criarArquivo() {
        System.out.print("Nome do arquivo: ");
        String nome = scanner.nextLine();
        System.out.print("Tamanho do arquivo (KB): ");
        int tamanho = scanner.nextInt();
        scanner.nextLine();
        if (sistema.alocarEspaco(tamanho)) {
            arquivos.add(new Arquivo(nome, tamanho, sistema));
            System.out.println("Arquivo criado com sucesso!");
        }
    }

    private void excluirArquivo() {
        System.out.print("Nome do arquivo a excluir: ");
        String nome = scanner.nextLine();
        arquivos.removeIf(arquivo -> {
            if (arquivo.getNome().equals(nome)) {
                sistema.liberarEspaco(arquivo.getTamanho());
                return true;
            }
            return false;
        });
        System.out.println("Arquivo removido com sucesso!");
    }

    private void excluirDiretorio() {
        System.out.print("Nome do diretório a excluir: ");
        String nomeDir = scanner.nextLine();
        if (!subdiretorios.containsKey(nomeDir)) {
            System.out.println("Diretório não encontrado!");
            return;
        }
        if (!subdiretorios.get(nomeDir).arquivos.isEmpty()) {
            System.out.println("O diretório não está vazio!");
            return;
        }
        subdiretorios.remove(nomeDir);
        System.out.println("Diretório removido com sucesso!");
    }

    private void listarConteudo() {
        System.out.println("Conteúdo de " + nome + ":");
        subdiretorios.keySet().forEach(dir -> System.out.println("[D] " + dir));
        arquivos.forEach(arq -> System.out.println("  - [A] " + arq.getNome() + " (" + arq.getTamanho() + " KB)"));
    }
}
