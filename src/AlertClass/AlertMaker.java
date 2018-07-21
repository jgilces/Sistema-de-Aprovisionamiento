package AlertClass;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.events.JFXDialogEvent;
import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

//import library.assistant.util.LibraryAssistantUtil;

public class AlertMaker {
    
    //DECLARAMOS AL CUADRO DE DIALOGO COMO PUBLICO ESTATICO
    public static JFXDialog dialog;
    public static Node node_to_be_blur;
    
    public static void showMaterialDialog(StackPane root,Parent root2, Node nodeToBeBlurred) throws IOException {
        BoxBlur blur = new BoxBlur(4, 4, 4);
        
        node_to_be_blur=nodeToBeBlurred;
        
        dialog = new JFXDialog(root, (Region)root2, JFXDialog.DialogTransition.TOP);
        
        dialog.show();
        dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
            node_to_be_blur.setEffect(null);
        });
        
        node_to_be_blur.setEffect(blur);
    }
}
