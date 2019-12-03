package teste;

//import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import java.util.ArrayList;
import java.util.List;
import java.awt.EventQueue;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import dao.ProdutoDAO;
import modelo.Produto;

public class CrudProduto extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tf_descricao;
	private JTextField tf_valor;
	private JTextField tf_quantidade;
	private JTextField tf_ID;
	private JButton btnInserir, btnLimpar,
		btnVoltar, btnAvancar, btnAtualizar, btnRemover;
	private ProdutoDAO dao;
	private List<Produto> lista;
	private int cursor;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
                        @Override
			public void run() {
				try {
					CrudProduto frame = new CrudProduto();
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
	public CrudProduto() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 490, 319);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDescricao = new JLabel("Descrição:");
		lblDescricao.setBounds(25, 41, 75, 14);
		contentPane.add(lblDescricao);
		
		JLabel lblValor = new JLabel("Valor:");
		lblValor.setBounds(25, 82, 104, 14);
		contentPane.add(lblValor);
		
		JLabel lblQuantidade = new JLabel("Quantidade:");
		lblQuantidade.setBounds(25, 118, 122, 14);
		contentPane.add(lblQuantidade);
		
		tf_descricao = new JTextField();
		tf_descricao.setBounds(184, 39, 240, 20);
		contentPane.add(tf_descricao);
		tf_descricao.setColumns(10);
		
		tf_valor = new JTextField();
		tf_valor.setBounds(184, 80, 240, 20);
		contentPane.add(tf_valor);
		tf_valor.setColumns(10);
		
		tf_quantidade = new JTextField();
		tf_quantidade.setBounds(184, 116, 240, 20);
		contentPane.add(tf_quantidade);
		tf_quantidade.setColumns(10);
		
		btnInserir = new JButton("Inserir");
		btnInserir.setBounds(118, 190, 94, 25);
		btnInserir.addActionListener(this);
		contentPane.add(btnInserir);
				
		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(12, 190, 86, 25);
		btnLimpar.addActionListener(this);
		contentPane.add(btnLimpar);
		
		btnVoltar = new JButton("<<");
		btnVoltar.setBounds(116, 226, 96, 25);
		btnVoltar.addActionListener(this);
		contentPane.add(btnVoltar);
		
		btnAvancar = new JButton(">>");
		btnAvancar.setBounds(233, 227, 105, 23);
		btnAvancar.addActionListener(this);
		contentPane.add(btnAvancar);
		
		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.setBounds(234, 190, 104, 25);
		btnAtualizar.addActionListener(this);
		contentPane.add(btnAtualizar);
		
		btnRemover = new JButton("Remover");
		btnRemover.setBounds(361, 190, 104, 25);
		btnRemover.addActionListener(this);
		contentPane.add(btnRemover);
		
		JLabel lblId = new JLabel("Codigo:");
		lblId.setBounds(31, 12, 69, 15);
		contentPane.add(lblId);
		
		tf_ID = new JTextField();
		tf_ID.setEditable(false);
		tf_ID.setBounds(76, 10, 114, 19);
		contentPane.add(tf_ID);
		tf_ID.setColumns(10);
		
		dao = new ProdutoDAO();
		
		lista = (ArrayList<Produto>)dao.getListar();
		cursor = 0;
		carregaEstoque();
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnLimpar)
		{	limparTela();
		}
		else if(e.getSource() == btnInserir)
		{	dao.adiciona(new Produto(
                                        tf_descricao.getText(),
					Float.parseFloat(tf_valor.getText()),
                                        Float.parseFloat(tf_quantidade.getText())));
			JOptionPane.showMessageDialog(null, "Inserido com sucesso!");
			lista = (ArrayList<Produto>)dao.getListar();
			cursor = lista.size()-1;
			carregaEstoque();
		}
		else if(e.getSource() == btnAtualizar)
		{	if(janelaConfirmacao("Atualização")) {
				dao.altera(new Produto(
                                        //Integer.parseInt(tf_ID.getText()),
                                        tf_descricao.getText(), 
						Float.parseFloat(tf_valor.getText()),
                                                Float.parseFloat(tf_quantidade.getText())
						
                                                ));
			JOptionPane.showMessageDialog(null, "Inserido com sucesso!");
				lista = (ArrayList<Produto>)dao.getListar();
				carregaEstoque();
			}
		}
		else if(e.getSource() == btnVoltar)
		{	if(cursor > 0) cursor--;
			carregaEstoque();
		}
		else if(e.getSource() == btnAvancar)
		{	if(cursor < lista.size()) cursor++;
			carregaEstoque();
		}
		else if(e.getSource() == btnRemover)
		{	if(janelaConfirmacao("Exclusão")) {
				dao.remove(Integer.parseInt(tf_ID.getText()));
				lista = (ArrayList<Produto>)dao.getListar();
				if(cursor > 0) cursor--;
				carregaEstoque();
			}
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
		tf_descricao.setText("");
		tf_valor.setText("");
		tf_quantidade.setText("");
		tf_ID.setText("");
	}
	
	private void carregaEstoque() {
		if(cursor >= lista.size()) limparTela();
		else {
			tf_descricao.setText(lista.get(cursor).getDescricao());
			tf_valor.setText(lista.get(cursor).getValor() + "");
			tf_quantidade.setText(lista.get(cursor).getEstoque() + "");
			tf_ID.setText("" + lista.get(cursor).getCodigo());
		}
	}
	
	public void irParaID(Long id) {
		for(int i=0; i<lista.size(); i++)
			if(lista.get(i).getCodigo() == id) {
				cursor = i;
				carregaEstoque();
				break;
			}
	}

}
