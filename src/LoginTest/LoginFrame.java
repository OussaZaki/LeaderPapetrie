package LoginTest;

import java.awt.HeadlessException;
import java.io.IOException;
import javax.swing.ImageIcon;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class LoginFrame extends javax.swing.JFrame {

    private String BackgroundLocation = "src\\assets\\Login\\login1.jpg";
    private String LogoLocation = "src\\assets\\LogoTitle\\logo.png";
    private String MyTitle = "Connection";
    
    public LoginFrame() throws HeadlessException, IOException {
        try {
            UIManager.setLookAndFeel("com.jgoodies.looks.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
        }
        this.getContentPane().add(new LoginPan(BackgroundLocation,this));
        
        setTitle(MyTitle);
        this.setIconImage(new ImageIcon(LogoLocation).getImage());
        this.setSize(470, 300);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setUndecorated(true);
        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        reduire = new javax.swing.JButton();
        CloseButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        reduire.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Login/close.png"))); // NOI18N
        reduire.setBorderPainted(false);
        reduire.setContentAreaFilled(false);
        reduire.setSelected(true);
        reduire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reduireActionPerformed(evt);
            }
        });

        CloseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/Login/reduire.png"))); // NOI18N
        CloseButton.setBorderPainted(false);
        CloseButton.setContentAreaFilled(false);
        CloseButton.setSelected(true);
        CloseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(308, Short.MAX_VALUE)
                .addComponent(CloseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reduire, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(reduire, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CloseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(259, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void reduireActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reduireActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_reduireActionPerformed

    private void CloseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CloseButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CloseButton;
    private javax.swing.JButton reduire;
    // End of variables declaration//GEN-END:variables
}
