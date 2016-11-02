package Util;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Created by User on 16.08.2016.
 */
public class ShowText {
    public static void show(String str, Parent parent, Stage parentStage) {
        Stage st = new Stage();
        if(parent != null) {
            parent.setDisable(true);
            st.initOwner(parentStage);
            st.setOnCloseRequest((WindowEvent event) -> parent.setDisable(false));
        }
        st.setAlwaysOnTop(true);
        TextArea textArea = new TextArea(str) ;
        textArea.setStyle("-fx-border-color: darkSlateGrey; -fx-border-witch: 3pt; -fx-font: 12pt Arial; -fx-text-fill: darkSlateBlue");
        st.setScene(new Scene(textArea));
        st.show();
    }
}
