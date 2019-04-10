package controller.Listener;

import controller.PaneController;
import controller.controllerDashboard;
import javafx.scene.layout.AnchorPane;
import view.Artist.viewSong_ArtistShowArtistPlaylistSongs;
import view.Listener.viewSong_ListenerShowArtistPlaylistSongs;

public class controllerSong_ListenerShowArtistPlayistSongs extends PaneController {

    public controllerSong_ListenerShowArtistPlayistSongs(AnchorPane mainPane, controllerDashboard dashboardController){
        super(mainPane, dashboardController);
        this.model = dashboardController.getModel();
        view = new viewSong_ListenerShowArtistPlaylistSongs(   mainPane,this, dashboardController);
    }
}