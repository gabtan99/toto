package controller.Artist;

import controller.PaneController;
import controller.controllerDashboard;
import javafx.scene.layout.AnchorPane;
import view.Artist.viewSong_ArtistAlbumsFollowedSongs;
import view.Artist.viewSong_ArtistShowArtistPlaylistSongs;

public class controllerSong_ArtistShowArtistPlaylistSongs extends PaneController {

    public controllerSong_ArtistShowArtistPlaylistSongs(AnchorPane mainPane, controllerDashboard dashboardController){
        super(dashboardController);
        this.model = dashboardController.getModel();
        view = new viewSong_ArtistShowArtistPlaylistSongs(mainPane,this, dashboardController);
    }
}
