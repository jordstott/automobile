package automobileonline.co;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

@Named(value = "commentBean")
@SessionScoped
public class commentBean implements Serializable {
    //Bean to manage the comment box at the bottom of the index page
    
    @Inject
    private mainBean mainBean;

    //Comment object
    private String username;
    private String text;

    //Add comment by saving it to the database
    public void addComment() {
        Connection cnt = mainBean.getConnection();
        
        //Save current username alongside the text
        String sql = "INSERT INTO DB_ADMIN.COMMENTS (username, text) values ('"
                + mainBean.getCurrentUser() + "', '" + mainBean.getCommentField() + "')";

        try {
            Statement stment = cnt.createStatement();
            stment.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Clear comment input box
        mainBean.setCommentField(null);
    }

    //Get all comments and display them on the index page
    public List<commentBean> getComments() {
        ResultSet rs;
        Connection cnt = mainBean.getConnection();
        
        //Select all comments
        String sql = "SELECT * FROM DB_ADMIN.COMMENTS";
        List<commentBean> commentList = new ArrayList<>();

        try {
            Statement stment = cnt.createStatement();
            stment.execute(sql);
            rs = stment.getResultSet();

            while (rs.next()) {
                //Make a comment object and add it to the list
                commentBean comment = new commentBean();
                comment.setUsername(rs.getString(2));
                comment.setText(rs.getString(3));
                commentList.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commentList;
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
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }
}
