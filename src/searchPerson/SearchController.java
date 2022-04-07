package searchPerson;

import add.person.AddPerson;
import db.StudentRepository;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.StudentSS;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class SearchController implements Initializable {
    private static final String TAB_PERSON_ADD = "1";
    private static final String TAB_PERSON_LIST = "2";
    private static final String TAB_PERSON_EDIT = "3";

    StudentRepository studentRepository = new StudentRepository();
    private StudentSS studentSS;

    @FXML
    BorderPane optionsPane;

    @FXML
    TextField personID;
    @FXML
    Text stName;
    @FXML
    Text stAge;
    @FXML
    Text stAddress;
    @FXML
    Text stEmail;
    @FXML
    Text stProfession;
    @FXML
    Button editBtn;
    @FXML
    Button deleteBtn;

    @FXML
    private TabPane mainTab;


    //select/read-----R
    @FXML
    public void findStudentByIdFromFX(){
        setFiledEmpty();

        int id= Integer.parseInt(personID.getText());

        if (studentRepository.findById(id)==null) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"ID not fund please try again");
            alert.show();
        }else {
            deleteBtn.setDisable(false);
            editBtn.setDisable(false);
            studentSS = studentRepository.findById(id);
            setStudentInfoTextFiledValue(studentSS);
        }
    }

    //update----------U
    @FXML
    public void setEditBtn() throws IOException {
       // StudentSS studentSSForEdit = studentRepository.findById(studentSS.getId());

        System.out.println("form main windows\n"+studentSS);
        Stage stage = new Stage();
        FXMLLoader fxmlLoaderControl = new FXMLLoader(getClass().getResource("../add/person/add_person.fxml"));

        Scene scene = new Scene(fxmlLoaderControl.load());

        AddPerson editPerson = fxmlLoaderControl.getController();

        editPerson.setDataTextField(this.studentSS);

        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Update Information");
        stage.show();

        System.out.println("edit btn clicked");
    }

    //delete----------D
    @FXML
    public void setDeleteBtn() {
        System.out.println("delete");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete ?");
        alert.setHeaderText(" Delete Person information ");
        Optional<ButtonType> result = alert.showAndWait();

        //int findId=Integer.parseInt(personID.getText());;
        if (result.get()==ButtonType.OK){
            studentRepository.deleteStudentInfoByID(studentSS.getId());

            setFiledEmpty();
            Alert successfully = new Alert(Alert.AlertType.INFORMATION);
            successfully.setTitle("Information deleted successfully");
            successfully.show();
            personID.setText("");
        }else {
            alert.close();
        }
    }

    //extra method for finding person by sending id
    private void setStudentInfoTextFiledValue(StudentSS studentSS) {

        stName.setText(studentSS.getName());
        stAge.setText(String.valueOf(studentSS.getAge()));
        stAddress.setText(studentSS.getAddress());
        stEmail.setText(studentSS.getEmail());
        stProfession.setText(studentSS.getAddress());
    }

    //setting everything to empty
    private void setFiledEmpty(){
        deleteBtn.setDisable(true);
        editBtn.setDisable(true);
        stName.setText("");
        stProfession.setText("");
        stEmail.setText("");
        stAge.setText("");
        stAddress.setText("");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UnaryOperator<TextFormatter.Change> filter = change->{
            String text = change.getText();

            if(text.matches("[0-9]*")){
                return change;
            }
            return null;
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        personID.setTextFormatter(textFormatter);
    }
}
