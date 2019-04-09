package automobileonline.co;

import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "userBean")
@SessionScoped
public class userBean implements Serializable {
    //Bean to manage user functions

    @Inject
    private mainBean mainBean;

    //Temporary storage for sign-in/up functions
    private String name = null;
    private String username = null;
    private String password = null;
    private int mobileNumber = 0;
    private String email = null;

    //Try limit
    List<String> lockedAccounts = new ArrayList<String>();
    private int limitCount = 3;
    private String limitUsername = "";

    //Login user
    //Check inputted values against stored credentials to update current user
    public void signIn(String uName) {
        FacesContext fc = FacesContext.getCurrentInstance();
        ResultSet rs;
        Connection cnt = mainBean.getConnection();

        //Select row where inputted username matches stored username
        //No longer refers to string directly, but with "?", securing against sql injection
        String sql = "SELECT * FROM automobile.CUSTOMERS WHERE USERNAME = ?";

        Boolean accountLockCheck = false;

        //Check if account is locked
        for (String lockedAccount : lockedAccounts) {
            if (mainBean.getTempUsername().equals(lockedAccount)) {
                accountLockCheck = true;
            }
        }

        //If account isn't locked, proceed to login
        if (accountLockCheck == false) {

            try {
                //Prepared statement is set, securing against sql injections
                PreparedStatement stment = cnt.prepareStatement(sql);

                //Setting the string value "uName" into the "?" of the query
                stment.setString(1, uName);
                rs = stment.executeQuery();

                while (rs.next()) {
                    //Extract credentials and data from the row
                    username = rs.getString(1);
                    name = rs.getString(2);
                    password = rs.getString(3);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //Check if the username and password matches stored credentials
            if (mainBean.getTempUsername().equalsIgnoreCase(username) && mainBean.getTempPassword().equalsIgnoreCase(password)) {

                //If matches, proceed to login
                try {
                    //Set current user
                    mainBean.setCurrentUser(username);
                    mainBean.setCurrentName(name);
                    limitUsername = "";

                    //Clear sign-in fields
                    mainBean.setTempUsername(null);
                    mainBean.setTempPassword(null);

                    //Redirect back to index
                    fc.getExternalContext().redirect("index.xhtml");
                } catch (Exception e) {
                }
                //If credentials don't match, engage try limit procedures
            } else {
                //If current limit username doesn't match, reset
                if (!limitUsername.equals(mainBean.getTempUsername())) {
                    limitCount = 3;
                    limitUsername = mainBean.getTempUsername();
                }
                //If try limit reached, lock the account
                if (limitCount == 0) {
                    lockedAccounts.add(mainBean.getTempUsername());
                    fc.addMessage(null, new FacesMessage("Your account has been locked. Please contact an administrator."));
                } else {
                    fc.addMessage(null, new FacesMessage("Incorrect Username or Password. You have " + limitCount + " tries left."));
                }
                limitCount--;
            }
        } else {
            fc.addMessage(null, new FacesMessage("Your account has been locked. Please contact an administrator."));
        }
    }

    //Sign user out
    //Clear currently saved user
    public void signOut() throws IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        mainBean.setCurrentUser(null);
        mainBean.setCurrentName(null);

        //Redirect back to index and display a confirmation
        fc.getExternalContext().redirect("index.xhtml");
        fc.addMessage(null, new FacesMessage("You have been signed out."));
    }

    //Sign user up (register)
    //Check if inputted username doesn't exist and insert data into the database
    public void signUp() throws IOException, SQLException {
        FacesContext fc = FacesContext.getCurrentInstance();
        Connection cnt = mainBean.getConnection();
        ResultSet rs;

        //Insert all inputted data and credentials into the database
        //Query is modified to use "?" as a reference, securing against sql injection
        String insertUserSQL = "INSERT INTO automobile.CUSTOMERS (username, customer_name, password, mobile_number, email) values (?, ?, ?, ?, ?)";

        //Look for inputted username in the database
        String checkUserSQL = "SELECT * FROM automobile.CUSTOMERS WHERE username = ? ";

        //Check if inputted passwords match and if they fit the policy
        if ((mainBean.getTempNewPassword().equals(mainBean.getTempNewPasswordCheck()))
                && //is less than or equal to 16 in length
                (mainBean.getTempNewPassword().length() <= 16)
                && //is grater than or equal to 8 in length
                (mainBean.getTempNewPassword().length() >= 8)
                && //contains a capital letter
                (!mainBean.getTempNewPassword().equals(mainBean.getTempNewPassword().toLowerCase()))) {

            Boolean check = false;
            try {
                //Create a prepared statement to secure agains sql injection
                PreparedStatement stment = cnt.prepareStatement(checkUserSQL);

                //Set the string to refer to the "?" in the query
                stment.setString(1, mainBean.getTempNewUsername());
                rs = stment.executeQuery();

                if (rs.next()) {
                    //If row isn't empty, set check to true
                    check = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //If check is still false, username is free. Proceed to register user
            if ((check == false) && (!mainBean.getTempNewUsername().equals(""))) {
                try {
                    //Create a prepared statement to prevent sql injections
                    PreparedStatement stment = cnt.prepareStatement(insertUserSQL);

                    //Set all the variables so they can be used in the query as "?"
                    stment.setString(1, mainBean.getTempNewUsername());
                    stment.setString(2, mainBean.getTempNewName());
                    stment.setString(3, mainBean.getTempNewPassword());
                    stment.setInt(4, mainBean.getTempNewMobileNumber());
                    stment.setString(5, mainBean.getTempNewEmail());
                    stment.executeUpdate();

                    //Set current user (automatically log them in)
                    mainBean.setCurrentUser(mainBean.getTempNewUsername());
                    mainBean.setCurrentName(mainBean.getTempNewName());

                    //Clear register fields
                    mainBean.setTempNewUsername(null);
                    mainBean.setTempNewName(null);
                    mainBean.setTempNewPassword(null);
                    mainBean.setTempNewPasswordCheck(null);
                    mainBean.setTempNewMobileNumber(0);
                    mainBean.setTempNewEmail(null);

                    //Redirect back to index
                    fc.getExternalContext().redirect("index.xhtml");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                //If username taken or empty, display error message
            } else {
                fc.addMessage(null, new FacesMessage("Username is invalid or already taken, please try again."));
            }
            //If password does not match or is invalid, display error message
        } else {
            fc.addMessage(null, new FacesMessage("The password is invalid or does not match. "
                    + "It must be longer than 8 characters, not longer than 16, contain a letter/number, and have a capital letter. "
                    + "Please try again."));
        }
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the mobile number
     */
    public int getMobileNo() {
        return mobileNumber;
    }

    /**
     * @param mobileNumber the mobile number to set
     */
    public void setMobileNo(int mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
