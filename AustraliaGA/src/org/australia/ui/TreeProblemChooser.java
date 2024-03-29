/*
 * TreeProblemChooser.java
 *
 * Created on 5. Juli 2007, 00:32
 */

package org.australia.ui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import org.australia.problem.Problem;
import org.australia.problem.ProblemBoccia;
import org.australia.problem.ProblemHolmberg;
import org.australia.util.Text;

/**
 *
 * @author  Daniel_h4x
 */
public class TreeProblemChooser extends javax.swing.JDialog {
    /** A return status code - returned if Cancel button has been pressed */
    public static final int RET_CANCEL = 0;
    /** A return status code - returned if OK button has been pressed */
    public static final int RET_OK = 1;
    
    private JTextField retValField;
    private String chosenProblem;
    
    /** Creates new form TreeProblemChooser
     * @param parent
     * @param modal
     * @param field
     */
    public TreeProblemChooser(java.awt.Frame parent, boolean modal, JTextField field) {
        super(parent, modal);
        initComponents();
        pack();
        Rectangle parentBounds = parent.getBounds();
        Dimension size = getSize();
        // Center in the parent
        int x = Math.max(0, parentBounds.x + (parentBounds.width - size.width) / 2);
        int y = Math.max(0, parentBounds.y + (parentBounds.height - size.height) / 2);
        setLocation(new Point(x, y));
        this.retValField = field;
    }
    
    /** @return the return status of this dialog - one of RET_OK or RET_CANCEL */
    public int getReturnStatus() {
        return returnStatus;
    }
    
    private DefaultTreeCellRenderer  getCellRenderer(){
        
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        
        ImageIcon icon = new ImageIcon("src/org/australia/ui/img/Leaf.png");
        
        renderer.setLeafIcon(icon);
        
        return renderer;
    }
    
    
    private TreeModel  getCustomTreeModel(){
        
        
        
        DefaultMutableTreeNode  root = new DefaultMutableTreeNode("Probleminstanzen");
        
        DefaultMutableTreeNode  holmberg = new DefaultMutableTreeNode("Holmberg-Instanzen");
        DefaultMutableTreeNode  boccia = new DefaultMutableTreeNode("Boccia-Instanzen");
        
        root.add(holmberg);
        root.add(boccia);
        
        DefaultMutableTreeNode  holmberg15 = new DefaultMutableTreeNode(" 1-15");
        DefaultMutableTreeNode  holmberg30 = new DefaultMutableTreeNode("16-30");
        DefaultMutableTreeNode  holmberg45 = new DefaultMutableTreeNode("31-45");
        DefaultMutableTreeNode  holmberg60 = new DefaultMutableTreeNode("46-60");
        DefaultMutableTreeNode  holmberg71 = new DefaultMutableTreeNode("61-71");
        
        holmberg.add(holmberg15);
        holmberg.add(holmberg30);
        holmberg.add(holmberg45);
        holmberg.add(holmberg60);
        holmberg.add(holmberg71);
        
        DefaultMutableTreeNode  boccia5050 = new DefaultMutableTreeNode("i5050");
        DefaultMutableTreeNode  boccia5075 = new DefaultMutableTreeNode("i5075");
        DefaultMutableTreeNode  boccia50100 = new DefaultMutableTreeNode("i50100");
        DefaultMutableTreeNode  boccia75100 = new DefaultMutableTreeNode("i75100");
        DefaultMutableTreeNode  boccia7575 = new DefaultMutableTreeNode("i7575");
        
        boccia.add(boccia5050);
        boccia.add(boccia5075);
        boccia.add(boccia50100);
        boccia.add(boccia75100);
        boccia.add(boccia7575);
        
        
        
        //add holmberg categorized by numbers
        
        for (int i = 1; i < 16; i++) {
            String name = "P"+i;
            holmberg15.add(new DefaultMutableTreeNode(name));
        }
        
        for (int i = 16; i < 31; i++) {
            String name = "P"+i;
            holmberg30.add(new DefaultMutableTreeNode(name));
        }
        
        for (int i = 31; i < 46; i++) {
            String name = "P"+i;
            holmberg45.add(new DefaultMutableTreeNode(name));
        }
        
        for (int i = 46; i < 61; i++) {
            String name = "P"+i;
            holmberg60.add(new DefaultMutableTreeNode(name));
        }
        
        for (int i = 61; i < 72; i++) {
            String name = "P"+i;
            holmberg71.add(new DefaultMutableTreeNode(name));
        }
        
        // add boccia by name
        
        for (int j = 1; j <= 15; j++) {
            boccia5050.add(new DefaultMutableTreeNode("i5050_"+j+".plc"));
        }
        
        for (int j = 1; j <= 15; j++) {
            boccia5075.add(new DefaultMutableTreeNode("i5075_"+j+".plc"));
        }
        
        for (int j = 1; j <= 15; j++) {
            boccia50100.add(new DefaultMutableTreeNode("i50100_"+j+".plc"));
        }
        
        for (int j = 1; j <= 15; j++) {
            boccia75100.add(new DefaultMutableTreeNode("i75100_"+j+".plc"));
        }
        
        for (int j = 1; j <= 15; j++) {
            boccia7575.add(new DefaultMutableTreeNode("i7575_"+j+".plc"));
        }
        
        
        return new DefaultTreeModel(root);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jLabel1 = new javax.swing.JLabel();
        LagerField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        KundenField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Abbrechen");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jTree1.setCellRenderer(getCellRenderer());
        jTree1.setModel(getCustomTreeModel());
        jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTree1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jTree1);

        jLabel1.setText("Anzahl Lager");

        LagerField.setEnabled(false);

        jLabel2.setText("Anzahl Kunden");

        KundenField.setEnabled(false);

        jLabel3.setText("Bitte w\u00e4hlen Sie eine Probleminstanz:");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel3)
                    .add(layout.createSequentialGroup()
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 236, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(okButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 67, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(cancelButton))
                            .add(layout.createSequentialGroup()
                                .add(10, 10, 10)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(jLabel2)
                                    .add(jLabel1))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(KundenField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                                    .add(LagerField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))))))
                .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[] {cancelButton, okButton}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel3)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(41, 41, 41)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel1)
                            .add(LagerField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel2)
                            .add(KundenField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(226, 226, 226)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(cancelButton)
                            .add(okButton)))
                    .add(layout.createSequentialGroup()
                        .add(13, 13, 13)
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
private void jTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTree1ValueChanged
    DefaultMutableTreeNode node = (DefaultMutableTreeNode)
            jTree1.getLastSelectedPathComponent();
    
    if (node == null) return;
    
    String instance = (String)node.getUserObject();
    Problem problem;
    if (node.isLeaf()) {
        if(instance.startsWith("P")){
            problem = ProblemHolmberg.readProblem(instance);
        } else{
            problem = ProblemBoccia.readProblem(instance);
        }

        
        LagerField.setText("" + problem.getWarehouses());
        KundenField.setText(""+ problem.getCustomers());
    }
    
    
}//GEN-LAST:event_jTree1ValueChanged

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        
        DefaultMutableTreeNode selected = (DefaultMutableTreeNode)this.jTree1.getLastSelectedPathComponent();
        
        if(selected != null){
            this.retValField.setText((String) selected.getUserObject());
        } else{
            this.retValField.setText(Text.NO_INSTANCE);
        }
        
        doClose(RET_OK);
    }//GEN-LAST:event_okButtonActionPerformed
    
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.retValField.setText(Text.NO_INSTANCE);
        doClose(RET_CANCEL);
    }//GEN-LAST:event_cancelButtonActionPerformed
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        this.retValField.setText(Text.NO_INSTANCE);
        doClose(RET_CANCEL);
    }//GEN-LAST:event_closeDialog
    
    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TreeProblemChooser dialog = new TreeProblemChooser(new javax.swing.JFrame(), true, new JTextField());
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField KundenField;
    private javax.swing.JTextField LagerField;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTree jTree1;
    private javax.swing.JButton okButton;
    // End of variables declaration//GEN-END:variables
    
    private int returnStatus = RET_CANCEL;
}
