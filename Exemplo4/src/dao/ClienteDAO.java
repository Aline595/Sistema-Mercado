package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.ConnectionFactory;
import modelo.*;

public class ClienteDAO {
	// a conexao com o banco de dados
	private Connection connection;

	public ClienteDAO() {
		try{
			this.connection = ConnectionFactory.getConnection();
		}
		catch(Exception e)
		{
			System.out.println("Erro na abertura do banco de dados: " + e.getMessage());
		}
	}

	public List<Cliente> getListar(){
		try{    PreparedStatement stmt = this.connection.prepareStatement(
                                "SELECT co.id, co.nome, co.email, co.endereco, co.dataNascimento, cl.cpf " +
                                "FROM contatos co, clientes cl " +
                                "WHERE co.id = cl.id;" );
			ResultSet rs = stmt.executeQuery();

			List<Cliente> clientes = new ArrayList<Cliente>();

			while (rs.next()) {
				// criando o objeto Contato
				Cliente cliente = new Cliente();
				cliente.setId(rs.getInt("id"));
				cliente.setNome(rs.getString("nome"));
				cliente.setEmail(rs.getString("email"));
				cliente.setEndereco(rs.getString("endereco"));
				cliente.setDataNascimento(rs.getString("dataNascimento"));
                                cliente.setCPF(rs.getString("cpf"));
				// adicionando o objeto a lista
				clientes.add(cliente);
			}

			rs.close();
			stmt.close();
			return clientes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int adiciona(Cliente cliente) {
            int id = -1;
            try {
                        PreparedStatement stmt = this.connection.prepareStatement(
                                "SELECT id, nome, email, endereco, dataNascimento " +
                                "FROM contatos WHERE nome = ? AND email = ? AND endereco = ? AND dataNascimento = ? ;" );
                        stmt.setString(1, cliente.getNome());
			stmt.setString(2, cliente.getEmail());
			stmt.setString(3, cliente.getEndereco());
			stmt.setString(4, cliente.getDataNascimento());
                        ResultSet rs = stmt.executeQuery();
                        
                        if (rs.next() == false) { //Não há contato cadastrado, então cadastre
                            String sql = "insert into contatos (nome,email,endereco,dataNascimento) values (?,?,?,?)";
                            // prepared statement para insercao
                            stmt = connection.prepareStatement(sql);
                            stmt.setString(1, cliente.getNome());
                            stmt.setString(2, cliente.getEmail());
                            stmt.setString(3, cliente.getEndereco());
                            stmt.setString(4, cliente.getDataNascimento());
                            stmt.execute();
                            rs = stmt.getGeneratedKeys();
                            if (rs.next()){
                                id=rs.getInt(1);
                            }
                        } else {//Há contato cadastrado
                            id = rs.getInt("id");                      
                        }
                        
                        String sql = "insert into clientes (id,cpf) values (?,?)";
                        stmt = connection.prepareStatement(sql);
                        stmt.setInt(1, id);
                        stmt.setString(2, cliente.getCPF());
			// executa
			stmt.execute();
                       
                        rs.close();
			stmt.close();

		} 	catch (SQLException e) {
			throw new RuntimeException(e);
		}
            return id;
	}
/*
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
*/
}