import java.util.*;

public class Genetic {
    //attributes
    static int N = 3, numberOfGenerations, populationSize = 10;
    static double recombinationRate, mutationRate;

    private static double selectionRate = 0.75;

    static Random rand = new Random();

    public Genetic(int n, int numberOfGenerations, int populationSize, double recombinationRate, double mutationRate) {
        N = n;
        this.numberOfGenerations = numberOfGenerations;
        this.populationSize = populationSize;
        this.recombinationRate = recombinationRate;
        this.mutationRate = mutationRate;
    }


    private void algorithm(){

        //first of all, let's create our first population randomly
        ArrayList <String> initialPopulation = create_initial_population();

        //now let's select a parents from our population
        Parent parent = selection(initialPopulation);
    }
////////////////////////////////////////////////////////////////////////////////////
    private static ArrayList<String> create_initial_population(){

        //in this array we save the chromosomes of first population
        ArrayList<String> initial_population = new ArrayList<>();

        //generating chromosomes
        for (int i = 0; i < populationSize; i++) {
            String chromosome = "";
            for (int j = 0; j < N*N; j++) {
                int number = rand.nextInt(N*N);
                if (!chromosome.contains(String.valueOf(number)))
                    chromosome += number;
                else
                    j--;
            }
            initial_population.add(chromosome);
        }
        return initial_population;
    }
//////////////////////////////////////////////////////////////////////////////////////
    private static Parent selection(ArrayList<String> population){
        while (true) {
            int motherIndex = rand.nextInt(N * N);
            int fatherIndex;
            do {
                fatherIndex = rand.nextInt(N * N);
            } while (fatherIndex == motherIndex);
            double parentRate = rand.nextDouble();

            Parent parents = new Parent(population.get(motherIndex), population.get(fatherIndex), parentRate);

            if (parents.parentRate < selectionRate)
                return parents;
        }
    }
//////////////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
        ArrayList<String> ini = create_initial_population();
        System.out.println(ini);
        Parent p = selection(ini);
        System.out.println("mom" + p.mother);
        System.out.println("dad" + p.father);

    }
}
