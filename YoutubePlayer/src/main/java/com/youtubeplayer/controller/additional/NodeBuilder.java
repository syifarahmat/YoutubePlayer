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
package com.youtubeplayer.controller.additional;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXNodesList;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import com.youtubeplayer.Exception.Exceptions;
import com.youtubeplayer.YoutubePlayer;
import static com.youtubeplayer.controller.MainController.service;
import com.youtubeplayer.controller.SettingController;
import static com.youtubeplayer.controller.additional.RoleMenu.PROJECT_URL;
import static com.youtubeplayer.controller.additional.RoleMenu.SETTING_PATH;
import com.youtubeplayer.model.Channel;
import com.youtubeplayer.model.Response;
import com.youtubeplayer.service.impl.ServiceYoutube;
import com.youtubeplayer.util.Session;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author rizal
 */
public class NodeBuilder {
    private final Exceptions exceptions = new Exceptions(this.getClass());
    
    /**
     * getContent based on menu path
     * @param load menu path
     * @param row destination node
     */
    public void openContent(String load, HBox row){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            row.getChildren().clear();
            Parent content = fxmlLoader.load(getClass().getClassLoader().getResourceAsStream(load));
            content.getStylesheets()
                    .add(getClass().getClassLoader().getResource("css/main.css")
                    .toExternalFo‌rm());
            //row.setVisible(true);//node always true
            row.getChildren().add(content);  
        } catch (IOException e) {
            exceptions.log(e);
        }
    }
    
    /**
     * build user button
     * @return button
     */
    public Map<String, Object> user(){
        Map<String, Object> map = new HashMap<>();
        Label lName;
        Image img;
        Channel userChannel;
        Response response = service.user();
        if (response.isStatus()) {
            userChannel = (Channel) response.getData();
            lName = new Label(userChannel.getChannelTitle());
            img =new Image(userChannel.getThumbnailURL(), 30, 30, false, false, true);
        }else{
            userChannel = null;
            lName = new Label("Anonymous");
            img = defaultThumbnailUser(30);
        }
        lName.setId("lGreyBox");
        
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(30);imgView.setFitHeight(30);
        imgView.setClip(new Circle(15, 15, 15));
        
        HBox hBox = new HBox(lName, imgView);
        hBox.setId("channelRight");
        hBox.setSpacing(10);
        
        JFXButton open = new JFXButton("", hBox);
        open.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        
        map.put("node", open);
        map.put("user", userChannel);
        return map;
    }
    private Image defaultThumbnailUser(double size){
        return new Image(
                    getClass().getClassLoader().getResourceAsStream(
                            "images/human-min.png"), 
                            size, 
                            size, 
                            false, 
                            false
            );
    }
    
    /**
     * build user setting
     * @param userChannel
     * @return nodes
     */
    public VBox userSetting(Channel userChannel){
        Label lName;
        Image img;
        HBox box = new HBox();
        box.setSpacing(5);
        box.setId("box");
        if (userChannel == null) {
            lName = new Label("Anonymous");
            img = defaultThumbnailUser(120);
            
            JFXButton bLogin = new JFXButton("Login");
            bLogin.setId("bUser");
            bLogin.setOnAction((e) -> {
                Session session = new Session();
                session.setLogin(true);
                session.setRefreshLogin(true);
                lName.setText("Close setting to reload!");
            });
            box.getChildren().add(bLogin);
        }else{
            lName = new Label(userChannel.getChannelTitle());
            img =new Image(userChannel.getThumbnailURL(), 120, 120, false, false, true);
            
            JFXButton bLogout = new JFXButton("Logout");
            bLogout.setId("bUser");
            bLogout.setOnAction((e) -> {
                Session session = new Session();
                session.setLogin(false);
                session.setRefreshLogin(true);
                lName.setText("Close setting to reload!");
            });
            JFXButton bRefresh = new JFXButton("Refresh");
            bRefresh.setId("bUser");
            bRefresh.setOnAction((e) -> {
                Session session = new Session();
                session.setLogin(true);
                session.setRefreshLogin(true);
                lName.setText("Close setting to reload!");
            });
            box.getChildren().addAll(bLogout, bRefresh);
        }
        
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(120);imgView.setFitHeight(120);
        imgView.setClip(new Circle(60, 60, 60));
        
        lName.setId("lUser");
        
        VBox vbox = new VBox(imgView, lName, box);
        vbox.setSpacing(7);
        vbox.setId("box");
        return vbox;
    }
    
    /**
     * open YoutubePlayer project on github
     */
    public void github(){
        new Thread(() -> {
            try {
                Desktop.getDesktop().browse(URI.create(PROJECT_URL));
            } catch (IOException e) {
                exceptions.log(e);
            }
        }).start();
    }
    
    /**
     * (Unused)
     * initiateContent menu
     * @param load fxml path 
     * @return content
     * @throws Exception 
     */
    public Parent initiateContent(String load) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        return fxmlLoader.load(YoutubePlayer.class.getResourceAsStream(load));
    }
    
}
