/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject3;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import javax.swing.undo.UndoManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Abdur Raafee
 */
public class TestEditor {
    TestEditor(){
        JFrame frame=new JFrame();
        File imgFile = new File("C:\\TestFiles\\SampleImage/DoJava.png");
        ImageIcon icon = createImageIcon(imgFile.toString(),"DoJava");
        frame.setTitle("Lightweight Java IDE");
        //frame.setIconImage(icon.getImage());
        JPanel panel=new JPanel();
        JPanel panel2=new JPanel();
        JPanel panel3=new JPanel();
        JPanel panel0=new JPanel();
        
        Font fnt=new Font("Open Sans", Font.PLAIN, 12);
        Font fnt2=new Font("Open Sans", Font.PLAIN, 16);
        
        JTextArea text1=new JTextArea(20,50);
        
        text1.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println(e.getClass());
                if(e.getKeyChar()=='\n'){
                String sourceString=text1.getText();
                String formattedSource = null;
                boolean go=true;
                    try {
                        formattedSource = new Formatter().formatSource(sourceString);
                    } catch (FormatterException ex) {
                        //System.out.println(ex.toString());
                        go=false;
                        System.out.println("Exception found!");
                    }
                    finally{
                        if(go==true){
                        System.out.println(formattedSource);
                        text1.setText("");
                        text1.append(formattedSource);
                        }
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                 
            }
        });
        JTextArea text2=new JTextArea(10,40);
        JTextArea text3=new JTextArea(10,40);
        
        JButton button1=new JButton("Format");
        button1.addActionListener((e) -> {
            if(e.getActionCommand().equalsIgnoreCase("Format")){
            String sourceString=text1.getText();
                String formattedSource = null;
                boolean go=true;
                    try {
                        formattedSource = new Formatter().formatSource(sourceString);
                    } catch (FormatterException ex) {
                        //System.out.println(ex.toString());
                        go=false;
                        System.out.println("Exception found!");
                    }
                    finally{
                        if(go==true){
                        System.out.println(formattedSource);
                        text1.setText("");
                        text1.append(formattedSource);
                        }
                    }
            }
        });
        JButton button2=new JButton("Compile");
        button2.addActionListener((e) -> {
            File file = new File("C:\\TestFiles\\SampleText/input.java");
            try {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write(text1.getText());
                try {
                Runtime.getRuntime().exec("cmd /c start C:\\TestFiles\\SampleText\\build.bat");
                } catch (IOException ex) {
                System.out.println(ex.toString());
            }   
                }
            } catch (FileNotFoundException ex) {
                System.out.println(ex.toString());
            } catch (IOException ex) {
                System.out.println(ex.toString());
            }
            
        });
        JButton button3=new JButton("Run");
        button3.addActionListener((f)->{
            String[] command =
            {
                "cmd",
            };
            Process p;
            try {
                p = Runtime.getRuntime().exec(command);
              new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
              new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
              PrintWriter stdin = new PrintWriter(p.getOutputStream());
              //stdin.println("dir c:\\");
              stdin.println("cd C:\\TestFiles\\SampleText");
              stdin.println("javac input.java");
              stdin.println("java input");
              
              stdin.close();
                int returnCode;
                try {
                    returnCode = p.waitFor();
                    System.out.println("Return code = " + returnCode);
                } catch (InterruptedException ex) {
                    System.out.println(ex.toString());
                }
                } catch (IOException ex) {
                    System.out.println(ex.toString());
                }
        });
        JButton button4=new JButton("Copy");
        button4.addActionListener((g)->{
            text1.copy();
        });
        JButton button5=new JButton("Paste");
        button5.addActionListener((h)->{
            text1.paste();
        });
        JButton button6=new JButton("Cut");
        button6.addActionListener((i)->{
            text1.cut();
        });
        JButton button7=new JButton("Find");
        button7.addActionListener((ActionEvent j) -> {
            String word;
            word=JOptionPane.showInputDialog(panel0,"Word: ","Find",JOptionPane.QUESTION_MESSAGE);
            //System.out.println(word);
            
            File file = new File("C:\\TestFiles\\SampleText/input.java");
            String str="";
            try{
                BufferedReader reader = new BufferedReader(new FileReader(file));
                Scanner sc = new Scanner(reader);
                while(sc.hasNextLine()){
                        str=str+sc.nextLine();
                        str=str+"\n";
                }
                //System.out.println(str);
                ArrayList<Integer> lst=new ArrayList<>();
                int fromIndex=0;
                while(true){
                    //System.out.println(fromIndex);
                    int curr=str.indexOf(word, fromIndex);
                    if(curr!=-1) lst.add(curr);
                    else break;
                    fromIndex=curr+1;
                }
              System.out.println(word);
              String str2="";
              for(int i=0;i<lst.size();i++) {
                  System.out.println(lst.get(i));
                  str2=str2+lst.get(i)+"\n";
              }
              JOptionPane.showMessageDialog(panel0, str2,"Result",JOptionPane.PLAIN_MESSAGE);
            }
            catch (Exception ex){
                System.out.println(ex.toString());
            }
        });
        JButton button10=new JButton("Replace");
        button10.addActionListener((ActionEvent j) -> {
            CharSequence word1;
            CharSequence word2;
            word1=JOptionPane.showInputDialog(panel0,"Target word: ","Replace",JOptionPane.QUESTION_MESSAGE);
            word2=JOptionPane.showInputDialog(panel0,"Replace with: ","Replace",JOptionPane.QUESTION_MESSAGE);
            //System.out.println(word1+" "+word2);
            File file = new File("C:\\TestFiles\\SampleText/input.java");
            String str="";
            try{
                BufferedReader reader = new BufferedReader(new FileReader(file));
                Scanner sc = new Scanner(reader);
                while(sc.hasNextLine()){
                        str=str+sc.nextLine();
                        str=str+"\n";
                }
                reader.close();
                System.out.println(str);
                String modstr=str.replace(word1,word2);
                System.out.println(modstr);
                //BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                //writer.write(modstr);
                //writer.close();
                text1.setText(modstr);
            }
            catch (Exception ex){
                System.out.println(ex.toString());
            }
        });
        UndoManager manager = new UndoManager();
        text1.getDocument().addUndoableEditListener(manager);
        
        JButton button8 = new JButton("Undo");
        button8.addActionListener((ActionEvent k) -> {
            manager.undo();
        });
        JButton button9= new JButton("Redo");
        button9.addActionListener((ActionEvent l) -> {
            manager.redo();
        });
        JButton button11= new JButton("System Info");
        button11.addActionListener((ActionEvent m) -> {
            String osname=System.getProperty("os.name");
            String osarch=System.getProperty("os.arch");
            String osversion=System.getProperty("os.version");
            String javaversion=System.getProperty("java.version");
            String javavendor=System.getProperty("java.vendor");
            String msg="JRE "+javaversion+"\n"+"Vendor: "+javavendor+"\nOS: "+osname+" version "+osversion+"\n Architecture: "+osarch+"\n";
            JOptionPane.showMessageDialog(panel,msg,"System Info", 1);
        });
        
        JScrollPane scrl1 = new JScrollPane(text1);
        JScrollPane scrl2 = new JScrollPane(text2);
        JScrollPane scrl3 = new JScrollPane(text3);
        
        panel2.add(button1); button1.setFont(fnt);
        panel2.add(button2); button2.setFont(fnt);
        panel2.add(button3); button3.setFont(fnt);
        panel2.add(button11); button11.setFont(fnt); 
        
        panel0.add(button4); button4.setFont(fnt);
        panel0.add(button5); button5.setFont(fnt);
        panel0.add(button6); button6.setFont(fnt);
        panel0.add(button8); button8.setFont(fnt);
        panel0.add(button9); button9.setFont(fnt);
        panel0.add(button7); button7.setFont(fnt);
        panel0.add(button10); button10.setFont(fnt);
        
        panel.add(scrl1); text1.setFont(fnt2);
        panel3.add(scrl2); text2.setFont(fnt);
        panel3.add(scrl3); text3.setFont(fnt);
        
        panel0.setLayout(new BoxLayout(panel0, BoxLayout.PAGE_AXIS));
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.PAGE_AXIS));
        
        frame.add(panel0);
        frame.add(panel);
        frame.add(panel2);
        frame.add(panel3);
        FlowLayout lyt=new FlowLayout(FlowLayout.CENTER);
        frame.setLayout(lyt);
        frame.setSize(1170,730);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        TestEditor test=new TestEditor();
        //System.out.println(System.getProperty("user.dir"));
    }

    private ImageIcon createImageIcon(String path,String description) {
         java.net.URL imgURL = getClass().getResource(path);
    if (imgURL != null) {
        return new ImageIcon(imgURL, description);
    } else {
        System.err.println("Couldn't find file: " + path);
        return null;
    }
    }
}
