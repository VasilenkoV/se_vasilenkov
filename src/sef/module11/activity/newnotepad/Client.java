package sef.module11.activity.newnotepad;

public class Client {
    public static void main(String[] args) {
        TextEditor editor = new NewTextEditor();

        String text = editor.typeIn();

        editor.saveAs(text, args[0]);
    }
}
