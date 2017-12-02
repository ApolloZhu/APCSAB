package net.fcpsschools._1685666.maze;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author ApolloZhu, Pd. 1
 */
public class QueueBasedBFSMazeSolver extends MazeSolver {
    //    Stack<Step> path = new Stack<>();
    Queue<Step> pending = new LinkedList<>();

    // Same old thing, greedy algorithm
    protected void pushAllNextStepsFrom(Loc curLoc, /*targeting*/ Loc target) {
        int dR = target.getR() - curLoc.getR();
        int dC = target.getC() - curLoc.getC();
        if (Math.abs(dR) <= Math.abs(dC)) {
            if (dR != 0) pending.add(new Step(curLoc, dR < 0 ? Direction.UP : Direction.DOWN));
            if (dC != 0) pending.add(new Step(curLoc, dC < 0 ? Direction.LEFT : Direction.RIGHT));
            if (dR <= 0) pending.add(new Step(curLoc, Direction.DOWN));
            if (dR >= 0) pending.add(new Step(curLoc, Direction.UP));
            if (dC <= 0) pending.add(new Step(curLoc, Direction.RIGHT));
            if (dC >= 0) pending.add(new Step(curLoc, Direction.LEFT));
        } else {
            if (dC != 0) pending.add(new Step(curLoc, dC < 0 ? Direction.LEFT : Direction.RIGHT));
            if (dR != 0) pending.add(new Step(curLoc, dR < 0 ? Direction.UP : Direction.DOWN));
            if (dC <= 0) pending.add(new Step(curLoc, Direction.RIGHT));
            if (dC >= 0) pending.add(new Step(curLoc, Direction.LEFT));
            if (dR <= 0) pending.add(new Step(curLoc, Direction.DOWN));
            if (dR >= 0) pending.add(new Step(curLoc, Direction.UP));
        }
    }

    @Override
    protected boolean start(int r, int c, int tR, int tC) {
        // Setup
        forEachListener(l -> l.started(r, c, tR, tC, getGrid()));
        final Loc start = new Loc(r, c), end = new Loc(tR, tC);
        boolean hasPath = false;
        Step curStep = new Step(start, Direction.NONE);
        // Mainloop
        while (curStep != null) {
            Step copy = curStep;
            forEachListener(l -> l.tryout(copy.getStart().getR(), copy.getStart().getC(),
                    copy.getDirection(), null, getGrid()));
            final Loc curLoc = curStep.getEnd();
            if (curLoc.equals(end)) {
                hasPath = true;
                forEachListener(l -> l.found(tR, tC, null, getGrid()));
                break;
            }
            int curR = curLoc.getR(), curC = curLoc.getC();
            if (get(curR, curC) == MazeCoder.Block.EMPTY) {
//                path.push(curStep);
                set(curLoc, MazeCoder.Block.PATH);
                System.out.println(curLoc);
                pushAllNextStepsFrom(curLoc, end);
            } else {
                if (curStep.isLastStep()) {
                    Loc failed = curStep.getStart();
                set(failed, MazeCoder.Block.VISITED);
                forEachListener(l -> l.failed(failed.getR(),
                        failed.getC(), null, getGrid()));
                }
//                while (!pending.isEmpty() && path.peek().getDirection() != Direction.NONE
//                    && !path.peek().getEnd().equals(pending.peek().getStart())) {
//
            }
            curStep = pending.isEmpty() ? null : pending.remove();
        }
        // End search
        boolean copy = hasPath;
        forEachListener(l -> l.ended(copy, getGrid()));
        return hasPath;
    }

    public static class Step extends StackBasedMazeSolver.Step {
        private boolean isLastStep = false;

        public Step(Loc start, Direction direction) {
            super(start, direction);
        }

        public static Step lastStep(Loc start, Direction direction) {
            Step step = new Step(start, direction);
            step.isLastStep = true;
            return step;
        }

        public boolean isLastStep() {
            return isLastStep;
        }
    }
}
