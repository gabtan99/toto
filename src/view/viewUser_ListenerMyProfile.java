package view;

import controller.controllerDashboard;
import controller.controllerSong_ArtistAllSongs;
import controller.controllerUser_ListenerMyProfile;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import model.ModelCentral;

import java.io.IOException;

public class viewUser_ListenerMyProfile extends View {

    public controllerUser_ListenerMyProfile controller;

    public viewUser_ListenerMyProfile(AnchorPane mainPane, controllerUser_ListenerMyProfile controller, controllerDashboard dashboardController){
        this.controller = controller;
        this.model = dashboardController.getModel();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("templateListeners.fxml"));
        loader.setController(this);

        try {
            mainPane.getChildren().setAll((AnchorPane) loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Update(){

    }
}
