package org.aapkerala.broomRevolution;

/**
 * Created by mayank raj sinha on 26-02-2017.
 */

public class MemberData {

    private String name;
    private String sector;
    private String lac;


    public MemberData(String name, String sector, String lac){
        this.name=name;
        this.sector=sector;
        this.lac=lac;

    }

    public String getName() {
        return name;
    }

    public String getSector() {
        return sector;
    }

    public String getLac() {
        return lac;
    }



}
