package calculadora;

import java.awt.Color;
import javax.swing.JButton;

public class BotonCalculadora extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String texto;
	private Color colorFondo, colorFuente;
	
	public BotonCalculadora(String texto, Color colorFondo, Color colorFuente){
		super(texto);
		this.setBackground(colorFondo);
		this.setForeground(colorFuente);
		
	}
	
	@Override
	public String toString(){
		return this.getText();
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public Color getColorFondo() {
		return colorFondo;
	}
	public void setColorFondo(Color colorFondo) {
		this.colorFondo = colorFondo;
	}
	public Color getColorFuente() {
		return colorFuente;
	}
	public void setColorFuente(Color colorFuente) {
		this.colorFuente = colorFuente;
	}
	
}