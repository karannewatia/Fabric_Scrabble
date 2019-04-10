public class App {

    public static void main(String[] args) {

        System.out.println(" ");
        System.out.println("Let's play some Scrabble!!");
        System.out.println("----------------");
        System.out.println(" ");

        Scrabble scrabble = new Scrabble();
        scrabble.joinGame();
        scrabble.startGame();

        scrabble.gameLoop();

    }

}
