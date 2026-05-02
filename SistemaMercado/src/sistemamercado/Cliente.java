public class Cliente {
    private final int idCliente;
    private final String cpfCliente;
    private final String nomeCliente;
    private final String enderecoCliente;

    public Cliente(int idCliente, String cpfCliente, String nomeCliente, String enderecoCliente) {
        this.idCliente = idCliente;
        this.cpfCliente = cpfCliente;
        this.nomeCliente = nomeCliente;
        this.enderecoCliente = enderecoCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getEnderecoCliente() {
        return enderecoCliente;
    }

    @Override
    public String toString() {
        return idCliente + " - " + nomeCliente + " | CPF: " + cpfCliente + " | Endereco: " + enderecoCliente;
    }
}
