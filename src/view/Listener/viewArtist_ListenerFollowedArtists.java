package view.Listener;

import controller.Listener.controllerArtists_ListenerFollowedArtists;
import controller.controllerDashboard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import view.View;
import view_builders.Artist.builderArtist_ArtistFollowedArtists;
import view_builders.Director;
import view_builders.Listener.builderArtist_ListenerFollowedArtists;
import view_builders.builderUser;

import java.io.IOException;

public class viewArtist_ListenerFollowedArtists extends View {
    public controllerArtists_ListenerFollowedArtists controller;

    @FXML AnchorPane artistHeader;
    @FXML AnchorPane artistContent;
    @FXML AnchorPane mainPane;

    private Label headerLabel;
    private TilePane artistList;


    public viewArtist_ListenerFollowedArtists(AnchorPane mainPane, controllerArtists_ListenerFollowedArtists controller, controllerDashboard dashboardController){
        this.controller = controller;
        this.model = dashboardController.getModel();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/templateArtist.fxml"));
        loader.setController(this);

        try {
            mainPane.getChildren().setAll((AnchorPane) loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        initHeader();
        Update();
    }

    @Override
    public void Update(){
        artistContent.getChildren().clear();

        artistList = new TilePane();

        builderUser builder = new builderArtist_ListenerFollowedArtists(controller);
        Director director = Director.getInstance();
        director.setBuilder(builder);
        director.construct();
        for (Object object: builder.getProduct()){
            AnchorPane anchorPane = (AnchorPane)object;
            artistList.getChildren().add(anchorPane);
        }

        artistContent.getChildren().add(artistList);
    }

    public void initHeader(){
        mainPane.getStylesheets().add("view/theme.css");

        headerLabel = new Label("All Followed Artists");

        headerLabel.setId("songHeader");

        artistHeader.setLeftAnchor(headerLabel, 252.0);
        artistHeader.setTopAnchor(headerLabel, 23.0);

        artistHeader.getChildren().add(headerLabel);

    }
}
