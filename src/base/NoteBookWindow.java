package base;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 
 * NoteBook GUI with JAVAFX
 * 
 * COMP 3021
 * 
 * 
 * @author valerio
 *
 */
public class NoteBookWindow extends Application {

	/**
	 * TextArea containing the note
	 */
	final TextArea textAreaNote = new TextArea("");
	/**
	 * list view showing the titles of the current folder
	 */
	final ListView<String> titleslistView = new ListView<String>();
	/**
	 * Combobox for selecting the folder
	 */
	final ComboBox<String> foldersComboBox = new ComboBox<String>();
	/**
	 *  Button next to foldersComboBox
	*/
	final Button addFolderButton = new Button();
	/**
	 * Add Note Button
	 */
	final Button addNoteButton = new Button();
	/**
	 * This is our Notebook object
	 */
	NoteBook noteBook = null;
	/**
	 * current folder selected by the user
	 */
	String currentFolder = "";
	/**
	 * current search string
	 */
	String currentSearch = "";
	/**
	 *
	 */
	String currentNote = "";
	/**
	* the stage of this display
	*/
	Stage stage;
	public static void main(String[] args) {
		launch(NoteBookWindow.class, args);
	}

	@Override
	public void start(Stage stage) {
		loadNoteBook();
		// Use a border pane as the root for scene
		BorderPane border = new BorderPane();
		// add top, left and center
		border.setTop(addHBox());
		border.setLeft(addVBox());
		VBox vBox = new VBox();
		vBox.getChildren().addAll(addButtons(), addGridPane());
		border.setCenter(vBox);

		Scene scene = new Scene(border);
		stage.setScene(scene);
		stage.setTitle("NoteBook COMP 3021");
		stage.show();
	}

	/**
	 * This create the top section
	 * 
	 * @return
	 */
	private HBox addHBox() {

		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10); // Gap between nodes

		Button buttonLoad = new Button("Load from File");
		buttonLoad.setPrefSize(100, 20);
//		buttonLoad.setDisable(true);
		buttonLoad.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				// TODO load a NoteBook from File
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Please Choose An File Which Contains a NoteBook Object!");

				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Serialized Object File (*.ser)", "*.ser");
				fileChooser.getExtensionFilters().add(extFilter);

				File file = fileChooser.showOpenDialog(stage);
				if(file!= null){
					// TODO
					loadNoteBook(file);
					// update foldersComboBox
					foldersComboBox.getItems().clear();
					ArrayList<String> foldersName = new ArrayList<>();
					for (Folder f: noteBook.getFolders()){
						foldersName.add(f.getName());
					}
					foldersComboBox.getItems().addAll(foldersName);
					// update listView
					updateListView();
				}
			}
		});

		Button buttonSave = new Button("Save to File");
		buttonSave.setPrefSize(100, 20);
//		buttonSave.setDisable(true);
		buttonSave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				// TODO save a NoteBook to File
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Please Choose An File Which Contains a NoteBook Object!");

				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Serialized Object File (*.ser)", "*.ser");
				fileChooser.getExtensionFilters().add(extFilter);

				File file = fileChooser.showOpenDialog(stage);
				if(file!= null){
					// TODO
					if(noteBook.save(file.getAbsolutePath())){
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("Successfully saved");
						alert.setContentText("You file has been saved to file " + file.getName());
						alert.showAndWait().ifPresent(rs -> {
							if (rs == ButtonType.OK) {
								System.out.println("Pressed OK.");
							}
						});
					}
				}
			}
		});

		hbox.getChildren().addAll(buttonLoad, buttonSave);

		// TODO Task 5
		Label label = new Label("Search : ");
		label.setPrefSize(80, 20);

		TextField textfield = new TextField();
		textfield.setPrefSize(200, 20);

		Button searchButton = new Button("Search");
		searchButton.autosize();
		searchButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				currentSearch = textfield.getText();
				textAreaNote.setText("");
				for (Folder f: noteBook.getFolders()){
					if (Objects.equals(f.getName(), currentFolder)){
						List<String> list = new ArrayList<>();
						List<Note> notes = f.searchNotes(currentSearch);
						for (Note n: notes)
							list.add(n.getTitle());
						titleslistView.getItems().clear();
						titleslistView.getItems().addAll(list);
					}
				}

			}
		});

		Button clearSearchButton = new Button("Clear Search");
		clearSearchButton.autosize();
		hbox.getChildren().addAll(label, textfield, searchButton, clearSearchButton);
		clearSearchButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				currentSearch = "";
				textfield.setText("");
				textAreaNote.setText("");
				updateListView();
			}
		});

		return hbox;
	}

	/**
	 * this create the section on the left
	 * 
	 * @return
	 */
	private VBox addVBox() {

		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10)); // Set all sides to 10
		vbox.setSpacing(8); // Gap between nodes

		// TODO adding a extra HBox contains foldersComboBox and addFolder
		HBox hBox = new HBox();
		hBox.setSpacing(10); // Gap between nodes

		ArrayList<String> foldersName = new ArrayList<>();
		for (Folder f: noteBook.getFolders()){
			foldersName.add(f.getName());
		}
		foldersComboBox.getItems().addAll(foldersName);

		foldersComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue ov, Object t, Object t1) {
				if(t1 != null)
					currentFolder = t1.toString();
				else
					currentFolder = "";
				currentNote = "";
				// this contains the name of the folder selected
				// TODO update listview
				updateListView();
			}

		});
		foldersComboBox.setValue("-----");
		// Setting the addFolder button
		addFolderButton.setText("Add a Folder");
		addFolderButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				TextInputDialog dialog = new TextInputDialog("Add a Folder");
				dialog.setTitle("Input");
				dialog.setHeaderText("Add a new folder for your notebook:");
				dialog.setContentText("Please enter the name you want to create:");

				// Traditional way to get the response value.
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()){
					// TODO
					boolean addFile = true;
					if(Objects.equals(result.get(), "")){
						Alert alert = new Alert(Alert.AlertType.WARNING);
						alert.setTitle("Warning");
						alert.setContentText("Please input an valid folder name");
						alert.showAndWait().ifPresent(rs -> {
							if (rs == ButtonType.OK) {
								System.out.println("Pressed OK.");
							}
						});
						addFile = false;
					}
					for(String f: foldersName){
						if(f.equals(result.get())){
							Alert alert = new Alert(Alert.AlertType.WARNING);
							alert.setTitle("Warning");
							alert.setContentText("You already have a folder named with"+f);
							alert.showAndWait().ifPresent(rs -> {
								if (rs == ButtonType.OK) {
									System.out.println("Pressed OK.");
								}
							});
							addFile = false;
							break;
						}
					}
					if(addFile){
						String fileName = result.get();
						noteBook.addFolder(fileName);
						foldersName.add(fileName);
						foldersComboBox.getItems().add(fileName);
					}
				}
			}
		});

		vbox.getChildren().add(new Label("Choose folder: "));
		hBox.getChildren().addAll(foldersComboBox, addFolderButton);
		vbox.getChildren().add(hBox);
		// End of TODO Lab 8 Task 2

		titleslistView.setPrefHeight(100);
		titleslistView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue ov, Object t, Object t1) {
				if (t1 == null)
					return;
				String title = t1.toString();
				// This is the selected title
				// TODO load the content of the selected note in textAreaNote
				String content = "";

				for (Folder f: noteBook.getFolders()){
					for(Note n : f.getNotes()){
						if (Objects.equals(n.getTitle(), title)){
							content = ((TextNote) n).getContent();
						}
					}
				}
				textAreaNote.setText(content);
				currentNote = title;
			}
		});

		vbox.getChildren().add(new Label("Choose note title"));
		vbox.getChildren().add(titleslistView);

		// TODO add addNote Button
		addNoteButton.setText("Add a Note");
		addNoteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				if(Objects.equals(currentFolder, "") || Objects.equals(currentFolder, "-----")){
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setContentText("Please choose a folder first!");
					alert.showAndWait().ifPresent(rs -> {
						if (rs == ButtonType.OK) {
							System.out.println("Pressed OK.");
						}
					});
				}
				else{
					TextInputDialog dialog = new TextInputDialog("Add a Note");
					dialog.setTitle("Input");
					dialog.setHeaderText("Add a new note to current folder");
					dialog.setContentText("Please enter the name of your note:");

					Optional<String> result = dialog.showAndWait();
					if (result.isPresent()) {
						noteBook.createTextNote(currentFolder, result.get());
						// update the list view
						updateListView();
						// Adding alert
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setTitle("Successful!");
						alert.setContentText("Insert note " + result.get()+" to folder "+currentFolder+" successfully!");
						alert.showAndWait().ifPresent(rs -> {
							if (rs == ButtonType.OK) {
								System.out.println("Pressed OK.");
							}
						});
					}
				}
			}
		});
		vbox.getChildren().add(addNoteButton);
		return vbox;
	}

	private void updateListView() {
		ArrayList<String> list = new ArrayList<String>();

		// TODO populate the list object with all the TextNote titles of the currentFolder
		for (Folder f: noteBook.getFolders()){
			if (Objects.equals(f.getName(), currentFolder)){
				for(Note note: f.getNotes()){
					if (note instanceof TextNote){
						list.add(note.getTitle());
					}
				}
			}
		}

		titleslistView.getItems().addAll(list);

		ObservableList<String> combox2 = FXCollections.observableArrayList(list);
		titleslistView.setItems(combox2);
		textAreaNote.setText("");
	}

	/*
	 * Creates a grid for the center region with four columns and three rows
	 */
	private GridPane addGridPane() {

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 10, 10));
		textAreaNote.setEditable(true);
		textAreaNote.setMaxSize(450, 400);
		textAreaNote.setWrapText(true);
		textAreaNote.setPrefWidth(450);
		textAreaNote.setPrefHeight(400);
		// 0 0 is the position in the grid
		grid.add(textAreaNote, 0, 0);
		return grid;
	}
	private HBox addButtons(){
		// 	TODO: add 2 buttons saveNote and deleteNote
		HBox hBox = new HBox();
		hBox.setSpacing(10); // Gap between nodes
		ImageView saveView = new ImageView();
		saveView.setImage(new Image(String.valueOf(new File("save.png"))));
		saveView.setFitHeight(18);
		saveView.setFitWidth(18);
		saveView.setPreserveRatio(true);

		Button saveNote = new Button("Save Note");
		saveNote.setPrefSize(100, 20);
		saveNote.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				if(Objects.equals(currentNote, "")){
					// Give a warning
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setContentText("Please select a folder and a note");
					alert.showAndWait().ifPresent(rs -> {
						if (rs == ButtonType.OK) {
							System.out.println("Pressed OK.");
						}
					});
				}
				else{
					// Save the note
					for(Folder folder:noteBook.getFolders()){
						if(Objects.equals(folder.getName(), currentFolder)){
							for(Note note: folder.getNotes()){
								if(Objects.equals(note.getTitle(), currentNote) && note instanceof TextNote){
									((TextNote) note).setContent(textAreaNote.getText());
								}
							}
						}
					}
				}
			}
		});

		ImageView deleteView = new ImageView();
		deleteView.setImage(new Image(String.valueOf(new File("save.png"))));
		deleteView.setFitHeight(18);
		deleteView.setFitWidth(18);
		deleteView.setPreserveRatio(true);

		Button deleteNote = new Button("Delete Note");
		deleteNote.setPrefSize(100, 20);
		deleteNote.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				if(Objects.equals(currentNote, "")){
					// give a warning
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setContentText("Please choose a note to delete");
					alert.showAndWait().ifPresent(rs -> {
						if (rs == ButtonType.OK) {
							System.out.println("Pressed OK.");
						}
					});
				}
				else{
					for (Folder f:noteBook.getFolders()){
						if(f.getName().equals(currentFolder)){
							if(f.removeNotes(currentNote)){
								updateListView();
								Alert alert = new Alert(Alert.AlertType.INFORMATION);
								alert.setTitle("Successful!");
								alert.setContentText("Your note have been successfully removed");
								alert.showAndWait().ifPresent(rs -> {
									if (rs == ButtonType.OK) {
										System.out.println("Pressed OK.");
									}
								});
								currentNote = "";
							}
						}
					}
				}
			}
		});
		hBox.getChildren().addAll(saveView, saveNote, deleteView, deleteNote);
		return hBox;
	}
	private void loadNoteBook(File file){
		noteBook = new NoteBook(file.getAbsolutePath());
	}
	private void loadNoteBook() {
		NoteBook nb = new NoteBook();
		nb.createTextNote("COMP3021", "COMP3021 syllabus", "Be able to implement object-oriented concepts in Java.");
		nb.createTextNote("COMP3021", "course information",
				"Introduction to Java Programming. Fundamentals include language syntax, object-oriented programming, inheritance, interface, polymorphism, exception handling, multithreading and lambdas.");
		nb.createTextNote("COMP3021", "Lab requirement",
				"Each lab has 2 credits, 1 for attendence and the other is based the completeness of your lab.");

		nb.createTextNote("Books", "The Throwback Special: A Novel",
				"Here is the absorbing story of twenty-two men who gather every fall to painstakingly reenact what ESPN called “the most shocking play in NFL history” and the Washington Redskins dubbed the “Throwback Special”: the November 1985 play in which the Redskins’ Joe Theismann had his leg horribly broken by Lawrence Taylor of the New York Giants live on Monday Night Football. With wit and great empathy, Chris Bachelder introduces us to Charles, a psychologist whose expertise is in high demand; George, a garrulous public librarian; Fat Michael, envied and despised by the others for being exquisitely fit; Jeff, a recently divorced man who has become a theorist of marriage; and many more. Over the course of a weekend, the men reveal their secret hopes, fears, and passions as they choose roles, spend a long night of the soul preparing for the play, and finally enact their bizarre ritual for what may be the last time. Along the way, mishaps, misunderstandings, and grievances pile up, and the comforting traditions holding the group together threaten to give way. The Throwback Special is a moving and comic tale filled with pitch-perfect observations about manhood, marriage, middle age, and the rituals we all enact as part of being alive.");
		nb.createTextNote("Books", "Another Brooklyn: A Novel",
				"The acclaimed New York Times bestselling and National Book Award–winning author of Brown Girl Dreaming delivers her first adult novel in twenty years. Running into a long-ago friend sets memory from the 1970s in motion for August, transporting her to a time and a place where friendship was everything—until it wasn’t. For August and her girls, sharing confidences as they ambled through neighborhood streets, Brooklyn was a place where they believed that they were beautiful, talented, brilliant—a part of a future that belonged to them. But beneath the hopeful veneer, there was another Brooklyn, a dangerous place where grown men reached for innocent girls in dark hallways, where ghosts haunted the night, where mothers disappeared. A world where madness was just a sunset away and fathers found hope in religion. Like Louise Meriwether’s Daddy Was a Number Runner and Dorothy Allison’s Bastard Out of Carolina, Jacqueline Woodson’s Another Brooklyn heartbreakingly illuminates the formative time when childhood gives way to adulthood—the promise and peril of growing up—and exquisitely renders a powerful, indelible, and fleeting friendship that united four young lives.");

		nb.createTextNote("Holiday", "Vietnam",
				"What I should Bring? When I should go? Ask Romina if she wants to come");
		nb.createTextNote("Holiday", "Los Angeles", "Peter said he wants to go next Agugust");
		nb.createTextNote("Holiday", "Christmas", "Possible destinations : Home, New York or Rome");
		noteBook = nb;
	}
}
