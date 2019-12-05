package teste;
import modelo.*;
import dao.*;
import java.util.ArrayList;
import java.util.List;

public class Teste {
    public static void main(String args[])
    {   //Clientes
        ContatoDAO cdao = new ContatoDAO();
        cdao.adiciona(new Contato("Celso", "c@gmail.com", "Rua XYZ, 123", "01/01/2000"));
        cdao.adiciona(new Contato("Aline", "a@gmail.com", "Rua XYZ, 234", "01/01/2002"));
        cdao.adiciona(new Contato("Alexandre", "ale@gmail.com", "Rua XYZ, 678", "01/01/2001")); // O Alexandre será somente contato 
        
        ClienteDAO clidao = new ClienteDAO();
        clidao.adiciona(new Cliente("Celso", "c@gmail.com", "Rua XYZ, 123", "01/01/2000", "123456789"));
        clidao.adiciona(new Cliente("Aline", "a@gmail.com", "Rua XYZ, 234", "01/01/2002", "234567890"));
        clidao.adiciona(new Cliente("Marcelo", "m@gmail.com", "Rua ABC, 234", "01/01/1982", "3456789012")); //O Marcelo não era contato e foi adicionado direto como cliente
        
        //Estoque
        int id;
        ProdutoDAO pdao = new ProdutoDAO();
        Produto p1 = new Produto("Arroz Camil", 5.45f, 1000.0f);
        id = pdao.adiciona(p1);
        p1.setCodigo(id);
        Produto p2 = new Produto("Feijão Camil", 4.57f, 500.0f);
        id = pdao.adiciona(p2);
        p2.setCodigo(id);
        Produto p3 = new Produto("Carne Alcatra", 28.57f, 90.0f);
        id = pdao.adiciona(p3);
        p3.setCodigo(id);
        Produto p4 = new Produto("Salmão", 45.00f, 98.0f);
        id = pdao.adiciona(p4);
        p4.setCodigo(id);
        
        //Compra 1 da Aline (id=2)
        Compra compra1 = new Compra();
        compra1.setData("31/10/2019");
        ArrayList<Produto> itensCompradosAline1 = new ArrayList<Produto>();
        ArrayList<Float> quantCompradaAline1 = new ArrayList<Float>();
        itensCompradosAline1.add(p1); //Arroz Camil
        quantCompradaAline1.add(2f); //2 kg
        itensCompradosAline1.add(p2); //Feijão Camil
        quantCompradaAline1.add(1f); //1 kg
        itensCompradosAline1.add(p4); //Salmao Chileno
        quantCompradaAline1.add(2f); //2 kg
        compra1.setItensComprados(itensCompradosAline1);
        compra1.setQuantidades(quantCompradaAline1);
        compra1.setValor(2*5.45f + 1*4.57f + 2*45.00f);
        //armazenando a compra (sem os itens ainda)
        CompraDAO compradao = new CompraDAO();
        id = compradao.adiciona(compra1, 2); //id da Aline é 2
        compra1.setCodigo(id);
        //armazenando os itens comprados
        ItensCompradosDAO itensdao = new ItensCompradosDAO();
        itensdao.gravaItens(compra1);
        
        //Compra 2 da Aline (id=2)
        Compra compra2 = new Compra();
        compra2.setData("29/11/2019");
        ArrayList<Produto> itensCompradosAline2 = new ArrayList<Produto>();
        ArrayList<Float> quantCompradaAline2 = new ArrayList<Float>();
        itensCompradosAline2.add(p3); //Carne Alcatra
        quantCompradaAline2.add(1f); //1 kg
        itensCompradosAline2.add(p2); //Feijão Camil
        quantCompradaAline2.add(2f); //2 kg
        compra2.setItensComprados(itensCompradosAline2);
        compra2.setQuantidades(quantCompradaAline2);
        compra2.setValor(28.57f + 2*4.57f);
        //armazenando a compra (sem os itens ainda)
        id = compradao.adiciona(compra2, 2); //id da Aline é 2
        compra2.setCodigo(id);
        //armazenando os itens comprados
        itensdao.gravaItens(compra2);
        
        //Compra do Celso (id=1)
        Compra compra3 = new Compra();
        compra3.setData("03/11/2019");
        ArrayList<Produto> itensCompradosCelso = new ArrayList<Produto>();
        ArrayList<Float> quantCompradaCelso = new ArrayList<Float>();
        itensCompradosCelso.add(p1); //Arroz Camil
        quantCompradaCelso.add(3f); //3 kg
        compra3.setItensComprados(itensCompradosCelso);
        compra3.setQuantidades(quantCompradaCelso);
        compra3.setValor(3*5.45f);
        //armazenando a compra (sem os itens ainda)
        compradao = new CompraDAO();
        id = compradao.adiciona(compra3, 1); //id do Celso é 1
        compra3.setCodigo(id);
        //armazenando os itens comprados
        itensdao.gravaItens(compra3);
        
        //Compra do Marcelo (id=4)
        Compra compra4 = new Compra();
        compra4.setData("04/11/2019");
        ArrayList<Produto> itensCompradosMarcelo = new ArrayList<Produto>();
        ArrayList<Float> quantCompradaMarcelo = new ArrayList<Float>();
        itensCompradosMarcelo.add(p2); //Feijão Camil
        quantCompradaMarcelo.add(1f); //1 kg
        compra4.setItensComprados(itensCompradosMarcelo);
        compra4.setQuantidades(quantCompradaMarcelo);
        compra4.setValor(1*4.57f);
        //armazenando a compra (sem os itens ainda)
        compradao = new CompraDAO();
        id = compradao.adiciona(compra4, 4); //id do Marcelo é 4
        compra4.setCodigo(id);
        //armazenando os itens comprados
        itensdao.gravaItens(compra4);
        
        //Mostrando dados puxados do banco
        System.out.println("Contatos:");
        List<Contato> lc = cdao.getListar();
        for(Contato c: lc) System.out.println(c);
        
        System.out.println("\n\nClientes:");
        List<Cliente> lcli = clidao.getListar();
        for(Cliente cli: lcli) System.out.println(cli);
        
        System.out.println("\n\nProdutos:");
        List<Produto> lp = pdao.getListar();
        for(Produto p: lp) System.out.println(p);
        
        System.out.println("\n\nCompras da Aline:");
        List<Compra> lca = compradao.getListar(2); //id da Aline é 2
        for(Compra compra: lca) itensdao.lerItens(compra);
        for(Compra compra: lca) System.out.println(compra);
        
        System.out.println("\n\nCompras do Celso:");
        List<Compra> lcc = compradao.getListar(1); //id do Celso é 1
        for(Compra compra: lcc) itensdao.lerItens(compra);
        for(Compra compra: lcc) System.out.println(compra);
        
        System.out.println("\n\nCompras do Marcelo:");
        List<Compra> lcm = compradao.getListar(4); //id do Marcelo é 4
        for(Compra compra: lcm) itensdao.lerItens(compra);
        for(Compra compra: lcm) System.out.println(compra);
    }
}
