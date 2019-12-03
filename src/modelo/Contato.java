package modelo;

public class Contato {
	protected int id;
	protected String nome;
	protected String email;
	protected String endereco;
	protected String dataNascimento;
	
	public Contato() {}
	public Contato(String nome, String email, String endereco,
			String dataNascimento) {
		this.nome = nome;
		this.email = email;
		this.endereco = endereco;
		this.dataNascimento = dataNascimento;
	}
	
	// metodos get e set para id, nome, email, endereco e dataNascimento
	public String getNome() {
		return this. nome;
	}
	public void setNome(String novo) {
		this. nome = novo;
	}
	public String getEmail() {
		return this. email;
	}
	public void setEmail(String novo) {
		this. email = novo;
	}
	public String getEndereco() {
		return this. endereco;
	}
	public void setEndereco(String novo) {
		this. endereco = novo;
	}
	public int getId() {
		return this. id;
	}
	public void setId(int novo) {
		this. id = novo;
	}
	public String getDataNascimento() {
		return this. dataNascimento;
	}
	public void setDataNascimento(String dataNascimento) {
		this. dataNascimento = dataNascimento;
	}
	@Override
	public String toString() {
		return "Contato {id=" + id + ", nome=" + nome + ", email=" + email + ", endereco=" + endereco
				+ ", dataNascimento=" + dataNascimento + "}";
	}
}