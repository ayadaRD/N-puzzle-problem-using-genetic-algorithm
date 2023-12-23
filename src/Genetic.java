import java.util.*;

public class Genetic {
    //attributes
    static int N = 3, numberOfGenerations, populationSize = 10;
    static double crossOverRate=0.75, mutationRate;

    static double selectionRate = 0.75;

    static Random rand = new Random();

    static String goalState = "012345678";

    //public Genetic(int n, int numberOfGenerations, int populationSize, double crossOverRate, double mutationRate) {
    //    N = n;
    //    this.numberOfGenerations = numberOfGenerations;
    //    this.populationSize = populationSize;
    //    this.crossOverRate = crossOverRate;
    //    this.mutationRate = mutationRate;
    //}


    private void algorithm(){}

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

    private static ArrayList<String> crossOver(ArrayList<String> population){
        ArrayList<String> newPopulation = new ArrayList<>();
        int boundary = (N*N)/2;

        do {

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
                if (!newPopulation.contains(child))
                    newPopulation.add(child);
            } else {
                if (!newPopulation.contains(parent.father))
                    newPopulation.add(parent.father);
                if (!newPopulation.contains(parent.mother))
                    newPopulation.add(parent.mother);
            }

        } while (newPopulation.size() != populationSize);

        return newPopulation;
    }

    private static void mutation(ArrayList<String> population){
        double rate = rand.nextDouble();
        if (rate < crossOverRate) {
            int randomIndex = rand.nextInt(populationSize);
            char[] chromosome = population.get(randomIndex).toCharArray();
            int firstIndex = rand.nextInt(N * N);
            int secondIndex = rand.nextInt(N * N);
            char firstChar = chromosome[firstIndex];
            char secondChar = chromosome[secondIndex];
            chromosome[firstIndex] = secondChar;
            chromosome[secondIndex] = firstChar;
            population.set(randomIndex, String.valueOf(chromosome));
        }
    }

    private static int fitness(String string){
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
    static ArrayList<String> ini;
    static ArrayList<String> new_p;
    public static void main(String[] args) {

        ini = create_initial_population();
        System.out.println(ini);

        new_p = crossOver(ini);
        System.out.println(new_p);

        mutation(new_p);
        System.out.println(new_p);

        System.out.println(fitness("123456780"));

    }
}
