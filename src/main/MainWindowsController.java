package main;

import add.person.AddPerson;
import db.StudentRepository;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.StudentSS;
import person.list.PersonList;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.UnaryOperator;
//implements Initializable
public class MainWindowsController {
    private static final String TAB_PERSON_ADD = "1";
    private static final String TAB_PERSON_LIST = "2";
    private static final String TAB_PERSON_SEARCH = "3";
    private static final String TAB_PERSON_EDIT_UPDATE = "4";

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

    //create----------C
    @FXML
    public void showAddPerson() throws IOException {
        Node node = FXMLLoader.load(getClass().getResource("../add/person/add_person.fxml"));
        Tab selectedTab = null;

        if(!isTabAlreadyPresent(TAB_PERSON_ADD)){
            System.out.println("in isTabAlreadyPresent false");
            selectedTab = new Tab(" Add Person ", node);
            selectedTab.setId(TAB_PERSON_ADD);
            mainTab.getTabs().add(selectedTab);
        }else {
            selectedTab = getTabById(TAB_PERSON_ADD);
        }
        System.out.println("selecting tab ");
        mainTab.getSelectionModel().select(selectedTab);

/*
        Scene scene = new Scene(parent);
        stage.setTitle("Add Person");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();*/
    }

    @FXML
    public void showPersonList() throws IOException {

        FXMLLoader loaderPersonList = new  FXMLLoader(getClass().getResource("../person/list/person_list.fxml/"));
        Node node= loaderPersonList.load();
        PersonList controllerPersonList =loaderPersonList.getController();

        controllerPersonList.setPersonSelectedCallback((p)->{
            System.out.println("in main windows");
            System.out.println(p);
            try {
                showEditUpdatePersonTabInMain(p, controllerPersonList);
            } catch (IOException e) {
                System.out.println("Exception___________________________________");
                e.printStackTrace();
            }
        });
        Tab tab1 = null;

        if(!isTabAlreadyPresent(TAB_PERSON_LIST)){
            System.out.println("in showPersonList false");

            tab1 =new Tab("Show Person list", node);
            tab1.setId(TAB_PERSON_LIST);
            mainTab.getTabs().add(tab1);
        }else {
            System.out.println("in showPersonList true");
            tab1 = getTabById(TAB_PERSON_LIST);
        }
        mainTab.getSelectionModel().select(tab1);
   }
    @FXML
    public void showSearchTab() throws IOException {

        Node node = FXMLLoader.load(getClass().getResource("../searchPerson/search_windwos.fxml"));
        Tab tab2 = null;
        if (!isTabAlreadyPresent(TAB_PERSON_SEARCH)){
            tab2=new Tab("Search Tab", node);
            tab2.setId(TAB_PERSON_SEARCH);
            mainTab.getTabs().add(tab2);
        }else {
            tab2 = getTabById(TAB_PERSON_SEARCH);
        }
        mainTab.getSelectionModel().select(tab2);
    }

    private void showEditUpdatePersonTabInMain(StudentSS studentSS, PersonList controllerPersonList) throws IOException {

        System.out.println("in showEditUpdatePersonTab");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../add/person/add_person.fxml"));

        Node node = loader.load();

        System.out.println("in showEditUpdatePersonTab  1111");
        AddPerson controller = loader.getController();

        controller.setDataTextField(studentSS);
        Tab tab1 = null;

        if(!isTabAlreadyPresent(TAB_PERSON_EDIT_UPDATE)){
            System.out.println("in showPersonList false");

            tab1 =new Tab("Edit & Update", node);
            tab1.setId(TAB_PERSON_EDIT_UPDATE);
            mainTab.getTabs().add(tab1);
        }else {
            System.out.println("in showPersonList true");
            tab1 = getTabById(TAB_PERSON_EDIT_UPDATE);
            tab1.setContent(node);
        }
        mainTab.getSelectionModel().select(tab1);
        controller.setPersonEditUpdateCallback((v)-> {
            closeTab(TAB_PERSON_EDIT_UPDATE);
            controllerPersonList.loadData();
        });

    }
/*    private void closeEditPersonTab() {
        Tab editTab = getTabById(TAB_PERSON_EDIT);
        mainTab.getTabs().remove(editTab);
    }*/
    private void closeTab(String id){
        Tab tab = getTabById(id);
        mainTab.getTabs().remove(tab);
    }

    private boolean isTabAlreadyPresent(String tabNum) {
        if(getTabById(tabNum)!=null){
            return true;
        }
        return false;
    }
    private Tab getTabById(String tabNum) {
        System.out.println("in getTabById");
        ObservableList<Tab> tab = mainTab.getTabs();
        for(Tab t : tab){
            System.out.println("in getTabById  For");
            if(t.getId().equals(tabNum)) {
                return t;
            }
        }
        return null;
    }


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


    //select/read all -R
   /* @FXML----------------------------------------------------------in 61 line
    public void showPersonList() throws IOException {
        //Stage stage = new Stage();
        //Parent parent = FXMLLoader.load(getClass().getResource("../person/list/person_list.fxml/"));


        Node node = FXMLLoader.load(getClass().getResource("../person/list/person_list.fxml/"));
        optionsPane.setCenter(node);
        Tab tab = null;
        if(isTabAlreadyPresent(TAB_PERSON_LIST)){
            tab = new Tab("Show Person list", node);
            tab.setId(TAB_PERSON_LIST);
            mainTab.getTabs().add(tab);
        }else {
            tab = getTabById(TAB_PERSON_LIST);
        }
        mainTab.getSelectionModel().select(tab);
    }
*/
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


   /* @Override
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
    }*/
}
