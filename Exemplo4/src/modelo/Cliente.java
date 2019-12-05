package modelo;
import java.util.ArrayList;

public class Cliente extends Contato{
    private String CPF;
    private ArrayList<Compra> compras;
    
    public Cliente(){}
    public Cliente(String nome, String email, String endereco,
	String dataNascimento, String CPF) {
        super(nome, email, endereco, dataNascimento);
        this.CPF = CPF;
        compras = new ArrayList<Compra>();
    }
    @Override
    public String toString() {
        return "Cliente{ id=" + id + ", nome=" + nome + ", email=" + email + ", endereco=" + endereco
				+ ", dataNascimento=" + dataNascimento + ", CPF=" + CPF + '}';
    }
    public String getCPF(){
        return CPF;
    }
    public void setCPF(String CPF){
        this.CPF = CPF;
    }
    public ArrayList<Compra> getCompras(){
        return compras;
    }
    public void setCompras(ArrayList<Compra> compras){
        this.compras = compras;
    }
}