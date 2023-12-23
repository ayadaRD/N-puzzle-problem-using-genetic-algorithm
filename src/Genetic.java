import java.util.*;

public class Genetic {
    //attributes
    static int N = 3, numberOfGenerations, populationSize = 10;
    static double crossOverRate=0.75, mutationRate;

    static double selectionRate = 0.75;

    static Random rand = new Random();

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

    public static void main(String[] args) {

        ArrayList<String> ini = create_initial_population();
        System.out.println(ini);

        ArrayList<String> new_p = crossOver(ini);
        System.out.println(new_p);


    }
}
