package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.ConnectionFactory;
import modelo.*;

public class ProdutoDAO {
	// a conexao com o banco de dados
	private Connection connection;

	public ProdutoDAO() {
		try{
			this.connection = ConnectionFactory.getConnection();
		}
		catch(Exception e)
		{
			System.out.println("Erro na abertura do banco de dados: " + e.getMessage());
		}
	}

	public List<Produto> getListar(){
		try{
			PreparedStatement stmt = this.connection.prepareStatement("select * from produtos");
			ResultSet rs = stmt.executeQuery();

			List<Produto> produtos = new ArrayList<Produto>();

			while (rs.next()) {
				// criando o objeto Produto
				Produto produto = new Produto();
				produto.setCodigo(rs.getInt("codigo"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setValor(rs.getFloat("valor"));
				produto.setEstoque(rs.getFloat("estoque"));
				
				// adicionando o objeto a lista
				produtos.add(produto);
			}

			rs.close();
			stmt.close();
			return produtos;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int adiciona(Produto produto) {
                int id = -1;
		String sql = "insert into produtos (descricao,valor,estoque) values (?,?,?)";
		try {
			// prepared statement para insercao
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setString(1,produto.getDescricao());
			stmt.setFloat(2,produto.getValor());
			stmt.setFloat(3,produto.getEstoque());

			// executa
			stmt.execute();
                        ResultSet rs = stmt.getGeneratedKeys();
                        if (rs.next()){
                            id=rs.getInt(1);
                        }
                        rs.close();
			stmt.close();

		} 	catch (SQLException e) {
			throw new RuntimeException(e);
		}
                return id;
	}
}