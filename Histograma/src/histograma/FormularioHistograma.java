/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package histograma;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
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
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

/**
 *
 * @author ramos
 */
public class FormularioHistograma extends JFrame {

    public FormularioHistograma() {
        setSize(1010, 480);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Histogramas");
        setLocationRelativeTo(null);
        add(new Panel());
    }

    final class Panel extends JPanel {

        private final JPanel panelCenter = new JPanel();
        private final JPanel panelSouth = new JPanel();
        private final JButton buttonChoose = new JButton("Seleccionar imagen"), buttonDrawNormal = new JButton("Dibujar histogramas");
        private final JLabel labelImage = new JLabel(), labelPathImage = new JLabel("", SwingConstants.CENTER);
        private final JScrollPane panelImage = new JScrollPane();
        private final JPanel panelRed = new JPanel();
        private final JPanel panelGreen = new JPanel();
        private final JPanel panelBlue = new JPanel();
        private final JPanel panelGray = new JPanel();
        private final JButton buttonForkJoin = new JButton("Fork/Join");
        private final JButton buttonExecutorServices = new JButton("Executor Services");
        private final JLabel labelSecuential = new JLabel("Secuencial: 0.0 ms", SwingConstants.CENTER);
        private final JLabel labelForkJoin = new JLabel("Fork/Join: 0.0 ms", SwingConstants.CENTER);
        private final JLabel labelExecutorServices = new JLabel("Executor Services: 0.0 ms", SwingConstants.CENTER);

        Panel() {
            setBackground(Color.red);
            setLayout(new BorderLayout());
            addPanels();
            addEvents();
        }

        void addPanels() {
            // panelCenter.setBackground(Color.BLACK);
            // panelCenter.add(image);
            JPanel panelC = new JPanel(new GridLayout(1, 2)), panelL = new JPanel(new BorderLayout()), panelR = new JPanel(new GridLayout(2, 2));
            //
            //labelImage.setPreferredSize(new Dimension(300, 300));
            //labelImage.setMaximumSize(new Dimension(300, 300));
            panelImage.setPreferredSize(new Dimension(300, 300));
            panelImage.setMaximumSize(new Dimension(300, 300));
            // panelImage.setSize(100, 100);
            panelImage.setViewportView(labelImage);
            panelL.add(panelImage, BorderLayout.CENTER);
            JPanel panelLSouth = new JPanel(new GridLayout(3, 1));
            panelLSouth.add(buttonChoose);
            panelLSouth.add(buttonDrawNormal);
            panelLSouth.add(labelPathImage);
            panelL.add(panelLSouth, BorderLayout.SOUTH);

            // panel rigth
            panelR.setPreferredSize(new Dimension(500, 160));
            panelR.setMaximumSize(new Dimension(500, 160));

            panelRed.setPreferredSize(new Dimension(150, 80));
            panelRed.setMaximumSize(new Dimension(150, 80));
            panelR.add(panelRed);
            panelGreen.setPreferredSize(new Dimension(150, 80));
            panelGreen.setMaximumSize(new Dimension(150, 80));
            panelR.add(panelGreen);
            panelBlue.setPreferredSize(new Dimension(150, 80));
            panelBlue.setMaximumSize(new Dimension(150, 80));
            panelR.add(panelBlue);
            panelGray.setPreferredSize(new Dimension(150, 80));
            panelGray.setMaximumSize(new Dimension(150, 80));
            panelR.add(panelGray);

            panelC.add(panelL);
            panelC.add(panelR);
            panelC.setBackground(Color.red);
            panelCenter.add(panelC);
            add(panelCenter, BorderLayout.CENTER);

            panelSouth.setLayout(new GridLayout(2, 3));
            // panelSouth.setBackground(Color.red);
            panelSouth.add(buttonDrawNormal);
            panelSouth.add(buttonForkJoin);
            panelSouth.add(buttonExecutorServices);
            panelSouth.add(labelSecuential);
            panelSouth.add(labelForkJoin);
            panelSouth.add(labelExecutorServices);

            add(panelSouth, BorderLayout.SOUTH);
        }

        void addEvents() {
            buttonChoose.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // image.setIcon(new ImageIcon("/Users/armando/foto3.jpg"));
                    JFileChooser fileChooser = new JFileChooser();
                    if (fileChooser.showOpenDialog(labelImage) == JFileChooser.APPROVE_OPTION) {
                        // BufferedImage img = ImageIO.read(fileChooser.getSelectedFile());
                        labelImage.setIcon(new ImageIcon(fileChooser.getSelectedFile().toString()));
                        labelPathImage.setText(fileChooser.getSelectedFile().toString());
                        // System.out.println(fileChooser.getSelectedFile());
                    }
                }
            });
            buttonDrawNormal.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Histograma objHistograma = new Histograma();
                    try {
                        long startTime = System.currentTimeMillis();
                        int[][] histograma = objHistograma.histograma((BufferedImage) ImageIO.read(new File(labelPathImage.getText())));
                        DibujarGrafico objDibujarGrafico = new DibujarGrafico();
                        for (int i = 0; i < 5; i++) {
                            int[] histogramaCanal = new int[256];
                            System.arraycopy(histograma[i], 0, histogramaCanal, 0, histograma[i].length);
                            if (i == 0) {
                                objDibujarGrafico.crearHistograma(histogramaCanal, panelRed, Color.red);
                            }
                            if (i == 1) {
                                objDibujarGrafico.crearHistograma(histogramaCanal, panelGreen, Color.green);
                            }
                            if (i == 2) {
                                objDibujarGrafico.crearHistograma(histogramaCanal, panelBlue, Color.blue);
                            }
                            if (i == 4) {
                                objDibujarGrafico.crearHistograma(histogramaCanal, panelGray, Color.gray);
                            }

                        }
                        long endTime = System.currentTimeMillis();
                        labelSecuential.setText("Secuencial: " + (endTime - startTime) + " ms");

                    } catch (IOException ex) {
                        Logger.getLogger(FormularioHistograma.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
    }

    public static void main(String[] args) {
        new FormularioHistograma().setVisible(true);
    }

}
