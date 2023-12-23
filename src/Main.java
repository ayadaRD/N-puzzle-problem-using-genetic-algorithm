import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int N, numberOfGenerations, populationSize;
        double recombinationRate, mutationRate;
        Scanner cin = new Scanner(System.in);

        System.out.println("*--N puzzle problem using genetic algorithm--*\n");

        System.out.println("What is the dimension of your puzzle? (N)");
        N = cin.nextInt();

        System.out.println("What is the nu,ber of generations?");
        numberOfGenerations = cin.nextInt();

        System.out.println("What is the size of population?");
        populationSize = cin.nextInt();

        System.out.println("What is the rate of recombination?");
        recombinationRate = cin.nextDouble();

        System.out.println("What is the rate of mutation?");
        mutationRate = cin.nextDouble();

    }
}