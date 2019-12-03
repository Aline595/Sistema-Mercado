package modelo;

public class Produto {
    private int codigo;
    private String descricao;
    private float valor;
    private float estoque;

    public Produto( ) { }
    public Produto(String descricao, float valor, float estoque) {
        this.descricao = descricao;
        this.valor = valor;
        this.estoque= estoque;
    }
    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public float getValor() {
        return valor;
    }
    public void setValor(float valor) {
        this.valor = valor;
    }
    public float getEstoque() {
        return estoque;
    }
    public void setEstoque(float estoque) {
        this.estoque = estoque;
    }
    @Override
    public String toString() {
        return "Produto{" + "codigo=" + codigo + ", descricao=" + descricao + ", valor=" + valor + ", estoque=" + estoque + '}';
    }
}
