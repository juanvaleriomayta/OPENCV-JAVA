package OpenCV;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import static org.opencv.objdetect.Objdetect.CASCADE_SCALE_IMAGE;
import org.opencv.videoio.VideoCapture;

public class Face extends javax.swing.JFrame {

    String RutaDelCascade = "C:\\Users\\Juan-Valerio\\Desktop\\Proyecto OpenCV - "
            + "JavaSE\\SC_OCV\\opencv\\haarcascades\\haarcascade_frontalface_alt2.xml";
//    String RutaDelCascade = "C:\\Users\\Juan-Valerio\\Desktop\\Proyecto OpenCV - "
//            + "JavaSE\\SC_OCV\\opencv\\haarcascades\\haarcascade_eye_tree_eyeglasses.xml";
    CascadeClassifier Cascade = new CascadeClassifier(RutaDelCascade);
    Thread hilo;

    public Face() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnIniciarC = new javax.swing.JButton();
        btnCerrarC = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 595, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 417, Short.MAX_VALUE)
        );

        btnIniciarC.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnIniciarC.setText("Iniciar Cámara");
        btnIniciarC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarCActionPerformed(evt);
            }
        });

        btnCerrarC.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnCerrarC.setText("Cerrar Cámara");
        btnCerrarC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarCActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(108, Short.MAX_VALUE)
                .addComponent(btnIniciarC)
                .addGap(77, 77, 77)
                .addComponent(btnCerrarC)
                .addGap(158, 158, 158))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnIniciarC)
                    .addComponent(btnCerrarC))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIniciarCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarCActionPerformed
        (hilo = new Thread() {
            public void run() {
                //Clase encargada de conectarse con la camara
                VideoCapture capture = new VideoCapture(0);
                MatOfRect rostros = new MatOfRect();
                MatOfByte mem = new MatOfByte();

                Mat frame = new Mat();
                Mat frame_gray = new Mat();

                Rect[] facesArray;
                Graphics g;
                BufferedImage buff = null;

                if (capture.isOpened()) {
                    JOptionPane.showMessageDialog(null, "Recopilando Imagenes");

                } else {
                    JOptionPane.showMessageDialog(null, "Error la camara no esta Recopilando Imagenes");
                }

                while (capture.read(frame)) {
                    if (frame.empty()) {
                        System.out.println("Error!, la camara no esta Recopilando Imagenes");
                        break;
                    } else {
                        try {
                            g = jPanel1.getGraphics();
                            Imgproc.cvtColor(frame, frame_gray, Imgproc.COLOR_BGR2GRAY);
                            Imgproc.equalizeHist(frame_gray, frame_gray);
                            double w = frame.width();
                            double h = frame.height();
                            Cascade.detectMultiScale(frame_gray, rostros, 1.2, 2, 0 | CASCADE_SCALE_IMAGE, new Size(30, 30), new Size(w, h));
                            facesArray = rostros.toArray();
                            System.out.println("Se Detecto un Rostro: " + facesArray.length);//CANTIDAD DE CARAS ENCONTRADAS

                            for (int i = 0; i < facesArray.length; i++) {
                                Point center = new Point((facesArray[i].x + facesArray[i].width * 0.5),
                                        (facesArray[i].y + facesArray[i].height * 0.5));
                                Imgproc.rectangle(frame,
                                        new Point(facesArray[i].x, facesArray[i].y),
                                        new Point(facesArray[i].x + facesArray[i].width, facesArray[i].y + facesArray[i].height),
                                        new Scalar(123, 213, 23, 220));
                            }
                            Imgcodecs.imencode(".bmp", frame, mem);
                            Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));
                            buff = (BufferedImage) im;
                            if (g.drawImage(buff, 0, 0, jPanel1.getWidth(), jPanel1.getHeight(), 0, 0, buff.getWidth(), buff.getHeight(), null)) {
                            }
                        } catch (Exception ex) {
                            Logger.getLogger(Face.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }).start();
    }//GEN-LAST:event_btnIniciarCActionPerformed

    private void btnCerrarCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarCActionPerformed
        hilo.stop();
        jPanel1.removeAll();
        repaint();
    }//GEN-LAST:event_btnCerrarCActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        System.load("C:\\Users\\Juan-Valerio\\Desktop\\Proyecto OpenCV - "
                + "JavaSE\\SC_OCV\\opencv\\x64\\opencv_java410.dll");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Face().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrarC;
    private javax.swing.JButton btnIniciarC;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
