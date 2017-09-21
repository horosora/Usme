import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.markdown4j.Markdown4jProcessor;

public class AppController implements Initializable {

    @FXML private WebView webView;
    @FXML private TextArea textArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                webView.getEngine().loadContent(new Markdown4jProcessor().process(newValue));
            }
            catch (StringIndexOutOfBoundsException | IOException e) {
                webView.getEngine().loadContent(newValue);
            }
        });
    }

    @FXML
    public void fileOpen(Event e){
        FileChooser fileSelect = new FileChooser();
        fileSelect.setTitle("File selection");
        fileSelect.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("markdown file", "*.md"),
                                                new FileChooser.ExtensionFilter("All Files", "*.*"));

        File importFile = fileSelect.showOpenDialog(null);

    }

    @FXML
    public void appExit(Event e) {
        System.exit(0);
    }


    @FXML
    public void fileSaveAa(Event e){
        FileChooser saveSelect = new FileChooser();
        saveSelect.setTitle("Select storage location");
        saveSelect.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("markdown file", "*.md"),
                                                new FileChooser.ExtensionFilter("All Files", "*.*"));

        try{
            File saveFile = saveSelect.showSaveDialog(null);
            FileWriter filewriter = new FileWriter(saveFile);
            filewriter.write(textArea.getText());
            filewriter.close();
        }catch(IOException er){
            System.out.println(er);
        }
    }

    @FXML
    public void about(Event e) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("About.fxml"));
        Scene scene = new Scene(root, 330, 220);
        Stage Stage = new Stage();
        Stage.setScene(scene);
        Stage.setTitle("USME");
        Stage.initModality(Modality.APPLICATION_MODAL);   //閉じるまで操作禁止
        Stage.setResizable(false);   //リサイズ禁止
        Stage.show();
    }
}