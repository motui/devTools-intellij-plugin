package cn.motui.devtools.ui.component.json;

import com.intellij.lang.Language;
import com.intellij.openapi.editor.EditorSettings;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.project.Project;
import com.intellij.ui.LanguageTextField;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 自定义LanguageTextField
 *
 * @author it.motui
 * @date 2021-03-06
 */
public class MyLanguageTextField extends LanguageTextField {

  protected MyLanguageTextField(@Nullable Language language,
                                @Nullable Project project,
                                @NotNull String value,
                                @NotNull DocumentCreator documentCreator) {
    super(language, project, value, documentCreator, false);
  }

  @Override
  protected EditorEx createEditor() {
    EditorEx editor = super.createEditor();
    editor.setVerticalScrollbarVisible(true);
    editor.setHorizontalScrollbarVisible(true);
    EditorSettings settings = editor.getSettings();
    // 显示行号
    settings.setLineNumbersShown(true);
    // 启动代码自动折叠
    settings.setAutoCodeFoldingEnabled(true);
    // 显示折叠轮廓
    settings.setFoldingOutlineShown(true);
    // 允许单个逻辑折叠
    settings.setAllowSingleLogicalLineFolding(true);
    // 设置动画滚动
    settings.setAnimatedScrolling(true);
    // 显示的右边距
    settings.setRightMarginShown(true);
    // 自动折行
    settings.setUseSoftWraps(true);
    // 垂直缩进参考线
    settings.setIndentGuidesShown(true);
    return editor;
  }
}
