public class ThreePrisonersDilemma {

	/* ===========================================
	 * 学生测试区域 - 在这里添加你的策略进行测试
	 * ===========================================
	 *
	 * 使用说明：
	 * 1. 将你的策略文件（如 GongZeruiPlayer.java）放在同一目录下
	 * 2. 修改 numPlayers 的值（增加1）
	 * 3. 在 switch 语句中添加一个新的 case
	 * 4. 运行这个文件，查看结果（右上角点击运行或使用命令行运行）
	 *
	 * 注意：这个文件仅用于测试，提交作业时不需要提交此文件
	 */

	int numPlayers = 6; // 修改：当你添加策略时，将6改为7
	Player makePlayer(int which) {
		switch (which) {
		case 0: return new NicePlayer();
		case 1: return new NastyPlayer();
		case 2: return new RandomPlayer();
		case 3: return new TolerantPlayer();
		case 4: return new FreakyPlayer();
		case 5: return new T4TPlayer();
		// 在这里添加你的策略，例如：
		//case 6: return new GongZeruiPlayer();
		}
		throw new RuntimeException("Bad argument passed to makePlayer");
	}
	// ===========================================

	/* ===========================================
	 * 以下代码不要修改
	 * ===========================================
	 * ===========================================
	 * ===========================================
	 */
	/*
	 这个Java程序模拟了三人囚徒困境游戏。
	 我们使用整数"0"表示合作，使用"1"表示背叛。

	 回忆一下，在二人囚徒困境中，U(DC) > U(CC) > U(DD) > U(CD)，其中
	 我们给出列表中第一个玩家的收益。我们希望三人游戏
	 在固定一个玩家的回应时类似于二人游戏，并且我们
	 也希望对称性，所以U(CCD) = U(CDC)等。这给出了唯一的排序

	 U(DCC) > U(CCC) > U(DDC) > U(CDC) > U(DDD) > U(CDD)

	 玩家1的收益由以下矩阵给出： */

	static int[][][] payoff = {
		{{6,3},  //当第一个和第二个玩家合作时的收益
		 {3,0}}, //当第一个玩家合作，第二个玩家背叛时的收益
		{{8,5},  //当第一个玩家背叛，第二个玩家合作时的收益
	     {5,2}}};//当第一个和第二个玩家都背叛时的收益

	/*
	 所以payoff[i][j][k]表示当第一个玩家的行动是i，第二个玩家的行动是j，
	 第三个玩家的行动是k时，玩家1的收益。

	 在这个模拟中，三个玩家将在一场"比赛"中重复相互对战。
	 一场比赛包含大约100轮，你从该比赛中获得的分数是
	 该比赛每轮收益的平均值。对于每一轮，你的策略会获得
	 之前游戏的列表（所以你可以记住你的对手做了什么）
	 并且必须计算下一个行动。  */


	static abstract class Player {
		// 这个过程接收到目前为止的轮数(n)，以及
		// 比赛中的之前游戏，并返回适当的行动。
		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			throw new RuntimeException("You need to override the selectAction method.");
		}

		// 用于提取这个玩家类的名称。
		final String name() {
			String result = getClass().getName();
			return result.substring(result.indexOf('$')+1);
		}
	}

	/* 这里有四个简单策略： */

	class NicePlayer extends Player {
		//NicePlayer总是合作
		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			return 0;
		}
	}

	class NastyPlayer extends Player {
		//NastyPlayer总是背叛
		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			return 1;
		}
	}

	class RandomPlayer extends Player {
		//RandomPlayer每次都随机选择他的行动
		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			if (Math.random() < 0.5)
				return 0;  //一半时间合作
			else
				return 1;  //一半时间背叛
		}
	}

	class TolerantPlayer extends Player {
		//TolerantPlayer查看他的对手历史，只有当
		//至少一半的其他玩家的行动是背叛时才背叛
		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			int opponentCoop = 0;
			int opponentDefect = 0;
			for (int i=0; i<n; i++) {
				if (oppHistory1[i] == 0)
					opponentCoop = opponentCoop + 1;
				else
					opponentDefect = opponentDefect + 1;
			}
			for (int i=0; i<n; i++) {
				if (oppHistory2[i] == 0)
					opponentCoop = opponentCoop + 1;
				else
					opponentDefect = opponentDefect + 1;
			}
			if (opponentDefect > opponentCoop)
				return 1;
			else
				return 0;
		}
	}

	class FreakyPlayer extends Player {
		//FreakyPlayer在比赛开始时决定
		//要么总是友好要么总是恶劣。
		//注意这个类有一个非平凡的构造函数。
		int action;
		FreakyPlayer() {
			if (Math.random() < 0.5)
				action = 0;  //一半时间合作
			else
				action = 1;  //一半时间背叛
		}

		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			return action;
		}
	}

	class T4TPlayer extends Player {
		//在每次游戏时随机选择一个对手，
		//并对他们使用'以牙还牙'策略
		int selectAction(int n, int[] myHistory, int[] oppHistory1, int[] oppHistory2) {
			if (n==0) return 0; //默认合作
			if (Math.random() < 0.5)
				return oppHistory1[n-1];
			else
				return oppHistory2[n-1];
		}
	}


	/* 在我们的锦标赛中，每对策略将相互对战一场比赛。
	 这个过程模拟单场比赛并返回分数。 */
	float[] scoresOfMatch(Player A, Player B, Player C, int rounds) {
		int[] HistoryA = new int[0], HistoryB = new int[0], HistoryC = new int[0];
		float ScoreA = 0, ScoreB = 0, ScoreC = 0;

		for (int i=0; i<rounds; i++) {
			int PlayA = A.selectAction(i, HistoryA, HistoryB, HistoryC);
			int PlayB = B.selectAction(i, HistoryB, HistoryC, HistoryA);
			int PlayC = C.selectAction(i, HistoryC, HistoryA, HistoryB);
			ScoreA = ScoreA + payoff[PlayA][PlayB][PlayC];
			ScoreB = ScoreB + payoff[PlayB][PlayC][PlayA];
			ScoreC = ScoreC + payoff[PlayC][PlayA][PlayB];
			HistoryA = extendIntArray(HistoryA, PlayA);
			HistoryB = extendIntArray(HistoryB, PlayB);
			HistoryC = extendIntArray(HistoryC, PlayC);
		}
		float[] result = {ScoreA/rounds, ScoreB/rounds, ScoreC/rounds};
		return result;
	}

//	这是scoresOfMatch需要的辅助函数。
	int[] extendIntArray(int[] arr, int next) {
		int[] result = new int[arr.length+1];
		for (int i=0; i<arr.length; i++) {
			result[i] = arr[i];
		}
		result[result.length-1] = next;
		return result;
	}

	/* 最后，剩余的代码实际运行锦标赛。 */

	public static void main (String[] args) {
		ThreePrisonersDilemma instance = new ThreePrisonersDilemma();
		instance.runTournament();
	}

	boolean verbose = false; // 如果你得到太多文本输出，设置verbose = false

	void runTournament() {
		float[] totalScore = new float[numPlayers];

		// 这个循环让每三个玩家相互对战。
		// 注意我们包括重复：你的策略的两个副本将对战
		// 每个其他策略一次，你的策略的三个副本将对战一次。

		for (int i=0; i<numPlayers; i++) for (int j=i; j<numPlayers; j++) for (int k=j; k<numPlayers; k++) {

			Player A = makePlayer(i); // 创建每个玩家的新副本
			Player B = makePlayer(j);
			Player C = makePlayer(k);
			int rounds = 90 + (int)Math.rint(20 * Math.random()); // 在90到110轮之间
			float[] matchResults = scoresOfMatch(A, B, C, rounds); // 运行比赛
			totalScore[i] = totalScore[i] + matchResults[0];
			totalScore[j] = totalScore[j] + matchResults[1];
			totalScore[k] = totalScore[k] + matchResults[2];
			if (verbose)
				System.out.println(A.name() + " 得分 " + matchResults[0] +
						" 分, " + B.name() + " 得分 " + matchResults[1] +
						" 分, " + C.name() + " 得分 " + matchResults[2] + " 分.");
		}
		int[] sortedOrder = new int[numPlayers];
		// 这个循环按分数对玩家进行排序。
		for (int i=0; i<numPlayers; i++) {
			int j=i-1;
			for (; j>=0; j--) {
				if (totalScore[i] > totalScore[sortedOrder[j]])
					sortedOrder[j+1] = sortedOrder[j];
				else break;
			}
			sortedOrder[j+1] = i;
		}

		// 最后，打印排序结果。
		if (verbose) System.out.println();
		System.out.println("锦标赛结果");
		for (int i=0; i<numPlayers; i++)
			System.out.println(makePlayer(sortedOrder[i]).name() + ": "
				+ totalScore[sortedOrder[i]] + " 分.");

	} // runTournament()结束

} // PrisonersDilemma类结束
