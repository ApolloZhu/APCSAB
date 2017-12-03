package net.fcpsschools._1685666.maze;

import java.util.Stack;

import static net.fcpsschools._1685666.maze.StackBasedMazeSolver.Step;

/**
 * @author ApolloZhu, Pd. 1
 */
public class StackBasedDFSMazeSolver extends MazeSolver {
    private Stack<Step> pending, path;

    // Same old thing, greedy algorithm
    protected void pushAllNextStepsFrom(Loc curLoc, /*targeting*/ Loc target) {
        int dX = target.getR() - curLoc.getR();
        int dY = target.getC() - curLoc.getC();
        if (Math.abs(dX) <= Math.abs(dY)) {
            if (dY >= 0) pending.push(new Step(curLoc, Direction.LEFT));
            if (dY <= 0) pending.push(new Step(curLoc, Direction.RIGHT));
            if (dX >= 0) pending.push(new Step(curLoc, Direction.UP));
            if (dX <= 0) pending.push(new Step(curLoc, Direction.DOWN));
            if (dY != 0) pending.push(new Step(curLoc, dY < 0 ? Direction.LEFT : Direction.RIGHT));
            if (dX != 0) pending.push(new Step(curLoc, dX < 0 ? Direction.UP : Direction.DOWN));
        } else {
            if (dX >= 0) pending.push(new Step(curLoc, Direction.UP));
            if (dX <= 0) pending.push(new Step(curLoc, Direction.DOWN));
            if (dY >= 0) pending.push(new Step(curLoc, Direction.LEFT));
            if (dY <= 0) pending.push(new Step(curLoc, Direction.RIGHT));
            if (dX != 0) pending.push(new Step(curLoc, dX < 0 ? Direction.UP : Direction.DOWN));
            if (dY != 0) pending.push(new Step(curLoc, dY < 0 ? Direction.LEFT : Direction.RIGHT));
        }
    }

    @Override
    protected boolean start(int r, int c, int tR, int tC) {
        // Setup
        pending = new Stack<>();
        path = new Stack<>();
        forEachListener(l -> l.started(r, c, tR, tC, getGrid()));
        final Loc start = new Loc(r, c), end = new Loc(tR, tC);
        boolean hasPath = false;
        Step curStep = new Step(start, Direction.NONE);
        // Mainloop
        while (curStep != null) {
            Step copy = curStep;
            forEachListener(l -> l.tryout(copy.getStart().getR(), copy.getStart().getC(),
                    copy.getDirection(), path, getGrid()));
            final Loc curLoc = curStep.getEnd();
            if (curLoc.equals(end)) {
                hasPath = true;
                set(tR, tC, MazeCoder.Block.PATH);
                forEachListener(l -> l.found(tR, tC, path, getGrid()));
                break;
            }
            int curR = curLoc.getR(), curC = curLoc.getC();
            if (get(curR, curC) == MazeCoder.Block.EMPTY) {
                path.push(curStep);
                set(curLoc, MazeCoder.Block.PATH);
                pushAllNextStepsFrom(curLoc, end);
            } else while (!pending.isEmpty() && path.peek().getDirection() != Direction.NONE
                    && !path.peek().getEnd().equals(pending.peek().getStart())) {
                Step step = path.pop();
                set(step.getEnd(), MazeCoder.Block.VISITED);
                forEachListener(l -> l.failed(step.getEnd().getR(), step.getEnd().getC(), path, getGrid()));
            }
            curStep = pending.isEmpty() ? null : pending.pop();
        }
        // End search
        boolean copy = hasPath;
        forEachListener(l -> l.ended(copy, getGrid()));
        return hasPath;
    }
}
