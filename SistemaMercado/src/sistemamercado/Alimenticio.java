import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Alimenticio extends Produtos {
    private final boolean perecivel;

    public Alimenticio(
            int idProduto,
            String nomeProduto,
            double precoProduto,
            LocalDate dataValidade,
            String codigoBarra,
            double descontoProduto,
            boolean perecivel) {
        super(idProduto, nomeProduto, precoProduto, dataValidade, "Alimenticio", codigoBarra, descontoProduto);
        this.perecivel = perecivel;
    }

    public boolean isPerecivel() {
        return perecivel;
    }

    public double calcularPrecoFinal(double precoProduto, int desconto, int dataValidade) {
        double descontoTotal = desconto;
        if (perecivel && dataValidade <= 7) {
            descontoTotal += 10;
        }
        return calcularPrecoFinal(precoProduto, descontoTotal);
    }

    @Override
    public double calcularPrecoFinal() {
        long diasAteValidade = ChronoUnit.DAYS.between(LocalDate.now(), getDataValidade());
        return calcularPrecoFinal(getPrecoProduto(), (int) getDescontoProduto(), (int) diasAteValidade);
    }

    @Override
    public String toString() {
        return super.toString() + " | Perecivel: " + (perecivel ? "sim" : "nao");
    }
}
