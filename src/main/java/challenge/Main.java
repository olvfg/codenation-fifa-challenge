package challenge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args){
        Main main = new Main();
        main.q1();
    }

    ArrayList<String[]> table;
    String[] tableColumns;

    public boolean processFile() throws NullPointerException{
        try
        {
            URL url = getClass().getClassLoader().getResource("data.csv");
            File file = new File(url.getFile());

            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            table = new ArrayList<>();

            // read column names
            String line = bufferedReader.readLine();
            tableColumns = (line.split(","));

            // read entries
            while((line = bufferedReader.readLine()) != null){
                table.add(line.split(","));
            }
            System.out.println(String.format("Read %s entries", table.size()));
        }
        catch (FileNotFoundException e){
            System.out.println(e.getMessage());
            return false;
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    // Quantas nacionalidades (coluna `nationality`) diferentes existem no arquivo?
	public int q1() {
        if (processFile()){
            int nationalityIndex = Arrays.asList(tableColumns).indexOf("nationality");
            if(nationalityIndex < 0)
            {
                System.out.println("nationality not found");
                return -1;
            }

            for (String[] playerInfo :
                    table) {
                System.out.println(playerInfo[nationalityIndex]);
            }
        }

        return -1;
	}

    // Quantos clubes (coluna `club`) diferentes existem no arquivo?
    // Obs: Existem jogadores sem clube.
	public int q2() {
		return 0;
	}

    // Liste o primeiro nome (coluna `full_name`) dos 20 primeiros jogadores.
	public List<String> q3() {
		return null;
	}
    // Quem são os top 10 jogadores que possuem as maiores cláusulas de rescisão?
    // (utilize as colunas `full_name` e `eur_release_clause`)

	public List<String> q4() {
		return null;
	}
    // Quem são os 10 jogadores mais velhos (use como critério de desempate o campo `eur_wage`)?
    // (utilize as colunas `full_name` e `birth_date`)

	public List<String> q5() {
		return null;
	}
    // Conte quantos jogadores existem por idade. Para isso, construa um mapa onde as chaves são as idades e os valores a contagem.
    // (utilize a coluna `age`)

	public Map<Integer, Integer> q6() {
		return null;
	}

}
