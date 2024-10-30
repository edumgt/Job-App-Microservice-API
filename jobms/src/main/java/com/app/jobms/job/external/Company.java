package com.app.jobms.job.external;


public class Company {
    private Long id;
    private String name;
    private String description;

//    private List<Review> review;

    public Company(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }


    public Company() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
