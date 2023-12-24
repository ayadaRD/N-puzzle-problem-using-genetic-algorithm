import java.util.*;

public class Genetic {

    private static int N;      //The dimension of puzzle (N*N)
    private static int numberOfGenerations;        //Maximum number of generations that algorithm process
    private static int populationSize;     //size of population in every generation
    private static double crossOverRate;
    private static double mutationRate;
    private static String goalState;       //final pattern
    private static final Random rand = new Random();      //obj for generating random numbers
    private static int finalIndex;      //used for finding the proper chromosome in final generation

    public Genetic(int n, int numberOfGenerations, int populationSize, double crossOverRate, double mutationRate, String goalState) {
        N = n;
        Genetic.numberOfGenerations = numberOfGenerations;
        Genetic.populationSize = populationSize;
        Genetic.crossOverRate = crossOverRate;
        Genetic.mutationRate = mutationRate;
        Genetic.goalState = goalState;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////
    //Main function of algorithm
    public void start(){

        boolean finishToken = false;

        //let's create an initial population
        ArrayList<String> initialPopulation = create_initial_population();
        System.out.println("Initial population:");
        System.out.println(initialPopulation);

        if (termination_check(initialPopulation)){
            //that was easy! no need to create another generation
            solved(initialPopulation, 1);
        }
        else {

            for (int i = 0; i < numberOfGenerations; i++) {

                //cross over
                ArrayList<String> temp = crossOver(initialPopulation);

                //mutation
                double mRate = rand.nextDouble();
                if (mRate < mutationRate){
                    Collections.copy(initialPopulation, mutation(temp));
                }
                else {
                    Collections.copy(initialPopulation, temp);
                }

                if (termination_check(initialPopulation)){
                    solved(initialPopulation, i + 2);
                    finishToken = true;
                    break;
                }
                System.out.println("Generation " + (i + 2) + ":  " + initialPopulation);

            }

            if (!finishToken)
                not_solved(initialPopulation, numberOfGenerations);
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////
    private void solved (ArrayList<String> population, int generationNumber){
        System.out.println("\n\nProblem solved in generation " + generationNumber + ".");
        System.out.println("Population of this generation:");
        System.out.println(population);
        System.out.println("Final chromosome:");
        System.out.println(population.get(finalIndex));
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////
    private void not_solved (ArrayList<String> population, int generationNumber){
        System.out.println("\n\nProblem didn't solve after "+generationNumber+" generation!");
        System.out.println("the nearest chromosome to goal state is " + population.get(finalIndex));
        System.out.println("with fitness of " + fitness(population.get(finalIndex)));
        System.out.println("in generation " + generationNumber);
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////
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
//////////////////////////////////////////////////////////////////////////////////////////////////////
    private static Parent selection(ArrayList<String> population){
        while (true) {
            int motherIndex = fitnessList(population).indexOf(Collections.min(fitnessList(population)));
            int fatherIndex;
            do {
                fatherIndex = rand.nextInt(N * N);
            } while (fatherIndex == motherIndex);
            double parentRate = rand.nextDouble();

            Parent parents = new Parent(population.get(motherIndex), population.get(fatherIndex), parentRate);

            //Rates
            double selectionRate = 0.75;
            if (parents.parentRate < selectionRate)
                return parents;
        }
    }

    private static ArrayList<Integer> fitnessList(ArrayList<String> population){
        ArrayList<Integer> fitness = new ArrayList<>();
        for (int i = 0; i < populationSize; i++)
            fitness.add(fitness(population.get(i)));
        return fitness;
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////
    private static ArrayList<String> crossOver(ArrayList<String> population){
        ArrayList<String> newPopulation = new ArrayList<>();
        int boundary = (N*N)/2;


        while (true){
            if (newPopulation.size() >= populationSize)
                break;

            Parent parent = selection(population);
            double rate = rand.nextDouble();
            String motherPart = parent.mother.substring(0, boundary);
            String fatherPart = "";

            if (rate < crossOverRate) {
                for (char i : parent.father.toCharArray()) {
                    if (!motherPart.contains(String.valueOf(i)))
                        fatherPart += i;
                }
                String child = motherPart + fatherPart;
                newPopulation.add(child);
            }
            else {
                if (!newPopulation.contains(parent.father) && !(newPopulation.size() >= populationSize))
                    newPopulation.add(parent.father);
                if (!newPopulation.contains(parent.mother)&& !(newPopulation.size() >= populationSize))
                    newPopulation.add(parent.mother);
            }

        }
        return newPopulation;
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////
    private static ArrayList<String> mutation(ArrayList<String> population){
        int randomIndex = rand.nextInt(populationSize);
        char[] chromosome = population.get(randomIndex).toCharArray();
        int firstIndex = rand.nextInt(N * N);
        int secondIndex = rand.nextInt(N * N);
        char firstChar = chromosome[firstIndex];
        char secondChar = chromosome[secondIndex];
        chromosome[firstIndex] = secondChar;
        chromosome[secondIndex] = firstChar;
        population.set(randomIndex, String.valueOf(chromosome));
        return population;
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////
    public static int fitness(String string){
        char[] chromosome = string.toCharArray();
        char[] goal = goalState.toCharArray();
        int fitness = 0;
        for (int i = 0; i < chromosome.length; i++) {
            if (chromosome[i] != '0'){
                int currentRow = i / N;
                int currentCol = i % N;

                int index = -1;
                for (int j = 0; j < goal.length; j++) {
                    if (goal[j] == chromosome[i]){
                        index = j;
                        break;
                    }
                }

                int targetRow = index / N;
                int targetCol = index % N;

                int distance = Math.abs(currentRow - targetRow) + Math.abs(currentCol - targetCol);
                fitness += distance;
            }
        }
        return fitness;
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////
    private static boolean termination_check(ArrayList<String> population){
        int min = 100000;
        int index = 0;
        for (int i = 0; i < populationSize; i++) {
            int fitness = fitness(population.get(i));
            if (fitness < min){
                min = fitness;
                index = i;
            }
            if (fitness == 0) {
                finalIndex = i;
                return true;
            }
        }
        finalIndex = index;
        return false;
    }
}