package net.fcpsschools._1685666._3.lab._4_map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.Utilities;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;
import java.util.prefs.Preferences;

/**
 * @author ApolloZhu, Pd. 1
 */
public class DictionaryPanel extends JPanel {
    Preferences pref = Preferences.userNodeForPackage(this.getClass());
    Dictionary dictionary = new Dictionary(pref);
    Dictionary.Language from = Dictionary.Language.ENGLISH;
    JButton loadButton = new JButton("Load Dictionary");
    JButton switchButton = new JButton();
    JButton autoSwitchButton = new JButton();
    JButton addWordButton = new JButton("Add Translation");
    JButton saveButton = new JButton("Save Dictionary");

    JTextArea editableTextArea = new JTextArea("Type a word to translate");
    JTextArea nonEditableTextArea = new JTextArea("Nothing to translate");
    private boolean isActive = false;
    private boolean isAutoDetecting = false;

    public DictionaryPanel() {
        new Thread(() -> {
            if (dictionary.isEmpty()) {
                synchronized (this) {
                    dictionary = Dictionary.composeDefault();
                }
                dictionary.save(pref);
            }
        }).start();

        setLayout(new BorderLayout());
        JPanel allControls = new JPanel();
        add(allControls, BorderLayout.NORTH);
        allControls.setLayout(new GridLayout(2, 1));
        JPanel controls = new JPanel();
        allControls.add(controls);
        controls.add(loadButton);
        loadButton.addActionListener(ignored -> {
            dictionary.load(this);
        });

        controls.add(addWordButton);
        addWordButton.addActionListener(ignored -> {
            addWord();
        });
        controls.add(saveButton);
        saveButton.addActionListener(ignored -> {
            dictionary.save(this);
        });

        JPanel secondRow = new JPanel();
        allControls.add(secondRow);

        secondRow.add(switchButton);
        switchButton.addActionListener(ignored -> {
            setLanguage(from.translateTo(), SetLanguageMode.MANUAL);
        });
        secondRow.add(autoSwitchButton);
        autoSwitchButton.addActionListener(ignored -> {
            setAutoDetecting(!isAutoDetecting);
        });
        setAutoDetecting(true);

        // MARK: Text Input

        JPanel textFields = new JPanel();
        textFields.setBorder(new EmptyBorder(8, 8, 8, 8));
        add(textFields, BorderLayout.CENTER);
        textFields.setLayout(new GridLayout(1, 2, 8, 8));
        textFields.add(editableTextArea);
        setup(editableTextArea);
        editableTextArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                translate();
            }

            @Override
            public void keyPressed(KeyEvent e) {
                translate();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                translate();
            }
        });
        textFields.add(nonEditableTextArea);
        setup(nonEditableTextArea);
        nonEditableTextArea.setEditable(false);
        nonEditableTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });


        setLanguage(Dictionary.Language.ENGLISH, SetLanguageMode.RESET);
    }

    public void setAutoDetecting(boolean isAutoDetecting) {
        if (isAutoDetecting == this.isAutoDetecting) return;
        this.isAutoDetecting = isAutoDetecting;
        autoSwitchButton.setText(isAutoDetecting
                ? "Disable Auto Detection"
                : "Enable Auto Detection"
        );
        translate();
    }

    private void setLanguage(Dictionary.Language newLanguage, SetLanguageMode mode) {
        from = newLanguage;
        switchButton.setText(from.labelText());
        switch (mode) {
            case AUTO:
                break;
            case MANUAL:
                translate();
                break;
            case RESET:
                translateNothing();
                break;
        }
    }

    private void translateNothing() {
        String text = getText();
        if (null == text || text.isEmpty())
            nonEditableTextArea.setText("Nothing to translate");
    }

    public String getText() {
        try {
            int start = Utilities.getWordStart(editableTextArea, 0);
            int end = Utilities.getWordEnd(editableTextArea, start);
            String text = editableTextArea.getText(start, end - start);
            return text.toLowerCase();
        } catch (Throwable ignored) {
            return null;
        }
    }

    public void translate() {
        Dictionary.Language lang = from;
        String word = getText();
        Set<String> translation = dictionary.get(lang, word);
        if (null == translation || translation.isEmpty()) {
            if (isAutoDetecting) {
                new Thread(() -> {
                    tryTranslate(from.translateTo(), word);
                }).start();
            } else if (null == word || 0 == word.length()) {
                translateNothing();
            } else {
                noTranslationFound();
            }
        } else {
            setTranslation(translation);
        }
    }

    public void tryTranslate(Dictionary.Language newLanguage, String word) {
        try {
            Set<String> translation = dictionary.get(newLanguage, word);
            if (from.translateTo() != newLanguage
                    || !word.equals(getText())
                    || null == translation
                    || translation.isEmpty()) {
                if (null == word || 0 == word.length()) {
                    translateNothing();
                } else {
                    noTranslationFound();
                }
            } else {
                setLanguage(newLanguage, SetLanguageMode.AUTO);
                setTranslation(translation);
            }
        } catch (Throwable ignored) {
            ignored.printStackTrace();
        }
    }

    private void noTranslationFound() {
        isActive = true;
        String text = "No translation found. " +
                "Add a translation for " + getText() + "?";
        SwingUtilities.invokeLater(() ->
                nonEditableTextArea.setText(text)
        );
    }

    private void setTranslation(Set<String> words) {
        isActive = false;
        try {
            String string = words.toString();
            string = string.substring(1, string.length() - 1);
            nonEditableTextArea.setText(string);
        } catch (Throwable ignored) {
            nonEditableTextArea.setText(ignored.getLocalizedMessage());
        }
    }

    private void setup(JTextArea textArea) {
        textArea.setLineWrap(true);
        textArea.setFont(textArea.getFont().deriveFont(32f));
        textArea.setWrapStyleWord(true);
    }

    private void addWord() {
        // TODO: Add word
    }

    enum SetLanguageMode {RESET, AUTO, MANUAL}
}
