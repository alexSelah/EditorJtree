
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Highlighter;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;


	/**
	 * Clase que busca, buscar siguiente y reemplaza en un texto de un JTextPane 
	 * pasado como argumento. Esta clase es aut�noma respecto al Editor
	 * 
	 * @author Alejandro Campos Fuentes 
	 * alex.selah@yahoo.es 
	 * \u0040 2013
	 * 
	 * 		Este programa se distribuye bajo licencia p\u00fablica GNU-GPL. Forma parte del programa completo llamado
	 * Editor de texto con explorador en \u00e1rbol.
	 * Eres libre de copiar, distribur y mejorar el programa bajo los t\u00e9rminos de la licencia de la 
	 * Free Software Foundation. Ante cualquier duda, p\u00f3ngase en contacto conmigo en el email arriba.
	 * 
	 * Puede ver una traducci\u00f3n de la licencia GNU-GPL en espa\u00F1ol en el siguiente enlace:
	 * http://www.viti.es/gnu/licenses/gpl.html
	 * 
	 * This file is part of Editor de texto con explorador en \u00e1rbol.
	 * 
	 *     Editor de texto con explorador en arbol is free software: you can redistribute it and/or modify
	 * it under the terms of the GNU General Public License as published by
	 * the Free Software Foundation, either version 3 of the License, or
	 * (at your option) any later version.
	 * 
	 * 		Editor de texto con explorador en arbol is distributed in the hope that it will be useful,
	 * but WITHOUT ANY WARRANTY; without even the implied warranty of
	 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	 * GNU General Public License for more details.
	 * 
	 * 		You should have received a copy of the GNU General Public License
	 * along with Editor de texto con explorador en arbol.  If not, see <http://www.gnu.org/licenses/>.
	 * 
	 */
public class BuscaYReemplaza extends JDialog implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JTextPane texto;
	private JLabel lblBuscar, lblReemplazar;
	private JTextField txtBuscar, txtReemplazar;
	private JButton btnBuscar, btnBuscarSiguiente, btnReemplazar, btnReemplazaTodo, cerrar;
	private JComboBox<String> comboColor;

	
	//para subrayar y buscar
	String textoBuscar, textoReemplazar;
	Highlighter highlighter;
	HTMLDocument document;
	HTMLDocument.Iterator it;
	Pattern patron;
	Matcher matcher;
	String fragmento;
	int PvezPulsado;
	DefaultHighlightPainter colorSub;
	
	//variables de depuracion
	int i,j,a,b;

	/**
	 * Contructor de la clase BuscayReemplaza
	 * 
	 * @param panelTexto es el JtextPane que contiene el texto a buscar. 
	 * realizara los cambios (reemplazar) sobre este panel.
	 */
	public BuscaYReemplaza ( JTextPane panelTexto){
		texto = panelTexto;
		iniciarComponentes();
		//txtBuscar.requestFocus();
		this.setLocationRelativeTo(null);
		this.setSize(400, 250);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setAlwaysOnTop(true);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent arg0) {
				txtBuscar.requestFocus();
			}
	
		});
		

	}
	
	private void iniciarComponentes(){
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.LIGHT_GRAY);
		
		//etiquetas
		lblBuscar = new JLabel();
		lblBuscar.setText("Palabra a buscar:");
		lblReemplazar = new JLabel();
		lblReemplazar.setText("Reemplazar por:");
		//areas de texto
		txtBuscar = new JTextField();
		txtReemplazar = new JTextField();
		//area de opciones
		comboColor = new JComboBox<String>();
		colorSub = new DefaultHighlighter.DefaultHighlightPainter(Color.LIGHT_GRAY);
		comboColor.addItem("Color de busqueda"); //0
		comboColor.addItem("Rojo");//1
		comboColor.addItem("Amarillo");//2
		comboColor.addItem("Azul");//3
		comboColor.addItem("Cian");//4
		comboColor.addItem("Gris");//5
		comboColor.addItem("Magenta");//6
		comboColor.addItem("Gris oscuro");//7
		comboColor.addItem("Naranja");//8
		comboColor.addItem("Verde");//9
		comboColor.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				int elegido;
				elegido=comboColor.getSelectedIndex();
				switch (elegido) {
					case 0:
						colorSub = new DefaultHighlighter.DefaultHighlightPainter(Color.LIGHT_GRAY);
						break;
					case 1:
						colorSub = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);
						break;
					case 2:
						colorSub = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
						break;
					case 3:
						colorSub = new DefaultHighlighter.DefaultHighlightPainter(Color.BLUE);
						break;
					case 4:
						colorSub = new DefaultHighlighter.DefaultHighlightPainter(Color.CYAN);
						break;
					case 5:
						colorSub = new DefaultHighlighter.DefaultHighlightPainter(Color.GRAY);
						break;
					case 6:
						colorSub = new DefaultHighlighter.DefaultHighlightPainter(Color.MAGENTA);
						break;
					case 7:
						colorSub = new DefaultHighlighter.DefaultHighlightPainter(Color.DARK_GRAY);
						break;
					case 8:
						colorSub = new DefaultHighlighter.DefaultHighlightPainter(Color.ORANGE);
						break;
					case 9:
						colorSub = new DefaultHighlighter.DefaultHighlightPainter(Color.GREEN);
						break;
				}//fin de  switch
			}
		});//fin de additemlistener
		
		//Ponemos las cajas de texto y los label
		lblBuscar.setBounds(10, 10, 150, 21);
		panel.add(lblBuscar);
		txtBuscar.setBounds(10, 30, 150, 21);
		txtBuscar.requestFocus();
		panel.add(txtBuscar);
		lblReemplazar.setBounds(10, 60, 150, 21);
		panel.add(lblReemplazar);
		txtReemplazar.setBounds(10, 80, 150, 21);
		panel.add(txtReemplazar);
		//ponemos las opciones de eleccion
		comboColor.setBounds(10, 120, 150, 21);
		panel.add(comboColor);
		
		//Botones
		btnBuscar = new JButton("Buscar", new ImageIcon(getClass().getResource("recursos/lupa.png")));
		btnBuscarSiguiente = new JButton("Buscar Siguiente", new ImageIcon(getClass().getResource("recursos/siguiente.png")));
		btnReemplazar = new JButton("Reemplazar", new ImageIcon(getClass().getResource("recursos/replace.png")));
		btnReemplazaTodo = new JButton("Reemplazar todo", new ImageIcon(getClass().getResource("recursos/replaceall.png")));
		
		btnBuscar.setBounds(200, 10, 180, 30);
		btnBuscar.addActionListener(this);
		btnBuscar.setToolTipText("Busca texto que coincida con el texto introducido bajo el recuado 'Buscar'");
		panel.add(btnBuscar);
		btnBuscarSiguiente.setBounds(200, 50, 180, 30);
		btnBuscarSiguiente.addActionListener(this);
		btnBuscarSiguiente.setToolTipText("Busca la siguiente coincidencia");
		panel.add(btnBuscarSiguiente);
		btnReemplazar.setBounds(200, 90 , 180, 30);
		btnReemplazar.addActionListener(this);
		btnReemplazar.setToolTipText("Reemplaza la primera palabra que coincida con el texto introducido");
		panel.add(btnReemplazar);
		btnReemplazaTodo.setBounds(200, 130, 180, 30);
		btnReemplazaTodo.addActionListener(this);
		btnReemplazaTodo.setToolTipText("Reemplaza todas las cadenas que coincidan con el texto introducido");
		panel.add(btnReemplazaTodo);
		
		cerrar = new JButton("CERRAR",new ImageIcon(getClass().getResource("recursos/xdibujo.png")));
		cerrar.setBounds(10, 180, 370, 30);
		cerrar.addActionListener(this);
		panel.add(cerrar);
		
		//Inicializamos las variables de busqueda

		PvezPulsado = 0;

		//A�adimos el panel a la ventana
		this.getContentPane().add(panel);
		this.setTitle("Buscar y Reemplazar");
		

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cerrar){
			this.setVisible(false);
		}//Fin de cerrar
		
		/**
		 * Boton de busqueda de texto. coge el texto a buscar del jTextFiend "txtBuscar"
		 * cuando termina de buscar por todo el texto, reinicia su busqueda desde el principio
		 */
		if (e.getSource() == btnBuscar){
			boolean hacerClick = false;
			textoBuscar = txtBuscar.getText();
			
			//Si nunca ha sido pulsado el bot�n
			if (PvezPulsado == 0){
				document = (HTMLDocument) texto.getDocument();
				highlighter = texto.getHighlighter();
				highlighter.removeAllHighlights();
				it = document.getIterator(HTML.Tag.CONTENT);
				if (it.isValid()){
					it.next();
					try {
						fragmento = document.getText(it.getStartOffset(),it.getEndOffset() - it.getStartOffset());
						patron = Pattern.compile(txtBuscar.getText());
						matcher = patron.matcher(fragmento);
						if (matcher.find()) {
							highlighter.addHighlight(it.getStartOffset() + matcher.start(),
									it.getStartOffset() + matcher.end(), colorSub);
						} 
					} catch (BadLocationException e2) {
						e2.printStackTrace();
					}
				}// Fin del IF v�lido 
			}
			//Si ya ha sido pulsado el bot�n una vez al menos
			else{	
					try {
						if (matcher.find()) {
							highlighter.removeAllHighlights();
							highlighter.addHighlight(it.getStartOffset() + matcher.start(),
									it.getStartOffset() + matcher.end(), colorSub);	
						}
						else{
							PvezPulsado = -1;
							hacerClick = true;
						}
					}
					catch (BadLocationException e1) {
						e1.printStackTrace();
					}
			} 
			
			PvezPulsado++;
			if(hacerClick){
				btnBuscar.doClick();
			}
		}//Fin de btnBuscar
		
		
		/**
		 * btnBuscarSiguiente hace, a todos los efectos, lo mismo que btnBuscar
		 */
		if (e.getSource() == btnBuscarSiguiente){
			boolean hacerClick = false;
			textoBuscar = txtBuscar.getText();
			
			//Si nunca ha sido pulsado el bot�n
			if (PvezPulsado == 0){
				document = (HTMLDocument) texto.getDocument();
				highlighter = texto.getHighlighter();
				highlighter.removeAllHighlights();
				it = document.getIterator(HTML.Tag.CONTENT);
				if (it.isValid()){
					it.next();
					try {
						fragmento = document.getText(it.getStartOffset(),it.getEndOffset() - it.getStartOffset());
						patron = Pattern.compile(txtBuscar.getText());
						matcher = patron.matcher(fragmento);
						if (matcher.find()) {
							highlighter.addHighlight(it.getStartOffset() + matcher.start(),
									it.getStartOffset() + matcher.end(), colorSub);
						} 
					} catch (BadLocationException e2) {
						e2.printStackTrace();
					}
				} 
			}
			//Si ya ha sido pulsado el boton una vez al menos
			else{	
					try {
						if (matcher.find()) {
							highlighter.removeAllHighlights();
							highlighter.addHighlight(it.getStartOffset() + matcher.start(),
									it.getStartOffset() + matcher.end(), colorSub);	
						}
						else{
							PvezPulsado = -1;
							hacerClick = true;
						}
					}
					catch (BadLocationException e1) {
						e1.printStackTrace();
					}
			} 
			
			PvezPulsado++;
			if(hacerClick){
				btnBuscar.doClick();
			}
		}// Fin de btnBuscarsiguiente
		
		/**
		 * Reemplaza un texto seleccionado mediante el boton buscar o bien, busca la primera coincidencia
		 * en el texto y la intercambia por el texto escrito en el JTextField "txtReemplazar"
		 */
		if ( e.getSource() == btnReemplazar){
			try {
				if(PvezPulsado == 0){
					btnBuscar.doClick();
				}
				document.replace(it.getStartOffset() + matcher.start(),
							textoBuscar.length(), txtReemplazar.getText(),null);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			catch (IllegalStateException noHay) {
				JOptionPane.showMessageDialog(null, "No hay m\u00E1s coincidencias en el texto");
			}
			PvezPulsado = 0;
			
		}//fin de btnReemplazar
		
		/**
		 * Reemplaza TODO el texto que encuentre que coincida con txtBuscar y lo cambiara
		 * por el texto escrito en txtReemplazar
		 */
		if ( e.getSource() == btnReemplazaTodo){
			textoBuscar = txtBuscar.getText();
			
			String frase = textoBuscar;
			HTMLDocument document = (HTMLDocument) texto.getDocument();
			for (HTMLDocument.Iterator it = document.getIterator(HTML.Tag.CONTENT);
			     it.isValid(); it.next()) {
			  try {
			    String fragment = document.getText(it.getStartOffset(),
			                                       it.getEndOffset() - it.getStartOffset());
			   
			    Pattern patron = Pattern.compile(frase);
			    Matcher matcher = patron.matcher(fragment);
			    
			    while (matcher.find()) {
			    	document.replace(it.getStartOffset() + matcher.start(),
			    			frase.length(), txtReemplazar.getText(),null);
			    }
			  } catch (BadLocationException ex) {
			  }
			}	
		}// fin btnReemplazaTodo
	
	
	}//Fin de Action Performed
}
