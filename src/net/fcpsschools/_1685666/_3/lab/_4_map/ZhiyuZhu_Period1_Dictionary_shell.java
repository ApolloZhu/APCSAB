package net.fcpsschools._1685666._3.lab._4_map;

/**
 * Name: Zhiyu Zhu
 * Period: 1
 * Date: 3/13
 * What I learned:
 * - Must explain how my method works
 * How I feel about this lab:
 * What I wonder:
 */

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

public class ZhiyuZhu_Period1_Dictionary_shell {
    public static void main(String[] args) throws Throwable {
        // Redirect output to that file.
        System.setOut(new PrintStream(new FileOutputStream("dictionaryOutput.txt")));
        Map<String, Set<String>> eng2spn = new TreeMap<>();
        // Java 7, try-with-resources.
        InputStream stream = ZhiyuZhu_Period1_Dictionary_shell.class.getResourceAsStream("spanglish.txt");
        try (Scanner infile = new Scanner(stream)) {
            while (infile.hasNext()) {
                add(eng2spn, infile.next(), infile.next());
            }
        }
        System.out.println("ENGLISH TO SPANISH");
        display(eng2spn);

        Map<String, Set<String>> spn2eng = reverse(eng2spn);
        System.out.println("SPANISH TO ENGLISH");
        display(spn2eng);
    }

    /**
     * display the contents of a dictionary
     */
    private static void display(Map<String, Set<String>> m) {
        for (String key : m.keySet())
            System.out.printf("\t%s %s\n", key, m.get(key));
        System.out.println();
    }

    /**
     * Insert a new pair to the English to Spanish Dictionary
     */
    private static void add(Map<String, Set<String>> engToSpnDictionary, String word, String translation) {
        if (!engToSpnDictionary.containsKey(word))
            engToSpnDictionary.put(word, new TreeSet<>());
        engToSpnDictionary.get(word).add(translation);
    }

    /**
     * @return a Spanish to English dictionary
     */
    private static Map<String, Set<String>> reverse(Map<String, Set<String>> engToSpnDictionary) {
        Map<String, Set<String>> reversed = new TreeMap<>();
        for (String englishTerm : engToSpnDictionary.keySet()) {
            for (String spanishTerm : engToSpnDictionary.get(englishTerm)) {
                if (!reversed.containsKey(spanishTerm))
                    reversed.put(spanishTerm, new TreeSet<>());
                reversed.get(spanishTerm).add(englishTerm);
            }
        }
        return reversed;
    }
}

/* @formatter:off
	INPUT:
   	    holiday
		fiesta
		holiday
		vacaciones
		party
		fiesta
		celebration
		fiesta
     <etc.>
  ***********************************
	OUTPUT:
		ENGLISH TO SPANISH
			banana [banana]
			celebration [fiesta]
			computer [computadora, ordenador]
			double [doblar, doble, duplicar]
			father [padre]
			feast [fiesta]
			good [bueno]
			hand [mano]
			hello [hola]
			holiday [fiesta, vacaciones]
			party [fiesta]
			plaza [plaza]
			priest [padre]
			program [programa, programar]
			sleep [dormir]
			son [hijo]
			sun [sol]
			vacation [vacaciones]

		SPANISH TO ENGLISH
			banana [banana]
			bueno [good]
			computadora [computer]
			doblar [double]
			doble [double]
			dormir [sleep]
			duplicar [double]
			fiesta [celebration, feast, holiday, party]
			hijo [son]
			hola [hello]
			mano [hand]
			ordenador [computer]
			padre [father, priest]
			plaza [plaza]
			programa [program]
			programar [program]
			sol [sun]
			vacaciones [holiday, vacation]

@formatter:on */
