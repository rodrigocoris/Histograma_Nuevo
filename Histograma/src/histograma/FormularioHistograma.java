/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package histograma;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ramos
 */
public class FormularioHistograma extends JFrame {

    public FormularioHistograma() {
        setSize(700, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Histogramas");
        setLocationRelativeTo(null);
        add(new Panel());
    }

    class Panel extends JPanel {

        private JPanel panelCenter = new JPanel(), panelSouth = new JPanel();
        private JButton buttonChoose = new JButton("Seleccionar imagen");
        private JLabel image = new JLabel();
        
        Panel() {
            setBackground(Color.red);
            setLayout(new BorderLayout());
            addPanels();
            addEvents();
        }

        void addPanels() {
            panelCenter.setBackground(Color.BLACK);
            JPanel panelC = new JPanel(new GridLayout(1, 2)), panelL = new JPanel(new BorderLayout()), panelR = new JPanel(new GridLayout(2, 2));
            //
            panelL.add(buttonChoose, BorderLayout.SOUTH);
            panelC.add(panelL);
            panelC.add(panelR);
            panelC.setBackground(Color.red);
            panelCenter.add(panelC);
            add(panelCenter, BorderLayout.CENTER);
            panelSouth.setBackground(Color.red);
            add(panelSouth, BorderLayout.SOUTH);
        }
        
        void addEvents() {
            buttonChoose.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    if (fileChooser.showOpenDialog(image) == JFileChooser.APPROVE_OPTION){
                        try {
                            System.out.println(fileChooser.getSelectedFile());
                            image.setIcon(new ImageIcon(ImageIO.read(new File(fileChooser.getSelectedFile().toString()))));
                        } catch (IOException ex) {
                            Logger.getLogger(FormularioHistograma.class.getName()).log(Level.SEVERE, null, ex);
                            
                        }
                    }
                }
            });
        }
    }

    public static void main(String[] args) {
        new FormularioHistograma().setVisible(true);
    }

}
