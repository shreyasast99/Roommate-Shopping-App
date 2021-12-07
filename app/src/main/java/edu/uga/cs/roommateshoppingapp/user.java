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

    /**
     * Default constructor
     */
    public user(){
        this.id = -1;
        this.userName = "";
        this.spent = 0.00F;
    }

    /**
     * Constructor when the name and amount spent has been provided
     * @param userName
     * @param spent
     */
    public user( String userName, float spent) {
        this.userName = userName;
        this.spent = spent;
    }

    /**
     * Get ID
     * @return
     */
    public long getId()
    {
        return id;
    }

    /**
     * Set ID
     * @param id
     */
    public void setId(long id)
    {
        this.id = id;
    }

    /**
     * Get name
     * @return
     */
    public String getName()
    {
        return userName;
    }

    /**
     * Set name
     * @param userName
     */
    public void setName(String userName)
    {
        this.userName = userName;
    }

    /**
     * Get Spent
     * @return
     */
    public float getSpent()
    {
        return spent;
    }

    /**
     * Set Spent
     * @param spent
     */
    public void setSpent(float spent)
    {
        this.spent = spent;
    }

}
