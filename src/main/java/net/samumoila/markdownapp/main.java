package net.samumoila.markdownapp;

public class main {
    public static void main(String[] args) {
//        String teksti = MyFileReader.readFile("README.md");
//        System.out.println(teksti);

        TextSimple markdownteksti = new TextSimple(MyFileReader.readFile("README.md"));
        System.out.println(markdownteksti.getText());
        System.out.println(markdownteksti.getCharCount());
        System.out.println(markdownteksti.getWordCount());
        System.out.println(markdownteksti.getRowCount());

        System.exit(0);
    }
}
