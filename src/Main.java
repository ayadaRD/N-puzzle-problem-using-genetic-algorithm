import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int N, numberOfGenerations, populationSize;
        double recombinationRate, mutationRate;
        String goal;
        Scanner cinInt = new Scanner(System.in);
        Scanner cinStr = new Scanner(System.in);

        System.out.println("*--N puzzle problem using genetic algorithm--*\n");

        System.out.println("What is the dimension of your puzzle? (N)");
        N = cinInt.nextInt();

        System.out.println("What is the number of generations?");
        numberOfGenerations = cinInt.nextInt();

        System.out.println("What is the size of population?");
        populationSize = cinInt.nextInt();

        System.out.println("What is the rate of recombination?");
        recombinationRate = cinInt.nextDouble();

        System.out.println("What is the rate of mutation?");
        mutationRate = cinInt.nextDouble();

        System.out.print("Enter your desired final pattern: ");
        goal = cinStr.nextLine();

        Genetic algorithm = new Genetic(N, numberOfGenerations, populationSize, recombinationRate, mutationRate, goal);
        //Genetic algorithm = new Genetic(3,100,10,0.75,0.5,"123456780");
        algorithm.start();


    }
}