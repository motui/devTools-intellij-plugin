package cn.motui.devtools.ui;

import javax.swing.*;

/**
 * @author zhihaoguo
 * @date 2021-02-27
 */
public class MainForm {
  private JPanel root;
  private JLabel label;

  public JComponent root() {
    return root;
  }

  public void setLabel(String text) {
    label.setText(text);
  }
}
