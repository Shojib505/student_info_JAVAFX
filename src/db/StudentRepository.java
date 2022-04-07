package db;

import models.StudentSS;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    private ConnectionInterface dbConnection;

    public StudentRepository() {
        System.out.println("Getting Connection");
        dbConnection = DbConnection.getConnection();
    }

    /* boolean b;
        public boolean dataNotFund(){
            int i = 0;
            System.out.println("data not found"+i++);
            return b;
        }*/
    //Add new Student information
    public void saveNewStudentInfo(StudentSS studentSS){
        String saveQuery = "INSERT INTO student_Info (" +
                "StudentName," +
                "age," +
                "address," +
                "email," +
                "profession" +
                ")" +
                "VALUES(?,?,?,?,?)";

        try(Connection connection = dbConnection.getConnection()){
            PreparedStatement prSt = connection.prepareStatement(saveQuery);

            prSt.setString(1,studentSS.getName());
            prSt.setInt(2,studentSS.getAge());
            prSt.setString(3,studentSS.getAddress());
            prSt.setString(4,studentSS.getEmail());
            prSt.setString(5,studentSS.getProfession());

            prSt.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    //For showing All student info
    public List<StudentSS> showStudentInfo(){
        List<StudentSS> studentSSList = new ArrayList<>();
        String selectQuery="SELECT " +
                "id," +
                "StudentName as name," +
                "age," +
                "address," +
                "email," +
                "profession " +
                "from student_info";

        try (Connection connection = dbConnection.getConnection()){
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(selectQuery);
                while (resultSet.next()){
                    StudentSS studentSS = executeQueryForGettingStudentInfo(resultSet);
                    studentSSList.add(studentSS);
                }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return studentSSList;
    }

    //for find by id
    public StudentSS findById(int id){
        String findQuery="SELECT " +
                "id," +
                "StudentName as name," +
                "age," +
                "address," +
                "email," +
                "profession " +
                "FROM student_info " +
                "WHERE id = ?";
        StudentSS studentSS = null;

        try(Connection connection = dbConnection.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(findQuery);
            preparedStatement.setInt(1,id);

            ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    studentSS = executeQueryForGettingStudentInfo(resultSet);
                }else {
                    System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");

                }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return studentSS;
    }

    //update student info
    public void updateStudentInfo(StudentSS studentSS){
        String updateQuery = "UPDATE student_Info" +
                " SET" +
                " StudentName = ?," +
                " age  = ?," +
                " address  = ?," +
                " email = ?," +
                " profession = ?" +
                " WHERE id = ? ";

        try(Connection connection = dbConnection.getConnection()){
            PreparedStatement prSt = connection.prepareStatement(updateQuery);
            prSt.setString(1,studentSS.getName());
            prSt.setInt(2,studentSS.getAge());
            prSt.setString(3, studentSS.getEmail());
            prSt.setString(4, studentSS.getAddress());
            prSt.setString(5,studentSS.getProfession());
            prSt.setInt(6,studentSS.getId());
            prSt.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    //delete student info
    public void  deleteStudentInfoByID(int deleteID){
        String deleteQuery = "DELETE" +
                "   FROM" +
                "   student_info" +
                "   WHERE" +
                "   id = ?";

        try(Connection connection = dbConnection.getConnection()){
            PreparedStatement pStatement = connection.prepareStatement(deleteQuery);
            pStatement.setInt(1,deleteID);

            pStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    private StudentSS executeQueryForGettingStudentInfo(ResultSet rs) throws SQLException {
        int personId= rs.getInt("id");
        String personName = rs.getString("name");
        int personAge = rs.getInt("age");
        String personAddress = rs.getString("address");
        String personEmail = rs.getString("email");
        String personProfession = rs.getString("profession");

        StudentSS studentSS = new StudentSS(personId,personName,personAge,personAddress,personEmail,personProfession);

        return studentSS;

    }







}
