/*
 * Copyright 2020 Java Programmer Indonesia.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.youtubeplayer.controller.child.playlist.additional;

import com.jfoenix.controls.JFXButton;
import com.youtubeplayer.Exception.Exceptions;
import static com.youtubeplayer.controller.MainController.playerContainer;
import com.youtubeplayer.model.Video;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author rizal
 */
public class PlaylistBuilder {
    private final Exceptions exceptions = new Exceptions(this.getClass());
    private final HBox parent;
    
    public PlaylistBuilder(HBox parent) {
        this.parent = parent;
    }
   //START PLAYLIST CONTENT
    /**
     * Build grid playlist content
     * @param list
     * @return content multiple rows
     */
    public GridPane buildPlaylist(List<Video> list){
        GridPane grid = new GridPane();
        //grid.setPadding(new Insets(5));
        grid.setVgap(8);
        grid.setStyle("-fx-alignment: TOP_LEFT;"
                //+ "-fx-background-color: white;"
                );
        int row = 0;
        for (Video v : list) {
            grid.add(playlistButton(v), 0, row++);
        }
        return grid;
    }
    private Image defaultThumbnailVideo(){
        return new Image(
                    getClass().getClassLoader().getResourceAsStream(
                            "images/default-min.png"), 
                            175, 
                            80, 
                            true, //enable preseve ratio 
                            false
            );
    }
    private JFXButton playlistButton(Video v){
        Image img = null;
        try {
            if (!v.getThumbnailURL().isEmpty()) {
                img =new Image(v.getThumbnailURL(), 175, 80, false, false, true);
            }else img =defaultThumbnailVideo();
        } catch (Exception e) {
            img =defaultThumbnailVideo();
            exceptions.log(e);
        }
        ImageView imageView = new ImageView(img);
        imageView.setPreserveRatio(false);
        imageView.prefWidth(175);
        imageView.prefHeight(80);
        //imageView.setFitHeight(80);
        imageView.setFitWidth(175);
        StackPane stack = new StackPane(imageView);
        Rectangle rect = new Rectangle(175, 80);
        rect.setArcHeight(15);
        rect.setArcWidth(15);
        stack.setClip(rect);

        Label lTitle = new Label(v.getVideoTitle());
        lTitle.setId("lTrendinTitle");
        //lTitle.setPrefWidth(150);
        lTitle.setMaxHeight(30);
        lTitle.setPrefHeight(30);

        Label lChannel = new Label(v.getChannelTitle());
        lChannel.setId("lTrendingOther");
        //lChannel.setPrefWidth(150);

        Label lDate = new Label(v.getPublishedAt());
        lDate.setId("lTrendingOther");
        //lDate.setPrefWidth(150);

        VBox boxTxt = new VBox(lTitle, lChannel, lDate);
        boxTxt.setId("boxTxt");
        boxTxt.setSpacing(1);

        HBox box = new HBox(stack, boxTxt);
        box.setId("hbBody");
        box.setPadding(new Insets(0));
        JFXButton button = new JFXButton("", box);
        button.setId("trendingButton");
        button.setMinWidth(600);
        button.setMinHeight(90);
        button.setPrefSize(1920, 90);
        button.setOnAction((e) -> {
            playerContainer.prepare(
                    v.getVideoTitle(), 
                    v.getVideoURL()
            );
            playerContainer.setVisible(true);
            playerContainer.play();
        });
        button.setTooltip(new Tooltip(v.getVideoTitle()));
        HBox.setHgrow(button, Priority.ALWAYS);
        button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        return button;
    }
    //END PLAYLIST CONTENT
}
