
import java.util.Random; //Import class Random - generate a random number in java.
import java.util.Scanner;//In order to get input from the user, importing the Scanner class is needed.

public class Driver {
	private static final Random random = new Random();
	public static void main(String[] args) 
	{
		//Creates a new object that we called (keyIn) in order to take the input from the user.	
		Scanner keyIn = new Scanner (System.in);

		//Displaying the welcome message
		System.out.println("[-------------------------------------------------------]");
		System.out.println("[             WELCOME TO THE BATTLE GAME                ]");
		System.out.println("[-------------------------------------------------------]");

		//Variable declaration
		int creature;

		//This do-while loop is assure that the user will enter a number between 2-8.
		do 
		{
			System.out.print("\nHow many creatures would you like to have (minimum 2, maximum 8)? ");
			creature = keyIn.nextInt();
			if (creature < 2 || creature>8) //If user does not enter a number between 2-8 it will display an error message
			{System.out.println("\n*** Illegal number of creatures requested ***");}
		}while(creature <2 || creature >8);

		Creature[] creatures = new Creature[creature];//Creates an array of the type Creature of the amount of creatures as the length of the array.

		//The purpose of this for loop is to print out the information of all the creatures that are created
		for(int i = 0; i<creature; i++ )
		{
			if(i==0)
			{
				keyIn.nextLine();
			}
			System.out.print("\nWhat is the name of creature " +(i+1) + "? ");
			String name = keyIn.nextLine();
			creatures[i] = new Creature(name);
			System.out.println(creatures[i].toString());
		}

		//variable Declaration	
		int currentCreature = random.nextInt(creature);//This will generate a number between 0 and the number of creatures created by the user.
		int option;


		do {//This do-while loop is to make sure that this will repeat until there is 1 creature alive.
			do {//This do-while loop is to make sure that if user chooses option 1-4 it will repeat
				do {//This do-while loop is to make sure the user inputs a number between 1-6. If not it will repeat.
					System.out.println("\nCreature # " + (currentCreature+1) + ": " +   creatures[currentCreature].getName() + ", what do you want to do?");
					System.out.println("\t1. How many creatures are alive?");
					System.out.println("\t2. See my statuts");
					System.out.println("\t3. See status of all players");
					System.out.println("\t4. Change my name");
					System.out.println("\t5. Work for some food");
					System.out.println("\t6. Attack another creature (Warning! may turn against you)");
					System.out.print("\nYour choice please > ");
					option = keyIn.nextInt();
				}while (option <0 || option>6);

				//Switch statement so the user can decide what he wants to do.
				switch(option) {

				//Display how many creatures are alive
				case 1: 
					System.out.println("\tNumber of creatures alive " + Creature.getNumStillAlive());
					break;
					//Display creature's status
				case 2:
					System.out.println(creatures[currentCreature].toString());
					break;
					//Display all creatures status
				case 3:

					for(int i = 0; i<creature;i++)
					{
						System.out.println(creatures[i].toString());
					}
					break;
					//Option to change creatures name
				case 4:
					System.out.println("\tYour name is currently " +" \""+ creatures[currentCreature].getName()+"\" ");
					System.out.print("\tWhat is the new name: ");
					String newName = keyIn.next();
					creatures[currentCreature].setName(newName);
					break;
					//Option to work for some food
				case 5:
					System.out.println("\nYour status before working for food: " + creatures[currentCreature].showStatus() + " ... You earned " +creatures[currentCreature].earnFood()+ " food units.");

					System.out.println("\nYour status after working for food: " +creatures[currentCreature].showStatus());

					do {//this do-while loop is make sure that it will give turn to next creature and skip turn if the creature is dead
						if(currentCreature == creature -1) {
							currentCreature -= creature -1;
						}else currentCreature++;

					}while(!creatures[currentCreature].isAlive());
					break;
					//Attacking option
				case 6:
					int attackedCreature;
					do {//this do-while loop is to make sure the user chooses another creature to attack and not himself and make sure that the creature he wants to attack is not dead.
						System.out.print("\nWho do you want to attack? (enter a number from 1 to " + creature+ " other than yourself("+ (currentCreature+1) + ")): ");
						attackedCreature = keyIn.nextInt();
						//if enters himself it will display this error message
						if(attackedCreature==(currentCreature+1)) {
							System.out.println("Can't attack yourself silly! Try again... ");
						}
						//If he wants to attack a creature that does not exist it will give an error message
						if(attackedCreature>(creature)||(attackedCreature) <0) 
							System.out.println("That creature does not exist. Try again...");
						//If creature is already dead it will display this error message
						else if(!creatures[(attackedCreature-1)].isAlive())
						{
							System.out.println("\n" + creatures[(attackedCreature-1)].getName() + " is already dead! Try again...");
						}
					}while (attackedCreature==(currentCreature+1) || attackedCreature>(creature) || (attackedCreature)<0 || !creatures[(attackedCreature-1)].isAlive());

					int randNum = random.nextInt(3);// generates a random number 0-2 inclusive.

					//if creature has more than 2 fire power units and the random number is not 0 then it will allow the creature to attack another creature and display status of creature before and after the attack
					if (creatures[currentCreature].getFirePowerUnits()>=2 && randNum !=0)
					{
						System.out.println("\n.... You are attacking " + creatures[(attackedCreature-1)].getName()+"!" );
						System.out.println("Your status before attacking: " + creatures[currentCreature].showStatus());
						creatures[currentCreature].attacking( creatures[attackedCreature-1]);
						System.out.println("Your status after attacking: " +  creatures[currentCreature].showStatus());
						//If you attack creature that has equal or greater than 2 fire power units and you had less than 2 fire power units then you will get attacked by that creature and then it will display the your status before and after the attack
					}else if(creatures[(attackedCreature-1)].getFirePowerUnits()>=2) {

						System.out.print("\nThat was not a good idea ...");

						if(creatures[currentCreature].getFirePowerUnits()<2)
						{
							System.out.println("you only had " + creatures[currentCreature].getFirePowerUnits() + " Fire Power Units!!!");
						}

						System.out.println("\n....... Oh No!!! You are being attacked by " + creatures[(attackedCreature -1)].getName() + "!");

						System.out.println("Your status before attacking: " + creatures[currentCreature].showStatus());
						creatures[(attackedCreature-1)].attacking( creatures[currentCreature]);
						System.out.println("Your status after attacking: " +  creatures[currentCreature].showStatus() + "\n");

						// if both of us dont have 2 fire power units or more then no one will be attacked.
					}else
						System.out.println("Lucky you, the odds were that the other player attacks you, but " + creatures[(attackedCreature-1)].getName() + " doesn't have enough fire power to attack you! So is status quo!!");


					//if attacked creature is dead then it will display that he is dead
					if (!creatures[(attackedCreature-1)].isAlive())
						System.out.println("\n---->" + creatures[(attackedCreature-1)].getName() + " is dead");
					//if attacking creature is dead then it will display the he is dead
					else if (!creatures[currentCreature].isAlive())
						System.out.println("\n---->" + creatures[currentCreature].getName() + " is dead");

					do {//this do-while loop is make sure that it will give turn to next creature and skip turn if the creature is dead
						if(currentCreature == creature -1) {
							currentCreature -= creature -1;
						}else currentCreature++;

					}while(!creatures[currentCreature].isAlive());
					break;

				}

			}while(option==1 || option==2 || option==3 || option==4);
		}while (Creature.getNumStillAlive()>1);		


		System.out.println("\nGAME OVER!!!!!\n");

		//This for loop will make sure to display all creatures status and information
		for(int i = 0; i<creature;i++)
		{
			System.out.println(creatures[i].toString());
		}


		System.out.println("\nThank you for playing THE BATTLE GAME!");

		//closing scanner
		keyIn.close();


		



	}

}
//Thank you for taking a look at this program!	