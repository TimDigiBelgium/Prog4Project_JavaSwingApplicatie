/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package digilabelformatsjava;

import BusinessLogic.GenerateFormatCode;
import DAL.DATemperatureFields;
import DAL.DAdevice;
import DAL.DAFields;
import DAL.DAlabelformat;
import DAL.DAsize;
import entities.Device;
import entities.Fields;
import entities.LabelFormat;
import entities.Size;
import entities.TemperatureFields;
import java.awt.Image;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.SpinnerListModel;

/**
 *
 * @author Media Markt
 */
public class AddArticleFormatDialog extends javax.swing.JDialog {

    private Boolean OkCancel;
    private Boolean toConstruct;
    private List<Device> devicesList;
     private List<String> restDoelFormaten;
    private List<Size> sizesList;
    private LabelFormat selectedFormat;
    /**
     * Creates new form AddArticleFormatDialog
     * 
     * 
     */
    public AddArticleFormatDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
            lstDevice.setModel(FillDevices());
        lstSize.setModel(FillSizes());
        lstFields.setModel(FillFields());
        lstTemperatureFields.setModel(FillTemperatureFields());
        //this.numDoelFormaten.setModel(FillRestDoelFormaten());
        
        
        lstDevice.setSelectedIndex(0);
        lstSize.setSelectedIndex(0);
        //lstFields.setSelectedIndex(0);
        findFormat();
        
        btnOK.setEnabled(false);
        this.btnToCreate.setEnabled(true);
        
        this.OkCancel = false;
        this.toConstruct = false;
        
        //volgende code zorgt ervoor dat de ctrl toets niet moet worden ingedrukt
        //bij de multiselect listbox 'lstFields'
        lstFields.setSelectionModel(new DefaultListSelectionModel() {
    public void setSelectionInterval(int index0, int index1) {
        if (isSelectedIndex(index0))
            super.removeSelectionInterval(index0, index1);
        else
            super.addSelectionInterval(index0, index1);
    }
});
        
        lstTemperatureFields.setSelectionModel(new DefaultListSelectionModel() {
        private static final long serialVersionUID = 1L;

        boolean gestureStarted = false;

        @Override
        public void setSelectionInterval(int index0, int index1) {
            if(!gestureStarted){
            if (index0==index1) {
                if (isSelectedIndex(index0)) {
                    removeSelectionInterval(index0, index0);
                    return;
                }
            }
            super.setSelectionInterval(index0, index1);
            }
            gestureStarted = true;
        }

        @Override
        public void addSelectionInterval(int index0, int index1) {
            if (index0==index1) {
                if (isSelectedIndex(index0)) {
                    removeSelectionInterval(index0, index0);
                    return;
                }
            super.addSelectionInterval(index0, index1);
            }
        }

        @Override
        public void setValueIsAdjusting(boolean isAdjusting) {
            if (isAdjusting == false) {
                gestureStarted = false;
            }
        }

    });
        this.setLocationRelativeTo(null);
        
        
    }

//    AddArticleFormatDialog() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    
     public void DisplayImage()
    {
        byte[] imageInByte;
        imageInByte = getSelectedFormat().getImage().getImageFile();
        
       // InputStream in = new ByteArrayInputStream(imageInByte);
        //BufferedImage image = ImageIO.read(in);
        
        int width;
        int height;
        
        width = getSelectedFormat().getLabelFormatSize().getWidth();
        height = getSelectedFormat().getLabelFormatSize().getHeight();
        
        width = width * 3;
        height = height * 3;
        
        ImageIcon imageIcon = new ImageIcon(imageInByte); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it 
        Image newimg = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        imageIcon = new ImageIcon(newimg);  // transform it back
        
        lblImage.setIcon(imageIcon);
        
        //JLabel label = new JLabel(new ImageIcon(bytes));

        
        
    }
        public DefaultListModel FillDevices()
    {
        DAdevice dd = new DAdevice();
        DefaultListModel listModel;
        listModel = new DefaultListModel();
        this.devicesList = dd.findAllDevices();
        
        this.devicesList.stream().forEach((a1) -> {
            listModel.addElement(a1.toString() + " \n");
        });
        return listModel;
    }
              public DefaultListModel FillTemperatureFields()
    {
        DATemperatureFields dd = new DATemperatureFields();
        DefaultListModel listModel;
        listModel = new DefaultListModel();
        List<TemperatureFields> tf = dd.findAllTemperatureFields();
        
        tf.stream().forEach((a1) -> {
            listModel.addElement(a1.toString() + " \n");
        });
        return listModel;
    }
    
     
       public LabelFormat LoadLabelFormat()
    {
        DAlabelformat dl= new DAlabelformat();
         
        LabelFormat lf = dl.findBySelection(devicesList.get(lstDevice.getSelectedIndex()), sizesList.get(lstSize.getSelectedIndex()), lblFormatCode.getText());

        return lf;
    }
    
           public LabelFormat LoadToContructLabelFormat()
    {
        DAlabelformat dl= new DAlabelformat();
         
        LabelFormat lf = dl.findBySelection(devicesList.get(0), sizesList.get(2), "In");

        return lf;
    }
       
     
       public DefaultListModel FillSizes()
    {
        DAsize dd = new DAsize();
        DefaultListModel listModel;
        listModel = new DefaultListModel();
        this.sizesList = dd.findAllSizes();
        
        this.sizesList.stream().forEach((a1) -> {
            listModel.addElement(a1.toString() + " \n");
        });
        return listModel;
    }
     
     
        public DefaultListModel FillFields()
    {
        DAFields dd = new DAFields();
        DefaultListModel listModel;
        listModel = new DefaultListModel();
        List<Fields> a = dd.findAllFields();
        
        a.stream().forEach((a1) -> {
            listModel.addElement(a1.toString() + " \n");
        });
        return listModel;
    }
        
            public SpinnerListModel FillRestDoelFormaten()
    {
//        DAfields dd = new DAfields();
         SpinnerListModel listModel;
         listModel = new SpinnerListModel(this.restDoelFormaten);
//        List<Fields> a = dd.findAllFields();
        
      
        return listModel;
    }
        
        
     
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Device1 = new javax.swing.JScrollPane();
        lstFields = new javax.swing.JList();
        lblSize = new javax.swing.JLabel();
        lblFields = new javax.swing.JLabel();
        lblDevice = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstSize = new javax.swing.JList();
        lblImage = new javax.swing.JLabel();
        lblFormatCode = new javax.swing.JLabel();
        btnOK = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstDevice = new javax.swing.JList();
        numDoelFormaten = new javax.swing.JSpinner();
        btnCancel = new javax.swing.JButton();
        lblFormatNumber = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        lstTemperatureFields = new javax.swing.JList();
        btnToCreate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lstFields.setSelectedIndex(1);
        lstFields.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstFieldsMouseClicked(evt);
            }
        });
        Device1.setViewportView(lstFields);

        lblSize.setText("Select Size :");

        lblFields.setText("Select Fields :");

        lblDevice.setText("Select device");

        lstSize.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstSize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstSizeMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(lstSize);

        btnOK.setLabel("OK");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        lstDevice.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstDevice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstDeviceMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(lstDevice);

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        lblFormatNumber.setText("Select Format # in database :");

        lstTemperatureFields.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        lstTemperatureFields.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstTemperatureFieldsMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(lstTemperatureFields);

        btnToCreate.setLabel("To Create");
        btnToCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToCreateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblFormatNumber)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(numDoelFormaten, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnToCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblDevice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblFields, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(68, 68, 68)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Device1)
                            .addComponent(jScrollPane3))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblFormatCode)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblSize, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                    .addGap(415, 415, 415)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFormatCode)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblDevice, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblFields, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(Device1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(47, 47, 47)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(numDoelFormaten, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFormatNumber)
                            .addComponent(btnToCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(91, 91, 91)
                    .addComponent(lblSize, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(403, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lstFieldsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstFieldsMouseClicked
        findFormat();
    }
    private void findFormat()
    {
        if(this.lstFields.getSelectedIndex()!=-1){
        GenerateFormatCode l = new GenerateFormatCode(lstFields.getSelectedIndices(),lstTemperatureFields.getSelectedIndex());
        lblFormatCode.setText(l.GetlabelFormatCode());
        this.selectedFormat = LoadLabelFormat();
        
        if (getSelectedFormat()!=null) {
        DisplayImage();    
          btnOK.setEnabled(true);
          this.btnToCreate.setEnabled(false);
          this.selectedFormat.setSummary(l.GetlabelFormatSummary());
        }
        else
        {
            lblImage.setIcon(null);
              btnOK.setEnabled(false);
              this.btnToCreate.setEnabled(true);
              this.selectedFormat = this.LoadToContructLabelFormat();
              this.selectedFormat.setLabelFormatCode(this.lblFormatCode.getText() + "_To Create !!!");
              this.selectedFormat.setSummary(l.GetlabelFormatSummary());
        }
        
        }
    }//GEN-LAST:event_lstFieldsMouseClicked

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.toConstruct = false;
        this.OkCancel = false;
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed
        this.toConstruct = false;
        this.OkCancel = true;
        this.selectedFormat.setFormatNumber(this.numDoelFormaten.getValue().toString());
        // TODO add your handling code here:
        this.dispose();    
    }//GEN-LAST:event_btnOKActionPerformed

    private void lstSizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstSizeMouseClicked
        findFormat();
    }//GEN-LAST:event_lstSizeMouseClicked

    private void lstDeviceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstDeviceMouseClicked
       findFormat();
    }//GEN-LAST:event_lstDeviceMouseClicked

    private void lstTemperatureFieldsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstTemperatureFieldsMouseClicked
       findFormat();
    }//GEN-LAST:event_lstTemperatureFieldsMouseClicked

    private void btnToCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToCreateActionPerformed
        this.toConstruct = true;
        this.OkCancel = true;
        this.selectedFormat.setFormatNumber(this.numDoelFormaten.getValue().toString());
        // TODO add your handling code here:
        this.dispose();            // TODO add your handling code here:
    }//GEN-LAST:event_btnToCreateActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddArticleFormatDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddArticleFormatDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddArticleFormatDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddArticleFormatDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AddArticleFormatDialog dialog = new AddArticleFormatDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane Device1;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOK;
    private javax.swing.JButton btnToCreate;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblDevice;
    private javax.swing.JLabel lblFields;
    private javax.swing.JLabel lblFormatCode;
    private javax.swing.JLabel lblFormatNumber;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblSize;
    private javax.swing.JList lstDevice;
    private javax.swing.JList lstFields;
    private javax.swing.JList lstSize;
    private javax.swing.JList lstTemperatureFields;
    private javax.swing.JSpinner numDoelFormaten;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the selectedFormat
     */
    public LabelFormat getSelectedFormat() {
        return selectedFormat;
    }

    /**
     * @param restDoelFormaten the restDoelFormaten to set
     */
    public void setRestDoelFormaten(List<String> restDoelFormaten) {
        this.restDoelFormaten = restDoelFormaten;
        this.numDoelFormaten.setModel(FillRestDoelFormaten());
    }

    /**
     * @return the OkCancel
     */
    public Boolean getOkCancel() {
        return OkCancel;
    }

    /**
     * @return the toConstruct
     */
    public Boolean getToConstruct() {
        return toConstruct;
    }

    /**
     * @param doelFormaten the doelFormaten to set
     */
  

    /**
     * @param formatnumbers the formatnumbers to set
     */
   
}
