package org.aapkerala.broomRevolution;

/**
 * Created by mayank raj sinha on 02-03-2017.
 */

public class Member_lac_data {
    private String name;
    private String sector;
    private String lac;
    private String id;


    public Member_lac_data(String name, String sector, String lac, String id){
        this.name=name;
        this.sector=sector;
        this.lac=lac;
        this.id=id;

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

    public String getId() {
        return id;
    }
}
