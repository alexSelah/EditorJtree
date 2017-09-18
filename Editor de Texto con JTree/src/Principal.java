
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import calculadora.Calculadora2012;


public class Principal {

	/**
	 * Editor de Texto con implementaci\u00f3n de un JTree para navegar por los archivos
	 * Desarrollado por: Alejandro Campos
	 * 
	 * Alguna parte del codigo o algunas clases han sido sacadas de la Comunidad Java de internet
	 * La mayor parte de los iconos han sido sacados de la web: www.iconspedia.com
	 * 
	 * Las Clases Calculadora y editor c on Jtree Pertenecen a pr\u00e1cticas realizadas por mi mismo en otras
	 * asignaturas o proyectos.
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
	
	
	public static void main(String[] args) {
		PantallaPrincipal p=new PantallaPrincipal();
		p.setVisible(true);	
	}

}

/**
 * Clase que implementa la pantalla principal, donde se a\u00f1aden dos JInternalFrame, el de la izquierda
 * o PANEL1 pertenece a un JTree donde se navegar\u00e1 por los archivos y carpetas creados
 * el de la derecha o PANEL2, implementa un editor de textos con funciones HTML.
 * 
 * @author Alejandro Campos
 *
 */
class PantallaPrincipal extends JFrame implements ActionListener, ComponentListener {
	
	private static final long serialVersionUID = 1L;
	JInternalFrame panel1= new JInternalFrame("",true);
	JInternalFrame panel2= new JInternalFrame("",true);
	File directorio = new File("./docs");
	
	ArbolFicheros arbol;
	
	private JMenu archivo, edicion, herramientas, ayuda;
	private JMenuBar barraMenu;
	private JMenuItem abrir, guardar, guardarcomo, calculadora, acercade, copiar, pegar, cortar, cerrar;
	private JMenuItem salir, buscar, reemplazar, imprimir;
	
	
	
	public PantallaPrincipal () {
		  super("Editor de textos con explorador en arbol");
		  
		  ImageIcon icon;
          icon = new ImageIcon(getClass().getResource("recursos/libro.png"));
		  super.setIconImage(icon.getImage());
		  iniciarComponentes();
	      ponerBarras();
	      this.setLayout(null);
	      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void ponerBarras() {
		barraMenu = new JMenuBar();
		//barraMenu.setBackground(Color.CYAN);
		archivo = new JMenu("Archivo");
		edicion = new JMenu("Edicion");
		herramientas = new JMenu("Herramientas");
		ayuda = new JMenu("Ayuda");
		
		
		ImageIcon icono = new ImageIcon(getClass().getResource("recursos/libro.png"));
		super.setIconImage(icono.getImage());
		
		abrir = new JMenuItem("Abrir", new ImageIcon(getClass().getResource("recursos/abrir.png")));
		abrir.addActionListener(this);
		archivo.add(abrir);
		
		guardar = new JMenuItem("Guardar", new ImageIcon(getClass().getResource("recursos/guardar.png")));
		guardar.addActionListener(this);
		archivo.add(guardar);
		archivo.addSeparator();
		
		guardarcomo = new JMenuItem("Guardar Como...", new ImageIcon(getClass().getResource("recursos/guardar.png")));
		guardarcomo.addActionListener(this);
		archivo.add(guardarcomo);
		
		cerrar = new JMenuItem("Cerrar", new ImageIcon(getClass().getResource("recursos/cruzroja.png")));
		cerrar.addActionListener(this);
		archivo.add(cerrar);
		archivo.addSeparator();
		
		imprimir = new JMenuItem("Imprimir", new ImageIcon(getClass().getResource("recursos/imprimir.png")));
		imprimir.addActionListener(this);
		archivo.add(imprimir);
		archivo.addSeparator();
		
		salir = new JMenuItem("Salir", new ImageIcon(getClass().getResource("recursos/carita.png")));
		salir.addActionListener(this);
		archivo.add(salir);
		
		copiar = new JMenuItem("Copiar", new ImageIcon(getClass().getResource("recursos/copiar.png")));
		copiar.addActionListener(this);
		copiar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		copiar.setMnemonic(KeyEvent.VK_C);
		edicion.add(copiar);
		
		cortar = new JMenuItem("Cortar", new ImageIcon(getClass().getResource("recursos/cortar.png")));
		cortar.addActionListener(this);
		cortar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		cortar.setMnemonic(KeyEvent.VK_X);
		edicion.add(cortar);
		
		pegar = new JMenuItem("Pegar", new ImageIcon(getClass().getResource("recursos/pegar.png")));
		pegar.addActionListener(this);
		pegar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		pegar.setMnemonic(KeyEvent.VK_P);
		edicion.add(pegar);
		
		buscar = new JMenuItem("Buscar", new ImageIcon(getClass().getResource("recursos/buscar.png")));
		buscar.addActionListener(this);
		buscar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
		buscar.setMnemonic(KeyEvent.VK_F);
		edicion.addSeparator();
		edicion.add(buscar);
		
		reemplazar = new JMenuItem("Buscar/Reemplazar", new ImageIcon(getClass().getResource("recursos/reemplazar.png")));
		reemplazar.addActionListener(this);
		edicion.add(reemplazar);
		
		calculadora = new JMenuItem("Calculadora", new ImageIcon(getClass().getResource("recursos/calculadora.png")));
		calculadora.addActionListener(this);
		herramientas.add(calculadora);
		
		acercade = new JMenuItem("Acera del Editor", new ImageIcon(getClass().getResource("recursos/informacion.png")));
		acercade.addActionListener(this);
		ayuda.add(acercade);
		
		barraMenu.add(archivo);
		barraMenu.add(edicion);
		barraMenu.add(herramientas);
		barraMenu.add(ayuda);
		//barraMenu.setBounds(0, 0, this.getWidth(), 50);
		
		this.setJMenuBar(barraMenu);		
	}

	private void iniciarComponentes(){
			//creamos el directorio donde se guardar�n los archivos creados
		   if (directorio.exists()){
		   }
		   else{
			   if (directorio.mkdirs())
				   JOptionPane.showMessageDialog(null,"El directorio DOCS, donde se guardar\u00e1n los archivos, " +
					   		"no ha podido ser encontrado. Se crear\u00e1 uno para guardar los archivos","Aviso", JOptionPane.WARNING_MESSAGE, null);
			   else
				   JOptionPane.showMessageDialog(null,"Ha habido un problema con la creaci\u00f3n del archivo","ERROR", JOptionPane.ERROR_MESSAGE, null);   
		   }
		   
		  //Vamos configurando los paneles	      
	      this.getContentPane().setLayout(new BorderLayout());
	      
	      //Coloca la ventana en mitad de la pantalla  
	      Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
	      int height = pantalla.height+300;
	      int width = pantalla.width+500;
	      setSize(width/2, height/2+50);		
	      setLocationRelativeTo(null);		
	      setVisible(true);
	      setLayout(null);
	      setResizable(false);
	      //this.pack();
	      
	      
	      //Iniciamos los dos paneles, el izquierdo (arbol) y el derecho (editor)
	      panel1.setBounds(0, 0, (getContentPane().getSize().width)/6, (getContentPane().getSize().height)-20);
	      panel1.setBorder(null);
	      panel1.addComponentListener(this);
	      
	      panel2.setBounds(panel1.getSize().width, 0,(getContentPane().getSize().width)- panel1.getSize().width, (getContentPane().getSize().height)-20);
	      panel2.setBorder(null);
	      panel2.addComponentListener(this);
	      
	      //this.add(panel1, BorderLayout.WEST);
	      this.add(panel1);
	      panel1.setResizable(false);
	      //this.add(panel2, BorderLayout.CENTER);
	      this.add(panel2);
	      panel2.setResizable(false);
	    
	      //Editor e=new Editor(panel2);
	      arbol = new ArbolFicheros(panel1,panel2);
	      
	      //Capturamos el cierre de la ventana (x de la esquina)y le decimos que abra el metodo que queremos
	      setDefaultCloseOperation(0);
	      this.addWindowListener(new WindowAdapter() {
	    	  @Override
				public void windowClosing(WindowEvent arg0) {
					salirEditor();
				}
	      });
	      
	    
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == acercade){
			AcercaDe acerca = new AcercaDe();
			acerca.setAlwaysOnTop(true);
			acerca.setEnabled(true);
			acerca.setVisible(true);
		}
		
		if(e.getSource() == imprimir){
			arbol.imprimir();
		}
		if (e.getSource() == abrir){
			panel2.setVisible(true);
			arbol.abrir();
		}
		if (e.getSource() == guardar){
			arbol.guardarArbol();
			arbol.cambiarCambiado(false);
		}

		if (e.getSource() == guardarcomo){
			if (panel2.isVisible()){
				arbol.guardar();
			}
			else{
				JOptionPane.showMessageDialog(null, "Debe seleccionar un nodo (archivo) primero");
			}
		}
		if (e.getSource() == cerrar){
			arbol.cerrar();
		}
		if(e.getSource() == salir){
			salirEditor();
		}
		if (e.getSource() == calculadora){
			Calculadora2012 cal = new Calculadora2012();
			cal.setVisible(true);
		}
		if (e.getSource() == copiar){
			arbol.copiar();
		}
		if ( e.getSource() == cortar){
			arbol.cortar();
		}
		if (e.getSource() == pegar){
			arbol.pegar();
		}
		if (e.getSource() == buscar){
			if (panel2.isVisible()){
				arbol.buscar();
			}
			else{
				JOptionPane.showMessageDialog(null, "Debe seleccionar un nodo (archivo) para buscar en \u00e9l");
			}
		}
		if (e.getSource() == reemplazar){
			arbol.cambiarCambiado(true);
			if (panel2.isVisible()){
				arbol.reemplazar();				
			}
			else{
				JOptionPane.showMessageDialog(null, "Debe seleccionar un nodo (archivo) para buscar en \u00e9l");
			}
		}

		//abrir, guardarcomo, calculadora, acercade, copiar, pegar, cortar
	}

	//Controlamos que al salir del editor se haya hecho algun cambio, si no cimplemente sale.
	/**
	 * Determina que har\u00e1 la ventana al querer cerrarla o salir. Si el texto del editor ha sido modificado
	 * preguntar\u00e1 si se quieren guardar los cambios.
	 */
	private void salirEditor(){
		if (arbol.textoCambiado()){
			int respuesta = JOptionPane.showConfirmDialog(null, "\u00BFDesea guardar los cambios antes de cerrar?");
			if(respuesta == JOptionPane.OK_OPTION){
				arbol.guardarArbol();
				System.exit(0);
			}
			if(respuesta == JOptionPane.NO_OPTION){
				System.exit(0);	
			}			
		}
		else System.exit(0);	
	}
	
	@Override
	public void componentHidden(ComponentEvent arg0) {	
	}
	@Override
	public void componentMoved(ComponentEvent arg0) {
		panel1.setLocation(0,0);
		panel2.setLocation(panel1.getWidth(),0);	
	}
	@Override
	public void componentResized(ComponentEvent arg0) {	
	}
	@Override
	public void componentShown(ComponentEvent arg0) {
	}

	
}