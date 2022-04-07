package add.person;

import db.StudentRepository;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import models.StudentSS;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class AddPerson implements Initializable {

    public Button updateBtn;
    public Text textViewAdd;
    public Text textViewUpdate;
    public Label lbId;
    public TextField getName;
    public TextField getAge;
    public TextField getAddress;
    public TextField getEmail;
    public TextField getProfession;

    /**ReadOnlyObjectWrapper FOR PARENT CLASS*/
    //private final ReadOnlyObjectWrapper<StudentSS> curSt = new ReadOnlyObjectWrapper<>();
   /*public ReadOnlyObjectProperty<StudentSS> gerCurSt(){
        return curSt.getReadOnlyProperty();
    }*/


//HIDING FOR SAME TAB SHOW
    public Consumer<String> personEditUpdateCallback;
    public void setPersonEditUpdateCallback(Consumer<String> stCallback){
        this.personEditUpdateCallback= stCallback;
    }

    StudentRepository studentRepository = new StudentRepository();

    /**FOR ADD NEW STUDENT*/
    public void addStudentInfoBtn(Event event) {
        String name = getName.getText();
        Integer age = Integer.parseInt(getAge.getText());
        String address = getAddress.getText();
        String email = getEmail.getText();
        String profession = getProfession.getText();

        StudentSS studentSS = new StudentSS(name,age,address,email,profession);

        studentRepository.saveNewStudentInfo(studentSS);

        clearForm();

        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Your information Added successfully ");
        alert.showAndWait();
        //((Node)(event.getSource())).getScene().getWindow().hide();
    }

    StudentSS studentSS = null;

    public void setDataTextField(StudentSS studentSS){

        textViewAdd.setVisible(false);
        textViewUpdate.setVisible(true);

        System.out.println("form edit windows\n"+studentSS);
        this.studentSS =studentSS;
        updateBtn.setVisible(true);
        updateBtn.setDisable(false);
        lbId.setText(String.valueOf(studentSS.getId()));
        getName.setText(studentSS.getName());
        getAge.setText(String.valueOf(studentSS.getAge()));
        getAddress.setText(studentSS.getAddress());
        getEmail.setText(studentSS.getEmail());
        getProfession.setText(studentSS.getProfession());


    }

    @FXML
    public void updateStudentInfoBtn(Event event){
        System.out.println("update");

        String name = getName.getText();
        Integer age = Integer.parseInt(getAge.getText());
        String address = getAddress.getText();
        String email = getEmail.getText();
        String profession = getProfession.getText();

        studentSS.setName(name);
        studentSS.setAge(age);
        studentSS.setAddress(address);
        studentSS.setEmail(email);
        studentSS.setProfession(profession);

        System.out.println("form            update             windows/n \n\n"+studentSS);

        studentRepository.updateStudentInfo(studentSS);

        /**Passing data to ReadOnlyObjectWrapper FOR PARENT CLASS*/
        //curSt.set(this.studentSS);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Your information Updated successfully ");
        alert.showAndWait();

        personEditUpdateCallback.accept("Updated");
        clearForm();

        //hide&close stage
        //((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    public void resetButtonOnAction() {
        clearForm();
    }

    private void clearForm() {
        getName.setText("");
        getAge.setText("");
        getEmail.setText("");
        getProfession.setText("");
        getAddress.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inputIntTextFormatter();
    }

    private void inputIntTextFormatter() {

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            System.out.println(text);
            if(text.matches("[0-9]*")){
                return change;
            }
            return null;
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        getAge.setTextFormatter(textFormatter);
    }

}
