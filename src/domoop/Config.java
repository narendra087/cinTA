package domoop;

import java.util.ArrayList;

public class Config {
    private ArrayList<Subpart> listSubpart = new ArrayList<>();
    private int configId;
    
    Config(int configId){
        this.configId = configId;
    }
    
    void addSubpart(Subpart a){
        getListSubpart().add(a);
    }

    /**
     * @return the listSubpart
     */
    public ArrayList<Subpart> getListSubpart() {
        return listSubpart;
    }

    /**
     * @param listSubpart the listSubpart to set
     */
    public void setListSubpart(ArrayList<Subpart> listSubpart) {
        this.listSubpart = listSubpart;
    }

    /**
     * @return the configId
     */
    public int getConfigId() {
        return configId;
    }

    /**
     * @param configId the configId to set
     */
    public void setConfigId(int configId) {
        this.configId = configId;
    }
    
}
