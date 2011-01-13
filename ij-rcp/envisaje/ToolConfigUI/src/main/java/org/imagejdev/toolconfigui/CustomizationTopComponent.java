package org.imagejdev.toolconfigui;

import java.awt.Dimension;
import java.io.Serializable;
import java.util.Collection;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.imagejdev.imagine.spi.tools.Customizer;
import org.imagejdev.imagine.spi.tools.CustomizerProvider;
import org.imagejdev.imagine.spi.tools.Tool;
import org.imagejdev.misccomponents.DefaultSharedLayoutData;
import org.imagejdev.misccomponents.LayoutDataProvider;
import org.imagejdev.misccomponents.SharedLayoutData;

import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.windows.TopComponent;

/**
 * Top component which displays the customizer, if any, for the currently
 * selected tool.
 */
final class CustomizationTopComponent extends TopComponent implements LookupListener, SharedLayoutData {

    private static CustomizationTopComponent instance;

    public CustomizationTopComponent() {
        initComponents();
        setName(NbBundle.getMessage(CustomizationTopComponent.class,
                "CTL_CustomizationTopComponent"));
        setToolTipText(NbBundle.getMessage(CustomizationTopComponent.class,
                "HINT_CustomizationTopComponent"));

        jScrollPane1.setPreferredSize(new Dimension(20, 20));
        jScrollPane1.setBorder(BorderFactory.createEmptyBorder());
        jScrollPane1.setViewportBorder(BorderFactory.createEmptyBorder());
        setInnerComponent(null, true);
        int val = Utilities.getOperatingSystem() == Utilities.OS_MAC ? 12 : 5;
        setBorder(BorderFactory.createEmptyBorder(val, val, val, val));
    }
    private Lookup.Result res = null;

    @Override
    public void addNotify() {
        super.addNotify();
        res = Utilities.actionsGlobalContext().lookup(new Lookup.Template(Tool.class));
        res.addLookupListener(this);
        resultChanged(null);
    }

    @Override
    public void removeNotify() {
        res.removeLookupListener(this);
        super.removeNotify();
        resultChanged(null);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();

        setLayout(new java.awt.BorderLayout());

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

    }
    // </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    /**
     * Gets default instance. Don't use directly, it reserved for '.settings' file only,
     * i.e. deserialization routines, otherwise you can get non-deserialized instance.
     */
    public static synchronized CustomizationTopComponent getDefault() {
        if (instance == null) {
            instance = new CustomizationTopComponent();
        }
        return instance;
    }

    @Override
    public int getPersistenceType() {
        return TopComponent.PERSISTENCE_ALWAYS;
    }

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    /** replaces this in object stream */
    @Override
    public Object writeReplace() {
        return new ResolvableHelper();
    }

    @Override
    protected String preferredID() {
        return "CustomizationTopComponent";
    }

    public void resultChanged(LookupEvent e) {
        if (e == null && !isDisplayable()) {
            setTool(null);
        } else {
            Collection c = res.allInstances();
            if (c.size() > 0) {
                setTool((Tool) c.iterator().next());
            } else {
                setTool(null);
            }
        }
    }

    /*
    private JPanel sharedPanel = new JPanel();
    private JComponent getCustomizerComponent (CustomizerProvider provider) {
    JPanel result = sharedPanel;
    result.removeAll();
    Customizer c = provider.getCustomizer();
    if (c == null) {
    return null;
    }
    ComponentGroup[] groups = c.getComponentGroups();
    result.setLayout (new GridBagLayout());
    int maxCols = 1;
    for (int i = 0; i < groups.length; i++) {
    maxCols = Math.max (maxCols, groups[i].getComponents().length);
    }
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    for (int i = 0; i < groups.length; i++) {
    JComponent[] contents = groups[i].getComponents();
    JComponent[] withSpacer = new JComponent[contents.length + 1];
    System.arraycopy (contents, 0, withSpacer, 0, contents.length);
    withSpacer[contents.length] = spacer;
    contents = withSpacer;
    maxCols++;
    gbc.weighty = 0;
    for (int j = 0; j < contents.length; j++) {
    System.err.println(j + ": " + contents[j].getClass().getName());
    contents[j].setOpaque(false);
    contents[j].setBackground(Color.WHITE); //XXX
    gbc.gridx = j;
    gbc.gridy = i;
    if (j == 0 && contents.length > 1) {
    gbc.weightx = 0;
    } else {
    gbc.weightx = 1D / (double) (contents.length - 1);
    }
    gbc.anchor = GridBagConstraints.PAGE_START;
    if (j == contents.length - 1) {
    //assume TitledPanel is only component
    if (contents[j] instanceof LayoutDataProvider) {
    gbc.gridwidth = Math.max (2, maxCols - j);
    } else {
    gbc.gridwidth = maxCols - j;
    }
    } else {
    if (contents[j] instanceof LayoutDataProvider) {
    gbc.gridwidth = 2;
    } else {
    gbc.gridwidth = 1;
    }
    }
    if (contents[j].getClass().getName().indexOf("ColorChooser") > 0 || contents[j] instanceof JSlider && ((JSlider)contents[j]).getUI().getClass().getName().indexOf("PopupSlider") > 0) {  //NOI18N
    gbc.fill = GridBagConstraints.NONE;
    gbc.anchor = GridBagConstraints.FIRST_LINE_START;
    } else {
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.FIRST_LINE_END;
    }
    gbc.weighty = 1D / (double) groups.length;
    result.add (contents[j], gbc);
    }
    }
    return result;
    }
     */
    private final JPanel spacer = new JPanel();

    private void setTool(Tool tool) {
        CustomizerProvider provider = tool == null ? null
                : tool.getLookup().lookup(CustomizerProvider.class);
        JComponent comp = null;
        if (provider != null) {
            Customizer c = provider.getCustomizer();
            if (c != null) {
                comp = c.getComponent();
            }
        }
        setInnerComponent(comp, tool == null);
        setDisplayName(tool == null ? NbBundle.getMessage(CustomizationTopComponent.class,
                "CTL_CustomizationTopComponent")
                : NbBundle.getMessage(CustomizationTopComponent.class,
                "FMT_CustomizationTopComponent", tool.getName()));
    }

    private void setInnerComponent(JComponent comp, boolean noTool) {
        if (comp == null) {
            comp = new JLabel(NbBundle.getMessage(
                    CustomizationTopComponent.class, noTool ? "LBL_Empty" : "LBL_No_Customizer"));
            comp.setEnabled(false);
            ((JLabel) comp).setHorizontalTextPosition(SwingConstants.CENTER);
        }
//        comp.setBackground(Color.WHITE); //XXX
        jScrollPane1.setViewportView(comp);
    }

    final static class ResolvableHelper implements Serializable {

        private static final long serialVersionUID = 12309L;

        public Object readResolve() {
            return CustomizationTopComponent.getDefault();
        }
    }
    private final SharedLayoutData data = new DefaultSharedLayoutData();

    public int xPosForColumn(int column) {
        return data.xPosForColumn(column);
    }

    public void register(LayoutDataProvider p) {
        data.register(p);
    }

    public void unregister(LayoutDataProvider p) {
        data.unregister(p);
    }

    public void expanded(LayoutDataProvider p, boolean state) {
        data.expanded(p, state);
    }
}
