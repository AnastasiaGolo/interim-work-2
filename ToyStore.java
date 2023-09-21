import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ToyStore {
    private List<Toy> prizes;

    public ToyStore() {
        prizes = new ArrayList<>();
    }

    public void addNewToy(int id, String name, int quantity, int weight) {
        Toy toy = new Toy(id, name, quantity, weight);
        prizes.add(toy);
    }

    public void changeWeight(int toyId, int newWeight) {
        for (Toy toy : prizes) {
            if (toy.getId() == toyId) {
                toy.setWeight(newWeight);
                break;
            }
        }
    }

    public Toy selectPrize() {
        Random random = new Random();
        int totalWeight = prizes.stream().mapToInt(Toy::getWeight).sum();
        int randomWeight = random.nextInt(totalWeight);

        int cumulativeWeight = 0;
        for (Toy toy : prizes) {
            cumulativeWeight += toy.getWeight();
            if (randomWeight < cumulativeWeight) {
                prizes.remove(toy);
                return toy;
            }
        }

        return null;
    }

    public void writeToFile(Toy toy, String filepath) {
        try (FileWriter writer = new FileWriter(filepath, true)) {
            writer.write(toy.toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ToyStore toyProgram = new ToyStore();
        toyProgram.addNewToy(1, "Teddy Bear", 10, 50);
        toyProgram.addNewToy(2, "Doll", 15, 30);
        toyProgram.addNewToy(3, "Ball", 5, 20);
        toyProgram.addNewToy(4, "Car", 10, 40);

        Toy prize = toyProgram.selectPrize();
        if (prize != null) {
            toyProgram.writeToFile(prize, "prize.txt");
            System.out.println("Congratulations! You won a " + prize.getName());
        } else {
            System.out.println("No prizes available.");
        }
    }
}