package challenge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private String[] tableColumns;
    private ArrayList<String[]> table;

    public static void main(String[] args){
        Main main = new Main();

        //int nationalities = main.q1();
        //System.out.println(nationalities +" nationalities found.");

        //int clubs = main.q2();
        //System.out.println(clubs +" clubs found.");

        //List<String> names = main.q3();
        //System.out.println(names.size() + " names found.");
        //System.out.println(names);

//        List<String> bigClausesNames = main.q4();
//        if(bigClausesNames == null) return;
//        System.out.println(bigClausesNames.size() + " names found.");
//        System.out.println(bigClausesNames);

        List<String> bigClausesNames = main.q5();
        if(bigClausesNames == null) return;
        System.out.println(bigClausesNames.size() + " names found.");
        System.out.println(bigClausesNames);
    }

    public void processFile() throws NullPointerException, IOException {

            URL url = getClass().getClassLoader().getResource("data.csv");
            String fileUrl = url.getFile();
            File file = new File(fileUrl);

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


    private int getColumnIndex(String columnName)throws Exception {
        int index = Arrays.asList(tableColumns).indexOf(columnName);
        if(index < 0){
            throw new Exception(columnName + " not found.");
        }

        return index;
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
            int clubIndex = getColumnIndex("club");

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

            return table.subList(0, 20).stream().map(p -> p[full_nameIndex])
                    .collect(Collectors.toList());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
	}
    // Quem são os top 10 jogadores que possuem as maiores cláusulas de rescisão?
    // (utilize as colunas `full_name` e `eur_release_clause`)

	public List<String> q4() {
		try {
		    processFile();
		    int clauseIndex = Arrays.asList(tableColumns).indexOf("eur_release_clause");
		    if(clauseIndex < 0)
            {
                System.out.println("eur_release_clause not found");
                return null;
            }

            int full_nameIndex = Arrays.asList(tableColumns).indexOf("full_name");
            if(full_nameIndex < 0)
            {
                System.out.println("club not found");
                return null;
            }

            Stream<String[]> playerStream = table.stream().filter(p -> !p[clauseIndex].isEmpty()).sorted((p1, p2) -> {
                double p1Clause = Double.parseDouble(p1[clauseIndex]);
                double p2Clause = Double.parseDouble(p2[clauseIndex]);
                return Double.compare(p2Clause, p1Clause);
            });

            return playerStream.limit(10).map(p -> p[full_nameIndex]).collect(Collectors.toList());

        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
	}
    // Quem são os 10 jogadores mais velhos (use como critério de desempate o campo `eur_wage`)?
    // (utilize as colunas `full_name` e `birth_date`)
	public List<String> q5() {
		try {
            processFile();
            int bithDateIndex = getColumnIndex("birth_date");
            int wageIndex = getColumnIndex("eur_wage");
            int full_nameIndex = getColumnIndex("full_name");

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            Stream<String[]> playerStream = table.stream().filter(p -> !p[bithDateIndex].isEmpty()).sorted((p1, p2) -> {
                try {
                    Date p1Birth = df.parse(p1[bithDateIndex]);
                    Date p2Birth = df.parse(p2[bithDateIndex]);
                    int comparison = p1Birth.compareTo(p2Birth);
                    if (comparison == 0) {
                        Double p1Wage = Double.parseDouble(p1[wageIndex]);
                        Double p2Wage = Double.parseDouble(p2[wageIndex]);
                        return Double.compare(p1Wage, p2Wage);
                    }
                    else {
                        return comparison;
                    }
                }
                catch (ParseException e){
                    System.out.println("Error parsing birthday dates.");
                    return 0;
                }
            });

            return playerStream.limit(10).map(p -> {
                try{
                    Date today = new Date();
                    Date d = df.parse(p[bithDateIndex]);
                    long diff = today.getTime() - d.getTime();
                    long years = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) / 365;

                    return p[full_nameIndex] + " - " + years + "yo";
                }
                catch (ParseException e){
                    System.out.println("Error parsing birthday dates.");
                    return "<null>";
                }

            }).collect(Collectors.toList());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
	}
    // Conte quantos jogadores existem por idade. Para isso, construa um mapa onde as chaves são as idades e os valores a contagem.
    // (utilize a coluna `age`)

	public Map<Integer, Integer> q6() {
		return null;
	}

}
