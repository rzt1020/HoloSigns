package cn.myrealm.holosigns.manager;

/**
 * @program: HoloSigns
 * @description: Managing information and resources
 * @author: rzt1020
 * @create: 2022/09/28
 **/
public class Manager {
    // vars
    public static Manager instance;
    
    /**
     * @Description: Constructor
     * @Param: []
     * @return: 
     * @Author: rzt1020
     * @Date: 2022/9/28
    **/
    public Manager() {
        instance = this;
    }

    /**
     * @Description: Reload the Manager
     * @Param:
     * @return:
     * @Author: rzt1020
     * @Date: 2022/9/28
    **/
    public void reload() {
        new Manager();
    }
}
