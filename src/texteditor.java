import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class texteditor extends JFrame implements ActionListener {
    JTextArea textArea;
    JScrollPane scene;
    JLabel fontlabel;

    JSpinner font_size_spinner;

    JComboBox fontstylebox;

    JMenuBar menubar;

    JMenu file,edit,themes;
    JMenuItem save,cut,copy,paste,selectAll;
    JMenuItem exit,darkTheme,LightTheme;

    JButton fontColor;


    texteditor(){
        this.setTitle("Text Editor");
        this.setSize(1000,700);
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content= this.getContentPane();
        content.setBackground(new Color(68,158,122));

        textArea=new JTextArea();
        textArea.setFont(new Font("Arial",Font.PLAIN,25));
        scene= new JScrollPane(textArea);
        scene.setPreferredSize(new Dimension(900,500));
        scene.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        fontlabel =new JLabel("Font: ");

        font_size_spinner=new JSpinner();
        font_size_spinner.setPreferredSize(new Dimension(40,30));
        font_size_spinner.setValue(20);
        font_size_spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,(int) font_size_spinner.getValue()));
            }
        });

        String[] fonts=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontstylebox=new JComboBox(fonts);
        fontstylebox.setSelectedItem("Arial");

        fontstylebox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setFont(new Font((String) fontstylebox.getSelectedItem(),Font.PLAIN,(int) font_size_spinner.getValue()));
            }
        });
        fontColor = new JButton("Color");
        fontColor.addActionListener(this);

        menubar = new JMenuBar();
        file = new JMenu("File");
        save = new JMenuItem("Save");
        exit= new JMenuItem("Exit");

        themes= new JMenu("Theme");
        darkTheme=new JMenuItem("dark");
        LightTheme=new JMenuItem("light");

        edit=new JMenu("Edit");
        cut= new JMenuItem("Cut");
        copy=new JMenuItem("Copy");
        paste=new JMenuItem("Paste");
        selectAll=new JMenuItem("Select all");

        save.addActionListener(this);
        exit.addActionListener(this);

        darkTheme.addActionListener(this);
        LightTheme.addActionListener(this);

        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);

        file.add(save);
        file.add(exit);

        themes.add(darkTheme);
        themes.add(LightTheme);

        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(selectAll);

        menubar.add(file);
        menubar.add(themes);
        menubar.add(edit);

        this.setJMenuBar(menubar);
        this.add(fontlabel);
        this.add(font_size_spinner);
        this.add(fontstylebox);
        this.add(fontColor);
        this.add(scene);



        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==fontColor){
            JColorChooser colorChooser = new JColorChooser();
            Color color = colorChooser.showDialog(null, "Choose a color", Color.black);
            textArea.setForeground(color);
        }
        if(e.getSource()==copy){
            textArea.copy();
        }
        if(e.getSource()==cut){
            textArea.cut();
        }
        if(e.getSource()==paste){
            textArea.paste();
        }
        if(e.getSource()==selectAll){
            textArea.selectAll();
        }
        if(e.getSource()==darkTheme){

            textArea.setBackground(Color.DARK_GRAY);
            textArea.setForeground(Color.WHITE);

        }
        if(e.getSource()==LightTheme){
            textArea.setBackground(Color.WHITE);
            textArea.setForeground(Color.BLACK);
        }
        if(e.getSource()==exit){

            System.exit(99);
        }

        if(e.getSource()==save){
            JFileChooser fileChooser= new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));

            int response_of_user =fileChooser.showSaveDialog(null);

            if(response_of_user == JFileChooser.CANCEL_OPTION){
                ;
            }
            else{
                System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
                File file=new File(fileChooser.getSelectedFile().getAbsolutePath());
                try{
                    PrintWriter writing_content =new PrintWriter(file);
                    writing_content.println(textArea.getText());
                    writing_content.close();
                }catch (FileNotFoundException ex){
                    throw new RuntimeException(ex);
                }
            }

        }
    }


}
