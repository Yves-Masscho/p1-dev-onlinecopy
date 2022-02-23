module Sokoban {
	requires java.sql;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.media;
	
	opens main to javafx.graphics,javafx.fxml;
	opens gui to javafx.graphics,javafx.fxml;
}