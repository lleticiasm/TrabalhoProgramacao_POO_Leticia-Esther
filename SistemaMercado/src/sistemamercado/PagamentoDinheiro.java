public class PagamentoDinheiro implements Pagamento {
    @Override
    public double pagarValor(double valor) {
        double desconto = valor * 0.05;
        return valor - desconto;
    }

    public void pagar(double valor) {
        System.out.println("Pagamento em dinheiro registrado. Total com desconto: R$ " + String.format("%.2f", pagarValor(valor)));
    }

    public void operation19() {
        System.out.println("Operacao finalizada.");
    }
}
