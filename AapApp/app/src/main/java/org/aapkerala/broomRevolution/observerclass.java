package org.aapkerala.broomRevolution;

/**
 * Created by GOTHAM on 12-03-2017.
 */

/**
 * Created by mayank raj sinha on 10-03-2017.
 */

public class observerclass {
    private String name;
    private String pc_name;
    private String number;

    public observerclass(String name,String pc_name,String number){
        this.name=name;
        this.pc_name=pc_name;
        this.number=number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getPc_name() {
        return pc_name;
    }
}