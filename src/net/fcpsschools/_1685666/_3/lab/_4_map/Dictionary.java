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

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.prefs.Preferences;

/**
 * @author Apollo/Zhiyu Zhu/朱智语, Pd. 1
 */
public class Dictionary {
    private Map<String, Set<String>> englishKeyed = new HashMap<>();
    private Map<String, Set<String>> spanishKeyed = new HashMap<>();

    protected Dictionary() {

    }

    public Dictionary(Preferences pref) {
        try {
            for (String term : pref.keys()) {
                String value = pref.get(term, "");
                String[] translations = value.split(", ");
                for (String translation : translations) {
                    add(Language.ENGLISH, term, translation);
                }
            }
        } catch (Throwable thrown) {
            Dictionary_GUI.showError(thrown);
        }
    }

    public Dictionary(
            Language keyLanguage,
            Map<String, Set<String>> map
    ) {
        for (String term : map.keySet()) {
            for (String translation : map.get(term)) {
                add(keyLanguage, term, translation);
            }
        }
    }

    public Dictionary(
            Map<String, Set<String>> englishKeyed,
            Map<String, Set<String>> spanishKeyed
    ) {
        this.englishKeyed = englishKeyed;
        this.spanishKeyed = spanishKeyed;
    }

    private static boolean add(
            Map<String, Set<String>> map1, String key1,
            Map<String, Set<String>> map2, String key2
    ) {
        if (!map2.containsKey(key2))
            map2.put(key2, new HashSet<>());
        if (!map1.containsKey(key1))
            map1.put(key1, new HashSet<>());

        boolean result = map2.get(key2).add(key1);
        return map1.get(key1).add(key2) && result;
    }

    public static Dictionary composeDefault() {
        Dictionary dict = new Dictionary();
        dict.add(Language.ENGLISH, "holiday", "fiesta");
        dict.add(Language.ENGLISH, "holiday", "vacaciones");
        dict.add(Language.ENGLISH, "party", "fiesta");
        dict.add(Language.ENGLISH, "celebration", "fiesta");
        dict.add(Language.ENGLISH, "feast", "fiesta");
        dict.add(Language.ENGLISH, "hand", "mano");
        dict.add(Language.ENGLISH, "father", "padre");
        dict.add(Language.ENGLISH, "priest", "padre");
        dict.add(Language.ENGLISH, "sun", "sol");
        dict.add(Language.ENGLISH, "son", "hijo");
        dict.add(Language.ENGLISH, "sleep", "dormir");
        dict.add(Language.ENGLISH, "vacation", "vacaciones");
        dict.add(Language.ENGLISH, "plaza", "plaza");
        dict.add(Language.ENGLISH, "banana", "banana");
        dict.add(Language.ENGLISH, "hello", "hola");
        dict.add(Language.ENGLISH, "good", "bueno");
        dict.add(Language.ENGLISH, "double", "doble");
        dict.add(Language.ENGLISH, "double", "doblar");
        dict.add(Language.ENGLISH, "double", "duplicar");
        dict.add(Language.ENGLISH, "computer", "ordenador");
        dict.add(Language.ENGLISH, "computer", "computadora");
        dict.add(Language.ENGLISH, "program", "programa");
        dict.add(Language.ENGLISH, "program", "programar");
        return dict;
    }

    public boolean add(Language language, String from, String to) {
        switch (language) {
            case ENGLISH:
                return add(englishKeyed, from, spanishKeyed, to);
            case SPANISH:
                return add(spanishKeyed, from, englishKeyed, to);
        }
        return false;
    }

    public boolean load(Component parent) {
        try {
            String path = choosePath(parent, "Load Dictionary");
            if (null == path) return false;
            // Java 7, try-with-resources.
            try (Scanner infile = new Scanner(new File(path))) {
                while (infile.hasNext()) {
                    add(Language.ENGLISH, infile.next(), infile.next());
                }
            }
            JOptionPane.showMessageDialog(parent,
                    "Dictionary loaded from " + path,
                    "Updated!", JOptionPane.INFORMATION_MESSAGE
            );
            return true;
        } catch (Throwable thrown) {
            Dictionary_GUI.showError(thrown);
            return false;
        }
    }

    public boolean save(Component parent) {
        try {
            String path = choosePath(parent, "Save Dictionary");
            if (null == path) return false;
            PrintStream old = System.out;
            System.setOut(new PrintStream(new FileOutputStream(path)));
            englishKeyed.forEach((term, translations) -> {
                translations.forEach(translation -> {
                    System.out.println(term);
                    System.out.println(translation);
                });
            });
            System.setOut(old);
            JOptionPane.showMessageDialog(parent,
                    "Dictionary saved to " + path,
                    "Saved!", JOptionPane.INFORMATION_MESSAGE
            );
            return true;
        } catch (Throwable thrown) {
            Dictionary_GUI.showError(thrown);
            return false;
        }
    }

    public boolean save(Preferences to) {
        englishKeyed.forEach((term, translations) -> {
            String array = translations.toString();
            to.put(term, array.substring(1, array.length() - 1));
        });
        try {
            to.sync();
            return true;
        } catch (Throwable thrown) {
            return false;
        }
    }

    private String choosePath(Component parent, String title) {
        JFileChooser chooser = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter(
                "Plain Text (*.txt)", "txt"
        );
        chooser.setFileFilter(filter);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setDialogTitle(title);
        if (chooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            try {
                String path = chooser.getSelectedFile().getAbsolutePath();
                if (!path.endsWith(".txt")) path += ".txt";
                return path;
            } catch (Throwable thrown) {
                Dictionary_GUI.showError(thrown);
            }
        } else JOptionPane.showMessageDialog(parent,
                "You didn't choose a file.",
                "Cancelled!", JOptionPane.WARNING_MESSAGE
        );
        return null;
    }

    public boolean isEmpty() {
        return englishKeyed.isEmpty() || spanishKeyed.isEmpty();
    }

    public Set<String> get(Language language, String term) {
        switch (language) {
            case ENGLISH:
                return englishKeyed.get(term);
            case SPANISH:
                return spanishKeyed.get(term);
        }
        return null;
    }

    public String getWithPrefix(Language language, String prefix) {
        switch (language) {
            case ENGLISH:
                return getWithPrefix(englishKeyed, prefix);
            case SPANISH:
                return getWithPrefix(spanishKeyed, prefix);
        }
        return null;
    }

    private String getWithPrefix(Map<String, Set<String>> map, String prefix) {
        for (String term : map.keySet())
            if (term.startsWith(prefix))
                return term;
        return null;
    }

    enum Language {
        ENGLISH, SPANISH;

        Language translateTo() {
            switch (this) {
                case ENGLISH:
                    return SPANISH;
                default:
                    return ENGLISH;
            }
        }

        String knownAs() {
            switch (this) {
                case ENGLISH:
                    return "English";
                case SPANISH:
                    return "Spanish";
            }
            return "Unknown";
        }

        String labelText() {
            switch (this) {
                case ENGLISH:
                    return "English >> Spanish";
                case SPANISH:
                    return "Spanish >> English";
            }
            return "Switch Language";
        }
    }
}
