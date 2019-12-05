package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.ConnectionFactory;
import modelo.Contato;

public class ContatoDAO {
	// a conexao com o banco de dados
	private Connection connection;

	public ContatoDAO() {
		try{
			this.connection = ConnectionFactory.getConnection();
		}
		catch(Exception e)
		{
			System.out.println("Erro na abertura do banco de dados: " + e.getMessage());
		}
	}

	public List<Contato> getListar(){
		try{
			PreparedStatement stmt = this.connection.prepareStatement("select * from contatos");
			ResultSet rs = stmt.executeQuery();

			List<Contato> contatos = new ArrayList<Contato>();

			while (rs.next()) {
				// criando o objeto Contato
				Contato contato = new Contato();
				contato.setId(rs.getInt("id"));
				contato.setNome(rs.getString("nome"));
				contato.setEmail(rs.getString("email"));
				contato.setEndereco(rs.getString("endereco"));
				contato.setDataNascimento(rs.getString("dataNascimento"));

				// adicionando o objeto a lista
				contatos.add(contato);
			}

			rs.close();
			stmt.close();
			return contatos;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int adiciona(Contato contato) {
            int id = -1;
            String sql = "insert into contatos (nome,email,endereco,dataNascimento) values (?,?,?,?)";
            try {
			// prepared statement para insercao
			PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setString(1,contato.getNome());
			stmt.setString(2,contato.getEmail());
			stmt.setString(3,contato.getEndereco());
			stmt.setString(4, contato.getDataNascimento());

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
	
	public List<Contato> getPesquisar(String nome){
		String sql = "select * from contatos where nome like ?";
		try{
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, '%' + nome + '%');
			ResultSet rs = stmt.executeQuery();

			List<Contato> contatos = new ArrayList<Contato>();

			while (rs.next()) {
				// criando o objeto Contato
				Contato contato = new Contato();
				contato.setId(rs.getInt("id"));
				contato.setNome(rs.getString("nome"));
				contato.setEmail(rs.getString("email"));
				contato.setEndereco(rs.getString("endereco"));
				contato.setDataNascimento(rs.getString("dataNascimento"));

				// adicionando o objeto a lista
				contatos.add(contato);
			}

			rs.close();
			stmt.close();
			return contatos;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void altera(Contato contato) {
		String sql = "update contatos set nome=?, email=?, endereco=?," +
				"dataNascimento=? where id=?";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, contato.getNome());
			stmt.setString(2, contato.getEmail());
			stmt.setString(3, contato.getEndereco());
			stmt.setString(4, contato.getDataNascimento());
			stmt.setInt(5, contato.getId());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void remove(int id) {
		try {
			PreparedStatement stmt = connection.prepareStatement("delete from contatos where id=?");
			stmt.setInt(1, id);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}