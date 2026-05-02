import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ProdutoQuimico extends Produtos {
    private final boolean toxico;

    public ProdutoQuimico(
            int idProduto,
            String nomeProduto,
            double precoProduto,
            LocalDate dataValidade,
            String codigoBarra,
            double descontoProduto,
            boolean toxico) {
        super(idProduto, nomeProduto, precoProduto, dataValidade, "Produto Quimico", codigoBarra, descontoProduto);
        this.toxico = toxico;
    }

    public boolean isToxico() {
        return toxico;
    }

    public double calcularPrecoFinal(double precoProduto, int descontoProduto, int dataVenda) {
        double acrescimo = toxico ? 5 : 0;
        double descontoExtra = dataVenda <= 30 ? 5 : 0;
        double precoComAcrescimo = precoProduto + (precoProduto * acrescimo / 100.0);
        return calcularPrecoFinal(precoComAcrescimo, descontoProduto + descontoExtra);
    }

    @Override
    public double calcularPrecoFinal() {
        long diasAteValidade = ChronoUnit.DAYS.between(LocalDate.now(), getDataValidade());
        return calcularPrecoFinal(getPrecoProduto(), (int) getDescontoProduto(), (int) diasAteValidade);
    }

    @Override
    public String toString() {
        return super.toString() + " | Toxico: " + (toxico ? "sim" : "nao");
    }
}
