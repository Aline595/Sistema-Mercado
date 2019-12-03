package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.ConnectionFactory;
import modelo.*;

public class ItensCompradosDAO {
    // a conexao com o banco de dados

    private Connection connection;

    public ItensCompradosDAO() {
        try {
            this.connection = ConnectionFactory.getConnection();
        } catch (Exception e) {
            System.out.println("Erro na abertura do banco de dados: " + e.getMessage());
        }
    }

    public void lerItens(Compra compra) {
        try {
            PreparedStatement stmt = this.connection.prepareStatement(
"SELECT p.codigo, p.descricao, p.valor, p.estoque, cprod.quantidade " +
"FROM compras cmp, COMPRAS_PRODUTOS cprod, PRODUTOS p " +
"WHERE cprod.codigoproduto = p.codigo " +
"AND cprod.codigocompra = cmp.codigo " +
"AND cmp.codigo = ? ;");
            stmt.setInt(1, compra.getCodigo());
            ResultSet rs = stmt.executeQuery();

            ArrayList<Produto> itensComprados = new ArrayList<Produto>();
            ArrayList<Float> quantidades = new ArrayList<Float>();
            compra.setItensComprados(itensComprados);
            compra.setQuantidades(quantidades);

            while (rs.next()) {
                Produto p = new Produto();
                p.setCodigo(rs.getInt("codigo"));
                p.setValor(rs.getFloat("valor"));
                p.setDescricao(rs.getString("descricao"));
                p.setEstoque(rs.getFloat("estoque")); //quantidade em estoque
                itensComprados.add(p);
                
                quantidades.add(rs.getFloat("quantidade"));//quantidade comprada
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void gravaItens(Compra compra) {
        System.out.println(compra);
        for (int i = 0; i < compra.getItensComprados().size(); i++) {
            String sql = "insert into compras_produtos (quantidade,codigoCompra,codigoProduto) values (?,?,?)";
            try {
                // prepared statement para insercao
                PreparedStatement stmt = connection.prepareStatement(sql);

                stmt.setFloat(1, compra.getQuantidades().get(i));
                stmt.setInt(2, compra.getCodigo());
                stmt.setInt(3, compra.getItensComprados().get(i).getCodigo());

                // executa
                stmt.execute();
                stmt.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
