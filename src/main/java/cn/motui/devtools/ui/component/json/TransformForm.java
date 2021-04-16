package cn.motui.devtools.ui.component.json;

import cn.motui.devtools.ui.component.ComponentForm;
import com.intellij.json.JsonLanguage;
import com.intellij.lang.xml.XMLLanguage;
import com.intellij.openapi.project.Project;
import com.intellij.ui.LanguageTextField;

import javax.swing.*;

/**
 * Json 转 Xml Form
 *
 * @author it.motui
 * @date 2021-03-06
 */
public class TransformForm implements ComponentForm {
  private JPanel root;
  protected JButton toRightButton;
  protected JButton toLeftButton;
  protected LanguageTextField leftTextField;
  protected LanguageTextField rightTextField;

  protected void createUIComponents() {
  }

  @Override
  public JComponent root() {
    return root;
  }
}
