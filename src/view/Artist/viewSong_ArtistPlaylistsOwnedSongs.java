package view.Artist;

import com.jfoenix.controls.JFXListView;
import controller.Artist.controllerSong_ArtistPlaylistFollowedSongs;
import controller.Artist.controllerSong_ArtistPlaylistOwnedSongs;
import controller.Artist.controllerSong_ArtistPlaylistOwnedSongs;
import controller.controllerDashboard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import object.Playlist;
import view.View;
import view_builders.Director;
import view_builders.builderSong;
import view_builders.builderSong_ArtistPlaylistOwnedSongs;

import java.io.IOException;

public class viewSong_ArtistPlaylistsOwnedSongs extends View {

    private controllerSong_ArtistPlaylistOwnedSongs controller;
    @FXML AnchorPane songsHeader;
    @FXML JFXListView songListView;
    private Label headerLabel;
    private Label subheaderLabel;
    private Playlist selectedPlaylist;

    //Songs inside the Playlist I own

    public viewSong_ArtistPlaylistsOwnedSongs (AnchorPane mainPane, controllerSong_ArtistPlaylistOwnedSongs controller, controllerDashboard dashboardController){
        this.controller = controller;
        this.model = dashboardController.getModel();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/templateSearchable.fxml"));
        loader.setController(this);

        try {
            mainPane.getChildren().setAll((AnchorPane) loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        selectedPlaylist = model.getLibraryModel().getSelectedPlaylist();
        initHeader();
        Update();
    }

    @Override
    public void Update(){
        setSongs();
    }

    private void initHeader () {
        //INITIALIZES THE HEADER//
        String PLAYLISTNAME = selectedPlaylist.getName();
        String ARTISTNAME = "Your Playlist";
        headerLabel = new Label(PLAYLISTNAME);
        subheaderLabel = new Label(ARTISTNAME);
        headerLabel.setFont(Font.font("Comfortaa", 18));
        subheaderLabel.setFont(Font.font("Comfortaa",12));
        songsHeader.setLeftAnchor(headerLabel, 285.0);
        songsHeader.setTopAnchor(headerLabel, 50.0);
        songsHeader.setLeftAnchor(subheaderLabel, 285.0);
        songsHeader.setTopAnchor(subheaderLabel, 70.0);
        songsHeader.getChildren().add(headerLabel);
        songsHeader.getChildren().add(subheaderLabel);
    }
    //BUILDER//

    private void setSongs () {
        songListView.getItems().clear();
        //SETS SONGS//
        builderSong builder = new builderSong_ArtistPlaylistOwnedSongs(controller);
        Director director = Director.getInstance();
        director.setBuilder(builder);
        director.construct();

        for (Object object: builder.getProduct()){
            AnchorPane anchorPane = (AnchorPane) object;
            songListView.getItems().add(anchorPane);
        }
    }
}
