import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Produtos {
    private final int idProduto;
    private final String nomeProduto;
    private final double precoProduto;
    private final LocalDate dataValidade;
    private final String tipoProduto;
    private final String codigoBarra;
    private final double descontoProduto;

    public Produtos(
            int idProduto,
            String nomeProduto,
            double precoProduto,
            LocalDate dataValidade,
            String tipoProduto,
            String codigoBarra,
            double descontoProduto) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.precoProduto = precoProduto;
        this.dataValidade = dataValidade;
        this.tipoProduto = tipoProduto;
        this.codigoBarra = codigoBarra;
        this.descontoProduto = descontoProduto;
    }

    public double calcularPrecoFinal(double preco, double desconto) {
        return Math.max(0, preco - (preco * desconto / 100.0));
    }

    public abstract double calcularPrecoFinal();

    public int getIdProduto() {
        return idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public double getPrecoProduto() {
        return precoProduto;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public String getTipoProduto() {
        return tipoProduto;
    }

    public String getCodigoBarra() {
        return codigoBarra;
    }

    public double getDescontoProduto() {
        return descontoProduto;
    }

    protected String dataFormatada() {
        return dataValidade.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @Override
    public String toString() {
        return idProduto + " - " + nomeProduto
                + " | Tipo: " + tipoProduto
                + " | Preco: R$ " + String.format("%.2f", precoProduto)
                + " | Desconto: " + String.format("%.1f", descontoProduto) + "%"
                + " | Final: R$ " + String.format("%.2f", calcularPrecoFinal())
                + " | Validade: " + dataFormatada()
                + " | Codigo: " + codigoBarra;
    }
}
