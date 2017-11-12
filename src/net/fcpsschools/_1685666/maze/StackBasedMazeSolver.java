package net.fcpsschools._1685666.maze;

import java.util.Stack;

/**
 * @author ApolloZhu, Pd. 1
 */
public class StackBasedMazeSolver extends MazeSolver {
    private static String debugDescriptionOf(Stack<Step> steps) {
        StringBuilder sb = new StringBuilder();
        for (Step s : steps) {
            sb.append('#');
            sb.append(s.pass);
            sb.append(s.start);
            sb.append(' ');
            sb.append(s.direction);
            sb.append(' ');
        }
        return sb.toString();
    }

    @Override
    protected boolean start(int r, int c, int targetR, int targetC) {
        // Setup
        forEachListener(l -> l.started(r, c, targetR, targetC, getGrid()));
        Loc start = new Loc(r, c), end = new Loc(targetR, targetC);
        boolean hasPath = false;
        Stack<Step> steps = new Stack<>();
        steps.push(new Step(start, Direction.NONE));
        // Mainloop
        while (!steps.isEmpty()) {
            Step lastStep = steps.peek();
            Loc cur = lastStep.getEnd();
            int curR = cur.getR(), curC = cur.getC();
            Direction lastStepDirection = lastStep.direction;
            // First time here
            if (lastStep.pass == 0 && lastStepDirection != Direction.NONE) {
                // Fire last step event.
                Loc back = lastStepDirection.reverse(curR, curC);
                forEachListener(l -> l.tryout(back.getR(), back.getC(),
                        lastStepDirection, steps, getGrid()));
                // Is invalid
                if (get(cur) != MazeCoder.Block.EMPTY) {
                    steps.pop();
                    steps.peek().nextStepFailed();
                    continue;
                }
            }
            set(cur, MazeCoder.Block.PATH);
            if (hasPath = isAtTarget(steps, end)) break;
            // Next step
            if (!pushNextStep(steps, end)) {
                if (lastStepDirection == Direction.NONE) break;
                set(cur, MazeCoder.Block.VISITED);
                forEachListener(l -> l.failed(curR, curC, steps, getGrid()));
                steps.pop();
                steps.peek().nextStepFailed();
            }

        }
        // End search
        boolean copy = hasPath;
        forEachListener(l -> l.ended(copy, getGrid()));
        return hasPath;
    }

    private boolean isAtTarget(Stack<Step> steps, Loc end) {
        if (steps.peek().getEnd().equals(end)) {
            forEachListener(l -> l.found(end.getR(), end.getC(), steps, getGrid()));
            return true;
        }
        return false;
    }

    // Almost shortest path, but not really.
    private boolean pushNextStep(Stack<Step> steps, Loc target) {
        Step curStep = steps.peek(), nextStep = null;
        Loc curLoc = curStep.getEnd();
        int dX = target.getR() - curLoc.getR();
        int dY = target.getC() - curLoc.getC();
        boolean isPriorityX = Math.abs(dX) <= Math.abs(dY);
        while (true) {
            if (isPriorityX && curStep.pass == 0 || !isPriorityX && curStep.pass == 1)
                if (dX == 0) {
                    curStep.nextStepFailed();
                    continue;
                } else nextStep = new Step(curLoc, dX < 0 ? Direction.UP : Direction.DOWN);
            if (isPriorityX && curStep.pass == 1 || !isPriorityX && curStep.pass == 0)
                if (dY == 0) {
                    curStep.nextStepFailed();
                    continue;
                } else nextStep = new Step(curLoc, dY < 0 ? Direction.LEFT : Direction.RIGHT);
            if (isPriorityX && curStep.pass / 2 == 1 || !isPriorityX && curStep.pass / 2 == 2) {
                if (dX > 0 || dX == 0 && curStep.pass == 4) curStep.nextStepFailed();
                nextStep = new Step(curLoc, curStep.pass % 2 == 0 ? Direction.DOWN : Direction.UP);
            } else if (isPriorityX && curStep.pass / 2 == 2 || !isPriorityX && curStep.pass / 2 == 1) {
                if (dY > 0 || dY == 0 && curStep.pass == 4) curStep.nextStepFailed();
                nextStep = new Step(curLoc, curStep.pass % 2 == 0 ? Direction.RIGHT : Direction.LEFT);
            }
            if (nextStep == null) return false;
            break;
        }
        steps.push(nextStep);
        return true;
    }

    public static class Step {
        private Loc start;
        private Direction direction;
        private int pass;

        public Step(Loc start, Direction direction) {
            this.start = start;
            this.direction = direction == null
                    ? Direction.NONE : direction;
        }

        public Loc getEnd() {
            return direction.forward(start);
        }

        public void nextStepFailed() {
            pass++;
        }
    }
}
