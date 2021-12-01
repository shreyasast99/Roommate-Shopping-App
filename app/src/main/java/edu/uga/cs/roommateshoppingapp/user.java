package edu.uga.cs.roommateshoppingapp;

/**
 * This class (a POJO) represents a single user, including the id, user name
 * and amount spent so far.
 * The id is -1 if the object has not been persisted in the database yet, and
 * the db table's primary key value, if it has been persisted.
 */
public class user {

    private long id;
    private String userName;
    private float spent;

    public user(){
        this.id = -1;
        this.userName = "";
        this.spent = 0.00F;
    }

    public user( String userName, float spent) {
        this.userName = userName;
        this.spent = spent;
    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }
    public String getName()
    {
        return userName;
    }

    public void setName(String userName)
    {
        this.userName = userName;
    }
    public float getSpent()
    {
        return spent;
    }

    public void setSpent(float spent)
    {
        this.spent = spent;
    }

}
