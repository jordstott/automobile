package automobileonline.co;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Named(value = "mainBean")
@SessionScoped
public class mainBean implements Serializable {
    //Main bean to store global variables and reusable functions
    
    //Currently logged on user
    private String currentUser;
    private String currentName;

    //Login input field storage
    private String tempUsername;
    private String tempPassword;

    //Register input field storage
    private String tempNewName;
    private String tempNewUsername;
    private String tempNewPassword;
    private String tempNewPasswordCheck;
    private int tempNewMobileNumber = 0;
    private String tempNewEmail;

    //Comment input box storage
    private String commentField;

    //Create a connection to the database, reusable public class
    public Connection getConnection() {
        //Set vars
        Connection con = null;
        String url = "jdbc:mysql://localhost:3306/automobile?allowMultiQueries=true";
        String user = "root";//<- Add your username here
        String password = "";//<- Add your DB password here

        //Load MySQL driver & log any errors
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        //Establish connection with DB & log any errors
        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return con;
    }

    /**
     * @return the tempUsername
     */
    public String getTempUsername() {
        return tempUsername;
    }

    /**
     * @param tempUsername the tempUsername to set
     */
    public void setTempUsername(String tempUsername) {
        this.tempUsername = tempUsername;
    }

    /**
     * @return the tempPassword
     */
    public String getTempPassword() {
        return tempPassword;
    }

    /**
     * @param tempPassword the tempPassword to set
     */
    public void setTempPassword(String tempPassword) {
        this.tempPassword = tempPassword;
    }

    /**
     * @return the tempNewName
     */
    public String getTempNewName() {
        return tempNewName;
    }

    /**
     * @param tempNewName the tempNewName to set
     */
    public void setTempNewName(String tempNewName) {
        this.tempNewName = tempNewName;
    }

    /**
     * @return the tempNewUsername
     */
    public String getTempNewUsername() {
        return tempNewUsername;
    }

    /**
     * @param tempNewUsername the tempNewUsername to set
     */
    public void setTempNewUsername(String tempNewUsername) {
        this.tempNewUsername = tempNewUsername;
    }

    /**
     * @return the tempNewPassword
     */
    public String getTempNewPassword() {
        return tempNewPassword;
    }

    /**
     * @param tempNewPassword the tempNewPassword to set
     */
    public void setTempNewPassword(String tempNewPassword) {
        this.tempNewPassword = tempNewPassword;
    }

    /**
     * @return the tempNewPasswordCheck
     */
    public String getTempNewPasswordCheck() {
        return tempNewPasswordCheck;
    }

    /**
     * @param tempNewPasswordCheck the tempNewPasswordCheck to set
     */
    public void setTempNewPasswordCheck(String tempNewPasswordCheck) {
        this.tempNewPasswordCheck = tempNewPasswordCheck;
    }

    /**
     * @return the tempNewMobileNumber
     */
    public int getTempNewMobileNumber() {
        return tempNewMobileNumber;
    }

    /**
     * @param tempNewMobileNumber the tempNewMobileNumber to set
     */
    public void setTempNewMobileNumber(int tempNewMobileNumber) {
        this.tempNewMobileNumber = tempNewMobileNumber;
    }

    /**
     * @return the tempNewEmail
     */
    public String getTempNewEmail() {
        return tempNewEmail;
    }

    /**
     * @param tempNewEmail the tempNewEmail to set
     */
    public void setTempNewEmail(String tempNewEmail) {
        this.tempNewEmail = tempNewEmail;
    }

    /**
     * @return the currentUser
     */
    public String getCurrentUser() {
        return currentUser;
    }

    /**
     * @param currentUser the currentUser to set
     */
    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * @return the currentName
     */
    public String getCurrentName() {
        return currentName;
    }

    /**
     * @param currentName the currentName to set
     */
    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }

    /**
     * @return the commentField
     */
    public String getCommentField() {
        return commentField;
    }

    /**
     * @param commentField the commentField to set
     */
    public void setCommentField(String commentField) {
        this.commentField = commentField;
    }
}
