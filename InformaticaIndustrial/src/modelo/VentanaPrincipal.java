package modelo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;

public class VentanaPrincipal extends JFrame {

	private JPanel contentPane;
	private ArrayList<JLabel> listaLb= new ArrayList<>();
	private ArrayList<GridBagConstraints> listaGrid = new ArrayList<>();
	private int i=0;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal frame = new VentanaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 568, 371);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		
	}
	
	public void ArmaGrilla(Nodo nodo,Integer x, Integer y)
	{
		//FALTA IR VARIANDO LOS lb_hijo SEGUN VA ITERANDO
			listaLb.add(new JLabel("Padre" + nodo.GetValor()));
			listaGrid.add(new GridBagConstraints());
			listaGrid.get(i).gridx = x;
			listaGrid.get(i).gridy = y;
			contentPane.add(listaLb.get(i), listaGrid.get(i));
			i++;
			while (nodo.GetHijos()!=null && i<nodo.GetHijos().size())
			{
				for (Nodo hijo: nodo.GetHijos())
				{
					ArmaGrilla(hijo,x+1,y+1);
					y++;
				}
			}
		return;
	}
}
