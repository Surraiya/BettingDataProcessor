import betting.BettingProcessor;


public class Main {
    public static void main(String[] args){
        BettingProcessor bettingProcessor = new BettingProcessor();
        bettingProcessor.processMatchDataFile("src/main/resources/match_data.txt");
        bettingProcessor.processPlayerDataFile("src/main/resources/player_data.txt");
        bettingProcessor.generateResultFile("src/main/java/result.txt");
    }
}
