package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.ConnectionFactory;
import modelo.*;

public class CompraDAO {
    // a conexao com o banco de dados

    private Connection connection;

    public CompraDAO() {
        try {
            this.connection = ConnectionFactory.getConnection();
        } catch (Exception e) {
            System.out.println("Erro na abertura do banco de dados: " + e.getMessage());
        }
    }

    public List<Compra> getListar(int idCliente) {
        try {
            PreparedStatement stmt = this.connection.prepareStatement(
                    "select * from compras where idCliente = ?");
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            List<Compra> compras = new ArrayList<Compra>();

            while (rs.next()) {
                // criando o objeto Produto
                Compra compra = new Compra();
                compra.setCodigo(rs.getInt("codigo"));
                compra.setData(rs.getString("data"));
                compra.setValor(rs.getFloat("valor"));
                // adicionando o objeto a lista
                compras.add(compra);
            }

            rs.close();
            stmt.close();
            return compras;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int adiciona(Compra compra, int idCliente) {
        int id = -1;
        String sql = "insert into compras (valor,data,idCliente) values (?,?,?)";
        try {
            // prepared statement para insercao
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setFloat(1, compra.getValor());
            stmt.setString(2, compra.getData());
            stmt.setFloat(3, idCliente);

            // executa
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()){
                id=rs.getInt(1);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
}
