import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Vendas {
    private final Cliente cliente;
    private final List<Produtos> listaProdutos;
    private final LocalDate dataVenda;
    private String status;
    private Pagamento pagamento;
    private double totalPago;

    public Vendas(Cliente cliente) {
        this.cliente = cliente;
        this.listaProdutos = new ArrayList<>();
        this.dataVenda = LocalDate.now();
        this.status = "Aberta";
    }

    public void adicionarProduto(Produtos produto) {
        listaProdutos.add(produto);
    }

    public double calcularTotal(List<Produtos> produtos) {
        double total = 0;
        for (Produtos produto : produtos) {
            total += produto.calcularPrecoFinal();
        }
        return total;
    }

    public static List<Vendas> historicoVenda(int idCliente, List<Vendas> vendas) {
        List<Vendas> historico = new ArrayList<>();
        for (Vendas venda : vendas) {
            if (venda.getCliente().getIdCliente() == idCliente) {
                historico.add(venda);
            }
        }
        return historico;
    }

    public void finalizar(Pagamento pagamento) {
        this.pagamento = pagamento;
        this.totalPago = pagamento.pagarValor(calcularTotal(listaProdutos));
        this.status = "Finalizada";
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<Produtos> getListaProdutos() {
        return Collections.unmodifiableList(listaProdutos);
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public String getStatus() {
        return status;
    }

    public double getTotalPago() {
        return totalPago;
    }

    public String getFormaPagamento() {
        if (pagamento == null) {
            return "Nao definida";
        }
        return pagamento instanceof PagamentoCartao ? "Cartao" : "Dinheiro";
    }

    @Override
    public String toString() {
        return "Cliente: " + cliente.getNomeCliente()
                + " | Data: " + dataVenda
                + " | Produtos: " + listaProdutos.size()
                + " | Status: " + status
                + " | Pagamento: " + getFormaPagamento()
                + " | Total pago: R$ " + String.format("%.2f", totalPago);
    }
}
