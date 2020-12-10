package cleaner;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Cleaner {

	private List<Row> rows = new ArrayList<>();

	public Cleaner(String filename) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"));

			String line = null;

			Row current = null;

			br.readLine(); // Saltarse cabeceras

			while ((line = br.readLine()) != null) {

				String[] elements = line.split(",");

				for (int i = 0; i < elements.length; i++) {
					if (i<elements.length-1 && isDate(elements[i].replace("\"", "")) && isTarget(elements[i+1])) {
						savePrevious(current);
						current = new Row(elements[i], elements[i + 1]);
						i++;
					} else {
						current.addNoticia(elements[i]);
					}
				}

			}
			savePrevious(current);

			br.close();
		} catch (IOException e) {
			System.err.println(e);
		}

	}

	private boolean isTarget(String string) {
		return "\"UP\"".equals(string) || "\"DOWN\"".equals(string);
	}

	private boolean isDate(String string) {
		string = string.replace("\"", "");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		df.setLenient(false);
		try {
			df.parse(string);
			return true;
		} catch (ParseException e) {
			return false;
		}

	}

	private void savePrevious(Row row) {
		if (row != null) {
			rows.add(row);
		}
	}

	public void print() {
		try {
			FileWriter fw = new FileWriter("results.tsv", false);
			fw.write("Fecha\tLabel\tNoticias\n");
			fw.write("s\td\ts\n");
			fw.write("meta\tclass\tmeta\n");

			
			for (Row row : rows) {
				fw.write(row.toString());
				fw.write("\n");
			}
			
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
