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
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class Main {

    String[] tableColumns;
    ArrayList<String[]> table;

    public static void main(String[] args){
        Main main = new Main();

        //int nationalities = main.q1();
        //System.out.println(nationalities +" nationalities found.");

        //int clubs = main.q2();
        //System.out.println(clubs +" clubs found.");

        List<String> names = main.q3();
        System.out.println(names.size() + " names found.");
        System.out.println(names);
    }

    public void processFile() throws NullPointerException, FileNotFoundException, IOException {

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

    // Quantas nacionalidades (coluna `nationality`) diferentes existem no arquivo?
	public int q1() {
        try {
            processFile();
            int nationalityIndex = Arrays.asList(tableColumns).indexOf("nationality");
            if(nationalityIndex < 0)
            {
                System.out.println("nationality not found");
                return -1;
            }

            List<String> nationalities = table.stream().map(p -> p[nationalityIndex])
                    .distinct().collect(Collectors.toList());

            return nationalities.size();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return -1;
        }
	}

    // Quantos clubes (coluna `club`) diferentes existem no arquivo?
    // Obs: Existem jogadores sem clube.
	public int q2() {
        try {
            processFile();
            int clubIndex = Arrays.asList(tableColumns).indexOf("club");
            if(clubIndex < 0)
            {
                System.out.println("club not found");
                return -1;
            }

            List<String> clubs = table.stream().map(p -> p[clubIndex])
                    .filter(c -> !c.isEmpty())
                    .distinct().collect(Collectors.toList());

            return clubs.size();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return -1;
        }
	}

    // Liste o primeiro nome (coluna `full_name`) dos 20 primeiros jogadores.
	public List<String> q3() {
        try {
            processFile();
            int full_nameIndex = Arrays.asList(tableColumns).indexOf("full_name");
            if(full_nameIndex < 0)
            {
                System.out.println("club not found");
                return null;
            }

            List<String> names = table.subList(0, 20).stream().map(p -> p[full_nameIndex])
                    .collect(Collectors.toList());

            return names;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
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
