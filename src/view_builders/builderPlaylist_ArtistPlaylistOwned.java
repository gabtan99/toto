package view_builders;

import com.jfoenix.controls.JFXPopup;
import controller.Artist.controllerPlaylist_ArtistsAllPlaylists;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import object.Playlist;

import java.util.ArrayList;
import java.util.List;

public class builderPlaylist_ArtistPlaylistOwned extends builderPlaylist<AnchorPane> {

    private controllerPlaylist_ArtistsAllPlaylists controller;

    public builderPlaylist_ArtistPlaylistOwned(controllerPlaylist_ArtistsAllPlaylists controller) {
        this.controller = controller;
        this.listElements = controller.getModel().getLibraryModel().getMyPlaylists();
        this.listProducts = new ArrayList<>();
    }

    @Override
    public void build() {
        while(listElements.hasNext()) {
            Playlist p = listElements.next();
            AnchorPane albumIndiv = new AnchorPane();
            Circle albumCover = new Circle(45);
            Label text = new Label(p.getName());
            Image privateImg = new Image("resources/privateCover.png");
            Image publicImg = new Image("resources/publicCover.png");

            if(p.isIs_public())
                albumCover.setFill(new ImagePattern(publicImg));
            else
                albumCover.setFill(new ImagePattern(privateImg));

            text.setFont(Font.font("Poppins", 13));


            JFXPopup popup = new JFXPopup();
            VBox content = new VBox();
            content.setPrefWidth(65);
            Button deleteButton = new Button("Delete");
            Button editButton = new Button ("Edit");
            deleteButton.setMinWidth(content.getPrefWidth());
            editButton.setMinWidth(content.getPrefWidth());
            content.getChildren().addAll(deleteButton, editButton);
            popup.setPopupContent(content);

            albumCover.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
                        if (((MouseEvent) event).getButton().equals(MouseButton.SECONDARY))
                            popup.show(albumIndiv, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT);
                    }
                }
            });

            deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                }
            });

            editButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                }
            });

            albumIndiv.setLeftAnchor(albumCover, 20.0);
            albumIndiv.setTopAnchor(albumCover, 13.0);
            albumIndiv.setTopAnchor(text, 104.0);
            albumIndiv.setLeftAnchor(text, 46.0);

            albumIndiv.getChildren().add(albumCover);
            albumIndiv.getChildren().add(text);
            listProducts.add(albumIndiv);

            text.setMaxWidth(80.0);
            text.setAlignment(Pos.CENTER);
            text.setWrapText(true);
        }

    }

    @Override
    public List<AnchorPane> getProduct() {
        return listProducts;
    }
}
