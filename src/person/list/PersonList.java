package person.list;

import add.person.AddPerson;
import db.StudentRepository;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.StudentSS;
import services.StudentService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class PersonList {

    StudentRepository studentRepository = new StudentRepository();
    final StudentService studentService = new StudentService();
    @FXML
    ContextMenu contextMenu;
    public TableView tableView;
    public TableColumn<StudentSS, Integer> columnId;
    public TableColumn<StudentSS, String> columnName;
    public TableColumn<StudentSS, Integer> columnAge;
    public TableColumn<StudentSS, String> columnAddress;
    public TableColumn<StudentSS, String> columnEmail;
    public TableColumn<StudentSS, String> columnProfession;
    public ProgressIndicator progressIndicator;

    public Consumer<StudentSS> personSelectedCallback;
    public void setPersonSelectedCallback(Consumer<StudentSS> stCallback){
        this.personSelectedCallback= stCallback;
    }

    @FXML
    public void initialize(){
        initializeDataConnection();
        loadData();

        progressIndicator.visibleProperty().bind(studentService.runningProperty());
        studentService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                System.out.println("Succeed");
                List<StudentSS> studentSSList = studentService.getValue();
                tableView.getItems().clear();
                tableView.getItems().addAll(studentSSList);
            }
        });
        studentService.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                System.out.println("Failed");
            }
        });
        studentService.start();
    }

    @FXML
    Button selectBtn;
    @FXML
    Button editBtn;
    @FXML
    Button deleteBtn;

    @FXML
    public void clickOnEditCM() throws IOException {
        StudentSS studentSS = (StudentSS) tableView.getSelectionModel().getSelectedItem();
        if(studentSS != null){
            //AddPerson controller = new AddPerson();

            System.out.println(studentSS);
            personSelectedCallback.accept(studentSS);

            //controller.setPersonEditUpdateCallback((v)->loadData());
        }
    }
    @FXML
    public void clickOnDeleteCM(){
        System.out.println("context menu Delete");
        StudentSS studentSS= (StudentSS) tableView.getSelectionModel().getSelectedItem();

        if (studentSS==null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete ?");
        alert.setHeaderText(" Delete Person information ");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get()==ButtonType.OK){
            studentRepository.deleteStudentInfoByID(studentSS.getId());

            loadData();
            Alert successfully = new Alert(Alert.AlertType.INFORMATION);
            successfully.setTitle("Information deleted successfully");

            successfully.showAndWait();
        }else alert.close();
    }
    @FXML
    public void clickOnRefreshCM(){
        loadData();
        studentService.restart();
    }

    int findId;
    @FXML
    public void setSelectBtn(){
        editBtn.setDisable(false);
        deleteBtn.setDisable(false);

        StudentSS studentSS = (StudentSS) tableView.getSelectionModel().getSelectedItem();
        System.out.println(studentSS);
        findId=studentSS.getId();

    }

    //riptide code
    @FXML
    public void setDeleteBtn(){
        System.out.println("delete");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete ?");
        alert.setHeaderText(" Delete Person information ");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get()==ButtonType.OK){
            studentRepository.deleteStudentInfoByID(findId);

            Alert successfully = new Alert(Alert.AlertType.INFORMATION);
            successfully.setTitle("Information deleted successfully");
            successfully.show();
        }else {
            alert.close();
        }

    }

    private void initializeDataConnection(){
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnProfession.setCellValueFactory(new PropertyValueFactory<>("profession"));
    }

    public void loadData(){
//        List<StudentSS> list = studentRepository.showStudentInfo();
//        tableView.getItems().clear();
//        tableView.getItems().addAll(list);

        new Thread(() ->{
            try {
                Thread.sleep(5000);
            }catch (Exception e){
                e.printStackTrace();
            }

            List<StudentSS> listi = studentRepository.showStudentInfo();
            tableView.getItems().clear();
            tableView.getItems().addAll(listi);

        }).start();
    }

}
