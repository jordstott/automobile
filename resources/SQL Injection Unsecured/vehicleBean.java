package automobileonline.co;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "vehicleBean")
@Dependent
public class vehicleBean {
    //Bean to manage vehicle functions
    
    @Inject
    private mainBean mainBean;

    //Vehicle object variables
    private int id;
    private String name;
    private String type;
    private Boolean availability;

    public vehicleBean() {
    }

    //Get all vehicles from the database and return a list
    public List<vehicleBean> getVehicles() {
        ResultSet rs;
        Connection cnt = mainBean.getConnection();
        
        //Select all vehicle rows
        String sql = "SELECT * FROM DB_ADMIN.VEHICLES";
        List<vehicleBean> vehicleList = new ArrayList<>();

        try {
            Statement stment = cnt.createStatement();
            stment.executeQuery(sql);
            rs = stment.getResultSet();

            while (rs.next()) {
                //Create a vehicle object and add to list
                vehicleBean vehicle = new vehicleBean();
                vehicle.setId(rs.getInt(1));
                vehicle.setName(rs.getString(2));
                vehicle.setType(rs.getString(3));
                vehicle.setAvailability(rs.getBoolean(4));
                vehicleList.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicleList;
    }

    //Rent a vehicle
    //Check if availability is true and update it, then store the rent log
    public void rentVehicle(int vehicleID) {
        FacesContext fc = FacesContext.getCurrentInstance();

        //Get current date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.now();

        ResultSet rs;
        Connection cnt = mainBean.getConnection();

        //Select vehicle where chosen id matches
        String sql = "SELECT * FROM DB_ADMIN.VEHICLES WHERE VEHICLE_ID = " + vehicleID + "";
        
        //Set chosen vehicle's availability to false
        String sql2 = "UPDATE DB_ADMIN.VEHICLES SET availability = 'false' WHERE VEHICLE_ID = " + vehicleID + "";
        
        //Add a rent log to the rent table
        String sql3 = "INSERT INTO DB_ADMIN.RENTS (Vehicle_ID, Date, username) values (" + vehicleID + ", '"
                + dtf.format(localDate) + "', '" + mainBean.getCurrentUser() + "')";

        Boolean check = false;
        String vehicleBrand = "";

        try {
            Statement stment = cnt.createStatement();
            stment.execute(sql);
            rs = stment.getResultSet();

            while (rs.next()) {
                //Get vehicle brand and availability
                vehicleBrand = rs.getString(2);
                check = Boolean.parseBoolean(rs.getString(4));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //If vehicle is free, proceed to rent
        if (check == true) {
            try {
                Statement stment1 = cnt.createStatement();
                stment1.executeUpdate(sql2);
                Statement stment2 = cnt.createStatement();
                stment2.executeUpdate(sql3);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //Display rent confirmation
            fc.addMessage(null, new FacesMessage("You have successfully rented a " + vehicleBrand + "!"));
        //If vehicle is taken, display error
        } else {
            fc.addMessage(null, new FacesMessage("This " + vehicleBrand + " is currently unavailable."));
        }
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the availability
     */
    public Boolean getAvailability() {
        return availability;
    }

    /**
     * @param availability the availability to set
     */
    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }
}
