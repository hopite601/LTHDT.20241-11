/**
 * 
 */
/**
 * 
 */
module treeVisualization {
	requires javafx.controls;
	requires javafx.fxml;
	
	exports GUI; // Xuất khẩu package GUI cho module javafx.graphics
	opens GUI to javafx.fxml; //  // Mở package GUI cho javafx.fxml để load FXML
}