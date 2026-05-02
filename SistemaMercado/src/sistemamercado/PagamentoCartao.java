public class PagamentoCartao implements Pagamento {
    @Override
    public double pagarValor(double valor) {
        double taxa = valor * 0.02;
        return valor + taxa;
    }

    public void pagar(double valor) {
        System.out.println("Pagamento no cartao aprovado. Total com taxa: R$ " + String.format("%.2f", pagarValor(valor)));
    }
}
