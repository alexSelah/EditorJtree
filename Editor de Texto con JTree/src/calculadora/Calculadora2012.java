package calculadora;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultEditorKit;


public class Calculadora2012 extends JFrame implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Barra de men�s y elementos
	private JMenuBar barraMenu;
	private JMenu edicion, ayuda, archivo;
	private JMenuItem cortar, copiar, pegar, acerca, ayudaItem, salirItem;
	//componentes para la pantalla
	private JTextField txtPantalla;
	private JLabel lblPantalla;
	//btn's numuricos
	private BotonCalculadora btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0,btnPunto; 
	//btn's borrar
	private BotonCalculadora btnCe, btnC;
	//btn's operaciones
	private BotonCalculadora btnMas, btnMenos, btnPor, btnEntre, btnIgual;
	private BotonCalculadora btnPorcentaje, btnPi, btnMasMenos, btnE, btnCuadrado, btnPotencia, btnRaiz, btnLog;
	//paneles
	private JPanel pnlPantalla,pnlNumeros, pnlIzquierda, pnlOperaciones;
	//Constantes para valor boton Pi y boton E
	private static final double PI = Math.PI;
	private static final double E = Math.E;
	GridBagConstraints cons = new GridBagConstraints();
	private double op1,op2;
	boolean nuevoOp;
	int operacion; //1-suma 2-resta 3-multi 4-divi 5-porcentaje 6-potencia
	String aux="";
	
	public Calculadora2012(){
		this.setTitle("Calculadora de alex.selah");
		this.getContentPane().setBackground(new Color(238,238,224));
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		//this.setResizable(false);
		this.setSize(500, 450);
		initComponents();
		montarVentana();
		montarBarraMenu();
		nuevoOp=true;
		this.pack();
		this.setLocationRelativeTo(null);
		
	}
	
	private void montarBarraMenu() {
		barraMenu.add(archivo);
		edicion.add(copiar);
		edicion.add(cortar);
		edicion.add(pegar);
		barraMenu.add(edicion);
		ayuda.add(acerca);
		ayuda.add(ayudaItem);
		barraMenu.add(ayuda);
		this.setJMenuBar(barraMenu);
		
	}

	private void initComponents(){
		
		this.getContentPane().setLayout(new GridBagLayout());
		//Inicializacion del menu
		barraMenu = new JMenuBar();
		edicion=new JMenu("Edici\u00f3n");
		ayuda=new JMenu("Ayuda");
		salirItem=new JMenuItem("Salir");
		salirItem.addActionListener(this);
		
		new DefaultEditorKit();
		//Instancia Copiar, asigna el ctr+c para copiar y la mascara para el menu. asi como el mnemonico.
		copiar=new JMenuItem(DefaultEditorKit.copyAction);
		copiar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		copiar.setText("Copiar");
		copiar.setMnemonic(KeyEvent.VK_C);

		cortar=new JMenuItem("Cortar");
		new DefaultEditorKit();
		cortar=new JMenuItem(DefaultEditorKit.cutAction);
		cortar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		cortar.setText("Cortar");
		cortar.setMnemonic(KeyEvent.VK_X);
		
		new DefaultEditorKit();
		pegar=new JMenuItem(DefaultEditorKit.pasteAction);
		pegar=new JMenuItem("Pegar");
		pegar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		pegar.setText("Pegar");
		pegar.setMnemonic(KeyEvent.VK_V);
		
		acerca=new JMenuItem("Acerca");
		acerca.addActionListener(this);
		ayudaItem=new JMenuItem("Ayuda");
		ayudaItem.addActionListener(this);
		
		archivo=new JMenu("Archivo");
		archivo.add(salirItem);
		//paneles
			//numericos
		pnlNumeros = new JPanel(new GridLayout(3, 3, 5, 5));
			//panel izquierdo
		pnlIzquierda = new JPanel(new GridBagLayout());
			//pantalla
		pnlPantalla = new JPanel(new GridLayout(2, 1, 0, 3));
			//operaciones
		pnlOperaciones = new JPanel(new GridBagLayout());
		
		//PANTALLA
		txtPantalla = new JTextField("0");
		txtPantalla.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				txtPantalla.setBackground(new Color(255, 255, 0));
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				txtPantalla.setBackground(new Color(238,238,0));
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		txtPantalla.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent key) {
								
			}
			
			@Override
			public void keyReleased(KeyEvent key) {
				String tec=key.getKeyChar()+"";
				if(tec.equals("0")||tec.equals("1")||tec.equals("2")||tec.equals("3")||tec.equals("4")||tec.equals("5")||tec.equals("6")||
						tec.equals("7")||tec.equals("8")||tec.equals("9")){
					if(aux.equals("0")){
						txtPantalla.setText("");
						txtPantalla.setText(tec);
					}
					else{
						txtPantalla.setText("");
						txtPantalla.setText(aux+tec);
					}
				}
				else{
					txtPantalla.setText("");
					txtPantalla.setText(aux);
				}
			}//fin de key release
			@Override
			public void keyPressed(KeyEvent key) {
				aux=txtPantalla.getText();
				String tec=key.getKeyChar()+"";
				if(tec.equals("0")||tec.equals("1")||tec.equals("2")||tec.equals("3")||tec.equals("4")||tec.equals("5")||tec.equals("6")||
						tec.equals("7")||tec.equals("8")||tec.equals("9")){
					if(aux.equals("0")){
						txtPantalla.setText("");
						txtPantalla.setText(tec);
					}
					else{
						txtPantalla.setText("");
						txtPantalla.setText(aux+tec);
					}
				}
				else{
					txtPantalla.setText("");
					txtPantalla.setText(aux);
				}
			}//fin key pressed
		});
		lblPantalla = new JLabel("");
			//colores de fondo para los componentes de la pantalla
		txtPantalla.setBackground(new Color(255, 255, 0));
		lblPantalla.setBackground(new Color(193, 255, 193));
			//colores de fuente para los componentes de la pantalla
		txtPantalla.setForeground(new Color(105, 139, 105));
		lblPantalla.setForeground(new Color(105, 139, 105));
			//caja de texto sin borde
		txtPantalla.setBorder(null);
			//fuente para la caja de texto y para la etiqueta
		txtPantalla.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		lblPantalla.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
			//alineaci�n a la izquierda en la caja de texto y en la etiqueta
		txtPantalla.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPantalla.setHorizontalAlignment(SwingConstants.RIGHT);
			//se inhabilita la entrada de datos en la caja de texto por teclado
		txtPantalla.setEditable(true);
			//estado para que la etiqueta muestre sus propiedades de dise\u00f1o (por defecto se crea a false la opacidad
		lblPantalla.setOpaque(true);
		//Botones
			//Numericos
		btn1 = new BotonCalculadora("1", new Color(220, 220, 220) , new Color(70,70,70));
		btn1.addActionListener(this);
		btn2 = new BotonCalculadora("2", new Color(220, 220, 220) , new Color(70,70,70));
		btn2.addActionListener(this);
		btn3 = new BotonCalculadora("3", new Color(220, 220, 220) , new Color(70,70,70));
		btn3.addActionListener(this);
		btn4 = new BotonCalculadora("4", new Color(220, 220, 220) , new Color(70,70,70));
		btn4.addActionListener(this);
		btn5 = new BotonCalculadora("5", new Color(220, 220, 220) , new Color(70,70,70));
		btn5.addActionListener(this);
		btn6 = new BotonCalculadora("6", new Color(220, 220, 220) , new Color(70,70,70));
		btn6.addActionListener(this);
		btn7 = new BotonCalculadora("7", new Color(220, 220, 220) , new Color(70,70,70));
		btn7.addActionListener(this);
		btn8 = new BotonCalculadora("8", new Color(220, 220, 220) , new Color(70,70,70));
		btn8.addActionListener(this);
		btn9 = new BotonCalculadora("9", new Color(220, 220, 220) , new Color(70,70,70));
		btn9.addActionListener(this);
		btn0 = new BotonCalculadora("0", new Color(220, 220, 220) , new Color(70,70,70));
		btn0.addActionListener(this);
		btnPunto = new BotonCalculadora(".", new Color(220, 220, 220) , new Color(70,70,70));
		btnPunto.addActionListener(this);
			//Borrar
		btnCe = new BotonCalculadora("CE", new Color(170, 170, 170) , Color.RED);
		btnCe.addActionListener(this);
		btnC = new BotonCalculadora("C", new Color(170, 170, 170) , Color.RED);
		btnC.addActionListener(this);
			//operadores
		btnEntre = new BotonCalculadora("/", new Color(176, 226, 225) , new Color(70,70,70));
		btnEntre.addActionListener(this);
		btnMas = new BotonCalculadora("+", new Color(176, 226, 225) , new Color(70,70,70));
		btnMas.addActionListener(this);
		btnMenos = new BotonCalculadora("-", new Color(176, 226, 225) , new Color(70,70,70));
		btnMenos.addActionListener(this);
		btnPor = new BotonCalculadora("x", new Color(176, 226, 225) , new Color(70,70,70));
		btnPor.addActionListener(this);
		btnIgual = new BotonCalculadora("=", new Color(125, 158, 192) , new Color(70,70,70));
		btnIgual.addActionListener(this);
			//operadores avanzados
		btnPorcentaje = new BotonCalculadora("%", new Color(193, 255, 193) , new Color(70,70,70));
		btnPorcentaje.addActionListener(this);
		btnPi = new BotonCalculadora("\u03C0", new Color(193, 255, 193) , new Color(70,70,70));
		btnPi.addActionListener(this);
		btnMasMenos = new BotonCalculadora("\u00b1", new Color(193, 255, 193) , new Color(70,70,70));
		btnMasMenos.addActionListener(this);
		btnE = new BotonCalculadora("E", new Color(193, 255, 193) , new Color(70,70,70));
		btnE.addActionListener(this);
		btnCuadrado = new BotonCalculadora("x\u00b2", new Color(193, 255, 193) , new Color(70,70,70));
		btnCuadrado.addActionListener(this);
		btnPotencia = new BotonCalculadora("x"+"\u207f", new Color(193, 255, 193) , new Color(70,70,70));
		btnPotencia.addActionListener(this);
		btnRaiz = new BotonCalculadora("\u221a", new Color(193, 255, 193) , new Color(70,70,70));
		btnRaiz.addActionListener(this);
		btnLog = new BotonCalculadora("Lg", new Color(193, 255, 193) , new Color(70,70,70));
		btnLog.addActionListener(this);
	}
	
	
	private void montarVentana(){
		montarPanelPantalla();
		montarPanelIzquierda();
		montarPanelOperaciones();
		this.pack();
	}
	
	
	private void montarPanelPantalla(){
		pnlPantalla.add(txtPantalla);
		pnlPantalla.add(lblPantalla);
		cons.ipady=40;
		cons.gridx=0;
		cons.gridy=0;
		cons.gridwidth=GridBagConstraints.REMAINDER;
		cons.fill= GridBagConstraints.HORIZONTAL;
		this.getContentPane().add(pnlPantalla,cons);
		//reseteo de ipady
		cons.ipady=0;
	}
	
	private void montarPanelOperaciones() {
		GridBagConstraints cons2 = new GridBagConstraints();
		cons2.insets = new Insets(0, 5, 5, 0);
		
		cons2.gridx=0;
		cons2.gridy=0;
		pnlOperaciones.add(btnEntre,cons2);
		cons2.gridx=1;
		cons2.gridy=0;
		pnlOperaciones.add(btnCe,cons2);
		cons2.gridx=2;
		cons2.gridy=0;
		pnlOperaciones.add(btnC,cons2);
		cons2.gridx=0;
		cons2.gridy=1;
		pnlOperaciones.add(btnPor,cons2);
		cons2.gridx=1;
		cons2.gridy=1;
		pnlOperaciones.add(btnPorcentaje,cons2);
		cons2.gridx=2;
		cons2.gridy=1;
		pnlOperaciones.add(btnPi,cons2);
		cons2.gridx=0;
		cons2.gridy=2;
		pnlOperaciones.add(btnMenos,cons2);
		cons2.gridx=1;
		cons2.gridy=2;
		pnlOperaciones.add(btnMasMenos,cons2);
		cons2.gridx=2;
		cons2.gridy=2;
		pnlOperaciones.add(btnE,cons2);
		cons2.gridx=0;
		cons2.gridy=3;
		cons2.gridheight=2;
		cons2.weighty=1.0;
		cons2.fill=GridBagConstraints.VERTICAL;
		pnlOperaciones.add(btnMas,cons2);
		//reseteo gridheight
		cons2.gridheight=1;
		cons2.gridx=1;
		cons2.gridy=3;
		pnlOperaciones.add(btnCuadrado,cons2);
		cons2.gridx=2;
		cons2.gridy=3;
		pnlOperaciones.add(btnPotencia,cons2);
		cons2.gridx=1;
		cons2.gridy=4;
		pnlOperaciones.add(btnRaiz,cons2);
		cons2.gridx=2;
		cons2.gridy=4;
		pnlOperaciones.add(btnLog,cons2);
		
		cons.gridx=1;
		cons.gridy=1;
		cons.insets= new Insets(10, 30, 0, 0);
		this.getContentPane().add(pnlOperaciones,cons);
		cons.insets = new Insets(0, 0, 0, 0);
	}

	
	
	private void montarPanelIzquierda() {
		GridBagConstraints cons2 = new GridBagConstraints();

		cons2.fill=GridBagConstraints.HORIZONTAL;
		cons2.weightx=1.0;
		
		//panel de los numero 1 al 9
		pnlNumeros.add(btn1);
		pnlNumeros.add(btn2);
		pnlNumeros.add(btn3);
		pnlNumeros.add(btn4);
		pnlNumeros.add(btn5);
		pnlNumeros.add(btn6);
		pnlNumeros.add(btn7);
		pnlNumeros.add(btn8);
		pnlNumeros.add(btn9);
		cons2.insets= new Insets(0, 5, 5, 0);
		cons2.gridx=0;
		cons2.gridy=0;
		cons2.gridwidth=3;
		pnlIzquierda.add(pnlNumeros,cons2);
		
		cons2.gridx=0;
		cons2.gridy=1;
		cons2.gridwidth=2;
		pnlIzquierda.add(btn0,cons2);
		
		cons2.gridx=2;
		cons2.gridy=1;
		cons2.gridwidth=1;
		cons2.weightx=0.0;
		cons2.fill=GridBagConstraints.NONE;
		pnlIzquierda.add(btnPunto,cons2);
		
		cons2.gridx=0;
		cons2.gridy=2;
		cons2.gridwidth=3;
		cons2.fill=GridBagConstraints.HORIZONTAL;
		pnlIzquierda.add(btnIgual,cons2);
		
		cons.gridx=0;
		cons.gridy=1;
		cons.gridwidth=1;
		cons.insets=new Insets(10, 0, 0, 0);
		cons.fill=GridBagConstraints.HORIZONTAL;
		
		this.getContentPane().add(pnlIzquierda,cons);
	}

	@Override
	public void actionPerformed(ActionEvent e) {//Recoge los eventos de la ventana.
		if(e.getSource() == acerca){
			JOptionPane.showMessageDialog(null, "Calculadora realizada por Alejandro Campos","Acerca de...", JOptionPane.INFORMATION_MESSAGE);
		}
		if (e.getSource() == ayudaItem){
			try {
				Desktop.getDesktop().browse(new URI("http://es.wikipedia.org/wiki/Calculadora"));
			} catch (IOException | URISyntaxException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		
		}
		//Pulsa un bot�n de n�mero
		if(e.getSource()==btn1||e.getSource()==btn2||e.getSource()==btn3||e.getSource()==btn4||e.getSource()==btn5||e.getSource()==btn6||e.getSource()==btn7||e.getSource()==btn8||e.getSource()==btn9||e.getSource()==btn0)
		{
			if (nuevoOp==true){ //se mete aqui si no ha introducido ningun operador
				nuevoOp=false;
				txtPantalla.setText(e.getSource().toString());
				}
				
			
			else{
				if(txtPantalla.getText().length()<15){
					if(txtPantalla.getText().equals("0")){
						txtPantalla.setText(e.getSource().toString());
					}
					else{
						txtPantalla.setText(txtPantalla.getText()+e.getSource().toString());
					}
				}
			}
		}
		//Comprobamos ahora el resto de botones y programamos sus operaciones.
		if(txtPantalla.getText().length()<15){
			if(e.getSource()==btnPunto){
				if(txtPantalla.getText().indexOf(".")==-1)
					txtPantalla.setText(txtPantalla.getText()+".");
			}
		}
		if(e.getSource()==btnC) {
			txtPantalla.setText("0");
			lblPantalla.setText("");
		}
		if(e.getSource()==btnCe){
			txtPantalla.setText("0");
		}
		//ahora dos formas de convertir un double (Pi y E) en String para pasarselo a txtPantalla
		if(e.getSource()==btnPi){
			txtPantalla.setText(String.valueOf(PI));//invocando a la funcion
		}
		if(e.getSource()==btnE){
			txtPantalla.setText(E+"");//concatenandolo a una cadena (vacia)
		}
		//si pulsa el boton MasMenos, tiene que comprobar que haya algo en la pantalla (que no sea 0)
		if(e.getSource()==btnMasMenos && !(txtPantalla.getText().equals("0"))){
			if(txtPantalla.getText().indexOf("-")==-1){
				txtPantalla.setText("-"+txtPantalla.getText());
			}
			else{
				txtPantalla.setText(txtPantalla.getText().substring(1, txtPantalla.getText().length()));
			}
		}
		if(e.getSource()==btnCuadrado){
			operacion=0;
			nuevoOp=true;
			op1=Double.parseDouble(txtPantalla.getText());
			txtPantalla.setText(aString(Math.pow(op1, 2)));	
			lblPantalla.setText(aString(op1)+" ^2");			
		}
		if(e.getSource()==btnPotencia){
			nuevoOp=true;
			//cambiar nuevoOp para introducir el segundo operador
			if(operacion()){
				if(operacion!=6) igual();
				op1=Double.parseDouble(txtPantalla.getText());
				lblPantalla.setText(aString(op1));
				lblPantalla.setText(aString(op1)+" ^");
			}
			else{
				op2=Double.parseDouble(txtPantalla.getText());
				txtPantalla.setText(aString(Math.pow(op1,op2)));
				lblPantalla.setText(aString(op1)+" ^ "+aString(op2));
				op1=Double.parseDouble(txtPantalla.getText());
			}
			operacion=6;	
		}
		if(e.getSource()==btnRaiz){
			if(Double.parseDouble(txtPantalla.getText())<0){
				lblPantalla.setText("No existen Raices de n\u00fameros negativos");
			}
			else{
				operacion=0;
				op1=Double.parseDouble(txtPantalla.getText());
				txtPantalla.setText(aString(Math.sqrt((Double.parseDouble(txtPantalla.getText())))));	
				lblPantalla.setText("\u221a "+aString(op1));
			}
			operacion=0;
			nuevoOp=true;
		}
		if(e.getSource()==btnLog){
			if(Double.parseDouble(txtPantalla.getText())<0){
				lblPantalla.setText("No existen logaritmos de n\u00fameros negativos");
			}
			else{
				String aux=Math.log(Double.parseDouble(txtPantalla.getText()))+"";
				txtPantalla.setText(aux.substring(0,15));
			}
			operacion=0;
			nuevoOp=true;
		}
		if(e.getSource()==btnPorcentaje){
			nuevoOp=true;
			//cambiar nuevoOp para introducir el segundo operador
			if(operacion()){
				if(operacion!=5){
					op2=Double.parseDouble(txtPantalla.getText());
					lblPantalla.setText(lblPantalla.getText()+" "+aString(op2)+" %");
					switch (operacion){
					case 1:
						txtPantalla.setText(aString(op1+(op1*op2/100)));
						break;
					case 2:
						txtPantalla.setText(aString(op1-(op1*op2/100)));
						break;
					case 3:
						txtPantalla.setText(aString(op1*(op1*op2/100)));
						break;
					case 4:
						txtPantalla.setText(aString(op1/(op1*op2/100)));
						break;
					default:
						lblPantalla.setText("Opci\u00f3n inv\u00e1lida");
						txtPantalla.setText("");
					}
				}
				else{
					lblPantalla.setText("Opci\u00f3n Inv\u00e1lida");
				}
			}
			else{
				op2=Double.parseDouble(txtPantalla.getText());
				//txtPantalla.setText(aString((op1*op2/100)));
				lblPantalla.setText(aString(op1)+" % "+aString(op2));
				op1=Double.parseDouble(txtPantalla.getText());
			}
			operacion=5;
		}
		
		//OPERACIONES B�SICAS:
		if(e.getSource()==btnMas){
			nuevoOp=true;
			//cambiar nuevoOp para introducir el segundo operador
			if(operacion()){
				if(operacion!=1) igual();
				op1=Double.parseDouble(txtPantalla.getText());
				lblPantalla.setText(aString(op1)+" +");
			}
			else{
				op2=Double.parseDouble(txtPantalla.getText());
				txtPantalla.setText(aString(op1+op2));
				lblPantalla.setText(aString(op1)+" + "+aString(op2));
				op1=Double.parseDouble(txtPantalla.getText());
			}
			operacion=1;
		}//fin btnmas
		if(e.getSource()==btnMenos){
			nuevoOp=true;
			//cambiar nuevoOp para introducir el segundo operador
			if(operacion()){
				if(operacion!=2) igual();
				op1=Double.parseDouble(txtPantalla.getText());
				lblPantalla.setText(aString(op1)+" -");
			}
			else{
				op2=Double.parseDouble(txtPantalla.getText());
				txtPantalla.setText(aString(op1-Double.parseDouble(txtPantalla.getText())));
				lblPantalla.setText(aString(op1)+" - "+aString(op2));
				op1=Double.parseDouble(txtPantalla.getText());
			}			
			operacion=2;
		}//fin btnMenos
		if(e.getSource()==btnPor){
			operacion=3;
			nuevoOp=true;
			//cambiar nuevoOp para introducir el segundo operador
			if(operacion()){
				if(operacion!=3) igual();
				op1=Double.parseDouble(txtPantalla.getText());
				lblPantalla.setText(aString(op1)+" *");
			}
			else{
				op2=Double.parseDouble(txtPantalla.getText());
				txtPantalla.setText(aString(op1*Double.parseDouble(txtPantalla.getText())));
				lblPantalla.setText(aString(op1)+" * "+aString(op2));
				op1=Double.parseDouble(txtPantalla.getText());
			}			
			operacion=3;
		}//fin btnPor
		if(e.getSource()==btnEntre){
			operacion=4;
			nuevoOp=true;
			//cambiar nuevoOp para introducir el segundo operador
			if(operacion()){
				if(operacion!=4) igual();
				op1=Double.parseDouble(txtPantalla.getText());
				lblPantalla.setText(aString(op1)+" /");
			}
			else{
				op2=Double.parseDouble(txtPantalla.getText());
				txtPantalla.setText(aString(op1/Double.parseDouble(txtPantalla.getText())));
				lblPantalla.setText(aString(op1)+" / "+aString(op2));
				op1=Double.parseDouble(txtPantalla.getText());
			}	
			operacion=4;
		}//fin btnEntre
		if(e.getSource()==btnIgual){
			nuevoOp=true;
			igual();
			operacion=0;
		}//fin del btnigual
		
		
		//Botones del Menu
		//Menu Archivo
		if(e.getSource()==salirItem){
			this.setVisible(false);
		}
		
	
	}//fin metodo Action Performed
	
	
	//Implementacion del boton IGUAL
	private void igual(){
		op2=Double.parseDouble(txtPantalla.getText());
		switch(operacion){
		case 1:
			op2=Double.parseDouble(txtPantalla.getText());
			txtPantalla.setText(aString(op1+Double.parseDouble(txtPantalla.getText())));
			lblPantalla.setText(aString(op1)+" + "+aString(op2));
			op1=Double.parseDouble(txtPantalla.getText());
			break;
		case 2:
			op2=Double.parseDouble(txtPantalla.getText());
			txtPantalla.setText(aString(op1-Double.parseDouble(txtPantalla.getText())));
			lblPantalla.setText(aString(op1)+" - "+aString(op2));
			op1=Double.parseDouble(txtPantalla.getText());
			break;
		case 3:
			op2=Double.parseDouble(txtPantalla.getText());
			txtPantalla.setText(aString(op1*Double.parseDouble(txtPantalla.getText())));
			lblPantalla.setText(aString(op1)+" * "+aString(op2));
			op1=Double.parseDouble(txtPantalla.getText());
			break;
		case 4:
			op2=Double.parseDouble(txtPantalla.getText());
			txtPantalla.setText(aString(op1/Double.parseDouble(txtPantalla.getText())));
			lblPantalla.setText(aString(op1)+" / "+aString(op2));
			op1=Double.parseDouble(txtPantalla.getText());
			break;
		case 5:
			op2=Double.parseDouble(txtPantalla.getText());
			txtPantalla.setText(aString((op1*op2/100)));
			lblPantalla.setText(aString(op1)+" % "+aString(op2));
			op1=Double.parseDouble(txtPantalla.getText());
			break;
		case 6:
			op2=Double.parseDouble(txtPantalla.getText());
			txtPantalla.setText(aString(Math.pow(op1,op2)));
			lblPantalla.setText(aString(op1)+" ^ "+aString(op2));
			op1=Double.parseDouble(txtPantalla.getText());
			break;
		}
	}
	
	//Este metodo devuelve un String de un double quitandole el .0
	//por ejemplo, para 2.0 devolveria 2.
	private String aString (double d){
		String aux;
		aux=d+"";
		if(aux.indexOf(".")==aux.length()-2&&aux.substring(aux.length()-1).equals("0")){
			return aux.substring(0,aux.indexOf(".0"));
		}
		else return d + "";
	}//fin de aString
	
	//metodo que comprueba si hay un signo de operacion en lblPantalla.
	//devuelve TRUE si NO hay ningun signo de operacion en lblPantalla
	private boolean operacion(){
		if (lblPantalla.getText().indexOf("+")==-1&&lblPantalla.getText().indexOf("-")==-1&&
				lblPantalla.getText().indexOf("*")==-1&&lblPantalla.getText().indexOf("/")==-1
				&&lblPantalla.getText().indexOf("%")==-1 &&lblPantalla.getText().indexOf("^")==-1){
			return true;
		}
		
		
		else return false;
	}//fin operacion
				
}//fin clase

