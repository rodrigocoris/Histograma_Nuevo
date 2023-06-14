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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
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

public class FormularioHistograma extends JFrame {

    public FormularioHistograma() {
        setSize(1010, 480);//tamanio
        setResizable(false);//no hacer que se mueva
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Histogramas");//yitulo ventana
        setLocationRelativeTo(null);
        add(new Panel());//anadir al panel
    }

    final class Panel extends JPanel {

        private final JPanel panelCentral = new JPanel();
        private final JPanel panelSur = new JPanel();
        private final JButton botonelegir = new JButton("Seleccionar imagen"), buttonDrawNormal = new JButton("Dibujar histogramas");
        private final JLabel labelImagen = new JLabel(), labelPathImage = new JLabel("", SwingConstants.CENTER);
        private final JScrollPane panelImagen = new JScrollPane();
        private final JPanel panelRed = new JPanel();
        private final JPanel panelGreen = new JPanel();
        private final JPanel panelBlue = new JPanel();
        private final JPanel panelGray = new JPanel();
        private final JButton botonForkJoin = new JButton("Fork/Join");
        private final JButton botonLimpiar = new JButton("Limpiar");
        private final JButton botonExecutorServices = new JButton("Executor Services");
        private final JLabel labelSecuencial = new JLabel("Secuencial: 0.0 ms", SwingConstants.CENTER);
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
            panelImagen.setPreferredSize(new Dimension(300, 300));
            panelImagen.setMaximumSize(new Dimension(300, 300));
            // panelImage.setSize(100, 100);
            panelImagen.setViewportView(labelImagen);
            panelL.add(panelImagen, BorderLayout.CENTER);
            JPanel panelLSouth = new JPanel(new GridLayout(3, 1));
            panelLSouth.add(botonelegir);
            panelLSouth.add(buttonDrawNormal);
            panelLSouth.add(labelPathImage);
            panelL.add(panelLSouth, BorderLayout.SOUTH);

            // panel derecho
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
            panelCentral.add(panelC);
            add(panelCentral, BorderLayout.CENTER);

            panelSur.setLayout(new GridLayout(3, 4));
            // panelSouth.setBackground(Color.red);
            panelSur.add(buttonDrawNormal);
            panelSur.add(botonForkJoin);
            panelSur.add(botonExecutorServices);
            panelSur.add(labelSecuencial);
            panelSur.add(labelForkJoin);
            panelSur.add(labelExecutorServices);
            panelSur.add(botonLimpiar);
            //panelSouth.add(buttonLimpiar);

            add(panelSur, BorderLayout.SOUTH);
        }

        void addEvents() {
            botonelegir.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // image.setIcon(new ImageIcon("/users/ramos/foto3.jpg"));
                    JFileChooser fileChooser = new JFileChooser();
                    if (fileChooser.showOpenDialog(labelImagen) == JFileChooser.APPROVE_OPTION) {
                        // BufferedImage img = ImageIO.read(fileChooser.getSelectedFile());
                        labelImagen.setIcon(new ImageIcon(fileChooser.getSelectedFile().toString()));
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
                        labelSecuencial.setText("Secuencial: " + (endTime - startTime) + " ms");

                    } catch (IOException ex) {
                        Logger.getLogger(FormularioHistograma.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            botonForkJoin.addActionListener(new ActionListener() {
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
                        labelForkJoin.setText("Secuencial: " + (endTime - startTime) + " ms");

                    } catch (IOException ex) {
                        Logger.getLogger(FormularioHistograma.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            botonExecutorServices.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        Histograma objHistograma = new Histograma();
                        int[][] histograma = objHistograma.histograma((BufferedImage) ImageIO.read(new File(labelPathImage.getText())));
                        ExecutorService exec = Executors.newFixedThreadPool(4);
                        long startTime = System.currentTimeMillis();
                        exec.execute(new executor(histograma, panelRed, panelGreen, panelBlue, panelGray));

                        long endTime = System.currentTimeMillis();
                        labelExecutorServices.setText("Secuencial: " + (endTime - startTime) + " ms");

                    } catch (IOException ex) {
                        Logger.getLogger(FormularioHistograma.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            botonLimpiar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    labelSecuencial.setText("");
                    labelForkJoin.setText("");
                    labelExecutorServices.setText("");
                }
            });

        }
    }

    public static void main(String[] args) {
        new FormularioHistograma().setVisible(true);
    }

}
