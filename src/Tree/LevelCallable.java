package Tree;

import game.Action;
import game.Constants;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import ai.AmazonSuccessorByte;

public class LevelCallable implements Callable<ArrayList<Node>> {

	private ArrayList<Node> list;
	private boolean white;
	private AmazonSuccessorByte sf;
	private long startTime;

	public LevelCallable(boolean white, AmazonSuccessorByte sf,
			long startTime, int quartile, int threadCount, ArrayList<Node> actions) {
		this.white = white;
		this.sf = sf;
		this.startTime = startTime;

		int to = actions.size() / threadCount;

		list = new ArrayList<Node>();
		int end = to * quartile;
		int start = to * (quartile - 1);

		for (int i = start; i < end; i++) {
			list.add(actions.get(i));
		}
	}

	@Override
	public ArrayList<Node> call() throws Exception {
		ArrayList<Node> levelNodes = new ArrayList<>();

		for (Node n : list) {
			ArrayList<Action> level = new ArrayList<>();
			if (System.currentTimeMillis() - startTime <= Constants.TIME_OUT_GEN) {
				boolean broken = false;
				level.addAll(sf.generateTreeLevelThreaded(n.getAction()
						.makeThisMove(), white));
				for (Action a : level) {
					if (System.currentTimeMillis() - startTime <= Constants.TIME_OUT_GEN) {
					Node adder = new Node(a, white);
					levelNodes.add(adder);
					adder.setParent(n);
					a.getValue(white);// Links up but not back down so that tree
					}else{
						throw new Exception();
					}
				}
			}else{
				throw new Exception();
			}
		}
		return levelNodes;
	}
}
