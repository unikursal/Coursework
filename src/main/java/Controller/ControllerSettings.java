package Controller;

import Util.MySettings;
import Util.ShowText;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by User on 23.09.2016.
 */
public class ControllerSettings {
    private Stage stage;

    @FXML
    private TextField fieldForKodCourt;

    @FXML
    private Button saveKodCourt;

    public void clickSave(){
        String str = fieldForKodCourt.getText();
        if(str.isEmpty())
            return;
        try {
            int kod = Integer.parseInt(str);

            if(kod < 99 || kod > 1000)
                return;

            MySettings mySettings = new MySettings(kod);

            saveInFile(mySettings);
        }catch(NumberFormatException e){
            ShowText.show("Неправильний формат", null, null);
            return;
        }

        ShowText.show("Збережено\n", null, null);


    }

    private void saveInFile(MySettings mySettings){
        try {
            JAXBContext context = JAXBContext.newInstance(MySettings.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(mySettings, new File(getClass().getResource("/settings.xml").toURI()));
        }catch(JAXBException e){
            ShowText.show("Не вдалось зберегти налаштування\n"+ e.getMessage(), null, null);
            return;
        }catch (URISyntaxException e1){
            ShowText.show("Не вдалось зберегти налаштування\n"+ e1.getMessage(), null, null);

        }
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
