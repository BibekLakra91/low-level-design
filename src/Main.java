import java.util.*;
import java.io.*;
class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}

class MagicalArena{

    public String result(Player p1,Player p2){
        int h1=p1.getHealth(),h2=p2.getHealth();
        if(h1>0 && h2>0) return "Match unfinished";
        else if(h1>0)  return p1.getName()+" is the winner!";
        else return p2.getName()+" is the winner!";
    }
    public String summary(Player attacker,Player defender,int damage){
        if(damage>0) return attacker.getName()+" broke the defense and did "+damage+" damage.";
        else return defender.getName()+" defended the attack.";
    }
    public boolean turn(Player p1,Player p2){
        boolean player1Turn;
        if (p1.getHealth() < p2.getHealth()) {
            player1Turn = true;
        } else if (p2.getHealth() < p1.getHealth()) {
            player1Turn = false;
        } else {
            // equal health check() If both have equal health, simulate a toss
            player1Turn = new Random().nextBoolean();
        }
        return player1Turn;
    }
    public void simulate_fight(Player p1, Player p2){
        Scanner sc = new Scanner(System.in);
        int round=1;
        Player attacker, defender;
        boolean flag=turn(p1,p2);//true=>p1 is attacker, false=>p2 is attacker

        while(p1.isAlive() && p2.isAlive()){
            System.out.println("Want to proceed?(Y/N)");
            char response=sc.next().charAt(0);
            if(response!='y' && response!='Y') break;
            if (flag) {
                attacker = p1;
                defender = p2;
            } else {
                attacker = p2;
                defender = p1;
            }
            int attackerRoll = attacker.rollDice();
            int defenderRoll = defender.rollDice();

            int damage = attacker.getAttack() * attackerRoll;
            int defense = defender.getStrength() * defenderRoll;
            int damageTaken = Math.max(0, damage - defense);
            defender.takeDamage(damageTaken);

            System.out.println("Round#"+round+" Summary:");
            System.out.println(summary(attacker,defender,damageTaken));
            attacker.show(attackerRoll,"Attacker");
            defender.show(defenderRoll,"Defender");
            System.out.println("----------------------------------------");
            flag=!flag;
            round++;
        }
        System.out.print("Result: ");
        System.out.println(result(p1,p2));
    }
    public void test_show(Player p,PrintWriter outputWriter){
        outputWriter.println(p.getName()+":___________");
        outputWriter.println("Health: "+p.getHealth());
        outputWriter.println("Strength: "+p.getStrength());
        outputWriter.println("Attack: "+p.getAttack());
        outputWriter.print("\n");
    }
    public void test_show(Player p, int roll,String turn,PrintWriter outputWriter){
        outputWriter.println("-----------------------");
        outputWriter.println(turn+" Details");
        outputWriter.println("-----------------------");
        outputWriter.println("Name: "+p.getName());
        outputWriter.println("Roll: "+roll);
        outputWriter.println("Health: "+p.getHealth());
        outputWriter.println("Strength: "+p.getStrength());
        outputWriter.println("Attack: "+p.getAttack());
    }
    public void test_fight(Player p1, Player p2,PrintWriter outputWriter){
        int round=1;
        Player attacker, defender;
        boolean flag=turn(p1,p2);//true=>p1 is attacker, false=>p2 is attacker
        while(p1.isAlive() && p2.isAlive()){
            if (flag) {
                attacker = p1;
                defender = p2;
            } else {
                attacker = p2;
                defender = p1;
            }
            int attackerRoll = attacker.rollDice();
            int defenderRoll = defender.rollDice();
            int damage = attacker.getAttack() * attackerRoll;
            int defense = defender.getStrength() * defenderRoll;
            int damageTaken = Math.max(0, damage - defense);
            defender.takeDamage(damageTaken);
            outputWriter.println("Round#"+round+" Summary:");
            outputWriter.println(summary(attacker,defender,damageTaken));
            test_show(attacker,attackerRoll,"Attacker",outputWriter);
            test_show(defender,defenderRoll,"Defender",outputWriter);
            outputWriter.println("----------------------------------------");
            flag=!flag;
            round++;
        }
        outputWriter.println("Result: ");
        outputWriter.println(result(p1,p2));
    }
    public void test(int n){
        for (int i = 1; i <= n; i++) { // Assuming you have n input files, update as needed
            String inputFilePath = "src/data/input" + i + ".txt";
            String outputFilePath = "src/data/output" + i + ".txt";
            try {
                Scanner fileScanner = new Scanner(new File(inputFilePath));
                PrintWriter outputWriter = new PrintWriter(new File(outputFilePath));
                Player p1 = new Player(
                        fileScanner.next(),
                        fileScanner.nextInt(),
                        fileScanner.nextInt(),
                        fileScanner.nextInt()
                );

                Player p2 = new Player(
                        fileScanner.next(),
                        fileScanner.nextInt(),
                        fileScanner.nextInt(),
                        fileScanner.nextInt()
                );
                test_show(p1,outputWriter);
                test_show(p2,outputWriter);
                System.out.println("Output"+i+" generated");
                // Captured the result and write it to the output file
                test_fight(p1,p2,outputWriter);

                fileScanner.close();
                outputWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
class Player {
    private String name;
    private int health;
    private int attack;
    private int strength;
    public String getName(){
        return this.name;
    }
    public void setName(String Name){
        this.name=Name;
    }
    public int getHealth(){
        return this.health;
    }
    public void setHealth(int Health){
        this.health=Health;
    }
    public int getAttack(){
        return this.attack;
    }
    public void setAttack(int Attack ){
        this.attack=Attack;
    }
    public int getStrength(){
        return this.strength;
    }
    public void setStrength(int Strength){
        this.strength=Strength;
    }

    Player (String Name, int Health, int Strength, int Attack) throws InvalidInputException{
        this.setName(Name);
        this.setHealth(Health);
        if (Health <= 0) {
            throw new InvalidInputException("Health must be a positive integer.");
        }
        this.setStrength(Strength);
        if (Strength <= 0) {
            throw new InvalidInputException("Strength must be a positive integer.");
        }
        this.setAttack(Attack);
        if (Attack <= 0) {
            throw new InvalidInputException("Attack must be a positive integer.");
        }
    }
    //Constructor overloading
     Player() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Name:");
        this.setName(sc.nextLine());

         // Input validation
         do {
             System.out.print("Health:");
             int Health = sc.nextInt();
             if (Health <= 0) {
                 System.out.println("Health must be a positive integer. Please enter a valid value.");
             } else {
                 this.setHealth(Health);
                 break;
             }
         } while (true);

         do {
             System.out.print("Strength:");
             int Strength = sc.nextInt();
             if (Strength <= 0) {
                 System.out.println("Strength must be a positive integer. Please enter a valid value.");
             } else {
                 this.setStrength(Strength);
                 break;
             }
         } while (true);

         do {
             System.out.print("Attack:");
             int Attack = sc.nextInt();
             if (Attack <= 0) {
                 System.out.println("Attack must be a positive integer. Please enter a valid value.");
             } else {
                 this.setAttack(Attack);
                 break;
             }
         } while (true);
    }

    public void show(){
        System.out.println(this.getName()+":___________");
        System.out.println("Health: "+this.getHealth());
        System.out.println("Strength: "+this.getStrength());
        System.out.println("Attack: "+this.getAttack());
        System.out.print("\n");
    }

    public void show(int roll,String turn){
        System.out.println("-----------------------");
        System.out.println(turn+" Details");
        System.out.println("-----------------------");
        System.out.println("Name: "+this.getName());
        System.out.println("Roll: "+roll);
        System.out.println("Health: "+this.getHealth());
        System.out.println("Strength: "+this.getStrength());
        System.out.println("Attack: "+this.getAttack());
    }
    public int rollDice() {
        Random random = new Random();
        return random.nextInt(6) + 1; // A 6-sided dice
    }
    public void takeDamage(int damage){
        if(damage>0) this.setHealth(this.getHealth()-damage);
        if(this.getHealth()<0) this.setHealth(0);
    }
    public boolean isAlive() {
        return this.getHealth() > 0;
    }
}
//Testing code
public class Main{
    public static void main(String[] args) {
//        System.out.println("P1");
//        Player p1= new Player(); // player 1
//        System.out.print("\n");
//        System.out.println("P2");
//        Player p2= new Player(); // player 2
//        p1.show();
//        p2.show();
        MagicalArena arena = new MagicalArena();
        arena.test(3);
//        arena.simulate_fight(p1, p2);
    }
}
