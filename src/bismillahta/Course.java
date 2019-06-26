/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bismillahta;

import java.util.ArrayList;

/**
 *
 * @author Umar Rizki
 */
public class Course {
    private ArrayList<Config> listConfig = new ArrayList<>();
    private int courseId;
    
    Course(int courseId){
        this.courseId = courseId;
    }
    
    void addConfig(Config a){
        getListConfig().add(a);
    }

    /**
     * @return the listConfig
     */
    public ArrayList<Config> getListConfig() {
        return listConfig;
    }

    /**
     * @param listConfig the listConfig to set
     */
    public void setListConfig(ArrayList<Config> listConfig) {
        this.listConfig = listConfig;
    }

    /**
     * @return the courseId
     */
    public int getCourseId() {
        return courseId;
    }

    /**
     * @param courseId the courseId to set
     */
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    
    
}
