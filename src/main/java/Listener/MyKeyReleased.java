package Listener;

import ObjectTable.ObjectData;
import com.sun.javafx.scene.control.skin.ComboBoxListViewSkin;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Created by User on 10.10.2016.
 */

public class MyKeyReleased implements EventHandler<KeyEvent> {
    private ComboBox<ObjectData> comboBox;
    private StringBuilder  sb;

    public MyKeyReleased(ComboBox<ObjectData> comboBox){
        this.comboBox = comboBox;
        sb = new StringBuilder();
    }


     @Override
     public void handle(KeyEvent event){
         comboBox.show();

         if (event.getCode() == KeyCode.ESCAPE && sb.length() > 0) {
             sb.delete(0, sb.length());
         }

         if(event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.UP || event.getCode() == KeyCode.TAB)
             return;

         if (event.getCode() == KeyCode.BACK_SPACE && sb.length() > 0)
             sb.deleteCharAt(sb.length() - 1);
         else
             sb.append(event.getText());

         if(sb.length() == 0) {
             ((ComboBoxListViewSkin) comboBox.getSkin()).getListView().scrollTo(0);
             return;
         }

         ObservableList<ObjectData> items = comboBox.getItems();

         for(int i = 0; i < items.size(); i++){
             if(items.get(i).getValue2().getValue().toLowerCase().startsWith(sb.toString().toLowerCase())){
                 ListView<ObjectData> lv = ((ComboBoxListViewSkin) comboBox.getSkin()).getListView();
                 lv.scrollTo(i);
                 break;
             }
         }
     }
}
