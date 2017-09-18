


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

	/**
	 * Ventana con informaciï¿½n de la creaciï¿½n del Editor de Textos con JTree
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
public class AcercaDe extends JDialog {

	/**
	 * Clase que muestra una ventana con informaciï¿½n del programa
	 */
	private static final long serialVersionUID = 1L;
	
	/** Constructor de AcercaDe */
    public AcercaDe() {
        initComponents();
        setTitle("Sobre el Editor de Textos");
        ImageIcon icon = new ImageIcon(getClass().getResource("recursos/about.png"));
		setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);
        this.setSize(300, 190);
        pack();
    }

  
    private void initComponents() {
    	
    	    //super(parent, "About Dialog", true);

    	    Box b = Box.createVerticalBox();
    	    b.add(Box.createGlue());
    	    String text = "********************************" + "<br>" +
    	              "-- Editor de textos con explorador de archivos en árbol --" + "<br>"+ "<br>"+
    	              "Alejandro Campos" + "<br>" +
    	              "\u00A9 2013" + "<br>" + "<br>"  +
    	              "alex.selah@yahoo.es"+"<br>";
    	    JLabel label = new JLabel("<html><div style='text-align: center;'>" + text + "</div></html>");
    	    b.add(label);
    	    b.add(Box.createGlue());
    	    b.setOpaque(false);
    	    getContentPane().add(b, "Center");

    	    JPanel p2 = new JPanel();
    	    JButton ok = new JButton();
    	    ok.setIcon(new ImageIcon(getClass().getResource("recursos/cararisa.png")));
    	    p2.add(ok);
    	    p2.setSize(300, 190);
    	    getContentPane().add(p2, "South");

    	    
    	    ok.addActionListener(new ActionListener() {
    	      public void actionPerformed(ActionEvent evt) {
    	        setVisible(false);
    	      }});

    	    setSize(300, 200);
    }//Fin InitComponents

}
