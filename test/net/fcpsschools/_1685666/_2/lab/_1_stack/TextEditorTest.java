package net.fcpsschools._1685666._2.lab._1_stack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextEditorTest {
    private String to(String s) {
        return TextEditor.dump(TextEditor.process(s));
    }

    @Test
    public void testGivenExample() {
        assertEquals("Computer Science",
                to("AP$$-Compp-utee-r Sic--cei--ience"));
        assertEquals("Raft",
                to("Ca-noe$Ra3-fx-t"));
    }
}
