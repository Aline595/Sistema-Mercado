package modelo;

import java.util.ArrayList;
import java.util.Arrays;

public class Compra {
    private int codigo;
    private String data;
    private float valor;
    private ArrayList<Produto> itensComprados;
    private ArrayList<Float> quantidades;
    
    public Compra(){}
    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public float getValor() {
        return valor;
    }
    public void setValor(float valor) {
        this.valor = valor;
    }
    public ArrayList<Produto> getItensComprados() {
        return itensComprados;
    }
    public void setItensComprados(ArrayList<Produto> itensComprados) {
        this.itensComprados= itensComprados;
    }
    public ArrayList<Float> getQuantidades() {
        return quantidades;
    }
    public void setQuantidades(ArrayList<Float> quantidades) {
        this.quantidades = quantidades;
    }
    @Override
    public String toString() {
        String saida = "Compra\n" + "  cód.=" + codigo + "  data=" + data + "\n\n" +
                "QTDE.\tPRODUTO\tPREÇO UNITÁRIO\n";
        if(itensComprados != null)
        {   for(int i=0; i<itensComprados.size(); i++)
                saida += quantidades.get(i).toString() + "\t" +
                        itensComprados.get(i).getDescricao() + "\t" +
                        itensComprados.get(i).getValor() + "\n";
        }
        else saida += "---Sem itens comprados---";
        saida += "\n\nVALOR TOTAL = R$ " + valor + "\n";
        return saida;
    }
    
}
