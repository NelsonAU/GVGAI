package tracks.singlePlayer;

import core.competition.CompetitionParameters;
import core.game.ForwardModel;
import tools.Utils;
import tracks.ArcadeMachine;
import tracks.singlePlayer.advanced.olets.SingleMCTSPlayer;

import java.util.Random;


/**
 * Created with IntelliJ IDEA. User: Diego Date: 04/10/13 Time: 16:29 This is a
 * Java port from Tom Schaul's VGDL - https://github.com/schaul/py-vgdl
 */
public class OletsTest {

    public static void main(String[] args) {

		// Available tracks:
		String sampleRandomController = "tracks.singlePlayer.simple.sampleRandom.Agent";
		String doNothingController = "tracks.singlePlayer.simple.doNothing.Agent";
		String sampleOneStepController = "tracks.singlePlayer.simple.sampleonesteplookahead.Agent";
		String sampleFlatMCTSController = "tracks.singlePlayer.simple.greedyTreeSearch.Agent";

		String sampleMCTSController = "tracks.singlePlayer.advanced.sampleMCTS.Agent";
        String sampleRSController = "tracks.singlePlayer.advanced.sampleRS.Agent";
        String sampleRHEAController = "tracks.singlePlayer.advanced.sampleRHEA.Agent";
		String sampleOLETSController = "tracks.singlePlayer.advanced.olets.Agent";

		//Load available games
		String spGamesCollection =  "examples/all_games_sp.csv";
		String[][] games = Utils.readGames(spGamesCollection);

		//Game settings
		boolean visuals = false;
		int seed = new Random().nextInt();

		// Game and level to play
		int gameIdx = Integer.parseInt(args[0]);
		int levelIdx = Integer.parseInt(args[1]);
		int repetitions = Integer.parseInt(args[2]);
		SingleMCTSPlayer.staticMCTSRolloutDepth = Integer.parseInt(args[3]);
		CompetitionParameters.ACTION_TIME = Integer.parseInt(args[4]);

		String[] argnames = new String[]{"gameIdx", "levelIdx", "repetitions", "depth", "cpu time"};

		for (int i = 0; i < 5; i++) {
			System.err.print(argnames[i]);
			System.err.print(":");
			System.err.println(args[i]);
		}

		String gameName = games[gameIdx][1];
		String game = games[gameIdx][0];
		String level1 = game.replace(gameName, gameName + "_lvl" + levelIdx);

		for (int i = 0; i < repetitions; i++) {
			SingleMCTSPlayer.totalIters = 0;
			ForwardModel.numCalls = 0;
			double[] result = ArcadeMachine.runOneGame(game, level1, false, sampleOLETSController, null, seed, 0);
			String output = String.format("%s, %s, %d, %d, %d : %d, %f, %d, %d, %d",
					game,
					level1,
					repetitions,
					SingleMCTSPlayer.staticMCTSRolloutDepth,
					CompetitionParameters.ACTION_TIME,
					(int)result[0], // result: -100 for disqualified, -1 for no winner, 0 for loss, 1 for win
					result[1],      // score
					(int)result[2], // game length in ticks
					SingleMCTSPlayer.totalIters,
					ForwardModel.numCalls
					);

			System.out.println(output);
		}

		String recordActionsFile = null;// "actions_" + games[gameIdx] + "_lvl"
						// + levelIdx + "_" + seed + ".txt";
						// where to record the actions
						// executed. null if not to save.

		// 1. This starts a game, in a level, played by a human.
		//ArcadeMachine.playOneGame(game, level1, recordActionsFile, seed);

		// 2. This plays a game in a level by the controller.
		//ArcadeMachine.runOneGame(game, level1, visuals, sampleMCTSController, recordActionsFile, seed, 0);


		// 3. This replays a game from an action file previously recorded
	//	 String readActionsFile = recordActionsFile;
	//	 ArcadeMachine.replayGame(game, level1, visuals, readActionsFile);

		// 4. This plays a single game, in N levels, M times :
//		String level2 = new String(game).replace(gameName, gameName + "_lvl" + 1);
//		int M = 10;
//		for(int i=0; i<games.length; i++){
//			game = games[i][0];
//			gameName = games[i][1];
//			level1 = game.replace(gameName, gameName + "_lvl" + levelIdx);
//			ArcadeMachine.runGames(game, new String[]{level1}, M, sampleMCTSController, null);
//		}

		//5. This plays N games, in the first L levels, M times each. Actions to file optional (set saveActions to true).
		//5 times per level as noted in meeting




    }
}

