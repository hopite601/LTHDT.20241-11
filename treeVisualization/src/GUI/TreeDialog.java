package GUI;

import java.util.Arrays;
import java.util.List;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class TreeDialog {
	
	 public static int showCreateDialog() {
	        Dialog<Integer> dialog = new Dialog<>();
	        dialog.setTitle("Create Tree");

	        TextField txtNodeValue = new TextField();
	        txtNodeValue.setPromptText("Enter value to insert");

	        dialog.getDialogPane().setContent(txtNodeValue);

	        ButtonType okButton = new ButtonType("OK");
	        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
	        dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

	        dialog.setResultConverter(dialogButton -> {
	            if (dialogButton == okButton) {
	                try {
	                    return Integer.parseInt(txtNodeValue.getText());  // Trả về int nếu hợp lệ
	                } catch (NumberFormatException e) {
	                    return -1;  
	                }
	            }
	            return null;
	        });

	        dialog.showAndWait();

	        Integer result = dialog.getResult();
	        return (result != null) ? result : -1;
	    }
	 
	 
	 public static int[] showInsertDialog() {
	        Dialog<int[]> dialog = new Dialog<>();
	        dialog.setTitle("Insert Node");

	        TextField txtParentValue = new TextField();
	        txtParentValue.setPromptText("Enter Parent Value");

	        TextField txtChildValue = new TextField();
	        txtChildValue.setPromptText("Enter Child Value");

	        // hien thi 2 dialog vao vbox
	        VBox vbox = new VBox(10, txtParentValue, txtChildValue);
	        dialog.getDialogPane().setContent(vbox);

	        ButtonType okButton = new ButtonType("OK");
	        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
	        dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

	        dialog.setResultConverter(dialogButton -> {
	            if (dialogButton == okButton) {
	                try {
	                	int parentValue = Integer.parseInt(txtParentValue.getText());
	                    int childValue = Integer.parseInt(txtChildValue.getText());
	                    return new int[]{parentValue, childValue}; 
	                } catch (NumberFormatException e) {
	                    return null;  
	                }
	            }
	            return null;
	        });

	        dialog.showAndWait();

	        return dialog.getResult() != null ? dialog.getResult() : new int[]{-1, -1}; 
	    }
	 
	 public static String showTraversalDialog() {
			List<String> choices = Arrays.asList("DFS", "BFS");
			ChoiceDialog<String> dialog = new ChoiceDialog<>("DFS", choices);
			dialog.setTitle("Choose Traversal Method");
			dialog.setHeaderText("Select Traversal Method");
			dialog.setContentText("Choose your option:");
			dialog.showAndWait();
			return dialog.getResult();
		}
		public static int showSearchDialog() {
			Dialog<Integer> dialog = new Dialog<>();
			dialog.setTitle("Search Node");
			TextField txtNodeValue = new TextField();
			txtNodeValue.setPromptText("Enter value to search");
			dialog.getDialogPane().setContent(txtNodeValue);
			ButtonType okButton = new ButtonType("OK");
			ButtonType cancelButton = new ButtonType("Cancel");
			dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);
			dialog.setResultConverter(dialogButton -> {
				if (dialogButton == okButton) {
					try {
						return Integer.parseInt(txtNodeValue.getText()); // Trả về int nếu hợp lệ
					} catch (NumberFormatException e) {
						return -1;
					}
				}
				return null;
			});
			dialog.showAndWait();
			Integer result = dialog.getResult();
			return (result != null) ? result : -1;
		}
		
		public static int maxHeightDifDialog() {
			Dialog<Integer> dialog = new Dialog<>();
			dialog.setTitle("MaxHeightDif:");
			dialog.setHeaderText("Enter maxHeightDif:");
			
			TextField textField = new TextField();
			textField.setText("1");
			textField.setPromptText("Enter a integer");
			
			VBox vbox = new VBox(10, textField);
			vbox.setStyle("-fx-padding: 10;");
			
			dialog.getDialogPane().setContent(vbox);
			
			dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
			
			
			// Xử lý kết quả khi nhấn OK
	        dialog.setResultConverter(dialogButton -> {
	            if (dialogButton == ButtonType.OK) {
	                try {
	                    return Integer.parseInt(textField.getText().trim());
	                } catch (NumberFormatException e) {
	                    return -1;
	                }
	            }
	            return null; // Trả về null nếu nhấn Cancel
	        });

	        dialog.showAndWait();
	        Integer result = dialog.getResult();
	        return (result != null) ? result : -1;
		}
}
