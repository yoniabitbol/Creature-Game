import java.util.Date; //Import class Date - represents a specific instant in time, with millisecond precision.
import java.util.Random; //Import class Random - generate a random number in java.

public class Creature {

	//Attributes Declaration (Data Members)
	private static final int FOOD2HEALTH = 6; //constant that converts food to health
	private static final int HEALTH2POWER = 4; //constant that converts health to power
	private String name; //Creatures name
	private int foodUnits; //Number of food
	private int healthUnits; //Number of health 
	private int firePowerUnits; //Number of fire power
	private Date dateCreated; // Date of creation of creature
	private Date dateDied; // date of death of creature
	private static int numStillAlive = 0;//number of creatures alive
	private static final Random random = new Random();//This will generate a random number

	//Constructor
	public Creature(String name)	
	{

		numStillAlive++; //increment the number of creatures that are alive.
		this.name =name; //creature's name will be stored in instance variable "this.name" 
		foodUnits = random.nextInt(12) + 1; //generates a random number between 1-12 inclusive
		healthUnits = random.nextInt(7) + 1; // generates a random number between 1-7 inclusive
		firePowerUnits = random.nextInt(11); //generates a random number between 0-10 inclusive 
		dateCreated = new Date();//creates current date of the creature
		dateDied = null;//this instance variable will be set to null
		normalizeUnits();//this method will normalize food health and fire power units (see normalizeUnits())


	}

	//**PUBLIC ACCESSOR METHOD**//

	public String getName() 
	{
		return name;
	}

	public int getHealthUnits() 
	{
		return healthUnits;
	}

	public int getFoodUnits() 
	{
		return foodUnits;
	}


	public static int getNumStillAlive()
	{
		return numStillAlive;

	}

	public Date getDateCreated()
	{
		return dateCreated;
	}

	public Date getDateDied()
	{
		return dateDied;

	}

	public int getFirePowerUnits()
	{
		return firePowerUnits;
	}


	//**PUBLIC MUTATOR METHOD**//

	public void setName(String name) 
	{
		this.name = name;
	}

	public void setHealthUnits(int healthUnits) 
	{
		this.healthUnits = healthUnits;
	}


	public void setFoodUnit(int foodUnits) 
	{
		this.foodUnits = foodUnits;
	}

	//This method is to reduce the fire power units by integer n
	public void reduceFirePowerUnits(int n)	
	{
		firePowerUnits-=n;

	}

	//Method that declares that the creature is alive
	public boolean isAlive()
	{
		return dateDied == null;
	}

	//Method that makes sure that the creature earns some food.
	public int earnFood()
	{
		int foodEarned;

		foodEarned = random.nextInt(16);//generates a number of foodEarned between 0-15 inclusive
		this.foodUnits += foodEarned;//foodUnits will then be the amount of foodEarned + the foodUnits that the creature will have.
		normalizeUnits();
		return foodEarned;
	}

	//Method that will store the effect of an attack
	public void attacking(Creature player)
	{
		foodUnits += ((player.foodUnits+1))/2; //The attacking creature gets 50% of the other creature�s food units
		player.foodUnits -= ((player.foodUnits+1))/2;//The attacked creature lost 50% of his food units

		healthUnits += ((player.healthUnits+1))/2;////The attacking creature gets 50% of the other creature�s health units 
		player.healthUnits -= ((player.healthUnits+1))/2;//The attacked creature lost 50% of his health units

		reduceFirePowerUnits(2);//reduce fire power units by 2

		player.healthFoodUnitsZero();//determine whether the creature being attacked is dead after the attck

		normalizeUnits();//this method will normalize food health and fire power units (see normalizeUnits())

	}

	//Method that will determine if the creature is dead.
	public boolean healthFoodUnitsZero() 
	{
		//If health and food units are 0 then the creature is dead otherwise false.
		if(healthUnits==0 && foodUnits==0)
		{
			if(dateDied==null) 
			{
				died();
			}
			return true;
		}

		return false;
	}

	//Method that will determine the date of the death of creature 
	private void died()
	{
		dateDied = new Date();
		numStillAlive--;//number of creatures alive will decrement
	}

	//Method that will return a general descriptive information
	public String toString()
	{
		//If player is alive then it will return this message
		if(dateDied==null)
		{
			return "\nFood units\tHealth units\tFire power units\tName\n" + 
					"----------\t------------\t----------------\t----\n" + 
					getFoodUnits() + "\t\t" + getHealthUnits() + "\t\t" + getFirePowerUnits() + "\t\t\t" + getName() + "\n"
					+ "Date created: " + getDateCreated() + "\nDate died: is still alive";
			//If player is dead then it will return this message
		}else
			return "Food units\tHealth units\tFire power units\tName\n" + 
			"----------\t------------\t----------------\t----\n" + 
			getFoodUnits() + "\t\t" + getHealthUnits() + "\t\t" + getFirePowerUnits() + "\t\t\t" + getName() + "\n"
			+ "Date created: " + getDateCreated() + "\nDate died: " + getDateDied();
	}

	//Method that will show the status of the creature.
	public String showStatus()
	{
		return getFoodUnits() + " food units, "+ getHealthUnits()+" health units, " + getFirePowerUnits() + " fire power units";
	}

	//Method that will convert units when needed.This will convert each 6 units of food to a health unit and each 4 health units to a fire power unit.
	private void normalizeUnits()
	{
		healthUnits += foodUnits/FOOD2HEALTH;
		foodUnits %= FOOD2HEALTH; 
		if (foodUnits == 0) { 
			while (healthUnits > HEALTH2POWER ) { 
				healthUnits -= HEALTH2POWER;
				firePowerUnits++;
			}
		}else { 
			firePowerUnits += healthUnits/HEALTH2POWER; 
			healthUnits %= HEALTH2POWER; 
		}
	}

}


//Thank you for taking a look at this program!	


