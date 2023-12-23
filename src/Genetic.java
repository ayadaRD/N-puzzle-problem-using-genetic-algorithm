public class Genetic {
    //attributes
    int N, numberOfGenerations, populationSize;
    double recombinationRate, mutationRate;

    public Genetic(int n, int numberOfGenerations, int populationSize, double recombinationRate, double mutationRate) {
        N = n;
        this.numberOfGenerations = numberOfGenerations;
        this.populationSize = populationSize;
        this.recombinationRate = recombinationRate;
        this.mutationRate = mutationRate;
    }



    //this function randomly generates initial population
    private String create_initial_population(){
        String[] initialPopulation = new String[populationSize];
        return "";
    }

}
