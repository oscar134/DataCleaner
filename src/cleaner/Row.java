package cleaner;

public class Row {
	public String fecha;
	public String valor;
	public String noticias="";
	public Row(String fecha, String valor) {
		this.fecha = fecha;
		this.valor = valor;
	}
	public void addNoticia(String string) {
		string = cleanString(string);
		
		noticias+=" "+string;
	}
	
	private String cleanString(String string) {
		string = string.replace("b'", "");
		string = string.replace("\"b\"\"", "");
		string = string.replace("\\", "");
		string = string.replace("\"", "");
		string = string.replace("\t", "");


		return string;
	}
	@Override
	public String toString() {
		return fecha+"\t"+valor+"\t"+noticias;
	}
}
