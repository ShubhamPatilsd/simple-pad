import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class SimplePad {
    public static File file;
    private static String filepath;
    private static String contents;
    private static JTextArea write;
    private static JFrame frame;
    private static String filestate="";






    public static void main(String[] args) {

        frame=new JFrame();

        try {
            frame.setTitle(chooseFile());
        }catch(Exception e){
            frame.setTitle("Untitled");
        }


        write = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(write);
        scrollPane.setBorder(null);
        write.setMargin(new Insets(0,5,0,5));
        write.setFont(new Font("Consolas",Font.BOLD,15));
        Color c = new Color(30,30,30);
        write.setBackground(c);
        write.setForeground(Color.white);
        write.setCaretColor(Color.white);

        try {

            Scanner reader= new Scanner(file);
            while(reader.hasNextLine()){
                write.append("\n"+reader.nextLine());
            }

        }catch(Exception e){

        }

        write.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filestate="Unsaved";
                String filename="";
                try {
                    filename = file.getName();
                }catch(Exception exception){
                     filename = "Untitled";
                }

                frame.setTitle(filename+"  -- "+filestate);

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filestate="Unsaved";
                String filename="";
                try {
                    filename = file.getName();
                }catch(Exception exception){
                    filename = "Untitled";
                }
                frame.setTitle(filename+"  -- "+filestate);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filestate="Unsaved";
                String filename="";
                try {
                    filename = file.getName();
                }catch(Exception exception){
                    filename = "Untitled";
                }

                frame.setTitle(filename+"  -- "+filestate);
            }
        });

        frame.add(scrollPane);


        MenuListener listener = new MenuListener();
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(listener);
        fileMenu.add(saveMenuItem);


        JMenuItem saveAsMenuItem = new JMenuItem("Save As");
        fileMenu.add(saveAsMenuItem);
        saveAsMenuItem.addActionListener(listener);


        frame.setJMenuBar(menuBar);






        //Change this to save the file later or a prompt asking to save file or not
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);

        URL icon = SimplePad.class.getResource("icon.png");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(icon));

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static String chooseFile(){
        UIManager.put("FileChooser.cancelButtonText","New File");
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);

        file= chooser.getSelectedFile();
        String filename = file.getName();
        filepath = file.getPath();
        return filename;

    }

public static void save(){
    UIManager.put("FileChooser.cancelButtonText","Cancel");
        try{
            BufferedWriter fileOut = new BufferedWriter(new FileWriter(file));
            write.write(fileOut);
            String filename = file.getName();
            filestate = "Saved";
            frame.setTitle(filename+"  -- "+filestate);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Could not save file. This might be due to the contents in the editor are not already a file. If so, try to save it as a file.",null,JOptionPane.PLAIN_MESSAGE);
        }
}

public static void saveAs() throws IOException {
    UIManager.put("FileChooser.cancelButtonText","Cancel");
    JFileChooser chooser = new JFileChooser();
    chooser.showSaveDialog(null);
    file = chooser.getSelectedFile();
    String filename=file.getName();
    filepath = file.getAbsolutePath();

    file.createNewFile();
    filestate = "Saved";
    frame.setTitle(filename+"  -- "+filestate);
    save();


}

}

