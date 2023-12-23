import java.util.*;

public class Genetic {
    //attributes
    static int N, numberOfGenerations, populationSize;
    static double recombinationRate, mutationRate;

    public Genetic(int n, int numberOfGenerations, int populationSize, double recombinationRate, double mutationRate) {
        N = n;
        this.numberOfGenerations = numberOfGenerations;
        this.populationSize = populationSize;
        this.recombinationRate = recombinationRate;
        this.mutationRate = mutationRate;
    }



    //this function randomly generates initial population
    private static ArrayList<String> create_initial_population(){

        //in this array we save the chromosomes of first population
        ArrayList<String> initial_population = new ArrayList<>();

        Random rand = new Random();

        //generating chromosomes
        for (int i = 0; i < populationSize; i++) {
            String chromosome = "";
            for (int j = 0; j < N; j++) {
                int number = rand.nextInt(N);
                if (!chromosome.contains(String.valueOf(number)))
                    chromosome += number;
                else
                    j--;
            }
            initial_population.add(chromosome);
        }
        return initial_population;
    }

}
