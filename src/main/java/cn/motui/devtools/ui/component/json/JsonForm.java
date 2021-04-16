package cn.motui.devtools.ui.component.json;

import cn.motui.devtools.ui.component.ComponentForm;
import com.intellij.json.JsonLanguage;
import com.intellij.openapi.project.Project;
import com.intellij.ui.LanguageTextField;

import javax.swing.*;

/**
 * JSON面板
 *
 * @author it.motui
 * @date 2021-03-05
 */
public class JsonForm implements ComponentForm {
  private JPanel root;
  protected LanguageTextField languageTextField;
  protected JButton formatButton;
  protected JButton compressButton;
  protected JButton escapeButton;
  protected JButton unescapedButton;
  protected JButton unicodeToChineseButton;
  protected JButton chineseToUnicodeButton;
  protected JPanel toolBar;

  private final Project project;

  public JsonForm(Project project) {
    this.project = project;
  }

  private static final String DEFAULT_TEXT = "{\n}";

  @Override
  public JComponent root() {
    return root;
  }

  private void createUIComponents() {
    languageTextField = new MyLanguageTextField(JsonLanguage.INSTANCE,
        project, DEFAULT_TEXT, new MyLanguageTextField.SimpleDocumentCreator());
  }
}
