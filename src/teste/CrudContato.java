package teste;

import dao.ClienteDAO;
import dao.ContatoDAO;
import java.util.ArrayList;
import java.util.List;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import modelo.Contato;
//contato não tem cpf, não compra
public class CrudContato extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tf_nome;
	private JTextField tf_email;
	private JTextField tf_endereco;
	private JTextField tf_datanasc;
	private JTextField tf_ID;
        
	private JButton btnInserir, btnLimpar,
		btnVoltar, btnAvancar, btnAtualizar, btnRemover, btnVoltarInicio;
	private ContatoDAO dao;
	private List<Contato> lista;
	private int cursor;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CrudContato frame = new CrudContato();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Criar o frame
	 */
	public CrudContato() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 490, 335);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(25, 41, 75, 14);
		contentPane.add(lblNome);
		
		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setBounds(25, 82, 104, 14);
		contentPane.add(lblEmail);
		
		JLabel lblEndereo = new JLabel("Endere\u00E7o:");
		lblEndereo.setBounds(25, 118, 122, 14);
		contentPane.add(lblEndereo);
		
		JLabel lblDataDeNascimento = new JLabel("Data de Nascimento:");
		lblDataDeNascimento.setBounds(25, 150, 151, 14);
		contentPane.add(lblDataDeNascimento);
               
		tf_nome = new JTextField();
		tf_nome.setBounds(184, 39, 240, 28);
		contentPane.add(tf_nome);
		tf_nome.setColumns(10);
		
		tf_email = new JTextField();
		tf_email.setBounds(184, 80, 240, 28);
		contentPane.add(tf_email);
		tf_email.setColumns(10);
		
		tf_endereco = new JTextField();
		tf_endereco.setBounds(184, 116, 240, 28);
		contentPane.add(tf_endereco);
		tf_endereco.setColumns(10);
		
		tf_datanasc = new JTextField();
		tf_datanasc.setBounds(184, 148, 240, 28);
		contentPane.add(tf_datanasc);
		tf_datanasc.setColumns(10);
                
		btnInserir = new JButton("Inserir");
		btnInserir.setBounds(118, 210, 94, 25);
		btnInserir.addActionListener(this);
		contentPane.add(btnInserir);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(12, 210, 86, 25);
		btnLimpar.addActionListener(this);
		contentPane.add(btnLimpar);
		
		btnVoltar = new JButton("<<");
		btnVoltar.setBounds(116, 246, 96, 25);
		btnVoltar.addActionListener(this);
		contentPane.add(btnVoltar);
		
		btnAvancar = new JButton(">>");
		btnAvancar.setBounds(233, 247, 105, 23);
		btnAvancar.addActionListener(this);
		contentPane.add(btnAvancar);
		
		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setBounds(234, 210, 104, 25);
		btnAtualizar.addActionListener(this);
		contentPane.add(btnAtualizar);
		
		
		btnRemover = new JButton("Remover");
		btnRemover.setBounds(361, 210, 104, 25);
		btnRemover.addActionListener(this);
		contentPane.add(btnRemover);
                
                btnVoltarInicio = new JButton("Tela inicial");
		btnVoltarInicio.setBounds(361, 246, 104, 25);
		btnVoltarInicio.addActionListener(this);
		contentPane.add(btnVoltarInicio);
		
		JLabel lblId = new JLabel("ID:");
		lblId.setBounds(31, 12, 69, 15);
		contentPane.add(lblId);
		
		tf_ID = new JTextField();
		tf_ID.setEditable(false);
		tf_ID.setBounds(76, 10, 114, 27);
		contentPane.add(tf_ID);
		tf_ID.setColumns(10);
		
		dao = new ContatoDAO();
		
		lista = (ArrayList<Contato>)dao.getListar();
		cursor = 0;
		carregaAgenda();
                URL caminhoIcone = getClass().getResource("/imagem/user_edit.png");
                Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(caminhoIcone);
                this.setIconImage(iconeTitulo);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnLimpar)
		{	limparTela();
		}
		else if(e.getSource() == btnInserir)
		{	dao.adiciona(new Contato(tf_nome.getText(),
					tf_email.getText(), tf_endereco.getText(),
					tf_datanasc.getText()));
			JOptionPane.showMessageDialog(null, "Inserido com sucesso!");
			lista = (ArrayList<Contato>)dao.getListar();
			cursor = lista.size()-1;
			carregaAgenda();
		}
		else if(e.getSource() == btnAtualizar)
		{	if(janelaConfirmacao("Atualização")) {
                        Contato cl = lista.get(cursor);
			cl.setDataNascimento(tf_datanasc.getText());
                        cl.setEmail(tf_email.getText());
                        cl.setEndereco(tf_endereco.getText());
                        cl.setNome(tf_nome.getText());
                        dao.altera(cl);
                                					
			JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
			carregaAgenda();
			}
		}
		else if(e.getSource() == btnVoltar)
		{	if(cursor > 0) cursor--;
			carregaAgenda();
		}
		else if(e.getSource() == btnAvancar)
		{	if(cursor < lista.size()) cursor++;
			carregaAgenda();
		}
		else if(e.getSource() == btnRemover)
		{	if(janelaConfirmacao("Exclusão")) {
				dao.remove(Integer.parseInt(tf_ID.getText()));
				lista = (ArrayList<Contato>)dao.getListar();
				if(cursor > 0) cursor--;
				carregaAgenda();
			}
		}
                else if(e.getSource() == btnVoltarInicio)
		{	
                    new TelaInicial().setVisible(true);
                    dispose();
		}
		
	}
	
	private boolean janelaConfirmacao(String evento) {
		Object[] options = {"Confirmar", "Cancelar"};
	    int escolha = JOptionPane.showOptionDialog(null,
	    		"Confirma a " + evento + "?", "Agenda",
	          JOptionPane.YES_NO_OPTION,
	          JOptionPane.QUESTION_MESSAGE,
	          null, options, options[0]);
	    return escolha == 0;
		
	}
	
	private void limparTela() {
		tf_nome.setText("");
		tf_email.setText("");
		tf_endereco.setText("");
		tf_datanasc.setText("");
		tf_ID.setText("");
                
	}
	
	private void carregaAgenda() {
		if(cursor >= lista.size()) limparTela();
		else {
			tf_nome.setText(lista.get(cursor).getNome());
			tf_email.setText(lista.get(cursor).getEmail());
			tf_endereco.setText(lista.get(cursor).getEndereco());
			tf_datanasc.setText(lista.get(cursor).getDataNascimento());
			tf_ID.setText("" + lista.get(cursor).getId());
                        
		}
	}
	
	public void irParaID(Long id) {
		for(int i=0; i<lista.size(); i++)
			if(lista.get(i).getId() == id) {
				cursor = i;
				carregaAgenda();
				break;
			}
	}
}
