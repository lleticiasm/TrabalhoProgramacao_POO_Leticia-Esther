import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter FORMATADOR_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final List<Cliente> clientes = new ArrayList<>();
    private static final List<Produtos> produtos = new ArrayList<>();
    private static final List<Vendas> vendas = new ArrayList<>();

    public static void main(String[] args) {
        carregarDadosIniciais();
        int opcao;
        do {
            menu();
            opcao = lerInt("Escolha uma opcao: ");
            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    cadastrarProduto();
                    break;
                case 3:
                    listarClientes();
                    break;
                case 4:
                    listarProdutos();
                    break;
                case 5:
                    realizarVenda();
                    break;
                case 6:
                    listarHistorico();
                    break;
                case 0:
                    System.out.println("Sistema encerrado.");
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);
    }

    private static void menu() {
        System.out.println();
        System.out.println("==== Sistema de Vendas ====");
        System.out.println("1 - Cadastrar cliente");
        System.out.println("2 - Cadastrar produto");
        System.out.println("3 - Listar clientes");
        System.out.println("4 - Listar produtos");
        System.out.println("5 - Realizar venda");
        System.out.println("6 - Historico de vendas por cliente");
        System.out.println("0 - Sair");
    }

    private static void cadastrarCliente() {
        int id = clientes.size() + 1;
        String cpf = lerTexto("CPF: ");
        String nome = lerTexto("Nome: ");
        String endereco = lerTexto("Endereco: ");
        clientes.add(new Cliente(id, cpf, nome, endereco));
        System.out.println("Cliente cadastrado com sucesso.");
    }

    private static void cadastrarProduto() {
        int id = produtos.size() + 1;
        System.out.println("Tipo do produto:");
        System.out.println("1 - Alimenticio");
        System.out.println("2 - Produto quimico");
        int tipo = lerInt("Escolha: ");

        String nome = lerTexto("Nome: ");
        double preco = lerDouble("Preco: R$ ");
        LocalDate validade = lerData("Data de validade (dd/MM/yyyy): ");
        String codigo = lerTexto("Codigo de barras: ");
        double desconto = lerDouble("Desconto (%): ");

        if (tipo == 1) {
            boolean perecivel = lerBoolean("Perecivel");
            produtos.add(new Alimenticio(id, nome, preco, validade, codigo, desconto, perecivel));
            System.out.println("Produto alimenticio cadastrado.");
        } else if (tipo == 2) {
            boolean toxico = lerBoolean("Toxico");
            produtos.add(new ProdutoQuimico(id, nome, preco, validade, codigo, desconto, toxico));
            System.out.println("Produto quimico cadastrado.");
        } else {
            System.out.println("Tipo invalido. Produto nao cadastrado.");
        }
    }

    private static void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
    }

    private static void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }
        for (Produtos produto : produtos) {
            System.out.println(produto);
        }
    }

    private static void realizarVenda() {
        if (clientes.isEmpty() || produtos.isEmpty()) {
            System.out.println("Cadastre ao menos um cliente e um produto antes de vender.");
            return;
        }

        listarClientes();
        Cliente cliente = buscarCliente(lerInt("ID do cliente: "));
        if (cliente == null) {
            System.out.println("Cliente nao encontrado.");
            return;
        }

        Vendas venda = new Vendas(cliente);
        int idProduto;
        do {
            listarProdutos();
            idProduto = lerInt("ID do produto para adicionar (0 para terminar): ");
            if (idProduto != 0) {
                Produtos produto = buscarProduto(idProduto);
                if (produto == null) {
                    System.out.println("Produto nao encontrado.");
                } else {
                    venda.adicionarProduto(produto);
                    System.out.println("Produto adicionado.");
                }
            }
        } while (idProduto != 0);

        if (venda.getListaProdutos().isEmpty()) {
            System.out.println("Venda cancelada: nenhum produto informado.");
            return;
        }

        double total = venda.calcularTotal(venda.getListaProdutos());
        System.out.println("Total da venda antes do pagamento: R$ " + String.format("%.2f", total));
        System.out.println("Forma de pagamento:");
        System.out.println("1 - Cartao (taxa de 2%)");
        System.out.println("2 - Dinheiro (desconto de 5%)");
        int opcaoPagamento = lerInt("Escolha: ");

        Pagamento pagamento;
        if (opcaoPagamento == 1) {
            pagamento = new PagamentoCartao();
        } else if (opcaoPagamento == 2) {
            pagamento = new PagamentoDinheiro();
        } else {
            System.out.println("Pagamento invalido. Venda cancelada.");
            return;
        }

        venda.finalizar(pagamento);
        vendas.add(venda);
        System.out.println("Venda finalizada. Total pago: R$ " + String.format("%.2f", venda.getTotalPago()));
    }

    private static void listarHistorico() {
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda finalizada.");
            return;
        }
        listarClientes();
        int idCliente = lerInt("ID do cliente: ");
        List<Vendas> historico = Vendas.historicoVenda(idCliente, vendas);
        if (historico.isEmpty()) {
            System.out.println("Nenhuma venda encontrada para esse cliente.");
            return;
        }
        for (Vendas venda : historico) {
            System.out.println(venda);
            for (Produtos produto : venda.getListaProdutos()) {
                System.out.println("  - " + produto.getNomeProduto() + ": R$ " + String.format("%.2f", produto.calcularPrecoFinal()));
            }
        }
    }

    private static Cliente buscarCliente(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getIdCliente() == id) {
                return cliente;
            }
        }
        return null;
    }

    private static Produtos buscarProduto(int id) {
        for (Produtos produto : produtos) {
            if (produto.getIdProduto() == id) {
                return produto;
            }
        }
        return null;
    }

    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    private static int lerInt(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException erro) {
                System.out.println("Digite um numero inteiro valido.");
            }
        }
    }

    private static double lerDouble(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException erro) {
                System.out.println("Digite um valor numerico valido.");
            }
        }
    }

    private static LocalDate lerData(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return LocalDate.parse(scanner.nextLine().trim(), FORMATADOR_DATA);
            } catch (DateTimeParseException erro) {
                System.out.println("Digite a data no formato dd/MM/yyyy.");
            }
        }
    }

    private static boolean lerBoolean(String campo) {
        while (true) {
            String resposta = lerTexto(campo + " (s/n): ").toLowerCase();
            if (resposta.equals("s") || resposta.equals("sim")) {
                return true;
            }
            if (resposta.equals("n") || resposta.equals("nao")) {
                return false;
            }
            System.out.println("Responda com s ou n.");
        }
    }

    private static void carregarDadosIniciais() {
        clientes.add(new Cliente(1, "111.111.111-11", "Maria Silva", "Rua Central, 100"));
        clientes.add(new Cliente(2, "222.222.222-22", "Joao Santos", "Av. Brasil, 200"));

        produtos.add(new Alimenticio(1, "Arroz 5kg", 28.90, LocalDate.now().plusMonths(8), "789100000001", 3, false));
        produtos.add(new Alimenticio(2, "Iogurte", 6.50, LocalDate.now().plusDays(5), "789100000002", 0, true));
        produtos.add(new ProdutoQuimico(3, "Agua sanitaria", 9.90, LocalDate.now().plusMonths(12), "789100000003", 2, true));
    }
}

