// Generated code from Butter Knife. Do not modify!
package cn.kunggka.easyweather.ui.chooseArea;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import cn.kunggka.easyweather.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ChooseAreaFragment_ViewBinding implements Unbinder {
  private ChooseAreaFragment target;

  @UiThread
  public ChooseAreaFragment_ViewBinding(ChooseAreaFragment target, View source) {
    this.target = target;

    target.backButton = Utils.findRequiredViewAsType(source, R.id.back_button, "field 'backButton'", Button.class);
    target.titleText = Utils.findRequiredViewAsType(source, R.id.title_text, "field 'titleText'", TextView.class);
    target.listView = Utils.findRequiredViewAsType(source, R.id.list_view, "field 'listView'", ListView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ChooseAreaFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.backButton = null;
    target.titleText = null;
    target.listView = null;
  }
}
