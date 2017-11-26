package net.fcpsschools._1685666.maze;

import java.util.Stack;

/**
 * @author ApolloZhu, Pd. 1
 */
public class StackBasedDFSMazeSolver extends MazeSolver {
    private Stack<Step> pending = new Stack<>();
    private Stack<Step> path = new Stack<>();

    // Same old thing, greedy algorithm
    protected void pushNextStepsFrom(Loc curLoc, /*targeting*/ Loc target) {
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
            Loc curLoc = curStep.getEnd();
            if (curLoc.equals(end)) {
                hasPath = true;
                forEachListener(l -> l.found(tR, tC, path, getGrid()));
                break;
            }
            if (get(curLoc) == MazeCoder.Block.EMPTY) {
                // Push the next steps
                pushNextStepsFrom(curLoc, pending, end);
            }

            int curR = cur.getR(), curC = cur.getC();
            Direction lastStepDirection = lastStep.direction;
            // First time here
            if (lastStep.pass == 0 && lastStepDirection != Direction.NONE) {
                // Fire last step event.

                // Is invalid
                if (get(cur) != MazeCoder.Block.EMPTY) {
                    steps.pop();
                    steps.peek().nextStepFailed();
                    continue;
                }
            }
            set(cur, MazeCoder.Block.PATH);
            // Next step
            nextStep = pending.isEmpty() ? null : pending.pop();
            if (nextStep == null) break;
            while (!path.isEmpty() && !pending.isEmpty() &&
                    !nextStep.getStart().equals(curStep.getStart())) {
                if (lastStepDirection == Direction.NONE) break;
                set(curLoc, MazeCoder.Block.VISITED);
                forEachListener(l -> l.failed(curR, curC, path, getGrid()));
                path.pop();
            }
            Step copy = curStep = nextStep;
            path.push(copy);
            forEachListener(l -> l.tryout(copy.getStart().getR(), copy.getStart().getC(),
                    copy.getDirection(), path, getGrid()));
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
