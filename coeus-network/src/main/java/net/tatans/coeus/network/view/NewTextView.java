/*
package net.tatans.coeus.network.view;

import net.tatans.coeus.network.imp.ISendChar;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.accessibility.AccessibilityEvent;
import android.widget.TextView;


*/
/**
 * @deprecated Use the new {@link }
 *//*

public class NewTextView extends TextView {

    private String TAG = "NewTextView";
    ISendChar iSendChar;

    public NewTextView(Context context,ISendChar iSendChar) {
        super(context);
        this.iSendChar = iSendChar;
    }

    */
/**
     * (API����4)���û���һ����ͼ����ʱ���ô˷������¼��ǰ����û��������ͷ���,��TYPE_VIEW_CLICKED��
     * ��ͨ������Ҫʵ�ָ÷���,�������Ǵ���һ���Զ�����ͼ��
     * @param eventType
     *//*

    @SuppressLint("NewApi")
	@Override
    public void sendAccessibilityEvent(int eventType) {
        super.sendAccessibilityEvent(eventType);
        if(eventType == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED){
            iSendChar.onSendChar(this.getText().toString());
        }
    }
}
*/
