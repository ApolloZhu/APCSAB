package net.fcpsschools._1685666._3.lab._4_map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Utilities;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.util.Set;
import java.util.prefs.Preferences;

/**
 * @author ApolloZhu, Pd. 1
 * TODO: Joseph Xu: other reaction when input is nonsense.
 */
public class DictionaryPanel extends JPanel {
    Preferences pref = Preferences.userNodeForPackage(this.getClass());
    Dictionary dictionary = new Dictionary(pref);
    Dictionary.Language from = Dictionary.Language.ENGLISH;
    JButton loadButton = new JButton("Load Dictionary");
    JButton switchButton = new JButton();
    JButton autoSwitchButton = new JButton();
    JButton addTranslationButton = new JButton("Add Translation");
    JButton saveButton = new JButton("Save Dictionary");

    JTextArea editableTextArea = new JTextArea("Type a word to translate");
    JTextArea nonEditableTextArea = new JTextArea();
    private boolean isActive = false;
    private boolean isAutoDetecting = false;
    private boolean isShowingPlaceHolder = true;
    MouseListener removePlaceholderListener = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            removePlaceholderIfNeeded();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            removePlaceholderIfNeeded();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            removePlaceholderIfNeeded();
        }
    };
    private boolean shouldPredict = true;

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

        controls.add(addTranslationButton);
        addTranslationButton.addActionListener(ignored -> {
            addTranslationForWord();
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
        editableTextArea.addMouseListener(removePlaceholderListener);
        editableTextArea.setForeground(Color.lightGray);
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
        editableTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                shouldPredict = true;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(() -> {
                    editableTextArea.replaceSelection("\n");
                });
                shouldPredict = false;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                SwingUtilities.invokeLater(() -> {
                    editableTextArea.replaceSelection("\n");
                });
                shouldPredict = false;
            }
        });
        textFields.add(nonEditableTextArea);
        setup(nonEditableTextArea);
        nonEditableTextArea.setEditable(false);
        nonEditableTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isActive) return;
                addTranslationForWord();
            }
        });
        setLanguage(Dictionary.Language.ENGLISH, SetLanguageMode.RESET);
        translateNothing();
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

    private void removePlaceholderIfNeeded() {
        if (!isShowingPlaceHolder) return;
        editableTextArea.setText("");
        translateNothing();
        editableTextArea.setForeground(Color.black);
        editableTextArea.removeMouseListener(removePlaceholderListener);
    }

    private void setLanguage(
            Dictionary.Language newLanguage,
            SetLanguageMode mode
    ) {
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
        if (isShowingPlaceHolder || null == text || text.isEmpty())
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
        if (shouldPredict) {
            String prefix = getText();
            String currentPrediction = dictionary.getWithPrefix(from, prefix);
            if (failedToInsertPrediction(currentPrediction)) {
                if (isAutoDetecting) {
                    String anotherPrediction = dictionary.getWithPrefix(from.translateTo(), prefix);
                    if (failedToInsertPrediction(anotherPrediction)) {
                        displayNoTranslation();
                    }
                } else {
                    displayNoTranslation();
                }
            }
        } else {
            displayNoTranslation();
        }
    }

    private boolean failedToInsertPrediction(String predicted) {
        try {
            String prefix = getText();
            if (!predicted.startsWith(prefix)) return false;
            int len = prefix.length();
            String toInsert = predicted.substring(len);
            editableTextArea.insert(toInsert, len);
            editableTextArea.setCaretPosition(predicted.length());
            editableTextArea.moveCaretPosition(len);
            return false;
        } catch (Throwable ignored) {
            return true;
        }

    }

    private void displayNoTranslation() {
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
            StringSelection toCopy = new StringSelection(string);
            Clipboard board = Toolkit.getDefaultToolkit().getSystemClipboard();
            board.setContents(toCopy, toCopy);
        } catch (Throwable ignored) {
            nonEditableTextArea.setText(ignored.getLocalizedMessage());
        }
    }

    private void setup(JTextArea textArea) {
        textArea.setLineWrap(true);
        textArea.setFont(textArea.getFont().deriveFont(32f));
        textArea.setWrapStyleWord(true);
    }

    private void addTranslationForWord() {
        // TODO: Add word
    }

    enum SetLanguageMode {RESET, AUTO, MANUAL}
}
