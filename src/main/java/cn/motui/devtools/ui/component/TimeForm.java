package cn.motui.devtools.ui.component;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;

import javax.swing.*;

/**
 * Time 面板
 *
 * @author it.motui
 */
public class TimeForm implements ComponentForm {
  private JPanel root;
  protected JBLabel nowLabel;
  protected JBLabel nowTimeLabel;
  protected JBLabel nowTimeFormatLabel;

  protected JBLabel timestampLabel;
  protected JBTextField timestampField;
  protected ComboBox<Unit> unitComboBox;

  protected JButton timestampButton;
  protected JButton formatButton;

  protected JBLabel formatLabel;
  protected ComboBox<Utc> utcComboBox;
  protected JBTextField formatField;

  @Override
  public JComponent root() {
    return root;
  }
}
