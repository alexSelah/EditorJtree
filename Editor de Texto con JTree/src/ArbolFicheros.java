import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;


	/**
	 * Clase que implementa un JTree que hace de selector y explorador de archivos.
	 * Interactua directamente con el Editor de textos.
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

public class ArbolFicheros extends JInternalFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	JInternalFrame panel;
	JInternalFrame panel2;
	DefaultMutableTreeNode raiz;
	JTree arbol;
	JScrollPane scroll;
	JMenuBar barraMenu;
	JButton nuevoDir, nuevoItem, eliminarItem;
	Editor editor;
	String archivoAbierto;
	int archivosRaiz; // indica cuantos archivos tiene el nodo raiz.
	
	//ficheros de escritura del arbol
	File fichero=new File("./docs/estructura.stc");
	FileWriter fWriter;

	/**
	 * Contructor de la clase.
	 * 
	 * @param ar es el JPanel donde se situa el Jtree
	 * @param er es el JPanel donde se situa el Editor de Textos
	 */
	public ArbolFicheros (JInternalFrame ar, JInternalFrame er){
		panel=ar;
		panel2=er;
		initComponents();
		panel.setTitle("Arbol de ficheros");
		this.setVisible(true);
		this.setResizable(true);
		
	}

	private void initComponents() {
		archivosRaiz = 0;
		JScrollPane scroll= new JScrollPane();
		raiz = new DefaultMutableTreeNode("Documentos");
		File existeEstructura = new File("./docs/estructura.stc");
		if(existeEstructura.exists()){
			JOptionPane.showMessageDialog(null,"Se ha cargado la estructura del \u00e1rbol de documentos con \u00e9xito" +
			   		"","Eureka!", JOptionPane.INFORMATION_MESSAGE, null);
			cargarArbol();
		}
		else{
			JOptionPane.showMessageDialog(null,"No se ha podido encontrar una estructura de documentos previa" +
			   		". Se crear\u00e1 una por defecto","Aviso", JOptionPane.WARNING_MESSAGE, null);
			crearNodosDefecto(raiz);			
		}
		//arbol=new JTree(raiz);
		scroll.setViewportView(arbol);
		
		arbol.getSelectionModel().setSelectionMode(javax.swing.tree.TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		arbol.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener(){
			public void valueChanged(javax.swing.event.TreeSelectionEvent evt){
				panel2.setVisible(true);
				// Primero guardamos el texto que hab\u00eda en pantalla.
				if (archivoAbierto == ""){
					
				}
				else{
					editor.guardar(archivoAbierto);					
				}
				// Luego dejamos que abra el nuevo documento
				DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) arbol.getLastSelectedPathComponent();
				try{
					if (nodo.getAllowsChildren()){
						archivoAbierto="";
						panel2.setVisible(false);
					}
					else{
						archivoAbierto = arbolValueChanged(evt);					
					}
				}
				catch (NullPointerException e){
					
				}
			}
		});
		//getContentPane().add(scroll, java.awt.BorderLayout.CENTER);
		
		//men\u00fa del arbol
		barraMenu = new JMenuBar();
		nuevoDir = new JButton(new ImageIcon(getClass().getResource("recursos/nuevacarpeta.png")));
		nuevoDir.addActionListener(this);
		nuevoDir.setToolTipText("Crea una nueva Carpeta contenedora. Se crear\u00e1 bajo el sitio seleccionado");
		barraMenu.add(nuevoDir);
		nuevoItem = new JButton(new ImageIcon(getClass().getResource("recursos/editar.png")));
		nuevoItem.addActionListener(this);
		nuevoItem.setToolTipText("Crea un nuevo archivo bajo el sitio seleccionado. Si no selecciona ninguno se crear\u00e1 en la raiz");
		barraMenu.add(nuevoItem);
		barraMenu.add(new JSeparator());
		eliminarItem = new JButton(new ImageIcon(getClass().getResource("recursos/cruzroja.png")));
		eliminarItem.addActionListener(this);
		eliminarItem.setToolTipText("\u00A1Cuidado! Elimina el archivo o carpeta seleccionada");
		barraMenu.add(eliminarItem);
		
		
		
		panel.setLayout(new BorderLayout());
		panel.add(scroll, BorderLayout.CENTER);
		panel.add(barraMenu, BorderLayout.NORTH);
		panel.setVisible(true);
		panel.setFrameIcon(new ImageIcon(getClass().getResource("recursos/arbolficheros.png")));
		
		//editor=new Editor(panel2);
		
		
	}
	
	/**
	 * M\u00e9todo que se lanza para guardar el estado del \u00e1rbol de elementos, 
	 * as\u00ed como del editor abierto en ese momento.
	 * 
	 * @param evt Evento que llama al m\u00e9todo
	 * @return devuelve el nombre del nodo que est\u00e1 abierto.
	 */
	private String arbolValueChanged(javax.swing.event.TreeSelectionEvent evt){
		DefaultMutableTreeNode nodoSeleccionado;
		if (arbol.getLastSelectedPathComponent() == null){
			arbol.setSelectionRow(1);
		}
		nodoSeleccionado = (DefaultMutableTreeNode) arbol.getLastSelectedPathComponent();			
		editor.textPane.setText("");
		editor.abrir(nodoSeleccionado.toString());
		if ( nodoSeleccionado == null || nodoSeleccionado.isRoot()){
			return "";
		}
		
		Object nodoUsuario = nodoSeleccionado.getUserObject();
		return nodoUsuario.toString();
	}

	/**
	 * m\u00e9todo que crea un arbol de ficheros y contenido de ejemplo.
	 * Se lanza si al abrir la aplicaci\u00f3n no encuentra uno creado con anterioridad.
	 * 
	 * @param raiz es la raiz a partir de la cual colgar\u00e1n todos los elementos
	 */
	private void crearNodosDefecto(DefaultMutableTreeNode raiz) {
		editor = new Editor(panel2);
		
		DefaultMutableTreeNode carpeta = null;
		DefaultMutableTreeNode fichero = null;
		
		String st="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum viverra commodo ipsum, " +
				"id malesuada mi vestibulum nec. Ut volutpat augue et lacus dapibus et ultricies mauris fringilla. " +
				"Donec est ligula, porttitor imperdiet interdum eget, eleifend vitae dui. Proin auctor dui vel est " +
				"tincidunt ac tincidunt massa laoreet. Etiam venenatis elit sit amet orci gravida sit amet ullamcorper " +
				"augue eleifend. Proin sed justo id purus molestie euismod eu sed diam. In ac nisi velit. " +
				"Maecenas sed nisi quis velit ornare dictum ac vel est. Vestibulum ante ipsum primis in faucibus " +
				"orci luctus et ultrices posuere cubilia Curae; Sed in nibh massa. Nam ullamcorper libero nec tellus " +
				"elementum rutrum. Suspendisse vel velit tincidunt orci feugiat fermentum. Ut convallis, " +
				"ipsum at pretium ultricies, erat elit vestibulum leo, id dictum odio quam in ante. Curabitur " +
				"tellus erat, rutrum sit amet bibendum sed, ultricies ut dui. Ut egestas tempus ipsum a lobortis. " +
				"Nullam sed viverra ante. ";
		
		fichero = new DefaultMutableTreeNode("Notas");
		fichero.setAllowsChildren(false);
		raiz.add(fichero);
		editor.textPane.setText(st);
		editor.guardar("Notas");
		archivosRaiz = 1;
		
		carpeta = new DefaultMutableTreeNode("Amigos");
		carpeta.setAllowsChildren(true);
		raiz.add(carpeta);
		fichero = new DefaultMutableTreeNode("telefono Mar\u00eda");
		fichero.setAllowsChildren(false);
		carpeta.add(fichero);
		editor.textPane.setText("Telefono de Mar\u00eda: 123456789");
		editor.guardar("telefono Maria");
		
		carpeta = new DefaultMutableTreeNode("Clientes");
		carpeta.setAllowsChildren(true);
		raiz.add(carpeta);
		
		fichero = new DefaultMutableTreeNode("Juan");
		fichero.setAllowsChildren(false);
		carpeta.add(fichero);
		editor.textPane.setText("Esto es un ejemplo de Juan");
		editor.guardar("Juan");
		
		fichero = new DefaultMutableTreeNode("Pedro");
		fichero.setAllowsChildren(false);
		carpeta.add(fichero);
		editor.textPane.setText("Esto es un ejemplo de Pedro");
		editor.guardar("Pedro");
		
		arbol=new JTree(raiz);
		arbol.setExpandsSelectedPaths(true);
		guardarArbol();
		
		arbol.setSelectionRow(1);
		editor.abrir(arbol.getLastSelectedPathComponent().toString());
		archivoAbierto = arbol.getLastSelectedPathComponent().toString();
	}
	
	/**
	 * Crea un nuevo nodo. El lugar donde se crea depender\u00e1 de si es una carpeta o un archivo
	 * 
	 * @param nombre es el nombre del nodo.
	 * @param op se pasa un "2" si es un archivo.
	 */
	public void crearNuevoNodo(String nombre, String op){
		DefaultTreeModel modeloAux = (DefaultTreeModel) arbol.getModel();
		
		// Hay que asegurarse que el arbol est� cargado correctamente y no hay errores de repintado
		DefaultMutableTreeNode nuevoNodo = new DefaultMutableTreeNode(nombre);
		MutableTreeNode localizacion;
		if(op == "2"){
			nuevoNodo.setAllowsChildren(false);
			if (nuevoNodo.isNodeAncestor(raiz)){
				archivosRaiz++;
			}
		}
		//Ponemos una localizacion por defecto. Si es null, la localizacion ser� root
		if (arbol.getSelectionPath() != null)
		{
			localizacion = (MutableTreeNode) arbol.getSelectionPath().getLastPathComponent();
		} else
		{
			localizacion = (MutableTreeNode) modeloAux.getRoot();
		}
		//Si es un archivo, fijamos la localizacion en su carpeta contenedora.
		if (!localizacion.getAllowsChildren())
		{
			localizacion = (MutableTreeNode) localizacion.getParent();
		}
		//Creamos el nodo dependiendo si es una carpeta o un fichero
		if (nuevoNodo.getAllowsChildren()){
			//si es una carpeta la insertamos despues de los archivos de raiz o al final de la carpeta contenedora
			if (nuevoNodo.getAllowsChildren()){
				int numHijos = localizacion.getChildCount();
				modeloAux.insertNodeInto(nuevoNodo, localizacion, numHijos);
			}
			else{
				modeloAux.insertNodeInto(nuevoNodo, localizacion, archivosRaiz);				
			}
		}
		else{
			//si es un archivo lo ponemos en la posicion 0;
			modeloAux.insertNodeInto(nuevoNodo, localizacion, 0);
			archivosRaiz++;
		}
		modeloAux.nodeChanged(nuevoNodo);
		editor.guardar(nombre);
		arbol.setSelectionPath(new TreePath(nuevoNodo.getPath()));
		
	}
	
	/**
	 * borra un elemento del arbol. Comporobar\u00e1 si tiene subnodos y los eliminar\u00e1.
	 * Elimina tambi\u00e9n los archivos asociados a los nodos
	 */
	public void borrarElemento (){
		DefaultTreeModel arbolAux = (DefaultTreeModel) arbol.getModel();
        TreePath[] ruta = arbol.getSelectionPaths();
        int result = -1;
        if (ruta != null)
        {
            for (TreePath path : ruta)
            {
                DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) path.getLastPathComponent();
                if (nodo.getParent() != null && nodo.isRoot() == false){
                    if (nodo.getChildCount() > 0){
                    //Si se mete aqui es una arpeta que contiene varios elementos
                        result = JOptionPane.showConfirmDialog(
                                this.getRootPane(),
                                "Esta carpeta contiene " + nodo.getChildCount() + " subnodos. " + "Se eliminar\u00e1n todos ellos. \u00BFDesea continuar?",
                                "Eliminaci\u00f3n de ficheros hijo",
                                JOptionPane.YES_NO_OPTION);
                        if (result != 1)
                        {
                        	for (int i=0;i<nodo.getChildCount();i++){ //eliminamos los hijos
                        		DefaultMutableTreeNode nodoHijo = (DefaultMutableTreeNode) nodo.getChildAt(i);
                        		File fich = new File("./docs/"+nodoHijo.toString()+".acf");
                        		if(fich.exists()){
                        			fich.delete();
                        			}
                        	}
                        	panel2.setEnabled(false);
                        	arbolAux.removeNodeFromParent(nodo);
                        }
                        else{
                        	//opcion cancelada
                        }
                    }
                    //Si no, es un archivo ( o una carpeta vacia, lo mismo da)
                    else{
                    	if (nodo.isNodeChild(raiz));
                    	{
                    		archivosRaiz--;
                    	}
                    	try{
                    		arbolAux.removeNodeFromParent(nodo);
                    	}
                    	catch (NullPointerException e){
                    		System.out.println(nodo.toString());
                    	}
                    	//Eliminamos el archivo
                    	File fich = new File("./docs/"+nodo.toString()+".acf");
                    	if(fich.exists()){
                    		editor.enBlanco();
                    		editor.guardar(nodo.toString());
                    		panel2.dispose();
                    		fich.delete();
                    		}
                    }

                } 
                else
                {
                    JOptionPane.showMessageDialog(
                            this.getRootPane(),
                            "El nodo que intenta eliminar es el nodo Raiz!",
                            "No se puede eliminar raiz",
                            JOptionPane.WARNING_MESSAGE);
                }
                arbolAux.nodeChanged(nodo);
            }//fin del for
        }
	}//Fin borrarElemento

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()== nuevoDir){
			panel2.setVisible(true);
			new JOptionPane();
			String nombre = JOptionPane.showInputDialog(null, "Introduzca Nombre: ", "");
			crearNuevoNodo(nombre,"1");
		    guardarArbol();
		}
		
		if(e.getSource() == nuevoItem){
			panel2.setVisible(true);
			new JOptionPane();
			String nombre = JOptionPane.showInputDialog(null, "Introduzca Nombre: ", "Nuevo Archivo");
			TreeModel model = arbol.getModel();
			Object root = model.getRoot();
			if(buscarNodo(model,root,nombre)){
				JOptionPane.showMessageDialog(null, "Lo siento, ya existe un nodo con ese nombre. El" +
						" programa no permite que haya dos archivos iguales salvo en Raiz",
						"Imposible crear nuevo archivo",JOptionPane.ERROR_MESSAGE);
			}
			else{
				crearNuevoNodo(nombre,"2");
				guardarArbol();	
				editor.enBlanco();
			}
			
		}
		
		if(e.getSource() == eliminarItem){
			borrarElemento();
			this.cambiarCambiado(true);
		    guardarArbol();
		}
		
	}// FIN ACTIONPERFORMED
	
	/**
	 * Guarda la estructura del arbol en un fichero. 
	 * El formato de guardado es texto plano, pero se guarda con extensi\u00f3n ".stc" para 
	 * protegerla de ediciones manuales.
	 */
	public void guardarArbol(){ 
		editor.guardar(archivoAbierto);
		TreeModel model = arbol.getModel(); 

		try {
			FileWriter fWriter = new FileWriter(fichero);
			PrintWriter pWriter=new PrintWriter(fWriter);
			if (model != null) { 
				Object root = model.getRoot(); 
				pWriter.println("Raiz: "+root.toString());
				recorrer(model,root, fWriter, pWriter); 
			} 
			else 
				System.out.println("Tree is empty.");
			
			pWriter.flush();
			pWriter.close();
			fWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Busca un nodo en la estructura del arbol
	 * 
	 * @param model modelo del arbol de ficheros
	 * @param o es la raiz del arbol, o la carpeta bajo la cual se desea buscar
	 * @param nombre es el nombre del nodo a buscar
	 * @return devuelve true si lo encuentra, false si no.
	 */
	public boolean buscarNodo (TreeModel model, Object o, String nombre){
		int cc = model.getChildCount(o);
		boolean es = false;
		for( int i=0; i < cc; i++) {
			Object child = model.getChild(o, i ); 
			if (child.toString().equals(nombre)){ 
				es = true;
			}
		} 	
		return es;
	}
	
	/**
	 * Recorre el arbol de ficheros. Discrimina si el nodo es un archivo o una carpeta
	 * 
	 * @param model modelo del arbol
	 * @param o objeto raiz del arbol
	 * @param fw FileWritter para escribir en el fichero donde se guarda la estructura
	 * @param pw el buffer de escritura
	 */
	protected void recorrer(TreeModel model, Object o, FileWriter fw, PrintWriter pw){ 
		int cc; 
		cc = model.getChildCount(o);
		String aux = ""; 
			for( int i=0; i < cc; i++) {
				Object child = model.getChild(o, i ); 
				if (model.isLeaf(child)){ 
					aux = ("Documento: "+i+" "+child.toString());
					pw.println(aux);
				}
				else { 
					aux = ("Carpeta: "+i+" "+child.toString());
					pw.println(aux);
					recorrer(model,child, fw, pw); 
				} 
			} 	
	} 

	/**
	 * Carga la estructura del \u00e1rbol del archivo 'estructura.stc'. 
	 */
	public void cargarArbol() {
		DefaultMutableTreeNode raiz;
		raiz = new DefaultMutableTreeNode("Documentos");
		FileReader fr = null;
		BufferedReader br = null;
		String linea="";
		
		arbol = new JTree(raiz);
		
		try {
			fr = new FileReader(fichero);
			br = new BufferedReader(fr);
			//leo la primera linea, que deber\u00eda ser Raiz. Si no, cargar\u00eda un arbol por defecto.
			linea = br.readLine();
			
			if (linea.indexOf("Raiz:")==-1){
				JOptionPane.showMessageDialog(null,"La estructura del archivo est\u00e1 corrupta" +
						". Se crear\u00e1 una por defecto","Aviso", JOptionPane.WARNING_MESSAGE, null);
				crearNodosDefecto(raiz);
			}
			
			//Empezamos a cargar los datos
			else{
				while ((linea=br.readLine()) != null){
					//System.out.println(linea);
					if(determinaCoA(linea)){
						// es una carpeta
						String nombreCarpeta = linea.substring(linea.indexOf("Carpeta:")+11);
						if(linea.equals("")){
							
						}
						else{
							crearNuevoNodoOrdenado(nombreCarpeta, "1");							
						}
					}
					else{ //es un archivo
						String nombreArchivo = linea.substring(linea.indexOf("Documento:")+13);
						if(linea.equals("")){
							
						}
						else{
						crearNuevoNodoOrdenado(nombreArchivo, "2");
						}
					}
				}
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Ahora vamos a hacer que seleccione el primer archivo que vea (no carpetas, que no tienen texto)
		//para ello recorremos el arbol y vamos comprobando se se pueden a\u00f1adir hijos.
		boolean ok = false;
		int i = 0;
		while (ok == false || i==100){
			arbol.setSelectionRow(i);
			DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) arbol.getLastSelectedPathComponent();
			if (nodo.getAllowsChildren()){
			}
			else{
				Editor e=new Editor(panel2);
				e.abrir(nodo.toString());
				editor=e;
				archivoAbierto = arbol.getLastSelectedPathComponent().toString();
				ok = true;
			}
			i++;
		}
	}//fin de CargarArbol
	
	private int posicionA=0;//entero creado para controlar la posici\u00f3n de creaci\u00f3n de un nodo archivo en la carpeta
	private int posicionC=0;//entero creado para controlar la posici\u00f3n de creaci\u00f3n de un nodo carpeta en raiz
	
	/**
	 * Con el fin de cargar los nodos ordenadamente y no crear incoherencias en la estructura, se
	 * implementa esta clase para recorrer el arbol de manera ordenada, esto es: Primero la raiz, luego
	 * sus archivos (de la raiz) y a continuaci\u00f3n las carpetas con sus archivos.
	 * Es un m\u00e9todo gemelo de "crearNuevoNodo", pero lo hace de manera ordenada, espec\u00edficamente para
	 * el m\u00e9todo de cargarArbol.
	 * 
	 * @param nombre nombre del nodo
	 * @param op se pasa "2" si es un archivo.
	 */
	public void crearNuevoNodoOrdenado(String nombre, String op){//creado para el m\u00e9todo cargarArbol()
		DefaultTreeModel modeloAux = (DefaultTreeModel) arbol.getModel();
		int pos=0;
		
		// Hay que asegurarse que el arbol est\u00e1 cargado correctamente y no hay errores.
		DefaultMutableTreeNode nuevoNodo = new DefaultMutableTreeNode(nombre);
		MutableTreeNode localizacion;
		if(op == "2"){
			//Si es un fichero le decimos que pueda tener hijos y que seleccione como localizaci\u00f3n
			//el ultimo elemento creado. Adem\u00e1s si es un archivo de la raiz incrementamos el contador.
			nuevoNodo.setAllowsChildren(false);
			if (nuevoNodo.isNodeAncestor(raiz)){
				archivosRaiz++;				
			}
			if(posicionC == 0){//no se ha creado ninguna carpeta
				localizacion = (MutableTreeNode) modeloAux.getRoot();
			}
			else{
				localizacion = (MutableTreeNode) arbol.getSelectionPath().getLastPathComponent();	
				if(localizacion.getAllowsChildren() == false){
					localizacion = (MutableTreeNode) localizacion.getParent();
				}
			}
			pos=posicionA;
			posicionA++;
		} else
		{ //Si es una carpeta, le decimos que pueda tener hijos y fijamos la localizacion en root
			nuevoNodo.setAllowsChildren(true);
			localizacion = (MutableTreeNode) modeloAux.getRoot();
			if (archivosRaiz == 0 && posicionA == 0){
				//Con esto comprobamos si se ha creado al menos un archivo en la raiz, si no, ponemos en la
				//posicion 0 del arbol la carpeta. Aqui solo se deber\u00eda meter si el primer elemento es una carpeta
				System.out.println("No tiene archivos raiz");
				posicionC++;
				posicionA=0;
				pos=0;
			}
			else{
				if (posicionC == 0){//No se ha creado ninguna carpeta, solo los archivos del raiz
					posicionC=posicionA;
					pos=posicionC;
					posicionA=0;
				}
				else{//Ya ha creado al menos una carpeta
					pos=posicionC;
					posicionC++;
					posicionA=0;
				}				
			}
		}
		//Creamos el nodo	
		modeloAux.insertNodeInto(nuevoNodo, localizacion, pos);
		modeloAux.nodeChanged(nuevoNodo);
		arbol.setSelectionPath(new TreePath(nuevoNodo.getPath()));
		
	}
	
	/**
	 * Clase para determinar si un nombre de nodo es un archivo o una carpeta contenedora
	 * 
	 * @param linea es el nombre que quiero comprobar
	 * @return devuelve TRUE si es una carpeta. FALSE si es un archivo
	 */
	protected boolean determinaCoA(String linea){
		/**
		 * Devuelve true si es una carpeta, y false si es un archivo
		 */
			
			if (linea.indexOf("Documento:")>=0){ 
				// Es un documento
				return false;
			}
			else { 
				//Es una carpeta
				return true;
			} 
	}

	/**
	 * El m\u00e9todo crea un nuevo nodo y da la opci\u00f3n de seleccionar un archivo TXT o ACF y lo incluye
	 * en el archivo que se acaba de crear.
	 */
	public void abrir() {
		int seleccion=JOptionPane.showConfirmDialog(null,"Se crear\u00e1 un nuevo nodo Documento para el " +
				"archivo abierto", "Aviso", JOptionPane.YES_NO_OPTION);
			
		if(seleccion==JOptionPane.OK_OPTION){
			String nombre = JOptionPane.showInputDialog(null, "Introduzca un nombre para el nuevo documento" +
					"", "Nuevo archivo", JOptionPane.OK_CANCEL_OPTION);
			archivoAbierto = nombre;
			crearNuevoNodo(nombre, "2");
			Editor e = new Editor(panel2);
			e.abrir();
			editor=e;
		}
		
		guardarArbol();		
	}

	/**
	 * Ejecuta el m\u00e9todo Guardar() del Editor
	 */
	public void guardar() {
		editor.guardar();
		
	}
	
	/**
	 * sirve para cambiar el estado de la variable cambiado de la clase Editor.
	 * Esta variable controla si se ha cambiado el texto.
	 * 
	 * @param c es el nuevo estado que tomar\u00e1 la variable
	 */
	public void cambiarCambiado(boolean c){
		editor.cambiado = c;
	}

	/**
	 * comprueba si se ha cambiado algo en el texto de editor.
	 * 
	 * @return devuelve TRUE si se ha realizado alg\u00fan cambio
	 */
	public boolean textoCambiado(){
		return editor.cambiado();
	}
	
	/**
	 * Copia el contenido seleccionado del editor
	 */
	public void copiar() {
		editor.textPane.copy();
		
	} 
	/**
	 * Corta el texto seleccionado en el editor
	 */
	public void cortar () {
		editor.textPane.cut();
	}
	/**
	 * pega a partir del cursor el contenido del portapapeles
	 */
	public void pegar() {
		editor.textPane.paste();
	}
	
	/**
	 * Busca todas las coincidencias en el editor de texto de un texto que se pide
	 * a trav\u00e9s de una ventana emergente. Muestra el n\u00famero de resultados obtenidos
	 */
	public void buscar() {
		int i = editor.buscar();
		JOptionPane.showMessageDialog(null, "Se han obtenido "+i+" resultados","Buscar", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	/**
	 * Ejecuta el m\u00e9todo reemplazar de la clase editor.
	 */
	public void reemplazar() {
		editor.reemplazar();		
	}

	/**
	 * M\u00e9todo que comprueba si se ha realizado alg\u00fan cambio para guardar su contenido.
	 * Luego cierra el archivo abierto y oculta el panel2 (donde est\u00e1 el editor)
	 */
	public void cerrar() {
		if (editor.cambiado()){
			int respuesta = JOptionPane.showConfirmDialog(null, "\u00BFDesea guardar los cambios antes de cerrar?");
			if(respuesta == JOptionPane.OK_OPTION){
				guardarArbol();
				Editor e = new Editor(panel2);
				e.enBlanco();
				e.textPane.repaint();
				arbol.setSelectionRow(0);
				panel2.dispose();
			}
			if (respuesta == JOptionPane.NO_OPTION){
				Editor e = new Editor(panel2);
				e.enBlanco();
				e.textPane.repaint();
				arbol.setSelectionRow(0);
			}
		}
		else{
			Editor e = new Editor(panel2);
			e.enBlanco();
			arbol.setSelectionRow(0);
			panel2.dispose();
		}
	}

	public void imprimir() {
		editor.imprimir();
	}
	
}
