package net.fcpsschools._1685666.maze;

import java.util.Stack;

/**
 * @author ApolloZhu, Pd. 1
 */
public class StackBasedDFSMazeSolver extends MazeSolver {
    private Stack<Step> pending = new Stack<>();
    private Stack<Step> path = new Stack<>();

    // Same old thing, greedy algorithm
    protected void pushAllNextStepsFrom(Loc curLoc, /*targeting*/ Loc target) {
        int dX = target.getR() - curLoc.getR();
        int dY = target.getC() - curLoc.getC();
        boolean isPriorityX = Math.abs(dX) <= Math.abs(dY);
        if (isPriorityX) {
            if (dX != 0) pending.push(new Step(curLoc, dX < 0 ? Direction.UP : Direction.DOWN));
            if (dY != 0) pending.push(new Step(curLoc, dY < 0 ? Direction.LEFT : Direction.RIGHT));
            if (dX <= 0) pending.push(new Step(curLoc, Direction.DOWN));
            if (dX >= 0) pending.push(new Step(curLoc, Direction.UP));
            if (dY <= 0) pending.push(new Step(curLoc, Direction.RIGHT));
            if (dY >= 0) pending.push(new Step(curLoc, Direction.LEFT));
        } else {
            if (dY != 0) pending.push(new Step(curLoc, dY < 0 ? Direction.LEFT : Direction.RIGHT));
            if (dX != 0) pending.push(new Step(curLoc, dX < 0 ? Direction.UP : Direction.DOWN));
            if (dY <= 0) pending.push(new Step(curLoc, Direction.RIGHT));
            if (dY >= 0) pending.push(new Step(curLoc, Direction.LEFT));
            if (dX <= 0) pending.push(new Step(curLoc, Direction.DOWN));
            if (dX >= 0) pending.push(new Step(curLoc, Direction.UP));
        }
    }

    @Override
    protected boolean start(int r, int c, int tR, int tC) {
        // Setup
        forEachListener(l -> l.started(r, c, tR, tC, getGrid()));
        Loc start = new Loc(r, c), end = new Loc(tR, tC);
        boolean hasPath = false;
        Step curStep = new Step(start, Direction.NONE), nextStep = null;
        // Mainloop
        while (curStep != null) {
            Step copy = curStep;
            forEachListener(l -> l.tryout(copy.getStart().getR(), copy.getStart().getC(),
                    copy.getDirection(), path, getGrid()));
            Loc curLoc = curStep.getEnd();
            if (curLoc.equals(end)) {
                hasPath = true;
                forEachListener(l -> l.found(tR, tC, path, getGrid()));
                break;
            }
            int curR = curLoc.getR(), curC = curLoc.getC();
            if (get(curR, curC) == MazeCoder.Block.EMPTY) {
                path.push(curStep);
                set(curLoc, MazeCoder.Block.PATH);
                pushAllNextStepsFrom(curLoc, end);
            } else {
                while (!pending.isEmpty() && path.peek().getDirection() != Direction.NONE
                        && !path.peek().getEnd().equals(pending.peek().getStart())) {
                    Step step = path.pop();
                    set(step.getEnd(), MazeCoder.Block.VISITED);
                    forEachListener(l -> l.failed(step.getEnd().getR(),
                            step.getEnd().getC(), path, getGrid()));
                }
            }
            curStep = pending.isEmpty() ? null : pending.pop();
        }
        // End search
        boolean copy = hasPath;
        forEachListener(l -> l.ended(copy, getGrid()));
        return hasPath;
    }

    public static class Step extends StackBasedMazeSolver.Step {
        public Step(Loc start, Direction direction) {
            super(start, direction);
        }
    }
}
