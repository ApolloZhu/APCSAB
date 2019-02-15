/*
MIT License

Copyright (c) 2017-2019 Apollo/Zhiyu Zhu/朱智语 <public-apollonian@outlook.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package net.fcpsschools._1685666._3.lab._4_map;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

/**
 * @author Apollo/Zhiyu Zhu/朱智语
 * Period: 1
 * Date: 2018/03/13 23:59
 * What I learned:
 * - Must explain how my method works.
 * How I feel about this lab:
 * - Very similar to acting school one.
 * What I wonder:
 * - Is there another way to redirect input/output in Java?
 */
public class ZhiyuZhu_Period1_Dictionary_shell {
    public static void main(String[] args) throws Throwable {
        // Redirect output to that file.
        System.setOut(new PrintStream(new FileOutputStream("dictionaryOutput.txt")));
        Map<String, Set<String>> eng2spn = new TreeMap<>();
        // Java 7, try-with-resources.
        InputStream stream = ZhiyuZhu_Period1_Dictionary_shell.class
                .getResourceAsStream("spanglish.txt");
        // This will automatically close
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
        // This will ensure that get will always be non-null
        if (!engToSpnDictionary.containsKey(word))
            engToSpnDictionary.put(word, new TreeSet<>());
        engToSpnDictionary.get(word).add(translation);
    }

    /**
     * @return a Spanish to English dictionary
     */
    private static Map<String, Set<String>> reverse(Map<String, Set<String>> engToSpnDictionary) {
        // Again, we want it to be ordered
        Map<String, Set<String>> reversed = new TreeMap<>();
        for (String englishTerm : engToSpnDictionary.keySet()) {
            // We know there are more than one translations
            for (String spanishTerm : engToSpnDictionary.get(englishTerm)) {
                // Similar to add, supply non-null set
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
