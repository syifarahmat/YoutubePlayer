/*
 * Copyright 2020 rizal.
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
package com.youtubeplayer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import static com.youtubeplayer.controller.additional.RoleMenu.MAIN_PATH;
import static com.youtubeplayer.controller.additional.RoleMenu.APP_TITLE;

/**
 *
 * @author rizal
 */
public class YoutubePlayer extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(MAIN_PATH));
        stage.setOnCloseRequest((WindowEvent event) -> {
            System.exit(0);
        });
        Scene scene = new Scene(root);
        //Platform.setImplicitExit(true);
        stage.setTitle(APP_TITLE);
        scene.setFill(Color.TRANSPARENT);
        stage.getIcons().add(new Image(
                getClass().getClassLoader().getResourceAsStream("logo-min.png")));
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
