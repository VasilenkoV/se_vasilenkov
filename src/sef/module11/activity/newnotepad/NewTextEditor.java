package sef.module11.activity.newnotepad;

import java.io.*;

public class NewTextEditor implements TextEditor{
    private BufferedReader in;

    public NewTextEditor() {
        System.out.println("Open editor");
        this.in = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void saveAs(String text, String name) {
        System.out.println("File name: " + name + ".txt");
        String path = ".\\src\\sef\\module11\\activity\\newnotepad\\";
        System.out.println("Path where the file will be created: " + path);


        try {
            File file = new File(path + name);

            file.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsolutePath(), true);

            Writer output = new BufferedWriter(fw);

            output.write(text);
            output.flush();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Your file was saved");
    }

    @Override
    public String typeIn() {
        System.out.println("Enter a string or type 'END' to exit");

        StringBuilder text = new StringBuilder();

        String line = "";

        while(!line.equals("END")){
            line = requestArgs();

            text.append(line).append(System.lineSeparator());

            if (line.equals("END")) {
                System.out.println("The End");
            } else {
                System.out.println("You typed: " + line);
            }
        }

        return text.toString();
    }

    private String requestArgs() {
        String arg = "";
        try {
            arg = this.in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arg;
    }
}
