package game;

/**
 * Created by jakedavies on 15-01-12.
 */
public class GameTimer {

        private long startTime;

        public void start(){
            startTime = System.currentTimeMillis();
        }
        public long msRemaining()
        {
            return System.currentTimeMillis() - startTime;
        }
        //less than 2 seconds left to make a move, time up
        public boolean timeUp() {
            return (System.currentTimeMillis() - startTime) < 2000;
        }
}
